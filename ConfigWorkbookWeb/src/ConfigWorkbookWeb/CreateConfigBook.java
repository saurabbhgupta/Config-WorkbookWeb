package ConfigWorkbookWeb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class CreateConfigBook {
	//Fetching values from config file
	static private Properties prop = ConfigurationProperties.getPropValues();

	public static void configBookWrite() throws SAXException, IOException,
			ParserConfigurationException {
		//Fetching zip file and storing it in variable zf
		ZipFile zf = new ZipFile("retrieveresults.zip");
		//Creates enumeration list of zipfile entry
		Enumeration<? extends ZipEntry> entries = zf.entries();

		WriteToExcel wE = new WriteToExcel();
		//Creates array of classes
		ArrayList<Object[]> classArr = new ArrayList<Object[]>();
		classArr.add(new Object[] { "Class Name", "Version", "Status" });
		//Creates array of pages
		ArrayList<Object[]> pageArr = new ArrayList<Object[]>();
		pageArr.add(new Object[] { "Visualforce Page Name", "Version" });
		//Creates array of triggers
		ArrayList<Object[]> triggerArr = new ArrayList<Object[]>();
		triggerArr.add(new Object[] { "Trigger Name", "Version", "Status" });
		//Creates array of validation rules
		ArrayList<Object[]> validateArr = new ArrayList<Object[]>();
		validateArr.add(new Object[] { "Name", "Status", "Description",
				"Error Condition", "Error Display Field", "Error message" });
		//Reads zipfile and creates object sheets in xml file
		while (entries.hasMoreElements()) {
			ZipEntry ze = (ZipEntry) entries.nextElement();

			if (ze.getName().endsWith("object")) {

				Map<Integer, Object[]> data = XMLParser.parseObject(zf
						.getInputStream(ze));
				wE.createObjectSheet(
						ze.getName().substring(
								ze.getName().lastIndexOf("/") + 1,
								ze.getName().lastIndexOf(".object")), data);
				Object[] objArr = XMLParser.parseValidations(zf
						.getInputStream(ze));
				if (objArr != null) {
					validateArr.add(objArr);
				}
			} else if (ze.getName().endsWith("cls-meta.xml")) {
				String className = ze.getName().substring(
						ze.getName().lastIndexOf("/") + 1,
						ze.getName().lastIndexOf(".cls-meta.xml"));
				classArr.add(XMLParser.parseClasses(className,
						zf.getInputStream(ze)));
			} else if (ze.getName().endsWith("page-meta.xml")) {
				pageArr.add(XMLParser.parsePages(zf.getInputStream(ze)));
			} else if (ze.getName().endsWith("trigger-meta.xml")) {
				String className = ze.getName().substring(
						ze.getName().lastIndexOf("/") + 1,
						ze.getName().lastIndexOf(".trigger-meta.xml"));
				triggerArr.add(XMLParser.parseClasses(className,
						zf.getInputStream(ze)));
			}
		}
		//Creating excel sheet for classes
		if (classArr.size() > 0) {
			wE.createOtherSheets("Classes", classArr);	
		}
		//Creating excel sheet for VisualforcePages
		if (pageArr.size() > 0) {
			wE.createOtherSheets("Visualforce Pages", pageArr);
		}
		//Creating excel sheet for Triggers
		if (triggerArr.size() > 0) {
			wE.createOtherSheets("Triggers", triggerArr);
		}
		//Creating excel sheet for Validation Rules
		if (validateArr.size() > 0) {
			wE.createOtherSheets("Validation Rules", validateArr);
		}
		zf.close();
	}
	
}