package com.sdp.utilities;

import java.io.File;

import java.io.FileInputStream;

import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.ss.usermodel.Workbook;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadGuru99ExcelFile {

	public void readExcel(String filePath, String fileName, String sheetName) throws IOException {

		File file = new File(filePath + "\\" + fileName);
		FileInputStream inputStream = new FileInputStream(file);
		Workbook guru99Workbook = null;
		String fileExtensionName = fileName.substring(fileName.indexOf("."));
		if (fileExtensionName.equals(".xlsx")) {
			guru99Workbook = new XSSFWorkbook(inputStream);
		} else if (fileExtensionName.equals(".xls")) {
			guru99Workbook = new HSSFWorkbook(inputStream);
		}
		Sheet guru99Sheet = guru99Workbook.getSheet(sheetName);
		// System.out.println(guru99Sheet.getLastRowNum());
		// System.out.println(guru99Sheet.getFirstRowNum());
		int rowCount = guru99Sheet.getLastRowNum() - guru99Sheet.getFirstRowNum();
		System.out.println("rowcount" + rowCount);
		System.out.println("getLastCellNum" + guru99Sheet.getRow(1).getLastCellNum());

		
		/* reading data from file */
/*		System.out.println("data at each line is:");
		for (int i = 1; i < rowCount + 1; i++) {
			Row row = guru99Sheet.getRow(i);
			for (int j = 0; j < row.getLastCellNum(); j++) {
				System.out.print(row.getCell(j).getStringCellValue() + "|| ");
			}
			System.out.println();
		}
*/	}

	public static void main(String... strings) throws IOException {
		ReadGuru99ExcelFile objExcelFile = new ReadGuru99ExcelFile();
		 String filePath="C:\\Users\\ruchira.more\\Downloads";
		//String filePath = "C:\\Users\\ruchira.more\\AppData\\Local\\Temp";
		objExcelFile.readExcel(filePath, "Purchase In Report - (Mar 1, 2018 - Apr 2, 2018).xls", "Purchase Report");
		//objExcelFile.readExcel(filePath, "Logged In Report - (Jan 1, 2016 - Jan 1, 2018).xls", "Last Login Report");

	}

}