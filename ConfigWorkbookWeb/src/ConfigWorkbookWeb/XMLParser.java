package ConfigWorkbookWeb;


import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParser {
	// This method fetches object information from metadata
	public static Map<Integer, Object[]> parseObject(InputStream input)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(input);

		Map<Integer, Object[]> data = new HashMap<Integer, Object[]>();

		data.put(0,
				new Object[] { "Field Label", "Field Name", "Data Type",
						"Length", "Required", "Unique", "ExternalId",
						"Picklist Values" });

		NodeList listOfFields = doc.getElementsByTagName("fields");

		for (int s = 0; s < listOfFields.getLength(); s++) {

			Object[] objArr = new Object[8];
			Node name = listOfFields.item(s);
			if (name.getNodeType() == Node.ELEMENT_NODE) {

				Element fieldName = (Element) name;

				NodeList fullNameNode = fieldName
						.getElementsByTagName("fullName");

				if (fullNameNode.getLength() > 0) {
					objArr[1] = fullNameNode.item(0).getTextContent();
				}

				NodeList labelNode = fieldName.getElementsByTagName("label");

				if (labelNode.getLength() > 0) {
					objArr[0] = labelNode.item(0).getTextContent();
				} else {
					objArr[0] = fullNameNode.item(0).getTextContent();
				}

				NodeList dataTypeNode = fieldName.getElementsByTagName("type");

				if (dataTypeNode.getLength() > 0) {
					objArr[2] = dataTypeNode.item(0).getTextContent();
				}

				NodeList precisionNode = fieldName
						.getElementsByTagName("precision");

				if (precisionNode.getLength() > 0) {
					objArr[3] = precisionNode.item(0).getTextContent()
							+ ","
							+ fieldName.getElementsByTagName("scale").item(0)
									.getTextContent();
				}

				NodeList requiredNode = fieldName
						.getElementsByTagName("required");

				if (requiredNode.getLength() > 0) {
					objArr[4] = requiredNode.item(0).getTextContent();
				}

				NodeList uniqueNode = fieldName.getElementsByTagName("unique");

				if (uniqueNode.getLength() > 0) {
					objArr[5] = uniqueNode.item(0).getTextContent();
				}

				NodeList exterNalIdNode = fieldName
						.getElementsByTagName("externalId");

				if (exterNalIdNode.getLength() > 0) {
					objArr[6] = exterNalIdNode.item(0).getTextContent();
				}

				NodeList pickListNode = fieldName
						.getElementsByTagName("picklistValues");

				String picklistVals = "";

				for (int i = 0; i < pickListNode.getLength(); i++) {

					if (name.getNodeType() == Node.ELEMENT_NODE) {
						Element picklistElement = (Element) pickListNode
								.item(i);

						picklistVals += picklistElement
								.getElementsByTagName("fullName").item(0)
								.getTextContent()
								+ "\n";
					}
				}
				objArr[7] = picklistVals;

			}

			data.put(Integer.valueOf(s + 1), objArr);
		}

		return data;
	}

	// This method fetches class and trigger information from metadata
	public static Object[] parseClasses(String className, InputStream input)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(input);

		Object[] data = new Object[3];

		data[0] = className;

		NodeList apiVersion = doc.getElementsByTagName("apiVersion");

		if (apiVersion.getLength() > 0) {
			data[1] = apiVersion.item(0).getTextContent();
		}

		NodeList status = doc.getElementsByTagName("status");

		if (status.getLength() > 0) {
			data[2] = status.item(0).getTextContent();
		}

		return data;
	}

	// This method fetches pages from metadata
	public static Object[] parsePages(InputStream input)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(input);

		Object[] data = new Object[2];

		NodeList pageName = doc.getElementsByTagName("label");

		if (pageName.getLength() > 0) {
			data[0] = pageName.item(0).getTextContent();
		}

		NodeList apiVersion = doc.getElementsByTagName("apiVersion");

		if (apiVersion.getLength() > 0) {
			data[1] = apiVersion.item(0).getTextContent();
		}

		return data;
	}

	// This method fetches validation rules from metadata
	public static Object[] parseValidations(InputStream input)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(input);

		Object[] data = new Object[6];
		NodeList listOfField = doc.getElementsByTagName("validationRules");

		if (listOfField.getLength() < 1) {
			return null;
		}

		for (int p = 0; p < listOfField.getLength(); p++) {

			NodeList fullNameNode1 = doc.getElementsByTagName("fullName");

			if (fullNameNode1.getLength() > 0) {
				data[0] = fullNameNode1.item(0).getTextContent();
			}

			NodeList labelNode1 = doc.getElementsByTagName("active");

			if (labelNode1.getLength() > 0) {
				data[1] = labelNode1.item(0).getTextContent();
			}

			NodeList dataTypeNode1 = doc.getElementsByTagName("description");

			if (dataTypeNode1.getLength() > 0) {
				data[2] = dataTypeNode1.item(0).getTextContent();
			}

			NodeList requiredNode1 = doc
					.getElementsByTagName("errorConditionFormula");

			if (requiredNode1.getLength() > 0) {
				data[3] = requiredNode1.item(0).getTextContent();
			}

			NodeList uniqueNode1 = doc
					.getElementsByTagName("errorDisplayField");

			if (uniqueNode1.getLength() > 0) {
				data[4] = uniqueNode1.item(0).getTextContent();
			}

			NodeList exterNalIdNode1 = doc.getElementsByTagName("errorMessage");

			if (exterNalIdNode1.getLength() > 0) {
				data[5] = exterNalIdNode1.item(0).getTextContent();
			}
		}
		return data;
	}

}