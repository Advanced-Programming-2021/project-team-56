package controller.duel.effects;

import controller.duel.DuelWithUser;
import model.Card;
import model.MonsterCard;
import view.duel.EffectView;

import java.util.ArrayList;

public class SpellEffectCanActivate {

    private static SpellEffectCanActivate spellEffectCanActivate;
    private DuelWithUser duelWithUser = DuelWithUser.getInstance();
    private EffectView effectView = EffectView.getInstance();

    private SpellEffectCanActivate() {

    }

    public static SpellEffectCanActivate getInstance() {
        if (spellEffectCanActivate == null)
            spellEffectCanActivate = new SpellEffectCanActivate();
        return spellEffectCanActivate;
    }

    public int canAdvancedRitualArtActivate() {
        if (!doseHandIncludeCrabTurtle() && !doseHandIncludeSkullGuardian()) {
            return 0;
        }
        if (!areThereEnoughTributeFromDeck(7)) {
            return 0;
        }
        if (doseHandIncludeCrabTurtle() && doseHandIncludeSkullGuardian() && areThereEnoughTributeFromDeck(8)) {
            while (true) {
                effectView.output("Crab Turtle or Skull Guardian ?");
                String input = effectView.input();
                if (input.equals("Crab Turtle")) {
                    return 1;
                }
                if (input.equals("Skull Guardian")) {
                    return 2;
                }
                effectView.output("invalid selection");
            }
        }
        if (doseHandIncludeSkullGuardian()) {
            return 2;
        }
        if (doseHandIncludeCrabTurtle() && areThereEnoughTributeFromDeck(8)) {
            return 1;
        }
        return 0;
    }

    private boolean areThereEnoughTributeFromDeck(int totalLevel) {
        ArrayList<Card> mainDeck = duelWithUser.getMyBoard().getMainDeck();
        for (int i = 0; i < mainDeck.size(); i++) {
            if (mainDeck.get(i) instanceof MonsterCard) {
                totalLevel -= ((MonsterCard) mainDeck.get(i)).getLevel();
            }
        }
        if (totalLevel <= 0) {
            return true;
        }
        return false;
    }

    private boolean doseHandIncludeCrabTurtle() {
        ArrayList<Card> playerHand = duelWithUser.getMyBoard().getPlayerHand();
        for (int i = 0; i < playerHand.size(); i++) {
            if (playerHand.get(i).getName().equals("Crab Turtle")) {
                return true;
            }
        }
        return false;
    }

    private boolean doseHandIncludeSkullGuardian() {
        ArrayList<Card> playerHand = duelWithUser.getMyBoard().getPlayerHand();
        for (int i = 0; i < playerHand.size(); i++) {
            if (playerHand.get(i).getName().equals("Skull Guardian")) {
                return true;
            }
        }
        return false;
    }

}
