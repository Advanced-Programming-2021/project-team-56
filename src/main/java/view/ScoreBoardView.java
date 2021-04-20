package view;

import controller.ScoreBoardController;

import java.util.regex.Matcher;

public class ScoreBoardView {

    private static ScoreBoardView scoreBoardView;

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
            command = LoginMenuView.scan.nextLine();
            command = command.trim();

            if (command.equals("menu show-current")) {
                System.out.println("Scoreboard");
                continue;
            }
            if (command.equals("menu exit")) {
                break;
            }
            String regex = "^menu enter (?:Login|Duel|Deck|Scoreboard|Profile|Shop|Import\\/Export)$";
            Matcher matcher = LoginMenuView.getMatcher(command, regex);
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
