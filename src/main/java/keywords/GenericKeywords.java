package keywords;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;

import reports.ExtentManager;

public class GenericKeywords {
	public WebDriver driver;
	public Properties prop;
	public Properties envProp;
	public ExtentTest test;
	public SoftAssert softAssert;
	
	
	
	public void openBrowser(String browser) {
		log("Opening The Browser "+ browser);
		if(prop.get("grid_run").equals("Y")) {
			
			DesiredCapabilities cap=new DesiredCapabilities();
			if(browser.equals("Mozilla")){
				
				cap.setBrowserName("firefox");
				cap.setJavascriptEnabled(true);
				cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);
				
			}else if(browser.equals("Chrome")){
				 cap.setBrowserName("chrome");
				 cap.setPlatform(org.openqa.selenium.Platform.WINDOWS);
			}
			
			try {
				// hit the hub
				driver = new RemoteWebDriver(new URL("http://localhost:4444"), cap);
			} catch (Exception e) {
			  e.printStackTrace();
			}
		}else {  //local machine
			if(browser.equals("Chrome")) {
				System.setProperty("webdriver.chrome.driver", "E:\\chromedriver.exe");
				ChromeOptions options = new ChromeOptions();
				options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
				//options.addArguments("--disable-infobars");
				options.addArguments("--remote-allow-origins=*");
				// to disable Save Password after login
				Map<String, Object> prefs = new HashMap<>();
				prefs.put("credentials_enable_service", false);
				prefs.put("profile.password_manager_enabled", false);
				options.setExperimentalOption("prefs", prefs);

				driver = new ChromeDriver(options);
				//To Maximize the window after chrome Launch
				driver.manage().window().maximize();
			}else if(browser.equals("Mozilla")) {
				System.setProperty("webdriver.gecko.driver", "E:\\Common\\drivers\\geckodriver.exe");
				driver = new FirefoxDriver();
			}else if(browser.equals("Edge")) {
				System.setProperty("webdriver.edge.driver", "E:\\Common\\msedgedriver.exe");
				driver = new EdgeDriver();
			}
		}
		// implicit wait
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		//driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
		
	}
	
	public void navigate(String urlKey) {
		log("Navigating to "+ urlKey);
		takeScreenShot();
		driver.get(envProp.getProperty(urlKey));
	}
	
	public void click(String locatorKey, String locator) {
		log("Clicking on "+locatorKey);
		takeScreenShot();
		getElement(locatorKey, locator).click();// not present, not visible
		System.out.println("Element Clicked: ");
	}
	
	public void clickOnData(String locator, String data) {
		log("Clicking on "+data);
		takeScreenShot();
		//String newlocator = ".//span[contains(text(),'oldString')]";
		String newlocator = locator.replace("oldString", data);
		System.out.println("oldlocator: "+locator);
		System.out.println("newlocator: "+newlocator);
		driver.findElement(By.xpath(newlocator)).click();
	}
	
//	public void selectDataclick(String locatorKey, String data) {
//		log("Clicking on "+locatorKey);
//		getElement(locatorKey).click();// not present, not visible
//	}
	
	public void type(String locatorKey, String locator, String data) {
		log("Typing in object "+locatorKey+" of locator"+locator+ " with Data "+ data);
		takeScreenShot();
		getElement(locatorKey, locator).sendKeys(data);
	}
//	public void clear(String locatorKey) {
//		log("Clearing text field "+ locatorKey);
//		getElement(locatorKey).clear();
//	}
	
