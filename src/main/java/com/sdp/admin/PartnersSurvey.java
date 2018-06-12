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
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

/**
 * @author ruchira.more
 *
 */
public class PartnersSurvey {

	public WebDriver driver;
	public static int companySurveyCount = 0, partnerSurveyCount = 0, projectSurveyCount = 0, totalsurveycount = 0,
			chartValuesQuarterAnswered = 0, chartValuesQuarterUnAnswered = 0, chartValuesAnswered = 0,
			chartValuesUnAnswered = 0;

	public void login(String username) throws InterruptedException {
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
		driver.findElement(By.id("userName")).sendKeys(username);
		driver.findElement(By.id("password")).sendKeys("Password1!");
		getWebelement("//input[@value='Sign in']").click();

	}

	@Test(priority=0)
	public void filterByTop10Company() throws InterruptedException {
		login("xdong692");
		Thread.sleep(25000);

		getWebelement("//a[contains(text(),'Partner Survey')]").click();
		Thread.sleep(5000);

		Select topCompanyDropDown = new Select(driver.findElement(By.id("topCompany")));
		for (int a = 1; a <= topCompanyDropDown.getOptions().size(); a++) {
			System.out.println("a is" + a);
			topCompanyDropDown.selectByIndex(a);

			// topCompanyDropDown.selectByVisibleText("Top 10 Company");

			WebElement searchbtn = getWebelement("//button[contains(text(),'Search')]");
			searchbtn.click();
			Thread.sleep(1000);

			Select companyName = new Select(
					getWebelement("//select[@data-ng-model='PSController.survey.customer_org_id']"));
			List<WebElement> totalCompanies = companyName.getOptions();
			System.out.println("total companies are:" + (totalCompanies.size() - 1));

			/* total survey count for that particular company */
			WebElement chart_values = driver.findElement(By.className("chart-values"));
			String[] chart = chart_values.getText().split("/");
			totalsurveycount = Integer.parseInt(chart[1].trim());
			System.out.println("totalsurveycount is" + totalsurveycount);

			/*
			 * if (totalCompanies.size() <= 10) {
			 * Assert.assertEquals(totalCompanies.size(),
			 * companyName.getOptions().size()); } else {
			 * Assert.assertEquals(totalCompanies.size(), 11); }
			 */

			System.out.println("Company size is" + companyName.getOptions().size());

			/* company loop for 10 companies */
			for (int i = 1; i < companyName.getOptions().size(); i++) {
				companyName.selectByIndex(i);
				Thread.sleep(3000);
				searchbtn.click();
				Thread.sleep(3000);

				/* total survey count for that particular company */
				WebElement chartValuesForeachCompany = driver.findElement(By.className("chart-values"));
				String[] chartForCompany = chartValuesForeachCompany.getText().split("/");
				int tempCSC = Integer.parseInt(chartForCompany[1].trim());
				companySurveyCount = companySurveyCount + tempCSC;

				Thread.sleep(3000);

				Select partnerDrpDwn = new Select(
						getWebelement("//select[@data-ng-model='PSController.survey.person_id']"));
				System.out.println("Partners list size" + partnerDrpDwn.getOptions().size());

				/* for partner which belongs to that company */
				for (int j = 1; j < partnerDrpDwn.getOptions().size(); j++) {

					partnerDrpDwn.selectByIndex(j);
					Thread.sleep(2000);
					searchbtn.click();
					Thread.sleep(2000);

					/* total survey count for that particular company */
					WebElement chartValuesForeachPartner = driver.findElement(By.className("chart-values"));
					String[] chartForPartner = chartValuesForeachPartner.getText().split("/");
					int tempPartner = Integer.parseInt(chartForPartner[1].trim());
					partnerSurveyCount = partnerSurveyCount + tempPartner;
					System.out.println("partnerSurveyCount" + partnerSurveyCount);

					/* project list */
					Select projectDrpDwn = new Select(
							getWebelement("//select[@data-ng-model='PSController.survey.project_name']"));
					System.out.println("project list for partner is" + projectDrpDwn.getOptions().size());

					/* for projects which belongs to that partner */
					for (int y = 1; y < projectDrpDwn.getOptions().size(); y++) {
						projectDrpDwn.selectByIndex(y);
						Thread.sleep(2000);
						searchbtn.click();
						Thread.sleep(2000);

						WebElement chartValuesForeachProject = driver.findElement(By.className("chart-values"));
						String[] chartForProject = chartValuesForeachProject.getText().split("/");
						int tempProject = Integer.parseInt(chartForProject[1].trim());
						projectSurveyCount = projectSurveyCount + tempProject;
						System.out.println("projectSurveyCount" + projectSurveyCount);
					}
					System.out.println("After all projects in that partner" + projectSurveyCount);
					// Assert.assertEquals(projectSurveyCount,
					// partnerSurveyCount);
					projectSurveyCount = 0;
				}
				System.out.println("After all partners count" + partnerSurveyCount);
				// Assert.assertEquals(partnerSurveyCount, companySurveyCount);
				partnerSurveyCount = 0;
				System.out.println("--------------------------------------------------------------------");
			}
			System.out.println("After companySurveyCount" + companySurveyCount);
			//Assert.assertEquals(companySurveyCount, totalsurveycount);
			companySurveyCount = 0;
		}
		getWebelement("//button[contains(text(),'Reset All')]").click();
	}

