package com.vtg.auto.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.vtg.auto.reusablecomponents.GlobalVariables;
import com.vtg.auto.reusablecomponents.Initialize;


public class XmlTestSuiteGenerator extends BaseTest {

	DocumentBuilderFactory documentBuildFactory;
	File testSuiteFile;
	//String dataSheetPath = GlobalVariables.workingDir + "/src/test/resources/testdata/TestData.xlsx";
	String dataSheetPath =GlobalVariables.configData.get("TestDataPath");
	FileInputStream fileInputStream;
	List<String> executableClassMethods;
	public Set<String> allModules = new HashSet<String>();
	protected Initialize initialize = new Initialize();

	private Set<String> getAllModules() throws Exception {
		// TODO Auto-generated method stub
		fileInputStream = new FileInputStream(GlobalVariables.configData.get("TestDataPath"));
		Workbook workBook = new XSSFWorkbook(fileInputStream);
		XSSFSheet workSheet = (XSSFSheet) workBook.getSheet("Modules");
		int lastRowNow = workSheet.getLastRowNum();
		int calumnNum = 0;
		String module, runManagerValue;
		XSSFCell cell;
		XSSFRow row;
		for (int columnValue = calumnNum; columnValue < 1; columnValue++) {
			for (int rowNum = 1; rowNum <= lastRowNow; rowNum++) {
				row = workSheet.getRow(rowNum);
				//cell = row.getCell(calumnNum);
				cell = row.getCell(calumnNum);
				module = stringtype(cell);
				cell = row.getCell(calumnNum + 1);
				runManagerValue = stringtype(cell);
				if (runManagerValue.equalsIgnoreCase("Yes"))
					allModules.add("com.vtg.auto." + module);
			}
		}

		return allModules;
	}

	private LinkedHashSet<String> getAllClasses(Set<String> modules) throws Exception {
		LinkedHashSet<String> allClasses = new LinkedHashSet<String>();
		fileInputStream = new FileInputStream(dataSheetPath);
		Workbook workBook = new XSSFWorkbook(fileInputStream);
		int calumnNum = 0;
		XSSFCell cell;
		XSSFRow row;
		String TestModuleName;
		for (String module : allModules) {
			TestModuleName = module.substring(module.lastIndexOf("."));
			XSSFSheet workSheet = (XSSFSheet) workBook.getSheet(TestModuleName.substring(1));
			int lastRowNow = workSheet.getLastRowNum();
			for (int rowNum = 1; rowNum <= lastRowNow; rowNum++) {
				row = workSheet.getRow(rowNum);
				cell = row.getCell(calumnNum);
				if (stringtype(cell).equalsIgnoreCase("Yes")) {
					calumnNum = calumnNum + 1;
					row = workSheet.getRow(rowNum);
					cell = row.getCell(calumnNum);
					allClasses.add(module + "." + stringtype(cell));
				}
				calumnNum = 0;
			}
		}
		return allClasses;
	}

	public void generateTestNgXmlSuite() throws Exception {
		Set<String> allModules = getAllModules();
		LinkedHashSet<String> allClass = getAllClasses(allModules);
		List<String> allMethods = getAllMethods(allModules);
		createTestSuite(allModules, allClass, allMethods);
	}

	public List<String> getAllMethods(Set<String> allClass) throws Exception {
		fileInputStream = new FileInputStream(dataSheetPath);
		Workbook workBook = new XSSFWorkbook(fileInputStream);
		List<String> executableMethods = new ArrayList<String>();
		executableClassMethods = new ArrayList<String>();
		int calumnNum = 0;
		XSSFCell cell;
		XSSFRow row;
		String testClass, testScript;
		for (String module : allModules) {
			module = module.substring(module.lastIndexOf("."));
			XSSFSheet workSheet = (XSSFSheet) workBook.getSheet(module.substring(1));
			int lastRowNumber = workSheet.getLastRowNum();
			for (int rowNum = 1; rowNum <= lastRowNumber; rowNum++) {
				row = workSheet.getRow(rowNum);
				cell = row.getCell(calumnNum);
				if (stringtype(cell).equalsIgnoreCase("Yes")) {
					calumnNum = calumnNum + 1;
					cell = row.getCell(calumnNum);
					testClass = stringtype(cell);
					calumnNum = calumnNum + 1;
					cell = row.getCell(calumnNum);
					testScript = stringtype(cell);
					executableClassMethods.add("com.vtg.auto" + module + "." + testClass + "#" + testScript);
					executableMethods.add(testScript);
					calumnNum = calumnNum - 2;
				}
			}
		}
		List<String> listDistinct = executableClassMethods.stream().distinct().collect(Collectors.toList());
		return listDistinct;
	}

