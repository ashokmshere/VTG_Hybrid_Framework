package com.vtg.auto.vtghome;

import java.io.IOException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.vtg.auto.pageobjectutils.DropDown;
import com.vtg.auto.pageobjectutils.WebElementKeys;
import com.vtg.auto.reusablecomponents.*;;



public class ReusableMethods {
	private WebDriver driver;
	FrameHandler mousehandler = new FrameHandler();
	WebElementKeys webAction = new WebElementKeys();
	DropDown dropdown=new DropDown();
	
	@FindBy(id="txtUserName")
	WebElement userName;

	@FindBy(id="txtPassword")
	WebElement password;
	
	@FindBy(id="btnSignIn")
	WebElement signIn;
	
	@FindBy(xpath="//*[text()='Operation Planning & Execution']")
	WebElement operationPlannExec;
	

	@FindBy(xpath="//*[text()='Workload Planner']")
	WebElement workloadPlanner;
	
	@FindBy(id="dtPlanning")
	WebElement date;
	
	@FindBy(id="cmbShiftBand")
	WebElement selectShiftBand;
	
	@FindBy(id="btnSearchDailyPlanning")
	WebElement search;
	
	@FindBy(id="dpDailyPlanningTimeLine")
	WebElement createVesselTable;
	
	
	
	// Constructor
	public ReusableMethods(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public boolean Login(String userid, String pwd) throws Exception {
		webAction.setText(userName, userid);
		webAction.setText(password, pwd);
		webAction.click(signIn);
		return false;


	}
	
	
	public void ResourceSetDefinitionP2(String userid, String pwd) throws Exception {
		webAction.setText(userName, userid);
		webAction.setText(password, pwd);
		webAction.click(signIn);
		


	}
	
	public void PrimeMoverPlanning(String userid, String pwd) throws Exception {
		webAction.setText(userName, userid);
		webAction.setText(password, pwd);
		webAction.click(signIn);
		


	}
	
	public void RTGPlanning(String userid, String pwd) throws Exception {
		webAction.setText(userName, userid);
		webAction.setText(password, pwd);
		webAction.click(signIn);
		


	}
	

	

}
//testing 04-01-2023