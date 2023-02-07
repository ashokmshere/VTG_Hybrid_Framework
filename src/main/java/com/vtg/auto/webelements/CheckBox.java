package com.vtg.auto.webelements;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.vtg.auto.reusablecomponents.Browser;
import com.vtg.auto.reusablecomponents.Common;

/**
 * 
 * @author Automation Team
 */
public class CheckBox extends Common {
	private WebElement checkBoxObject;

	public boolean checkBoxIsSelected(String locator) throws Exception {
		checkBoxObject = getObject(locator);
		return checkBoxObject.isSelected();
	}

	public boolean checkBoxIsDisplayed(String locator) throws Exception {
		checkBoxObject = getObject(locator);
		return checkBoxObject.isDisplayed();
	}

	public boolean checkBoxIsEnabled(String locator) throws Exception {
		checkBoxObject = getObject(locator);
		return checkBoxObject.isEnabled();
	}

	public void selectAllCheckBox(String locator) throws Exception {
		List<WebElement> checkObjects = getObjects(locator);
		for (WebElement checkObject : checkObjects) {
			if (!checkObject.isSelected()) {
				checkObject.click();
			}
		}
	}

	public void unSelectAllCheckBox(String locator) throws Exception {
		List<WebElement> checkObjects = getObjects(locator);
		for (WebElement checkObject : checkObjects) {
			if (checkObject.isSelected()) {
				checkObject.click();
			}
		}
	}

	public void selectCheckBox(String locator) {
		try {
			checkBoxObject = getObject(locator);
			if (!checkBoxObject.isSelected()) {
				checkBoxObject.click();
			}
		} catch (Throwable t) {
		}
	}

	public void unSelectCheckBox(String locator) {
		try {
			checkBoxObject = getObject(locator);
			if (checkBoxObject.isSelected()) {
				checkBoxObject.click();
			}
		} catch (Throwable t) {
		}
	}

	public void SelectCheckBoxUsingJavascript(String locator) throws Exception {
		JavascriptExecutor js = (JavascriptExecutor) Browser.getInstance().getDriver();
		checkBoxObject = getObject(locator);
		js.executeScript("arguments[0].click();", checkBoxObject);
	}

}
