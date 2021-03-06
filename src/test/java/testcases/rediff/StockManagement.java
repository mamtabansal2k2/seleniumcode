package testcases.rediff;

import org.testng.annotations.Test;

import testbase.BaseTest;

public class StockManagement extends BaseTest {
	
	@Test
	public void addNewStock() {
		System.out.println("Adding stock");
	}
	@Test
	public void modifyStock() {
		System.out.println("Modifying stock");
	}
	@Test
	public void verifyStockPresent() {
		System.out.println("Verifying stock present");
	}
	@Test
	public void verifyStockQuantity() {
		System.out.println("Verifying stock quantity");
	}
	@Test
	public void verifyStockAvgBuyPrice() {
		System.out.println("Verifying stock average buy price");
	}
	@Test
	public void verifyTransactionHistory() {
		System.out.println("Verifying transcation history");
	}

}