	@Test(priority=1)
	public void filterByTop10CompletedProject() throws InterruptedException {
		// login("xdong692");
		// Thread.sleep(25000);

		// getWebelement("//a[contains(text(),'Partner Survey')]").click();
		Thread.sleep(5000);

		Select topCompletedProjectDropDown = new Select(driver.findElement(By.id("topProject")));
		for (int a = 1; a <= topCompletedProjectDropDown.getOptions().size(); a++) {
			System.out.println("a is" + a);
			topCompletedProjectDropDown.selectByIndex(a);

			// topCompanyDropDown.selectByVisibleText("Top 10 Company");

			WebElement searchbtn = getWebelement("//button[contains(text(),'Search')]");
			searchbtn.click();
			Thread.sleep(1000);

			Select companyName = new Select(
					getWebelement("//select[@data-ng-model='PSController.survey.customer_org_id']"));
			List<WebElement> totalCompanies = companyName.getOptions();
			System.out.println("total companies are:" + (totalCompanies.size() - 1));

			/* total survey count for that particular company */
			WebElement chart_values = driver.findElement(By.className("chart-values"));
			String[] chart = chart_values.getText().split("/");
			totalsurveycount = Integer.parseInt(chart[1].trim());
			System.out.println("totalsurveycount is" + totalsurveycount);

			/*
			 * if (totalCompanies.size() <= 10) {
			 * Assert.assertEquals(totalCompanies.size(),
			 * companyName.getOptions().size()); } else {
			 * Assert.assertEquals(totalCompanies.size(), 11); }
			 */

			System.out.println("Company size is" + companyName.getOptions().size());

			/* company loop for 10 companies */
			for (int i = 1; i < companyName.getOptions().size(); i++) {
				companyName.selectByIndex(i);
				Thread.sleep(3000);
				searchbtn.click();
				Thread.sleep(3000);

				/* total survey count for that particular company */
				WebElement chartValuesForeachCompany = driver.findElement(By.className("chart-values"));
				String[] chartForCompany = chartValuesForeachCompany.getText().split("/");
				int tempCSC = Integer.parseInt(chartForCompany[1].trim());
				companySurveyCount = companySurveyCount + tempCSC;

				Thread.sleep(3000);

				Select partnerDrpDwn = new Select(
						getWebelement("//select[@data-ng-model='PSController.survey.person_id']"));
				System.out.println("Partners list size" + partnerDrpDwn.getOptions().size());

				/* for partner which belongs to that company */
				for (int j = 1; j < partnerDrpDwn.getOptions().size(); j++) {

					partnerDrpDwn.selectByIndex(j);
					Thread.sleep(2000);
					searchbtn.click();
					Thread.sleep(2000);

					/* total survey count for that particular company */
					WebElement chartValuesForeachPartner = driver.findElement(By.className("chart-values"));
					String[] chartForPartner = chartValuesForeachPartner.getText().split("/");
					int tempPartner = Integer.parseInt(chartForPartner[1].trim());
					partnerSurveyCount = partnerSurveyCount + tempPartner;
					System.out.println("partnerSurveyCount" + partnerSurveyCount);

					/* project list */
					Select projectDrpDwn = new Select(
							getWebelement("//select[@data-ng-model='PSController.survey.project_name']"));
					System.out.println("project list for partner is" + projectDrpDwn.getOptions().size());

					/* for projects which belongs to that partner */
					for (int y = 1; y < projectDrpDwn.getOptions().size(); y++) {
						projectDrpDwn.selectByIndex(y);
						Thread.sleep(2000);
						searchbtn.click();
						Thread.sleep(2000);

						WebElement chartValuesForeachProject = driver.findElement(By.className("chart-values"));
						String[] chartForProject = chartValuesForeachProject.getText().split("/");
						int tempProject = Integer.parseInt(chartForProject[1].trim());
						projectSurveyCount = projectSurveyCount + tempProject;
						System.out.println("projectSurveyCount" + projectSurveyCount);
					}
					System.out.println("After all projects in that partner" + projectSurveyCount);
					// Assert.assertEquals(projectSurveyCount,
					// partnerSurveyCount);
					projectSurveyCount = 0;
				}
				System.out.println("After all partners count" + partnerSurveyCount);
				// Assert.assertEquals(partnerSurveyCount, companySurveyCount);
				partnerSurveyCount = 0;
				System.out.println("--------------------------------------------------------------------");
			}
			System.out.println("After companySurveyCount" + companySurveyCount);
			//Assert.assertEquals(companySurveyCount, totalsurveycount);
			companySurveyCount = 0;
		}
		getWebelement("//button[contains(text(),'Reset All')]").click();
	}

