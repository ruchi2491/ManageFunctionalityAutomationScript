/**
 * 
 */
package com.sdp.admin;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author ruchira.more
 *
 */
public class ManageProjects {

	WebDriver driver;

	@Test(priority = 1)
	public void projectlist() throws InterruptedException {

		driver = new Utils().login();
		Thread.sleep(15000);
		driver.findElement(By.xpath("//a[text()='Manage']")).click();
		Thread.sleep(3000);
		List<WebElement> options = driver.findElements(By.xpath("//a[@data-toggle='collapse']"));
		options.get(1).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[contains(text(),'Manage Projects')]")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//input[@id='search']")).sendKeys("Grant Cynor");
		driver.findElement(By.xpath("//button[text()='Search']")).click();
		List<WebElement> partnername = driver.findElements(By.xpath("//tr/td[3]"));
		for (int i = 0; i < partnername.size(); i++) {
			if (partnername.get(i).getText().equals("Grant Cynor")) {
				System.out.println("Pass");
			}
		}
		driver.findElement(By.xpath("//button[contains(text(),'Reset')]")).click();

		Thread.sleep(5000);
		driver.findElement(
				By.xpath("//*[@id='wrapper']/div[1]/div/div[2]/div/section/div/div[1]/div/div[1]/div[2]/ul/li[8]/a"))
				.click();
		List<WebElement> pageinitation = driver.findElements(By.xpath("//li[contains(@class,'pagination-page')]"));
		System.out.println("Size of list" + pageinitation.size());
		System.out.println("Last element in the list is:" + pageinitation.get(3).getText());
		List<WebElement> records = driver.findElements(By.xpath("//tr"));
		System.out.println("records in table:" + records.size());
		int totalrecord = ((Integer.parseInt(pageinitation.get(3).getText()) - 1) * 20) + (records.size() - 1);
		System.out.println("Total Records:" + totalrecord);

		Thread.sleep(5000);
		driver.findElement(By.xpath("//tr[1]/td[6]/img")).click();
		Thread.sleep(5000);
		String popmsg = driver.findElement(By.xpath("//p[contains(text(),'You want to')]")).getText();
		System.out.println("popup messgae:" + popmsg);
		driver.findElement(By.xpath("//button[contains(text(),'Yes')]")).click();
		Thread.sleep(10000);
		if (popmsg.contains("InActivate")) {
			System.out.println("Inactive");
			Assert.assertTrue(driver.findElement(By.xpath("//tr[1]/td[6]/img[@data-ng-src='/assets/images/tick.png']"))
					.isDisplayed());
		} else {
			System.out.println("Active");
			Assert.assertTrue(
					driver.findElement(By.xpath("//tr[1]/td[6]/img[@data-ng-src='/assets/images/delete.png']"))
							.isDisplayed());
		}

	}
}
