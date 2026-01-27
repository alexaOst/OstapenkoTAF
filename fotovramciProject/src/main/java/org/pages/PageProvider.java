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

    public PlivkaPage getPlivkaPage() {
        return new PlivkaPage(webDriver);
    }

    public FotokameryPage getFotokameryPage() {
        return new FotokameryPage(webDriver);
    }
}
