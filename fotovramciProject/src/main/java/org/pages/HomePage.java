package org.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.pages.elements.HeaderOfSite;

public class HomePage extends ParentPage {

    private Logger logger = Logger.getLogger(getClass());

    public HomePage(WebDriver webDriver) {
        super(webDriver);
    }

    public HeaderOfSite getHeader() {
        return new HeaderOfSite(webDriver);
    }

    @Override
    protected String getRelativeUrl() {
        return "/";
    }

    public HomePage openHomePage() {
        webDriver.get(baseUrl);
        logger.info("Home page was opened by url " + baseUrl);
        return this;
    }

}
