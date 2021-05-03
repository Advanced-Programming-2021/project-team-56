package view;

import controller.DeckMenuController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeckMenuView {

    private static DeckMenuView deckMenuView;
    static Pattern menuEnter = Pattern.compile("^menu enter (?:Duel|Deck|Scoreboard|Profile|Shop|Import/Export)$");
    static Pattern createDeck = Pattern.compile("^deck create (\\S+)$");
    static Pattern deleteDeck = Pattern.compile("^deck delete (\\S+)$");
    static Pattern setActive = Pattern.compile("^deck set-activate (\\S+)$");
    static Pattern addToSideDeck1 = Pattern.compile("^deck add-card --card (\\S+) --deck (\\S+) --side$");
    static Pattern addToSideDeck2 = Pattern.compile("^deck add-card --card (\\S+) --side --deck (\\S+)$");
    static Pattern addToSideDeck3 = Pattern.compile("^deck add-card --side --card (\\S+) --deck (\\S+)$");
    static Pattern addToSideDeck4 = Pattern.compile("^deck add-card --deck (\\S+) --card (\\S+) --side$");
    static Pattern addToSideDeck5 = Pattern.compile("^deck add-card --deck (\\S+) --side --card (\\S+)$");
    static Pattern addToSideDeck6 = Pattern.compile("^deck add-card --side --deck (\\S+) --card (\\S+)$");
    static Pattern addToMainDeck1 = Pattern.compile("^deck add-card --card (\\S+) --deck (\\S+)$");
    static Pattern addToMainDeck2 = Pattern.compile("^deck add-card --deck (\\S+) --card (\\S+)$");
    static Pattern removeFromSideDeck1 = Pattern.compile("^deck rm-card --card (\\S+) --deck (\\S+) --side$");
    static Pattern removeFromSideDeck2 = Pattern.compile("^deck rm-card --card (\\S+) --side --deck (\\S+)$");
    static Pattern removeFromSideDeck3 = Pattern.compile("^deck rm-card --side --card (\\S+) --deck (\\S+)$");
    static Pattern removeFromSideDeck4 = Pattern.compile("^deck rm-card --deck (\\S+) --card (\\S+) --side$");
    static Pattern removeFromSideDeck5 = Pattern.compile("^deck rm-card --deck (\\S+) --side --card (\\S+)$");
    static Pattern removeFromSideDeck6 = Pattern.compile("^deck rm-card --side --deck (\\S+) --card (\\S+)$");
    static Pattern removeFromMainDeck1 = Pattern.compile("^deck rm-card --card (\\S+) --deck (\\S+)$");
    static Pattern removeFromMainDeck2 = Pattern.compile("^deck rm-card --deck (\\S+) --card (\\S+)$");
    static Pattern showSideDeck1 = Pattern.compile("^deck show --deck-name (\\S+) --side$");
    static Pattern showSideDeck2 = Pattern.compile("^deck show --side --deck-name (\\S+)$");
    static Pattern showMainDeck = Pattern.compile("^deck show --deck-name (\\S+)$");

    private DeckMenuView() {
    }

    public static DeckMenuView getInstance() {
        if (deckMenuView == null)
            deckMenuView = new DeckMenuView();
        return deckMenuView;
    }

    public void run(String username) {
        while (true) {
            String command = LoginMenuView.scan.nextLine().trim();
            if (command.equals("menu show-current")) {
                System.out.println("Deck Menu");
                continue;
            }
            if (command.equals("menu exit")) {
                return;
            }
            Matcher matcher = menuEnter.matcher(command);
            if (matcher.find()) {
                System.out.println("menu navigation is not possible");
                continue;
            }
            if (command.startsWith("deck")) {
                checkDeckCommands(command, username);
                continue;
            }
            System.out.println("invalid command");
        }
    }

    private void checkDeckCommands(String command, String username) {
        if (command.startsWith("deck create")) {
            checkCreateDeckCommand(command, username);
            return;
        }
        if (command.startsWith("deck delete")) {
            checkDeleteDeckCommand(command, username);
            return;
        }
        if (command.startsWith("deck set-activate")) {
            checkSetDeckActivateCommand(command, username);
            return;
        }
        if (command.startsWith("deck add-card")) {
            checkAddCardCommand(command, username);
            return;
        }
        if (command.startsWith("deck rm-card")) {
            checkRemoveCardCommand(command, username);
            return;
        }
        if (command.equals("deck show --all")) {
            String result = DeckMenuController.getInstance().showUsersDecks(username);
            System.out.print(result);
            return;
        }
        if (command.startsWith("deck show")) {
            System.out.print(checkShowDeck(command, username));
            return;
        }
        if (command.equals("deck show --cards")) {
            String result = DeckMenuController.getInstance().showCards(username);
            System.out.print(result);
            return;
        }
        System.out.println("invalid command");
    }

    private void checkCreateDeckCommand(String command, String username) {
        Matcher matcher = createDeck.matcher(command);
        if (matcher.find()) {
            String result = DeckMenuController.getInstance().createDeck(matcher.group(1), username);
            System.out.println(result);
        } else {
            System.out.println("invalid command");
        }
    }

    private void checkDeleteDeckCommand(String command, String username) {
        Matcher matcher = deleteDeck.matcher(command);
        if (matcher.find()) {
            String result = DeckMenuController.getInstance().deleteDeck(matcher.group(1), username);
            System.out.println(result);
        } else {
            System.out.println("invalid command");
        }
    }

    private void checkSetDeckActivateCommand(String command, String username) {
        Matcher matcher = setActive.matcher(command);
        if (matcher.find()) {
            String result = DeckMenuController.getInstance().setActive(matcher.group(1), username);
            System.out.println(result);
        } else {
            System.out.println("invalid command");
        }
    }

    private void checkAddCardCommand(String command, String username) {
        if (command.contains("--side")) {
            System.out.println(checkAddCardToSideDeckCommand(command, username));
        } else {
            System.out.println(checkAddCardToMainDeckCommand(command, username));
        }
    }

    private String checkAddCardToSideDeckCommand(String command, String username) {
        DeckMenuController deckMenuController = DeckMenuController.getInstance();
        Matcher matcher = addToSideDeck1.matcher(command);
        if (matcher.find()) {
            return deckMenuController.addToDeck(matcher.group(2), matcher.group(1), username, true);
        }
        matcher = addToSideDeck2.matcher(command);
        if (matcher.find()) {
            return deckMenuController.addToDeck(matcher.group(2), matcher.group(1), username, true);
        }
        matcher = addToSideDeck3.matcher(command);
        if (matcher.find()) {
            return deckMenuController.addToDeck(matcher.group(2), matcher.group(1), username, true);
        }
        matcher = addToSideDeck4.matcher(command);
        if (matcher.find()) {
            return deckMenuController.addToDeck(matcher.group(1), matcher.group(2), username, true);
        }
        matcher = addToSideDeck5.matcher(command);
        if (matcher.find()) {
            return deckMenuController.addToDeck(matcher.group(1), matcher.group(2), username, true);

        }
        matcher = addToSideDeck6.matcher(command);
        if (matcher.find()) {
            return deckMenuController.addToDeck(matcher.group(1), matcher.group(2), username, true);
        }
        return "invalid command";
    }

    private String checkAddCardToMainDeckCommand(String command, String username) {
        DeckMenuController deckMenuController = DeckMenuController.getInstance();
        Matcher matcher = addToMainDeck1.matcher(command);
        if (matcher.find()) {
            return deckMenuController.addToDeck(matcher.group(2), matcher.group(1), username, false);
        }
        matcher = addToMainDeck2.matcher(command);
        if (matcher.find()) {
            return deckMenuController.addToDeck(matcher.group(1), matcher.group(2), username, false);
        }
        return "invalid command";
    }

    private void checkRemoveCardCommand(String command, String username) {
        if (command.contains("--side")) {
            System.out.println(checkRemoveFromSideDeck(command, username));
        } else {
            System.out.println(checkRemoveFromMainDeck(command, username));
        }
    }

    private String checkRemoveFromSideDeck(String command, String username) {
        DeckMenuController deckMenuController = DeckMenuController.getInstance();
        Matcher matcher = removeFromSideDeck1.matcher(command);
        if (matcher.find()) {
            return deckMenuController.removeFromSideDeck(matcher.group(2), matcher.group(1), username);
        }
        matcher = removeFromSideDeck2.matcher(command);
        if (matcher.find()) {
            return deckMenuController.removeFromSideDeck(matcher.group(2), matcher.group(1), username);
        }
        matcher = removeFromSideDeck3.matcher(command);
        if (matcher.find()) {
            return deckMenuController.removeFromSideDeck(matcher.group(2), matcher.group(1), username);
        }
        matcher = removeFromSideDeck4.matcher(command);
        if (matcher.find()) {
            return deckMenuController.removeFromSideDeck(matcher.group(1), matcher.group(2), username);
        }
        matcher = removeFromSideDeck5.matcher(command);
        if (matcher.find()) {
            return deckMenuController.removeFromSideDeck(matcher.group(1), matcher.group(2), username);
        }
        matcher = removeFromSideDeck6.matcher(command);
        if (matcher.find()) {
            return deckMenuController.removeFromSideDeck(matcher.group(1), matcher.group(2), username);
        }
        return "invalid command";
    }

    private String checkRemoveFromMainDeck(String command, String username) {
        DeckMenuController deckMenuController = DeckMenuController.getInstance();
        Matcher matcher = removeFromMainDeck1.matcher(command);
        if (matcher.find()) {
            return deckMenuController.removeFromMainDeck(matcher.group(2), matcher.group(1), username);
        }
        matcher = removeFromMainDeck2.matcher(command);
        if (matcher.find()) {
            return deckMenuController.removeFromMainDeck(matcher.group(1), matcher.group(2), username);
        }
        return "invalid command";
    }

    private String checkShowDeck(String command, String username) {
        DeckMenuController deckMenuController = DeckMenuController.getInstance();
        Matcher matcher = showSideDeck1.matcher(command);
        if (matcher.find()) {
            return deckMenuController.showMainOrSideDeck(matcher.group(1), username, "side");
        }
        matcher = showSideDeck2.matcher(command);
        if (matcher.find()) {
            return deckMenuController.showMainOrSideDeck(matcher.group(1), username, "side");
        }
        matcher = showMainDeck.matcher(command);
        if (matcher.find()) {
            return deckMenuController.showMainOrSideDeck(matcher.group(1), username, "main");
        }
        return "invalid command\n";
    }
}
