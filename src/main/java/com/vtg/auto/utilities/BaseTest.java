package com.vtg.auto.utilities;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
//import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.vtg.auto.reports.ExtentITestListenerClassAdapter;
import com.vtg.auto.reusablecomponents.Browser;
import com.vtg.auto.reusablecomponents.Common;
import com.vtg.auto.reusablecomponents.FrameHandler;
import com.vtg.auto.reusablecomponents.GlobalVariables;
import com.vtg.auto.reusablecomponents.Initialize;
import com.vtg.auto.reusablecomponents.KeyHandler;
import com.vtg.auto.reusablecomponents.MouseHandler;
import com.vtg.auto.reusablecomponents.PopupHandler;
import com.vtg.auto.reusablecomponents.ScrollHandler;
import com.vtg.auto.reusablecomponents.Wait;
import com.vtg.auto.webelements.Button;
import com.vtg.auto.webelements.CheckBox;
import com.vtg.auto.webelements.DropDown;
import com.vtg.auto.webelements.Link;
import com.vtg.auto.webelements.RadioButton;
import com.vtg.auto.webelements.TextBox;



@Listeners(ExtentITestListenerClassAdapter.class)
public class BaseTest extends GlobalVariables {

	//protected Hashtable<String, String> dataTable;
	public ReadDataValues readDataValues = new ReadDataValues();
	protected Button button = new Button();
	protected Link link = new Link();
	protected Common common = new Common();
	protected CheckBox checkBox = new CheckBox();
	protected DropDown dropDown = new DropDown();
	protected RadioButton radioButton = new RadioButton();
	protected TextBox textBox = new TextBox();
	protected TestVerification testVerificationObj = new TestVerification();
	protected Initialize initialize = new Initialize();
	protected ScrollHandler scrollHandler = new ScrollHandler();
	protected FrameHandler frameHandler = new FrameHandler();
	protected PopupHandler popupHandler = new PopupHandler();
	protected MouseHandler mouseHandler = new MouseHandler();
	protected KeyHandler keyHandler = new KeyHandler();
	public WebDriver driver;
	protected String methodName;
	protected Wait waitObj = new Wait();

	// Common objects for normal and parallel mode
	protected static ExtentSparkReporter htmlReporter;

	// only for parallel mode
	protected static ThreadLocal<ExtentTest> gridParentTest = new ThreadLocal<ExtentTest>();
	protected static ThreadLocal<ExtentTest> gridChildTest = new ThreadLocal<ExtentTest>();
	protected static ExtentTest methodLevelChildTest;

	// only for normal mode
	protected static ExtentReports normalExtent;
	protected static ExtentTest test;
	protected static ExtentTest childTest;
	public static String testResult; // 18/5/2018
	String os = getOperatingSystem();

	@BeforeSuite
	public void beforeSuiteSetup() {

		if (os.contains("Windows")) {
			killExistingWebDriverExecs();
		}
		initialize.initializeObjects();
		if (reportUtils == null) {
			reportUtils = new ReportUtils();

		}
		dataTable = new JSONReader();
		if (configData.get("Grid").equalsIgnoreCase("Yes"))
			isGridMode = true;
	}

	protected void setupClass(Package moduleName, String className) {				
		if (isGridMode) {

			ExtentTest parent = ExtentTestManager.createTest(getClass().getName());
			gridParentTest.set(parent);
		} else {
			test = ExtentTestManager.createTest(getClass().getName());
			gridParentTest.set(test);
		}
	}

	@BeforeMethod
	public synchronized void beforeMethod(Method method) throws Exception {
		try {
			
				methodName = method.getName();
				driver = Browser.getInstance().getDriver();
				
			
				if (isGridMode) {
	
					
					methodLevelChildTest = gridParentTest.get().createNode(methodName);
					gridChildTest.set(methodLevelChildTest);
					reportUtils.addInfo("Grid Enabled :: Driver Instance"+driver, methodName);
					
					
				} else {
					
					childTest = gridParentTest.get().createNode(method.getName());
					gridChildTest.set(childTest);
					reportUtils.addInfo("Grid Not Enabled :: ", methodName);
					
				}
				System.out.println("Inside the Method :: " + methodName);

		} catch (Exception e) {
			e.printStackTrace();

			reportUtils.addInfoOnValidations(methodName, false, "Failure", e.getMessage());
		}
	}

	protected void setupTest(Package moduleName, String className, String testScriptName) throws Exception {
		
			//dataTable = readDataValues.getTestDataAsTable(moduleName, testScriptName, 2);
		
		launchBrowser();
	}

