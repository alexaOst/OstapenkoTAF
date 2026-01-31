package org.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class UploadPhoto extends ParentPage {

    @FindBy(xpath = "uploadPhotoButton")
    private WebElement uploadPhotoButton;
    @FindBy(xpath = "//div[@class='text-error alarm']")
    private WebElement invalidMessage;

    public UploadPhoto(org.openqa.selenium.WebDriver webDriver) {
        super(webDriver);
    }

    private Logger logger = Logger.getLogger(getClass());


    @Override
    protected String getRelativeUrl() {
        return "https://print.fotovramci.com/upload-photos";
    }

    public UploadPhoto checkIsRedirectToUploadPhotoPage() {
        logger.info("Upload photo page was opened " + webDriver.getCurrentUrl());
        return this;
    }

    public UploadPhoto clickUpoloadButton() {
        clickOnElement(uploadPhotoButton);
        return this;
    }









    @FindBy(css = "input.file-input")
    private WebElement fileInput;

    @FindBy(xpath = "(//div[@class='title-block'])[2]")
    private WebElement uploadedPhotoPreview;

    private static final int UPLOAD_TIMEOUT_SEC = 10;

    public UploadPhoto uploadPhoto(String... fileNames){
        uploadPhotos( fileInput, uploadedPhotoPreview, fileNames);
        return this;

    }

    public UploadPhoto checkErrorMessageAboutInvalidPhotoSize(String text) {
        checkTextInElement(invalidMessage, text);
        logger.info("Invalid message has text: " + text);
        return this;
    }
}
