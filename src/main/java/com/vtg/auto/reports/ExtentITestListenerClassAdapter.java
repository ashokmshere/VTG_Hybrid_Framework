package com.vtg.auto.reports;

//import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.vtg.auto.reusablecomponents.GlobalVariables;


public class ExtentITestListenerClassAdapter extends TestListenerAdapter {
	public ExtentSparkReporter htmlReporter;
	public ExtentReports extent;
	public ExtentTest logger;

	public void onStart(ITestContext testContext) {
		if (!System.getProperty("os.name").contains("Linux")) {
			htmlReporter = new ExtentSparkReporter(
					GlobalVariables.configData.get("ReportPath") + GlobalVariables.configData.get("Project Name")
							+ " - " + "AutomationTestReport" + System.currentTimeMillis() + ".html");

		} else {
			htmlReporter = new ExtentSparkReporter(GlobalVariables.configData.get("ReportPath")
					+ GlobalVariables.configData.get("Project Name") + " - " + "AutomationTestReport" + ".html");
		}
		extent = new ExtentReports();
		// extent.setSystemInfo("Browser", "Chrome");
		extent.attachReporter(htmlReporter);
		htmlReporter.config().setDocumentTitle(GlobalVariables.configData.get("Project Name")); // Title of report
		htmlReporter.config()
				.setReportName("Release - " + GlobalVariables.configData.get("Release") + " Build - "
						+ GlobalVariables.configData.get("Release") + " Environment - "
						+ GlobalVariables.configData.get("Environment")); // name of the report
		htmlReporter.config().setTimelineEnabled(true);
		htmlReporter.config().setTheme(Theme.STANDARD);
	}

	public void onTestSuccess(ITestResult tr) {

		logger = extent.createTest(tr.getName()); // create new entry in th report
		logger.log(Status.PASS, MarkupHelper.createLabel(tr.getName(), ExtentColor.GREEN)); // send the passed
																							// information to the report
																							// with GREEN color
																							// highlighted
	}
	public void onTestFailedWithTimeout(ITestResult tr) {
		logger = extent.createTest(tr.getName()); // create new entry in th report
		logger.log(Status.FAIL, MarkupHelper.createLabel(tr.getName(), ExtentColor.RED)); // send the passed information
																							// to the report with GREEN
																							// color highlighted

		String screenshotPath = GlobalVariables.configData.get("ReportPath") + "AutomationTestReport"
				+ System.currentTimeMillis() + ".html" + tr.getName() + ".png";
		logger.fail("Screenshot is below:" + logger.addScreenCaptureFromBase64String(screenshotPath));

	}

	public void onTestFailure(ITestResult tr) {

		logger = extent.createTest(tr.getName()); // create new entry in th report
		logger.log(Status.FAIL, MarkupHelper.createLabel(tr.getName(), ExtentColor.RED)); // send the passed information
																							// to the report with GREEN
																							// color highlighted

		String screenshotPath = GlobalVariables.configData.get("ReportPath") + "AutomationTestReport"
				+ System.currentTimeMillis() + ".html" + tr.getName() + ".png";
		logger.fail("Screenshot is below:" + logger.addScreenCaptureFromBase64String(screenshotPath));

	}
}