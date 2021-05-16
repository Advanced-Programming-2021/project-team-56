package controller.duel.effects;

import controller.duel.DuelWithUser;
import model.Card;

import java.util.ArrayList;
import java.util.HashMap;

public class TrapEffectCanActivate {

    private static TrapEffectCanActivate trapEffectCanActivate;
    private DuelWithUser duelWithUser = DuelWithUser.getInstance();

    private TrapEffectCanActivate() {

    }

    public static TrapEffectCanActivate getInstance() {
        if (trapEffectCanActivate == null)
            trapEffectCanActivate = new TrapEffectCanActivate();
        return trapEffectCanActivate;
    }

    public boolean checkSpellAndTrapPossibility(String name) {
        switch (name) {
            case "Twin Twisters":
            case "Mystical space typhoon":
                return canIActivateSpaceTyphoonOrTwinTwister();
            case "Ring of Defense":
                return canIActivateRingOfDefence();
            case "Time Seal":
                return canIActivateTimeSeal();
            case "Magic Jammer":
                return canIActivateMagicJammer();
        }
        return false;
    }

    private boolean canIActivateRingOfDefence() {
        HashMap<Integer, Card> trapTerritory = duelWithUser.getEnemyBoard().getSpellAndTrapTerritory();
        for (int i = 1; i < 6; i++) {
            Card trap = trapTerritory.get(i);
            if (trap != null && trap.isItInChainLink() && trap.getName().equals("Magic Cylinder")) {
                return true;
            }
        }
        return false;
    }

    private boolean canIActivateSpaceTyphoonOrTwinTwister() {
        HashMap<Integer, Card> spellAndTrapTerritory = duelWithUser.getEnemyBoard().getSpellAndTrapTerritory();
        for (int i = 1; i < 6; i++) {
            if (spellAndTrapTerritory.get(i) != null) {
                return true;
            }
        }
        if (duelWithUser.getEnemyBoard().getFieldSpell() != null) {
            return true;
        }
        return false;
    }

    private boolean canIActivateTimeSeal() {
        return !duelWithUser.getEnemyBoard().isItEffectedByTimeSeal();
    }

    private boolean canIActivateMagicJammer() {
        ArrayList<Card> playerHand = duelWithUser.getMyBoard().getPlayerHand();
        if (playerHand.size() == 0) {
            return false;
        }
        HashMap<Integer, Card> spellAndTrapTerritory = duelWithUser.getEnemyBoard().getSpellAndTrapTerritory();
        for (int i = 1; i < 6; i++) {
            Card card = spellAndTrapTerritory.get(i);
            if (card != null && card.getIsFacedUp()) {
                return true;
            }
        }
        Card fieldSpell = duelWithUser.getEnemyBoard().getFieldSpell();
        if (fieldSpell != null && fieldSpell.getIsFacedUp()) {
            return true;
        }
        return false;
    }
}