	@Test(priority=2)
	public void searchByProjectName() throws InterruptedException {
		//login("xdong692");
		//Thread.sleep(25000);

		//getWebelement("//a[contains(text(),'Partner Survey')]").click();
		Thread.sleep(5000);

		// Thread.sleep(3000);

		WebElement searchbtn = getWebelement("//button[contains(text(),'Search')]");
		Select projectname = new Select(getWebelement("//*[@ data-ng-model='PSController.survey.project_name']"));
		for (int b = 1; b < projectname.getOptions().size(); b++) {
			projectname.selectByIndex(b);

			Thread.sleep(2000);
			searchbtn.click();
			Thread.sleep(2000);

			WebElement chartValuesForeachProject = driver.findElement(By.className("chart-values"));
			String[] chartForProject = chartValuesForeachProject.getText().split("/");
			int tempProject = Integer.parseInt(chartForProject[1].trim());
			projectSurveyCount = projectSurveyCount + tempProject;
			System.out.println("projectSurveyCount" + projectSurveyCount);
		}

		getWebelement("//button[contains(text(),'Search')]").click();

		Assert.assertEquals(projectSurveyCount, 62);
		getWebelement("//button[contains(text(),'Reset All')]").click();
	}

/*	@Test(priority=3)
	public void searchByCompanyName() throws InterruptedException {
		Thread.sleep(3000);

		WebElement element = driver.findElement(By.xpath("//*[@ng-model='PSController.survey.company_name']"));
		element.click();
		element.sendKeys(Keys.ARROW_DOWN);
		element.sendKeys(Keys.ENTER);
		getWebelement("//button[contains(text(),'Search')]").click();
	}

*/	@Test(priority=3)
	public void searchByQuarter() throws InterruptedException {

	/*	login("xdong692");
		Thread.sleep(25000);

		getWebelement("//a[contains(text(),'Partner Survey')]").click();
*/		Thread.sleep(5000);

		Select quarterlist = new Select(
				driver.findElement(By.xpath(" //select[@data-ng-change='PSController.selectQuarter()']")));

		for (int i = 1; i < quarterlist.getOptions().size(); i++) {
			quarterlist.selectByIndex(i);
			Thread.sleep(3000);
			getWebelement("//button[contains(text(),'Search')]").click();
			Thread.sleep(2000);
			WebElement chart_values = driver.findElement(By.className("chart-values"));
			String[] chart = chart_values.getText().split("/");
			int temp1 = Integer.parseInt(chart[0].trim());
			int temp2 = Integer.parseInt(chart[1].trim());
			chartValuesQuarterAnswered = chartValuesQuarterAnswered + temp1;
			chartValuesQuarterUnAnswered = chartValuesQuarterUnAnswered + temp2;

			System.out.println("chartValuesQuarterAnswered" + chartValuesQuarterAnswered
					+ "and chartValuesQuarterUnAnswered" + chartValuesQuarterUnAnswered);

		}
	}

	@Test(priority=5)
	public void searchByUnanswered() throws InterruptedException {

		Thread.sleep(3000);
		getWebelement("//input[@type='checkbox']").click();
		getWebelement("//button[contains(text(),'Search')]").click();
		WebElement chart_values = driver.findElement(By.className("chart-values"));
		String[] chart = chart_values.getText().split("/");
		int temp1 = Integer.parseInt(chart[0].trim());
		int temp2 = Integer.parseInt(chart[1].trim());
		chartValuesAnswered = chartValuesAnswered + temp1;
		chartValuesUnAnswered = chartValuesUnAnswered + temp2;

		System.out.println(
				"chartValuesAnswered" + chartValuesAnswered + "and chartValuesUnAnswered" + chartValuesUnAnswered);

	}

	public WebElement getWebelement(String expression) {

		return driver.findElement(By.xpath(expression));
	}

	@AfterTest
	public void closebrowser() {
		driver.close();
	}

}
