package org.pages;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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

    protected List<WebElement> getElementsList(List<WebElement> elements, int timeoutSec) {
        waitUntilAllVisible(elements, timeoutSec);
        if (elements == null) {
            return Collections.emptyList();
        }
        return elements;
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

    protected WebElement waitForElement(WebElement webElement, int timeoutInSeconds) {
        return new WebDriverWait(webDriver, Duration.ofSeconds(timeoutInSeconds))
                .until(ExpectedConditions.elementToBeClickable(webElement));
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


    protected void waitUntilAllVisible(List<WebElement> elements, int timeoutSec) {
        new WebDriverWait(webDriver, Duration.ofSeconds(timeoutSec))
                .until(driver -> elements.size() > 0);
    }

    protected void waitUntilClickable(WebElement element, int timeoutSec) {
        new WebDriverWait(webDriver, Duration.ofSeconds(timeoutSec))
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    protected boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            printErrorAndStopTest();
            return false;
        }
    }



    /**
     * Переходить на наступну сторінку списку
     */
    protected boolean goToNextPageIfExistsSafe(List<WebElement> productList, By nextButtonLocator) {
        List<WebElement> buttons = webDriver.findElements(nextButtonLocator);

        if (buttons.isEmpty()) {
            logger.info("Next page button not found — це остання сторінка");
            return false;
        }

        WebElement nextPageButton = buttons.get(0);

        try {
            if (!nextPageButton.isDisplayed() || !nextPageButton.isEnabled()) {
                logger.info("Next page button is disabled — це остання сторінка");
                return false;
            }

            List<WebElement> oldProducts = new ArrayList<>(productList);

            clickOnElement(nextPageButton);
            logger.info("Navigated to next page");

            if (!oldProducts.isEmpty()) {
                new WebDriverWait(webDriver, Duration.ofSeconds(30))
                        .until(ExpectedConditions.stalenessOf(oldProducts.get(0)));
            }

            return true;

        } catch (StaleElementReferenceException e) {
            logger.warn("Next page button became stale — можливо, це остання сторінка");
            return false;
        }
    }



    /**
     * Універсальний метод для перевірки, що список елементів містить очікуваний текст.
     * Можна використовувати для будь-якої сторінки і будь-якого списку з @FindBy.
     *
     * @param elements      список елементів @FindBy
     * @param nextButtonBy  By кнопки наступної сторінки
     * @param timeoutSec    час очікування видимості елементів
     * @param expectedTexts тексти, які має містити кожен елемент
     */
    protected void checkElementsHaveText(List<WebElement> elements, By nextButtonBy, int timeoutSec, String... expectedTexts) {
        SoftAssert softAssert = new SoftAssert();
        int pageNumber = 1;

        do {
            List<WebElement> currentList = getElementsList(elements, timeoutSec);
            logger.info("Checking page " + pageNumber + " with " + currentList.size() + " elements");

            for (int i = 0; i < currentList.size(); i++) {
                WebElement element = currentList.get(i);
                String text = element.getText();

                boolean containsAny = Arrays.stream(expectedTexts).anyMatch(text::contains);

                if (!containsAny) {
                    logger.warn((i + 1) + ". Element does NOT contain any of texts " +
                            Arrays.toString(expectedTexts) + " | Actual text: " + text);
                } else {
                    logger.info((i + 1) + ". Element contains at least one of texts " +
                            Arrays.toString(expectedTexts) + " | Actual text: " + text);
                }

                softAssert.assertTrue(containsAny,
                        "Element does not contain any of expected texts " +
                                Arrays.toString(expectedTexts) +
                                ". Actual text: " + text);
            }

            pageNumber++;

        } while (goToNextPageIfExistsSafe(getElementsList(elements, timeoutSec), nextButtonBy));

        softAssert.assertAll();
    }


    protected void checkElementsHaveTextSinglePage(List<WebElement> elements, int timeoutSec, String... expectedTexts) {
        SoftAssert softAssert = new SoftAssert();

        List<WebElement> currentList = getElementsList(elements, timeoutSec);
        logger.info("Checking " + currentList.size() + " elements on single page");

        for (int i = 0; i < currentList.size(); i++) {
            WebElement element = currentList.get(i);
            String text = element.getText();

            boolean containsAny = Arrays.stream(expectedTexts).anyMatch(text::contains);

            if (!containsAny) {
                logger.warn((i + 1) + ". Element does NOT contain any of texts " +
                        Arrays.toString(expectedTexts) + " | Actual text: " + text);
            } else {
                logger.info((i + 1) + ". Element contains at least one of texts " +
                        Arrays.toString(expectedTexts) + " | Actual text: " + text);
            }

            softAssert.assertTrue(containsAny,
                    "Element does not contain any of expected texts " +
                            Arrays.toString(expectedTexts) +
                            ". Actual text: " + text);
        }

        softAssert.assertAll();
    }



    private void printErrorAndStopTest() {
        logger.error("Error while working with element");
        Assert.fail("Error while working with element"); // wrote info into report
    }

}
