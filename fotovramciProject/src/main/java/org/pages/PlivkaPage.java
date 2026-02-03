package org.pages;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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
        checkElementIsNotZero(productListCounter, "product list");
        return this;
    }

    public PlivkaPage checkProductListItemsHaveTextInLabels(String... productTitle) {
        checkElementsHaveTextAcrossPages(productLocator, nextButtonLocator, productTitle);
        return this;
    }

    public ProductDetailsPage clickOnFirstPlivkaOnPage() {

        List<WebElement> products = getElementsList(productLocator);
        if (products.isEmpty()) {
            logger.error("No products found on the page to click");
            Assert.fail("No products found on the page to click");
        }
        clickOnElement(products.get(0));
        return new ProductDetailsPage(webDriver);
    }
}
