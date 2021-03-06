package listener;

import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class MyTestNGListener implements ITestListener{
	
	public void onTestSuccess(ITestResult result) {
		System.out.println("*************************Test Success: "+result.getName());
		ExtentTest test= (ExtentTest) result.getTestContext().getAttribute("test");  // need test object to log into the reports
		test.log(Status.PASS,result.getName()+" - Test Passed");
	}
	public void onTestFailure(ITestResult result) {
		System.out.println("*************************Test Failure: "+result.getName());
		ExtentTest test= (ExtentTest) result.getTestContext().getAttribute("test");  // need test object to log into the reports
		//Reporter.getCurrentTestResult().getTestContext().setAttribute("criticalFailure","Y");
		test.log(Status.FAIL,result.getThrowable().getMessage()+" - Test Failed");
	}
	public void onTestSkipped(ITestResult result) {
		System.out.println("*************************Test Skipped: "+result.getName());
		ExtentTest test= (ExtentTest) result.getTestContext().getAttribute("test");  // need test object to log into the reports
		test.log(Status.SKIP,result.getName()+" - Test Skipped");
	}
}
