package com.auto.utilities;

import java.io.FileNotFoundException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class reuableComponents  extends BaseTest {
	
	public reuableComponents() throws FileNotFoundException{
		super();
	}

	public void performWebElementTab(WebElement ele) {
		Actions oAct = new Actions(driver);
		oAct.sendKeys(ele, Keys.TAB).build().perform();
	}
	public void performWebElementClick(WebElement ele) {
		Actions oAct = new Actions(driver);
		oAct.click(ele).build().perform();
	}
	public void performWebElementRightClick(WebElement ele) {
		Actions oAct = new Actions(driver);
		oAct.moveToElement(ele).contextClick(ele).build().perform();
	}
	public void clickUsingJS(WebElement element) {
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);

	}
	public void waitforClickable(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(30));; 
		element = wait.until(ExpectedConditions.elementToBeClickable(element));
	}

}	
