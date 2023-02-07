package com.vtg.auto.webelements;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.vtg.auto.reusablecomponents.Browser;
import com.vtg.auto.reusablecomponents.Common;

/**
 * 
 * @author Automation Team
 */
public class TextBox extends Common {
	private WebElement txtObject;

	public WebElement getTextElement(String locator) throws Exception {
		txtObject = getObject(locator);
		return txtObject;
	}
	

	public void setText(String locator, String valueToBeEntered) throws Exception {
		getTextElement(locator).clear();
		getTextElement(locator).sendKeys(valueToBeEntered);
	}
	
	public String setSecureText(String locator, String valueToBeEntered) throws Exception {
		txtObject = getTextElement(locator);
		String str = decode(valueToBeEntered);
		txtObject.clear();
		txtObject.sendKeys(str);
		return str;
	}

	public void validateText(String locator, String textToBeVerified) throws Exception {
		assertText(locator, textToBeVerified);
	}

	public void clearText(String locator) throws Exception {
		txtObject = getTextElement(locator);
		txtObject.clear();
	}

	public void verifyLinkText(String locator, String expectedText) throws Exception {

		txtObject = getTextElement(locator);
		assertText(locator, expectedText);

	}

	public void assertText(String locator, String expectedLinkText) throws Exception {
		txtObject = getTextElement(locator);
		String actualLinkText = txtObject.getText().trim();
		String expLinkText = expectedLinkText.trim();
		testVerification.verifyEquals(expLinkText, actualLinkText);

	}

	public boolean verifyText(String locator, String expectedLinkText) throws Exception {
		txtObject = getTextElement(locator);
		String actualLinkText = txtObject.getText().trim();
		String expLinkText = expectedLinkText.trim();
		return expLinkText.equalsIgnoreCase(actualLinkText);

	}

	public static String decode(String strEncodedString) {
		String strActualString = null;
		try {
			strActualString = new String(Base64.decodeBase64(strEncodedString.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strActualString;
	}

	public String getText(String locator) throws Exception {

		txtObject = getTextElement(locator);
		return txtObject.getText();

	}

	public void VerifyAttributeValue(String locator, String attributeType, String expectedValue) throws Exception {
		String actualLinkText = getAttributeValue(locator, attributeType);
		testVerification.verifyEquals(expectedValue.trim(), actualLinkText.trim());
	}

	public String getAttributeValue(String locator, String attributeType) throws Exception {
		txtObject = getTextElement(locator);
		return txtObject.getAttribute(attributeType).trim();
	}

	/**
	 * �*Deprecated verrifyText method. �* �* @deprecated use {@link #verifyText()}
	 * instead.�� �
	 */
	@Deprecated
	public boolean verrifyText(String locator, String expectedLinkText) throws Exception {
		txtObject = getTextElement(locator);
		String actualLinkText = txtObject.getText().trim();
		String expLinkText = expectedLinkText.trim();
		return expLinkText.equalsIgnoreCase(actualLinkText);

	}
	public void setValueUsingJavaScript(String locatorKey, String valueToBeSet) throws Exception {
		WebDriver driver = Browser.getInstance().getDriver();
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		WebElement btnObject = getObject(locatorKey);
		jse.executeScript("arguments[0].value='" + valueToBeSet + "';", btnObject);
	}

}
