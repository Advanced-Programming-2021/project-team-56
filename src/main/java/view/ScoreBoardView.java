package view;

import controller.ScoreBoardController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScoreBoardView {

    private static ScoreBoardView scoreBoardView;
    static Pattern menuEnter = Pattern.compile("^menu enter (?:Login|Duel|Deck|Scoreboard|Profile|Shop|Import\\/Export)$");

    private ScoreBoardView() {
    }

    public static ScoreBoardView getInstance() {
        if (scoreBoardView == null)
            scoreBoardView = new ScoreBoardView();
        return scoreBoardView;
    }

    public void run() {
        String command;
        while (true) {
            command = LoginMenuView.scan.nextLine().trim();
            if (command.equals("menu show-current")) {
                System.out.println("Scoreboard");
                continue;
            }
            if (command.equals("menu exit")) {
                break;
            }
            Matcher matcher = menuEnter.matcher(command);
            if (matcher.find()) {
                System.out.println("menu navigation is not possible");
                continue;
            }
            if (command.equals("scoreboard show")) {
                showScoreBoard();
                continue;
            }
            System.out.println("invalid command");
        }
    }

    private void showScoreBoard() {
        String scoreBoard = ScoreBoardController.getInstance().showScoreBoard();
        System.out.print(scoreBoard);
    }
}
