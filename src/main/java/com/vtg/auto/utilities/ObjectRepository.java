package com.vtg.auto.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



public class ObjectRepository {
	public static String xmlFile = null;
	String value = null;
	public static Document document = null;
	static DocumentBuilderFactory builderFactory = null;
	public static DocumentBuilder builder = null;

	public static void Object_Repository(String xmlFile) {
		try {
			builderFactory = DocumentBuilderFactory.newInstance();
			builder = builderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		try {
			document = builder.parse(new FileInputStream(xmlFile));
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// gets the node value in the Object Repository file
	public String getNodeValue(String xpath) {
		XPath xPath = XPathFactory.newInstance().newXPath();

		try {
			value = xPath.evaluate(xpath, document);
		} catch (XPathExpressionException ex) {
			Logger.getLogger(ObjectRepository.class.getName()).log(Level.SEVERE, null, ex);
		}

		return value;
	}

	public static String getNodeProperty(String objectName, String sPropertyName) {
		String returnValue = "";
		XPath xPath = XPathFactory.newInstance().newXPath();
		String xpath = null;
		try {
			// Create the xpath using object name
			xpath = "//*[@objectId='" + objectName + "']";
			// Compile the xpath to get Node with the xpath passed

			XPathExpression expr = xPath.compile(xpath);
			NodeList nl = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
			if (nl.getLength() == 0)
				throw new XPathExpressionException("No element found with the specified xpath.");
			else {
				// Get the property value
				returnValue = nl.item(0).getAttributes().getNamedItem(sPropertyName).getNodeValue();
			}
		} catch (XPathExpressionException ex) {
			Logger.getLogger(ObjectRepository.class.getName()).log(Level.SEVERE, null, ex);
		}

		return returnValue;
	}
}
