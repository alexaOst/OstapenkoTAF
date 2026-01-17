package org.pages.elements;

import org.checkerframework.checker.units.qual.K;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.pages.CommonActionsWithElements;
import org.pages.HomePage;

public class HeaderOfSite extends CommonActionsWithElements {

    @FindBy(xpath = "//summary[@data-href='/collections/all']")
    private WebElement kupytyButton;

    public HeaderOfSite(WebDriver webDriver) {
        super(webDriver);
    }

    public HomePage clickOnButtonKupyty() {
        clickOnElement(kupytyButton);
        return new HomePage(webDriver);
    }
}
