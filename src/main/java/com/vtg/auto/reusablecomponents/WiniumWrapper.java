package com.vtg.auto.reusablecomponents;

import java.io.IOException;
import java.net.URL;

import com.vtg.auto.utilities.BaseTest;

public class WiniumWrapper extends BaseTest {

	/*public static WiniumDriver getWiniumDriver() throws IOException {
		String methodName = "getWiniumDriver";
		WiniumDriver winiumDriver = null;
		try {
			startWiniumServer();
			DesktopOptions options = new DesktopOptions();
			options.setApplicationPath(GlobalVariables.configData.get("WiniumAppPath"));
			winiumDriver = new WiniumDriver(new URL("http://localhost:9999"), options);
		} catch (Exception e) {
			e.printStackTrace();
			testResult = "fail";
			reportUtils.addInfoOnValidations(methodName, false, "Success_Not_Applicable", e.getMessage());
		}
		return winiumDriver;
	}

	public static boolean startWiniumServer() throws IOException {
		String methodName = "startWiniumServer";
		boolean winiumStartStatus = false;
		try {
			Process objWiniumDesktopProcess = Runtime.getRuntime()
					.exec(GlobalVariables.configData.get("WiniumDesktopDriver"));
			winiumStartStatus = true;
		} catch (Exception e) {
			e.printStackTrace();
			testResult = "fail";
			reportUtils.addInfoOnValidations(methodName, false, "Success_Not_Applicable", e.getMessage());
		}
		return winiumStartStatus;
	}

	public static boolean stopWiniumServer() throws IOException {
		String methodName = "stopWiniumServer";
		boolean winiumStopStatus = false;
		try {
			Process objProcess = Runtime.getRuntime().exec("taskkill /F /IM Winium.Desktop.Driver.exe");
			winiumStopStatus = true;
		} catch (Exception e) {
			e.printStackTrace();
			testResult = "fail";
			reportUtils.addInfoOnValidations(methodName, false, "Success_Not_Applicable", e.getMessage());
		}
		return winiumStopStatus;
	}*/

}