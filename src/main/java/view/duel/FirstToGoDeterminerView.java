package view.duel;

import controller.duel.FirstToGoDeterminerController;
import model.User;
import view.LoginMenuView;

import java.util.Locale;
import java.util.regex.Pattern;

public class FirstToGoDeterminerView {

    private static FirstToGoDeterminerView firstToGoDeterminerView;
    static Pattern rockPaperScissors = Pattern.compile("^(?:rock|paper|scissors)$");

    private FirstToGoDeterminerView() {

    }

    public static FirstToGoDeterminerView getInstance() {
        if (firstToGoDeterminerView == null)
            firstToGoDeterminerView = new FirstToGoDeterminerView();
        return firstToGoDeterminerView;
    }

    public String determineFirstPlayerToGo(String firstPlayerUsername, String secondPlayerUsername, int lastRoundResult) {
        String deciderUsername;
        if (lastRoundResult == 0) {
            deciderUsername = rockPaperScissors(firstPlayerUsername, secondPlayerUsername);
        } else if (lastRoundResult == 1) {
            deciderUsername = firstPlayerUsername;
        } else {
            deciderUsername = secondPlayerUsername;
        }
        String WinnerNickname = User.getUserByUsername(deciderUsername).getNickname();
        System.out.println(WinnerNickname + ", please choose the first player to go: "
                + firstPlayerUsername + " or " + secondPlayerUsername);
        while (true) {
            String firstPlayerToGoUsername = LoginMenuView.scan.nextLine().trim();
            if (firstPlayerToGoUsername.equals(firstPlayerUsername)) {
                return firstPlayerUsername;
            }
            if (firstPlayerToGoUsername.equals(secondPlayerUsername)) {
                return secondPlayerUsername;
            }
            System.out.println("wrong username, try again");
        }
    }

    private String rockPaperScissors(String firstPlayerUsername, String secondPlayerUsername) {
        String firstPlayerNickname = User.getUserByUsername(firstPlayerUsername).getNickname();
        String secondPlayerNickname = User.getUserByUsername(secondPlayerUsername).getNickname();
        while (true) {
            System.out.println(firstPlayerNickname + ", please choose between Rock, Paper or Scissors:");
            outer:
            while (true) {
                String firstPlayerCommand = LoginMenuView.scan.nextLine().toLowerCase(Locale.ROOT).trim();
                if (rockPaperScissors.matcher(firstPlayerCommand).find()) {
                    System.out.println(secondPlayerNickname + ", please choose between Rock, Paper or Scissors:");
                    while (true) {
                        String secondPlayerCommand = LoginMenuView.scan.nextLine().toLowerCase(Locale.ROOT).trim();
                        if (rockPaperScissors.matcher(secondPlayerCommand).find()) {
                            if (firstPlayerCommand.equals(secondPlayerCommand)) {
                                break outer;
                            }
                            String winnerCommand = FirstToGoDeterminerController
                                    .getInstance().getWinnerCommand(firstPlayerCommand, secondPlayerCommand);
                            if (winnerCommand.equals(firstPlayerCommand)) {
                                return firstPlayerUsername;
                            }
                            return secondPlayerUsername;
                        } else System.out.println("invalid command");
                    }
                } else System.out.println("invalid command");
            }
            System.out.println("Draw, please try again");
        }
    }

}
