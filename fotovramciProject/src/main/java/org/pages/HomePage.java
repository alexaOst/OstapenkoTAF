package org.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.pages.elements.HeaderOfSite;

import java.time.Duration;

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

    public HomePage openLoginPage() {
        webDriver.get(baseUrl);
        logger.info("Login page was opened by url " + baseUrl);
        return this;
    }

}
