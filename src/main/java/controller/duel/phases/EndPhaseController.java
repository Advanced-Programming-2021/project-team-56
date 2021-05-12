package controller.duel.phases;

import controller.duel.DuelWithUser;
import model.Card;

import java.util.ArrayList;

public class EndPhaseController {
    private static EndPhaseController endPhaseController;
    private DuelWithUser duelWithUser = DuelWithUser.getInstance();

    private EndPhaseController() {

    }

    public static EndPhaseController getInstance() {
        if (endPhaseController == null) {
            endPhaseController = new EndPhaseController();
        }
        return endPhaseController;
    }

    public void run() {
        minimizeHand();
    }


    private void minimizeHand() {
        ArrayList<Card> playerHand = duelWithUser.getMyBoard().getPlayerHand();
        ArrayList<Card> graveyard = duelWithUser.getMyBoard().getGraveyard();
        while (playerHand.size() > 6) {
            graveyard.add(playerHand.get(playerHand.size() - 1));
            playerHand.remove(playerHand.size() - 1);
        }
    }
}
