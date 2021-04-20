package view;

import controller.DeckMenuController;

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

    private void checkMenuEnterCommand(String Command) {
        String regex = "^menu enter (?:Duel|Deck|Scoreboard|Profile|Shop|Import/Export)$";
        LoginMenuView.getMatcher(comma)
    }

    private void checkDeckCommands(String command, String username) {
        if (command.startsWith("deck create")) {
            checkCreateDeckCommand(username);
            return;
        }
        if (command.startsWith("deck delete")) {
            checkDeleteDeckCommand();
            return;
        }
        if (command.startsWith("deck set-activate")) {
            checkSetDeckActivateCommand();
            return;
        }
        if (command.startsWith("deck add-card")) {
            checkAddCardCommand();
            return;
        }
        if (command.startsWith("deck rm-card")) {
            checkRemoveCardCommand();
            return;
        }
        if (command.equals("deck show --all")) {
            String result = DeckMenuController.getInstance().showUsersDecks(username);
            System.out.println(result);
            return;
        }
        if (command.startsWith("deck show")) {
            checkShowDeckCommand();
            return;
        }
    }
}
