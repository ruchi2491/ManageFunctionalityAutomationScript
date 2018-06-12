package com.sdp.utilities;

import java.awt.List;
import java.io.File;

import java.io.FileInputStream;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.ss.usermodel.Workbook;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SamplePurchase {

	public void readExcel(String filePath, String fileName, String samplefilename, String sheetName,
			String sampleSheetName) throws IOException {

		File file = new File(filePath + "\\" + fileName);
		FileInputStream inputStream = new FileInputStream(file);

		System.out.println(filePath + "\\" + samplefilename);

		File sfile = new File(filePath + "\\" + samplefilename);
		FileInputStream sinputStream = new FileInputStream(sfile);

		Workbook purchaseInWorkbook = null;
		Workbook samplePurchaseInWorkbook = null;
		String fileExtensionName = fileName.substring(fileName.indexOf("."));
		if (fileExtensionName.equals(".xlsx")) {
			purchaseInWorkbook = new XSSFWorkbook(inputStream);
			samplePurchaseInWorkbook = new XSSFWorkbook(sinputStream);
		} else if (fileExtensionName.equals(".xls")) {
			purchaseInWorkbook = new HSSFWorkbook(inputStream);
			samplePurchaseInWorkbook = new HSSFWorkbook(sinputStream);
		}
		Sheet purchaseInSheet = purchaseInWorkbook.getSheet(sheetName);
		Sheet samplePurchaseSheet = samplePurchaseInWorkbook.getSheet(sampleSheetName);
		// System.out.println(purchaseInSheet.getLastRowNum());
		// System.out.println(purchaseInSheet.getFirstRowNum());
		int rowCount = purchaseInSheet.getLastRowNum() - purchaseInSheet.getFirstRowNum();
		System.out.println("rowcount of purchaseInsheet" + rowCount);
		System.out.println("rowcount of samplePurchaseInWorkbook"
				+ (samplePurchaseSheet.getLastRowNum() - samplePurchaseSheet.getFirstRowNum()));
		System.out.println(
				"--------------------------------------------------------------------------------------------------------");
		System.out.println("getLastCellNum of purchaseInSheet " + purchaseInSheet.getRow(1).getLastCellNum());
		System.out.println("getLastCellNum of samplePurchaseSheet" + samplePurchaseSheet.getRow(1).getLastCellNum());

		System.out.println("Header is:");
		String match;
		ArrayList<String> stringcolumnnames = new ArrayList<String>();
		stringcolumnnames.add("Ship To Party End User Org ID");
		//stringcolumnnames.add("Bill To Customer Name And Org ID");
		stringcolumnnames.add("End User Name");
		stringcolumnnames.add("End User Company Address");
		stringcolumnnames.add("User Name");

		for (int l = 0; l < samplePurchaseSheet.getRow(0).getLastCellNum(); l++) {
			match = "";
			for (int k = 0; k < purchaseInSheet.getRow(0).getLastCellNum(); k++) {
				// System.out.println(samplePurchaseSheet.getRow(0).getCell(k).getStringCellValue());
				String purchasesheetHeader = purchaseInSheet.getRow(0).getCell(k).getStringCellValue();
				String samplepurchasesheetHeader = samplePurchaseSheet.getRow(0).getCell(l).getStringCellValue();

				if (purchasesheetHeader.equals(samplepurchasesheetHeader)) {
					match = "pass";

					for (int j = 0; j < stringcolumnnames.size(); j++) {
						if (purchasesheetHeader.contains(stringcolumnnames.get(j))) {
							System.out.println("column name that matches"+stringcolumnnames.get(j));
							for (int i = 1; i < rowCount; i++) {
								if (isAlphaNumeric(purchaseInSheet.getRow(i).getCell(k).getStringCellValue())) {
									System.out.println("isAlphaNumeric");
									System.out.println(purchaseInSheet.getRow(i).getCell(k).getStringCellValue());
								} else {
									System.out.println("not isAlphaNumeric");
									System.out.println(purchaseInSheet.getRow(i).getCell(k).getStringCellValue());
									break;
								}
								System.out.println("******************************************************");
							}
						}
					}
					break;
				}
			}
			if (match.equals("")) {
				match = "Fail";
			}
			System.out.println("column " + (l + 1) + " " + match);
			System.out.println("--------------------------------------------------------------------------------------");
		}

		/*
		 * reading data from file System.out.println("data at each line is:");
		 * for (int i = 1; i < rowCount + 1; i++) { Row row =
		 * purchaseInSheet.getRow(i); for (int j = 0; j < row.getLastCellNum();
		 * j++) { System.out.print(row.getCell(j).getStringCellValue() + "|| ");
		 * } System.out.println(); }
		 */
	}

	public static void main(String... strings) throws IOException {
		SamplePurchase objExcelFile = new SamplePurchase();
		String filePath = "C:\\Users\\ruchira.more\\Downloads";
		// String filePath = "C:\\Users\\ruchira.more\\AppData\\Local\\Temp";
		objExcelFile.readExcel(filePath, "Purchase In Report - (Jan 1, 2017 - Mar 29, 2018).xls",
				"Purchase In Report Sample.xls", "Purchase Report", "sample");
		// objExcelFile.readExcel(filePath, "Logged In Report - (Jan 1, 2016 -
		// Jan 1, 2018).xls", "Last Login Report");

	}

	public boolean isAlphaNumeric(String s) {
		String pattern = "^\\s*[\\da-zA-Z][\\da-zA-Z\\s\\W]*$";
		return s.matches(pattern);
		//\$[A-Za-z]+(_[A-Za-z]+)*\$
		//^\\s*[\\da-zA-Z][\\da-zA-Z\\s\\W]*$
		///^[ A-Za-z0-9_@./#&+-]*$/
	}

}