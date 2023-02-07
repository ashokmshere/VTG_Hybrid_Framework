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



public class JiViewsLeaveAdministration {
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
	
	
	@FindBy(xpath="//*[text()='Roles']")
	WebElement rolesLefNav;
	
	@FindBy(id="btnAddNew")
	WebElement buttonAddNew;
	

	
	@FindBy(xpath="//*[text()='Employment Basis updated successfully']")
	WebElement assert_EmpAdd;
	
	@FindBy(xpath="//*[text()='SYSTEM SETUP']")
	WebElement systemSetup;

	@FindBy(xpath="//*[text()='System Definitions']")
	WebElement systemDefLink;
	
	@FindBy(xpath="//*[text()='Employment Category']")
	WebElement empCategory;
	
	@FindBy(xpath="//*[@id=\"dvOrgUnitDropdown\"]/a/i")
	WebElement olm;
	
	@FindBy(xpath="//*[text()='Skills']")
	WebElement skillLefNav;
	
	
	@FindBy(xpath="//*[text()=\"Demo\"]")
	WebElement demo;
	
	@FindBy(xpath="//div[text()='Leave & Attendance Codes']")
	WebElement leaveAttendanceCodeLeftNav;

	
	@FindBy(id="btnAddExceptionCodes")
	WebElement btnAddLeave;
	
	@FindBy(id="txtExcpetionCodeName")
	WebElement excpetionCode;
	
	@FindBy(id="txtExceptionCodeDesc")
	WebElement exceptionCodeDesc;
	
	@FindBy(id="cmbExceptionTypeId")
	WebElement exceptionTypeId;
	
	@FindBy(id="btnSaveExceptionCodes")
	WebElement saveExceptionCodes;
	
	// Constructor
	public JiViewsLeaveAdministration(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public boolean Login(String userid, String pwd) throws Exception {
		webAction.setText(userName, userid);
		webAction.setText(password, pwd);
		webAction.click(signIn);
		return false;


	}
	
	
	
	public boolean addSkill(String code,String desc,String seq,String rank) throws Exception {
		webAction.click(olm);
		webAction.click(demo);
		webAction.click(systemSetup);
		webAction.click(systemDefLink);
		webAction.click(leaveAttendanceCodeLeftNav);
		webAction.click(btnAddLeave);
		webAction.setText(excpetionCode, code);
		webAction.setText(exceptionCodeDesc, desc);
		dropdown.selectByIndex(exceptionTypeId, 0);
		webAction.click(saveExceptionCodes);
		
		return false;
	}
	
	
	public boolean updatekill(String code,String desc) throws Exception {
		webAction.click(olm);
		webAction.click(demo);
		webAction.click(systemSetup);
		webAction.click(systemDefLink);
		webAction.click(leaveAttendanceCodeLeftNav);
		webAction.click(btnAddLeave);
		webAction.setText(excpetionCode, code);
		webAction.setText(exceptionCodeDesc, desc);
		dropdown.selectByIndex(exceptionTypeId, 0);
		webAction.click(saveExceptionCodes);
		
		return false;
	}
	
	
	public boolean empCategory() throws Exception {
		webAction.click(olm);
		webAction.click(demo);
		webAction.click(systemSetup);
		webAction.click(systemDefLink);
		webAction.click(empCategory);
		return false;
	}
	

	

}
