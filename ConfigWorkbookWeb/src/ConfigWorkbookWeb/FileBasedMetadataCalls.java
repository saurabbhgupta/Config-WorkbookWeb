package ConfigWorkbookWeb;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.sforce.soap.metadata.AsyncRequestState;
import com.sforce.soap.metadata.AsyncResult;
import com.sforce.soap.metadata.MetadataConnection;
import com.sforce.soap.metadata.PackageTypeMembers;
import com.sforce.soap.metadata.RetrieveMessage;
import com.sforce.soap.metadata.RetrieveRequest;
import com.sforce.soap.metadata.RetrieveResult;

public class FileBasedMetadataCalls {
	// Fetching values from config file
	static private Properties prop = ConfigurationProperties.getPropValues();
	// one second in milliseconds
	private static final long ONE_SECOND = 1000;
	// maximum number of attempts to retrieve the results

	private static final int MAX_NUM_POLL_REQUESTS = 50;
	// manifest file that controls which components get retrieved
	//private static final String MANIFEST_FILE = prop.getProperty("packageXML");
	private static final String MANIFEST_FILE = "Workspace/ConfigWorkbookWeb/src/ConfigWoorkbookWeb/package.xml";
	private static final double API_VERSION = 30.0;
	private MetadataConnection metadataConnection = null;

	public static void retrieve(MetadataConnection metadataConnection)
			throws RemoteException, Exception {
		FileBasedMetadataCalls fBMC = new FileBasedMetadataCalls();
		fBMC.metadataConnection = metadataConnection;
		fBMC.retrieveZip();
	}

	public void retrieveZip() throws RemoteException, Exception {
		RetrieveRequest retrieveRequest = new RetrieveRequest();
		// The version in package.xml overrides the version in RetrieveRequest
		retrieveRequest.setApiVersion(API_VERSION);
		setUnpackaged(retrieveRequest);
		// Start the retrieve operation
		AsyncResult asyncResult = metadataConnection.retrieve(retrieveRequest);
		
		// Wait for the retrieve to complete
		int poll = 0;
		long waitTimeMilliSecs = ONE_SECOND;
		RetrieveResult result = null;
		do {
			Thread.sleep(waitTimeMilliSecs);
			// Double the wait time for the next iteration
			waitTimeMilliSecs *= 2;
			if (poll++ > MAX_NUM_POLL_REQUESTS) {
				throw new Exception(
						"Request timed out. If this is a large set "
								+ "of metadata components, check that the time allowed "
								+ "by MAX_NUM_POLL_REQUESTS is sufficient.");
			}
			asyncResult = metadataConnection
					.checkStatus(new String[] { asyncResult.getId() })[0];

		} while (!asyncResult.isDone());
		// If request has been retrieved successfully store it in result
		if (asyncResult.getState() != AsyncRequestState.Completed) {
			throw new Exception(asyncResult.getStatusCode() + " msg: "
					+ asyncResult.getMessage());
		}
		result = metadataConnection.checkRetrieveStatus(asyncResult.getId());
		// Print out any warning messages
		StringBuilder buf = new StringBuilder();
		if (result.getMessages() != null) {
			for (RetrieveMessage rm : result.getMessages()) {
				buf.append(rm.getFileName() + " - " + rm.getProblem());
			}
		}
		if (buf.length() > 0) {
			System.out.println("Retrieve warnings:\n" + buf);
		}
		// Write the zip to the file system
		System.out.println("Writing results to zip file");

		ByteArrayInputStream bais = new ByteArrayInputStream(
				result.getZipFile());
		File resultsFile = new File("retrieveResults.zip");
		FileOutputStream os = new FileOutputStream(resultsFile);
		try {
			// Copying result file from destination to source
			ReadableByteChannel src = Channels.newChannel(bais);
			FileChannel dest = os.getChannel();
			copy(src, dest);
			System.out.println("Results written to "
					+ resultsFile.getAbsolutePath());
		} finally {
			os.close();
		}
	}

	private static void setUnpackaged(RetrieveRequest request) throws Exception {
		// Edit the path, if necessary, if your package.xml file is located
		// elsewhere
		File unpackedManifest = new File(MANIFEST_FILE);
		System.out.println("Manifest file: "
				+ unpackedManifest.getAbsolutePath());
		if (!unpackedManifest.exists() || !unpackedManifest.isFile())
			throw new Exception("Should provide a valid retrieve manifest "
					+ "for unpackaged content. " + "Looking for "
					+ unpackedManifest.getAbsolutePath());
		com.sforce.soap.metadata.Package p = parsePackage(unpackedManifest);
		request.setUnpackaged(p);
	}

	private static void copy(ReadableByteChannel src, WritableByteChannel dest)
			throws IOException {
		// Use an in-memory byte buffer
		ByteBuffer buffer = ByteBuffer.allocate(8092);
		while (src.read(buffer) != -1) {
			buffer.flip();
			while (buffer.hasRemaining()) {
				dest.write(buffer);
			}
			buffer.clear();
		}
	}

	// Method parses metadata to xml
	private static com.sforce.soap.metadata.Package parsePackage(File file)
			throws Exception {
		InputStream inputstream = null;
		try {
			inputstream = new FileInputStream(file);

			List<PackageTypeMembers> pd = new ArrayList<PackageTypeMembers>();
			DocumentBuilder db = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Element d = db.parse(inputstream).getDocumentElement();
			for (Node c = d.getFirstChild(); c != null; c = c.getNextSibling()) {
				if (c instanceof Element) {
					Element ce = (Element) c;
			
					NodeList namee = ce.getElementsByTagName("name");
					if (namee.getLength() == 0) {
						// not
						continue;
					}

					String name = namee.item(0).getTextContent();
					NodeList m = ce.getElementsByTagName("members");
					List<String> members = new ArrayList<String>();
					for (int i = 0; i < m.getLength(); i++) {
						Node mm = m.item(i);
						members.add(mm.getTextContent());
					}
					PackageTypeMembers pdi = new PackageTypeMembers();
					pdi.setName(name);
					pdi.setMembers(members.toArray(new String[members.size()]));
					pd.add(pdi);
				}
			}
			com.sforce.soap.metadata.Package r = new com.sforce.soap.metadata.Package();
			r.setTypes(pd.toArray(new PackageTypeMembers[pd.size()]));
			r.setVersion(API_VERSION + "");
			return r;
		} catch (ParserConfigurationException pce) {
			throw new Exception("Cannot create XML parser", pce);
		} catch (IOException ioe) {
			throw new Exception(ioe);
		} catch (SAXException se) {
			throw new Exception(se);
		}
		finally{
			inputstream.close();
		}
	}
	
}