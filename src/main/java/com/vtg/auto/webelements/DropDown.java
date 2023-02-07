package com.vtg.auto.webelements;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.vtg.auto.reusablecomponents.Common;

/**
 * 
 * @author Automation Team
 */
public class DropDown extends Common {
	private WebElement drpObject;

	public Select getAlloptionsFromDropDown(String locator) throws Exception {
		drpObject = getObject(locator);
		Select allDropdownOptions = new Select(drpObject);
		return allDropdownOptions;
	}

	public void selectByvisibleText(String locator, String text) throws Exception {
		Select select = getAlloptionsFromDropDown(locator);
		select.selectByVisibleText(text);
	}

	public void selectByIndex(String locator, int index) throws Exception {
		Select select = getAlloptionsFromDropDown(locator);
		select.selectByIndex(index);
	}

	public void selectByValue(String locator, String value) throws Exception {
		Select select = getAlloptionsFromDropDown(locator);
		select.selectByValue(value);
	}

	public void deSelectByVisibleText(String locator, String text) throws Exception {
		Select select = getAlloptionsFromDropDown(locator);
		select.deselectByVisibleText(text);
	}

	public void deSelectByIndex(String locator, int index) throws Exception {
		Select select = getAlloptionsFromDropDown(locator);
		select.deselectByIndex(index);
	}

	public void deSelectByValue(String locator, String value) throws Exception {
		Select select = getAlloptionsFromDropDown(locator);
		select.deselectByValue(value);
	}

	@Deprecated
	public void getFirstSelectedOption(String locator, String value) throws Exception {
		Select select = getAlloptionsFromDropDown(locator);
		select.getFirstSelectedOption();
	}

	public void getFirstSelectedOption(String locator) throws Exception {
		Select select = getAlloptionsFromDropDown(locator);
		select.getFirstSelectedOption();
	}

	@Deprecated
	public List<WebElement> getAllSelectedOptions(String locator, String value) throws Exception {
		Select select = getAlloptionsFromDropDown(locator);
		return select.getAllSelectedOptions();
	}

	public List<WebElement> getAllSelectedOptions(String locator) throws Exception {
		Select select = getAlloptionsFromDropDown(locator);
		return select.getAllSelectedOptions();
	}

	public void deSelectAll(String locator) throws Exception {
		Select select = getAlloptionsFromDropDown(locator);
		select.deselectAll();
	}

	public List<WebElement> getOptions(String locator) throws Exception {
		Select select = getAlloptionsFromDropDown(locator);
		return select.getOptions();
	}

}
