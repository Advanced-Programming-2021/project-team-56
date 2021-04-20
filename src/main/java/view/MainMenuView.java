package view;

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
            command = LoginMenuView.scan.nextLine().trim();

            if (command.equals("menu show-current")) {
                System.out.println("Main Menu");
                continue;
            }
            if (command.startsWith("menu enter")) {
                checkEnterMenuCommand(command, username);
                continue;
            }
            if (command.equals("menu exit")) {
                return;
            }
            if (command.equals("user logout")) {
                System.out.println("user logged out successfully!");
                return;
            }
            System.out.println("invalid command");
        }
    }

    private void checkEnterMenuCommand(String command, String username) {
        String regex = "^menu enter (Login|Duel|Deck|Scoreboard|Profile|Shop|Import\\/Export)$";
        Matcher matcher = LoginMenuView.getMatcher(command, regex);
        if (matcher.find()) {
            String menuName = matcher.group(1);
            if (menuName.equals("Scoreboard")) {
                ScoreBoardView.getInstance().run();
                return;
            }
            if (menuName.equals("Profile")) {
                ProfileView.getInstance().run(username);
                return;
            }
            //TODO Complete the menus
            if (menuName.equals("Shop")) {
                ShopView.getInstance().run(username);
            }
            if (menuName.equals("Deck")) {
                DeckMenuView.getInstance().run(username);
            }
            if (menuName.equals("Duel")) {
                //TODO Make a MainDuelMenu and run that
            }
            if (menuName.equals("Import/Export")) {
                //TODO Make a Import/Export Menu
            }
        } else {
            System.out.println("invalid command");
        }
    }
}
