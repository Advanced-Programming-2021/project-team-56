import controller.DeckMenuController;
import controller.LoginMenuController;
import controller.ShopController;
import controller.duel.DuelWithUser;
import controller.duel.phases.BattlePhaseController;
import model.*;
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
        Initialize.putMonsterCardsToBuy(cardsToBuy);
        Initialize.putSpellAndTrapCardsToBuy(cardsToBuy);
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

    private static void makeDeckForPlayers() {
        DeckMenuController.getInstance().createDeck("mehrDeck", "Mehrshad");
        DeckMenuController.getInstance().createDeck("amirDeck", "AmirAli");
        putCardsInDeck();
    }

    private static void putCardsInDeck() {
        DeckMenuController deckMenuController = DeckMenuController.getInstance();
        HashMap<String, Integer> cardsToAddToMainDeck = new HashMap<>();
        Initialize.cardsSetNO60Appender(cardsToAddToMainDeck);
        for (Map.Entry<String, Integer> map : cardsToAddToMainDeck.entrySet()) {
            String cardName = map.getKey();
            for (int i = 0; i < map.getValue(); i++) {
                deckMenuController.addToDeck("mehrDeck", cardName, "Mehrshad", false);
                deckMenuController.addToDeck("amirDeck", cardName, "AmirAli", false);
            }
        }
        HashMap<String, Integer> cardsToAddToSideDeck = new HashMap<>();
        Initialize.sideCardsSetAppender(cardsToAddToSideDeck);
        for (Map.Entry<String, Integer> map : cardsToAddToSideDeck.entrySet()) {
            String cardName = map.getKey();
            for (int i = 0; i < map.getValue(); i++) {
                deckMenuController.addToDeck("mehrDeck", cardName, "Mehrshad", true);
                deckMenuController.addToDeck("amirDeck", cardName, "AmirAli", true);
            }
        }
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
        IOAppender.loginViewIOAppender(commands, validOutputs);
        IOAppender.mainMenuViewIOAppender(commands, validOutputs);
        IOAppender.profileViewIOAppender(commands, validOutputs);
        differentDuelMenuViewIOAppenderExecutor(commands, validOutputs);
        IOAppender.scoreBoardMenuViewIOAppender(commands, validOutputs);
        IOAppender.shopViewIOAppender(commands, validOutputs);
        IOAppender.deckMenuViewIOAppender(commands, validOutputs);
        IOAppender.duelViewAppender1(commands, validOutputs);
        IOAppender.duelViewAppender2(commands, validOutputs);
//        battleViewAppender(commands, validOutputs);
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
        DuelMenuView.getInstance().run("Mehrshad");
        assertEquals(validOutputs.toString(), outputStream.toString());
    }

    private void differentDuelMenuViewIOAppenderExecutor(StringBuilder commands, StringBuilder validOutputs) {
        IOAppender.duelViewNoDeckIOAppender(commands, validOutputs);
        IOAppender.duelViewInvalidDeckIOAppender(commands, validOutputs);
        IOAppender.duelViewInvalidRoundsIOAppender(commands, validOutputs);
    }

    private void differentDuelMenuViewsExecutor() {
        DuelMenuView.getInstance().run("repetitiveUsername");
        DuelMenuView.getInstance().run("username");
        DuelMenuView.getInstance().run("Mehrshad");
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

//    private void battleViewAppender(StringBuilder commands, StringBuilder validOutputs) {
//        commands.append("summon");
//        validOutputs.append("phase: battle phase\r\n" + "action not allowed in this phase\r\n");
//    }

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

    @Test
    public void selectTest() {
        User user1 = User.getUserByUsername("Mehrshad");
        User user2 = User.getUserByUsername("AmirAli");
        Board board1 = new Board(user1);
        Board board2 = new Board(user2);
        DuelWithUser.getInstance().getBoards()[0] = board1;
        DuelWithUser.getInstance().getBoards()[1] = board2;
        for (Card card : user1.getUserAllCards()) {
            if (card.getName().equals("Forest")) {
                board1.setFieldSpell((SpellCard) card);
                board2.setFieldSpell((SpellCard) card);
            }
            if (card.getName().equals("Raigeki")) {
                board1.getSpellAndTrapTerritory().put(5, card);
                board2.getSpellAndTrapTerritory().put(2, card);
            }
            if (card.getName().equals("Command Knight")) {
                MonsterCard monsterCard = (MonsterCard) card;
                board1.getMonsterTerritory().put(1, monsterCard);
                board2.getMonsterTerritory().put(2, monsterCard);
            }
        }
        DuelWithUser.getInstance().selectCard("select --monster 1");
        assertEquals("Command Knight", DuelWithUser.getInstance().getMyBoard().getSelectedCard().getName());
        DuelWithUser.getInstance().selectCard("select --spell 5");
        assertEquals("Raigeki", DuelWithUser.getInstance().getMyBoard().getSelectedCard().getName());
        DuelWithUser.getInstance().selectCard("select --field");
        assertEquals("Forest", DuelWithUser.getInstance().getMyBoard().getSelectedCard().getName());
        DuelWithUser.getInstance().selectCard("select --monster 2 --opponent");
        assertEquals("Command Knight", DuelWithUser.getInstance().getMyBoard().getSelectedCard().getName());
        DuelWithUser.getInstance().selectCard("select --opponent --monster 3");
        assertEquals("Command Knight", DuelWithUser.getInstance().getMyBoard().getSelectedCard().getName());
        DuelWithUser.getInstance().selectCard("select --spell 2 --opponent");
        assertEquals("Raigeki", DuelWithUser.getInstance().getMyBoard().getSelectedCard().getName());
        DuelWithUser.getInstance().selectCard("select --opponent --spell 3");
        assertEquals("Raigeki", DuelWithUser.getInstance().getMyBoard().getSelectedCard().getName());
        DuelWithUser.getInstance().selectCard("select --field --opponent");
        assertEquals("Forest", DuelWithUser.getInstance().getMyBoard().getSelectedCard().getName());
        DuelWithUser.getInstance().deselectCard();
        assertNull(DuelWithUser.getInstance().getMyBoard().getSelectedCard());
    }

    @Test
    public void showSelectedCardAndShowGraveYard() {
        User user1 = User.getUserByUsername("Mehrshad");
        User user2 = User.getUserByUsername("AmirAli");
        Board board1 = new Board(user1);
        Board board2 = new Board(user2);
        DuelWithUser.getInstance().getBoards()[0] = board1;
        DuelWithUser.getInstance().getBoards()[1] = board2;
        for (Card card : user1.getUserAllCards()) {
            if (card.getName().equals("Silver Fang")) {
                board1.getMonsterTerritory().put(1, (MonsterCard) card);
            }
            if (card.getName().equals("Slot Machine")) {
                board1.getPlayerHand().add(card);
            }
            if (card.getName().equals("Yami") && board1.getGraveyard().size() == 0) {
                board1.getGraveyard().add(card);
            }
        }
        DuelWithUser.getInstance().selectCard("select --hand 1");
        assertEquals("Slot Machine", DuelWithUser.getInstance().getMyBoard().getSelectedCard().getName());
        DuelWithUser.getInstance().selectCard("select --monster 1");
        String graveYardOutput = DuelWithUser.getInstance().showGraveYard();
        String selectedCardOutput = DuelWithUser.getInstance().showSelectedCard();
        String output1 = "Yami:All Fiend and Spellcaster monsters on the field gain 200 ATK/DEF, also all Fairy monsters on the field lose 200 ATK/DEF.\n";
        String output2 = "Silver Fang: ATK, DEF = 1200, 800\n" +
                "A snow wolf that's beautiful to the eye, but absolutely vicious in battle.";
        assertEquals(selectedCardOutput, output2);
        assertEquals(graveYardOutput, output1);
    }

    @Test
    public void battleTest() {
        User user1 = User.getUserByUsername("Mehrshad");
        User user2 = User.getUserByUsername("AmirAli");
        Board board1 = new Board(user1);
        Board board2 = new Board(user2);
        DuelWithUser.getInstance().getBoards()[0] = board1;
        DuelWithUser.getInstance().getBoards()[1] = board2;
        for (Card card : user1.getUserAllCards()) {
            if (card.getName().equals("Battle OX")) {
                board1.getMonsterTerritory().put(1, (MonsterCard) card);
                board2.getMonsterTerritory().put(1, (MonsterCard) card);
            }
            if (card.getName().equals("Axe Raider")) board1.getMonsterTerritory().put(2, (MonsterCard) card);
            if (card.getName().equals("Silver Fang")) board2.getMonsterTerritory().put(2, (MonsterCard) card);
            if (card.getName().equals("Wattkid")) board1.getMonsterTerritory().put(3, (MonsterCard) card);
            if (card.getName().equals("Fireyarou")) board2.getMonsterTerritory().put(3, (MonsterCard) card);
        }
        DuelWithUser.getInstance().selectCard("select --monster 1");
        board1.getMonsterTerritory().get(1).setInAttackPosition(true);
        board1.getMonsterTerritory().get(1).setLastTimeAttackedTurn(50);
        BattlePhaseController.getInstance().attackCard(1);
        assertTrue(board1.getMonsterTerritory().get(1) == null && board2.getMonsterTerritory().get(1) == null);
        DuelWithUser.getInstance().selectCard("select --monster 2");
        board1.getMonsterTerritory().get(2).setInAttackPosition(true);
        board2.getMonsterTerritory().get(2).setInAttackPosition(true);
        board1.getMonsterTerritory().get(2).setLastTimeAttackedTurn(50);
        BattlePhaseController.getInstance().attackCard(2);
        assertTrue(board2.getLP() == 7500 && board2.getMonsterTerritory().get(2) == null);
        DuelWithUser.getInstance().selectCard("select --monster 3");
        board1.getMonsterTerritory().get(3).setInAttackPosition(true);
        board2.getMonsterTerritory().get(3).setInAttackPosition(true);
        board1.getMonsterTerritory().get(3).setLastTimeAttackedTurn(50);
        BattlePhaseController.getInstance().attackCard(3);
        assertTrue(board1.getLP() == 7700 && board1.getMonsterTerritory().get(3) == null);
    }
}