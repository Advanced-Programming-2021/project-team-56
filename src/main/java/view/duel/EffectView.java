package view.duel;

import controller.duel.DuelWithUser;
import model.Card;
import model.MonsterCard;
import view.LoginMenuView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


    public int getAddress() {
        while (true) {
            System.out.println("Give an address for monster location");
            String result = LoginMenuView.scan.nextLine().trim();
            Pattern number = Pattern.compile("[\\d]+");
            Matcher matcher = number.matcher(result);
            if (matcher.find()) {
                return Integer.parseInt(result);
            }else {
                System.out.println("invalid selection");
            }
        }
    }
}
