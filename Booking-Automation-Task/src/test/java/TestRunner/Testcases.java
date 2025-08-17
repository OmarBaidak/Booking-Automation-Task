package TestRunner;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import Pages.ConfirmationPage;
import Pages.HomePage;
import Pages.ProductPage;
import Pages.DetailsPage;
import utilities.Actions;
import utilities.BrowserInitializer;

import java.io.IOException;

public class Testcases {
    private WebDriver driver;
    private BrowserInitializer browserInitializer;
    private HomePage homePage;
    private ProductPage productPage;
    private DetailsPage detailsPage;
    private ConfirmationPage confirmationPage;

    @BeforeMethod
    public void Setup() throws IOException {
        browserInitializer = new BrowserInitializer();
        driver = browserInitializer.initializeBrowser("chrome");
        Actions.setup(driver);
        homePage = new HomePage(driver);
        productPage = new ProductPage(driver);
        detailsPage = new DetailsPage(driver);
        confirmationPage = new ConfirmationPage(driver);
    }

    @Test(priority = 1, description = "Verifies hotel name on the reservation page")
    public void testHotelNameOnReservationPage() throws IOException, ParseException, InterruptedException {
        homePage.typeLocation();
        productPage.clickOnExitSignInButtonIfExist();
        homePage.chooseDayFromCheckInAndOutField();
        homePage.clickOnSearchButton();
        productPage.clickOnExitSignInButtonIfExist();
        productPage.clickOnHotelsSearchFilterButton();
        productPage.assertHotelNameIsCorrectlyDisplayed();
    }

    @Test(priority = 2, description = "Verifies dates on the hotel details page")
    public void testDatesOnDetailsPage() throws IOException, ParseException, InterruptedException {
        homePage.typeLocation();
        productPage.clickOnExitSignInButtonIfExist();
        homePage.chooseDayFromCheckInAndOutField();
        homePage.clickOnSearchButton();
        productPage.clickOnExitSignInButtonIfExist();
        productPage.clickOnHotelsSearchFilterButton();
        productPage.assertHotelNameIsCorrectlyDisplayed();
        productPage.selectTolipHotel();
        detailsPage.switchToNewWindow();
        detailsPage.selectAmountAndBed("1");
        detailsPage.assertDatesAreCorrectlyDisplayed();
        confirmationPage.assertDatesAreCorrectlyDisplayed();
        detailsPage.closeAndSwitchBack();
    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}