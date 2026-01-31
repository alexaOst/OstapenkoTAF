package org.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PrintPhoto extends ParentPage {

    @FindBy(xpath = "//a[@href='https://print.fotovramci.com/upload-photos']")
    private WebElement orderButton;

    public PrintPhoto(org.openqa.selenium.WebDriver webDriver) {
        super(webDriver);
    }

    private Logger logger = Logger.getLogger(getClass());


    @Override
    protected String getRelativeUrl() {
        return "https://print.fotovramci.com/";
    }

    public PrintPhoto checkIsRedirectToPrintPhotoPage() {
        logger.info("Plivka page was opened " + webDriver.getCurrentUrl());
        return this;
    }

    public UploadPhoto clickOrderButton() {
        clickOnElement(orderButton);
        return new UploadPhoto(webDriver);
    }
}
