package org.tests;

import org.baseTest.BaseTest;
import org.junit.Test;

public class Search extends BaseTest {

    @Test
    public void TC003_resultOfSerarch() {
        pageProvider.getHomePage()
                .openHomePage()
                .getHeader()
                .clickOnSearch()
                .enterTextIntoSearchInput("kodak")
                .pressEnter();

        pageProvider.getSearchResultPage()
                .checkIsRedirectToSearchResultPage("kodak")
                .checkSearchResultsListIsNotEmpty()

                .checkProductListItemsHaveTextInLabels("kodak");
        ;
        logger.info("TC003 completed");
    }
}
