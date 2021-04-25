package view;

import model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DuelMenuView {

    private static DuelMenuView duelMenuView;
    static Pattern menuEnter = Pattern.compile("^menu enter (?:Login|Duel|Deck|Scoreboard|Profile|Shop|Import\\/Export)$");
    static Pattern newUserDuel1 = Pattern.compile("^duel --rounds (\\d+) --new --second-player (\\S+)$");
    static Pattern newUserDuel2 = Pattern.compile("^duel --new --rounds (\\d+) --second-player (\\S+)$");
    static Pattern newUserDuel3 = Pattern.compile("^duel --rounds (\\d+) --new --second-player (\\S+)$");
    static Pattern newUserDuel4 = Pattern.compile("^duel --second-player (\\S+) --new --rounds (\\d+)$");
    static Pattern newUserDuel5 = Pattern.compile("^duel --second-player (\\S+) --rounds (\\d+) --new$");
    static Pattern newUserDuel6 = Pattern.compile("^duel --new --second-player (\\S+) --rounds (\\d+)$");

    public static DuelMenuView getInstance() {
        if (duelMenuView == null)
            duelMenuView = new DuelMenuView();
        return duelMenuView;
    }

    public void run(String username) {
        while (true) {
            String command = LoginMenuView.scan.nextLine().trim();
            Matcher menuEnterMatcher = menuEnter.matcher(command);
            if (menuEnterMatcher.find()) {
                System.out.println("menu navigation is not possible");
                continue;
            }
            if (command.equals("menu exit")) {
                return;
            }
            if (command.equals("menu show-current")) {
                System.out.println("Duel Menu");
                continue;
            }
            if (command.startsWith("duel")) {
                if (command.contains("--ai") && (!command.contains("--second-player"))) {
                    checkDuelWithAICommand(command, username);
                } else {
                    checkDuelWithUserCommand(command, username);
                }
            }
            System.out.println("invalid command");
        }
    }

    private void checkDuelWithUserCommand(String command, String username) {
        Matcher matcher = newUserDuel1.matcher(command);
        if (matcher.find()) {
            checkDuelWithUser(username, matcher.group(2), matcher.group(1));
            return;
        }
        matcher = newUserDuel2.matcher(command);
        if (matcher.find()) {
            checkDuelWithUser(username, matcher.group(2), matcher.group(1));
            return;
        }
        matcher = newUserDuel3.matcher(command);
        if (matcher.find()) {
            checkDuelWithUser(username, matcher.group(2), matcher.group(1));
            return;
        }
        matcher = newUserDuel4.matcher(command);
        if (matcher.find()) {
            checkDuelWithUser(username, matcher.group(1), matcher.group(2));
            return;
        }
        matcher = newUserDuel5.matcher(command);
        if (matcher.find()) {
            checkDuelWithUser(username, matcher.group(1), matcher.group(2));
            return;
        }
        matcher = newUserDuel6.matcher(command);
        if (matcher.find()) {
            checkDuelWithUser(username, matcher.group(1), matcher.group(2));
            return;
        }
        System.out.println("invalid command");
    }

    private void checkDuelWithUser(String username, String secondPlayerUsername, String rounds) {
        if (User.isThisUsernameAlreadyTaken(secondPlayerUsername)) {
            if ()
        }
        System.out.println("there is no player with this username");
    }
}
