package com.vtg.auto.driver;

import java.util.List;

import org.testng.TestNG;
import org.testng.collections.Lists;

import com.vtg.auto.reusablecomponents.GlobalVariables;
import com.vtg.auto.reusablecomponents.Initialize;
import com.vtg.auto.utilities.XmlTestSuiteGenerator;

public class Driver extends GlobalVariables {
	public static boolean bLoginStatus = false;
	public static String strCDSID = System.getProperty("user.name");
	public static String rootFolderPath = System.getProperty("user.dir");
	public static String downloadPath = "c:/users/"+strCDSID+"/Downloads/";	

	public static void main(String args[]) throws Exception {
		Initialize initial = new Initialize();
		initial.initializeObjects();
		XmlTestSuiteGenerator xmlTestSuiteGenerator = new XmlTestSuiteGenerator();
		xmlTestSuiteGenerator.generateTestNgXmlSuite();
		TestNG testng = new TestNG();
		List<String> suites = Lists.newArrayList();
		suites.add(workingDir + "/testng.xml");
		testng.setTestSuites(suites);
		testng.run();
		
		
	}
}


	