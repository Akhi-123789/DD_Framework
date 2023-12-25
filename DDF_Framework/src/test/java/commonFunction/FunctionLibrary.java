package commonFunction;

import java.time.Duration;

import org.openqa.selenium.By;
import org.testng.Reporter;

import config.AppUtil;

public class FunctionLibrary extends AppUtil
{
	// method for login
 public static boolean adminLogin(String user,String pass)throws Throwable
 {
	 driver.get(conpro.getProperty("Url"));
	 driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	 driver.findElement(By.xpath(conpro.getProperty("ObjReset"))).click();
	 driver.findElement(By.xpath(conpro.getProperty("ObjUser"))).sendKeys(user);
	 driver.findElement(By.xpath(conpro.getProperty("ObjPass"))).sendKeys(pass);
	 driver.findElement(By.xpath(conpro.getProperty("ObjLogin"))).click();
	 Thread.sleep(4000);
	 
	 String Expected = "dashboard";
	 String Actual = driver.getCurrentUrl();
	 if(Actual.contains(Expected))
	 {
		 Reporter.log("Login Success::"+Expected+"   "+Actual,true);
		 //click logout
		 driver.findElement(By.xpath(conpro.getProperty("ObjLogout"))).click();
		 return true;
		 
	 }else
	 {
		 //capture error msg
		 String errormessage = driver.findElement(By.xpath(conpro.getProperty("ObjErrormessage"))).getText();
		 Reporter.log(errormessage+"  "+Expected+"   "+Actual,true);
		 driver.findElement(By.xpath(conpro.getProperty("ObjOkbtn"))).click();
		 return false;
	 }
	
	
 }
}
