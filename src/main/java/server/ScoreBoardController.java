package server;

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
        ArrayList<ServerUser> users = ServerUser.getUsers();
        Comparator<ServerUser> comparator = Comparator.comparing(ServerUser::getScore, Comparator.reverseOrder())
                .thenComparing(ServerUser::getNickname);
        Collections.sort(users, comparator);
        StringBuilder scoreBoard = new StringBuilder();
        int rank = 1;
        int userCounter = 1;
        for (int i = 0; i < users.size(); i++) {
            if (i == 0) {
                scoreBoard.append("1- " + users.get(0).getNickname() + ": " + users.get(0).getScore() + "\n");
            } else {
                if (users.get(i).getScore() != users.get(i - 1).getScore()) {
                    rank = userCounter;
                }
                scoreBoard.append(rank + "- " + users.get(i).getNickname() + ": " + users.get(i).getScore() + "\n");
            }
            if (userCounter == 20) {
                break;
            }
            userCounter++;
        }
        return scoreBoard.toString();
    }

}
