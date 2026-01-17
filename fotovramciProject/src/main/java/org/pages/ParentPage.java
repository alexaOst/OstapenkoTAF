package org.pages;

import org.openqa.selenium.WebDriver;

public class ParentPage extends CommonActionsWithElements {
    protected String baseUrl = "https://fotovramci.com/";

    public ParentPage(WebDriver webDriver) {
        super(webDriver);
    }
}
