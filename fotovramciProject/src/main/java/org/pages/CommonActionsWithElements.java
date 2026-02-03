package org.pages;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;
import org.utils.ConfigProvider;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommonActionsWithElements {
    protected WebDriver webDriver;

    private Logger logger = Logger.getLogger(getClass());
    SoftAssert softAssert = new SoftAssert();

    public CommonActionsWithElements(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this); // init elements declare by "@FindBy"
    }

    protected List<WebElement> getElementsList(List<WebElement> elements) {
        waitUntilAllListElementsBecomeVisible(elements);
        if (elements == null) {
            return Collections.emptyList();
        }
        return elements;
    }

    protected void clickOnElement(WebElement webElement) {
        try {
            webElement.click();
            logger.info("Element was clicked");
        } catch (Exception e) {
            printErrorAndStopTest();
        }
    }

    protected void hoverOnElement(WebElement element) {
        try {
            Actions actions = new Actions(webDriver);
            actions.moveToElement(element).perform();
            logger.info("Hovered over element");
        } catch (Exception e) {
            printErrorAndStopTest();
        }
    }

    protected WebElement waitForElement(WebElement webElement) {
        return new WebDriverWait(webDriver, Duration.ofSeconds(ConfigProvider.configProperties.TIME_FOR_IMPLICIT_WAIT()))
                .until(ExpectedConditions.elementToBeClickable(webElement));
    }

    protected void waitUntilAllListElementsBecomeVisible(List<WebElement> elements) {
        new WebDriverWait(webDriver, Duration.ofSeconds(ConfigProvider.configProperties.TIME_FOR_DEFAULT_WAIT()))
                .until(driver -> elements.size() > 0);
    }

    protected void selectTextInDropDown(WebElement webElement, String text) {
        try {
            Select select = new Select(webElement);
            select.selectByVisibleText(text);
            logger.info("Text '" + text + "' was selected in DropDown");
        } catch (Exception e) {
            printErrorAndStopTest();
        }
    }

    protected void clearAndEnterTextIntoElement(WebElement webElement, String text) {
        try {
            webElement.clear();
            webElement.sendKeys(text);
            logger.info(text + " was inputed.");
        } catch (Exception e) {
            printErrorAndStopTest();
        }
    }

    protected void checkTextInElement(WebElement element, String... expectedTexts) {
        try {
            String actualText = element.getText();
            String actualLower = actualText.toLowerCase();

            // Check if the actual text contains any of the expected texts (case-insensitive)
            boolean matches = Arrays.stream(expectedTexts)
                    .map(String::toLowerCase)
                    .anyMatch(actualLower::contains);

            softAssert.assertTrue(matches,
                    "Text in element is not as expected. " +
                            "Expected (contains one of): " + Arrays.toString(expectedTexts) +
                            ", Actual: " + actualText
            );

            // Logging the result
            if (matches) {
                logger.info("✔ Element text matches expected texts " + Arrays.toString(expectedTexts)
                        + " | Actual: '" + actualText + "'");
            } else {
                logger.warn("✖ Element text does NOT match expected texts " + Arrays.toString(expectedTexts)
                        + " | Actual: '" + actualText + "'");
            }

            // TODO add counter of passed/failed checks (move from checkListElementsHaveTextInTitle)

        } catch (Exception e) {
            logger.error("Error while checking element text: " + e.getMessage(), e);
            printErrorAndStopTest();
        }
    }

    protected void checkListElementsHaveTextInTitle(List<WebElement> elements, String... expectedTexts) {
        List<WebElement> currentList = getElementsList(elements);

        // Iterate through the list of elements and check if each element contains the expected text(s)
        for (int i = 0; i < currentList.size(); i++) {
            WebElement element = currentList.get(i);
            logger.info("Checking element #" + (i + 1));
            checkTextInElement(element, expectedTexts);
        }
    }


    /**
     * Universal method to verify that a list of elements contains the expected text.
     *
     * @param elements      list of elements defined with @FindBy
     * @param nextButtonBy  By locator for the next page button
     * @param expectedTexts texts that each element should contain
     */

    protected void checkElementsHaveTextAcrossPages(List<WebElement> elements, By nextButtonBy, String... expectedTexts) {
        int pageNumber = 1; // Page number counter
        do {
            // Log the current page number and the number of elements found on the page
            logger.info(
                    "Page " + pageNumber + ": found " + getElementsList(elements).size() + " elements"
            );
            // Check if the elements on the current page contain the expected text(s)
            checkListElementsHaveTextInTitle(elements, expectedTexts);
            pageNumber++; // Increment the page number
        } while (goToNextPage(getElementsList(elements), nextButtonBy)); // Navigate to the next page if possible
        softAssert.assertAll(); // Assert all collected soft assertions
    }


    /**
     * Navigates to the next page of the product list if the "Next" button is available and clickable.
     *
     * @param productList       The current list of products on the page.
     * @param nextButtonLocator The locator for the "Next" button.
     * @return true if navigation to the next page was successful, false otherwise.
     */
    protected boolean goToNextPage(List<WebElement> productList, By nextButtonLocator) {
        // Find all elements matching the "Next" button locator
        List<WebElement> buttons = webDriver.findElements(nextButtonLocator);

        // If no "Next" button is found, assume it's the last page
        if (buttons.isEmpty()) {
            logger.info("It's the last page, next page button is not displayed or not enabled");
            return false;
        }

        WebElement nextPageButton = buttons.get(0);

        try {
            // Check if the "Next" button is visible and enabled
            if (!nextPageButton.isDisplayed() || !nextPageButton.isEnabled()) {
                logger.info("It's the last page, next page button is not displayed or not enabled");
                return false;
            }

            // Store the current product list to detect changes after navigation
            List<WebElement> oldProducts = new ArrayList<>(productList);

            // Click the "Next" button to navigate to the next page
            clickOnElement(nextPageButton);
            logger.info("Navigated to next page");

            // Wait for the product list to refresh (staleness of old elements)
            if (!oldProducts.isEmpty()) {
                new WebDriverWait(webDriver, Duration.ofSeconds(30))
                        .until(ExpectedConditions.stalenessOf(oldProducts.get(0)));
            }

            return true;

        } catch (StaleElementReferenceException e) {
            // Handle the case where the "Next" button becomes stale
            logger.warn("Next page button became stale — possibly the last page");
            return false;
        }
    }


    public void pressEnter(WebElement element) {
        element.sendKeys(Keys.ENTER);
        logger.info("Pressed ENTER on element");
    }

    public void checkElementIsNotZero(WebElement counterElement, String counterType) {
        String text = counterElement.getText(); // наприклад: "72 елементів"

        int count = Integer.parseInt(text.replaceAll("\\D+", ""));

        String errorMessage;
        String successMessage;

        switch (counterType.toLowerCase()) {
            case "cart":
                errorMessage = "Cart is empty!";
                successMessage = "Cart is not empty. Items in cart: ";
                break;

            case "product list":
                errorMessage = "Product list is empty!";
                successMessage = "Product list is not empty. Products found: ";
                break;

            default:
                errorMessage = "Counter value is zero!";
                successMessage = "Counter value is greater than zero: ";
                break;
        }

        Assert.assertTrue(errorMessage, count > 0);
        logger.info(successMessage + count);
    }

    public void checkElementIsZero(WebElement counterElement) {
        String text = counterElement.getText(); // "0 елементів"

        int productsCount = Integer.parseInt(text.replaceAll("\\D+", ""));

        Assert.assertTrue(
                "Product list is not empty!",
                productsCount == 0
        );

        // TODO work on logger message

        logger.info("Product list is empty. Products found: " + productsCount);
    }


    /**
     * Uploads photos by providing file paths to the file input element and verifies the upload.
     *
     * @param fileInput            The WebElement representing the file input field.
     * @param uploadedPhotoPreview The WebElement representing the preview of the uploaded photo.
     * @param fileNames            The names of the files to be uploaded.
     * @throws IllegalArgumentException if no files are provided or if any file does not exist.
     * @throws AssertionError           if the uploaded photo preview does not appear.
     */
    public void uploadPhotos(WebElement fileInput,
                             WebElement uploadedPhotoPreview,
                             String... fileNames) {

        // Validate that file names are provided
        if (fileNames == null || fileNames.length == 0) {
            throw new IllegalArgumentException("No files provided for upload!");
        }

        // Resolve the base path for the photo files
        String relativeBasePath = ConfigProvider.configProperties.PHOTO_FILE();
        Path projectRoot = Paths.get(System.getProperty("user.dir"));
        Path basePath = projectRoot.resolve(relativeBasePath);

        List<String> absolutePaths = new ArrayList<>();

        // Resolve and validate each file path
        for (String fileName : fileNames) {
            Path filePath = basePath.resolve(fileName);
            logger.info("Resolved file path: " + filePath);

            if (!Files.exists(filePath)) {
                throw new IllegalArgumentException(
                        "File not found: " + filePath.toAbsolutePath()
                );
            }

            absolutePaths.add(filePath.toAbsolutePath().toString());
        }

        // Join the file paths and upload the files
        String joinedPaths = String.join("\n", absolutePaths);
        fileInput.sendKeys(joinedPaths);

        // Wait for the uploaded photo preview to appear
        waitForElement(uploadedPhotoPreview);

        // Verify that the uploaded photo preview is displayed
        if (!uploadedPhotoPreview.isDisplayed()) {
            throw new AssertionError("Uploaded photo preview did not appear!");
        }
    }

    private void printErrorAndStopTest() {
        logger.error("Error while working with element");
        Assert.fail("Error while working with element");
    }


}
