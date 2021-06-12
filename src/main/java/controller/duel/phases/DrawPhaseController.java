package controller.duel.phases;

import controller.duel.DuelWithUser;
import model.Card;
import model.MonsterCard;

import java.util.ArrayList;

public class DrawPhaseController {

    private static DrawPhaseController drawPhase;
    private final DuelWithUser duelWithUser;

    {
        duelWithUser = DuelWithUser.getInstance();
    }

    private DrawPhaseController() {
    }

    public static DrawPhaseController getInstance() {
        if (drawPhase == null)
            drawPhase = new DrawPhaseController();
        return drawPhase;
    }

    public String run() {
        if (!duelWithUser.getMyBoard().isItEffectedByTimeSeal()) {
            ArrayList<Card> mainDeck = duelWithUser.getMyBoard().getMainDeck();
            ArrayList<Card> playerHand = duelWithUser.getMyBoard().getPlayerHand();
            if (mainDeck.size() == 0) {
                return "No cards is in your deck";
            }
            Card newCard = mainDeck.get(mainDeck.size() - 1);
            if (newCard.getName().equals("Scanner")){
                ((MonsterCard)newCard).setItScanner(true);
            }
            playerHand.add(newCard);
            mainDeck.remove(mainDeck.size() - 1);
            return "new card added to the hand : " + playerHand.get(playerHand.size() - 1).getName();
        }else{
            duelWithUser.getMyBoard().setItEffectedByTimeSeal(false);
            return "this turn you couldn't draw a card";
        }
    }

    public String forceDraw(String cardName) {
        ArrayList<Card> mainDeck = duelWithUser.getMyBoard().getMainDeck();
        for (Card card : mainDeck) {
            if (card.getName().equals(cardName)) {
                if (card.getName().equals("Scanner")){
                    ((MonsterCard) card).setItScanner(true);
                }
                duelWithUser.getMyBoard().getPlayerHand().add(card);
                mainDeck.remove(card);
                return "card with name " + cardName + " got added to your hand";
            }
        }
        return "card with name " + cardName + " does not exist in your deck cards";
    }
}
