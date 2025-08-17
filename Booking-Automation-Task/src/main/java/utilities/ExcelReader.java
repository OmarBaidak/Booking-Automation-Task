package utilities;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelReader {

    public static String getCellData(String excelFilePath, String sheetName, int rowNum, int colNum) throws IOException {
        String cellValue = "";
        try (FileInputStream fis = new FileInputStream(excelFilePath);
             XSSFWorkbook excelWorkbook = new XSSFWorkbook(fis)) {

            XSSFSheet excelSheet = excelWorkbook.getSheet(sheetName);
            if (excelSheet == null) {
                throw new IllegalArgumentException("Sheet not found: " + sheetName);
            }

            XSSFRow row = excelSheet.getRow(rowNum);
            if (row == null) {
                return "";
            }

            XSSFCell cell = row.getCell(colNum);
            if (cell != null) {
                DataFormatter formatter = new DataFormatter();
                cellValue = formatter.formatCellValue(cell);
            }
        }
        return cellValue;
    }
}