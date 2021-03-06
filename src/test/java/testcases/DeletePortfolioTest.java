package testcases;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import keywords.ApplicationKeywords;

public class DeletePortfolioTest {
	
	@Test
	public void deletePortfolioTest() throws InterruptedException {
	
	// as soon as we create the object app of ApplicationKeywords class,default constructor is called and prop object is initialized to use	
	
		ApplicationKeywords app = new ApplicationKeywords(); //initialize applicationKeyword class object bcz it inherits other classes and can use them so we don't need to initialize object of every class
		app.openBrowser("Mozilla");
		app.navigate("url");
		app.type("username_css","ashishthakur1983");
		app.type("password_xpath", "pass@1234");
		app.click("login_submit_id");
		Select s = new Select(app.getElement("selectPortfolio_css"));
		s.selectByVisibleText("Ashi_8");
		app.click("deletePortfolio_xpath");
		
		app.driver.switchTo().alert().accept();
		//app.driver.switchTo().alert().dismiss();
		//app.driver.switchTo().defaultContent();
		Thread.sleep(5000);
		Select sel = new Select(app.getElement("selectPortfolio_css"));
		List<WebElement> list = sel.getOptions();
		System.out.println("list size= "+list.size());
		boolean result = false;
		for(int i=0;i<list.size();i++) {
			//System.out.println(list.get(i).getText());
			if(list.get(i).getText().equals("Ashi_8")) {
				result = false;
				break;
			}
			result= true;
			//System.out.println("Element deleted");
		}
		if(result)
			System.out.println("Element deleted");
		else
			System.out.println("Element not deleted");
		
 	}

}
