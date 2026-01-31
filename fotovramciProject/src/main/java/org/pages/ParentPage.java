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
        checkUrlWithQuery();
        assertQueryParamEquals("q", expectedSearchQuery);
    }

    protected void assertQueryParamEquals(String paramName, String expectedValue) {
        String actualValue = getQueryParamValue(paramName);

        Assert.assertEquals(
                "Query parameter '" + paramName + "' does not match expected",
                expectedValue,
                actualValue
        );

        logger.info("Query parameter '" + paramName + "' is correct: '" + actualValue + "'");
    }

    protected void checkUrlWithQuery() {
        String actualUrl = webDriver.getCurrentUrl();
        String expectedRegex = baseUrl + getRelativeUrl();

        Assert.assertTrue(
                "URL is not expected \nExpected regex: " + expectedRegex +
                        "\nActual: " + actualUrl,
                actualUrl.matches(expectedRegex)
        );
    }

    protected String getQueryParamValue(String paramName) {
        try {
            URI uri = URI.create(webDriver.getCurrentUrl());
            String query = uri.getQuery();

            logger.info("Current URL: " + uri + ", query string: " + query);

            Assert.assertNotNull("URL does not contain query parameters", query);

            return Arrays.stream(query.split("&"))
                    .map(p -> p.split("=", 2))
                    .filter(p -> p[0].equals(paramName))
                    .map(p -> URLDecoder.decode(p[1], StandardCharsets.UTF_8))
                    .findFirst()
                    .orElseThrow(() ->
                            new AssertionError("Query parameter '" + paramName + "' not found in URL"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse URL: " + webDriver.getCurrentUrl(), e);
        }
    }

}
