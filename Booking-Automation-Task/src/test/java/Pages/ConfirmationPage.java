package Pages;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utilities.Actions;
import utilities.Assertions;
import utilities.JsonPath;
import utilities.JsonReader;

import java.io.IOException;

public class ConfirmationPage {
    private final WebDriver driver;

    By confirmedCheckInDate = By.xpath("//h3[contains(text(), 'Check-in')]/following-sibling::div[1]");
    By confirmedCheckOutDate = By.xpath("//h3[contains(text(), 'Check-out')]/following-sibling::div[1]");

    public ConfirmationPage(WebDriver driver) {
        this.driver = driver;
    }

    public void assertDatesAreCorrectlyDisplayed() throws IOException, ParseException {
        String expectedCheckInDate = JsonReader.getJsonValueByKey(JsonPath.jsonData, "Check-in");
        String expectedCheckOutDate = JsonReader.getJsonValueByKey(JsonPath.jsonData, "Check-out");

        String actualCheckInDate = Actions.getElementText(confirmedCheckInDate);
        String actualCheckOutDate = Actions.getElementText(confirmedCheckOutDate);

        Assertions.assertDateContains(actualCheckInDate, expectedCheckInDate);
        Assertions.assertDateContains(actualCheckOutDate, expectedCheckOutDate);
    }
}