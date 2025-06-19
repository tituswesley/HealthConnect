package testbase;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import driver.DriverScript;
import keywords.ApplicationKeywords;
import reports.ExtentManager;
import runner.DataUtil;
// acceptable failure, critical failure, unexpected
import runner.ReadingXLS;

// how to configure and run on grid - 4 alpha 6  3.141.59
// how to manage data from xls or json
// how to run this with JSON config
// Running from GIT and Jenkins

public class BaseTest {
	public DriverScript ds;
	
	public ExtentReports rep;
	public ExtentTest test;
	
	
	@BeforeTest(alwaysRun = true)
	public void beforeTest(ITestContext context) throws NumberFormatException, FileNotFoundException, IOException, ParseException {
		System.out.println("----------Before Test---------");
		String datafilpath = context.getCurrentXmlTest().getParameter("datafilpath");
		String dataFlag = context.getCurrentXmlTest().getParameter("dataflag");
		String iteration = context.getCurrentXmlTest().getParameter("iteration");
		String sheetName = context.getCurrentXmlTest().getParameter("suitename");
		// suitename(sheetname)
		System.out.println("datafilpath: "+datafilpath);
		System.out.println("dataFlag: "+dataFlag);
		System.out.println("iteration: "+iteration);
		System.out.println("sheetName: "+sheetName);
		// reading data from JSON
		//JSONObject data = new DataUtil().getTestData(datafilpath, dataFlag, Integer.parseInt(iteration));
		//JSONObject data = new ReadingXLS().getTestData(sheetName, dataFlag, (Integer.parseInt(iteration)+1), datafilpath);
		JSONObject data = new ReadingXLS().getTestDataNew(sheetName, dataFlag, (Integer.parseInt(iteration)+1), datafilpath);
		//JSONObject data = from xls
		context.setAttribute("data", data);
		String runmode = (String)data.get("runmode");
		System.out.println("runmode: "+runmode);
		
		
		// what is the path to data json / xls
		// what is the data flag
		// what is the iteration number
		// read the data and keep it in a map
		
		
		// init the reporting for the test
		rep = ExtentManager.getReports();
		test =rep.createTest(context.getCurrentXmlTest().getName());
		test.log(Status.INFO, "Starting Test "+context.getCurrentXmlTest().getName());
		test.log(Status.INFO, "Data "+data.toString());

		context.setAttribute("report", rep);
		context.setAttribute("test", test);
        if(!runmode.equals("Y")) {
        	test.log(Status.SKIP, "Skpping as Data Runmode is N");
        	throw new SkipException("Skpping as Data Runmode is N");
		}
		
		
		
		
		// init and share it with all tests
        ds = new DriverScript(); // 1 app keyword object for entire test -All @Test
		ds.setReport(test);
        ds.defaultLogin("Chrome");
        
        ds.setTestData(data);
		context.setAttribute("driver", ds);
		
	}
	
	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(ITestContext context) {
		System.out.println("****Before Method****");
	    test = (ExtentTest)context.getAttribute("test");

		String criticalFailure = (String)context.getAttribute("criticalFailure");
		if(criticalFailure != null && criticalFailure.equals("Y")) {
			test.log(Status.SKIP, "Critical Failure in Prevoius Tests");
			throw new SkipException("Critical Failure in Prevoius Tests");// skip in testNG
		}
	    ds = (DriverScript)context.getAttribute("driver");
	    rep = (ExtentReports)context.getAttribute("report");

	}
	
	@AfterTest(alwaysRun = true)
	public void quit(ITestContext context) {
		ds = (DriverScript)context.getAttribute("driver");
		if(ds!=null)
			ds.quit();
		
		rep = (ExtentReports)context.getAttribute("report");

		if(rep !=null)
			rep.flush();
	}
	

}
