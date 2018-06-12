/**
 * 
 */
package com.sdp.admin;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.text.Utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import com.sdp.utilities.ReadGuru99ExcelFile;

/**
 * @author ruchira.more
 *
 */
public class ManageExport {

	public WebDriver driver;
	public String browserName;

	@Test(priority = 1)
	public void logInExport() throws InterruptedException, AWTException {
		driver = new Utils().login();
		Thread.sleep(15000);
		getWebelement("//a[text()='Manage']").click();
		Thread.sleep(3000);
		List<WebElement> options = driver.findElements(By.xpath("//a[@data-toggle='collapse']"));
		options.get(1).click();
		Thread.sleep(3000);
		getWebelement("//a[contains(text(),'Export')]").click();
		Thread.sleep(3000);
		getWebelement("//button[@ng-click='open1()']").click();
		Thread.sleep(2000);
		getWebelement("//button[@ng-click='toggleMode()']").click();
		Thread.sleep(2000);
		getWebelement("//button[@ng-click='move(-1)']").click();
		Thread.sleep(2000);
		getWebelement("//button[@ng-click='move(-1)']").click();
		Thread.sleep(2000);
		List<WebElement> months = driver.findElements(By.xpath("//button[@ng-click='select(dt.date)']"));
		months.get(0).click();
		Thread.sleep(2000);
		try {
			List<WebElement> dates = driver.findElements(By.xpath("//button[@ng-click='select(dt.date)']"));
			for (int i = 0; i < dates.size(); i++) {
				if (dates.get(i).getText().trim().equals("01")) {
					dates.get(i).click();
				}
			}
			Thread.sleep(2000);
			String startDate = getWebelement("//input[@name='startDate']").getText();

			System.out.println("startDate" + startDate);
		} catch (Exception e) {
			// TODO: handle exception
		}

		try {
			Thread.sleep(3000);
			getWebelement("//button[@ng-click='open2()']").click();
			Thread.sleep(2000);
			getWebelement("//button[@ng-click='toggleMode()']").click();
			Thread.sleep(2000);
			List<WebElement> months2 = driver.findElements(By.xpath("//button[@ng-click='select(dt.date)']"));
			Thread.sleep(2000);
			months2.get(0).click();
			Thread.sleep(2000);
			List<WebElement> dates2 = driver.findElements(By.xpath("//button[@ng-click='select(dt.date)']"));
			for (int i = 0; i < dates2.size(); i++) {
				if (dates2.get(i).getText().trim().equals("01")) {
					dates2.get(i).click();
				}
			}
			Thread.sleep(2000);
			String endDate = getWebelement("//input[@name='endDate']").getText();

			System.out.println("endDate" + endDate);
		} catch (Exception e) {
			// TODO: handle exception
		}
		Thread.sleep(2000);

		Select exportoption = new Select(driver.findElement(By.id("mySelect")));
		exportoption.selectByIndex(1);

		getWebelement("//button[@ng-click='EEController.sendExport(exportForm)']").click();

		Robot robot = new Robot();
		Thread.sleep(3000);
		robot.keyPress(KeyEvent.VK_ENTER);
	}

	@Test(priority = 2)
	public void PurchaseInExport() throws InterruptedException, AWTException {

		getWebelement("//button[@ng-click='open1()']").click();
		Thread.sleep(2000);
		getWebelement("//button[@ng-click='toggleMode()']").click();
		Thread.sleep(2000);
		getWebelement("//button[@ng-click='move(-1)']").click();
		Thread.sleep(2000);
		getWebelement("//button[@ng-click='move(-1)']").click();
		Thread.sleep(2000);
		List<WebElement> months = driver.findElements(By.xpath("//button[@ng-click='select(dt.date)']"));
		months.get(0).click();
		Thread.sleep(2000);
		try {
			List<WebElement> dates = driver.findElements(By.xpath("//button[@ng-click='select(dt.date)']"));
			for (int i = 0; i < dates.size(); i++) {
				if (dates.get(i).getText().trim().equals("01")) {
					dates.get(i).click();
				}
			}
			Thread.sleep(2000);
			String startDate = getWebelement("//input[@name='startDate']").getText();

			System.out.println("startDate" + startDate);
		} catch (Exception e) {
			// TODO: handle exception
		}

		Thread.sleep(2000);

		try {
			Thread.sleep(3000);
			getWebelement("//button[@ng-click='open2()']").click();
			Thread.sleep(2000);
			getWebelement("//button[@ng-click='toggleMode()']").click();
			Thread.sleep(2000);
			List<WebElement> months2 = driver.findElements(By.xpath("//button[@ng-click='select(dt.date)']"));
			Thread.sleep(2000);
			months2.get(0).click();
			Thread.sleep(2000);
			List<WebElement> dates2 = driver.findElements(By.xpath("//button[@ng-click='select(dt.date)']"));
			for (int i = 0; i < dates2.size(); i++) {
				if (dates2.get(i).getText().trim().equals("01")) {
					dates2.get(i).click();
				}
				// break;
			}
			Thread.sleep(2000);
		} catch (Exception e) {
			// TODO: handle exception
		}

		String endDate = getWebelement("//input[@name='endDate']").getText();
		//System.out.println("endDate" + endDate);

		Select exportoption = new Select(driver.findElement(By.id("mySelect")));
		exportoption.selectByIndex(2);

		getWebelement("//button[@ng-click='EEController.sendExport(exportForm)']").click();

		Robot robot = new Robot();

		// A short pause, just to be sure that OK is selected
		Thread.sleep(3000);

		robot.keyPress(KeyEvent.VK_ENTER);

	}

	@Test(priority = 3)
	public void readexcel() throws IOException {
		ReadGuru99ExcelFile objExcelFile = new ReadGuru99ExcelFile();
		// String filePath="C:\\Users\\ruchira.more\\Downloads";
		String filePath = "C:\\Users\\ruchira.more\\AppData\\Local\\Temp";
		objExcelFile.readExcel(filePath, "Logged In Report - (Jan 1, 2016 - Jan 1, 2018).xls", "Last Login Report");
		objExcelFile.readExcel(filePath, "Purchase In Report - (Jan 1, 2016 - Jan 1, 2018).xls", "Purchase Report");

	}

	public WebElement getWebelement(String expression) {

		return driver.findElement(By.xpath(expression));
	}

	@AfterClass
	public void closebrowser() {
		driver.close();
	}

}
