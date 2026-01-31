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

//    public UploadPhoto uploadPhotos(String... fileNames) {
//        if (fileNames == null || fileNames.length == 0) {
//            throw new IllegalArgumentException("No files provided for upload!");
//        }
//
//        // Резолвимо файли через ClassLoader
//        List<String> absolutePaths = new ArrayList<>();
//        for (String fileName : fileNames) {
//            ClassLoader classLoader = getClass().getClassLoader();
//            URL resource = classLoader.getResource("images/" + fileName);
//
//            if (resource == null) {
//                throw new IllegalArgumentException("File not found in resources: " + fileName);
//            }
//
//            File file = new File(resource.getFile());
//            if (!file.exists()) {
//                throw new IllegalArgumentException("File does not exist: " + file.getAbsolutePath());
//            }
//
//            absolutePaths.add(file.getAbsolutePath());
//        }
//
//        // Об'єднуємо шляхи для multiple input через \n
//        String joinedPaths = String.join("\n", absolutePaths);
//
//        // Надсилаємо шляхи прямо в hidden input
//        fileInput.sendKeys(joinedPaths);
//
//        // Чекаємо появи превʼю
//        new WebDriverWait(webDriver, Duration.ofSeconds(UPLOAD_TIMEOUT_SEC))
//                .until(ExpectedConditions.visibilityOf(uploadedPhotoPreview));
//
//        // Перевіряємо, що превʼю дійсно зʼявилось
//        if (!uploadedPhotoPreview.isDisplayed()) {
//            throw new AssertionError("Uploaded photo preview did not appear!");
//        }
//
//        return this; // для чейнінгу
//    }


    public UploadPhoto checkErrorMessageAboutInvalidPhotoSize(String text) {

        checkTextInElement(invalidMessage, text);
        logger.info("Invalid message has text: " + text);
        return this;
    }
}
