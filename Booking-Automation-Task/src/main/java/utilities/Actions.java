package utilities;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.NoSuchElementException;

public class Actions {
    protected static WebDriver driver;
    protected static WebDriverWait wait;

    public static void setup(WebDriver webDriver) {
        driver = webDriver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    protected static WebElement waitForElement(By locator) {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (TimeoutException e) {
            throw new RuntimeException("Element not found or not clickable within timeout: " + locator, e);
        }
    }

    protected static WebElement findElement(By locator) {
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (TimeoutException e) {
            throw new RuntimeException("Element not found: " + locator, e);
        }
    }

    public static void click(By by) {
        try {
            WebElement element = waitForElement(by);
            element.click();
        } catch (Exception e) {
            Assert.fail("Failed to click element: " + by + " due to " + e.getMessage());
        }
    }

    public static void type(By by, String text) {
        try {
            WebElement element = waitForElement(by);
            element.clear();
            element.sendKeys(text);
        } catch (Exception e) {
            Assert.fail("Failed to type into element: " + by + " due to " + e.getMessage());
        }
    }

    public static String getElementText(By by) {
        try {
            WebElement element = waitForElement(by);
            return element.getText();
        } catch (Exception e) {
            Assert.fail("Failed to get text from element: " + by + " due to " + e.getMessage());
            return null;
        }
    }

    public static void scrollIntoView(By by) {
        try {
            WebElement element = findElement(by);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (TimeoutException e) {
            throw new RuntimeException("Element not found or not visible after scrolling: " + by, e);
        }
    }

    public static void selectByValue(By locator, String value) {
        WebElement dropdownElement = findElement(locator);
        Select dropdown = new Select(dropdownElement);
        try {
            dropdown.selectByValue(value);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Option with value '" + value + "' not found in dropdown located by: " + locator, e);
        }
    }

    public static void selectByIndex(By locator, int index) {
        WebElement dropdownElement = findElement(locator);
        Select select = new Select(dropdownElement);
        try {
            select.selectByIndex(index);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Option at index '" + index + "' not found in dropdown located by: " + locator, e);
        }
    }

    public static void switchToIframe(By iframeLocator) {
        try {
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframeLocator));
        } catch (TimeoutException e) {
            throw new RuntimeException("Could not switch to iframe: " + iframeLocator, e);
        }
    }

    public static void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    public static void waitForNumberOfWindows(int numberOfWindows) {
        wait.until(ExpectedConditions.numberOfWindowsToBe(numberOfWindows));
    }
}