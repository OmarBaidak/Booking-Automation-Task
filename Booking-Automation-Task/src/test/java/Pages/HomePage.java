package Pages;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utilities.Actions;
import utilities.Assertions;
import utilities.JsonPath;
import utilities.JsonReader;

import java.io.IOException;

public class HomePage {
    private final WebDriver driver;

    By whereToGoField = By.xpath("//*[@placeholder=\"Where are you going?\"]");
    By checkInAndOutField = By.xpath("//*[@data-testid=\"searchbox-dates-container\"]");
    By arrowToNextMonthButton = By.xpath("//*[@aria-label=\"Next month\"]");
    By searchButton = By.xpath("(//*[contains(text(),'Search')])[4]");
    By octoberMonthCalender = By.xpath("(//*[contains(text(),'October 2025')])[last()]");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void typeLocation() throws IOException, ParseException {
        String location = JsonReader.getJsonValueByKey(JsonPath.jsonData, "Location");
        Actions.type(whereToGoField, location);
    }

    public void chooseDayFromCheckInAndOutField() throws IOException, ParseException {
        Actions.click(checkInAndOutField);
        if (!Assertions.isElementPresent(octoberMonthCalender)) {
            Actions.click(arrowToNextMonthButton);
        }
        String checkInDay = JsonReader.getJsonValueByKey(JsonPath.jsonData, "Check-in");
        String checkOutDay = JsonReader.getJsonValueByKey(JsonPath.jsonData, "Check-out");
        String checkInXPath = "//*[@aria-label=\"We " + checkInDay + " October 2025\"]";
        String checkOutXPath = "//*[@aria-label=\"Tu " + checkOutDay + " October 2025\"]";
        Actions.click(By.xpath(checkInXPath));
        Actions.click(By.xpath(checkOutXPath));
    }

    public void clickOnSearchButton() {
        Actions.click(searchButton);
    }
}