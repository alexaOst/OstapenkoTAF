package org.pages;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.utils.ConfigProvider;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Arrays;

public abstract class ParentPage extends CommonActionsWithElements {
    protected String baseUrl = ConfigProvider.configProperties.base_url();
    private Logger logger = Logger.getLogger(getClass());

    public ParentPage(WebDriver webDriver) {
        super(webDriver);
    }

    abstract protected String getRelativeUrl();

    protected void checkUrl() {
        Assert.assertEquals("URL is not expexted"
                , baseUrl + getRelativeUrl(), webDriver.getCurrentUrl());
    }

    protected void checkUrlWithPattern(){
        Assert.assertTrue("URL is not expected \n" +
                        "Expected url: " + baseUrl + getRelativeUrl() +
                        "\n Actual url: " + webDriver.getCurrentUrl(),
                webDriver.getCurrentUrl().matches(baseUrl + getRelativeUrl()));
    }

    protected void checkUrlAndSearchQuery(String expectedSearchQuery) {
        // Verifies that the current URL matches the expected URL with query parameters
        waitForUrlMatches();
        // Asserts that the query parameter "q" matches the expected search query
        assertQueryParamEquals("q", expectedSearchQuery);
    }

    protected void waitForUrlMatches() {
        new WebDriverWait(webDriver, Duration.ofSeconds(ConfigProvider.configProperties.TIME_FOR_IMPLICIT_WAIT()))
                .until(driver ->
                        driver.getCurrentUrl().matches(baseUrl + getRelativeUrl()));
    }

    protected void assertQueryParamEquals(String paramName, String expectedValue) {
        // Retrieves the actual value of the specified query parameter
        String actualValue = getQueryParamValue(paramName);

        // Asserts that the actual value matches the expected value
        Assert.assertEquals(
                "Query parameter '" + paramName + "' does not match expected",
                expectedValue,
                actualValue
        );

        logger.info("Query parameter '" + paramName + "' is correct: '" + actualValue + "'");
    }

    protected String getQueryParamValue(String paramName) {
        try {
            // Parses the current URL into a URI object
            URI uri = URI.create(webDriver.getCurrentUrl());
            // Extracts the query string from the URI
            String query = uri.getQuery();

            // Logs the current URL and query string
            logger.info("Current URL: " + uri + ", query string: " + query);

            // Asserts that the query string is not null
            Assert.assertNotNull("URL does not contain query parameters", query);

            // Splits the query string into key-value pairs and retrieves the value for the specified parameter
            return Arrays.stream(query.split("&"))
                    .map(p -> p.split("=", 2))
                    .filter(p -> p[0].equals(paramName))
                    .map(p -> URLDecoder.decode(p[1], StandardCharsets.UTF_8))
                    .findFirst()
                    .orElseThrow(() ->
                            new AssertionError("Query parameter '" + paramName + "' not found in URL"));
        } catch (Exception e) {
            // Throws a runtime exception if URL parsing fails
            throw new RuntimeException("Failed to parse URL: " + webDriver.getCurrentUrl(), e);
        }
    }

}
