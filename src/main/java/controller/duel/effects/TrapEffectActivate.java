package controller.duel.effects;

import controller.duel.DuelWithUser;
import model.Card;
import model.MonsterCard;
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
            case "Time Seal":
                timeSealActivate();
                break;
            case "Magic Jammer":
                magicJammerActivate();
                break;
            case "Magic Cylinder":
                magicCylinderActivate();
                break;
            case "Mirror Force":
                mirrorForceActivate();
                break;
            case "Negate Attack":
                negateAttackActivate();
                break;
        }
    }

    private void destroySpell(int counter) {
        HashMap<Integer, Card> spellTerritory = duelWithUser.getEnemyBoard().getSpellAndTrapTerritory();
        ArrayList<Card> graveyard = duelWithUser.getEnemyBoard().getGraveyard();
        while (counter > 0) {
            effectView.output("field zone/ spell zone?");
            String input = effectView.input();
            if (input.equals("field zone")) {
                Card spellField = duelWithUser.getEnemyBoard().getFieldSpell();
                if (spellField == null) {
                    effectView.output("there is no card on the field zone");
                    continue;
                }
                effectView.output("card " + spellField.getName() + "was destroyed");
                graveyard.add(spellField);
                duelWithUser.getEnemyBoard().setFieldSpell(null);
            } else if (input.equals("spell zone")) {
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
            }
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

    private void timeSealActivate() {
        duelWithUser.getEnemyBoard().setItEffectedByTimeSeal(true);
    }

    private void magicJammerActivate() {
        payingTributeForMagicJammer();
        HashMap<Integer, Card> spellAndTrapTerritory = duelWithUser.getEnemyBoard().getSpellAndTrapTerritory();
        while (true) {
            effectView.output("field zone/ spell zone?");
            String input = effectView.input();
            if (input.equals("spell zone")) {
                Card fieldSpell = duelWithUser.getEnemyBoard().getFieldSpell();
                if (fieldSpell == null) {
                    effectView.output("there is no card on the field zone");
                    continue;
                }
                if (!fieldSpell.getIsFacedUp()) {
                    effectView.output("this card is not activated yet");
                    continue;
                }
                effectView.output("card " + fieldSpell.getName() + "was destroyed");
                duelWithUser.getEnemyBoard().getGraveyard().add(fieldSpell);
                duelWithUser.getEnemyBoard().setFieldSpell(null);
            } else if (input.equals("field zone")) {
                int address = effectView.getAddress();
                if (address > 5 || address < 1) {
                    effectView.output("invalid selection");
                    continue;
                }
                Card card = spellAndTrapTerritory.get(address);
                if (card == null) {
                    effectView.output("there is no card on the address");
                    continue;
                }
                if (!card.getIsFacedUp()) {
                    effectView.output("this card is not activated yet");
                    continue;
                }
                effectView.output("card " + card.getName() + "was destroyed");
                duelWithUser.getEnemyBoard().getGraveyard().add(card);
                spellAndTrapTerritory.put(address, null);
            } else {
                effectView.output("invalid command");
            }
        }
    }

    private void payingTributeForMagicJammer() {
        ArrayList<Card> playerHand = duelWithUser.getMyBoard().getPlayerHand();
        while (true) {
            int address = effectView.getAddress();
            if (address > playerHand.size() || address < 1) {
                effectView.output("invalid selection");
            } else if (playerHand.get(address - 1) == null) {
                effectView.output("there is no card on this address");
            } else {
                duelWithUser.getMyBoard().getGraveyard().add(playerHand.get(address - 1));
                playerHand.remove(address - 1);
                break;
            }
        }
    }

    private void magicCylinderActivate() {
        duelWithUser.getEnemyBoard().setItEffectedByMagicCylinder(true);
    }

    private void negateAttackActivate() {
        duelWithUser.getEnemyBoard().setItAttackNegated(true);
    }

    private void mirrorForceActivate() {
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getEnemyBoard().getMonsterTerritory();
        ArrayList<Card> graveyard = duelWithUser.getEnemyBoard().getGraveyard();
        for (int i = 1; i < 6; i++) {
            MonsterCard monster = monsterTerritory.get(i);
            if (monster != null && monster.getIsFacedUp()) {
                graveyard.add(monster);
                monsterTerritory.put(i, null);
                duelWithUser.afterDeathEffect(2, monster);
            }
        }
        duelWithUser.getEnemyBoard().setItEffectedByMirrorFace(true);
    }
}
