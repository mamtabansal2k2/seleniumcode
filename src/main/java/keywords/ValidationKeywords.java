package keywords;

import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class ValidationKeywords extends GenericKeywords {
	
	public void validateTitle() {
		
		log("Validating Title");
		
		/*String actualTitle = "Rediff";
		String expectedTitle = driver.getTitle();
		if(actualTitle.equals(expectedTitle))
			System.out.println("Title matches");
		else
			System.out.println(actualTitle+" is not matching actual title that is "+expectedTitle);*/
		
	}
	public void validateText() {
		
	}
	public void validateElementPresent(String locator) {
		boolean result = isElementPresent(locator);
		//reportFailure("Critical Failure-Element not found "+locator,true);
	}
	public void validateLogin(String locator) {
		boolean result = isElementPresent(locator);
		if(result)
			System.out.println("Login successful");
		else {
			System.out.println("Login failed");
			Assert.fail();
		}
			
	}
	public void validateSelectedValueInDropDown(String locatorKey,String option) {
		Select s = new Select(getElement(locatorKey));
		String text = s.getFirstSelectedOption().getText();
		System.out.println("Text= "+text+" Option= "+option);
		if(!text.equals(option))
			reportFailure("Option "+option+" not present in Drop down "+locatorKey,true);
	}
	public void validateSelectedValueNotInDropDown(String locatorKey,String option) {
		Select s = new Select(getElement(locatorKey));
		String text = s.getFirstSelectedOption().getText();
		System.out.println("Text= "+text+" Option= "+option);
		if(text.equals(option))
			reportFailure("Option "+option+"  present in Drop down "+locatorKey,true);
	}
	
	
}
