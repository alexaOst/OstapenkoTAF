package org.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.pages.elements.HeaderOfSite;

public class HomePage extends ParentPage {

    private Logger logger = Logger.getLogger(getClass());

    @FindBy(xpath = "(//div[contains(text(),'Продано')])[1]/ancestor::div[contains(@class,'product-card__clickable-area')]")
    private WebElement outOfStockProduct;

    public HomePage(WebDriver webDriver) {
        super(webDriver);
    }

    public HeaderOfSite getHeader() {
        return new HeaderOfSite(webDriver);
    }

    @Override
    protected String getRelativeUrl() {
        return "/";
    }

    public HomePage openHomePage() {
        webDriver.get(baseUrl);
        logger.info("Home page was opened by url " + baseUrl);
        return this;
    }

    public ProductDetailsPage clickOnOutOfStockProduct() {
        clickOnElement(outOfStockProduct);
        return new ProductDetailsPage(webDriver);
    }
}
