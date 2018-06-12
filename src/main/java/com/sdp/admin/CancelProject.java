/**
 * 
 */
package com.sdp.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import com.sun.java.swing.plaf.windows.resources.windows;

/**
 * @author ruchira.more
 *
 */
public class CancelProject {

	public WebDriver driver;
	public String columndata[];

	public void login(String username) throws InterruptedException {
		System.setProperty("webdriver.gecko.driver", "C:\\geckodriver.exe");
		driver = new FirefoxDriver();
		String baseUrl = "http://SDP-qa.citrix.com";

		// launch Fire fox and direct it to the Base URL
		driver.get(baseUrl);

		Thread.sleep(10000);
		// driver.manage().timeouts().implicitlyWait(80,TimeUnit.SECONDS) ;
		driver.findElement(By.xpath("//a[contains(text(),'Login')]")).click();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.id("userName")).sendKeys(username);
		driver.findElement(By.id("password")).sendKeys("Password1!");
		driver.findElement(By.xpath("//input[@value='Sign in']")).click();

	}

	public void devlogin(String username) throws InterruptedException {
		System.setProperty("webdriver.gecko.driver", "C:\\geckodriver.exe");
		driver = new FirefoxDriver();
		String baseUrl = "http://SDP-dev.citrix.com/#/devlogin";

		// launch Fire fox and direct it to the Base URL
		driver.get(baseUrl);

		Thread.sleep(10000);
		// driver.manage().timeouts().implicitlyWait(80,TimeUnit.SECONDS) ;
		// driver.findElement(By.xpath("//a[contains(text(),'Login')]")).click();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.id("email")).sendKeys(username);
		driver.findElement(By.id("pwd")).sendKeys("Password1!");
		driver.findElement(By.xpath("//button[@type='submit']")).click();

	}

	@Test(priority = 0)
	public void RejectRequestTocancel() throws InterruptedException {

		login("bishop32");
		Thread.sleep(10000);

		driver.findElement(By.xpath("//a[@data-ui-sref='partner.myProjectList']")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//input[@ng-true-value=" + "\"'Open'\"" + "]")).click();
		Thread.sleep(5000);

		System.out.println(driver.findElement(By.xpath("//tr[2]")).getText());

		List<String> columndata = new ArrayList<String>();
		for (int i = 0; i < 11; i++) {
			// System.out.println(driver.findElement(By.xpath("//tr[2]/td["+(i+1)+"]")).getText());
			columndata.add(i, driver.findElement(By.xpath("//tr[2]/td[" + (i + 1) + "]")).getText());
			System.out.println(columndata.get(i));
		}

		driver.findElement(By.xpath("//tr[1]/td[11]/button")).click();
		driver.findElement(By.xpath("//textarea")).sendKeys("Please approve my request");
		driver.findElement(By.xpath("//button[contains(text(),'Submit')]")).click();
		Thread.sleep(5000);
		Assert.assertEquals(driver.findElement(By.xpath("//strong")).getText(), "Request Submitted");
		driver.findElement(By.xpath("//button[@title='Close (Esc)']")).click();

		Thread.sleep(5000);
		WebElement element = driver.findElement(By.xpath("//span[contains(text(),'Tim Bishop')]"));

		Actions action = new Actions(driver);

		action.moveToElement(element).build().perform();

		driver.findElement(By.xpath("//a[contains(text(),'Logout')]")).click();
		driver.close();
		login("xdong692");
		Thread.sleep(10000);
		driver.findElement(By.xpath("//a[text()='Manage']")).click();
		Thread.sleep(5000);

		List<WebElement> options = driver.findElements(By.xpath("//a[@data-toggle='collapse']"));
		options.get(1).click();
		Thread.sleep(10000);
		driver.findElement(By.linkText("Cancel Projects")).click();

		Thread.sleep(10000);
		System.out.println(driver.getPageSource().contains("bishop32"));

		List<WebElement> requestedBy = driver.findElements(By.xpath("//tr/td[2]"));
		for (int i = 0; i < requestedBy.size(); i++) {
			// System.out.println(requestedBy.get(i).getText());
			if (requestedBy.get(i).getText().equals("bishop32")) {
				Select changeStatusDropDown = new Select(
						driver.findElement(By.xpath("//tr[" + (i + 1) + "]/td[6]/select")));
				changeStatusDropDown.selectByVisibleText("Rejected");
				driver.findElement(By.xpath("//tr[" + (i + 1) + "]/td[7]/input")).sendKeys("Not interessted");
				driver.findElement(By.xpath("//tr[" + (i + 1) + "]/td[8]/button")).click();
				// Rejected Approved
			}

		}

		login("bishop32");
		Thread.sleep(10000);

		driver.findElement(By.xpath("//a[@data-ui-sref='partner.myProjectList']")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//input[@ng-true-value=" + "\"'Open'\"" + "]")).click();
		Thread.sleep(5000);

		List<WebElement> afterRejectlist = driver.findElements(By.xpath("//tr"));
		System.out.println(afterRejectlist.size());
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,75)");
		Thread.sleep(10000);

		System.out.println(driver.findElement(By.xpath("//tr[1]/td[1]")).getText());
		System.out.println(driver.findElement(By.xpath("//tr[2]/td[1]")).getText());
		System.out.println(driver.findElement(By.xpath("//tr[3]/td[1]")).getText());
		System.out.println(driver.findElement(By.xpath("//tr[4]/td[1]")).getText());
		System.out.println(driver.findElement(By.xpath("//tr[5]/td[1]")).getText());
		/*
		 * 
		 * 
		 * for(int i=1;i<(afterRejectlist.size()-1);i++){
		 * if(driver.findElement(By.xpath("//tr["+(i)+"]/td[1]")).equals(
		 * columndata.get(1))){
		 * System.out.println("You are sucessfully passed first test cases");
		 * if(driver.findElement(By.xpath("//tr["+(i)+"]/td[2]")).equals(
		 * columndata.get(2))){
		 * System.out.println("You are sucessfully passed second test cases");
		 * if(driver.findElement(By.xpath("//tr["+(i)+"]/td[3]")).equals(
		 * columndata.get(3))) {
		 * System.out.println("You are sucessfully passed third test cases");
		 * if(driver.findElement(By.xpath("//tr["+(i)+"]/td[4]")).equals(
		 * columndata.get(4))){
		 * System.out.println("You are sucessfully passed fourth test cases");
		 * if (driver.findElement(By.xpath("//tr["+(i)+"]/td[5]")).equals(
		 * columndata.get(5))) {
		 * System.out.println("You are sucessfully passed fitfth cases");
		 * if(driver.findElement(By.xpath("//tr["+(i)+"]/td[6]")).equals(
		 * columndata.get(6))){
		 * System.out.println("You are sucessfully passed sixth cases");
		 * if(driver.findElement(By.xpath("//tr["+(i)+"]/td[7]")).equals(
		 * columndata.get(7))){
		 * System.out.println("You are sucessfully passed seventh cases");
		 * if(driver.findElement(By.xpath("//tr["+(i)+"]/td[8]")).equals(
		 * columndata.get(8))){
		 * System.out.println("You are sucessfully passed eighth cases");
		 * if(driver.findElement(By.xpath("//tr["+(i)+"]/td[9]")).equals(
		 * columndata.get(9))){
		 * System.out.println("You are sucessfully passed nineth cases");
		 * if(driver.findElement(By.xpath("//tr["+(i)+"]/td[10]")).equals(
		 * columndata.get(10))){
		 * System.out.println("You are sucessfully passed tenth cases"); } } } }
		 * } } } } } } }
		 */
	}

	@Test(priority = 1)
	public void ApproveRequestTocancel() throws InterruptedException {
		login("bishop32");
		Thread.sleep(10000);

		driver.findElement(By.xpath("//a[@data-ui-sref='partner.myProjectList']")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//input[@ng-true-value=" + "\"'Open'\"" + "]")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//tr[1]/td[11]/button")).click();
		driver.findElement(By.xpath("//textarea")).sendKeys("Please approve my request");
		driver.findElement(By.xpath("//button[contains(text(),'Submit')]")).click();
		Thread.sleep(5000);
		Assert.assertEquals(driver.findElement(By.xpath("//strong")).getText(), "Request Submitted");
		driver.findElement(By.xpath("//button[@title='Close (Esc)']")).click();

		Thread.sleep(5000);
		WebElement element = driver.findElement(By.xpath("//span[contains(text(),'Tim Bishop')]"));

		Actions action = new Actions(driver);

		action.moveToElement(element).build().perform();

		driver.findElement(By.xpath("//a[contains(text(),'Logout')]")).click();
		driver.close();
		login("xdong692");
		Thread.sleep(210000);
		driver.findElement(By.xpath("//a[text()='Manage']")).click();
		Thread.sleep(10000);

		List<WebElement> options = driver.findElements(By.xpath("//a[@data-toggle='collapse']"));
		options.get(1).click();
		Thread.sleep(10000);
		driver.findElement(By.linkText("Cancel Projects")).click();

		Thread.sleep(10000);
		System.out.println(driver.getPageSource().contains("bishop32"));

		List<WebElement> requestedBy = driver.findElements(By.xpath("//tr/td[2]"));
		for (int i = 0; i < requestedBy.size(); i++) {
			// System.out.println(requestedBy.get(i).getText());
			if (requestedBy.get(i).getText().equals("bishop32")) {
				Select changeStatusDropDown = new Select(
						driver.findElement(By.xpath("//tr[" + (i + 1) + "]/td[6]/select")));
				changeStatusDropDown.selectByVisibleText("Approved");
				driver.findElement(By.xpath("//tr[" + (i + 1) + "]/td[7]/input")).sendKeys("Not interessted");
				driver.findElement(By.xpath("//tr[" + (i + 1) + "]/td[8]/button")).click();
				// Rejected Approved
			}

		}
	}

	@AfterTest
	public void closebrowser() {
		driver.close();
	}
}
