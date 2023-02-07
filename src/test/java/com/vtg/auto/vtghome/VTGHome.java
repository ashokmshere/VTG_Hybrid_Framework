package com.vtg.auto.vtghome;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.vtg.auto.business_reusablecomponents.ReusableComponents;
import com.vtg.auto.pageobjectutils.DropDown;
import com.vtg.auto.pageobjectutils.WebElementKeys;
import com.vtg.auto.reusablecomponents.*;

import junit.framework.Assert;;



public class VTGHome {
	private WebDriver driver;
	FrameHandler mousehandler = new FrameHandler();
	WebElementKeys webAction = new WebElementKeys();
	DropDown dropdown=new DropDown();
	ReusableComponents resuable=new ReusableComponents();
	
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
	
	@FindBy(xpath="//*[@id=\"dvGlobalOrganizationUnitTreeView\"]/ul/li[4]")
	WebElement olmSite;
	
	
	@FindBy(xpath="//*[text()='Skills']")
	WebElement skillLefNav;
	
	
	@FindBy(xpath="//*[text()=\"Demo\"]")
	WebElement demo;
	
	@FindBy(id="btnAddNew")
	WebElement empCategoryAdd;
	
	@FindBy(id="txtEmpBCode")
	WebElement empCode;
	
	@FindBy(id="txtEmpBDesc")
	WebElement empDesc;
	
	@FindBy(id="txtBSeq")
	WebElement empSeq;
	
	@FindBy(id="btnSaveEmpBasisDeatils")
	WebElement SaveButton;
	

	@FindBy(id="btnAddSkill")
	WebElement buttonoAddSkill;
	
	@FindBy(id="cmbPrimarySkillId")
	WebElement skillCode;
	
	@FindBy(id="txtSkillCode")
	WebElement skillCode_CreaeteSkill;
	
	@FindBy(id="txtSkillCodeDescription")
	WebElement skillCodeDescription;
	
	@FindBy(id="txtSequence")
	WebElement sequence;
	

	@FindBy(id="txtFixedRanking")
	WebElement fixedRanking;
	

	@FindBy(id="btnSaveSkill")
	WebElement saveSkill;
	
	@FindBy(xpath="//div[text()='Roles']")
	WebElement rolesLeftNav;
	

	@FindBy(id="btnAddNew")
	WebElement addRoles;
	
	@FindBy(id="txtRoleName")
	WebElement roleName;
	
	@FindBy(id="txtRoleDescription")
	WebElement roleDescription;
	
//	@FindBy(xpath="//Select[@id=\"cmbPrimarySkillId\"]")
	//WebElement SkillCode;
	

	@FindBy(id="btnSaveRolesDetails")
	WebElement saveRole;
	
	
	@FindBy(xpath="//div[text()='Role Groups']")
	WebElement roleGroupLeftNav;
	
	@FindBy(id="txtRoleGroupName")
	WebElement roleGroupName;
	
	@FindBy(xpath="//button[@title='Move all']")
	WebElement moveAll;
	
	@FindBy(id="btnSaveRoleGroupDtls")
	WebElement saveRoleGroup;
	
	//Employee Profile
	
	@FindBy(xpath="//*[text()='Employee Administration']")
	WebElement employeeAdmin;
	
	@FindBy(id="346")
	WebElement empProfile;
	
	@FindBy(id="btnAddEmployee")
	WebElement empAddEmp;
	
	@FindBy(id="txtEmployeeNumber")
	WebElement employeeNumber;
	
	@FindBy(id="txtBadgeNumber")
	WebElement badgeNumber;
	
	@FindBy(id="txtFirstName")
	WebElement firstName;
	
	@FindBy(id="txtLastName")
	WebElement lastName;
	
	@FindBy(id="dtBirthDate")
	WebElement birthDate;
	
	@FindBy(id="cmbEmploymentBasis")
	WebElement employmentBasis;
	
	@FindBy(id="cmbGender")
	WebElement gender;
	
	@FindBy(id="cmbMaritalStatus")
	WebElement maritalStatus;
	
	@FindBy(id="cmbTitle")
	WebElement title;
	
	@FindBy(id="txtAddressLine1")
	WebElement addressLine1;
	
	@FindBy(id="txtAddressLine2")
	WebElement addressLine2;
	
	@FindBy(id="txtPostCode")
	WebElement postCode;
	
	@FindBy(id="txtCity")
	WebElement cityName;
	
	@FindBy(id="txtState")
	WebElement state;
	
	@FindBy(id="cmbCountry")
	WebElement countryName;
	
	@FindBy(id="txtEmailAddress")
	WebElement emailAddress;
	
	@FindBy(id="cmbPosition")
	WebElement position;
	
	@FindBy(id="cmbScheduleRule")
	WebElement scheduleRule;
	
	@FindBy(xpath="//*[@id=\"cmbPayGroup\"]")
	WebElement payGroup;
	
	@FindBy(id="btnSaveEmployee")
	WebElement saveEmployees;
	

	//Employee Search
	@FindBy(xpath="//*[@id=\"emp-list_filter\"]//input")
	WebElement searchEmp;
	
