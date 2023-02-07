package com.vtg.auto.driver;

import java.util.List;

import org.testng.TestNG;
import org.testng.collections.Lists;

import com.vtg.auto.reusablecomponents.GlobalVariables;
import com.vtg.auto.reusablecomponents.Initialize;
import com.vtg.auto.utilities.XmlTestSuiteGenerator;

public class MainDriverScriptRemote extends GlobalVariables {
	
	public static void main(String args[]) throws Exception {
		Initialize initial = new Initialize();
		initial.initializeObjects();
		XmlTestSuiteGenerator xmlTestSuiteGenerator = new XmlTestSuiteGenerator();
		xmlTestSuiteGenerator.generateTestNgXmlSuite();
		TestNG testng = new TestNG();
		List<String> suites = Lists.newArrayList();
		suites.add(workingDir + "/testng.xml");
		testng.setTestSuites(suites);
		
	}
}
	