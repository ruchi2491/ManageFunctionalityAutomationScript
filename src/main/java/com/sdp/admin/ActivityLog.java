/*
 * 
 */
package com.sdp.admin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.ListIterator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author ruchira.more
 *
 */
public class ActivityLog {

	WebDriver driver;
	LocalDate currentdate, nextdate;

	public void activityTab() throws InterruptedException {
		driver = new Utils().login();
		Thread.sleep(15000);
		driver.findElement(By.xpath("//a[text()='Manage']")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[@data-ui-sref='admin.manage.activity']")).click();
		Thread.sleep(3000);
	}

	@Test(priority=0)
	public void searchByUsername() throws InterruptedException {
		String username = "srikanthreddy";
		activityTab();
		driver.findElement(By.xpath("//input[@data-ng-model='ActivityCtrl.activity.search']")).sendKeys(username);
		driver.findElement(By.xpath("//button[contains(text(),'Search')]")).click();
		Thread.sleep(2000);
		List<WebElement> usernames = driver.findElements(By.xpath("//tr/td[3]"));
		ListIterator<WebElement> listItr = usernames.listIterator();
		while (listItr.hasNext()) {
			//System.out.println(listItr.next().getText());
			Assert.assertEquals(listItr.next().getText(), username);
		}
		Thread.sleep(1000);
		driver.findElement(By.xpath("//button[contains(text(),'Reset')]")).click();
		Thread.sleep(1000);
	}

	@Test(priority=1)
	public void searchByActivity() throws InterruptedException {
		String activity = "Category";
		// activityTab();
		driver.findElement(By.xpath("//input[@data-ng-model='ActivityCtrl.activity.search']")).sendKeys(activity);
		Select drpOption = new Select(driver.findElement(By.xpath("//select")));
		drpOption.selectByVisibleText("By Activity");
		driver.findElement(By.xpath("//button[contains(text(),'Search')]")).click();
		Thread.sleep(1000);
		List<WebElement> activities = driver.findElements(By.xpath("//tr/td[2]"));
		ListIterator<WebElement> listItr1 = activities.listIterator();
		while (listItr1.hasNext()) {
			Assert.assertTrue(listItr1.next().getText().contains(activity));
		}
		Thread.sleep(1000);
		driver.findElement(By.xpath("//button[contains(text(),'Reset')]")).click();
		Thread.sleep(1000);
	}

	@Test(priority = 2)
	public void searchByFromDate() throws InterruptedException, ParseException {
	//	activityTab();
		driver.findElement(By.xpath("//button[@data-ng-click='open1()']")).click();
		String month = driver.findElement(By.xpath("//button[@ng-click='toggleMode()']")).getText();
		List<WebElement> dates = driver.findElements(By.xpath("//button[@ng-click='select(dt.date)']"));
		Thread.sleep(2000);

		String date = dates.get(0).getText();
		dates.get(0).click();
		System.out.println(date + " " + month);

		driver.findElement(By.xpath("//button[contains(text(),'Search')]")).click();

		List<WebElement> datesinTable = driver.findElements(By.xpath("//tr/td[4]"));
		ListIterator<WebElement> datesTableitr = datesinTable.listIterator();
		while (datesTableitr.hasNext()) {
			String dateString = datesTableitr.next().getText();
			String[] datearr = dateString.split(" ");

			Assert.assertTrue(new SimpleDateFormat("yyyy-MM-dd").parse(datearr[0])
					.after(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.ms").parse("2018-05-28 00:00:00.000")));
		}
		driver.findElement(By.xpath("//button[contains(text(),'Reset')]")).click();
		Thread.sleep(1000);

	}

	@Test(priority = 4)
	public void searchByTodayDate() throws InterruptedException, ParseException {

	//	activityTab();

		driver.findElement(By.xpath("//button[@data-ng-click='open1()']")).click();
		String fromMonth = driver.findElement(By.xpath("//button[@ng-click='toggleMode()']")).getText();
		List<WebElement> fromDates = driver.findElements(By.xpath("//button[@ng-click='select(dt.date)']"));
		Thread.sleep(2000);

		String fromDate = fromDates.get(15).getText();
		fromDates.get(15).click();
		System.out.println(fromDate + " " + fromMonth);

		driver.findElement(By.xpath("//button[@data-ng-click='open2()']")).click();
		String toMonth = driver.findElement(By.xpath("//button[@ng-click='toggleMode()']")).getText();
		List<WebElement> toDates = driver.findElements(By.xpath("//button[@ng-click='select(dt.date)']"));
		Thread.sleep(2000);

		String toDate = toDates.get(15).getText();
		toDates.get(15).click();
		System.out.println(toDate + " " + toMonth);

		driver.findElement(By.xpath("//button[contains(text(),'Search')]")).click();

		Thread.sleep(2000);
		List<WebElement> datesinTable = driver.findElements(By.xpath("//tr/td[4]"));
		ListIterator<WebElement> datesTableitr = datesinTable.listIterator();
		while (datesTableitr.hasNext()) {
			String dateString = datesTableitr.next().getText();
			String[] datearr = dateString.split(" ");
			Assert.assertTrue(new SimpleDateFormat("yyyy-MM-dd").parse(datearr[0])
					.equals(new SimpleDateFormat("yyyy-MM-dd").parse(currentDate().toString())));
		}
		driver.findElement(By.xpath("//button[contains(text(),'Reset')]")).click();
		Thread.sleep(1000);
	}

	@Test(priority = 3)
	public void searchByToDate() throws InterruptedException, ParseException {
		// activityTab();
		driver.findElement(By.xpath("//button[@data-ng-click='open2()']")).click();
		String month = driver.findElement(By.xpath("//button[@ng-click='toggleMode()']")).getText();
		List<WebElement> dates = driver.findElements(By.xpath("//button[@ng-click='select(dt.date)']"));
		Thread.sleep(2000);

		String date = dates.get(15).getText();
		dates.get(15).click();
		System.out.println(date + " " + month);

		driver.findElement(By.xpath("//button[contains(text(),'Search')]")).click();

		List<WebElement> datesinTable = driver.findElements(By.xpath("//tr/td[4]"));
		ListIterator<WebElement> datesTableitr = datesinTable.listIterator();
		while (datesTableitr.hasNext()) {
			String dateString = datesTableitr.next().getText();
			String[] datearr = dateString.split(" ");
			Assert.assertTrue(new SimpleDateFormat("yyyy-MM-dd").parse(datearr[0])
					.before(new SimpleDateFormat("yyyy-MM-dd").parse(tomorrowDate().toString())));
		}
		driver.findElement(By.xpath("//button[contains(text(),'Reset')]")).click();
		Thread.sleep(1000);
	}

	public LocalDate currentDate() {

		currentdate = LocalDate.now();
		System.out.println(currentdate);
		return currentdate;
	}

	public LocalDate tomorrowDate() {

		nextdate = LocalDate.now();
		nextdate = nextdate.plusDays(1);
		System.out.println("next day:" + nextdate);
		return nextdate;
	}
}
