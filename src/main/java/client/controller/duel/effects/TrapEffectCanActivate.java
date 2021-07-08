package controller.duel.effects;

import controller.duel.DuelWithUser;
import controller.duel.phases.MainPhase1Controller;
import model.Card;
import model.MonsterCard;

import java.util.ArrayList;
import java.util.HashMap;

public class TrapEffectCanActivate {

    private static TrapEffectCanActivate trapEffectCanActivate;

    private final DuelWithUser duelWithUser;

    {
        duelWithUser = DuelWithUser.getInstance();
    }

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
            case "Negate Attack":
            case "Mirror Force":
            case "Magic Cylinder":
                return canIActivateMagicCylinderOrMirrorForceOrNegateAttack();
            case "Mind Crush":
                return canIActivateMindCrush();
            case "Call of the Haunted":
                return canIActivateCallOfHunted();
            case "Torrential Tribute":
                return canIActivateTorentialTribute();
            case "Solemn Warning":
                return canSoleimanWarn();
            case "Trap Hole":
                return canIActivateTrapHole();
        }
        return false;
    }

    private boolean canSoleimanWarn() {
        return duelWithUser.getMyBoard().getLP() > 2000 && duelWithUser.getEnemyBoard().isItMySummon();
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

    public boolean canIActivateSpaceTyphoonOrTwinTwister() {
        HashMap<Integer, Card> spellAndTrapTerritory = duelWithUser.getEnemyBoard().getSpellAndTrapTerritory();
        for (int i = 1; i < 6; i++) {
            if (spellAndTrapTerritory.get(i) != null) {
                return true;
            }
        }
        return duelWithUser.getEnemyBoard().getFieldSpell() != null;
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
        return fieldSpell != null && fieldSpell.getIsFacedUp();
    }

    private boolean canIActivateMagicCylinderOrMirrorForceOrNegateAttack() {
        if (duelWithUser.getPhaseCounter() != 4) {
            return false;
        }
        if (duelWithUser.getTurnCounter() % 2 == 0) {
            return duelWithUser.getMyBoard().getStartedTurn() == 3;
        } else {
            return duelWithUser.getMyBoard().getStartedTurn() == 2;
        }
    }

    private boolean canIActivateMindCrush() {
        if (duelWithUser.getMyBoard().getPlayerHand().size() == 0) {
            return false;
        }
        return duelWithUser.getEnemyBoard().getPlayerHand().size() != 0;
    }


    private boolean canIActivateCallOfHunted() {
        if (SpellEffectCanActivate.getInstance().isMyMonsterTerritoryFull()) {
            return false;
        }
        ArrayList<Card> graveyard = duelWithUser.getMyBoard().getGraveyard();
        for (Card card : graveyard) {
            if (card instanceof MonsterCard) {
                return true;
            }
        }
        return false;
    }

    private boolean canIActivateTorentialTribute() {
        return MainPhase1Controller.getInstance().isSummoningInProcess();
    }

    private boolean canIActivateTrapHole() {
        return duelWithUser.getEnemyBoard().isMyMonsterInDangerOfTrapHole();
    }
}
