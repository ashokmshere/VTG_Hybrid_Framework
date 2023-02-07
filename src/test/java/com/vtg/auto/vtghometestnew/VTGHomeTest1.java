package com.vtg.auto.vtghometestnew;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;
import com.vtg.auto.pageobjectutils.FrameWindowHandler;
import com.vtg.auto.pageobjectutils.POMCommon;
import com.vtg.auto.pageobjectutils.WebElementKeys;
import com.vtg.auto.utilities.BaseTest;
import com.vtg.auto.vtghome.VTGHome;


public class VTGHomeTest1 extends BaseTest {
	
	
	VTGHome vtghome;
	POMCommon pomCommon;
	WebElementKeys webAction = new WebElementKeys();
	FrameWindowHandler windowHandler = new FrameWindowHandler();
	
	Faker faker = new Faker();

	String name = faker.name().fullName();
	String firstName = faker.name().firstName();
	String lastName = faker.name().lastName(); 
	String streetAddress = faker.address().streetAddress(); 
	
	
	@FindBy(xpath="//*[@class='toast-message']")
	WebElement successToast_Message;
	
	@Test
	public void VerifyLoginTest1() throws Exception {
		
		setupTest(this.getClass().getPackage(), this.getClass().toString(), methodName);
		VTGHome vtghome=PageFactory.initElements(driver, VTGHome.class);
		//String userId=dataTable.get("UserId");
		//String loginpwd=dataTable.get("pwd");
		vtghome.Test1();
		String homeAssert=driver.getTitle();
		System.out.println(homeAssert);
	Assert.assertEquals(homeAssert, "tesing");
		webAction.waitForPageLoad(2000);
	}
	
	@Test
	public void VerifyLoginTest2() throws Exception {
		
		setupTest(this.getClass().getPackage(), this.getClass().toString(), methodName);
		VTGHome vtghome=PageFactory.initElements(driver, VTGHome.class);
		//String userId=dataTable.get("UserId");
		//String loginpwd=dataTable.get("pwd");
		vtghome.Test2();
		String homeAssert=driver.getTitle();
		
	}
	
	@Test
	public void VerifyLoginTest3() throws Exception {
		
		setupTest(this.getClass().getPackage(), this.getClass().toString(), methodName);
		VTGHome vtghome=PageFactory.initElements(driver, VTGHome.class);
		//String userId=dataTable.get("UserId");
		//String loginpwd=dataTable.get("pwd");
		vtghome.Test3();
		String homeAssert=driver.getTitle();
		
	}
	
	@Test
	public void VerifyLoginTest4() throws Exception {
		
		setupTest(this.getClass().getPackage(), this.getClass().toString(), methodName);
		VTGHome vtghome=PageFactory.initElements(driver, VTGHome.class);
		//String userId=dataTable.get("UserId");
		//String loginpwd=dataTable.get("pwd");
		vtghome.Test4();
		String homeAssert=driver.getTitle();
		
	}
	
}
