package org.pages.elements;

import org.apache.log4j.Logger;
import org.checkerframework.checker.units.qual.K;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.pages.CommonActionsWithElements;
import org.pages.FotokameryPage;
import org.pages.HomePage;
import org.pages.PlivkaPage;

import java.time.Duration;

public class HeaderOfSite extends CommonActionsWithElements {

    @FindBy(xpath = "//summary[@data-href='/collections/all']")
    private WebElement kupytyButton;

    @FindBy(xpath = "//a[@href='/collections/plivka']")
    private WebElement plivkaButton;

    @FindBy(xpath = "//a[@href='/collections/fotokamery']")
    private WebElement fotokameryButton;

    @FindBy(xpath = "//div[@data-opened='true']")
    private WebElement openedDropdown;

    Logger logger = Logger.getLogger(getClass());



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




}
