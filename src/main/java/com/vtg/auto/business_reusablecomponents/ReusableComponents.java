package com.vtg.auto.business_reusablecomponents;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.vtg.auto.driver.Driver;
import com.vtg.auto.reusablecomponents.Browser;
import com.vtg.auto.reusablecomponents.Common;
import com.vtg.auto.reusablecomponents.GlobalVariables;
import com.vtg.auto.reusablecomponents.Wait;
import com.vtg.auto.utilities.BaseTest;
import com.vtg.auto.utilities.excelSheetReaderUtil;
import com.vtg.auto.webelements.Button;
import com.vtg.auto.webelements.CheckBox;
import com.vtg.auto.webelements.DropDown;
import com.vtg.auto.webelements.RadioButton;
import com.vtg.auto.webelements.TextBox;


public class ReusableComponents extends BaseTest {
	
	//static WebDriver driver = Browser.getInstance().getDriver();
	
	static String url;
	static Button button = new Button();
	static CheckBox checkBox = new CheckBox();
	static DropDown dropDown = new DropDown();
	static RadioButton radioButton = new RadioButton();
	static TextBox textBox = new TextBox();
//	static Browser browser = new Browser();
	static String actualString;
	static int iInvalidImageCount;
	static excelSheetReaderUtil excelSheetReaderUtil = new excelSheetReaderUtil( System.getProperty("user.dir")
			+ "\\src\\test\\resources\\testData\\TestData.xlsx");

	static Common objCommon = new Common();
	//static JavascriptExecutor jsExecutor = (JavascriptExecutor)driver;
	static String strMethodName = "";
	static Wait wait = new Wait();
	
		

	//Function that helps during remote execution to know the exact node detail 
	public static void getMachineDetails(){
		try {
			InetAddress ipAddr = InetAddress.getLocalHost();
			System.out.println(ipAddr.getHostAddress());
			System.out.println(ipAddr.getHostName());

			//reportUtils.passTest("Executed from machine: "+ipAddr.getHostAddress());
			//reportUtils.passTest("Executed from machine: "+ipAddr.getHostName());

		} catch (UnknownHostException ex) {
			ex.printStackTrace();
			System.out.println("Error- Number details");
		}		
	}

	public static void applicationLogin(String module,  String testScript, String userName, String password) {

		strMethodName = "applicationLogin - 1";
		//reportUtils.log("Log: Inside method - "+ strMethodName, strMethodName);
		System.out.println("Log: Inside method - "+ strMethodName);		

		try{

			userName = GlobalVariables.configData.get("User").toString().trim();
			password = GlobalVariables.configData.get("Password").toString().trim();			



			textBox.setText("secureLogin_id", userName);
			textBox.setSecureText("secureLoginPassword_id", password);
			button.click("secureLoginLogin_xpath");


			objCommon.click("ContinueButton_id");

			ReusableComponents.waitTill_PageLoads(30000);
			Driver.bLoginStatus = false;

		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Known Exception from > ReusableComponents - applicationLogin");
			//reportUtils.failTest(strMethodName, "Exception from Login page, please check");
		}
	}

