package controller.duel.phases;

import controller.duel.DuelWithUser;
import model.Card;

import java.util.ArrayList;

public class DrawPhaseController {
    private static DrawPhaseController drawPhase;
    private final DuelWithUser duelWithUser = DuelWithUser.getInstance();

    private DrawPhaseController() {
    }

    public static DrawPhaseController getInstance() {
        if (drawPhase == null) {
            drawPhase = new DrawPhaseController();
        }
        return drawPhase;
    }

    public String run() {
        ArrayList<Card> mainDeck = duelWithUser.getMyBoard().getMainDeck();
        ArrayList<Card> playerHand = duelWithUser.getMyBoard().getPlayerHand();
        if (mainDeck.size() == 0) {
            return "No cards is in your deck";
        }
        playerHand.add(mainDeck.get(mainDeck.size() - 1));
        mainDeck.remove(mainDeck.size() - 1);
        return "new card added to the hand : " + playerHand.get(playerHand.size() - 1).getName();
    }
}
