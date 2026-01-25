package org.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class PlivkaPage extends ParentPage {

    private Logger logger = Logger.getLogger(getClass());

    public PlivkaPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected String getRelativeUrl() {
        return "collections/plivka";
    }

    public PlivkaPage checkIsRedirectToPlivkaPage() {
        checkUrl();
        // TODO check some unique element on the page
        return this;
    }

}
