package org.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class SearchResultPage extends ParentPage {

    private By nextButtonLocator = By.className("pagination__next-button");

    public SearchResultPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected String getRelativeUrl() {
        return "search.*";
    }

    private Logger logger = Logger.getLogger(getClass());

    @FindBy(xpath = "//div[@role='status']")
    private WebElement productLocator;
    @FindBy(xpath = "//a[@class='product-card__link']")
    private List<WebElement> productList;



    public SearchResultPage checkIsRedirectToSearchResultPage(String expectedSearchQuery) {
        checkUrlAndSearchQuery(expectedSearchQuery);
        return this;
    }


    public SearchResultPage checkSearchResultsListIsNotEmpty() {
        checkElementIsNotZero(productLocator, "cart");
        return this;
    }


    public SearchResultPage checkProductListItemsHaveTextInLabels(String... productTitle) {
        checkElementsHaveTextAcrossPages(productList, nextButtonLocator, productTitle);
        return this;
    }


}
