package org.tests;

import org.baseTest.BaseTest;
import org.junit.Test;

import static org.data.TestData.INVALID_PHOTO_SIZE;

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
                .uploadPhoto("invalid_size_photo.png")
                .checkErrorMessageAboutInvalidPhotoSize(INVALID_PHOTO_SIZE)

        ;

        logger.info("TC005 completed");
    }
}
