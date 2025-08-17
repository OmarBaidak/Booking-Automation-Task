package utilities;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.DataProvider;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Helpers {

    public static void javaScriptExecutor(WebDriver driver, WebElement element) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            System.out.println("An error occurred while clicking the element: " + e.getMessage());
        }
    }

    public static void selectOptionFromDropdown(WebElement dropdownElement, String optionValue) {
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByValue(optionValue);
    }


    @DataProvider(name = "reservationData")
    public static Iterator<Object[]> getReservationData() {
        List<Object[]> data = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream("src/main/resources/DataFiles/TestData.xlsx");
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet("Sheet1");
            Iterator<Row> rowIterator = sheet.iterator();
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                String searchText = (row.getCell(0) != null) ? row.getCell(0).getStringCellValue() : "";
                String checkInDate = (row.getCell(1) != null) ? row.getCell(1).getStringCellValue() : "";
                String checkOutDate = (row.getCell(2) != null) ? row.getCell(2).getStringCellValue() : "";

                data.add(new Object[]{searchText, checkInDate, checkOutDate});
            }

        } catch (IOException e) {
            System.err.println("Error reading Excel file: " + e.getMessage());
            e.printStackTrace();
        }

        return data.iterator();
    }
}