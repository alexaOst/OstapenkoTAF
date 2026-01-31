package org.tests;

import org.baseTest.BaseTest;
import org.junit.Test;

public class AddProductsToCart extends BaseTest {

    @Test
    public void TC002_verifyCartCounter() {

        pageProvider.getHomePage()
                .openHomePage()
                .getHeader()
                .hoverOnButtonKupyty()
                .clickOnLinkFilms()
                .checkIsRedirectToPlivkaPage()
                .clickOnFirstPlivkaOnPage()
                .checkIsRedirectToProductDetailsPage()
                .addProductToCart()
                .checkCartModalWindowIsDisplayed()
                .checkCartCounterInModalWindow("1")
                .closeCartModalWindowWithAction();

        pageProvider.getHomePage()
                .getHeader()
                .hoverOnButtonKupyty()
                .clickOnLinkFotokamery()
                .checkIsRedirectToFotokameryPage()
                .clickOnFirstFotokameraOnPage()
                .checkIsRedirectToProductDetailsPage()
                .addProductToCart()
                .checkCartModalWindowIsDisplayed()
                .checkCartCounterInModalWindow("2")
                .checkItemsDisplayedInDifferentRowsInCartModalWindow()
                .checkItemsInModalWindowHaveTextInLabels("камера", "плівка");

        logger.info("TC002 completed");
    }
}
