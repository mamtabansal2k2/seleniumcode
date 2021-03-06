package testcases.rediff;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import testbase.BaseTest;

public class Session extends BaseTest{
	// We don't need a separate login test to run while running every testcase in xml file, so we can use defaultLogin() in ApplicationKeywords() and call it from BaseTest class in @BeforeTest so it will login by default before running any testcase in xml.   
	@Test
	public void doLogin() {
		test.log(Status.INFO,"Logging in");
		app.openBrowser("Mozilla");
		app.navigate("url");
		//int i=100/0;
		app.type("username_css","ashishthakur1983");
		//failure
		//app.reportFailure("First non critical Failure ",false);
		app.type("password_xpath", "pass@1234");
		app.validateElementPresent("login_submit_id");
		app.click("login_submit_id");
	}
	
	@Test
	public void doLogout() {
		System.out.println("Logging out");
	}

}
