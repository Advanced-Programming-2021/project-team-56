package controller;

public class ScoreBoardController {

    private static ScoreBoardController scoreBoardController;

    private ScoreBoardController() {
    }

    public static ScoreBoardController getInstance() {
        if (scoreBoardController == null)
            scoreBoardController = new ScoreBoardController();
        return scoreBoardController;
    }

    private String showScoreBoard() {

    }
}
