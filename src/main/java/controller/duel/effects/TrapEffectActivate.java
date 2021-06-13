package controller.duel.effects;

import controller.duel.DuelWithUser;
import model.Card;
import model.MonsterCard;
import model.Output;
import view.duel.EffectView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class TrapEffectActivate {

    private static TrapEffectActivate trapEffectActivate;

    private final DuelWithUser duelWithUser;
    private final EffectView effectView;
    private final SpellEffectActivate spellEffectActivate;

    {
        duelWithUser = DuelWithUser.getInstance();
        effectView = EffectView.getInstance();
        spellEffectActivate = SpellEffectActivate.getInstance();
    }

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
            case "Mind Crush":
                mindCrushActivate();
                break;
            case "Call of the Haunted":
                callOfHuntedActivate();
                break;
            case "Torrential Tribute":
                spellEffectActivate.darkHoleActivate();
                break;
            case "Solemn Warning":
                soleimanWarns();
                break;
            case "Trap Hole":
                trapHoleActivate();
                break;
        }
    }

    private void destroySpell(int counter) {
        while (counter > 0) {
            if(!isThereAnyCardLeft() && duelWithUser.getEnemyBoard() == null){
                break;
            }
            effectView.output("field zone/ spell zone?");
            String input = effectView.input();
            if (input.equals("field zone")) {
                if (killFieldSpell() == 0) {
                    continue;
                }
            } else if (input.equals("spell zone")) {
                if (killSpellZone() == 0) {
                    continue;
                }
            } else {
                effectView.output(Output.InvalidCommand.toString());
                continue;
            }
            counter--;
        }
    }

    private int killSpellZone() {
        if (!isThereAnyCardLeft()) {
            effectView.output("there is no spell left on the spell zone");
            return 0;
        }
        HashMap<Integer, Card> spellTerritory = duelWithUser.getEnemyBoard().getSpellAndTrapTerritory();
        ArrayList<Card> graveyard = duelWithUser.getEnemyBoard().getGraveyard();
        int address = effectView.getAddress();
        if (address > 5 || address < 1) {
            effectView.output(Output.InvalidSelection.toString());
            return 0;
        }
        Card card = spellTerritory.get(address);
        if (card == null) {
            effectView.output("there is no card on the address");
            return 0;
        }
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getEnemyBoard().getMonsterTerritory();
        if (card.getName().equals("Call of the Haunted") && card.getIsFacedUp()) {
            for (int j = 1; j < 6; j++) {
                MonsterCard monster = monsterTerritory.get(j);
                if (monster != null && !monster.isItControlledByChangeOfHeart() && monster.isItHunted()) {
                    graveyard.add(monster);
                    monsterTerritory.put(j, null);
                    break;
                }
            }
        }
        effectView.output("card " + card.getName() + " was destroyed");
        graveyard.add(card);
        spellTerritory.put(address, null);
        return 1;
    }

    private int killFieldSpell() {
        ArrayList<Card> graveyard = duelWithUser.getEnemyBoard().getGraveyard();
        Card spellField = duelWithUser.getEnemyBoard().getFieldSpell();
        if (spellField == null) {
            effectView.output("there is no card on the field zone");
            return 0;
        }
        effectView.output("card " + spellField.getName() + " was destroyed");
        graveyard.add(spellField);
        duelWithUser.getEnemyBoard().setFieldSpell(null);
        return 1;
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
                HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getEnemyBoard().getMonsterTerritory();
                ArrayList<Card> graveyard = duelWithUser.getEnemyBoard().getGraveyard();
                if (card.getName().equals("Call of the Haunted")) {
                    for (int j = 1; j < 6; j++) {
                        MonsterCard monster = monsterTerritory.get(j);
                        if (monster != null && !monster.isItControlledByChangeOfHeart() && monster.isItHunted()) {
                            graveyard.add(monster);
                            monsterTerritory.put(j, null);
                            break;
                        }
                    }
                }
                effectView.output("card " + card.getName() + "was destroyed");
                graveyard.add(card);
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

    private void mindCrushActivate() {
        ArrayList<Card> playerHand = duelWithUser.getMyBoard().getPlayerHand();
        ArrayList<Card> graveyard = duelWithUser.getMyBoard().getGraveyard();
        effectView.output("guess which monster does your opponent have");
        if (!doesPlayerHandIncludeTheGivenGuess()) {
            Random random = new Random();
            int cardNumber = random.nextInt(playerHand.size());
            graveyard.add(playerHand.get(cardNumber));
            playerHand.remove(cardNumber);
        }
    }

    private boolean doesPlayerHandIncludeTheGivenGuess() {
        String input = effectView.input();
        ArrayList<Card> playerHand = duelWithUser.getEnemyBoard().getPlayerHand();
        ArrayList<Card> graveyard = duelWithUser.getEnemyBoard().getGraveyard();
        ArrayList<Card> mainDeck = duelWithUser.getEnemyBoard().getMainDeck();
        for (int i = 0; i < playerHand.size(); i++) {
            if (playerHand.get(i).getName().equals(input)) {
                for (int j = 0; j < playerHand.size(); j++) {
                    if (playerHand.get(j).getName().equals(input)) {
                        graveyard.add(playerHand.get(j));
                        playerHand.remove(j);
                        j--;
                    }
                }
                for (int j = 0; j < mainDeck.size(); j++) {
                    if (mainDeck.get(j).getName().equals(input)) {
                        graveyard.add(mainDeck.get(j));
                        mainDeck.remove(j);
                        j--;
                    }
                }
                return true;
            }
        }
        return false;
    }

    private void callOfHuntedActivate() {
        while (true) {
            ArrayList<Card> graveyard = duelWithUser.getMyBoard().getGraveyard();
            effectView.showGraveyardForCardsEffects(true, false);
            int address = effectView.getAddress();
            if (graveyard.size() < address || address < 1) {
                effectView.output("invalid selection");
            } else if (!(graveyard.get(address - 1) instanceof MonsterCard)) {
                effectView.output("the chosen card is not a monster");
            } else {
                MonsterCard monster = (MonsterCard) graveyard.get(address - 1);
                graveyard.remove(address - 1);
                spellEffectActivate.monsterReborn(monster, false);
                monster.setItHunted(true);
            }
        }
    }

    private void soleimanWarns() {
        duelWithUser.getEnemyBoard().setItEffectedBySoleiman(true);
    }

    private void trapHoleActivate() {
        duelWithUser.getEnemyBoard().setAmIAffectedByTrapHole(true);
    }
}
