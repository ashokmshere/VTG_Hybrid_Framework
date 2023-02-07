package com.vtg.auto.reusablecomponents;

import java.io.IOException;
import java.net.URL;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.vtg.auto.utilities.BaseTest;

//import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class MobileWrapper extends BaseTest {

	String mobAlerttext;
	static AndroidDriver appdriver;
	static String appiumJSPath = "C:\\Program Files (x86)\\Appium\\resources\\app\\node_modules\\appium\\build\\lib\\appium.js";

	public static AndroidDriver mobileLaunchApp() throws IOException {
		String methodName = "mobileLaunchApp";

		String strMobileDeviceID, strMobileOSVersion, strMobilePlatformName, strMobileAppPath, strMobileAppiumServer,
				strMobileAppPackage, strMobileAppActivity;

		strMobileDeviceID = GlobalVariables.configData.get("MobileDeviceID");
		strMobilePlatformName = GlobalVariables.configData.get("MobilePlatformName");
		strMobileOSVersion = GlobalVariables.configData.get("MobileOSVersion");
		strMobileAppPath = GlobalVariables.configData.get("MobileAppPath");
		strMobileAppiumServer = GlobalVariables.configData.get("MobileAppiumServer");
		strMobileAppPackage = GlobalVariables.configData.get("MobileAppPackage");
		strMobileAppActivity = GlobalVariables.configData.get("MobileAppActivity");

		try {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("deviceName", strMobileDeviceID);
			capabilities.setCapability("platformName", strMobilePlatformName);
			capabilities.setCapability("version", strMobileOSVersion);
			// capabilities.setCapability("app", strMobileAppPath);
			capabilities.setCapability("newCommandTimeout", 120);
			// capabilities.setCapability("noReset","true");
			capabilities.setCapability("appPackage", strMobileAppPackage);
			capabilities.setCapability("appActivity", strMobileAppActivity);

			appdriver = new AndroidDriver(new URL("http://" + strMobileAppiumServer + "/wd/hub"),
					capabilities);
			Thread.sleep(5000);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return appdriver;
	}

	public static void launchApp() {
		try {
			appdriver.launchApp();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void closeApp() {
		try {
			appdriver.closeApp();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void installApp(String appName) {
		try {
			appdriver.installApp(appName);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void unInstallApp(String appName) {
		try {
			appdriver.removeApp(appName);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void hideKeyBoard() {
		try {
			appdriver.hideKeyboard();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	JavascriptExecutor mobJs = (JavascriptExecutor) appdriver;

	public void scrollup() {
		mobJs.executeScript("window.scrollTo(Math.max(document.documentElement.scrollHeight,"
				+ "document.body.scrollHeight,document.documentElement.clientHeight,0));");
	}

	public void scrolldown() {
		mobJs.executeScript("window.scrollBy(0,500)", "");
	}
}