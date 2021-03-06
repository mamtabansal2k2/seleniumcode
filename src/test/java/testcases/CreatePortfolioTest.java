package testcases;

import org.testng.annotations.Test;

import keywords.ApplicationKeywords;

public class CreatePortfolioTest {
	
	
	@Test
	public void createPortfolioTest() {
	
	// as soon as we create the object app of ApplicationKeywords class,default constructor is called and prop object is initialized to use	
	
	ApplicationKeywords app = new ApplicationKeywords(); //initialize applicationKeyword class object bcz it inherits other classes and can use them so we don't need to initialize object of every class
	app.openBrowser("Mozilla");
	app.navigate("url");
	app.validateTitle();
	app.type("username_css","ashishthakur1983");
	app.type("password_xpath", "pass@1234");
	app.validateElementPresent("login_submit_id");
	app.click("login_submit_id");
	//------------
	app.validateLogin("login_validate_xpath");
	app.click("createPortfolio_Button_xpath");
	app.clear("enterPortfolio_css");
	app.type("enterPortfolio_css","Hello");
	app.click("createMyPortfolio_Button_xpath");
	
	}

}
