package keywords;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;

public class ApplicationKeywords extends ValidationKeywords{
	
	public ApplicationKeywords() {
		String path  = System.getProperty("user.dir")+"//src//test//resources//env.properties";
		prop = new Properties();
		envProp = new Properties();
		try {
			FileInputStream fs = new FileInputStream(path);
			prop.load(fs);
			String env=prop.getProperty("env")+".properties";
			path  = System.getProperty("user.dir")+"//src//test//resources//"+env;
			fs = new FileInputStream(path);
			envProp.load(fs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		softAssert = new SoftAssert();
		
	}
	
	public void login() {
		
	}
	
//	public void selectDateFromCalendar(String date) {
//		log("Selecting Date "+date);
//		
//		try {
//			Date currentDate = new Date();
//			Date dateToSel=new SimpleDateFormat("d-MM-yyyy").parse(date);
//			String day=new SimpleDateFormat("d").format(dateToSel);
//			String month=new SimpleDateFormat("MMMM").format(dateToSel);
//			String year=new SimpleDateFormat("yyyy").format(dateToSel);
//			String monthYearToBeSelected=month+" "+year;
//			String monthYearDisplayed=getElement("monthyear_css").getText();
//			
//			while(!monthYearToBeSelected.equals(monthYearDisplayed)) {
//				click("datebackButoon_xpath");
//				monthYearDisplayed=getElement("monthyear_css").getText();
//			}
//			driver.findElement(By.xpath("//td[text()='"+day+"']")).click();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	
	public void verifyStockAdded() {
		
	}
	public void defaultLogin() {
		navigate("url");

		
	}
	

	
	public void selectHub() {
		
	}
	
	

	
	public void setReport(ExtentTest test) {
		this.test=test;
	}


}
