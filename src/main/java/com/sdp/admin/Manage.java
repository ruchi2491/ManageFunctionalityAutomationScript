/**
 * 
 */
package com.sdp.admin;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * @author ruchira.more
 *
 */
public class Manage {

	public WebDriver driver;
	public Utils utils;

	public Manage() {
		utils = new Utils();
		// TODO Auto-generated constructor stub
	}

	public void login() throws InterruptedException {

		System.setProperty("webdriver.gecko.driver", "C:\\geckodriver.exe");
		driver = new FirefoxDriver();
		String baseUrl = "http://SDP-qa.citrix.com";

		// launch Fire fox and direct it to the Base URL
		driver.get(baseUrl);

		Thread.sleep(10000);
		// driver.manage().timeouts().implicitlyWait(80,TimeUnit.SECONDS) ;
		getWebelement("//a[contains(text(),'Login')]").click();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.id("userName")).sendKeys("xdong692");
		driver.findElement(By.id("password")).sendKeys("Password1!");
		getWebelement("//input[@value='Sign in']").click();
	}

//	@Test
	public void devlogin() throws InterruptedException {
		System.setProperty("webdriver.gecko.driver", "C:\\geckodriver.exe");
		driver = new FirefoxDriver();
		String baseUrl = "http://SDP-dev.citrix.com/#/devlogin";

		// launch Fire fox and direct it to the Base URL
		driver.get(baseUrl);

		Thread.sleep(5000);
		// driver.manage().timeouts().implicitlyWait(80,TimeUnit.SECONDS) ;
		getWebelement("//a[contains(text(),'Login')]").click();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.id("userName")).sendKeys("xdong692");
		driver.findElement(By.id("password")).sendKeys("Password1!");
		getWebelement("//input[@value='Sign in']").click();

	}

	@Test(priority = 0)
	public void mangeUser() throws InterruptedException {
		login();
		Thread.sleep(25000);
		getWebelement("//a[text()='Manage']").click();
		Thread.sleep(5000);
		getWebelement("//input[@id='search']").sendKeys("genmgr1");
		getWebelement("//button[text()='Search']").click();
		String username = getWebelement("//td[1]").getText();
		Assert.assertEquals("genmgr1", username);
		getWebelement("//button[text()='Reset']").click();
	}

	@Test(priority = 1)
	public void manageUserCount() throws InterruptedException {
		Thread.sleep(5000);
		getWebelement("/html/body/div[1]/div[1]/div/div[2]/div/section/div/div[1]/div/div[1]/div[2]/ul/li[10]/a")
				.click();
		List<WebElement> pageinitation = driver.findElements(By.xpath("//li[contains(@class,'pagination-page')]"));
		System.out.println("Size of list" + pageinitation.size());
		System.out.println("Last element in the list is:" + pageinitation.get(5).getText());
		List<WebElement> records = driver.findElements(By.xpath("//tr"));
		System.out.println("records in table:" + records.size());
		int totalrecord = ((Integer.parseInt(pageinitation.get(5).getText()) - 1) * 20) + (records.size() - 1);
		System.out.println("Total Records:" + totalrecord);

		Thread.sleep(5000);
		getWebelement("//tr[1]/td[7]/img").click();
		Thread.sleep(5000);
		String popmsg = getWebelement("//p[contains(text(),'You want to')]").getText();
		System.out.println("popup messgae:" + popmsg);
		getWebelement("//button[contains(text(),'Yes')]").click();
		Thread.sleep(10000);
		if (popmsg.contains("InActivate")) {
			System.out.println("Inactive");
			Assert.assertTrue(getWebelement("//tr[1]/td[7]/img[@data-ng-src='/assets/images/tick.png']").isDisplayed());
		} else {
			System.out.println("Active");
			Assert.assertTrue(
					getWebelement("//tr[1]/td[7]/img[@data-ng-src='/assets/images/delete.png']").isDisplayed());
		}

	}

	@Test(priority = 2)
	public void manageAdmin() throws InterruptedException {
		Thread.sleep(5000);
		List<WebElement> options = driver.findElements(By.xpath("//a[@data-toggle='collapse']"));
		options.get(0).click();
		Thread.sleep(5000);
		getWebelement("//a[contains(text(),'Manage Admin')]").click();
		getWebelement("//button[@data-ng-click='manageAdminCtrl.addNewAdmin()']").click();
		getWebelement("//input[@name='username']").sendKeys("kevinko");
		getWebelement("//button[contains(text(),'Add')]").click();
		Assert.assertTrue(getWebelement("//p[contains(text(),'admin already present')]").isDisplayed());
		Thread.sleep(5000);

		getWebelement("//a[contains(text(),'Manage Admin')]").click();
		getWebelement("//button[@data-ng-click='manageAdminCtrl.addNewAdmin()']").click();
		getWebelement("//input[@name='username']").sendKeys("Ruchira");
		getWebelement("//button[contains(text(),'Add')]").click();
		Assert.assertTrue(getWebelement("//p[contains(text(),'Please try again')]").isDisplayed());
		getWebelement("//button[text()='OK']").click();

		Thread.sleep(5000);
		getWebelement("//a[contains(text(),'Manage Admin')]").click();
		getWebelement("//button[@data-ng-click='manageAdminCtrl.addNewAdmin()']").click();
		getWebelement("//input[@name='username']").clear();
		getWebelement("//input[@name='username']").sendKeys("xdong692");
		getWebelement("//button[contains(text(),'Add')]").click();
		Assert.assertTrue(getWebelement("//p[contains(text(),'You are already admin')]").isDisplayed());

		String username = getWebelement("//tr[1]/td[1]").getText();
		new WebDriverWait(driver, 60).until(ExpectedConditions.elementToBeClickable(By.xpath(
				".//*[@id='list']/table/tbody/tr[1]/td[4]/img[@ng-click='manageAdminCtrl.removeAdmin(admin.person_id)']")));
		Thread.sleep(5000);
		getWebelement("//tr[1]/td[4]/img").click();
		Assert.assertTrue(
				getWebelement("//p[contains(text(),'This user no longer able to loggedIn as admin')]").isDisplayed());

		Thread.sleep(5000);
		getWebelement("//a[contains(text(),'Manage Admin')]").click();
		getWebelement("//button[@data-ng-click='manageAdminCtrl.addNewAdmin()']").click();
		getWebelement("//input[@name='username']").sendKeys(username);
		getWebelement("//button[contains(text(),'Add')]").click();
		Assert.assertTrue(getWebelement("//p[contains(text(),'admin status updated to Active')]").isDisplayed());

	}

	@Test(priority = 3)
	public void manageEntitlement() throws InterruptedException {
		login();
		Thread.sleep(15000);
		getWebelement("//a[text()='Manage']").click();
		Thread.sleep(5000);

		String orgid = "63935HQ";
		Thread.sleep(5000);
		List<WebElement> options = driver.findElements(By.xpath("//a[@data-toggle='collapse']"));
		options.get(0).click();
		Thread.sleep(5000);
		getWebelement("//a[@data-ui-sref='admin.manage.entitlements']").click();
		Thread.sleep(2000);
		getWebelement("//button[contains(text(),'Assign Entitlement')]").click();
		Thread.sleep(2000);
		WebElement textbox = getWebelement("//input[@name='billing-form-id']");
		textbox.sendKeys(orgid);
		textbox.sendKeys(Keys.ENTER);

		Thread.sleep(3000);
		getWebelement("//input[@type='radio' and @value='1']").click();
		getWebelement("//textarea").sendKeys("This is new subscription");
		getWebelement("//a[contains(text(),'Assign')]").click();
		Thread.sleep(10000);
		driver.findElement(
				By.xpath("//a[contains(text(),'YES') and @data-ng-click='ManageEntitlementCtrl.assignSKU();']"))
				.click();
		Thread.sleep(10000);
		List<WebElement> totalentitlements = driver.findElements(By.xpath(
				"//tbody/tr[@data-ng-repeat='entitlement in ManageEntitlementCtrl.manualEntitlements | orderBy : ManageEntitlementCtrl.myOrderBy track by $index']"));
		System.out.println("total rows:" + totalentitlements.size());

		Assert.assertTrue(driver.getPageSource().contains(orgid));

		Thread.sleep(2000);
		getWebelement(
				"//td[contains(text(),'" + orgid + "')]/following-sibling::td[@data-title=" + "\"'Actions'\"" + "]/a")
						.click();
		Thread.sleep(5000);
		// System.out.println("hfkdgfh"+getWebelement("//strong[contains(text(),'remove')])")).isDisplayed());
		// Assert.assertTrue(getWebelement("//strong[contains(text(),'remove')])")).isDisplayed());

		getWebelement("//a[@data-ng-click='ManageEntitlementCtrl.deleteEntitlement();']").click();
		Thread.sleep(5000);
		Assert.assertEquals(driver.getPageSource().contains(orgid), false);

	}

	public WebElement getWebelement(String expression) {

		return driver.findElement(By.xpath(expression));
	}

	@Test(priority = 4)
	public void sendSurvey() throws InterruptedException {
		login();
		Thread.sleep(3000);
		getWebelement("//a[text()='Manage']").click();

		Thread.sleep(2000);

		getWebelement("//a[contains(text(),'Send Survey Reminder')]").click();
		Thread.sleep(2000);
		Assert.assertTrue(getWebelement("//h2").isDisplayed());
		getWebelement("//button[contains(text(),'Send')]").click();
		Thread.sleep(5000);
		// Assert.assertTrue(getWebelement("//p[contains(text(),'Survey Reminder
		// Sent')]").isDisplayed());
		getWebelement("//button[contains(text(),'OK')]").click();

		/* login with email id to check survey mail is coming or not */
		driver.get("https://www.google.com/gmail/about/#");
		getWebelement("//a[contains(text(),'Sign In')]").click();
		Thread.sleep(3000);
		getWebelement("//input[@type='email']").sendKeys("ruchira.more@atmecs.com");
		getWebelement("//div[@role='button']/content/span[contains(text(),'Next')]").click();
		Thread.sleep(3000);
		getWebelement("//input[@type='password']").sendKeys("91");
		getWebelement("//div[@role='button']/content/span[contains(text(),'Next')]").click();
		Thread.sleep(4000);

	}

	@AfterTest
	public void closebrowser() {
		driver.close();
	}
}
