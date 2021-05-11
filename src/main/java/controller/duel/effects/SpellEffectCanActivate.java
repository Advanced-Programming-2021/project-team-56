package controller.duel.effects;

import model.Board;

import controller.duel.DuelWithUser;
import model.Card;
import model.MonsterCard;
import model.SpellCard;
import view.duel.EffectView;

import java.util.ArrayList;
import java.util.HashMap;


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

    public boolean yamiCanActivate(Board board) {
        return board.getFieldSpell().getName().equals("Yami") && board.getFieldSpell().getIsFacedUp();
    }

    public boolean forestCanActivate(Board board) {
        return board.getFieldSpell().getName().equals("Forest") && board.getFieldSpell().getIsFacedUp();
    }

    public boolean closedForestCanActivate(Board board) {
        return board.getFieldSpell().getName().equals("closedForest") && board.getFieldSpell().getIsFacedUp();
    }

    public boolean umiirukaCanActive(Board board) {
        return board.getFieldSpell().getName().equals("UMIIRUKA") && board.getFieldSpell().getIsFacedUp();
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

    public boolean canTeraformingActivate() {
        ArrayList<Card> mainDeck = duelWithUser.getMyBoard().getMainDeck();
        for (int i = 0; i < mainDeck.size(); i++) {
            if (mainDeck.get(i) instanceof SpellCard) {
                if (((SpellCard) mainDeck.get(i)).getIcon().equals("Field")) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean potOfGreedCanActivate() {
        ArrayList<Card> mainDeck = duelWithUser.getMyBoard().getMainDeck();
        if (mainDeck.size() >= 2) return true;
        return false;
    }

    public boolean raigekiCanActivate() {
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getEnemyBoard().getMonsterTerritory();
        for (int i = 1; i <= 5; i++) {
            if (monsterTerritory.get(i) != null) return true;
        }
        return false;
    }

    public boolean harpiesFeatherDusterCanActivate() {
        HashMap<Integer, Card> spellAndTrapTerritory =  duelWithUser.getEnemyBoard().getSpellAndTrapTerritory();
        for (int i = 1; i <= 5; i++) {
            if (spellAndTrapTerritory.get(i) != null) return true;
        }
        return false;
    }

    public boolean darkHoleCanActivate() {
        HashMap<Integer, MonsterCard> monsterTerritory1 = duelWithUser.getMyBoard().getMonsterTerritory();
        HashMap<Integer, MonsterCard> monsterTerritory2 = duelWithUser.getEnemyBoard().getMonsterTerritory();
        for (int i = 1; i <= 5; i++) {
            if (monsterTerritory1.get(i) != null || monsterTerritory2.get(i) != null) return true;
        }
        return false;
    }

    public boolean swordsOfRevealingLightCanActivate() {
        ArrayList<Card> playerHand = DuelWithUser.getInstance().getMyBoard().getPlayerHand();
        HashMap<Integer, Card> spellAndTrapTerritory = DuelWithUser.getInstance().getMyBoard().getSpellAndTrapTerritory();
        if (playerHand.contains(DuelWithUser.getInstance().getMyBoard().getSelectedCard())) {
            for (int i = 1; i <= 5; i++) {
                if (spellAndTrapTerritory.get(i) == null) return true;
            }
            return false;
        }
        return true;
    }

}
