package Pages;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utilities.Actions;
import utilities.Assertions;
import utilities.JsonPath;
import utilities.JsonReader;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

public class DetailsPage {
    private final WebDriver driver;

    By queenBedElement = By.xpath("(//*[contains(text(),'1 queen bed')])[1]");
    By amountDropDownList = By.xpath("//*[@id=\"hprt_nos_select_78883120_386871369_0_33_0_131741\"]");
    By reserveButton = By.xpath("(//*[contains(text(),'reserve')])[2]");
    By checkInDateOnDetailsPage = By.xpath("//h3[contains(text(), 'Check-in')]/following-sibling::div[1]");
    By checkOutDateOnDetailsPage = By.xpath("//h3[contains(text(), 'Check-out')]/following-sibling::div[1]");

    public DetailsPage(WebDriver driver) {
        this.driver = driver;
    }

    public void switchToNewWindow() {
        String originalWindow = driver.getWindowHandle();
        Actions.waitForNumberOfWindows(2);
        Set<String> allWindows = driver.getWindowHandles();
        Iterator<String> it = allWindows.iterator();
        while (it.hasNext()) {
            String newWindow = it.next();
            if (!originalWindow.equals(newWindow)) {
                driver.switchTo().window(newWindow);
                break;
            }
        }
    }

    public void selectAmountAndBed(String roomsAmount) {
//        Actions.scrollIntoView(queenBedElement);
        Actions.click(queenBedElement);
        Actions.click(amountDropDownList);
        Actions.selectByValue(amountDropDownList, roomsAmount);
        Actions.click(reserveButton);
    }

    public void closeAndSwitchBack() {
        Set<String> allWindows = driver.getWindowHandles();
        String originalWindow = (String) allWindows.toArray()[0];
        driver.close();
        driver.switchTo().window(originalWindow);
    }

    public void assertDatesAreCorrectlyDisplayed() throws IOException, ParseException {
        String expectedCheckInDate = JsonReader.getJsonValueByKey(JsonPath.jsonData, "Check-in");
        String expectedCheckOutDate = JsonReader.getJsonValueByKey(JsonPath.jsonData, "Check-out");

        String actualCheckInDate = Actions.getElementText(checkInDateOnDetailsPage);
        String actualCheckOutDate = Actions.getElementText(checkOutDateOnDetailsPage);

        Assertions.assertDateContains(actualCheckInDate, expectedCheckInDate);
        Assertions.assertDateContains(actualCheckOutDate, expectedCheckOutDate);
    }
}