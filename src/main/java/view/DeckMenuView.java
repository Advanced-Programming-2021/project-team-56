package view;

import controller.DeckMenuController;

import java.util.regex.Matcher;

public class DeckMenuView {

    private static DeckMenuView deckMenuView;

    private DeckMenuView() {
    }

    public static DeckMenuView getInstance() {
        if (deckMenuView == null)
            deckMenuView = new DeckMenuView();
        return deckMenuView;
    }

    public void run(String username) {
        while (true) {
            String command = LoginMenuView.scan.nextLine();
            command = command.trim();
            if (command.equals("menu show-current")) {
                System.out.println("Deck Menu");
                continue;
            }
            if (command.equals("menu exit")) {
                return;
            }
            if (command.startsWith("menu enter")) {
                checkMenuEnterCommand(command);
                continue;
            }
            if (command.startsWith("deck")) {
                checkDeckCommands(command, username);
                continue;
            }
            System.out.println("invalid command");
        }
    }

    private void checkMenuEnterCommand(String command) {
        String regex = "^menu enter (?:Duel|Deck|Scoreboard|Profile|Shop|Import/Export)$";
        if (LoginMenuView.getMatcher(command, regex).find()) {
            System.out.println("menu navigation is not possible");
        } else {
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
        if (command.equals("deck show --all")) {
            String result = DeckMenuController.getInstance().showUsersDecks(username);
            System.out.println(result);
            return;
        }
        if (command.startsWith("deck show")) {
            checkShowDeckCommand(username);
        }
    }

    private void checkCreateDeckCommand(String command, String username) {
        Matcher matcher = LoginMenuView.getMatcher(command, "^deck create (\\S+)$");
        if (matcher.find()) {
            String result = DeckMenuController.getInstance().createDeck(matcher.group(1), username);
            System.out.println(result);
        } else {
            System.out.println("invalid command");
        }
    }

    private void checkDeleteDeckCommand(String command, String username) {
        Matcher matcher = LoginMenuView.getMatcher(command, "^deck delete (\\S+)$");
        if (matcher.find()) {
            String result = DeckMenuController.getInstance().deleteDeck(matcher.group(1), username);
            System.out.println(result);
        } else {
            System.out.println("invalid command");
        }
    }

    private void checkSetDeckActivateCommand(String command, String username) {
        Matcher matcher = LoginMenuView.getMatcher(command, "^deck set-activate (\\S+)$");
        if (matcher.find()) {
            String result = DeckMenuController.getInstance().setActive(matcher.group(1), username);
            System.out.println(result);
        } else {
            System.out.println("invalid command");
        }
    }

    private void checkAddCardCommand(String command, String username) {
        Matcher matcher = LoginMenuView.getMatcher(command, "");
    }

    private void checkRemoveCardCommand(String username) {

    }

    private void checkShowDeckCommand(String username) {

    }
}
