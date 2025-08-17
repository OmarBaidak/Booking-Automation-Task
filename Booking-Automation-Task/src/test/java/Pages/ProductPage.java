package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.Actions;
import utilities.Assertions;

import java.time.Duration;

public class ProductPage {
    private final WebDriver driver;

    By signInExitButton = By.xpath("//*[@aria-label=\"Dismiss sign-in info.\"]");
    By hotelsFilterButton = By.xpath("(//*[text()='Hotels'])[1]");
    By tolipHotelText = By.xpath("//*[text()='Tolip Hotel Alexandria']");
    By seeAvailabilityTolipButton = By.xpath("//*[contains(text(),'Tolip Hotel Alexandria')]//ancestor::div[@data-testid='property-card']//*[contains(text(),'See availability')]");
    By boxTolipHotelField = By.xpath("//*[@data-testid='title' and text()='Tolip Hotel Alexandria']/ancestor::div[@data-testid='property-card']");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickOnExitSignInButtonIfExist() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            wait.until(ExpectedConditions.elementToBeClickable(signInExitButton));
            Actions.click(signInExitButton);
            System.out.println("Sign-in pop-up was found and dismissed.");
        } catch (Exception e) {
            System.out.println("Sign-in pop-up did not appear.");
        }
    }

    public void clickOnHotelsSearchFilterButton() {
        Actions.click(hotelsFilterButton);
    }

    public void selectTolipHotel() {
        Actions.scrollIntoView(tolipHotelText);
        Actions.click(seeAvailabilityTolipButton);
    }

    public void assertHotelNameIsCorrectlyDisplayed() {
        Actions.scrollIntoView(tolipHotelText);
        Assertions.assertParentContainsChildWithText(driver, boxTolipHotelField, "Tolip Hotel Alexandria");
    }
}