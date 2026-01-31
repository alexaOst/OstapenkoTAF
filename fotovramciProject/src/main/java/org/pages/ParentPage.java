package org.pages;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.utils.ConfigProvider;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public abstract class ParentPage extends CommonActionsWithElements {
    protected String baseUrl = ConfigProvider.configProperties.base_url();
    private Logger logger = Logger.getLogger(getClass());

    public List<WebElement> webelementsList;

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
        checkUrlWithQuery();
        // Asserts that the query parameter "q" matches the expected search query
        assertQueryParamEquals("q", expectedSearchQuery);
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

        // Logs the result of the query parameter validation
        logger.info("Query parameter '" + paramName + "' is correct: '" + actualValue + "'");
    }

    protected void checkUrlWithQuery() {
        // Retrieves the current URL
        String actualUrl = webDriver.getCurrentUrl();
        // Constructs the expected URL regex based on the base URL and relative URL
        String expectedRegex = baseUrl + getRelativeUrl();

        // Asserts that the actual URL matches the expected regex
        Assert.assertTrue(
                "URL is not expected \nExpected regex: " + expectedRegex +
                        "\nActual: " + actualUrl,
                actualUrl.matches(expectedRegex)
        );
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
