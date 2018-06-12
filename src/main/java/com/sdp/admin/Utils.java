/**
 * 
 */
package com.sdp.admin;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * @author ruchira.more
 *
 */
public class Utils {
	public WebDriver driver;

	public WebDriver login() throws InterruptedException {

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
		driver.findElement(By.id("userName")).sendKeys("xdong692");
		driver.findElement(By.id("password")).sendKeys("Password1!");
		driver.findElement(By.xpath("//input[@value='Sign in']")).click();

		return driver;
	}

	// @Test
	public void devlogin() throws InterruptedException {
		System.setProperty("webdriver.gecko.driver", "C:\\geckodriver.exe");
		driver = new FirefoxDriver();
		String baseUrl = "http://SDP-dev.citrix.com/#/devlogin";

		// launch Fire fox and direct it to the Base URL
		driver.get(baseUrl);

		Thread.sleep(5000);
		// driver.manage().timeouts().implicitlyWait(80,TimeUnit.SECONDS) ;
		driver.findElement(By.xpath("//a[contains(text(),'Login')]")).click();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.id("userName")).sendKeys("xdong692");
		driver.findElement(By.id("password")).sendKeys("Password1!");
		driver.findElement(By.xpath("//input[@value='Sign in']")).click();

	}

	public String identifyBrowser() {
		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = cap.getBrowserName().toLowerCase();
		System.out.println(browserName);
		String os = cap.getPlatform().toString();
		// System.out.println(os);
		String v = cap.getVersion().toString();
		// System.out.println(v);
		return browserName;
	}

}
