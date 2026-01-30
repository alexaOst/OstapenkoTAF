package org.pages;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class SearchResultPage extends ParentPage {

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
    @FindBy(xpath = "//li[@class='main-search-results__list-item']")
    private List<WebElement> productList;


    public SearchResultPage checkIsRedirectToSearchResultPage(String expectedSearchQuery) {
        checkUrlAndSearchQuery(expectedSearchQuery);
        return this;
    }


    public SearchResultPage checkSearchResultsListIsNotEmpty() {
        checkProductsListIsNotEmpty(productLocator);
        return this;
    }


    public SearchResultPage checkProductListItemsHaveTextInLabels(String... productTitle) {

        checkElementsHaveText(productList, 5, productTitle);
        return this;
    }


}
