package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

import static utilities.Actions.driver;

public class Assertions {

    // No instance variables here since the class is now static
    // The constructor is not needed for a static class
    private Assertions() {
    }

    // Pass WebDriver as a parameter to methods that need it
    public static boolean isElementPresent(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void softAssertElementExists(WebDriver driver, SoftAssert softAssert, By by) {
        if (isElementPresent(by)) {
            softAssert.assertTrue(true, "Element " + by.toString() + " exists.");
        } else {
            softAssert.fail("Element not found: " + by.toString());
        }
    }

    public static void assertElementText(WebDriver driver, String expectedText, By by) {
        String actualText = getElementText(driver, by);
        Assert.assertEquals(normalizeText(actualText), normalizeText(expectedText), "Text assertion failed for element: " + by.toString());
    }

    public static void assertParentContainsChildWithText(WebDriver driver, By parentBy, String childText) {
        try {
            WebElement parentElement = driver.findElement(parentBy);
            By childLocator = By.xpath(".//descendant::*[normalize-space(text()) = normalize-space('" + childText + "')]");
            WebElement childElement = parentElement.findElement(childLocator);
            Assert.assertTrue(childElement.isDisplayed(), "The child element with text '" + childText + "' was not found or is not displayed inside the parent.");
        } catch (org.openqa.selenium.NoSuchElementException e) {
            Assert.fail("Assertion Failed: Parent element " + parentBy.toString() + " does not contain a child with the text '" + childText + "'.");
        }
    }

    public static void assertEquals(String actualText, String expectedText) {
        String normalizedActualText = normalizeText(actualText);
        String normalizedExpectedText = normalizeText(expectedText);
        Assert.assertEquals(normalizedActualText, normalizedExpectedText, "Text assertion failed.");
    }

    private static String normalizeText(String text) {
        if (text == null) {
            return "";
        }
        return text.trim().replaceAll("[\\s\\u00a0]+", " ").replaceAll("[\\d,.]", "");
    }

    private static String getElementText(WebDriver driver, By by) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            return element.getText();
        } catch (Exception e) {
            return "";
        }
    }
    public static void assertDateContains(String actualDateText, String expectedDay) {
        if (actualDateText == null || expectedDay == null) {
            Assert.fail("Assertion failed: Actual or expected date text is null.");
        }
        Assert.assertTrue(actualDateText.contains(expectedDay),
                "Date assertion failed. Expected day '" + expectedDay +
                        "' not found in actual date: '" + actualDateText + "'.");
    }
}