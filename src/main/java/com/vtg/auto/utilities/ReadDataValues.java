package com.vtg.auto.utilities;

import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.vtg.auto.reusablecomponents.GlobalVariables;


public class ReadDataValues extends GlobalVariables {

	public static FileInputStream fileInputStream;
	public static XSSFWorkbook workBook;
	public static int lastRowNum, colNum, rowsIterator = 0, rowIterator = 0, columnIterator = 0, testscript_rownum;
	public static String[][] data;
	public static XSSFSheet workSheet;
	public static XSSFRow row;
	public static XSSFCell cell;
	public static String dataTableValue, dataTableKey, rowValue;
	public static Hashtable<String, String> dataTable = new Hashtable<String, String>();
	
	public static String File_Path;

	public Hashtable<String, String> getTestDataAsTable(Package moduleName, String TestScript, int ColumnNumber)
			throws Exception {
		String TestModuleName = moduleName.toString();
		TestModuleName = TestModuleName.substring(TestModuleName.lastIndexOf("."));
		fileInputStream = new FileInputStream(configData.get("TestDataPath"));

		workBook = new XSSFWorkbook(fileInputStream);
		workSheet = workBook.getSheet(TestModuleName.substring(1));
		lastRowNum = workSheet.getLastRowNum() + 1;
		colNum = workSheet.getRow(0).getLastCellNum();
		int rowNumber = getRowNumber(TestScript, ColumnNumber);
		getNumberOfInstances(TestScript);
		dataTable.clear();
		if (rowNumber > 0) {
			for (columnIterator = 0; columnIterator <= colNum - 1; columnIterator++) {
				row = workSheet.getRow(0);
				cell = row.getCell(columnIterator);
				dataTableKey = stringtype(cell);
				row = workSheet.getRow(rowNumber);
				cell = row.getCell(columnIterator);
				dataTableValue = stringtype(cell);

				if (dataTableKey != null && dataTableValue != null)
					dataTable.put(dataTableKey, dataTableValue);

			}
		}
	fileInputStream.close();
		return dataTable;
	}

	public Hashtable<String, String> getTestDataAsTable(String moduleName, String TestScript, String file, int ColNum)
			throws Exception {
		String TestModuleName = moduleName.toString();
		TestModuleName = TestModuleName.substring(TestModuleName.lastIndexOf("."));
		fileInputStream = new FileInputStream(file);
		workBook = new XSSFWorkbook(fileInputStream);
		workSheet = workBook.getSheet(TestModuleName.substring(1));
		lastRowNum = workSheet.getLastRowNum() + 1;
		colNum = workSheet.getRow(0).getLastCellNum();
		int rowNumber = getRowNumber(TestScript, ColNum);
		getNumberOfInstances(TestScript);
		dataTable.clear();
		if (rowNumber > 0) {
			for (columnIterator = 0; columnIterator <= colNum - 1; columnIterator++) {
				row = workSheet.getRow(0);
				cell = row.getCell(columnIterator);
				dataTableKey = stringtype(cell);
				row = workSheet.getRow(rowNumber);
				cell = row.getCell(columnIterator);
				dataTableValue = stringtype(cell);

				if (dataTableKey != null && dataTableValue != null)
					dataTable.put(dataTableKey, dataTableValue);

			}
		}
		fileInputStream.close();
		return dataTable;
	}

	public static String getValueFromSheet(Package sheetname, String TestScript, String key) throws Exception {
		Hashtable<String, String> hashTable = new Hashtable<String, String>();
		FileInputStream getValuefis;
		XSSFWorkbook getwb;
		XSSFSheet getWs;
		int getRowNum, getColNum, gettestscript_rownum = 0;
		XSSFRow getrow;
		XSSFCell getcell;
		String getHtkey, gethtvalue, getCellValue;
		getValuefis = new FileInputStream(File_Path);
		getwb = new XSSFWorkbook(getValuefis);
		getWs = getwb.getSheet(sheetname.toString());
		getRowNum = getWs.getLastRowNum() + 1;
		getColNum = getWs.getRow(0).getLastCellNum();
		breakLoop: for (int q = 1; q <= getRowNum; q++) {
			getrow = getWs.getRow(q);
			for (int r = 0; r <= 0; r++) {
				getcell = getrow.getCell(r);
				getCellValue = stringtype(getcell);
				if (getCellValue.equalsIgnoreCase(TestScript)) {
					gettestscript_rownum = q;
					break breakLoop;
				}
			}
		}
		for (int s = 0; s <= getColNum - 1; s++) {
			getrow = getWs.getRow(0);
			getcell = getrow.getCell(s);
			getHtkey = stringtype(getcell);
			getrow = getWs.getRow(gettestscript_rownum);
			getcell = getrow.getCell(s);
			gethtvalue = stringtype(getcell);
			hashTable.put(getHtkey, gethtvalue);
		}
		return hashTable.get(key);
	}

	public int getRowNumber(String Testscript, int columnNumber) throws Exception {
		int getRowNumber = 0;
		XSSFRow row;
		XSSFCell cell;
		String rowValue;
		loop: for (int rowNumber = 1; rowNumber <= lastRowNum; rowNumber++) {
			row = workSheet.getRow(rowNumber);
			cell = row.getCell(columnNumber);
			rowValue = stringtype(cell);
			if (rowValue.equalsIgnoreCase(Testscript)) {
				getRowNumber = rowNumber;
				break loop;
			}
		}
		return getRowNumber;
	}

	public List<String> getNumberOfInstances(String Testscript) throws Exception {
		List<String> allTestMethods = new ArrayList<String>();
		allTestMethods.add(Testscript);
		return allTestMethods;
	}

	public List<Integer> getRowNumbers(String Testscript) throws Exception {
		List<Integer> rowNumbers = new ArrayList<Integer>();
		int columnNumber = 2;
		XSSFRow row;
		XSSFCell cell;
		String rowValue, screitRunMangerValue;
		for (int rowNumber = 1; rowNumber <= lastRowNum - 1; rowNumber++) {
			row = workSheet.getRow(rowNumber);
			cell = row.getCell(columnNumber);
			rowValue = stringtype(cell);
			if (rowValue.equalsIgnoreCase(Testscript)) {
				columnNumber = columnNumber - 2;
				row = workSheet.getRow(rowNumber);
				cell = row.getCell(columnNumber);
				screitRunMangerValue = stringtype(cell);
				if (screitRunMangerValue.equalsIgnoreCase("Yes")) {
					rowNumbers.add(rowNumber);
					columnNumber = columnNumber + 2;
				}
			}
		}
		return rowNumbers;
	}

	/**
	 * Gets the cell value.
	 *
	 * @param cell the cell
	 * @return the cell value
	 */
	private static String stringtype(XSSFCell cell) {
		if (cell != null) {
			switch (cell.getCellType()) {
			case BLANK:
				return null;
			case BOOLEAN:
				return String.valueOf(cell.getBooleanCellValue());
			case NUMERIC:
				return String.valueOf((int) cell.getNumericCellValue());
			case STRING:
				return cell.getRichStringCellValue().toString();
			}
		}
		return null;
	}
}