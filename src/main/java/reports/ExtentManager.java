package reports;

import java.io.File;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
	
	static ExtentReports reports;
	public static String screenshotFolderPath;  // declare outside bcz needs to be accessible in GenericKeywords
	
	public static ExtentReports getReports() {
		
		if(reports==null) {
			// init reports when first time getReports is called by any test 
			reports = new ExtentReports();
			Date d = new Date();
			String reportFolderPath = System.getProperty("user.dir")+"//reports//"+d.toString().replaceAll(":","_");
			screenshotFolderPath = reportFolderPath+"//screenshots";
			File f = new File(screenshotFolderPath);
			f.mkdirs();
			
			ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportFolderPath);
			sparkReporter.config().setReportName("Production Regression Testing");
			sparkReporter.config().setDocumentTitle("Automation Reports");
			sparkReporter.config().setTheme(Theme.DARK);
			sparkReporter.config().setEncoding("utf-8");
			reports.attachReporter(sparkReporter);
			
		}
		return reports;
	}

}
