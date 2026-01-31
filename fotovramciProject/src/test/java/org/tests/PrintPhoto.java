package org.tests;

import org.baseTest.BaseTest;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;

public class PrintPhoto extends BaseTest {

    private final String INVALID_PHOTO_SIZE = "*Деякі завантажені фотографії не відповідають обраному формату. " +
            "Відредагуйте за допомогою обрізки або додайте білі поля до потрібного розміру.";

    @Test
    public void TC005_invalidPhotoSizeToUpload() {


        pageProvider.getHomePage()
                .openHomePage()
                .getHeader()
                .clickOnPrintButton()
                .checkIsRedirectToPrintPhotoPage()
                .clickOrderButton()
                .checkIsRedirectToUploadPhotoPage()
                .uploadPhoto("invalid_size_photo.png")
                .checkErrorMessageAboutInvalidPhotoSize(INVALID_PHOTO_SIZE)

        ;

        logger.info("TC005 completed");
    }
}
