package com.vtg.auto.business_utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class excelUtilities {
	private static XSSFWorkbook workbook;
	private static XSSFSheet sheet;
	private static XSSFCell cell;
	public String  getData(String rowText, String colText, String Sheetname) throws IOException {
		return getExcelvalue(getRownumber(rowText, Sheetname), getColumnumber(colText, Sheetname), Sheetname);
	}
	public static Integer getRownumber(String testName, String Sheetname) throws IOException {
		Integer rownumber = 0;
		FileInputStream objfile = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\Testdata\\TestData.xlsx");
		XSSFWorkbook objWork = new XSSFWorkbook(objfile);
		XSSFSheet osheet = null;
		XSSFRow oRow = null;
		XSSFCell oCell = null;
		for (int i = 0; i < objWork.getNumberOfSheets(); i++) {
			if (objWork.getSheetName(i).equalsIgnoreCase(Sheetname)) {
				osheet = objWork.getSheetAt(i);
				break;
			}
		}
		for (int row = 0; row <= osheet.getLastRowNum(); row++) {
			oRow = osheet.getRow(row);
			for (int col = 0; col < oRow.getLastCellNum(); col++) {
				oCell = oRow.getCell(col);
				String columnname = null;
				if (oCell.getCellType().equals(CellType.STRING)) {
					columnname = oCell.getStringCellValue();

				} else if (oCell.getCellType().equals(CellType.NUMERIC)) {
					columnname = String.valueOf(oCell.getNumericCellValue());

				}
				if (columnname.equalsIgnoreCase(testName)) {
					rownumber = row;
					break;
				}
			}
		}
		objWork.close();
		return rownumber;
	}

	public static Integer getColumnumber(String testDataName, String Sheetname) throws IOException {
		Integer colnumber = 0;
		FileInputStream objfile = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\Testdata\\TestData.xlsx");
		XSSFWorkbook objWork = new XSSFWorkbook(objfile);
		XSSFSheet osheet = null;
		XSSFRow oRow = null;
		XSSFCell oCell = null;
		for (int i = 0; i < objWork.getNumberOfSheets(); i++) {
			if (objWork.getSheetName(i).equalsIgnoreCase(Sheetname)) {
				osheet = objWork.getSheetAt(i);
				break;
			}
		}
		for (int row = 0; row <= osheet.getLastRowNum(); row++) {
			oRow = osheet.getRow(row);
			for (int col = 0; col < oRow.getLastCellNum(); col++) {
				oCell = oRow.getCell(col);
				String columnname = null;
				if (oCell.getCellType().equals(CellType.STRING)) {
					columnname = oCell.getStringCellValue();

				} else if (oCell.getCellType().equals(CellType.NUMERIC)) {
					columnname = String.valueOf(oCell.getNumericCellValue());

				}
				if (columnname.equalsIgnoreCase(testDataName)) {
					colnumber = col;
					break;
				}
			}
		}
		objWork.close();
		return colnumber;
	}

	public static String getExcelvalue(int rowval, int colval, String Sheetname) throws IOException {
		String cellalue = "";
		FileInputStream objfile = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\Testdata\\TestData.xlsx");
		XSSFWorkbook objWork = new XSSFWorkbook(objfile);
		XSSFSheet osheet = null;
		XSSFRow oRow = null;
		XSSFCell oCell = null;
		for (int i = 0; i < objWork.getNumberOfSheets(); i++) {
			if (objWork.getSheetName(i).equalsIgnoreCase(Sheetname)) {
				osheet = objWork.getSheetAt(i);
				break;
			}
		}
		oRow = osheet.getRow(rowval);
		oCell = oRow.getCell(colval);
		if (oCell.getCellType().equals(CellType.STRING)) {
			cellalue = oCell.getStringCellValue();

		} else if (oCell.getCellType().equals(CellType.NUMERIC)) {
			cellalue = String.valueOf(oCell.getNumericCellValue());

		}

		objWork.close();
		return cellalue;
	}
}