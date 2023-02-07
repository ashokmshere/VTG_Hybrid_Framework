package com.vtg.auto.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.vtg.auto.reusablecomponents.GlobalVariables;

import java.util.Properties;

public class ExtentTestManager {
	public static Properties prop = new Properties();

	public static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();

	public static ExtentReports extent = ExtentManager.getInstance();

	public synchronized static ExtentTest getTest() {
		return extentTest.get();
	}

	public synchronized static ExtentTest createTest(String name, String description, String deviceId) {
		ExtentTest test = extent.createTest(name, description);

		if (deviceId != null && !deviceId.isEmpty())
			//test.assignCategory(deviceId);
		extentTest.set(test);
		return getTest();
	}

	public synchronized static ExtentTest createTest(String name, String description) {
		return createTest(name, description, "testid");
	//	return createTest(name, description, "");
	}

	public synchronized static ExtentTest createTest(String name) {
		return createTest(name, GlobalVariables.configData.get("Application Name"));
	}
}
