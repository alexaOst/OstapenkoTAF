package org.pages;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.interactions.Actions;
import org.pages.elements.HeaderOfSite;

import java.util.List;

public class ProductDetailsPage extends ParentPage {
    public ProductDetailsPage(org.openqa.selenium.WebDriver webDriver) {
        super(webDriver);
    }

    private Logger logger = Logger.getLogger(getClass());

    @Override
    protected String getRelativeUrl() {
        return "products/.*";
    }

    @FindBy(xpath = "//button[contains(text(), 'Додати в кошик')]")
    private WebElement addToCartButton;

    @FindBy(xpath = "//div[@role='dialog' and @data-animation-state='open']")
    private WebElement openedCartModalWindow;

    @FindBy(xpath = "//h2[@class='cart-drawer__heading']//span")
    private WebElement cartCounter;

    @FindBy(xpath = "//tr[@class='cart-drawer-form-item']")
    private List<WebElement> productLocator;

    public ProductDetailsPage checkIsRedirectToProductDetailsPage() {
        checkUrlWithPattern();
        // TODO check some unique element on the page
        logger.info("Product Details page was opened " + webDriver.getCurrentUrl());
        return this;
    }

    public HeaderOfSite getHeader() {
        return new HeaderOfSite(webDriver);
    }

    public ProductDetailsPage addProductToCart() {
        clickOnElement(addToCartButton);
        return this;
    }

    public ProductDetailsPage checkCartModalWindowIsDisplayed() {

        waitForElement(openedCartModalWindow, 5);
        logger.info("Cart modal window is opened");
        return this;
    }

    public ProductDetailsPage checkCartCounterInModalWindow(String numberOfItems) {
        waitForElement(cartCounter, 5);
        checkTextInElement(cartCounter, numberOfItems);
        return this;
    }

    public void closeCartModalWindowWithAction() {

        Actions actions = new Actions(webDriver);
        actions.moveByOffset(10, 10).click().perform();
        logger.info("Cart modal window was closed with action");
    }



    public ProductDetailsPage checkItemsDisplayedInDifferentRowsInCartModalWindow() {
        List<WebElement> products = getElementsList(productLocator, 5);
        if (products.isEmpty()) {
            Assert.fail("Cart is empty");
        }
        Assert.assertEquals(products.size(), 2);
        logger.info("Items are displayed in different rows in cart modal window");

        return this;
    }

    public ProductDetailsPage checkItemsInModalWindowHaveTextInLabels(String... productTitle) {

        checkElementsHaveText(productLocator, 5, productTitle);
        return this;
    }




}
