package testbase;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import keywords.ApplicationKeywords;
import reports.ExtentManager;
import runner.DataUtil;

public class BaseTest {
	
	public ApplicationKeywords app;
	public ExtentReports rep;
	public ExtentTest test;
	
	@BeforeTest(alwaysRun=true)
	public void beforeTest(ITestContext context) throws NumberFormatException, FileNotFoundException, IOException, ParseException {
		System.out.println("-----------Before Test------------");
		String dataflag = context.getCurrentXmlTest().getParameter("dataflag");
		String datafilepath = context.getCurrentXmlTest().getParameter("datafilepath");
		String iteration = context.getCurrentXmlTest().getParameter("iteration");
		String browser = context.getCurrentXmlTest().getParameter("sbrowser");
		System.out.println("dataflag= "+dataflag);
		System.out.println("datafilepath= "+datafilepath);
		System.out.println("iteration= "+iteration);
		System.out.println("browser= "+browser);
		JSONObject data = new DataUtil().getTestData(datafilepath,dataflag,Integer.parseInt(iteration));
		context.setAttribute("data", data);
		String runmode = (String) data.get("runmode");
		//what is the path to data json/xls
		//what is the data flag
		//what is the iteration number
		//read the data from DataUtil and it returnd data as JSONObject
		
		//init the reporting for the test
		rep = ExtentManager.getReports();
		test = rep.createTest(context.getCurrentXmlTest().getName());
		test.log(Status.INFO,"Creating "+context.getCurrentXmlTest().getName());
		test.log(Status.INFO,"Data : "+data.toString());
		
		context.setAttribute("report",rep);
		context.setAttribute("test",test);
		if(!runmode.equals("Y")) {
			test.log(Status.SKIP,"Skipping as runmode is N");
			throw new SkipException("Skipping as runmode is N");
		}
		
		//init and share it with all the tests
		app = new ApplicationKeywords();   // 1 app object for entire test- All @Test
		app.setReport(test); // to initialize test in genericKeywords so we could log in reports for every step 
		app.openBrowser(browser);
		app.defaultLogin();  //bcz login info is same for every test, so we r keeping this method
		
		context.setAttribute("app", app);
	}
	@BeforeMethod(alwaysRun=true)
	public void beforeMethod(ITestContext context) {
		System.out.println("*************Before Method***************");
		test = (ExtentTest) context.getAttribute("test");
		String criticalFailure = (String) context.getAttribute("criticalFailure");
		if(criticalFailure!=null && criticalFailure.equals("Y")) {
			test.log(Status.SKIP,"Critical failure in previous tests");
			throw new SkipException("Critical failure in previous tests");//skip in testng
			
		}
		app = (ApplicationKeywords) context.getAttribute("app");
		rep = (ExtentReports) context.getAttribute("report");
		
		
	}
	@AfterTest(alwaysRun=true)
	public void quit(ITestContext context) {
		app = (ApplicationKeywords) context.getAttribute("app");
		if(app!=null)
			app.quit(); // we need to close the browser after every test, so hub will come to know that node is free to execute next test on Grid
		rep = (ExtentReports) context.getAttribute("report"); //getting rep object from context,otherwise it will come out as null if runmode for every dataset in N and nothing is running and testcase is skipping 
		if(rep!=null)
			rep.flush();
		
	}
	

}
