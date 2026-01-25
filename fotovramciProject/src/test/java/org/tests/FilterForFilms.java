package org.tests;

import org.apache.log4j.Logger;
import org.baseTest.BaseTest;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class FilterForFilms extends BaseTest {

    private WebDriver webDriver;

    private Logger logger = Logger.getLogger(getClass());

    @Test
    public void testFilterForFilms() {
        logger.info("Test for filter films started");
        pageProvider.getHomePage()
                .openLoginPage()
                .getHeader()
                .hoverOnButtonKupyty()
                .clickOnLinkFilms()
                .checkIsRedirectToPlivkaPage();


        // TO DO implement test steps for filtering films
        logger.info("Test for filter films completed");
    }
}