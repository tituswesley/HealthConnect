package driver;
import org.json.simple.JSONObject;

import com.aventstack.extentreports.ExtentTest;

import keywords.ApplicationKeywords;
import util.Xls_Reader;

public class DriverScript {
	ApplicationKeywords app;
	JSONObject testData;

	public DriverScript () {
		app = new ApplicationKeywords();
	}
	
	public static void main (String [] args) {
		Xls_Reader xls = new Xls_Reader(System.getProperty("user.dir")+"\\src\\test\\resources\\testcases\\Testcases.xlsx");
		DriverScript ds = new DriverScript();
		ds.executeTest(xls, "TestCases", "CarePlan");
	}
	
public void executeTest(Xls_Reader xls, String sheet, String testName) {						
		int rows = xls.getRowCount(sheet);
		System.out.println("no of rows: "+rows);
		for(int rNum=2;rNum<rows;rNum++) {
			String tcid = xls.getCellData(sheet, "TCID", rNum);
			if(tcid.equalsIgnoreCase(testName)) {
				String keyword = xls.getCellData(sheet, "Keyword", rNum);
				String object = xls.getCellData(sheet, "Object", rNum);
				String locator = xls.getCellData(sheet, "Locator", rNum);
				String dataKey = xls.getCellData(sheet, "DataKey", rNum);
				System.out.println("locator: "+locator);
				System.out.println("dataKey: "+dataKey);
				String data = (String)testData.get(dataKey);
				//String data = xls.getCellData(sheet, "Data", rNum);	
				System.out.println("data: "+data);
				//String data = (String)testData.get(dataKey);
				System.out.println(tcid+"----"+keyword+"----"+object+"----"+locator+"----"+data);	
				if(keyword.equals("click")) 
					app.click(object, locator);
				if(keyword.equals("clickOnData")) 
					app.clickOnData(locator, data);
//				if(keyword.equals("clear")) 
//					app.clear(object);
				if(keyword.equals("type")) 
					app.type(object, locator, data);
				if(keyword.equals("waitForPageToLoad")) 
					app.waitForPageToLoad();	
				if(keyword.equals("wait")) {
					System.out.println("Inside wait check function");
					System.out.println("dataKey string: "+dataKey);
					dataKey = dataKey.replace(".0", "");
					System.out.println("dataKey seconds: "+dataKey);
					app.wait(Integer.parseInt(dataKey));
						
					}
					
			}
			
		}
	}


	
	public void executeTestold(Xls_Reader xls, String sheet, String testName) {				
		app = new ApplicationKeywords();
		app.openBrowser("Chrome");
		app.defaultLogin();
		int rows = xls.getRowCount(sheet);
		for(int rNum=2;rNum<rows;rNum++) {
			String tcid = xls.getCellData(sheet, "TCID", rNum);
			if(tcid.equalsIgnoreCase(testName)) {
				String keyword = xls.getCellData(sheet, "Keyword", rNum);
				String object = xls.getCellData(sheet, "Object", rNum);
				String locator = xls.getCellData(sheet, "Locator", rNum);
				String data = xls.getCellData(sheet, "Data", rNum);	
				System.out.println("data: "+data);
				//String data = (String)testData.get(dataKey);
				System.out.println(tcid+"----"+keyword+"----"+object+"----"+locator+"----"+data);	
				if(keyword.equals("click")) 
					app.click(object, locator);
//				if(keyword.equals("clear")) 
//					app.clear(object);
				if(keyword.equals("type")) 
					app.type(object, locator, data);
				if(keyword.equals("waitForPageToLoad")) 
					app.waitForPageToLoad();	
				if(keyword.equals("wait")) 
					app.wait(Integer.parseInt(data));	
			}
			
		}
	}

	public void setReport(ExtentTest test) {
		app.setReport(test);
		
	}

	public void defaultLogin(String browserName) {
		app.openBrowser(browserName);
		app.navigate("url");
		
		
		
	}

	public void quit() {
		if(app!=null)
			app.quit();
		
	}

	public void setTestData(JSONObject data) {
		
		testData = data;
	}

	public void log(String logMessage) {
		app.log(logMessage);
		
		
	}
}
