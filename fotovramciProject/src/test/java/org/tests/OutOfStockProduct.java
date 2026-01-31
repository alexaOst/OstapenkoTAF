package org.tests;

import org.baseTest.BaseTest;
import org.junit.*;

public class OutOfStockProduct extends BaseTest {

    @Before
    public void setUp() {
        pageProvider.getHomePage()
                .openHomePage()
                .getHeader()
                .checkCartCounterIsEmpty();
    }

    @Test
    public void TC004_verifyAddOutOfStockProductToCart() {
        pageProvider.getHomePage()
                .clickOnOutOfStockProduct()
                .checkIsRedirectToProductDetailsPage()
                .checkTextOutOfStockLabelOnButton("Продано")
                .clickOnSoldOutButton()
                .getHeader()
                .checkCartCounterIsEmpty();

        logger.info("TC004 completed");
    }

}
