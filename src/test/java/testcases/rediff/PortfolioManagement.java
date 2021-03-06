package testcases.rediff;

import org.json.simple.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import keywords.ApplicationKeywords;
import testbase.BaseTest;

public class PortfolioManagement extends BaseTest {
	
	@Test
	public void createPortfolio(ITestContext context) {
		app.log("Creating portfolio");
		JSONObject data = (JSONObject) context.getAttribute("data");
		String portfolioName = (String) data.get("portfolioname");
		
		app.click("createPortfolio_id");
		app.clear("portfolioname_id");
		app.type("portfolioname_id",portfolioName);
		app.click("createPortfolioButton_css");
		app.waitForPageToLoad();
		app.wait(5);
		//app.reportFailure("Second non critical failure",false);
		app.validateSelectedValueInDropDown("portfolioid_dropdown_id", portfolioName);
		app.assertAll();
	}
	@Test
	public void deletePortfolio(ITestContext context) {
		app.log("Deleting portfolio");
		JSONObject data = (JSONObject) context.getAttribute("data");
		String portfolioName = (String) data.get("portfolioname");
		app.selectByVisibleText("portfolioid_dropdown_id", portfolioName);
		app.waitForPageToLoad();
		app.click("deletePortfolio_id");
		app.acceptAlert();
		app.wait(5);
		//app.waitForPageToLoad();
		app.validateSelectedValueNotInDropDown("portfolioid_dropdown_id", portfolioName);
	}
	@Test
	public void selectPortfolio() {
		app.log("Selecting portfolio");
		app.selectByVisibleText("portfolioid_dropdown_id","Cat");
		app.waitForPageToLoad();
	}
	@Test
	public void verifyPortfolio() {
		
	}

}
