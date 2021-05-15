package controller.duel.effects;

import controller.duel.DuelWithUser;
import model.Card;

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
        HashMap<Integer, Card> spellAndTrapTerritory = duelWithUser.getMyBoard().getSpellAndTrapTerritory();
        for (int i = 1; i < 6; i++) {
            Card spell = spellAndTrapTerritory.get(i);
            if (spell.getName().equals("Mystical space typhoon") || spell.getName().equals("Twin Twisters")) {
                if (!spell.isItInChainLink()) {
                    if (doEnemyHaveSpellOnTheGround()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean doEnemyHaveSpellOnTheGround() {
        HashMap<Integer, Card> spellAndTrapTerritory = duelWithUser.getEnemyBoard().getSpellAndTrapTerritory();
        for (int i = 1; i < 6; i++) {
            if (spellAndTrapTerritory.get(i) != null) {
                return true;
            }
        }
        return false;
    }
}
