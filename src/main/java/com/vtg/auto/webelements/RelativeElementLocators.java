package com.vtg.auto.webelements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;

import com.vtg.auto.reusablecomponents.Browser;
import com.vtg.auto.reusablecomponents.Common;

public class RelativeElementLocators extends Common {
    private WebElement webReferenceObject;

    public WebElement findByRelativeElement(String location, String referenceLocator, String locatorName) throws InterruptedException {
        WebDriver driver = Browser.getInstance().getDriver();
        webReferenceObject = getObject(referenceLocator);
        switch (location) {
            case "above":
                return driver.findElement(RelativeLocator.with(getLocator(locatorName)).above(webReferenceObject));
            case "below":
                return driver.findElement(RelativeLocator.with(getLocator(locatorName)).below(webReferenceObject));
            case "right":
                return driver.findElement(RelativeLocator.with(getLocator(locatorName)).toRightOf(webReferenceObject));
            case "left":
                return driver.findElement(RelativeLocator.with(getLocator(locatorName)).toLeftOf(webReferenceObject));
            case "near":
                return driver.findElement(RelativeLocator.with(getLocator(locatorName)).near(webReferenceObject));
            default:
                return null;
        }
    }
}
