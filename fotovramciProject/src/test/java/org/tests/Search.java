package org.tests;

import org.baseTest.BaseTest;
import org.junit.Test;

import static org.data.TestData.TEXT_FOR_SEARCH;

public class Search extends BaseTest {

    @Test
    public void TC003_resultOfSearch() {
        pageProvider.getHomePage()
                .openHomePage()
                .getHeader()
                .clickOnSearch()
                .enterTextIntoSearchInput(TEXT_FOR_SEARCH)
                .pressEnter();

        pageProvider.getSearchResultPage()
                .checkIsRedirectToSearchResultPage(TEXT_FOR_SEARCH)
                .checkSearchResultsListIsNotEmpty()
                .checkProductListItemsHaveTextInLabels(TEXT_FOR_SEARCH);

        logger.info("TC003 completed");
    }
}
