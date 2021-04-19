package view;

import model.User;

public class ScoreBoardView {

    private static ScoreBoardView scoreBoardView;

    private ScoreBoardView() {
    }

    public static ScoreBoardView getInstance() {
        if (scoreBoardView == null)
            scoreBoardView = new ScoreBoardView();
        return scoreBoardView;
    }

    public void run(User user) {

    }
}
