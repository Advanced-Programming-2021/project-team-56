import controller.LoginMenuController;
import controller.ShopController;
import model.Card;
import model.Deck;
import model.User;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @BeforeEach
    public void createUsers() {
        ArrayList<String> userCreationCommands = new ArrayList<>();
        userCreationCommands.add("user create --username repetitiveUsername --nickname cool --password 12345");
        userCreationCommands.add("user create --username username --nickname repetitiveNickname --password 12345");
        userCreationCommands.add("user create --username bothRepetitive --nickname bothRepetitive --password 12345");
        userCreationCommands.add("user create --username Mehrshad --nickname MehrNick --password 0000");
        userCreationCommands.add("user create --username AmirAli --nickname AmirNick --password 0000");
        for (String command : userCreationCommands) {
            LoginMenuController.getInstance().createUser(command);
        }
    }

    @BeforeEach
    public void excelRun() throws IOException {
        ExcelUtils.getInstance().run();
    }

    @BeforeEach
    public void buyCardsForPlayers() {
        String firstPlayer = "Mehrshad";
        String secondPlayer = "AmirAli";

        int MoneyLeft = 100000;

//        HashMap<String, Integer> cardsToBuy = new HashMap<>();
//
//        cardsToBuy.put("Battle OX", 2);
//        cardsToBuy.put("Axe Raider", 2);
//        cardsToBuy.put("Yomi Ship", 2);
//        cardsToBuy.put("Horn Imp", 2);
//        cardsToBuy.put("Silver Fang", 2);
//        cardsToBuy.put("Suijin", 2);
//        cardsToBuy.put("Fireyarou", 2);
//        cardsToBuy.put("Curtain of the dark ones", 2);
//        cardsToBuy.put("Feral Imp", 2);
//        cardsToBuy.put("Dark magician", 2);
//        cardsToBuy.put("Wattkid", 2);
//        cardsToBuy.put("Baby dragon", 2);
//        cardsToBuy.put("Hero of the east", 2);
//        cardsToBuy.put("Battle warrior", 2);
//        cardsToBuy.put("Crawling dragon", 2);
//        cardsToBuy.put("Flame manipulator", 2);
//        cardsToBuy.put("Blue-Eyes white dragon", 2);
//        cardsToBuy.put("Crab Turtle", 2);
//        cardsToBuy.put("Skull Guardian", 2);
//        cardsToBuy.put("Slot Machine", 2);
//        cardsToBuy.put("Haniwa", 2);
//        cardsToBuy.put("Man-Eater Bug", 2);
//        cardsToBuy.put("Gate Guardian", 2);
//        cardsToBuy.put("Scanner", 2);
//        cardsToBuy.put("Bitron", 2);
//        cardsToBuy.put("Marshmallon", 2);
//        cardsToBuy.put("Beast King Barbaros", 2);
//        cardsToBuy.put("Texchanger", 2);
//        cardsToBuy.put("Leotron ", 2);
//        cardsToBuy.put("The Calculator", 2);
//        cardsToBuy.put("Alexandrite Dragon", 2);
//        cardsToBuy.put("Mirage Dragon", 2);
//        cardsToBuy.put("Herald of Creation", 2);
//        cardsToBuy.put("Exploder Dragon", 2);
//        cardsToBuy.put("Warrior Dai Grepher", 2);
//        cardsToBuy.put("Dark Blade", 2);
//        cardsToBuy.put("Wattaildragon", 2);
//        cardsToBuy.put("Terratiger, the Empowered Warrior", 2);
//        cardsToBuy.put("The Tricky", 2);
//        cardsToBuy.put("Spiral Serpent", 2);
//        cardsToBuy.put("Command Knight", 2);
//
//        for (Map.Entry<String, Integer> map : cardsToBuy.entrySet()) {
////            MoneyLeft -= Card.getCardByName(map.getKey()).getPrice() * map.getValue();
//            System.out.println(Card.getCardByName(map.getKey()));
//        }

        ArrayList<String> monsterNames = new ArrayList<>();
        monsterNames.add("Battle OX");
        monsterNames.add("Axe Raider");
        monsterNames.add("Yomi Ship");
        monsterNames.add("Horn Imp");
        monsterNames.add("Silver Fang");
        monsterNames.add("Suijin");
        monsterNames.add("Fireyarou");
        monsterNames.add("Curtain of the dark ones");
        monsterNames.add("Feral Imp");
        monsterNames.add("Dark magician");
        monsterNames.add("Wattkid");
        monsterNames.add("Baby dragon");
        monsterNames.add("Hero of the east");
        monsterNames.add("Battle warrior");
        monsterNames.add("Crawling dragon");
        monsterNames.add("Flame manipulator");
        monsterNames.add("Blue-Eyes white dragon");
        monsterNames.add("Crab Turtle");
        monsterNames.add("Skull Guardian");
        monsterNames.add("Slot Machine");
        monsterNames.add("Haniwa");
        monsterNames.add("Man-Eater Bug");
        monsterNames.add("Gate Guardian");
        monsterNames.add("Scanner");
        monsterNames.add("Bitron");
        monsterNames.add("Marshmallon");
        monsterNames.add("Beast King Barbaros");
        monsterNames.add("Texchanger");
        monsterNames.add("Leotron ");
        monsterNames.add("The Calculator");
        monsterNames.add("Alexandrite Dragon");
        monsterNames.add("Mirage Dragon");
        monsterNames.add("Herald of Creation");
        monsterNames.add("Exploder Dragon");
        monsterNames.add("Warrior Dai Grepher");
        monsterNames.add("Dark Blade");
        monsterNames.add("Wattaildragon");
        monsterNames.add("Terratiger, the Empowered Warrior");
        monsterNames.add("The Tricky");
        monsterNames.add("Spiral Serpent");
        monsterNames.add("Command Knight");

        for (String cardName : monsterNames) {
            System.out.println(Card.getCardByName(cardName));
        }

//        System.out.println(MoneyLeft);

//        ShopController.getInstance().buyCard("Command Knight", firstPlayer);
//        ShopController.getInstance().buyCard("Command Knight", secondPlayer);
//        ShopController.getInstance().buyCard("Yomi Ship", firstPlayer);
//        ShopController.getInstance().buyCard("Yomi Ship", secondPlayer);
//        ShopController.getInstance().buyCard("Suijin", firstPlayer);
//        ShopController.getInstance().buyCard("Suijin", secondPlayer);
//        ShopController.getInstance().buyCard("Crab Turtle", firstPlayer);
//        ShopController.getInstance().buyCard("Crab Turtle", secondPlayer);


    }

    @Test
    public void viewTest() {
        StringBuilder commands = new StringBuilder();
        StringBuilder validOutputs = new StringBuilder();

        loginViewIOAppender(commands, validOutputs);
        mainMenuViewIOAppender(commands, validOutputs);
        profileViewIOAppender(commands, validOutputs);
        duelMenuViewIOAppender(commands, validOutputs);
        deckMenuViewIOAppender(commands, validOutputs);

        ByteArrayInputStream inputStream = new ByteArrayInputStream(commands.toString().getBytes());
        System.setIn(inputStream);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        LoginMenuView.getInstance().run();
        MainMenuView.getInstance().run("Mehrshad");
        MainMenuView.getInstance().run("Mehrshad");
        ProfileView.getInstance().run("Mehrshad");
        DuelMenuView.getInstance().run("Mehrshad");
        DeckMenuView.getInstance().run("Mehrshad");

        String output = (outputStream.toString());

        assertEquals(validOutputs.toString(), output);
    }

    private void loginViewIOAppender(StringBuilder inputStringBuilder, StringBuilder outputStringBuilder) {
        inputStringBuilder.append("menu show-current\n" +
                "user create --username loginViewTest --nickname login --password 12345\n" +
                "menu enter Duel\n" +
                "invalid command\n" +
                "user login --username loginViewTest --password 12345\nmenu exit\n" +
                "user login --password 12345 --username loginViewTest\nmenu exit\n" +
                "user login invalid command\n" +
                "menu exit\n");
        outputStringBuilder.append("Login Menu\r\n" +
                "user created successfully!\r\n" +
                "please login first\r\ninvalid command\r\n" +
                "user logged in successfully!\r\nuser logged in successfully!\r\ninvalid command\r\n");
    }

    private void mainMenuViewIOAppender(StringBuilder inputStringBuilder, StringBuilder outputStringBuilder) {
        inputStringBuilder.append("invalid command\n" +
                "menu show-current\n" +
                "menu enter Duel\nmenu exit\nmenu enter Deck\nmenu exit\n" +
                "menu enter Shop\nmenu exit\nmenu enter Scoreboard\nmenu exit\n" +
                "menu enter Profile\nmenu exit\nmenu enter Import/Export\n" +
                "menu enter invalidMenu\n" +
                "user logout\n" +
                "menu exit\n");
        outputStringBuilder.append("invalid command\r\nMain Menu\r\ninvalid command\r\n" +
                "user logged out successfully!\r\n");
    }

    private void profileViewIOAppender(StringBuilder inputStringBuilder, StringBuilder outputStringBuilder) {
        inputStringBuilder.append("menu show-current\nmenu enter Duel\n" +
                "menu enter Deck\nmenu enter Shop\nmenu enter Scoreboard\nmenu enter Profile\n" +
                "menu enter Import/Export\nprofile change --nickname M k e\nprofile change --nickname AmirNick\n" +
                "profile change --nickname MehrNick2\nprofile change --nickname MehrNick\n" +
                "profile change --password --current m a --new m a\n" +
                "profile change --password --current 0000 --new 0000\n" +
                "profile change --password --current 1000 --new 0000\n" +
                "profile change --password --current 0000 --new 1111\n" +
                "profile change --current 1111 --password --new 0000\n" +
                "profile change --current 0000 --new 0000 --password\n" +
                "profile change --password --new 0000 --current 0000\n" +
                "profile change --new 0000 --password --current 0000\n" +
                "profile change --new 0000 --current 0000 --password\ninvalid command\nmenu exit\n");


        outputStringBuilder.append("Profile Menu\r\nmenu navigation is not possible\r\n" +
                "menu navigation is not possible\r\nmenu navigation is not possible\r\n" +
                "menu navigation is not possible\r\nmenu navigation is not possible\r\n" +
                "menu navigation is not possible\r\ninvalid command\r\nuser with nickname AmirNick already exists\r\n" +
                "nickname changed successfully\r\nnickname changed successfully\r\ninvalid command\r\n" +
                "please enter a new password\r\n" +
                "current password is invalid\r\npassword changed successfully\r\npassword changed successfully\r\n" +
                "please enter a new password\r\nplease enter a new password\r\nplease enter a new password\r\n" +
                "please enter a new password\r\ninvalid command\r\n");
    }

    private void duelMenuViewIOAppender(StringBuilder inputStringBuilder, StringBuilder outputStringBuilder) {
        //TODO Enters the duel(optional)
        inputStringBuilder.append("menu show-current\nmenu enter Duel\nmenu enter Deck\nmenu enter Shop\n" +
                "menu enter Scoreboard\nmenu enter Profile\nmenu enter Import/Export\nduel invalid\n" +
                "invalid\n" +
                "menu exit\n");

        outputStringBuilder.append("Duel Menu\r\nmenu navigation is not possible\r\n" +
                "menu navigation is not possible\r\nmenu navigation is not possible\r\n" +
                "menu navigation is not possible\r\nmenu navigation is not possible\r\n" +
                "menu navigation is not possible\r\ninvalid command\r\ninvalid command\r\n");
    }

    private void deckMenuViewIOAppender(StringBuilder inputStringBuilder, StringBuilder outputStringBuilder) {
        inputStringBuilder.append("menu show-current\nmenu enter Duel\ninvalid\ndeck invalid\ncard show  aa\n" +
                "menu exit");

        outputStringBuilder.append("Deck Menu\r\nmenu navigation is not possible\r\ninvalid command\r\n" +
                "invalid command\r\ninvalid command\r\n");
    }

    @Test
    public void loginTest() {
        String validSuccessfulLoginOutput = "user logged in successfully!";
        String validWrongPasswordAndNullNameOutput = "Username and password didn't match!";
        new User("testName", "testNickname", "testPassword");
        String successfulLoginMethodOutput = LoginMenuController.getInstance().logIn("testName", "testPassword");
        String wrongPasswordMethodOutput = LoginMenuController.getInstance().logIn("testName", "wrong");
        String nullUsernameMethodOutput = LoginMenuController.getInstance().logIn("wrong", "testPassword");
        String nullUsernameAndWrongPasswordMethodOutput = LoginMenuController.getInstance().logIn("wrong", "wrong");
        assertEquals(successfulLoginMethodOutput, validSuccessfulLoginOutput);
        assertEquals(wrongPasswordMethodOutput, validWrongPasswordAndNullNameOutput);
        assertEquals(nullUsernameMethodOutput, validWrongPasswordAndNullNameOutput);
        assertEquals(nullUsernameAndWrongPasswordMethodOutput, validWrongPasswordAndNullNameOutput);
    }

    @Test
    public void loginViewRunTest() {
        ArrayList<String> createUserTestCommands = new ArrayList<>();
        createUserTestCommands.add("user create --username ali --nickname aliPalang --password 12345");
        createUserTestCommands.add("user create --username reza --password 12345 --nickname rza");
        createUserTestCommands.add("user create --password 12345 --username mohammad --nickname mmd");
        createUserTestCommands.add("user create --password 12345 --nickname mmdi --username mohammadi");
        createUserTestCommands.add("user create --nickname abbas --password 12345 --username mohammadinami");
        createUserTestCommands.add("user create --nickname rezaiii --username abbas --password 12345");
        for (String command : createUserTestCommands) {
            String createUserOutput = LoginMenuController.getInstance().createUser(command);
            assertEquals("user created successfully!", createUserOutput);
        }
        String repetitiveUsernameCommand = "user create --username repetitiveUsername --nickname new --password 12345";
        String repetitiveUsernameOutput = LoginMenuController.getInstance().createUser(repetitiveUsernameCommand);
        assertEquals("user with username repetitiveUsername already exists", repetitiveUsernameOutput);

        String repetitiveNicknameCommand = "user create --username user --nickname repetitiveNickname --password 1";
        String repetitiveNicknameOutput = LoginMenuController.getInstance().createUser(repetitiveNicknameCommand);
        assertEquals("user with nickname repetitiveNickname already exists", repetitiveNicknameOutput);

        String bothRepetitiveCommand = "user create --username bothRepetitive --nickname bothRepetitive --password 12345";
        String bothRepetitiveOutput = LoginMenuController.getInstance().createUser(bothRepetitiveCommand);
        assertEquals("user with username bothRepetitive already exists", bothRepetitiveOutput);
    }

    @Test
    public void cardConstructorTest() {
        //TODO Can put all set and gets on testCard
        Card testCard = new Card("testCard", "testDescryption", 700);
        int outputPrice = testCard.getPrice();
        assertEquals(700, outputPrice);
        Card outputCard = Card.getCardByName("testCard");
        assertNull(outputCard);
        Card realTestCard = Card.getCardByName("Battle OX");
        assertNotNull(realTestCard);
    }

    public void deckControllerTest() {
        Deck testDeck = new Deck("testDeckName");

    }


}