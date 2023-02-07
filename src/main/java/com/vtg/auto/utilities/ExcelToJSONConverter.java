package com.vtg.auto.utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONValue;

import com.vtg.auto.reusablecomponents.GlobalVariables;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ExcelToJSONConverter extends BaseTest {


	public void creteJSONFileFromExcel() {
		// String jsonString ="";
		String filePath = GlobalVariables.configData.get("TestDataPath");
		try {
			FileInputStream fInputStream = new FileInputStream(new File(filePath));
			Workbook excelWookBook = new XSSFWorkbook(fInputStream);
			int totalSheetNumber = excelWookBook.getNumberOfSheets();
			List<String> Modulevalue = null;
			String jsonString = "";
			for (int i = 0; i < totalSheetNumber; i++) {
				Sheet sheet = excelWookBook.getSheetAt(i);
				String sheetName = sheet.getSheetName();
				if (sheetName != null && sheetName.length() > 0) {
					List<List<String>> sheetDataTable = getSheetDataList(sheet);
					if (!sheetName.equals("Modules")) {
						if (Modulevalue.contains(sheetName)) {
							jsonString = jsonString + getJSONStringFromList(sheetDataTable, sheetName);

						}
					} else {

						Modulevalue = getModuleDataList(sheet);

					}

				}
			}
			jsonString = jsonString.replace("}{", ",");
			String jsonFileName = "TestData.json";
			writeStringToFile(jsonString, jsonFileName);
			excelWookBook.close();
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}
	}

	/*
	 * Return sheet data in a two dimensional list. Each element in the outer list
	 * is represent a row, each element in the inner list represent a column. The
	 * first row is the column name row.
	 */
	private static List<List<String>> getSheetDataList(Sheet sheet) {
		List<List<String>> ret = new ArrayList<List<String>>();
		// Get the first and last sheet row number.
		int firstRowNum = sheet.getFirstRowNum();
		int lastRowNum = sheet.getLastRowNum();
		if (lastRowNum > 0) {
			// Loop in sheet rows.
			for (int i = firstRowNum; i < lastRowNum + 1; i++) {
				DataFormatter fmt = new DataFormatter();
				// Get current row object.
				Row row = sheet.getRow(i);
				// Get first and last cell number.
				int firstCellNum = row.getFirstCellNum();
				int lastCellNum = row.getLastCellNum();
				// Create a String list to save column data in a row.
				List<String> rowDataList = new ArrayList<String>();
				// Loop in the row cells.
				for (int j = firstCellNum; j < lastCellNum; j++) {
					// Get current cell.
					Cell cell = row.getCell(j);
					// Get cell type.
					CellType cellType = cell.getCellType();
					if (cellType == CellType.NUMERIC) {
						rowDataList.add(fmt.formatCellValue(cell));
					} else if (cellType == CellType.STRING) {
						String cellValue = cell.getStringCellValue();
						rowDataList.add(cellValue);
					} else if (cellType == CellType.BOOLEAN) {
						boolean numberValue = cell.getBooleanCellValue();
						String stringCellValue = String.valueOf(numberValue);
						rowDataList.add(stringCellValue);

					} else if (cellType == CellType.BLANK) {
						rowDataList.add("");
					}
				}

				// Add current row data list in the return list.
				ret.add(rowDataList);
			}
		}
		return ret;
	}

	private static List<String> getModuleDataList(Sheet sheet) {
		List<String> rowDataList = new ArrayList<String>();
		// Get the first and last sheet row number.
		int firstRowNum = sheet.getFirstRowNum();
		int lastRowNum = sheet.getLastRowNum();
		if (lastRowNum > 0) {
			// Loop in sheet rows.
			for (int i = firstRowNum; i < lastRowNum + 1; i++) {
				DataFormatter fmt = new DataFormatter();
				// Get current row object.
				Row row = sheet.getRow(i);
				// Get first and last cell number.
				int firstCellNum = row.getFirstCellNum();
				int lastCellNum = row.getLastCellNum();
				// Create a String list to save column data in a row.
				// Loop in the row cells.
				// for (int j = firstCellNum; j < lastCellNum; j++) {
				// Get current cell.
				Cell cell = row.getCell(1);

				// Get cell type.
				CellType cellType = cell.getCellType();
				String runManager = "";
				if (cellType == CellType.NUMERIC) {
					rowDataList.add(fmt.formatCellValue(cell));
				} else if (cellType == CellType.STRING) {
					runManager = cell.getStringCellValue();
					// rowDataList.add(cellValue);
				} else if (cellType == CellType.BOOLEAN) {
					boolean numberValue = cell.getBooleanCellValue();
					runManager = String.valueOf(numberValue);
					// rowDataList.add(stringCellValue);

				} else if (cellType == CellType.BLANK) {
					runManager = "";
				}
				if (runManager.equals("Yes")) {

					cell = row.getCell(0);

					// Get cell type.
					cellType = cell.getCellType();
					String moduleName = "";
					if (cellType == CellType.NUMERIC) {
						rowDataList.add(fmt.formatCellValue(cell));
					} else if (cellType == CellType.STRING) {
						moduleName = cell.getStringCellValue();
						rowDataList.add(moduleName);
					} else if (cellType == CellType.BOOLEAN) {
						boolean numberValue = cell.getBooleanCellValue();
						moduleName = String.valueOf(numberValue);
						rowDataList.add(moduleName);

					} else if (cellType == CellType.BLANK) {
						moduleName = "";
						rowDataList.add(moduleName);
					}

				}
				// }

				// Add current row data list in the return list.
			}
		}
		return rowDataList;
	}

	/* Return a JSON string from the string list. */
	private static String getJSONStringFromList(List<List<String>> dataTable, String local_sheetName) {
		String ret = "";
		Map<String, String> obj = new HashMap<String, String>();
		JSONObject ModuleJsonObject = new JSONObject();
		if (dataTable != null) {
			int rowCount = dataTable.size();
			if (rowCount > 1) {
				List<String> headerRow = dataTable.get(0);
				int columnCount = headerRow.size();
				JSONArray TestCasearray = new JSONArray();
				String calssName = "", testCasename = "";
				JSONObject TestCaseJsonObject = new JSONObject();
				JSONObject ClassNameObject = new JSONObject();
				for (int i = 1; i < rowCount; i++) {
					ret = "";
					List<String> dataRow = dataTable.get(i);
					for (int j = 0; j < columnCount; j++) {
						if ((headerRow.get(j).equals("RunManager") && (!dataRow.get(j).equals("Yes")))) {
							break;
						}
						String columnName = headerRow.get(j);
						String columnValue = dataRow.get(j);
						calssName = dataRow.get(1);
						testCasename = dataRow.get(2);
						if ((j != 0) && (j != 1) && (j != 2) && !(columnValue.equals(""))) {
							obj.put(columnName, columnValue);

						}
					}
					if ( !calssName.equals("")) {
						String jsonText = JSONValue.toJSONString(obj);
						TestCaseJsonObject.put(testCasename, jsonText);
						ClassNameObject.put(calssName, TestCaseJsonObject);
						TestCasearray.add(ClassNameObject.toString());
						obj.clear();
						TestCaseJsonObject.clear();
						ClassNameObject.clear();
						calssName ="";
					}

				}
				ModuleJsonObject.put(local_sheetName, TestCasearray);
			}
		}
		ret = ModuleJsonObject.toString();
		return ret;
	}

	/* Write string data to a file. */
	private static void writeStringToFile(String data, String fileName) {
		try {
			File file = new File("src/test/resources/testdata/TestData.json");
			FileWriter fw = new FileWriter(file);
			BufferedWriter buffWriter = new BufferedWriter(fw);
			if (file.exists()) {
				buffWriter.write(data);

			} else {
				buffWriter.append(data);
			}
			buffWriter.flush();
			buffWriter.close();
			//System.out.println("JSON File has been created.");

		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}

	}

	/* Return a JSON string from the string list. */
	

}