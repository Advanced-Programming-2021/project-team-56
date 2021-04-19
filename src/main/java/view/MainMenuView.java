package view;

import model.User;

import java.util.regex.Matcher;

public class MainMenuView {

    private static MainMenuView mainMenuView;

    private MainMenuView() {
    }

    public static MainMenuView getInstance() {
        if (mainMenuView == null)
            mainMenuView = new MainMenuView();
        return mainMenuView;
    }

    public void run(String username) {
        String command;
        while (true) {
            command = LoginMenuView.scan.nextLine();
            command = command.trim();

            if (command.equals("menu show-current")) {
                System.out.println("Main Menu");
                continue;
            }
            if (command.startsWith("menu enter")) {
                enterMenu(command);
            }
        }
    }

    private void enterMenu(String command) {
        String regex = "^menu enter (Duel|Deck|Scoreboard|Profile|Shop|Import\\/Export)$";
        Matcher matcher = LoginMenuView.getMatcher(command, regex);
        if (matcher.find()) {
            if ()
        }
    }
}
