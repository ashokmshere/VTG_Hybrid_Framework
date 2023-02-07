package com.vtg.auto.webelements;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.vtg.auto.reusablecomponents.Browser;
import com.vtg.auto.reusablecomponents.Common;

/**
 * 
 * @author Automation Team
 */
public class RadioButton extends Common {

	private WebElement radioObject;

	public boolean radioOptionIsSelected(String locator) throws Exception {
		radioObject = getObject(locator);
		return radioObject.isSelected();
	}

	public boolean radioOptionIsDisplayed(String locator) throws Exception {
		radioObject = getObject(locator);
		return radioObject.isDisplayed();
	}

	public boolean radioOptionIsEnabled(String locator) throws Exception {
		radioObject = getObject(locator);
		return radioObject.isEnabled();
	}

	@Deprecated
	public void selectAllRadioOption(String locator) throws Exception {
		WebDriver driver = Browser.getInstance().getDriver();
		List<WebElement> checkObjects = driver.findElements(By.xpath(locator));
		for (WebElement checkObject : checkObjects) {
			if (!checkObject.isSelected()) {
				checkObject.click();
			}
		}
	}

	public void selectRadioOption(String locator) throws Exception {
		try {
			radioObject = getObject(locator);
			if (!radioObject.isSelected()) {
				radioObject.click();
			}
		} catch (Throwable t) {
		}
	}

	@Deprecated
	public void unSelectRadioOption(String locator) {

		try {
			radioObject = getObject(locator);
			if (radioObject.isSelected()) {
				radioObject.click();
			}
		} catch (Throwable t) {
		}

	}

	public void ClickRadioButtonUsingJavascript(String locator) throws Exception {
		WebDriver driver = Browser.getInstance().getDriver();
		radioObject = getObject(locator);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", radioObject);
	}
}
