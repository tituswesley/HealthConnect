package testcases.hc;

import org.json.simple.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import testbase.BaseTest;


public class StockManagement extends BaseTest{
	// Adds a fresh stock in the table
	@Test
	public void addNewStock(ITestContext context) {
		
		

	}
	
	// sell or buy existing stock
	@Parameters ({"action"})
	@Test
	public void modifyStock(String action,ITestContext context) {
		
	}
	
	// checks if stock is present in the table
	@Test
	public void verifyStockPresent() {
		
		
	}
	
	// checks the stock quantity
	@Parameters ({"action"})
	@Test
	public void verifyStockQuantity(String action, ITestContext context) {
		
	}
	
	@Test
	public void verifyStockAvgBuyPrice() {
		
	}
	
	// verifies the transaction history
	@Parameters ({"action"})
	@Test
	public void verifyTransactionHistory(String action) {
		
	}
	

}
