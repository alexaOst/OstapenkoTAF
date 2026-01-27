package org.pages;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class PlivkaPage extends ParentPage {

    private Logger logger = Logger.getLogger(getClass());

    public PlivkaPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected String getRelativeUrl() {
        return "collections/plivka";
    }

    @FindBy(xpath = "//span[contains(text(), 'Формат')]")
    private WebElement buttonFormatSelector;

    @FindBy(xpath = "//button[@title='135 тип (35 мм)']")
    private WebElement button135mmFormat;

    @FindBy(xpath = "//a[@data-fvalue='135 тип (35 мм)']")
    private WebElement selected135mmFormatLabel;

    @FindBy(xpath = "//a[@data-fvalue='200 iso']")
    private WebElement selected200ISOLabel;

    @FindBy(xpath = "//span[contains(text(), 'Чутливість')]")
    private WebElement buttonISOSelector;

    @FindBy(xpath = "//button[@title='200 iso']")
    private WebElement button200ISO;

    @FindBy(xpath = "//span[@class='gf-summary']//b")
    private WebElement productListCounter;

    @FindBy(xpath = "//div[@class='h4 spf-product-card__title']")
    private List<WebElement> productLocator;

    private By nextButtonLocator = By.className("next");

    public PlivkaPage checkIsRedirectToPlivkaPage() {
        checkUrl();
        logger.info("Plivka page was opened " + webDriver.getCurrentUrl());

        // TODO check some unique element on the page
        return this;
    }

    public PlivkaPage selectTextInDropDown(String textForSelection) {
        selectTextInDropDown(buttonFormatSelector, textForSelection);
        return this;
    }

    public PlivkaPage clickFormatSelector() {
        clickOnElement(buttonFormatSelector);
        logger.info("Format selector clicked");
        return this;
    }

    public PlivkaPage click135mmFormat() {
        clickOnElement(button135mmFormat);
        logger.info("135mm format selected");
        return this;
    }

    public PlivkaPage check135mmFormatLabel(String textOfMessage) {
        checkTextInElement(selected135mmFormatLabel, textOfMessage);
        return this;
    }

    public PlivkaPage check200ISOLabel(String textOfMessage) {
        checkTextInElement(selected200ISOLabel, textOfMessage);
        return this;
    }

    public PlivkaPage clickOnISOSelector() {
        clickOnElement(buttonISOSelector);
        logger.info("Format selector clicked");
        return this;
    }

    public PlivkaPage click200ISOOption() {
        clickOnElement(button200ISO);
        logger.info("135mm format selected");
        return this;
    }

    public PlivkaPage checkProductListIsNotEmpty() {
        if (productListCounter.getText().equals("0")) {
            logger.error("Product list is empty!");
            Assert.fail("Product list is empty!");
        } else {
            logger.info("Product list is not empty. Products found: " + productListCounter.getText());
        }
        return this;
    }

    public PlivkaPage checkProductListItemsHaveTextInLabels(String... productTitle) {
//        checkElementsHaveTextSinglePage(productLocator, 5, productTitle);
        checkElementsHaveText(productLocator, nextButtonLocator, 5, productTitle);

        return this;
    }



    private boolean goToNextPageIfExistsSafe() {
        List<WebElement> buttons = webDriver.findElements(By.className("next"));
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

            // зберігаємо старі продукти для перевірки оновлення DOM
            List<WebElement> oldProducts = getElementsList(productLocator, 5);

            clickOnElement(nextPageButton);
            logger.info("Navigated to next page");

            // чекаємо, поки старі продукти стануть неактуальними (DOM оновиться)
            if (!oldProducts.isEmpty()) {
                WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
                wait.until(ExpectedConditions.stalenessOf(oldProducts.get(0)));
            }

            // дочекаємось нових продуктів
            waitUntilAllVisible(productLocator, 30);

            return true;

        } catch (StaleElementReferenceException e) {
            logger.warn("Next page button became stale — можливо, це остання сторінка");
            return false;
        }
    }

    public ProductDetailsPage clickOnFirstPlivkaOnPage() {

        List<WebElement> products = getElementsList(productLocator, 5);
        if (products.isEmpty()) {
            logger.error("No products found on the page to click");
            Assert.fail("No products found on the page to click");
        }
        clickOnElement(products.get(0));
        return new ProductDetailsPage(webDriver);
    }
}
