package testcases.hc;

import org.json.simple.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import driver.DriverScript;
import testbase.BaseTest;
import util.Xls_Reader;


public class CarePlan extends BaseTest{
	
	@Test
	public void createCarePlan(ITestContext context) {
		ds.log("Starting to create a care Plan");
		Xls_Reader xls = new Xls_Reader(System.getProperty("user.dir")+"\\src\\test\\resources\\testcases\\Testcases.xlsx");
		//DriverScript ds = new DriverScript();
		ds.executeTest(xls, "TestCases", "CarePlan");
	}
	
	
	@Test
	public void editCarePlan(ITestContext context) {
		
	}
	
	@Test
	public void viewCarePlan(ITestContext context) {
		
	}

}
