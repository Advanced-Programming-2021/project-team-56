package controller;

import model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ScoreBoardController {

    private static ScoreBoardController scoreBoardController;

    private ScoreBoardController() {
    }

    public static ScoreBoardController getInstance() {
        if (scoreBoardController == null)
            scoreBoardController = new ScoreBoardController();
        return scoreBoardController;
    }

    public String showScoreBoard() {
        ArrayList<User> users = User.getUsers();
        Comparator<User> comparator = Comparator.comparing(User::getScore, Comparator.reverseOrder())
                .thenComparing(User::getNickname);
        Collections.sort(users, comparator);
        StringBuilder scoreBoard = new StringBuilder();
        int rank = 1;
        for (int i = 0; i < users.size(); i++) {
            if (i == 0) {
                scoreBoard.append("1- " + users.get(0).getNickname() + ": " + users.get(0).getScore() + "\n");
            } else {
                if (users.get(i).getScore() != users.get(i - 1).getScore()) {
                    rank++;
                }
                scoreBoard.append(rank + "- " + users.get(i).getNickname() + ": " + users.get(i).getScore() + "\n");
            }
        }
        return scoreBoard.toString();
    }
}
