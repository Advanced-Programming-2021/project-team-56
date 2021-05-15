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

                break;
            case "Mystical space typhoon":

                break;
        }
    }

    private void twinTwistersEffect() {
        HashMap<Integer, Card> spellTerritory = duelWithUser.getEnemyBoard().getSpellAndTrapTerritory();
        ArrayList<Card> graveyard = duelWithUser.getEnemyBoard().getGraveyard();
        int counter = 1;
        while (counter <= 2) {
            int address = effectView.getAddress();
            if (address > 5 || address < 1) {
                effectView.output("invalid selection");
                continue;
            }
            Card card = spellTerritory.get(address);
            if (card == null) {
                effectView.output("there is no monster on the address");
                continue;
            }
            graveyard.add(card);
            spellTerritory.put(address, null);
            while (counter < 2) {
                counter++;
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
}
