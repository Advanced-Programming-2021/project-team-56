package view;

import controller.DuelMenuController;
import controller.duel.DuelWithUser;
import view.duel.DuelWithAIView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DuelMenuView {

    private static DuelMenuView duelMenuView;
    static Pattern menuEnter = Pattern.compile("^menu enter (?:Duel|Deck|Scoreboard|Profile|Shop|Import/Export)$");
    static Pattern newUserDuel1 = Pattern.compile("^duel --rounds (\\d+) --new --second-player (\\S+)$");
    static Pattern newUserDuel2 = Pattern.compile("^duel --new --rounds (\\d+) --second-player (\\S+)$");
    static Pattern newUserDuel3 = Pattern.compile("^duel --rounds (\\d+) --new --second-player (\\S+)$");
    static Pattern newUserDuel4 = Pattern.compile("^duel --second-player (\\S+) --new --rounds (\\d+)$");
    static Pattern newUserDuel5 = Pattern.compile("^duel --second-player (\\S+) --rounds (\\d+) --new$");
    static Pattern newUserDuel6 = Pattern.compile("^duel --new --second-player (\\S+) --rounds (\\d+)$");
    static Pattern newAIDuel1 = Pattern.compile("^duel --new --ai --rounds (\\d+)$");
    static Pattern newAIDuel2 = Pattern.compile("^duel --new --rounds (\\d+) --ai$");
    static Pattern newAIDuel3 = Pattern.compile("^duel --rounds (\\d+) --new --ai$");
    static Pattern newAIDuel4 = Pattern.compile("^duel --ai --new --rounds (\\d+)$");
    static Pattern newAIDuel5 = Pattern.compile("^duel --ai --rounds (\\d+) --new$");
    static Pattern newAIDuel6 = Pattern.compile("^duel --rounds (\\d+) --ai --new$");

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
                continue;
            }
            System.out.println("invalid command");
        }
    }

    private void checkDuelWithUserCommand(String command, String firstPlayerUsername) {
        ArrayList<Matcher> newUserDuelMatchers1 = new ArrayList<>();
        newUserDuelMatchers1.add(newUserDuel1.matcher(command));
        newUserDuelMatchers1.add(newUserDuel2.matcher(command));
        newUserDuelMatchers1.add(newUserDuel3.matcher(command));
        for (Matcher matcher : newUserDuelMatchers1) {
            if (matcher.find()) {
                checkDuelWithUser(firstPlayerUsername, matcher.group(2), matcher.group(1));
                return;
            }
        }
        ArrayList<Matcher> newUserDuelMatchers2 = new ArrayList<>();
        newUserDuelMatchers2.add(newUserDuel4.matcher(command));
        newUserDuelMatchers2.add(newUserDuel5.matcher(command));
        newUserDuelMatchers2.add(newUserDuel6.matcher(command));
        for (Matcher matcher : newUserDuelMatchers2) {
            if (matcher.find()) {
                checkDuelWithUser(firstPlayerUsername, matcher.group(1), matcher.group(2));
                return;
            }
        }
        System.out.println("invalid command");
    }

    private void checkDuelWithUser(String firstPlayerUsername, String secondPlayerUsername, String numberOfRounds) {
        String result = DuelMenuController.getInstance().canUsersDuel(firstPlayerUsername, secondPlayerUsername, numberOfRounds);
        if (result.equals("duel is valid")) {
            DuelWithUser.getInstance().run(firstPlayerUsername, secondPlayerUsername, numberOfRounds);
            return;
        }
        System.out.println(result);
    }

    private void checkDuelWithAICommand(String command, String username) {
        ArrayList<Matcher> newAIDuelMatchers = new ArrayList<>();
        newAIDuelMatchers.add(newAIDuel1.matcher(command));
        newAIDuelMatchers.add(newAIDuel2.matcher(command));
        newAIDuelMatchers.add(newAIDuel3.matcher(command));
        newAIDuelMatchers.add(newAIDuel4.matcher(command));
        newAIDuelMatchers.add(newAIDuel5.matcher(command));
        newAIDuelMatchers.add(newAIDuel6.matcher(command));
        for (Matcher matcher : newAIDuelMatchers) {
            if (matcher.find()) {
                checkDuelWithAI(username, matcher.group(1));
            }
            return;
        }
        System.out.println("invalid command");
    }

    private void checkDuelWithAI(String username, String numberOfRounds) {
        String result = DuelMenuController.getInstance().canUserDuel(username, numberOfRounds);
        if (result.equals("duel is valid")) {
            DuelWithAIView.getInstance().run(username, numberOfRounds);
            return;
        }
        System.out.println(result);
    }
}
