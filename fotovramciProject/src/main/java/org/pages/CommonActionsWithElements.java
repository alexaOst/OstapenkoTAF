package org.pages;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class CommonActionsWithElements {
    protected WebDriver webDriver;

    private Logger logger = Logger.getLogger(getClass());

    public CommonActionsWithElements(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this); // init elements declare by "@FindBy"
    }

    private String getElementName(WebElement webElement) {
        try {
            return webElement.getAccessibleName();
        } catch (Exception e) {
            return "";
        }
    }

    protected void clickOnElement(WebElement webElement) {
        try {
            webElement.click();
            logger.info("Element was clicked");
        } catch (Exception e) {
            printErrorAndStopTest();
        }
    }

    protected void hoverOnElement(WebElement element) {
        try {
            Actions actions = new Actions(webDriver);
            actions.moveToElement(element).perform();
            logger.info("Hovered over element");
        } catch (Exception e) {
            printErrorAndStopTest();
        }
    }

    protected WebElement waitForElement(WebElement element, int timeoutInSeconds) {
        return new WebDriverWait(webDriver, Duration.ofSeconds(timeoutInSeconds))
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void selectTextInDropDown(WebElement webElement, String text) {
        try {
            Select select = new Select(webElement);
            select.selectByVisibleText(text);
            logger.info("Text '" + text + "' was selected in DropDown");
        } catch (Exception e) {
            printErrorAndStopTest();
        }
    }


    protected void checkTextInElement(WebElement webElement, String expectedText) {
        try {
            String actualText = webElement.getText();
            Assert.assertTrue(
                    "Text in element is not as expected. " +
                            "Expected (equals or contains): " + expectedText +
                            ", Actual: " + actualText,
                    actualText.equals(expectedText) || actualText.contains(expectedText)
            );

            logger.info("Text in element matches expected text: " + expectedText);
        } catch (Exception e) {
            printErrorAndStopTest();
        }
    }

    private void printErrorAndStopTest() {
        logger.error("Error while working with element");
        Assert.fail("Error while working with element"); // wrote info into report
    }

}
