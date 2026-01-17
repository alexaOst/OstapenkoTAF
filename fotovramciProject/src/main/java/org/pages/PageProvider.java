package org.pages;

import org.openqa.selenium.WebDriver;

public class PageProvider {

    private WebDriver webDriver;

    public PageProvider(WebDriver webDriver) {
        this.webDriver = webDriver;
    }


    public HomePage getHomePage() {
        return new HomePage(webDriver);
    }
}
