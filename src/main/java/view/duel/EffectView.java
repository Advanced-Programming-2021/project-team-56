package view.duel;

import controller.duel.DuelWithUser;
import model.Card;
import model.MonsterCard;
import view.LoginMenuView;

import java.util.ArrayList;

public class EffectView {
    private static EffectView effectView;
    private DuelWithUser duelWithUser = DuelWithUser.getInstance();

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

    public String input() {
        return LoginMenuView.scan.nextLine().trim();
    }

    public void showDeck() {
        ArrayList<Card> mainDeck = duelWithUser.getMyBoard().getMainDeck();
        for (int i = 0, j = 1; i < mainDeck.size(); i++, j++) {
            System.out.println(j + ": " + mainDeck.get(i).getName());
        }
    }
}