//	public void clickEnterButton(String locatorKey) {
//		log("Clinking enter button");
//		getElement(locatorKey).sendKeys(Keys.ENTER);
//	}
	
	public void selectByVisibleText(String locatorKey,String locator, String data) {
		Select s = new Select(getElement(locatorKey, locator));
		s.selectByVisibleText(data);
	}
	
	
	public String getText(String locatorKey, String locator ) {
		return getElement(locatorKey,locator).getText();
	}
	
	// central functions to extract elements
	public WebElement getElement(String locatorKey, String locator) {
		//  check the presence
		if(!isElementPresent(locatorKey, locator)) {
			// report failure
			System.out.println("Element not present "+locatorKey);
		}
		//  check the visibility
		if(!isElementVisible(locatorKey, locator)) {
			// report failure
			System.out.println("Element not visible "+locatorKey);
		}
			
		WebElement e = driver.findElement(getLocator(locatorKey, locator));
		
		return e;
	}
	
	// true - present
	// false - not present
	public boolean isElementPresent(String locatorKey, String locator) {
		log("Checking presence of "+locatorKey);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(10000));
		System.out.println("waited for presence of Element");
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(getLocator(locatorKey, locator)));
			System.out.println("Element Present");
			
		}catch(Exception e) {
			System.out.println("Element not Present, Exception: "+e);
			return false;
		}
		return true;
	}
	
	// true - visible
	// false - not visible
	public boolean isElementVisible(String locatorKey, String locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		System.out.println("waited for visibility of Element");
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(getLocator(locatorKey, locator)));
			System.out.println("Element Visible");
		}catch(Exception e) {
			System.out.println("Element not visible, Exception: "+e);
			return false;
		}
		return true;
	}
	
	public By getLocator(String locatorKey, String locator) {
		By by=null;
		
		if(locatorKey.endsWith("_id"))
			by = By.id(prop.getProperty(locatorKey));
		else if(locatorKey.endsWith("_xpath"))
			by = By.xpath(locator);
		else if(locatorKey.endsWith("_css"))
			by = By.cssSelector(prop.getProperty(locatorKey));
		else if(locatorKey.endsWith("_name"))
			by = By.name(prop.getProperty(locatorKey));
		
		return by;
		
		
	}
	
	//reporting functions
	public void log(String msg) {
		System.out.println(msg);
		test.log(Status.INFO, msg);
		
	}
	
	public void reportFailure(String failureMsg, boolean stopOnFailure) {
		System.out.println(failureMsg);
		test.log(Status.FAIL, failureMsg);// failure in extent reports
		takeScreenShot();// put the screenshot in reports
		softAssert.fail(failureMsg);// failure in TestNG reports
		
		if(stopOnFailure) {
			Reporter.getCurrentTestResult().getTestContext().setAttribute("criticalFailure", "Y");
			assertAll();// report all the failures
		}
	}
	
	public void assertAll() {
		softAssert.assertAll();
	}
	
	public void takeScreenShot(){
		// fileName of the screenshot
		Date d=new Date();
		String screenshotFile=d.toString().replace(":", "_").replace(" ", "_")+".png";
		String filepath = ExtentManager.screenshotFolderPath+"//"+screenshotFile;
		// take screenshot
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			// get the dynamic folder name
			FileUtils.copyFile(srcFile, new File(ExtentManager.screenshotFolderPath+"//"+screenshotFile));
			//put screenshot file in reports
			//test.log(Status.INFO,"Screenshot-> "+ test.addScreenCaptureFromPath(ExtentManager.screenshotFolderPath+"//"+screenshotFile));
			test.log(Status.INFO, filepath, MediaEntityBuilder.createScreenCaptureFromPath(filepath).build());
			//MediaEntityBuilder.createScreenCaptureFromPath(ExtentManager.screenshotFolderPath+"//"+screenshotFile).build();			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void waitForPageToLoad(){
		JavascriptExecutor js = (JavascriptExecutor)driver;
		int i=0;
		// ajax status
		while(i!=10){
		String state = (String)js.executeScript("return document.readyState;");
		System.out.println(state);

		if(state.equals("complete"))
			break;
		else
			wait(2);

		i++;
		}
		// check for jquery status
		i=0;
		while(i!=10){
	
			Long d= (Long) js.executeScript("return jQuery.active;");
			System.out.println(d);
			if(d.longValue() == 0 )
			 	break;
			else
				 wait(2);
			 i++;
				
			}
		
		}
	
	public void wait(int time) {
		try {
			
			log("waiting for seconds "+time);
			Thread.sleep(time*1000);
			System.out.println("Waited for seconds:" +time);
		} catch (InterruptedException e) {
			System.out.println("Excetion: "+e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void acceptAlert(){
		test.log(Status.INFO, "Switching to alert");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.alertIsPresent());
		try{
			driver.switchTo().alert().accept();
			driver.switchTo().defaultContent();
			test.log(Status.INFO, "Alert accepted successfully");
		}catch(Exception e){
				reportFailure("Alert not found when mandatory",true);
		}
		
	}

	// finds the row number of the data
//	public int getRowNumWithCellData(String tableLocator, String data) {
//		
//		WebElement table = getElement(tableLocator);
//		List<WebElement> rows = table.findElements(By.tagName("tr"));
//		for(int rNum=0;rNum<rows.size();rNum++) {
//			WebElement row = rows.get(rNum);
//			List<WebElement> cells = row.findElements(By.tagName("td"));
//			for(int cNum=0;cNum<cells.size();cNum++) {
//				WebElement cell = cells.get(cNum);
//				System.out.println("Text "+ cell.getText());
//				if(!cell.getText().trim().equals(""))
//					if(data.startsWith(cell.getText()))
//						return(rNum+1);
//			}
//		}
//		
//		return -1; // data is not found
//	}
	

	public void quit() {
		driver.quit();
		
	}

	
	
	
	
	

}
