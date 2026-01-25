package org.tests;

import org.apache.log4j.Logger;
import org.baseTest.BaseTest;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class FilterForFilms extends BaseTest {

    private WebDriver webDriver;

    private Logger logger = Logger.getLogger(getClass());

    @Test
    public void TC001_testFilterForFilms() {
        logger.info("Test for filter films started");
        pageProvider.getHomePage()
                .openLoginPage()
                .getHeader()
                .hoverOnButtonKupyty()
                .clickOnLinkFilms()
                .checkIsRedirectToPlivkaPage()
                .clickFormatSelector()
                .click135mmFormat()
                .check135mmFormatLabel("135 тип (35 мм)")

                .clickOnISOSelector()
                .click200ISOOption()
                .check200ISOLabel("200")
                .checkProductListIsNotEmpty()

                .checkProductListItemsHaveTextInLabels("135", "200");

        // TODO implement test steps for filtering films
        logger.info("Test for filter films completed");
    }
}