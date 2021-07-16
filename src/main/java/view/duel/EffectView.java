package view.duel;

import controller.duel.DuelWithUser;
import server.model.Card;

import java.util.ArrayList;

public class EffectView {

    private static EffectView effectView;

    private final DuelWithUser duelWithUser;

    {
        duelWithUser = DuelWithUser.getInstance();
    }

    private EffectView() {

    }

    public static EffectView getInstance() {
        if (effectView == null) {
            effectView = new EffectView();
        }
        return effectView;
    }

    public void output(String string) {
        System.out.println(string);
    }

    public void output1(String string) {
        System.out.print(string);
    }

    public String input() {
        return null;
    }

    public void showDeck() {
        ArrayList<Card> mainDeck = duelWithUser.getMyBoard().getMainDeck();
        for (int i = 0, j = 1; i < mainDeck.size(); i++, j++) {
            System.out.println(j + ": " + mainDeck.get(i).getName());
        }
    }


    public int getAddress() {
        return 1;
    }

    public void showGraveyardForCardsEffects(boolean player1, boolean player2) {
        ArrayList<Card> myGraveyard = duelWithUser.getMyBoard().getGraveyard();
        ArrayList<Card> enemyGraveyard = duelWithUser.getEnemyBoard().getGraveyard();
        StringBuilder stringBuilder = new StringBuilder();
        int j = 0;
        if (player1) {
            for (Card card : myGraveyard) {
                j++;
                stringBuilder.append(j + ": " + card.getName() + "\n");
            }
        }
        if (player2) {
            for (Card card : enemyGraveyard) {
                j++;
                stringBuilder.append(j + ": " + card.getName() + "\n");
            }
        }
        System.out.print(stringBuilder);
    }
}
