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
        //TODO Complete the checkDeckCommands
        if (command.startsWith("deck create")) {
            checkCreateDeckCommand(command, username);
            return;
        } // done
        if (command.startsWith("deck delete")) {
            checkDeleteDeckCommand(command, username);
            return;
        } //done
        if (command.startsWith("deck set-activate")) {
            checkSetDeckActivateCommand(command, username);
            return;
        } //done
        if (command.startsWith("deck add-card")) {
            checkAddCardCommand(command, username);
            return;
        } //undone
        if (command.startsWith("deck rm-card")) {
            checkRemoveCardCommand(username);
            return;
        }
        //TODO make these two one: deck show
        //کامنت کردم که باگ نخوره
//        if (command.equals("deck show --all")) {
//            String result = DeckMenuController.getInstance().showUsersDecks(username);
//            System.out.println(result);
//            return;
//        }
//        if (command.startsWith("deck show")) {
//            checkShowDeckCommand(username);
//        }
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
            return deckMenuController.addToSideDeck(matcher.group(2), matcher.group(1), username);
        }
        matcher = addToSideDeck2.matcher(command);
        if (matcher.find()) {
            return deckMenuController.addToSideDeck(matcher.group(2), matcher.group(1), username);
        }
        matcher = addToSideDeck3.matcher(command);
        if (matcher.find()) {
            return deckMenuController.addToSideDeck(matcher.group(2), matcher.group(1), username);

        }
        matcher = addToSideDeck4.matcher(command);
        if (matcher.find()) {
            return deckMenuController.addToSideDeck(matcher.group(1), matcher.group(2), username);
        }
        matcher = addToSideDeck5.matcher(command);
        if (matcher.find()) {
            return deckMenuController.addToSideDeck(matcher.group(1), matcher.group(2), username);

        }
        matcher = addToSideDeck6.matcher(command);
        if (matcher.find()) {
            return deckMenuController.addToSideDeck(matcher.group(1), matcher.group(2), username);
        }
        return "invalid command";
    }

    private String checkAddCardToMainDeckCommand(String command, String username) {
        DeckMenuController deckMenuController = DeckMenuController.getInstance();
        Matcher matcher = addToMainDeck1.matcher(command);
        if (matcher.find()) {
            return deckMenuController.addToMainDeck(matcher.group(2), matcher.group(1), username);
        }
        matcher = addToMainDeck2.matcher(command);
        if (matcher.find()) {
            return deckMenuController.addToMainDeck(matcher.group(1), matcher.group(2), username);
        }
        return "invalid command";
    }

    private void checkRemoveCardCommand(String username) {

    }

    private void checkShowDeckCommand(String username) {

    }
}