	@FindBy(xpath="//table[@id='emp-list']/tbody/tr[1]/td[2]")
	WebElement empIdSearchResult;
	
	@FindBy(xpath="//table[@id='emp-list']/tbody/tr[1]/td[3]")
	WebElement empIdSearchResultName;
	
	//*[@class='toast-message']
	
	@FindBy(xpath="//*[@class='toast-message']")
	WebElement successToast_Message;
	
	//Employee Edit
	
	@FindBy(xpath="(//button[@type='button'])[5]")
	WebElement empProfileEdit;
	
	//Employee Duplicate
	@FindBy(xpath="(//button[@type='button'])[6]")
	WebElement empProfileEditDuplicate;
	
	//Contacts Sub Menu
	
	@FindBy(xpath="//*[text()='Contacts']")
	WebElement contactsSubMenu;
	
	@FindBy(id="btnAddNewContact")
	WebElement contactsAdd;
	
	@FindBy(id="txtContactName")
	WebElement contactName;
	
	@FindBy(id="cmbRelationship")
	WebElement contactRelationship;
	
	@FindBy(id="btnSaveContact")
	WebElement saveContactButton;
	
	@FindBy(xpath="(//*[@type='button'])[5]")
	WebElement cancelContactButton;
	
	//Excluded Skills Sub Menu
	
		@FindBy(xpath="//*[text()='Excluded Skills']")
		WebElement excludedSkillsSubMenu;
		
		@FindBy(id="btnAddNewExcludedSkills")
		WebElement addNewExcludedSkills;
		
		@FindBy(id="cmbExcludedSkill")
		WebElement excludedSkillDesc;
		
		@FindBy(id="dtExcludedSkillStartDate")
		WebElement excludedSkillStartDate;
		
		@FindBy(id="dtExcludedSkillEndDate")
		WebElement excludedSkillEndDate;
		
		@FindBy(id="btnSaveExcludedSkill")
		WebElement saveExcludedSkill;
		
		@FindBy(xpath="(//*[@type='button'])[15]")
		WebElement cancelExcludedSkill;
	
		//Roles Sub Menu
		
			@FindBy(xpath="//*[text()='Roles']")
			WebElement rolesSubMenu;
			
			@FindBy(id="cmbPrimaryRole")
			WebElement primaryRole;
			
			@FindBy(xpath="//button[@title='Move all']")
			WebElement moveAllEmpProfile;
			
			@FindBy(xpath="//button[@title='Remove all']")
			WebElement removeAllEmpProfile;
			
			@FindBy(xpath="//table[@id='employee-skill-competency-list']/tbody/tr[1]/td[7]")
			WebElement skillCodeEdit;
			

			@FindBy(id="btnSaveSkillCompetencyDetails")
			WebElement saveSkillCompetencyDetails;
			
			
			//Security Sub Menu
			
			@FindBy(xpath="//*[text()='Security']")
			WebElement securitySubMenu;
			
			@FindBy(id="btnAddEmployeeOU")
			WebElement addEmployeeOU;
			
			@FindBy(id="cmbSharedOrgUnit")
			WebElement orgUnitName;
			
			@FindBy(id="btnSaveEmployeeOU")
			WebElement saveEmployeeOU;
			
			//	ESS Sub Menu
			
			@FindBy(xpath="//*[text()='ESS']")
			WebElement ESSSubMenu;
			
			@FindBy(id="btnAddLeaveProfile")
			WebElement AddLeaveProfile;
			
			@FindBy(id="cmbEmpLeaveProfileName")
			WebElement empLeaveProfileName;
			
			@FindBy(id="btnSaveLeaveProfile")
			WebElement saveLeaveProfile;
			
			//add workflow route
			@FindBy(id="btnAddEmpWorkflowRoute")
			WebElement addEmpWorkflowRoute;
			
			@FindBy(id="cmbWorkFlowModuleName")
			WebElement workFlowModuleName;
		
			
			@FindBy(id="cmbWorkflowRouteName")
			WebElement workflowRouteName;
		
			
			@FindBy(id="btnSaveEmpWorkflowRoute")
			WebElement saveEmpWorkflowRoute;
		
			
			//Miscellaneous route
			
			@FindBy(xpath="//*[text()='Miscellaneous']")
			WebElement miscellaneous;
			
			@FindBy(id="chkMiscColumn1")
			WebElement chkMiscColumn;
			
			@FindBy(xpath="//*[text()='No thanks']")
			WebElement noThanks;
		
			@FindBy(xpath="//*[text()='Gmail']")
			WebElement gmail;
		
			
		
			
	// Constructor
	public VTGHome(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public boolean Test1() throws Exception {
		//webAction.click(noThanks);
		webAction.click(gmail);
		System.out.println("Test1");
		return false;


	}
	
	public boolean Test2() throws Exception {
		//dropdown.selectByIndex(AddLeaveProfile, 1);
		webAction.click(gmail);
		System.out.println("Test2");
		return false;


	}
	
	public boolean Test3() throws Exception {
		webAction.click(gmail);
		System.out.println("Test3");
		return false;


	}
	public boolean Test4() throws Exception {
		webAction.click(gmail);
		System.out.println("Test4");
		return false;


	}
	
	
	
	
}
