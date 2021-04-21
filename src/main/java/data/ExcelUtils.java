package data;

import model.MonsterCard;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ExcelUtils {
    private static ExcelUtils excelUtils;

    private ExcelUtils() {
    }

    public static ExcelUtils getInstance() {
        if (excelUtils == null) {
            excelUtils = new ExcelUtils();
        }
        return excelUtils;
    }

    private static String name;
    private static String attribute;
    private static int level;
    private static int price;
    private static String description;
    private static int attack;
    private static int defence;
    private static String cardType;
    private static String monsterType;

    public void run() throws IOException {
        String prjDir = System.getProperty("user.dir");
        String excelPath = prjDir + "/data/Monster.xlsx";
        FileInputStream files = new FileInputStream(new File(excelPath));
        XSSFWorkbook workbook = new XSSFWorkbook(files);
        XSSFSheet sheet = workbook.getSheetAt(0);
        createMonsterCardObjects(sheet);
    }

    private void createMonsterCardObjects(XSSFSheet sheet){
        ArrayList<MonsterCard> monsterCards = MonsterCard.getMonsterCards();
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            MonsterCard monsterCard = new MonsterCard();
            for (int j = 0; j < sheet.getRow(i).getPhysicalNumberOfCells(); j++) {
                switch (j) {
                    case 0:
                        name = sheet.getRow(i).getCell(j).toString();
                        break;
                    case 1:
                        level = (int) sheet.getRow(i).getCell(j).getNumericCellValue();
                        monsterCard.setLevel(level);
                        break;
                    case 2:
                        attribute = sheet.getRow(i).getCell(j).toString();
                        monsterCard.setAttribute(attribute);
                        break;
                    case 3:
                        monsterType = sheet.getRow(i).getCell(j).toString();
                        monsterCard.setMonsterType(monsterType);
                        break;
                    case 4:
                        cardType = sheet.getRow(i).getCell(j).toString();
                        monsterCard.setCardType(cardType);
                        break;
                    case 5:
                        attack = (int) sheet.getRow(i).getCell(j).getNumericCellValue();
                        System.out.println(attack);
                        monsterCard.setAttack(attack);
                        break;
                    case 6:
                        defence = (int) sheet.getRow(i).getCell(j).getNumericCellValue();
                        monsterCard.setDefence(defence);
                        break;
                    case 7:
                        description = sheet.getRow(i).getCell(j).toString();
                        System.out.println(description);
                        break;
                    case 8:
                        price = (int) sheet.getRow(i).getCell(j).getNumericCellValue();
                        monsterCard.setPrice(price);
                        break;
                }
            }
            monsterCards.add(monsterCard);
        }
    }
}
