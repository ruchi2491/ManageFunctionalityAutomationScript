/**
 * 
 */
package com.sdp.utilities;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

/**
 * @author ruchira.more
 *
 */
public class mouseHover{
	 
	public static WebDriver driver;
 
   public static void main(String[] args) throws InterruptedException {
 
	   System.setProperty("webdriver.gecko.driver", "C:\\geckodriver.exe");
        driver = new FirefoxDriver();
 
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
 
        driver.get("http://SDP-dev.citrix.com/#/devlogin");
        
        Thread.sleep(3000);
        driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.id("email")).sendKeys("bishop32");
		driver.findElement(By.id("pwd")).sendKeys("Password1!");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		
		Thread.sleep(3000);
        WebElement element = driver.findElement(By.xpath("//span[contains(text(),'Tim Bishop')]"));
 
        Actions action = new Actions(driver);
 
        action.moveToElement(element).build().perform();
 
        driver.findElement(By.xpath("//a[contains(text(),'Logout')]")).click();
 
        }
 
}