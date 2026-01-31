package org.pages;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class FotokameryPage extends ParentPage{
    public FotokameryPage(org.openqa.selenium.WebDriver webDriver) {
        super(webDriver);
    }

    private Logger logger = Logger.getLogger(getClass());

    @Override
    protected String getRelativeUrl() {
        return "collections/fotokamery";
    }

    @FindBy(xpath = "//div[@class='h4 spf-product-card__title']")
    private List<WebElement> productLocator;

    public FotokameryPage checkIsRedirectToFotokameryPage() {
        checkUrl();
        logger.info("Fotokamery page was opened " + webDriver.getCurrentUrl());
        return this;
    }

    public ProductDetailsPage clickOnFirstFotokameraOnPage() {
        List<WebElement> products = getElementsList(productLocator);
        if (products.isEmpty()) {
            logger.error("No products found on the page to click");
            Assert.fail("No products found on the page to click");
        }
        clickOnElement(products.get(0));
        return new ProductDetailsPage(webDriver);
    }

}
