package org.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UploadPhoto extends ParentPage {

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
        return this;
    }
}
