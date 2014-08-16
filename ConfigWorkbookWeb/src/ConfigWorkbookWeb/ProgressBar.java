package ConfigWorkbookWeb;


import java.awt.*;
import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.sforce.soap.metadata.MetadataConnection;
import com.sforce.ws.ConnectionException;

import java.beans.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ProgressBar extends JPanel implements PropertyChangeListener {

	
	private static final long serialVersionUID = 1L;
	private JProgressBar progressBar;
	private JTextArea taskOutput;
	private Task task;
	public String username, password, endpoint;

	class Task extends SwingWorker<Void, Void> {
		/*
		 * Main task. Executed in background thread.
		 */
		@Override
		public Void doInBackground() {
			// Initialize progress property.
			setProgress(0);
			taskOutput.append("Logging In....\n");
			try {
				MetadataConnection metadataConnection = SalesforceLogin
						.getMetadataConnection(username, password, endpoint);

				setProgress(20);
				FileBasedMetadataCalls.retrieve(metadataConnection);
				setProgress(70);
				CreateConfigBook.configBookWrite();
				setProgress(100);
			} catch (ConnectionException e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String theMessage = sw.toString();
				taskOutput.append(theMessage);
			} catch (SAXException e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String theMessage = sw.toString();
				taskOutput.append(theMessage);
			} catch (IOException e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String theMessage = sw.toString();
				taskOutput.append(theMessage);
			} catch (ParserConfigurationException e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String theMessage = sw.toString();
				taskOutput.append(theMessage);
			} catch (Exception e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String theMessage = sw.toString();
				taskOutput.append(theMessage);
			}

			return null;
		}

		/*
		 * Executed in event dispatching thread
		 */
		@Override
		public void done() {
			Toolkit.getDefaultToolkit().beep();
			setCursor(null); // turn off the wait cursor
			taskOutput.append("Config Workbook Created...\n");
		}
	}

	public ProgressBar(String username, String password, String endpoint) {
		super(new BorderLayout());

		this.username = username;
		this.password = password;
		this.endpoint = endpoint;

		// Create the demo's UI.
		progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);

		taskOutput = new JTextArea(5, 20);
		taskOutput.setMargin(new Insets(5, 5, 5, 5));
		taskOutput.setEditable(false);

		JPanel panel = new JPanel();
		panel.add(progressBar);

		add(panel, BorderLayout.PAGE_START);
		add(new JScrollPane(taskOutput), BorderLayout.CENTER);
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		setBounds(200, 300, 200, 300);

		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		// Instances of javax.swing.SwingWorker are not reusuable, so
		// we create new instances as needed.
		task = new Task();
		task.addPropertyChangeListener(this);
		task.execute();
	}

	/**
	 * Invoked when task's progress property changes.
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress" == evt.getPropertyName()) {
			int progress = (Integer) evt.getNewValue();
			progressBar.setValue(progress);
			if (progress == 0) {
				taskOutput.append("Logging In...");
			} else if (progress == 20) {
				taskOutput.append("Logged In Successfully..\n");
				taskOutput.append("Retrieving Metadata Components..\n");
			} else if (progress == 70) {
				taskOutput.append("Metadata Retrieved..\n");
				taskOutput.append("Writing to Zip files...\n");
			}
		}
	}
}