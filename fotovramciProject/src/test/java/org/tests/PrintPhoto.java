package org.tests;

import org.baseTest.BaseTest;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;

public class PrintPhoto extends BaseTest {

    @Test
    public void TC005_invalidPhotoSizeToUpload() {


        pageProvider.getHomePage()
                .openHomePage()
                .getHeader()
                .clickOnPrintButton()
                .checkIsRedirectToPrintPhotoPage()
                .clickOrderButton()
                .checkIsRedirectToUploadPhotoPage()
                .uploadPhotos("invalid_size_photo.png")
                .checkErrorMessageAboutInvalidPhotoSize("*Деякі завантажені фотографії не відповідають обраному формату. " +
                        "Відредагуйте за допомогою обрізки або додайте білі поля до потрібного розміру.")

        ;

        // TODO refactor uploadPhotos method
        logger.info("TC005 completed");
    }
}
