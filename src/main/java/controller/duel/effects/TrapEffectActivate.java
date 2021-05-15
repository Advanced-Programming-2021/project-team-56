package controller.duel.effects;

import controller.duel.DuelWithUser;
import model.Card;
import view.duel.EffectView;

import java.util.ArrayList;
import java.util.HashMap;

public class TrapEffectActivate {

    private static TrapEffectActivate trapEffectActivate;
    private EffectView effectView = EffectView.getInstance();
    private DuelWithUser duelWithUser = DuelWithUser.getInstance();

    private TrapEffectActivate() {

    }

    public static TrapEffectActivate getInstance() {
        if (trapEffectActivate == null)
            trapEffectActivate = new TrapEffectActivate();
        return trapEffectActivate;
    }

    public void trapAndQuickSpellCaller(String name) {
        switch (name) {
            case "Twin Twisters":
                destroySpell(2);
                break;
            case "Mystical space typhoon":
                destroySpell(1);
                break;
            case "Ring of Defense":
                ringOfDefenceActivate();
                break;
        }
    }

    private void destroySpell(int counter) {
        HashMap<Integer, Card> spellTerritory = duelWithUser.getEnemyBoard().getSpellAndTrapTerritory();
        ArrayList<Card> graveyard = duelWithUser.getEnemyBoard().getGraveyard();
        while (counter > 0) {
            int address = effectView.getAddress();
            if (address > 5 || address < 1) {
                effectView.output("invalid selection");
                continue;
            }
            Card card = spellTerritory.get(address);
            if (card == null) {
                effectView.output("there is no card on the address");
                continue;
            }
            effectView.output("card " + card.getName() + "was destroyed");
            graveyard.add(card);
            spellTerritory.put(address, null);
            counter--;
            if (!isThereAnyCardLeft()) {
                break;
            }
        }
    }

    private boolean isThereAnyCardLeft() {
        HashMap<Integer, Card> spellTerritory = duelWithUser.getEnemyBoard().getSpellAndTrapTerritory();
        for (int i = 1; i < 6; i++) {
            if (spellTerritory.get(i) != null) {
                return true;
            }
        }
        return false;
    }

    private void ringOfDefenceActivate() {
        HashMap<Integer, Card> trapTerritory = duelWithUser.getEnemyBoard().getSpellAndTrapTerritory();
        ArrayList<Card> graveyard = duelWithUser.getEnemyBoard().getGraveyard();
        for (int i = 1; i < 6; i++) {
            Card trap = trapTerritory.get(i);
            if (trap != null && trap.isItInChainLink() && trap.getName().equals("Magic Cylinder")) {
                graveyard.add(trap);
                trapTerritory.put(i, null);
                effectView.output(trap.getName() + "was disabled");
                break;
            }
        }
    }
}
