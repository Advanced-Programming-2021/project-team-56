package controller.duel;

import model.Board;
import model.User;
import view.duel.phase.*;

public class DuelWithUserController {
    private int phaseCounter = 0;

    private static DuelWithUserController duelWithUserController;
    private Board[] boards = new Board[2];

    private DuelWithUserController() {
    }

    public static DuelWithUserController getInstance() {
        if (duelWithUserController == null)
            duelWithUserController = new DuelWithUserController();
        return duelWithUserController;
    }

    public void phaseCaller() {
        switch (phaseCounter % 6 + 1) {
            case 1:
                DrawPhaseView.getInstance().run();
                break;
            case 2:
                StandByPhaseView.getInstance().run();
                break;
            case 3:
                MainPhase1View.getInstance().run();
                break;
            case 4:
                BattlePhaseView.getInstance().run();
                break;
            case 5:
                MainPhase2View.getInstance().run();
                break;
            case 6:
                EndPhaseView.getInstance().run();
                break;
        }
        phaseCounter++;
    }

    public void setUpGame(String firstPlayer, String secondPlayer) {
        boards[0] = new Board(User.getUserByUsername(firstPlayer).getActivatedDeck());
        boards[1] = new Board(User.getUserByUsername(secondPlayer).getActivatedDeck());
    }
}
