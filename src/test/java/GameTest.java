import controller.DeckMenuController;
import controller.LoginMenuController;
import controller.ShopController;
import model.Card;
import model.User;
import org.junit.jupiter.api.BeforeAll;
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

    @BeforeAll
    public static void gameTest() throws IOException {
        ArrayList<String> userCreationCommands = new ArrayList<>();
        userCreationCommands.add("user create --username Mehrshad --nickname MehrNick --password 0000");
        userCreationCommands.add("user create --username AmirAli --nickname AmirNick --password 0000");
        for (String command : userCreationCommands) {
            LoginMenuController.getInstance().createUser(command);
        }
        User.getUserByUsername("Mehrshad").setMoney(872300);
        User.getUserByUsername("AmirAli").setMoney(872300);
        ExcelUtils.getInstance().run();
        HashMap<String, Integer> cardsToBuy = new HashMap<>();
        putMonsterCardsToBuy(cardsToBuy);
        putSpellAndTrapCardsToBuy(cardsToBuy);
        for (Map.Entry<String, Integer> map : cardsToBuy.entrySet()) {
            String cardName = map.getKey();
            for (int i = 0; i < map.getValue(); i++) {
                ShopController.getInstance().buyCard(cardName, "Mehrshad");
                ShopController.getInstance().buyCard(cardName, "AmirAli");
            }
        }
        makeDeckForPlayers();
        DeckMenuController.getInstance().setActive("mehrDeck", "Mehrshad");
        DeckMenuController.getInstance().setActive("amirDeck", "AmirAli");
    }

    private static void putMonsterCardsToBuy(HashMap<String, Integer> cardsToBuy) {
        cardsToBuy.put("Battle OX", 3);
        cardsToBuy.put("Axe Raider", 3);
        cardsToBuy.put("Yomi Ship", 3);
        cardsToBuy.put("Horn Imp", 3);
        cardsToBuy.put("Silver Fang", 3);
        cardsToBuy.put("Suijin", 3);
        cardsToBuy.put("Fireyarou", 3);
        cardsToBuy.put("Curtain of the dark ones", 3);
        cardsToBuy.put("Feral Imp", 3);
        cardsToBuy.put("Dark magician", 3);
        cardsToBuy.put("Wattkid", 3);
        cardsToBuy.put("Baby dragon", 3);
        cardsToBuy.put("Hero of the east", 3);
        cardsToBuy.put("Battle warrior", 3);
        cardsToBuy.put("Crawling dragon", 3);
        cardsToBuy.put("Flame manipulator", 3);
        cardsToBuy.put("Blue-Eyes white dragon", 3);
        cardsToBuy.put("Crab Turtle", 3);
        cardsToBuy.put("Skull Guardian", 3);
        cardsToBuy.put("Slot Machine", 3);
        cardsToBuy.put("Haniwa", 3);
        cardsToBuy.put("Man-Eater Bug", 3);
        cardsToBuy.put("Gate Guardian", 3);
        cardsToBuy.put("Scanner", 4);
        cardsToBuy.put("Bitron", 3);
        cardsToBuy.put("Marshmallon", 3);
        cardsToBuy.put("Beast King Barbaros", 3);
        cardsToBuy.put("Texchanger", 3);
        cardsToBuy.put("Leotron", 3);
        cardsToBuy.put("The Calculator", 3);
        cardsToBuy.put("Alexandrite Dragon", 3);
        cardsToBuy.put("Mirage Dragon", 3);
        cardsToBuy.put("Herald of Creation", 3);
        cardsToBuy.put("Exploder Dragon", 3);
        cardsToBuy.put("Warrior Dai Grepher", 3);
        cardsToBuy.put("Dark Blade", 3);
        cardsToBuy.put("Wattaildragon", 3);
        cardsToBuy.put("Terratiger, the Empowered Warrior", 3);
        cardsToBuy.put("The Tricky", 3);
        cardsToBuy.put("Spiral Serpent", 3);
        cardsToBuy.put("Command Knight", 3);
    }

    private static void putSpellAndTrapCardsToBuy(HashMap<String, Integer> cardsToBuy) {
        cardsToBuy.put("Trap Hole", 3);
        cardsToBuy.put("Mirror Force", 3);
        cardsToBuy.put("Magic Cylinder", 3);
        cardsToBuy.put("Mind Crush", 3);
        cardsToBuy.put("Torrential Tribute", 3);
        cardsToBuy.put("Time Seal", 3);
        cardsToBuy.put("Negate Attack", 3);
        cardsToBuy.put("Solemn Warning", 3);
        cardsToBuy.put("Magic Jamamer", 3);
        cardsToBuy.put("Call of The Haunted", 3);
        cardsToBuy.put("Vanity's Emptiness", 3);
        cardsToBuy.put("Wall of Revealing Light", 3);
        cardsToBuy.put("Monster Reborn", 3);
        cardsToBuy.put("Terraforming", 3);
        cardsToBuy.put("Pot of Greed", 3);
        cardsToBuy.put("Raigeki", 3);
        cardsToBuy.put("Change of Heart", 3);
        cardsToBuy.put("Swords of Revealing Light", 3);
        cardsToBuy.put("Harpie's Feather Duster", 3);
        cardsToBuy.put("Dark Hole", 3);
        cardsToBuy.put("Supply Squad", 3);
        cardsToBuy.put("Spell Absorption", 3);
        cardsToBuy.put("Messenger of peace", 3);
        cardsToBuy.put("Twin Twisters", 3);
        cardsToBuy.put("Mystical space typhoon", 3);
        cardsToBuy.put("Ring of defense", 3);
        cardsToBuy.put("Yami", 3);
        cardsToBuy.put("Forest", 3);
        cardsToBuy.put("Closed Forest", 3);
        cardsToBuy.put("Umiiruka", 3);
        cardsToBuy.put("Sword of dark destruction", 3);
        cardsToBuy.put("Black Pendant", 3);
        cardsToBuy.put("United We Stand", 3);
        cardsToBuy.put("Magnum Shield", 3);
        cardsToBuy.put("Advanced Ritual Art", 3);
    }

    private static void makeDeckForPlayers() {
        User player1 = User.getUserByUsername("Mehrshad");
        User player2 = User.getUserByUsername("AmirAli");
        DeckMenuController.getInstance().createDeck("mehrDeck", "Mehrshad");
        DeckMenuController.getInstance().createDeck("amirDeck", "AmirAli");
        putCardsInDeck();
    }

    private static void putCardsInDeck() {
        DeckMenuController deckMenuController = DeckMenuController.getInstance();
        HashMap<String, Integer> cardsToAddToMainDeck = new HashMap<>();
        cardsSetNO60Appender(cardsToAddToMainDeck);
        for (Map.Entry<String, Integer> map : cardsToAddToMainDeck.entrySet()) {
            String cardName = map.getKey();
            for (int i = 0; i < map.getValue(); i++) {
                deckMenuController.addToDeck("mehrDeck", cardName, "Mehrshad", false);
                deckMenuController.addToDeck("amirDeck", cardName, "AmirAli", false);
            }
        }
        HashMap<String, Integer> cardsToAddToSideDeck = new HashMap<>();
        sideCardsSetAppender(cardsToAddToSideDeck);
        for (Map.Entry<String, Integer> map : cardsToAddToSideDeck.entrySet()) {
            String cardName = map.getKey();
            for (int i = 0; i < map.getValue(); i++) {
                deckMenuController.addToDeck("mehrDeck", cardName, "Mehrshad", true);
                deckMenuController.addToDeck("amirDeck", cardName, "AmirAli", true);
            }
        }
    }

    private static void cardsSetNO60Appender(HashMap<String, Integer> cardsToAdd) {
        cardsToAdd.put("Yomi Ship", 1);
        cardsToAdd.put("Suijin", 1);
        cardsToAdd.put("Crab Turtle", 1);
        cardsToAdd.put("Skull Guardian", 1);
        cardsToAdd.put("Man-Eater Bug", 1);
        cardsToAdd.put("Gate Guardian", 1);
        cardsToAdd.put("Scanner", 1);
        cardsToAdd.put("Marshmallon", 1);
        cardsToAdd.put("Beast King Barbaros", 1);
        cardsToAdd.put("Texchanger", 1);
        cardsToAdd.put("The Calculator", 1);
        cardsToAdd.put("Mirage Dragon", 1);
        cardsToAdd.put("Herald of Creation", 1);
        cardsToAdd.put("Exploder Dragon", 1);
        cardsToAdd.put("Terratiger, the Empowered Warrior", 1);
        cardsToAdd.put("The Tricky", 1);
        cardsToAdd.put("Command Knight", 1);
        cardsToAdd.put("Yami", 1);
        cardsToAdd.put("Forest", 1);
        cardsToAdd.put("Closed Forest", 1);
        cardsToAdd.put("Umiiruka", 1);
        cardsToAdd.put("Harpie's Feather Duster", 1);
        cardsToAdd.put("Dark Hole", 1);
        cardsToAdd.put("Mind Crush", 1);
        cardsToAdd.put("Flame manipulator", 1);
        cardsToAdd.put("Blue-Eyes white dragon", 1);
        cardsToAdd.put("Advanced Ritual Art", 2);
        cardsToAdd.put("Supply Squad", 2);
        cardsToAdd.put("Spell Absorption", 2);
        cardsToAdd.put("Messenger of peace", 2);
        cardsToAdd.put("Trap Hole", 2);
        cardsToAdd.put("Torrential Tribute", 2);
        cardsToAdd.put("Magic Jamamer", 2);
        cardsToAdd.put("Call of The Haunted", 2);
        cardsToAdd.put("Pot of Greed", 2);
        cardsToAdd.put("Raigeki", 2);
        cardsToAdd.put("Battle OX", 2);
        cardsToAdd.put("United We Stand", 3);
        cardsToAdd.put("Mystical space typhoon", 3);
        cardsToAdd.put("Monster Reborn", 3);
        cardsToAdd.put("notExist", 1);
        cardsToAdd.put("Change of Heart", 3);
    }

    private static void sideCardsSetAppender(HashMap<String, Integer>cardsToAdd) {
        cardsToAdd.put("Axe Raider", 3);
        cardsToAdd.put("Feral Imp", 3);
        cardsToAdd.put("Slot Machine", 1);
        cardsToAdd.put("Leotron", 3);
        cardsToAdd.put("Alexandrite Dragon", 1);
        cardsToAdd.put("Wattaildragon", 1);
        cardsToAdd.put("Spiral Serpent", 1);
        cardsToAdd.put("Baby dragon", 2);
    }

    @BeforeAll
    public static void createTestUsers() {
        ArrayList<String> creatTestUserCommands = new ArrayList<>();
        creatTestUserCommands.add("user create --username repetitiveUsername --nickname cool --password 12345");
        creatTestUserCommands.add("user create --username username --nickname repetitiveNickname --password 12345");
        creatTestUserCommands.add("user create --username bothRepetitive --nickname bothRepetitive --password 12345");
        for (String createTestUserCommand : creatTestUserCommands) {
            LoginMenuController.getInstance().createUser(createTestUserCommand);
        }
        DeckMenuController.getInstance().createDeck("usernameDeck", "username");
        DeckMenuController.getInstance().setActive("usernameDeck", "username");
        DeckMenuController.getInstance().createDeck("bothRepetitiveDeck", "bothRepetitive");
        DeckMenuController.getInstance().setActive("bothRepetitiveDeck", "bothRepetitive");
    }


    @Test
    public void viewTest() {
        StringBuilder commands = new StringBuilder();
        StringBuilder validOutputs = new StringBuilder();

        loginViewIOAppender(commands, validOutputs);
        mainMenuViewIOAppender(commands, validOutputs);
        profileViewIOAppender(commands, validOutputs);
        differentDuelMenuViewIOAppenderExecutor(commands, validOutputs);
        scoreBoardMenuViewIOAppender(commands, validOutputs);
        shopViewIOAppender(commands, validOutputs);
        deckMenuViewIOAppender(commands, validOutputs);
        duelViewAppender(commands, validOutputs);

        ByteArrayInputStream inputStream = new ByteArrayInputStream(commands.toString().getBytes());
        System.setIn(inputStream);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        LoginMenuView.getInstance().run();
        MainMenuView.getInstance().run("Mehrshad");
        MainMenuView.getInstance().run("Mehrshad");
        ProfileView.getInstance().run("Mehrshad");
        differentDuelMenuViewsExecutor();
        ScoreBoardView.getInstance().run();
        ShopView.getInstance().run("Mehrshad");
        DeckMenuView.getInstance().run("Mehrshad");
        DuelMenuView.getInstance().run("Mehrshad");

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

    private void differentDuelMenuViewIOAppenderExecutor(StringBuilder commands, StringBuilder validOutputs) {
        duelViewNoDeckIOAppender(commands, validOutputs);
        duelViewInvalidDeckIOAppender(commands, validOutputs);
        duelViewInvalidRoundsIOAppender(commands, validOutputs);
    }

    private void differentDuelMenuViewsExecutor() {
        DuelMenuView.getInstance().run("repetitiveUsername");
        DuelMenuView.getInstance().run("username");
        DuelMenuView.getInstance().run("Mehrshad");
    }

    private void duelViewNoDeckIOAppender(StringBuilder inputStringBuilder, StringBuilder outputStringBuilder) {
        //TODO
        inputStringBuilder.append("menu show-current\nmenu enter Duel\nmenu enter Deck\nmenu enter Shop\n" +
                "menu enter Scoreboard\nmenu enter Profile\nmenu enter Import/Export\nduel invalid\n" +
                "invalid\nduel --new --second-player noSuchPlayer --rounds 1\n" +
                "duel --new --second-player username --rounds 1\n" +
                "menu exit\n");

        outputStringBuilder.append("Duel Menu\r\nmenu navigation is not possible\r\n" +
                "menu navigation is not possible\r\nmenu navigation is not possible\r\n" +
                "menu navigation is not possible\r\nmenu navigation is not possible\r\n" +
                "menu navigation is not possible\r\ninvalid command\r\ninvalid command\r\n" +
                "there is no player with this username\r\nrepetitiveUsername has no active deck\r\n");
    }

    private void duelViewInvalidDeckIOAppender(StringBuilder inputStringBuilder, StringBuilder outputStringBuilder) {
        inputStringBuilder.append("duel --new --second-player repetitiveUsername --rounds 1\n" +
                "duel --new --second-player bothRepetitive --rounds 1\n" +
                "menu exit\n");

        outputStringBuilder.append("repetitiveUsername has no active deck\r\n" +
                "username's deck is invalid\r\n");
    }

    private void duelViewInvalidRoundsIOAppender(StringBuilder inputStringBuilder, StringBuilder outputStringBuilder) {
        inputStringBuilder.append("duel --new --second-player username --rounds 1\n" +
                "duel --new --second-player AmirAli --rounds 2\n" +
                "menu exit\n");


        outputStringBuilder.append("username's deck is invalid\r\nnumber of rounds is not supported\r\n");
    }

    private void scoreBoardMenuViewIOAppender(StringBuilder inputStringBuilder, StringBuilder outputStringBuilder) {
        inputStringBuilder.append("menu show-current\nmenu enter Duel\ninvalid command\n" +
                "scoreboard show\nmenu exit\n");


        outputStringBuilder.append("Scoreboard\r\nmenu navigation is not possible\r\ninvalid command\r\n" +
                "1- AmirNick: 0\n" +
                "1- MehrNick: 0\n" +
                "1- abbas: 0\n" +
                "1- aliPalang: 0\n" +
                "1- bothRepetitive: 0\n" +
                "1- cool: 0\n" +
                "1- login: 0\n" +
                "1- mmd: 0\n" +
                "1- mmdi: 0\n" +
                "1- repetitiveNickname: 0\n" +
                "1- rezaiii: 0\n" +
                "1- rza: 0\n" +
                "1- testNickname: 0\n");
    }

    private void shopViewIOAppender(StringBuilder inputStringBuilder, StringBuilder outputStringBuilder) {
        inputStringBuilder.append("menu show-current\nmenu enter Duel\ninvalid command\ncard show aliabad\n" +
                "card show Hero of the east\nincrease --money 0\nshop show --all\nshop buy not a card\n" +
                "shop buy Dark Blade\nmenu exit\n");

        outputStringBuilder.append("Shop Menu\r\nmenu navigation is not possible\r\ninvalid command\r\n" +
                "card with this name, could not be found!\r\nName: Hero of the east\n" +
                "Level: 3\nType: Normal\nATK: 1100\n" +
                "DEF: 1000\nDescription: Feel da strength ah dis sword-swinging samurai from da Far East.\r\n" +
                "your money increased by the value of 0!\r\n" +
                "Advanced Ritual Art:3000\nAlexandrite Dragon:2600\nAxe Raider:3100\nBaby dragon:1600\n" +
                "Battle OX:2900\nBattle warrior:1300\nBeast King Barbaros:9200\nBitron:1000\nBlack Pendant:4300\n" +
                "Blue-Eyes white dragon:11300\nCall of The Haunted:3500\nChange of Heart:2500\nClosed Forest:4300\n" +
                "Command Knight:2100\nCrab Turtle:10200\nCrawling dragon:3900\nCurtain of the dark ones:700\n" +
                "Dark Blade:3500\nDark Hole:2500\nDark magician:8300\nExploder Dragon:1000\nFeral Imp:2800\n" +
                "Fireyarou:2500\nFlame manipulator:1500\nForest:4300\nGate Guardian:20000\nHaniwa:600\n" +
                "Harpie's Feather Duster:2500\nHerald of Creation:2700\nHero of the east:1700\nHorn Imp:2500\n" +
                "Leotron:2500\nMagic Cylinder:2000\nMagic Jamamer:3000\nMagnum Shield:4300\nMan-Eater Bug:600\n" +
                "Marshmallon:700\nMessenger of peace:4000\nMind Crush:2000\nMirage Dragon:2500\nMirror Force:2000\n" +
                "Monster Reborn:2500\nMystical space typhoon:3500\nNegate Attack:3000\nPot of Greed:2500\n" +
                "Raigeki:2500\nRing of defense:3500\nScanner:8000\nSilver Fang:1700\nSkull Guardian:7900\n" +
                "Slot Machine:7500\nSolemn Warning:3000\nSpell Absorption:4000\nSpiral Serpent:11700\n" +
                "Suijin:8700\nSupply Squad:4000\nSword of dark destruction:4300\nSwords of Revealing Light:2500\n" +
                "Terraforming:2500\nTerratiger, the Empowered Warrior:3200\nTexchanger:200\nThe Calculator:8000\n" +
                "The Tricky:4300\nTime Seal:2000\nTorrential Tribute:2000\nTrap Hole:2000\nTwin Twisters:3500\n" +
                "Umiiruka:4300\nUnited We Stand:4300\nVanity's Emptiness:3500\nWall of Revealing Light:3500\n" +
                "Warrior Dai Grepher:3400\nWattaildragon:5800\nWattkid:1300\nYami:4300\nYomi Ship:1700\n" +
                "there is no card with this name\r\nnot enough money\r\n");
    }

    private void deckMenuViewIOAppender(StringBuilder inputStringBuilder, StringBuilder outputStringBuilder) {
        inputStringBuilder.append("menu show-current\nmenu enter Duel\ninvalid\ndeck invalid\ncard show  aa\n" +
                "deck create invalid deckName\ndeck create mehrDeck\ndeck create testDeck\ndeck delete i n\n" +
                "deck delete notExist\ndeck set-activate i n\ndeck set-activate notExist\n" +
                "deck set-activate testDeck\ndeck show --all\n" +
                "deck add-card --card Battle OX --deck testDeck --side\n" +
                "deck add-card --card Battle OX --side --deck testDeck\n" +
                "deck add-card --side --card Battle OX --deck testDeck\n" +
                "deck add-card --deck testDeck --card Command Knight --side\n" +
                "deck add-card --deck testDeck --side --card Axe Raider\n" +
                "deck add-card --side --deck testDeck --card Axe Raider\n" +
                "deck add-card --card Battle OX --side --deck testDeck\n" +
                "deck add-card --card Battle OX --side --deck test\n" +
                "deck add-card -card Battle OX --side --deck testDeck\n" +
                "deck add-card --card Yomi Ship --deck testDeck\n" +
                "deck add-card --deck testDeck --card Yomi Ship\n" +
                "deck add-card --deck testDeck --card Scanner\ndeck add-card --deck testDeck --card Scanner\n" +
                "deck add-card --deck testDeck --card Scanner\ndeck add-card --deck testDeck --card Scanner\n" +
                "deck add-card --deck testDeck -card Yomi Ship\n" +
                "deck rm-card --card Battle OX --deck testDeck --side\n" +//
                "deck rm-card --card Battle OX --side --deck testDeck\n" +
                "deck rm-card --side --card Command Knight --deck testDeck\n" +
                "deck rm-card --deck testDeck --card Yomi Ship --side\n" +
                "deck rm-card --deck test --side --card Battle OX\n" +
                "deck rm-card --side --deck testDeck --card aaaaa\n" +
                "deck rm-card --side --deck testDeck -card aaaaa\n" +
                "deck rm-card --card Yomi Ship --deck testDeck\n" +
                "deck rm-card --deck testDeck --card Battle OX\n" +//
                "deck rm-card --deck test --card Battle OX\n" +
                "deck rm-card --deck testDeck -card Battle OX\ndeck rm-card --deck testDeck --card Scanner\n" +
                "deck rm-card --deck testDeck --card Scanner\ndeck rm-card --deck testDeck --card Scanner\n" +
                "deck show --deck-name testDeck --side\n" +
                "deck show --side --deck-name test\n" +
                "deck show --deck-name testDeck\n" +
                "deck show --ddeck-name testDeck\n" +
                "deck delete testDeck\n" +
                "deck set-activate mehrDeck\n" +
                "menu exit\n");

        outputStringBuilder.append("Deck Menu\r\nmenu navigation is not possible\r\ninvalid command\r\n" +
                "invalid command\r\ninvalid command\r\ninvalid command\r\ndeck with name mehrDeck already exists\r\n" +
                "deck created successfully!\r\ninvalid command\r\ndeck with name notExist does not exist\r\n" +
                "invalid command\r\ndeck with name notExist does not exist\r\ndeck activated successfully\r\n" +
                "Decks:\nActive deck:\ntestDeck: main deck 0, side deck 0, invalid\n" +
                "Other decks:\nmehrDeck: main deck 60, side deck 15, valid\n" +
                "card added successfully\r\n" +
                "card added successfully\r\n" + "card added successfully\r\n" + "card added successfully\r\n" +
                "card added successfully\r\n" + "card added successfully\r\n" + "card with name Battle OX does not exists\r\n" +
                "deck with name test does not exist\r\n" + "invalid command\r\n" + "card added successfully\r\n" +
                "card added successfully\r\n" + "card added successfully\r\n" +
                "card added successfully\r\ncard added successfully\r\n" +
                "there are already three cards with name Scanner in deck testDeck\r\ninvalid command\r\n" +//
                "card removed from deck successfully\r\n" +
                "card removed from deck successfully\r\n" + "card removed from deck successfully\r\n" +
                "card with name Yomi Ship does not exist in side deck\r\n" + "deck with name test does not exist\r\n" +
                "card with name aaaaa does not exist in side deck\r\n" + "invalid command\r\n" + "card removed from deck successfully\r\n" +
                "card with name Battle OX does not exist in main deck\r\n" + "deck with name test does not exist\r\n" +
                "invalid command\r\ncard removed from deck successfully\r\ncard removed from deck successfully\r\n" +
                "card removed from deck successfully\r\n" +
                "Deck: testDeck\nSide deck:\nMonsters:\n" +
                "Axe Raider: An axe-wielding monster of tremendous strength and agility.\n" +
                "Axe Raider: An axe-wielding monster of tremendous strength and agility.\n" +
                "Battle OX: A monster with tremendous power, it destroys enemies with a swing of its axe.\nSpells and Traps:\n" +
                "deck with name test does not exist\n" + "Deck: testDeck\nSide deck:\nMonsters:\n" +
                "Yomi Ship: If this card is destroyed by battle and sent to the GY: Destroy the monster that destroyed this card.\n" +
                "Spells and Traps:\n" + "invalid command\n" +
                "deck deleted successfully\r\ndeck activated successfully\r\n");
    }

    public void duelViewAppender(StringBuilder inputStringBuilder, StringBuilder outputStringBuilder) {
        //TODO
        //traversing inside the menu
        inputStringBuilder.append("duel invalid\nduel --new --second-player AmirAli --rounds 1\n" +
                "rock\nscissors\nMehrshad\nsurrender\n" +
                "menu exit");


        outputStringBuilder.append("invalid command\r\nMehrNick, please choose between Rock, Paper or Scissors:\r\n" +
                "AmirNick, please choose between Rock, Paper or Scissors:\r\n" +
                "MehrNick, please choose the first player to go: Mehrshad or AmirAli\r\nphase: draw phase\r\n" +
                "new card added to the hand : Mirage Dragon\r\n" + //TODO
                "AmirNick:8000\n\tc\tc\tc\tc\tc\n55\n\tE\tE\tE\tE\tE\n\tE\tE\tE\tE\tE\n0\t\t\t\t\t\tE\n" +
                "\n--------------------------\n\nE\t\t\t\t\t\t0\n\tE\tE\tE\tE\tE\n\tE\tE\tE\tE\tE\n" +
                "\t\t\t\t\t\t54\tc\tc\tc\tc\tc\tc\nMehrNick:8000\nAmirAli won the whole match\r\n");
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

    @Test
    public void deckControllerTest() {
        String fullMainDeckTestResult = DeckMenuController.getInstance().addToDeck("mehrDeck", "Scanner", "Mehrshad", false);
        String fullSideDeckTestResult = DeckMenuController.getInstance().addToDeck("mehrDeck", "Scanner", "Mehrshad", true);
        assertEquals("main deck is full", fullMainDeckTestResult);
        assertEquals("side deck is full", fullSideDeckTestResult);
    }


}