	public static void applicationLogin(String module,  String testScript, String columnName) {

		strMethodName = "applicationLogin - 2";
		//reportUtils.log("Log: Inside method - "+ strMethodName, strMethodName);
		System.out.println("Log: Inside method - "+ strMethodName);		

		try{
			textBox.setText("secureLogin_id", getDataValue(module,testScript , columnName));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	//Generic function - Switch to sub-window
	public static boolean switchToSubWindow(){
		WebDriver driver = Browser.getInstance().getDriver();
		strMethodName = "switchToSubWindow";
		//reportUtils.log("Log: Inside method - "+ strMethodName, strMethodName);
		System.out.println("Log: Inside method - "+ strMethodName);		

		boolean bSwitchHappened = false;

		try{

			String subWindowHandler = null;

			Set<String> handles = driver.getWindowHandles(); // get all window handles
			if(handles.size() > 1){
				Iterator<String> iterator = handles.iterator();
				while (iterator.hasNext()){
					subWindowHandler = iterator.next();
					bSwitchHappened = true;
				}
				driver.switchTo().window(subWindowHandler); // switch to pop up window
			}
		}
		catch(Exception e){
			System.out.println("Exception - HomePageHelpers - switchToSubWindow");
			e.printStackTrace();			
		}

		return bSwitchHappened;
	}

	public static void waitTill_PageLoads(long lngTimeOutSeconds){
		WebDriver driver = Browser.getInstance().getDriver();
		strMethodName = "waitTill_PageLoads";
		//reportUtils.log("Log: Inside method - "+ strMethodName, strMethodName);
		System.out.println("Log: Inside method - "+ strMethodName);		

		try
		{
			long lngTime = 0;
			JavascriptExecutor oJsEngine = (JavascriptExecutor) driver;
			String sStatus = "";
			Thread.sleep(1000);

			while(!sStatus.equalsIgnoreCase("complete") && lngTime <= lngTimeOutSeconds)
			{
				System.out.println("From try block of waitTill_PageLoads, Inside while......");
				sStatus = oJsEngine.executeScript("return document.readyState").toString();
				Thread.sleep(1000);
				lngTime++;
			}
			System.out.println("From try block of waitTill_PageLoads, Outside while......");
			WebDriverWait oWait;

			oWait = new WebDriverWait(driver,Duration.ofSeconds(30));;
			oWait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));//wait till body tag is visible
		}catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("From catch block of waitTill_PageLoads......");
		}
	}

	//--------------------------------------------------------------------------------------

	public static void validateInvalidImages(){
		strMethodName = "validateInvalidImages";
		WebDriver driver = Browser.getInstance().getDriver();
		//reportUtils.log("Log: Inside method - "+ strMethodName, strMethodName);
		System.out.println("Log: Inside method - "+ strMethodName);		

		try{
			iInvalidImageCount = 0;
			List<WebElement> eImageList = driver.findElements(By.tagName("img"));
			System.out.println("Total no. of Images"+eImageList.size());
			int j = (int)(eImageList.size());
			for(int l = 0; l<j;l++)
			{
				System.out.println(l);
				System.out.println(eImageList.get(l).getAttribute("src"));
			}

			if(iInvalidImageCount > 0)
			{
				System.out.println("Broken Images");
			}
			else
			{
				System.out.println("Valid Images");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	//--------------------------------------------------------------------------------------

	public static void verifyimageActive(WebElement eImageElement){

		strMethodName = "verifyimageActive";
		//reportUtils.log("Log: Inside method - "+ strMethodName, strMethodName);
		System.out.println("Log: Inside method - "+ strMethodName);

		try{
			HttpClient cClient = HttpClientBuilder.create().build();
			HttpGet gRequest = new HttpGet(eImageElement.getAttribute("src"));
			HttpResponse rResponse = cClient.execute(gRequest);

			if(rResponse.getStatusLine().getStatusCode()!= 200)
			{
				iInvalidImageCount++;

			}		

		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static int getDataRow(String module,  String testScript){

		strMethodName = "getDataRow";
		//reportUtils.log("Log: Inside method - "+ strMethodName, strMethodName);
		System.out.println("Log: Inside method - "+ strMethodName);		

		int dataRowCount = 0;
		String testModuleName = module.substring(module.lastIndexOf("."));

		testModuleName = testModuleName.substring(1);
		excelSheetReaderUtil.getRowCount(testModuleName);

		int rowCount = excelSheetReaderUtil.getRowCount(testModuleName);
		int columnCount = excelSheetReaderUtil.getColumnCount(testModuleName);

		for (int i = 2;i<=rowCount;i++){
			for(int j=0;j<=columnCount;j++){
				String ActualString = excelSheetReaderUtil.getCellData(i, j);
				if(ActualString.equalsIgnoreCase(testScript)){
					dataRowCount = i;
					break;
				}

			}
		}

		return dataRowCount;
	}

	public static String getDataValue(String module,  String testScript , String column){

		strMethodName = "getDataValue";
		//reportUtils.log("Log: Inside method - "+ strMethodName, strMethodName);
		System.out.println("Log: Inside method - "+ strMethodName);		

		int dataRow= getDataRow(module,testScript);	
		String dataValue = null;
		String testModuleName = module.substring(module.lastIndexOf("."));
		testModuleName = testModuleName.substring(1);
		excelSheetReaderUtil.getRowCount(testModuleName);
		int columnCount = excelSheetReaderUtil.getColumnCount(testModuleName);

		for(int i=1 ;i<=columnCount; i++){
			actualString = excelSheetReaderUtil.getCellData(1, i);
			if(actualString.equalsIgnoreCase(column)){
				dataValue =  excelSheetReaderUtil.getCellData(dataRow,i);
			}

		}
		return dataValue;
	}

	public static void navigateToURL(String tempURL){
		WebDriver driver = Browser.getInstance().getDriver();
		strMethodName = "navigateToURL";
		//reportUtils.log("Log: Inside method - "+ strMethodName, strMethodName);
		System.out.println("Log: Inside method - "+ strMethodName);		

		//reportUtils.log(tempURL, tempURL);
		driver.navigate().to(tempURL);
		waitTill_PageLoads(30000);
	}

	public static boolean generic_fnIsDisplayedOrNot(By argBy){ //Generic Function
		WebDriver driver = Browser.getInstance().getDriver();
		strMethodName = "generic_fnIsDisplayedOrNot";
		//reportUtils.log("Log: Inside method - "+ strMethodName, strMethodName);
		System.out.println("Log: Inside method - "+ strMethodName);		

		boolean isDisplayed = false;

		try{
			if(driver.findElement(argBy).isDisplayed())				
				isDisplayed = true;
		}
		catch(Exception e){
			isDisplayed = false;
			System.out.println("From catch block of generic_fnIsDisplayedOrNot - for xPath::"+argBy);
		}

		return isDisplayed;
	}

	public static boolean generic_ReportStatus(boolean argStatus, String argFunctionName, String argSuccessMsg, String argFailureMsg){

		strMethodName = "generic_ReportStatus";
		//reportUtils.log("Log: Inside method - "+ strMethodName, strMethodName);
		System.out.println("Log: Inside method - "+ strMethodName);		

		if(argStatus == true){
			//reportUtils.passTest(argSuccessMsg);
			return true;
		}
		else{
			//reportUtils.failTest(argFunctionName, argFailureMsg);
			return false;
		}
	}


	public int ElementsCount(By by, WebDriver driver)
	{
		int count = 0;

		count = driver.findElements(by).size();

		return count ;
	}

	public List<WebElement> FindElements(By by,WebDriver driver)
	{
		return driver.findElements(by);
	}


	public void HighlightElement(WebDriver d,WebElement ele)
	{
		for(int i=0;i<2;i++)
		{
			JavascriptExecutor js = (JavascriptExecutor) d;
			js.executeScript("arguments[0].setAttribute('style',arguments[1]);",ele,"color:yellow; border: 6px solid green;");
			js.executeScript("arguments[0].setAttribute('style',arguments[1]);",ele,"");
		}
	}

	public void ScrollToTheElement(WebDriver d,WebElement ele)
	{
		((JavascriptExecutor)d).executeScript("arguments[0].ScrollIntoView(true);",ele);
	}



	public void WaitUntilBowserReady(WebDriver d) throws InterruptedException
	{
		int count = 0;
		JavascriptExecutor js = (JavascriptExecutor) d;
		do
		{
			count++;
			Thread.sleep(5000);
		}while(!(js.executeScript("return document.readyState").equals("complete")) || count == 10);

	}

}