	public void createTestSuite(Set<String> modules, LinkedHashSet<String> allClasses, List<String> allMethods)
			throws Exception {
		Element classChildElement;
		Attr classChildAttribute;
		int nTotalTestNeeded = allClasses.size();
		documentBuildFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuildFactory.newDocumentBuilder();
		Document doc = documentBuilder.newDocument();
		Element parentRootElement = doc.createElement("suite");
		doc.appendChild(parentRootElement);
		Attr parentAttribute = doc.createAttribute("name");
		parentRootElement.setAttributeNode(parentAttribute);
		parentAttribute.setValue("Suite");
		initialize.initializeObjects();
		if (configData.get("Grid").equalsIgnoreCase("Yes")) {
			Attr parallelAttribute = doc.createAttribute("parallel");
			parentRootElement.setAttributeNode(parallelAttribute);
			parallelAttribute.setValue("tests");
			if (!(configData.get("ThreadCount").equals(""))) {
				Attr threadCount = doc.createAttribute("thread-count");
				parentRootElement.setAttributeNode(threadCount);
				threadCount.setValue(configData.get("ThreadCount"));
			}
		}
		// Create Listener - V7 Changes
		Element listener = doc.createElement("listeners");
		parentRootElement.appendChild(listener);
		Element listenerclass = doc.createElement("listener");
		listener.appendChild(listenerclass);
		Attr testAttribute_lis = doc.createAttribute("class-name");
		listenerclass.setAttributeNode(testAttribute_lis);
		testAttribute_lis.setValue("com.vtg.auto.reports.ExtentITestListenerClassAdapter");
		int nForTestNumber = 0;
		for (String testClass : allClasses) {
			Element testElement = doc.createElement("test");
			parentRootElement.appendChild(testElement);
			Attr testAttribute = doc.createAttribute("name");
			testElement.setAttributeNode(testAttribute);

			nForTestNumber = nForTestNumber + 1;
			testAttribute.setValue("Test" + nForTestNumber);

			Element classElement = doc.createElement("classes");
			testElement.appendChild(classElement);

			classChildElement = doc.createElement("class");

			classElement.appendChild(classChildElement);
			classChildAttribute = doc.createAttribute("name");
			classChildElement.setAttributeNode(classChildAttribute);
			classChildAttribute.setValue(testClass);

			Element classMethodElement = doc.createElement("methods");
			classChildElement.appendChild(classMethodElement);

			for (String method : allMethods) {
				String testScript = method.substring(method.lastIndexOf("#")).substring(1);
				String testScriptClass = method.substring(0, method.indexOf("#"));
				if (testScriptClass.equalsIgnoreCase(testClass)) {

					Element methodIncludeElement = doc.createElement("include");
					classMethodElement.appendChild(methodIncludeElement);
					Attr methodAttributor = doc.createAttribute("name");
					methodIncludeElement.setAttributeNode(methodAttributor);
					methodAttributor.setValue(testScript);
				}
			}
		}

		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "http://testng.org/testng-1.0.dtd");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(GlobalVariables.workingDir + "/testng.xml"));
		transformer.transform(source, result);

	}

	public static String stringtype(XSSFCell cell) throws Exception {
		Object result;
		//type = cell.getCellType();
		CellType type = cell.getCellType();

		switch (type) {
		case NUMERIC:// numeric value in excel
			result = cell.getNumericCellValue();
			break;
		case STRING: // string value in excel
			result = cell.getStringCellValue();
			break;
		case BOOLEAN: // boolean value in excel
			result = cell.getBooleanCellValue();
			break;
		default:
			throw new Exception("Invalid type of cell");
		}
		return result.toString();
	}

}