	@AfterMethod
	public synchronized void afterMethod(ITestResult result) throws IOException {
		
		if (isGridMode) {
			if (result.getStatus() == ITestResult.FAILURE) {
				result.getThrowable().printStackTrace();

				gridChildTest.get().log(Status.FAIL, result.getThrowable());
				reportUtils.addInfoOnValidations(result.getMethod().getMethodName(), false, "Failure", "Test Failed");
			} else if (result.getStatus() == ITestResult.SKIP)
				gridChildTest.get().skip(result.getThrowable());
			else
				reportUtils.addInfoOnValidations(result.getMethod().getMethodName(), true, "Success", "Test Passed");
				gridChildTest.get().info("Test ended");
				
			ExtentManager.getInstance().flush();
		} else {
			if (result.getStatus() == ITestResult.FAILURE) {
				result.getThrowable().printStackTrace();
				childTest.log(Status.FAIL, result.getThrowable());
				reportUtils.addInfoOnValidations(result.getMethod().getMethodName(), false, "Failure", "Test Failed");
				// Assert.fail("Failed",result.getThrowable());

			} else if (result.getStatus() == ITestResult.SKIP)
				childTest.skip(result.getThrowable());
			else
				reportUtils.addInfoOnValidations(result.getMethod().getMethodName(), true, "Success", "Test Passed");
				childTest.info("Test ended");

			ExtentManager.getInstance().flush();
		}

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now) + "--> Removing driver for Thread id: " + Thread.currentThread().getId());

		Browser.getInstance().removeDriver();
	}

	@BeforeClass
	public void setUpClass() throws Exception {

		setupClass(this.getClass().getPackage(), this.getClass().toString());
	}

	@AfterClass
	public void afterClass() {

	}

	@AfterSuite
	public void afterTestSuite() {
		if (os.contains("Windows")) {
			File reportFile = getLatestFilefromDir(GlobalVariables.configData.get("ReportPath").toString());
			if (GlobalVariables.configData.get("WebDriverManager").equalsIgnoreCase("Yes")){
			WebDriverManager.chromedriver().avoidBrowserDetection().setup();}
			else
				System.setProperty("webdriver.chrome.driver", configData.get("ChromeDriverPath"));
			ChromeOptions options = new ChromeOptions();
			options.addArguments("chrome.switches", "--disable-extensions");
			options.addArguments("test-type");
			WebDriver reportDriver = new ChromeDriver(options);
			reportDriver.manage().window().maximize();
			reportDriver.get(reportFile.toString());
			if (testResult == "fail") {
				System.exit(1);
			}
		}
	}

	/**
	 * Reusable functions
	 */
	protected void launchBrowser() {
		goTo(GlobalVariables.configData.get("ApplicationURL").toString());
	}

	protected void launchBrowser(String applicationURL) {
		goTo(applicationURL);
	}

	public void goTo(String url) {
		WebDriver driver = Browser.getInstance().getDriver();

		String getWaitTime = GlobalVariables.configData.get("implicitWait");
		int waitTime = Integer.parseInt(getWaitTime.substring(0, 2));

		driver.get(url.trim());

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(waitTime));
		driver.manage().window().maximize();
	}

	public boolean verifyPageTitle(String expectedPageTitle, WebDriver driver) {
		try {
			String pageTitle = expectedPageTitle.trim();
			String actualPageTitle = driver.getTitle().trim();

			Assert.assertTrue(pageTitle.equalsIgnoreCase(actualPageTitle));
			return true;

		} catch (Throwable e) {
			return false;
		}
	}

	public void close(WebDriver driver) {
		driver.quit();
	}

	public void closeAll() throws IOException {
		Runtime.getRuntime().exec("taskkill /F /IM ChromeDriver.exe");
	}

	private File getLatestFilefromDir(String dirPath) {
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		if (files == null || files.length == 0) {
			return null;
		}
		File lastModifiedFile = files[0];
		for (int i = 1; i < files.length; i++) {
			if (lastModifiedFile.lastModified() < files[i].lastModified()) {
				lastModifiedFile = files[i];
			}
		}
		return lastModifiedFile;
	}

	/* Get the newest file for a specific extension */
	public static File getTheNewestFile(String filePath, String ext) {
		File theNewestFile = null;
		File dir = new File(filePath);
		FileFilter fileFilter = new WildcardFileFilter("*." + ext);
		File[] files = dir.listFiles(fileFilter);
		if (files.length > 0) {
			/** The newest file comes first **/
			Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
			theNewestFile = files[0];
		}
		return theNewestFile;
	}

	private void killExistingWebDriverExecs() {
		try {
			Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
			Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	// Added new code to get operating system
	public String getOperatingSystem() {
		String os = System.getProperty("os.name");

		return os;
	}

}
