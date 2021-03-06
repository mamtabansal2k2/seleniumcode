package keywords;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class ApplicationKeywords extends ValidationKeywords {
	
	public ApplicationKeywords() { // required to initialize the object of properties file while running the testcase
		System.out.println("Inside default condtructor of ApplicationKeywords class ");
		String path = System.getProperty("user.dir")+"//src//test//resources//env.properties";
		System.out.println("path= "+path);
		prop = new Properties();
		envProp = new Properties();
		try {
			FileInputStream fs = new FileInputStream(path);
			prop.load(fs);
			String env = prop.getProperty("env")+".properties";
			path= System.getProperty("user.dir")+"//src//test//resources//"+env;
			fs = new FileInputStream(path);
			envProp.load(fs);
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
		softAssert = new SoftAssert();
	}
	
	public void defaultLogin() {
		test.log(Status.INFO,"Logging in");
		//openBrowser("Mozilla");
		navigate("url");
		type("username_css",envProp.getProperty("admin_user_name"));//login info is kept in properties file not in data bcz it will be same for every testcase
		//failure
		//app.reportFailure("First non critical Failure ",false);
		type("password_xpath",envProp.getProperty("admin_password"));
		//validateElementPresent("login_submit_id");
		click("login_submit_id");
		waitForPageToLoad();
		wait(5);
		
	}
	public void selectDateFromCalender() {
		
	}
	public void verifyStockAdded() {
		
	}
	public void setReport(ExtentTest test) {
		this.test = test;	// setting GenericKeyword test object to point to current test object in BaseTest
	}
	
	

}
