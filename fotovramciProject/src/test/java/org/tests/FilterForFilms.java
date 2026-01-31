package org.tests;

import org.baseTest.BaseTest;
import org.junit.Test;

public class FilterForFilms extends BaseTest {

    @Test
    public void TC001_testFilterForFilms() {
        pageProvider.getHomePage()
                .openHomePage()
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

        logger.info("TC001 completed");
    }
}