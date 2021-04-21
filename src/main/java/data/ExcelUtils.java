package data;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;

public class ExcelUtils {
    private static ExcelUtils excelUtils;
    private ExcelUtils() {
    }

    public static ExcelUtils getInstance(){
        if (excelUtils == null){
            excelUtils = new ExcelUtils();
        }
        return excelUtils;
    }

    public void getRowCount(){
        String excelPath = "./data/Monster.csv";
        XSSFWorkbook workBook = null;
        try {
            workBook = new XSSFWorkbook(excelPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        XSSFSheet sheet = workBook.getSheet("Monster");
        int rowCount = sheet.getPhysicalNumberOfRows();
        System.out.println("No of rows : " + rowCount);
    }
}
