
package com.vtg.auto.reusablecomponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.vtg.auto.utilities.ExcelToJSONConverter;
import com.vtg.auto.utilities.excelSheetReaderUtil;

public class Initialize extends GlobalVariables

{
	public String configkey, configvalue;
	public XSSFWorkbook wb;
	public XSSFSheet ws;
	public int rowNum;
	public static int i;
	public int Colnum;
	public XSSFRow row;
	public File[] files;
	public String Fileneme = null;

	/*
	 * Purpose : This method loads the the properties file for config, object and
	 * database Parameters : None Returns : None Sample Method call : initConfig();
	 * Pre-conditions (if any) : The name of the proerties file in 'config' package
	 * should be specified in lowercase
	 */
	public void initializeObjects() {
		getConfigData();
		convertoJSON();

		if (configData.get("ObjectRepositoryFileType").toString().equalsIgnoreCase("Propertiesfile")) {
			loadFiles();
		} else {
			loadObjectFromExcelFile();
		}

	}

	public void loadObjectFromPropertiesFile() {
		try {

			if (Object == null) {

				Object = new Properties();
				FileInputStream file1 = new FileInputStream(
						System.getProperty("user.dir") + "/src/test/resources/objectrepository/admin.properties");
				Object.load(file1);
				FileInputStream file2 = new FileInputStream(
						System.getProperty("user.dir") + "/src/test/resources/objectrepository/Common.properties");
				Object.load(file2);
				FileInputStream file3 = new FileInputStream(
						System.getProperty("user.dir") + "/src/test/resources/objectrepository/description.properties");
				Object.load(file3);
				FileInputStream file4 = new FileInputStream(
						System.getProperty("user.dir") + "/src/test/resources/objectrepository/pricing.properties");
				Object.load(file4);
				FileInputStream file5 = new FileInputStream(System.getProperty("user.dir")
						+ "/src/test/resources/objectrepository/wincalculator.properties");
				Object.load(file5);

				// }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void fileListFromFolder() {
		File file = new File(System.getProperty("user.dir") + "/src/test/resources/objectrepository/");
		files = file.listFiles();

	}

	// The function gets the parameters from Configuration sheet in Excel file
	// and put it into Config data structure.

	// --------------------------------------------------Config
	// Properties---------------------------------------------------------------------------//

	// --------------------------------------------------Config
	// Properties---------------------------------------------------------------------------//

	// --------------------------------------------------Object
	// Properties---------------------------------------------------------------------------//

	/*
	 * Purpose : This method loads the the properties file objects Parameters : None
	 * Returns : None Sample Method call : initObjects(); Pre-conditions (if any) :
	 * None
	 */
	public static void loadObjectFromExcelFile() {

		try {
			String fileName = GlobalVariables.workingDir + "/src/test/resources/objectrepository/"
					+ GlobalVariables.configData.get("ObjectRepositoryFile") + ".xlsx";
			loadObjectRepository(fileName);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void loadObjectRepository(String fileName) {
		HashSet<String> hsBrowser = new HashSet<String>();
		HashSet<String> hsPage = new HashSet<String>();
		DocumentBuilderFactory docFactory;
		DocumentBuilder docBuilder;
		Document doc = null;
		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		try {
			excelSheetReaderUtil objectRepo = new excelSheetReaderUtil(fileName);

			Integer sheets = objectRepo.workBook.getNumberOfSheets();

			for (int j = 0; j < sheets; j++) {
				objectRepo.getSheetAtIndex(j);
				String sheetname = objectRepo.workSheet.getSheetName();
				if (sheetname.equals("Misc"))
					continue;
				objectRepo.workSheet.rowIterator();

				docFactory = DocumentBuilderFactory.newInstance();
				docBuilder = docFactory.newDocumentBuilder();

				// root elements
				doc = docBuilder.newDocument();
				Element rootElement = doc.createElement("Object");
				doc.appendChild(rootElement);

				int rowcount = objectRepo.getRowCount(sheetname);
				try {
					for (i = 2; i <= rowcount; i++)

					{
						// Get unique set of Browsers
						hsBrowser.add(objectRepo.getCellData(i, 1));
						// Get unique set of Pages
						hsPage.add(objectRepo.getCellData(i, 2));
					}
				} catch (Exception ex) {
				}

				// Add browsers to the xml
				if (hsBrowser.isEmpty())
					continue;
				for (String browser : hsBrowser) {
					try {
						Element browserElement = doc.createElement("Browser");
						browserElement.setAttribute("objectId", browser);
						rootElement.appendChild(browserElement);
					} catch (Exception ex) {
					}
				}

				// write the content into xml file
				File xmlFolder = new File(GlobalVariables.workingDir + "/src/test/resources/objectrepository/xml/");
				if (!xmlFolder.exists()) {
					xmlFolder.mkdir();
				}
				File xmlFile = new File(
						GlobalVariables.workingDir + "/src/test/resources/objectrepository/xml/" + sheetname + ".xml");
				if (xmlFile.exists())
					xmlFile.delete();
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				if (xmlFile.exists())
					xmlFile.delete();
				StreamResult result = new StreamResult(xmlFile);
				transformer.transform(source, result);

				try {
					doc = docBuilder.parse(xmlFile);
					for (i = 2; i <= rowcount; i++) {
						// Add pages to their respective browsers
						XPathExpression expr = xpath
								.compile("/Object/Browser[@objectId='" + objectRepo.getCellData(i, 1) + "']");
						NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
						if (nl.getLength() >= 1) {
							// Check if node has already been added
							for (String page : hsPage) {
								if (page == objectRepo.getCellData(i, 2)) {
									hsPage.remove(page);
									// Create page node and append
									try {
										Element pageElement = doc.createElement("Page");
										pageElement.setAttribute("objectId", objectRepo.getCellData(i, 2));
										nl.item(0).appendChild(pageElement);
									} catch (Exception ex) {
									}
									break;
								}
							}

						}
					}
				} catch (Exception ex) {
				}

				source = new DOMSource(doc);
				result = new StreamResult(xmlFile);
				transformer.transform(source, result);

				try {
					doc = docBuilder.parse(xmlFile);
					for (i = 2; i <= rowcount; i++) {
						// Add pages to their respective browsers
						XPathExpression expr = xpath
								.compile("/Object/Browser[@objectId='" + objectRepo.getCellData(i, 1)
										+ "']/Page[@objectId='" + objectRepo.getCellData(i, 2) + "']");
						NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
						if (nl.getLength() >= 1) {
							// Create page node and append
							Element objectElement = doc.createElement(objectRepo.getCellData(i, 3));
							objectElement.setAttribute("objectId", objectRepo.getCellData(i, 4));

							HashMap<String, String> hmAttributes = new HashMap<String, String>();
							for (int column = 5; column < objectRepo.workSheet.rowIterator().next()
									.getPhysicalNumberOfCells(); column++) {
								if (objectRepo.getCellData(0, column) == "Description")
									continue;
								try {
									if (objectRepo.getCellData(1, column).toString().isEmpty())
										break;
									if (!objectRepo.getCellData(i, column).toString().isEmpty())
										hmAttributes.put(objectRepo.getCellData(1, column),
												objectRepo.getCellData(i, column));
								} catch (Exception ex) {
								}
							}
							for (Map.Entry<String, String> entry : hmAttributes.entrySet()) {
								objectElement.setAttribute(entry.getKey(), entry.getValue());
							}
							nl.item(0).appendChild(objectElement);
						}
					}
				} catch (Exception ex) {
				}

				source = new DOMSource(doc);
				result = new StreamResult(xmlFile);
				transformer.transform(source, result);

				hsBrowser.clear();
				hsPage.clear();
			}
		} catch (Exception ex) {

		}

	}

	// --------------------------------------------------Object
	// Properties---------------------------------------------------------------------------//

	// --------------------------------------------------Database
	// Properties---------------------------------------------------------------------------//

	/*
	 * Purpose : This method loads the the properties file for database Parameters :
	 * None Returns : None Sample Method call : initDatabase(); Pre-conditions (if
	 * any) : None
	 */
	public void initDatabase() {

		try {
			if (Database == null) {
				Database = new Properties();
				FileInputStream fis3 = new FileInputStream(
						System.getProperty("user.dir") + "/src/config/database.properties");
				Database.load(fis3);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void convertoJSON() {

		try {
			ExcelToJSONConverter jsonconverter = new ExcelToJSONConverter();
			jsonconverter.creteJSONFileFromExcel();

		} catch (Exception HashmapException) {
		}

	}

	public static void getConfigData() {

		excelSheetReaderUtil configuration = new excelSheetReaderUtil(
				GlobalVariables.workingDir + "/src/test/resources/configuration/Config_Data.xlsx");
		configuration.getSheet("ConfigSheet");
		int row = configuration.getRowCount("ConfigSheet");

		String key, value;
		try {
			for (i = 1; i <= row; i++) {
				key = configuration.getCellData(i, 0).toString().trim();
				if (configuration.getCellData(i, 1).toString().isEmpty())
					value = "";
				else
					value = configuration.getCellData(i, 1).toString().trim();
				GlobalVariables.configData.put(key, value);
			}

		} catch (Exception HashmapException) {
		}

	}

	public List<String> getAllPropertiesFiles() {
		List<String> propertiesFiles = new ArrayList<String>();
		File file = new File(GlobalVariables.configData.get("ObjectRepositoryFilePath"));
		File[] files = file.listFiles();
		for (File f : files) {
			boolean fileName = f.getName().endsWith(".properties");
			if (fileName) {
				propertiesFiles.add(f.getName());
			}
		}
		return propertiesFiles;
	}

	public static List<String> getAllPropertiesFile() {

		File folder = new File(System.getProperty("user.dir") + "/src/test/resources/objectrepository/");
		File[] listOfFiles = folder.listFiles();
		List<String> allFileNames = new ArrayList<String>();

		for (int i = 0; i < listOfFiles.length; i++) {
			String fileName = FilenameUtils.getExtension(listOfFiles[i].getName());
			if (fileName.equalsIgnoreCase("Properties")) {
				allFileNames.add(listOfFiles[i].getName());
			}
		}
		return allFileNames;
	}

	public void getProperties(String strPropertyFileName) {
		try {
			InputStream objFileReader = new FileInputStream(
					System.getProperty("user.dir") + "/src/test/resources/objectrepository/" + strPropertyFileName);
			if (Object == null) {

				Object = new Properties();
			}
			Object.load(objFileReader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadFiles() {
		List<String> propertiesFileName = getAllPropertiesFile();
		for (String fileName : propertiesFileName) {
			getProperties(fileName);
		}
	}

}