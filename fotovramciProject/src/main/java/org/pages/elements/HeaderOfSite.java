package org.pages.elements;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.pages.CommonActionsWithElements;
import org.pages.FotokameryPage;
import org.pages.PlivkaPage;
import org.pages.PrintPhoto;

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
    @FindBy(xpath = "//span[@class='header__cart-count']")
    private WebElement cartCounter;
    @FindBy(xpath = "(//a[@href='https://print.fotovramci.com/'])[1]")
    private WebElement printPhotoButton;

    public HeaderOfSite(WebDriver webDriver) {
        super(webDriver);
    }

    private Logger logger = Logger.getLogger(getClass());

    public HeaderOfSite hoverOnButtonKupyty() {
        hoverOnElement(kupytyButton);
        return this;
    }

    public PlivkaPage clickOnLinkFilms() {
        waitForElement(openedDropdown);
        waitForElement(plivkaButton);
        clickOnElement(plivkaButton);
        return new PlivkaPage(webDriver);
    }

    public FotokameryPage clickOnLinkFotokamery() {
        waitForElement(openedDropdown);
        waitForElement(fotokameryButton);
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

    public HeaderOfSite pressEnter() {
        pressEnter(searchFiled);
        return this;
    }

    public HeaderOfSite checkCartCounterIsEmpty() {
        checkElementIsZero(cartCounter);
        return this;
    }

    public PrintPhoto clickOnPrintButton() {
        clickOnElement(printPhotoButton);
        return new PrintPhoto(webDriver);
    }
}
