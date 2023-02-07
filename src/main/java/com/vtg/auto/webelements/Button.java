package com.vtg.auto.webelements;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.vtg.auto.reusablecomponents.Browser;
import com.vtg.auto.reusablecomponents.Common;
import com.vtg.auto.reusablecomponents.GlobalVariables;

/**
 * 
 * @author Automation Team
 */
public class Button extends Common {
	private WebElement btnObject;

	public void verifyButtonText(String locator, String expectedText) throws Exception {
		btnObject = getObject(locator);
		String actualLinkText = btnObject.getText().trim();
		String expLinkText = expectedText.trim();
		GlobalVariables.testVerification.verifyEquals(expLinkText, actualLinkText);
	}

	public String getButtonText(String locator) throws Exception {
		btnObject = getObject(locator);
		String buttonText = btnObject.getText();
		return buttonText;
	}

	public boolean isButtonDisplayed(String locator) throws Exception {
		btnObject = getObject(locator);
		return btnObject.isDisplayed();
	}

	public boolean isButtonEnabled(String locator) throws Exception {
		btnObject = getObject(locator);
		return btnObject.isEnabled();
	}

	public void click(String locator) throws Exception {
		btnObject = getObject(locator);
		btnObject.click();
	}

	public void ClickButtonUsingJavascript(String locator) throws Exception {
		WebDriver driver = Browser.getInstance().getDriver();
		btnObject = getObject(locator);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnObject);
	}

}