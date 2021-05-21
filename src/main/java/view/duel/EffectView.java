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
            System.out.println("Give an address");
            String result = LoginMenuView.scan.nextLine().trim();
            Pattern number = Pattern.compile("[\\d]+");
            Matcher matcher = number.matcher(result);
            if (matcher.find()) {
                return Integer.parseInt(result);
            } else {
                System.out.println("invalid selection");
            }
        }
    }

    public void showGraveyardForMonsterRebornAndScannerAndCallOfHunted(boolean player1, boolean player2) {
        ArrayList<Card> myGraveyard = duelWithUser.getMyBoard().getGraveyard();
        ArrayList<Card> enemyGraveyard = duelWithUser.getEnemyBoard().getGraveyard();
        int j = 1;
        if (player1) {
            for (int i = 0; i < myGraveyard.size(); i++) {
                if (myGraveyard.get(i) instanceof MonsterCard) {
                    j++;
                    System.out.println(j + ": " + myGraveyard.get(i).getName());
                }
            }
        }
        if (player2) {
            for (int i = 0; i < enemyGraveyard.size(); i++) {
                if (enemyGraveyard.get(i) instanceof MonsterCard) {
                    j++;
                    System.out.println(j + ": " + enemyGraveyard.get(i).getName());
                }
            }
        }
    }
}
