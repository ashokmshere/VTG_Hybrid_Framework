package com.vtg.auto.utilities;

import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewName;
import com.vtg.auto.reusablecomponents.GlobalVariables;

public class ExtentManager {
	private static ExtentReports extent;

	public static ExtentReports getInstance() {
		if (extent == null) {
			
			if (System.getenv("BUILD_NUMBER") ==null&&!System.getProperty("os.name").contains("Linux") ) {

				createInstance(
						GlobalVariables.configData.get("ReportPath") + GlobalVariables.configData.get("Project Name")
								+ " - " + "AutomationTestReport" + System.currentTimeMillis() + ".html");
			} else {
				createInstance(GlobalVariables.configData.get("ReportPath")
						+ GlobalVariables.configData.get("Project Name") + " - " + "AutomationTestReport.html");
			}
		}
		// extent.setSystemInfo("Browser", "Firefox");
		return extent;
	}

	public static ExtentReports createInstance(String fileName) {

		// ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
		ExtentSparkReporter htmlReporter = new ExtentSparkReporter(fileName);

		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setDocumentTitle(GlobalVariables.configData.get("Project Name")); // Title of report
		//htmlReporter.config().setCss(".badge{padding:5px 7px;font-size:100%;font-weight:500;line-height:1}");
		//htmlReporter.config().setCss(".test-detail .name {font-size: 15px !important;font-weight: 500;word-break: break-all;width: 99%;}");
		htmlReporter.config().setCss(".test-detail .name {font-size: 15px !important;font-weight: 500;word-break: break-all;width: 99%;}body, p { font-family: Roboto,-apple-system, system-ui, BlinkMacSystemFont, \"Segoe UI\", \"Helvetica Neue\", Arial, sans-serif;color: #585858;font-size: 15px;}.test-wrapper .test-list .test-list-wrapper .test-list-item .test-item .test-detail .name {font-size: 16px;color: whitesmoke} .dark .node {	color: white;}.dark .card, .dark .media>li, .dark .badge-default, .dark.bdd-report .card-body.l1, .dark .table-bordered td, .dark .table-bordered th, .dark textarea {color: white;border: 1px solid white !important;}.dark .test-detail .name, .dark p {color: white !important;}");

		htmlReporter.config()
				.setReportName("Release - " + GlobalVariables.configData.get("Release") + " Build - "
						+ GlobalVariables.configData.get("Release") + " Environment - "
						+ GlobalVariables.configData.get("Environment"));
		htmlReporter.config().setTheme(Theme.DARK);
		extent = new ExtentReports();
		extent.setSystemInfo("OS", System.getProperty("os.name"));
		extent.setSystemInfo("Browser", GlobalVariables.configData.get("Browser"));
		
		if(GlobalVariables.configData.get("Grid").equals("Yes")) {
		
		extent.setSystemInfo("GRID URL", GlobalVariables.configData.get("GridHost"));
		}else {
			extent.setSystemInfo("Grid", "Not Enabled");
		}
		extent.attachReporter(htmlReporter);

		return extent;
	}
}
