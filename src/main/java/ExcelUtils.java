
import model.Card;
import model.MonsterCard;
import model.SpellCard;
import model.TrapCard;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
    private static String type;
    private static String status;
    private static String icon;

    public void run() throws IOException {
        String prjDir = System.getProperty("user.dir");
        String excelPath = prjDir + "/data/Monster.xlsx";
        FileInputStream files = new FileInputStream(excelPath);
        XSSFWorkbook workbook = new XSSFWorkbook(files);
        XSSFSheet sheet = workbook.getSheetAt(0);
        createMonsterCardObjects(sheet);
        prjDir = System.getProperty("user.dir");
        excelPath = prjDir + "/data/SpellTrap.xlsx";
        files = new FileInputStream(excelPath);
        workbook = new XSSFWorkbook(files);
        sheet = workbook.getSheetAt(0);
        createSpellCards(sheet);
        createTrapCards(sheet);
    }

    private void createMonsterCardObjects(XSSFSheet sheet) {
        ArrayList<MonsterCard> monsterCards = MonsterCard.getMonsterCards();
        ArrayList<Card> cards = Card.getCards();
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            MonsterCard monsterCard = new MonsterCard();
            for (int j = 0; j < sheet.getRow(i).getPhysicalNumberOfCells(); j++) {
                switch (j) {
                    case 0:
                        name = sheet.getRow(i).getCell(j).toString();
                        monsterCard.setName(name);
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
                        monsterCard.setAttack(attack);
                        break;
                    case 6:
                        defence = (int) sheet.getRow(i).getCell(j).getNumericCellValue();
                        monsterCard.setDefence(defence);
                        break;
                    case 7:
                        description = sheet.getRow(i).getCell(j).toString();
                        monsterCard.setDescription(description);
                        break;
                    case 8:
                        price = (int) sheet.getRow(i).getCell(j).getNumericCellValue();
                        monsterCard.setPrice(price);
                        break;
                }
            }
            monsterCard.setImageURL("/images/Cards/Monsters/"+ monsterCard.getName() +".jpg");
            cards.add(monsterCard);
            monsterCards.add(monsterCard);
        }
    }

    private void createSpellCards(XSSFSheet sheet) {
        ArrayList<SpellCard> spellCards = SpellCard.getSpellCards();
        ArrayList<Card> cards = Card.getCards();
        for (int i = 13; i < sheet.getPhysicalNumberOfRows(); i++) {
            SpellCard spellCard = new SpellCard();
            for (int j = 0; j < sheet.getRow(i).getPhysicalNumberOfCells(); j++) {
                switch (j) {
                    case 0:
                        name = sheet.getRow(i).getCell(j).toString();
                        spellCard.setName(name);
                        break;
                    case 1:
                        type = sheet.getRow(i).getCell(j).toString();
                        spellCard.setType(type);
                        break;
                    case 2:
                        icon = sheet.getRow(i).getCell(j).toString();
                        spellCard.setIcon(icon);
                        break;
                    case 3:
                        description = sheet.getRow(i).getCell(j).toString();
                        spellCard.setDescription(description);
                        break;
                    case 4:
                        status = sheet.getRow(i).getCell(j).toString();
                        spellCard.setStatus(status);
                        break;
                    case 5:
                        price = (int) sheet.getRow(i).getCell(j).getNumericCellValue();
                        spellCard.setPrice(price);
                        break;
                }
            }
            spellCard.setImageURL("/images/Cards/SpellTrap/"+ spellCard.getName() +".jpg");
            spellCards.add(spellCard);
            cards.add(spellCard);
        }
    }

    private void createTrapCards(XSSFSheet sheet) {
        ArrayList<TrapCard> trapCards = TrapCard.getTrapCards();
        ArrayList<Card> cards = Card.getCards();
        for (int i = 1; i < 13; i++) {
            TrapCard trapCard = new TrapCard();
            for (int j = 0; j < sheet.getRow(i).getPhysicalNumberOfCells(); j++) {
                switch (j) {
                    case 0:
                        name = sheet.getRow(i).getCell(j).toString();
                        trapCard.setName(name);
                        break;
                    case 1:
                        type = sheet.getRow(i).getCell(j).toString();
                        trapCard.setType(type);
                        break;
                    case 2:
                        icon = sheet.getRow(i).getCell(j).toString();
                        trapCard.setIcon(icon);
                        break;
                    case 3:
                        description = sheet.getRow(i).getCell(j).toString();
                        trapCard.setDescription(description);
                        break;
                    case 4:
                        status = sheet.getRow(i).getCell(j).toString();
                        trapCard.setStatus(status);
                        break;
                    case 5:
                        price = (int) sheet.getRow(i).getCell(j).getNumericCellValue();
                        trapCard.setPrice(price);
                        break;
                }
            }
            trapCard.setImageURL("/images/Cards/SpellTrap/"+ trapCard.getName() +".jpg");
            trapCards.add(trapCard);
            cards.add(trapCard);
        }
    }
}
