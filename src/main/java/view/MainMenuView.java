package view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainMenuView {

    private static MainMenuView mainMenuView;
    static Pattern menuEnter = Pattern.compile("^menu enter (Duel|Deck|Scoreboard|Profile|Shop|Import/Export)$");

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
        Matcher matcher = menuEnter.matcher(command);
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
            if (menuName.equals("Shop")) {
                ShopView.getInstance().run(username);
                return;
            }
            if (menuName.equals("Deck")) {
                DeckMenuView.getInstance().run(username);
                return;
            }
            if (menuName.equals("Duel")) {
                DuelMenuView.getInstance().run(username);
                return;
            }
            if (menuName.equals("Import/Export")) {
                //TODO Make a Import/Export Menu
            }
        } else {
            System.out.println("invalid command");
        }
    }
}
