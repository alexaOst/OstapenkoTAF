package org.pages.elements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.pages.CommonActionsWithElements;
import org.pages.FotokameryPage;
import org.pages.PlivkaPage;

public class HeaderOfSite extends CommonActionsWithElements {

    @FindBy(xpath = "//summary[@data-href='/collections/all']")
    private WebElement kupytyButton;

    @FindBy(xpath = "//a[@href='/collections/plivka']")
    private WebElement plivkaButton;

    @FindBy(xpath = "//a[@href='/collections/fotokamery']")
    private WebElement fotokameryButton;

    @FindBy(xpath = "//div[@data-opened='true']")
    private WebElement openedDropdown;

    @FindBy(xpath = "//a[@href='/search']")
    private WebElement searchButton;

    @FindBy(xpath = "//input[@class='gl-d-searchbox-input']")
    private WebElement searchFiled;

    public HeaderOfSite(WebDriver webDriver) {
        super(webDriver);
    }

    public HeaderOfSite hoverOnButtonKupyty() {
        hoverOnElement(kupytyButton);
        return this;
    }

    public PlivkaPage clickOnLinkFilms() {
        waitForElement(openedDropdown, 5);
        waitForElement(plivkaButton, 5);
        clickOnElement(plivkaButton);
        return new PlivkaPage(webDriver);
    }

    public FotokameryPage clickOnLinkFotokamery() {
        waitForElement(openedDropdown, 5);
        waitForElement(fotokameryButton, 5);
        clickOnElement(fotokameryButton);
        return new FotokameryPage(webDriver);
    }

    public HeaderOfSite clickOnSearch() {
        clickOnElement(searchButton);

        return this;
    }

    public HeaderOfSite enterTextIntoSearchInput(String inputText) {
        clearAndEnterTextIntoElement(searchFiled, inputText);
        return this;
    }
}
