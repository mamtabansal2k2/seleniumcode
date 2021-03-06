package keywords;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import reports.ExtentManager;

public class GenericKeywords {
	public WebDriver driver;
	public Properties prop;  //declaring it globally so we can use it everywhere
	public Properties envProp;
	public ExtentTest test;  // declared to access test object in GenericKeywords to log inside the reports
	public SoftAssert softAssert;  // initialize the object in applicationKeywords constructor
	
	public void openBrowser(String browser) {
		
		test.log(Status.INFO,"Opening browser  "+browser);
		if(browser.equals("Chrome")) {
			System.setProperty("webdriver.chrome.driver","c:\\Mamta\\Drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		}else if(browser.equals("Mozilla")) {
			System.setProperty("webdriver.gecko.driver","C:\\Mamta\\Drivers\\geckodriver26version\\geckodriver.exe");
			driver = new FirefoxDriver();
		}else if(browser.equals("Edge")) {
			System.setProperty("webdriver.edge.driver","C:\\Mamta\\Drivers\\IEDriver\\IEDriverServer.exe");
			driver = new FirefoxDriver();
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		
	}
	public void navigate(String urlKey) {  
		
		test.log(Status.INFO,"Navigating to "+urlKey);
		driver.get(envProp.getProperty(urlKey));
		
	}
	
	public void type(String locatorKey,String data) {
		test.log(Status.INFO,"Typing in "+locatorKey+". Data  "+data);
		getElement(locatorKey).sendKeys(data);  // what if element is not present or not visible
	} //replace all driver.find element commands with getElement() method
	
	public void click(String locatorKey) {
		test.log(Status.INFO,"Clicking on  "+locatorKey);
		getElement(locatorKey).click();
	}
	public void select(String locator,String data ) {
		
	}
	public void getText(String locator) {
		
	}
	
	// Central function to extract elements
	
	public WebElement getElement(String locatorKey) {
		//check presence
		if(!isElementPresent(locatorKey)) {
			//report failure , will be a critical failure ur testcase will stop here
			System.out.println("Element not present");
		}
		//check visible
		if(!isElementVisible(locatorKey)) {
			//report failure , will be a critical failure ur testcase will stop here
			System.out.println("Element not visible");
		}
		
		WebElement e = null;
		
		e = driver.findElement(getLocator(locatorKey));

		return e;
	}
	//true - present
	//false - not present
	public boolean isElementPresent(String locatorKey) {
		
		System.out.println("Checking presence of "+locatorKey);
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		try { 
			
				wait.until(ExpectedConditions.presenceOfElementLocated(getLocator(locatorKey)));
			
		}catch(Exception e) {
			return false;
		}
		return true;
	}
	//true - present
	//false - not present
	public boolean isElementVisible(String locatorKey) {
		System.out.println("Checking visibility of "+locatorKey);
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		try { 
			
				wait.until(ExpectedConditions.visibilityOfElementLocated(getLocator(locatorKey)));
			
		}catch(Exception e) {
			return false;
		}
		return true;
	}
	// to avoid same if-else statements in all above methods to locate element by different criteria like id,name,css,xpath we can use below method
	
	public By getLocator(String locatorKey) {
	   By by = null ;
	   
	   if(locatorKey.endsWith("_id"))
			by = By.id(prop.getProperty(locatorKey));
		else if(locatorKey.endsWith("_xpath"))
			by = By.xpath(prop.getProperty(locatorKey));
		else if(locatorKey.endsWith("_css"))
			by = By.cssSelector(prop.getProperty(locatorKey));
		else if(locatorKey.endsWith("_name"))
			by = By.name(prop.getProperty(locatorKey));

	   return by;
	}
	
	public void clear(String locatorKey) {
		getElement(locatorKey).clear();
	}
	
	public void selectByVisibleText(String locatorKey,String option) {
		Select s = new Select(getElement(locatorKey));
		s.selectByVisibleText(option);
	}
	
	public void acceptAlert() {
		driver.switchTo().alert().accept();
		//driver.switchTo().defaultContent();
		waitForPageToLoad();
	}
	
	public void waitForPageToLoad() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		// ajax status
		int i=0;
		while(i!=10) {
			
			String state = (String) js.executeScript("return document.readyState;" );
			System.out.println("State=  "+state);
			
			if(state.equals("complete"))
				break;
			else
				wait(2);
			
			i++;
			
		}
		// jquery status
		/*i=0;
		while(i!=10) {
			
			Long d = (Long) js.executeScript("return jQuery.active;" );
			System.out.println("Jquery=  "+d);
			
			if(d.longValue()==0)
				break;
			else
				wait(2);
			
			i++;
		}*/
	}
	public void wait(int time) {
		try {
			Thread.sleep(time*1000);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void quit() {
		driver.quit();
	}
	
	// reporting functions
	
	public void log(String msg) {
		System.out.println(msg);
		test.log(Status.INFO,msg);
	}
	public void reportFailure(String failureMsg,boolean stopOnFailure) {
		System.out.println(failureMsg);
		test.log(Status.FAIL,failureMsg);  //failure in extent reports
		takeScreenShot(); // put screenshot in reports
		softAssert.fail(failureMsg);  //failure in testng
		if(stopOnFailure){//this condition alone will not stop the testcase on critical failure,so we r using Reporter in assertAll() method to skip next testcases in series of execution
			Reporter.getCurrentTestResult().getTestContext().setAttribute("criticalFailure","Y");
			assertAll();
		}
	}
	public void assertAll() {
		//Reporter.getCurrentTestResult().getTestContext().setAttribute("criticalFailure","Y");
		softAssert.assertAll();
	}
	public void takeScreenShot(){
		// fileName of the screenshot
		Date d=new Date();
		String screenshotFile=d.toString().replace(":", "_").replace(" ", "_")+".png";
		// take screenshot
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			// get the dynamic folder name
			FileUtils.copyFile(srcFile, new File(ExtentManager.screenshotFolderPath+"//"+screenshotFile));
			//put screenshot file in reports
			test.log(Status.INFO,"Screenshot-> "+ test.addScreenCaptureFromPath(ExtentManager.screenshotFolderPath+"//"+screenshotFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
