package com.vtg.auto.utilities;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.vtg.auto.reusablecomponents.Browser;
import com.vtg.auto.reusablecomponents.GlobalVariables;


public class ReportUtils extends BaseTest {

	public String screenShotPath;

	/** Log Starts **/
	public void log(String argLogDetails, String methodName) {
		fnCommonForLog(Status.INFO, argLogDetails); // new way
	}

	public void addInfo(String argLogDetails, String methodName) {
		fnCommonForLog(Status.INFO, argLogDetails);
	}

	/** Log Ends **/

	/** passTest Starts **/
	public void passTest(String stepDetails) { // new way
		// test.log(Status.PASS, stepDetails);
		fnCommonForLog(Status.PASS, stepDetails);
	}

	public void addStepDetails(String stepDetails) {
		fnCommonForLog(Status.PASS, stepDetails);
	}

	/** passTest Ends **/

	/**
	 * failTest Starts
	 * 
	 * @throws IOException
	 **/
	public void failTest(String methodName, String details) throws IOException {
		takeScreenShotOnFailure(methodName, details);

	}

	public void addResult(String methodName, boolean result, String details) throws IOException {
		if (result == false) {
			takeScreenShotOnFailure(methodName, details);
		}
	}

	/** failTest Ends **/

	/**
	 * Report Log Status STARTS
	 * 
	 * @throws IOException
	 **/
	public void addInfoOnValidations(String methodName, boolean result, String passMessage, String failureMessage)
			throws IOException {
		if (result == true) {
			fnCommonForLog(Status.PASS, passMessage);
		} else {
			takeScreenShotOnFailure(methodName, failureMessage);
		}
	}

	public void reportLogStatus(String methodName, boolean result, String passMessage, String failureMessage)
			throws IOException {
		if (result == true) {
			// test.log(Status.PASS, passMessage);
			fnCommonForLog(Status.PASS, passMessage);
		} else {
			takeScreenShotOnFailure(methodName, failureMessage);
		}
	}

	/** Report Log Status ENDS **/

	public void fnCommonForLog(Status argLogStatus, String stepinfo) {
		if (GlobalVariables.isGridMode) {

			gridChildTest.get().log(argLogStatus, stepinfo);
		} else {

			// configReport();
			childTest.log(argLogStatus, stepinfo);
		}
	}

	public void takeScreenShotOnFailure(String methodName, String details) throws IOException {
		takeScreenShot(methodName,details);
		if (GlobalVariables.isGridMode) {
			//gridChildTest.get().log(Status.FAIL, methodName);
			gridChildTest.get().log(Status.FAIL, details);
		} else {
			childTest.log(Status.FAIL, methodName);
			childTest.log(Status.FAIL, details);
		}
		GlobalVariables.errorTrace = null;
	}
	public void takeScreenShot(String methodName, String details) throws IOException {
		String base64data = GlobalVariables.configData.get("base64");
		//System.out.println("Base 64::"+base64data);
		if(base64data!=null && !base64data.isEmpty()) {
		if(base64data.equalsIgnoreCase("yes")) {
		takeScreenShotBASE64(methodName + System.currentTimeMillis());
		}
		else if(base64data.equalsIgnoreCase("no")){
			takeScreenShotImage(methodName + System.currentTimeMillis());
		}
		}else {
			takeScreenShotImage(methodName + System.currentTimeMillis());
		}
		
	}
	private String takeScreenShotImage(String screenName) {
		WebDriver driver = Browser.getInstance().getDriver();
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			screenShotPath = GlobalVariables.configData.get("ReportPath") + screenName + ".png";
			if (GlobalVariables.isGridMode) {				
				gridChildTest.get().info(screenName,
						MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath).build());
			} else {
				//System.out.println("NO GRID - takeScreenShot");
				childTest.info(screenName, MediaEntityBuilder.createScreenCaptureFromPath(screenName + ".png").build());
			}
			FileUtils.copyFile(scrFile, new File(screenShotPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return screenShotPath;
	}
	private String takeScreenShotBASE64(String screenName) {
		WebDriver driver = Browser.getInstance().getDriver();
		String scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
		screenShotPath = GlobalVariables.configData.get("ReportPath") + screenName + ".png";
		
		if (GlobalVariables.isGridMode) {
			gridChildTest.get().info(screenName,
					MediaEntityBuilder.createScreenCaptureFromBase64String(scrFile).build());
		} else {
			
			childTest.info(screenName, MediaEntityBuilder.createScreenCaptureFromBase64String(scrFile).build());
		}
		
		return screenShotPath;
	}

	
}