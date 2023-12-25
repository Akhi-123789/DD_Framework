package driverFactory;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunction.FunctionLibrary;
import config.AppUtil;
import utilities.ExcelFileUtil;

public class AppTest extends AppUtil
{
String inputpath = "./FileInput/LoginData.xlsx";
String outputpath = "./FileOutput/DataDrivenResults.xlsx";
ExtentReports report;
ExtentTest logger;


@Test
public void startTest()throws Throwable
{
	report = new ExtentReports("./target/Report/Login.html");
	boolean res =false;
	//create object for excel file util
	ExcelFileUtil xl = new ExcelFileUtil(inputpath);
	int rc = xl.rowCount("Login");
	Reporter.log("No of Rows::"+rc,true);
	for(int i=1;i<=rc;i++)
	{
		logger = report.startTest("Validate Login");
		
		String username = xl.getCellData("Login", i, 0);
		String password = xl.getCellData("Login", i, 1);
		//call login method
		res = FunctionLibrary.adminLogin(username, password);
		if(res)
		{
			logger.log(LogStatus.PASS, "Login Success");
			//if res is true write as login success into results cell
			xl.setCellData("Login", i, 2, "Login Success", outputpath);
			//write a pass into status cell
			xl.setCellData("Login", i, 3, "Pass", outputpath);
		}else
		{
			//take screen shot and store
			File screen = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screen, new File("./Screenshot/Iteration/"+i+"Loginpage.png"));
			logger.log(LogStatus.FAIL, "Login Fail");
			//if res is false write as login success into results cell
			xl.setCellData("Login", i, 2, "Login Fail", outputpath);
			//write a fail into status cell
			xl.setCellData("Login", i, 3, "Fail", outputpath);
			
		}
		report.endTest(logger);
		report.flush();
		
	}
}


}
