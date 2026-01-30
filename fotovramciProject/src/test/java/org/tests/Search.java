package org.tests;

import org.baseTest.BaseTest;
import org.junit.Test;

public class Search extends BaseTest {

    @Test
    public void TC003_resultOfSearch() {
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

        // TODO fix single page issue in last step

        logger.info("TC003 completed");
    }
}
