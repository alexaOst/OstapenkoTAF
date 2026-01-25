package org.pages;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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

    public PlivkaPage checkIsRedirectToPlivkaPage() {
        checkUrl();
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
}
