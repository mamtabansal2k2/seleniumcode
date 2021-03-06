package runner;

import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;

public class Runner {

	public static void main(String[] args) {
		
		TestNGRunner testNG = new TestNGRunner(1);//number in constructor shows how many suite will run parallel
	/*	testNG.createSuite("Stock Management", false);
		testNG.addListener("listener.MyTestNGListener");
		testNG.addTest("Add new stock Test");
		testNG.addTestParameter("action","addstock");
		List<String> includedMethods = new ArrayList<String>();
		includedMethods.add("selectPortfolio");
		testNG.addTestClass("testcases.rediff.PortfolioManagement", includedMethods);
		includedMethods = new ArrayList<String>();
		includedMethods.add("addNewStock");
		includedMethods.add("verifyStockPresent");
		includedMethods.add("verifyStockQuantity");
		includedMethods.add("verifyTransactionHistory");
		testNG.addTestClass("testcases.rediff.StockManagement", includedMethods);*/
		
		testNG.createSuite("Portfolio Management", false);
		testNG.addTest("Create Portfolio Test");
		List<String> includedMethods = new ArrayList<String>();
		includedMethods.add("createPortfolio");
		testNG.addTestClass("testcases.rediff.PortfolioManagement", includedMethods);
		
		testNG.run();
	}

}
