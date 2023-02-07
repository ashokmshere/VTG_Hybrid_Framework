
//****************************************************************
package com.vtg.auto.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Iterator;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class excelSheetReaderUtil {
	public String path;
	public FileInputStream fileInputStream = null;
	public FileOutputStream fileOutputStream = null;
	public XSSFWorkbook workBook = null;
	public XSSFSheet workSheet = null;
	private XSSFRow rowValue = null;
	private XSSFCell cellValue = null;
	@SuppressWarnings("rawtypes")
	public Iterator rowIterator;
	public HSSFRow currentRow;

	public excelSheetReaderUtil(String path) {
		this.path = path;
		try {
			fileInputStream = new FileInputStream(path);
			workBook = new XSSFWorkbook(fileInputStream);
			workSheet = workBook.getSheetAt(0); 
			fileInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getRowCount(String sheetName) {
		int index = workBook.getSheetIndex(sheetName);
		if (index == -1)
			return 0;
		else {
			workSheet = workBook.getSheetAt(index);
			int number = workSheet.getLastRowNum() + 1;
			return number;
		}
	}

	public String getCellData(String sheetName, String colName, int rowNum) {
		try {
			if (rowNum <= 0)
				return "";

			int index = workBook.getSheetIndex(sheetName);
			int col_Num = -1;
			if (index == -1)
				return "";

			workSheet = workBook.getSheetAt(index);
			rowValue = workSheet.getRow(0);
			for (int i = 0; i < rowValue.getLastCellNum(); i++) {

				if (rowValue.getCell(i).getStringCellValue().trim().equals(colName.trim()))
					col_Num = i;
			}
			if (col_Num == -1)
				return "";

			workSheet = workBook.getSheetAt(index);
			rowValue = workSheet.getRow(rowNum - 1);
			if (rowValue == null)
				return "";
			//cellValue = rowValue.getCell(col_Num);
			Cell cellValue = rowValue.getCell(col_Num);
			if (cellValue == null)
				return "";

			if (cellValue.getCellType() == CellType.STRING)
				return cellValue.getStringCellValue();
			else if (cellValue.getCellType() == CellType.NUMERIC
					|| cellValue.getCellType() == CellType.FORMULA) {

				String cellText = String.valueOf(cellValue.getNumericCellValue());
				if (HSSFDateUtil.isCellDateFormatted(cellValue)) {
					double d = cellValue.getNumericCellValue();

					Calendar cal = Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + 1 + "/" + cellText;

				}

				return cellText;
			} else if (cellValue.getCellType() == CellType.BLANK)
				return "";
			else
				return String.valueOf(cellValue.getBooleanCellValue());

		} catch (Exception e) {

			e.printStackTrace();
			return "row " + rowNum + " or column " + colName + " does not exist in xls";
		}
	}

	public String getCellData(int rowNum, int colNum) {

		if (rowNum <= 0)
			return "";

		if (workSheet.equals(null))
			return "";

		rowValue = workSheet.getRow(rowNum);
		rowValue = workSheet.getRow(rowNum - 1);
		if (rowValue == null)
			return "";
		cellValue = rowValue.getCell(colNum);

		if (cellValue == null)
			return "";

		if (cellValue.getCellType() == CellType.STRING)
			return cellValue.getStringCellValue();
		else if (cellValue.getCellType() == CellType.NUMERIC
				|| cellValue.getCellType() == CellType.FORMULA) {

			String cellText = String.valueOf(cellValue.getNumericCellValue());
			if (HSSFDateUtil.isCellDateFormatted(cellValue)) {

				double d = cellValue.getNumericCellValue();

				Calendar cal = Calendar.getInstance();
				cal.setTime(HSSFDateUtil.getJavaDate(d));
				cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
				cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + 1 + "/" + cellText;

			}

			return cellText;
		} else if (cellValue.getCellType() == CellType.BLANK)
			return "";
		else
			return String.valueOf(cellValue.getBooleanCellValue());

	}

	public void getSheetAtIndex(int sheetIndex) {
		try {
			workSheet = workBook.getSheetAt(sheetIndex);
			rowIterator = workSheet.rowIterator();
			rowIterator.next();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public String getCellData(String sheetName, int colNum, int rowNum) {
		try {
			if (rowNum <= 0)
				return "";

			int index = workBook.getSheetIndex(sheetName);

			if (index == -1)
				return "";

			workSheet = workBook.getSheetAt(index);
			rowValue = workSheet.getRow(rowNum - 1);
			if (rowValue == null)
				return "";
			//cellValue = rowValue.getCell(colNum);
			   Cell cellValue = rowValue.getCell(colNum);
               // Get cell type.
               CellType cellType = cellValue.getCellType();
			if (cellValue == null)
				return "";

			if (cellValue.getCellType() == CellType.STRING)
				return cellValue.getStringCellValue();
			else if (cellValue.getCellType() == CellType.NUMERIC
					|| cellValue.getCellType() == CellType.FORMULA) {

				String cellText = String.valueOf(cellValue.getNumericCellValue());
				if (HSSFDateUtil.isCellDateFormatted(cellValue)) {

					double d = cellValue.getNumericCellValue();

					Calendar cal = Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.MONTH) + 1 + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cellText;

				}

				return cellText;
			} else if (cellValue.getCellType() == CellType.BLANK)
				return "";
			else
				return String.valueOf(cellValue.getBooleanCellValue());
		} catch (Exception e) {

			e.printStackTrace();
			return "row " + rowNum + " or column " + colNum + " does not exist  in xls";
		}
	}

	public boolean setCellData(String sheetName, String colName, int rowNum, String data) {
		try {
			fileInputStream = new FileInputStream(path);
			workBook = new XSSFWorkbook(fileInputStream);

			if (rowNum <= 0)
				return false;

			int index = workBook.getSheetIndex(sheetName);
			int colNum = -1;
			if (index == -1)
				return false;

			workSheet = workBook.getSheetAt(index);

			rowValue = workSheet.getRow(0);
			for (int i = 0; i < rowValue.getLastCellNum(); i++) {
				if (rowValue.getCell(i).getStringCellValue().trim().equals(colName))
					colNum = i;
			}
			if (colNum == -1)
				return false;

			workSheet.autoSizeColumn(colNum);
			rowValue = workSheet.getRow(rowNum - 1);
			if (rowValue == null)
				rowValue = workSheet.createRow(rowNum - 1);

			cellValue = rowValue.getCell(colNum);
			if (cellValue == null)
				cellValue = rowValue.createCell(colNum);
			cellValue.setCellValue(data);

			fileOutputStream = new FileOutputStream(path);

			workBook.write(fileOutputStream);

			fileOutputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean setCellData(String sheetName, String colName, int rowNum, String data, String url) {

		try {
			fileInputStream = new FileInputStream(path);
			workBook = new XSSFWorkbook(fileInputStream);

			if (rowNum <= 0)
				return false;

			int index = workBook.getSheetIndex(sheetName);
			int colNum = -1;
			if (index == -1)
				return false;

			workSheet = workBook.getSheetAt(index);

			rowValue = workSheet.getRow(0);
			for (int i = 0; i < rowValue.getLastCellNum(); i++) {

				if (rowValue.getCell(i).getStringCellValue().trim().equalsIgnoreCase(colName))
					colNum = i;
			}

			if (colNum == -1)
				return false;
			workSheet.autoSizeColumn(colNum);
			rowValue = workSheet.getRow(rowNum - 1);
			if (rowValue == null)
				rowValue = workSheet.createRow(rowNum - 1);

			cellValue = rowValue.getCell(colNum);
			if (cellValue == null)
				cellValue = rowValue.createCell(colNum);

			cellValue.setCellValue(data);
			XSSFCreationHelper createHelper = workBook.getCreationHelper();

			CellStyle hlink_style = workBook.createCellStyle();
			XSSFFont hlink_font = workBook.createFont();
			hlink_font.setUnderline(XSSFFont.U_SINGLE);
			hlink_font.setColor(IndexedColors.BLUE.getIndex());
			hlink_style.setFont(hlink_font);

			XSSFHyperlink link = createHelper.createHyperlink(HyperlinkType.FILE);
			link.setAddress(url);
			cellValue.setHyperlink(link);
			cellValue.setCellStyle(hlink_style);

			fileOutputStream = new FileOutputStream(path);
			workBook.write(fileOutputStream);

			fileOutputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean addSheet(String sheetname) {
		FileOutputStream fileOut;
		try {
			workBook.createSheet(sheetname);
			fileOut = new FileOutputStream(path);
			workBook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean removeSheet(String sheetName) {
		int index = workBook.getSheetIndex(sheetName);
		if (index == -1)
			return false;
		FileOutputStream fileOut;
		try {
			workBook.removeSheetAt(index);
			fileOut = new FileOutputStream(path);
			workBook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/* "addColumn" method needs fix due to update in Apache-POI Version upgrade */
	
	public boolean addColumn(String sheetName, String colName) {
		try {
			fileInputStream = new FileInputStream(path);
			workBook = new XSSFWorkbook(fileInputStream);
			int index = workBook.getSheetIndex(sheetName);
			if (index == -1)
				return false;
			XSSFCellStyle style = workBook.createCellStyle();
			//style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
			//style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			
			
			style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			workSheet = workBook.getSheetAt(index);
			rowValue = workSheet.getRow(0);
			if (rowValue == null)
				rowValue = workSheet.createRow(0);
			if (rowValue.getLastCellNum() == -1)
				cellValue = rowValue.createCell(0);
			else
				cellValue = rowValue.createCell(rowValue.getLastCellNum());
			cellValue.setCellValue(colName);
			cellValue.setCellStyle(style);
			fileOutputStream = new FileOutputStream(path);
			workBook.write(fileOutputStream);
			fileOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
//
//	public boolean removeColumn(String sheetName, int colNum) {
//		try {
//			if (!isSheetExist(sheetName))
//				return false;
//			fileInputStream = new FileInputStream(path);
//			workBook = new XSSFWorkbook(fileInputStream);
//			workSheet = workBook.getSheet(sheetName);
//			XSSFCellStyle style = workBook.createCellStyle();
//			style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
//			style.setFillPattern(HSSFCellStyle.NO_FILL);
//			for (int i = 0; i < getRowCount(sheetName); i++) {
//				rowValue = workSheet.getRow(i);
//				if (rowValue != null) {
//					cellValue = rowValue.getCell(colNum);
//					if (cellValue != null) {
//						cellValue.setCellStyle(style);
//						rowValue.removeCell(cellValue);
//					}
//				}
//			}
//			fileOutputStream = new FileOutputStream(path);
//			workBook.write(fileOutputStream);
//			fileOutputStream.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//		return true;
//	}

	public boolean isSheetExist(String sheetName) {
		int index = workBook.getSheetIndex(sheetName);
		if (index == -1) {
			index = workBook.getSheetIndex(sheetName.toUpperCase());
			if (index == -1)
				return false;
			else
				return true;
		} else
			return true;
	}

	public int getColumnCount(String sheetName) {
		if (!isSheetExist(sheetName))
			return -1;
		workSheet = workBook.getSheet(sheetName);
		rowValue = workSheet.getRow(0);
		if (rowValue == null)
			return -1;
		return rowValue.getLastCellNum();
	}

	public boolean addHyperLink(String sheetName, String screenShotColName, String testCaseName, int index, String url,
			String message) {
		url = url.replace('\\', '/');
		if (!isSheetExist(sheetName))
			return false;

		workSheet = workBook.getSheet(sheetName);

		for (int i = 2; i <= getRowCount(sheetName); i++) {
			if (getCellData(sheetName, 0, i).equalsIgnoreCase(testCaseName)) {
				setCellData(sheetName, screenShotColName, i + index, message, url);
				break;
			}
		}

		return true;
	}

	public int getCellRowNum(String sheetName, String colName, String cellValue) {
		for (int i = 2; i <= getRowCount(sheetName); i++) {
			if (getCellData(sheetName, colName, i).equalsIgnoreCase(cellValue)) {
				return i;
			}
		}
		return -1;
	}

	public void getSheet(String sheetName) {
		try {
			workSheet = workBook.getSheet(sheetName);
			rowIterator = workSheet.rowIterator();
			rowIterator.next();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void resetPointer() {
		try {
			rowIterator = workSheet.rowIterator();
			rowIterator.next();
		} catch (Exception ex) {
		}
	}

	public boolean moveToNextRow() {
		try {
			if (rowIterator.hasNext()) {
				currentRow = (HSSFRow) rowIterator.next();
				return true;
			} else
				return false;
		} catch (Exception ex) {
			return false;
		}
	}

}
