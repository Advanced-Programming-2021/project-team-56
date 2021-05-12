package controller.duel.effects;

import controller.duel.DuelWithUser;
import model.Board;
import model.Card;
import model.MonsterCard;

import model.SpellCard;
import view.duel.EffectView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class SpellEffectActivate {

    private static SpellEffectActivate spellEffectActivate;
    private SpellEffectCanActivate spellEffectCanActivate = SpellEffectCanActivate.getInstance();
    private EffectView effectView = EffectView.getInstance();
    private DuelWithUser duelWithUser = DuelWithUser.getInstance();

    private SpellEffectActivate() {

    }

    public static SpellEffectActivate getInstance() {
        if (spellEffectActivate == null)
            spellEffectActivate = new SpellEffectActivate();
        return spellEffectActivate;
    }

    public String spellCaller(String spellName) {
        switch (spellName) {
            case "Advanced Ritual Art":
                if (advancedRitualArt()) {
                    return getRidOfSpell();
                } else {
                    return "preparations of this spell are not done yet";
                }
            case "Terraforming":
                if (terraformingActivate()) {
                    return getRidOfSpell();
                } else {
                    return "preparations of this spell are not done yet";
                }
            case "Change of Heart":
                if (changeOfHeartActivate()) {
                    return getRidOfSpell();
                } else {
                    return "preparations of this spell are not done yet";
                }
            case "Harpie’s Feather Duster":
                if (harpiesFeatherDusterActivate()) {
                    return getRidOfSpell();
                } else {
                    return "preparations of this spell are not done yet";
                }
            case "Raigeki":
                if (raigekiActivate()) {
                    return getRidOfSpell();
                } else {
                    return "preparations of this spell are not done yet";
                }
            case "Pot of Greed":
                if (potOfGreedActivate()) {
                    return getRidOfSpell();
                } else {
                    return "preparations of this spell are not done yet";
                }
            case "Dark Hole":
                if (darkHoleActivate()) {
                    return getRidOfSpell();
                } else {
                    return "preparations of this spell are not done yet";
                }
            case "Swords of Revealing Light":
                swordsOfRevealingLightActivate();
                return "spell activated";
        }
        return "";
    }

    private String getRidOfSpell() {
        ArrayList<Card> playerHand = duelWithUser.getMyBoard().getPlayerHand();
        for (int i = 0; i < playerHand.size(); i++) {
            if (playerHand.get(i) == duelWithUser.getMyBoard().getSelectedCard()) {
                playerHand.remove(i);
                break;
            }
        }
        duelWithUser.getMyBoard().getGraveyard().add(duelWithUser.getEnemyBoard().getSelectedCard());
        duelWithUser.getMyBoard().setSelectedCard(null);
        spellAbsorption();
        return "spell activated";
    }

    private boolean changeOfHeartActivate() {
        if (!spellEffectCanActivate.raigekiCanActivate()) {
            return false;
        }
        HashMap<Integer, MonsterCard> enemyMonsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        HashMap<Integer, MonsterCard> myMonsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        for (int i = 1; i < 6 ; i++) {
            if (myMonsterTerritory.get(i) == null){
                break;
            }
            if (i == 5){
                return false;
            }
        }
        while (true) {
            effectView.output("which monster would you mind to takeover?");
            int address = effectView.getAddress();
            if (address > 5 || address < 1){
                effectView.output("invalid selection");
            }else if (enemyMonsterTerritory.get(address) == null){
                effectView.output("there is no monster on the address");
            }else {
                MonsterCard monsterCard = enemyMonsterTerritory.get(address);
                monsterCard.setItControlledByChangeOfHeart(true);
                for (int i = 1; i < 6 ; i++) {
                    if (myMonsterTerritory.get(i) == null){
                        myMonsterTerritory.put(i, monsterCard);
                    }
                }
                enemyMonsterTerritory.put(address, null);
                break;
            }
        }
        return true;
    }

    private boolean terraformingActivate() {
        if (!spellEffectCanActivate.canTeraformingActivate()) {
            return false;
        }
        ArrayList<Card> mainDeck = duelWithUser.getMyBoard().getMainDeck();
        while (true) {
            effectView.showDeck();
            int address = effectView.getAddress() - 1;
            if (address < 0 || address >= mainDeck.size()) {
                effectView.output("invalid selection");
            } else if (!(mainDeck.get(address) instanceof SpellCard)) {
                effectView.output("selected card isn't a spell card");
            } else if (!((SpellCard) mainDeck.get(address)).getIcon().equals("Field")) {
                effectView.output("selected card isn't a field spell ");
            } else {
                duelWithUser.getMyBoard().getPlayerHand().add(mainDeck.get(address));
                effectView.output("selected card successfully");
                return true;
            }
        }
    }

    public void yamiActivate() {
        Board board1 = duelWithUser.getMyBoard();
        Board board2 = duelWithUser.getEnemyBoard();
        if (spellEffectCanActivate.yamiCanActivate(board1) || spellEffectCanActivate.yamiCanActivate(board2)) {
            int repeat;
            if (spellEffectCanActivate.yamiCanActivate(board1) && spellEffectCanActivate.yamiCanActivate(board2))
                repeat = 2;
            else repeat = 1;
            for (int i = 1; i <= 5; i++) {
                MonsterCard monsterCard;
                for (int j = 0; j <= 1; j++) {
                    if (j == 0) monsterCard = board1.getMonsterTerritory().get(i);
                    else monsterCard = board2.getMonsterTerritory().get(i);
                    if (monsterCard != null) {
                        if (monsterCard.getMonsterType().equals("Fiend") || monsterCard.getMonsterType().equals("Spellcaster")) {
                            monsterCard.increaseFinalAttack(repeat * 200);
                            monsterCard.increaseFinalDefence(repeat * 200);
                        }
                        if (monsterCard.getMonsterType().equals("Fairy")) {
                            monsterCard.decreaseFinalAttack(repeat * 200);
                            monsterCard.decreaseFinalDefence(repeat * 200);
                        }
                    }
                }
            }
        }
    }

    private boolean advancedRitualArt() {
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        for (int i = 1; i < 6; i++) {
            if (monsterTerritory.get(i) == null) {
                break;
            }
            if (i == 5) {
                return false;
            }
        }
        int output = spellEffectCanActivate.canAdvancedRitualArtActivate();
        if (output == 0) {
            effectView.output("there is no way you could ritual summon a monster");
            return false;
        }
        effectView.output("you should ritual summon right now");
        if (output == 1) {
            payingTributeForRitualSummon(8);
        } else {
            payingTributeForRitualSummon(7);
        }
        effectView.output("do you want to put it in attack position or defence position");
        while (true) {
            String input = effectView.input();
            if (input.equals("attack position")) {
                ritualSummon(true, output);
                break;
            } else if (input.equals("defence position")) {
                ritualSummon(true, output);
                break;
            } else {
                input.equals("invalid command");
            }
        }
        Collections.shuffle(duelWithUser.getMyBoard().getMainDeck());
        effectView.output("summoned successfully");
        return true;
    }

    private void ritualSummon(boolean attackPosition, int addrese) {
        ArrayList<Card> playerHand = duelWithUser.getMyBoard().getPlayerHand();
        MonsterCard monsterCard = null;
        if (addrese == 1) {
            for (int i = 0; i < playerHand.size(); i++) {
                if (playerHand.get(i).getName().equals("Crab Turtle")) {
                    monsterCard = (MonsterCard) playerHand.get(i);
                    playerHand.remove(i);
                }
            }
        } else {
            for (int i = 0; i < playerHand.size(); i++) {
                if (playerHand.get(i).getName().equals("Skull Guardian")) {
                    monsterCard = (MonsterCard) playerHand.get(i);
                    playerHand.remove(i);
                }
            }
        }
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        for (int i = 1; i < 6; i++) {
            if (monsterTerritory.get(i) == null) {
                monsterTerritory.put(i, monsterCard);
            }
        }
        monsterCard.setSummonedTurn(duelWithUser.getTurnCounter());
        monsterCard.setInAttackPosition(attackPosition);
        monsterCard.setFacedUp(true);
    }

    private void payingTributeForRitualSummon(int totalLevel) {
        ArrayList<Card> mainDeck = duelWithUser.getMyBoard().getMainDeck();
        while (totalLevel < 8) {
            effectView.showDeck();
            int address = effectView.getAddress() - 1;
            if (address < 0 || address >= mainDeck.size()) {
                effectView.output("invalid selection");
            } else if (!isItValidTribute(address)) {
                effectView.output("selected card can't be tributed");
            } else {
                totalLevel += ((MonsterCard) mainDeck.get(address)).getLevel();
                effectView.output(mainDeck.get(address).getName() + "was removed from deck");
                duelWithUser.getMyBoard().getGraveyard().add(mainDeck.get(address));
                mainDeck.remove(address);
            }
        }
    }

    private boolean isItValidTribute(int address) {
        ArrayList<Card> mainDeck = duelWithUser.getMyBoard().getMainDeck();
        if (mainDeck.get(address) instanceof MonsterCard) {
            return true;
        }
        return false;
    }

    public void forestActivate() {
        Board board1 = duelWithUser.getMyBoard();
        Board board2 = duelWithUser.getEnemyBoard();
        if (spellEffectCanActivate.forestCanActivate(board1) || spellEffectCanActivate.forestCanActivate(board2)) {
            int repeat;
            if (spellEffectCanActivate.forestCanActivate(board1) && spellEffectCanActivate.forestCanActivate(board2))
                repeat = 2;
            else repeat = 1;
            for (int i = 1; i <= 5; i++) {
                MonsterCard monsterCard;
                for (int j = 0; j <= 1; j++) {
                    if (j == 0) monsterCard = board1.getMonsterTerritory().get(i);
                    else monsterCard = board2.getMonsterTerritory().get(i);
                    if (monsterCard != null) {
                        if (monsterCard.getMonsterType().equals("Beast") || monsterCard.getMonsterType().equals("Insect") || monsterCard.getMonsterType().equals("Beast-Warrior")) {
                            monsterCard.increaseFinalDefence(repeat * 200);
                            monsterCard.increaseFinalAttack(repeat * 200);
                        }
                    }
                }
            }
        }
    }

    public void closedForestActivate() {
        Board board1 = duelWithUser.getMyBoard();
        Board board2 = duelWithUser.getEnemyBoard();
        for (int i = 1; i <= 2; i++) {
            Board board;
            if (i == 1) board = board1;
            else board = board2;
            if (spellEffectCanActivate.closedForestCanActivate(board)) {
                int monstersInGraveYardCount = 0;
                for (Card card : board.getGraveyard()) {
                    if (card instanceof MonsterCard) monstersInGraveYardCount++;
                }
                for (int j = 1; j <= 5; j++) {
                    MonsterCard monsterCard = board.getMonsterTerritory().get(j);
                    if (monsterCard != null && monsterCard.getMonsterType().startsWith("Beast")) {
                        monsterCard.increaseFinalAttack(monstersInGraveYardCount * 100);
                    }
                }
            }
        }
    }

    public void umiirukaActivate() {
        Board board1 = duelWithUser.getMyBoard();
        Board board2 = duelWithUser.getEnemyBoard();
        if (spellEffectCanActivate.umiirukaCanActive(board1) || spellEffectCanActivate.umiirukaCanActive(board2)) {
            int repeat;
            if (spellEffectCanActivate.umiirukaCanActive(board1) && spellEffectCanActivate.umiirukaCanActive(board2))
                repeat = 2;
            else repeat = 1;
            for (int i = 1; i <= 5; i++) {
                MonsterCard monsterCard;
                for (int j = 0; j <= 1; j++) {
                    if (j == 0) monsterCard = board1.getMonsterTerritory().get(i);
                    else monsterCard = board2.getMonsterTerritory().get(i);
                    if (monsterCard != null) {
                        if (monsterCard.getMonsterType().equals("Aqua")) {
                            monsterCard.increaseFinalAttack(repeat * 500);
                            monsterCard.decreaseFinalDefence(repeat * 400);
                        }
                    }
                }
            }
        }
    }

    public boolean potOfGreedActivate() {
        if (!spellEffectCanActivate.potOfGreedCanActivate()) {
            return false;
        }
        ArrayList<Card> mainDeck = duelWithUser.getMyBoard().getMainDeck();
        ArrayList<Card> playerHand = duelWithUser.getMyBoard().getPlayerHand();
        ArrayList<Card> graveYard = duelWithUser.getMyBoard().getGraveyard();
        for (int i = 1; i <= 2; i++) {
            if (playerHand.size() < 6) {
                playerHand.add(mainDeck.get(mainDeck.size() - 1));
                mainDeck.remove(mainDeck.size() - 1);
            } else {
                graveYard.add(mainDeck.get(mainDeck.size() - 1));
                mainDeck.remove(mainDeck.size() - 1);
            }
        }
        return true;
    }

    public boolean raigekiActivate() {
        if (!spellEffectCanActivate.raigekiCanActivate()) {
            return false;
        }
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getEnemyBoard().getMonsterTerritory();
        ArrayList<Card> graveYard = duelWithUser.getEnemyBoard().getGraveyard();
        for (int i = 1; i <= 5; i++) {
            if (monsterTerritory.get(i) != null) {
                graveYard.add(monsterTerritory.get(i));
                monsterTerritory.put(i, null);
            }
        }
        return true;
    }

    public boolean harpiesFeatherDusterActivate() {
        if (!spellEffectCanActivate.harpiesFeatherDusterCanActivate()) {
            return false;
        }
        HashMap<Integer, Card> spellAndTrapTerritory = duelWithUser.getEnemyBoard().getSpellAndTrapTerritory();
        ArrayList<Card> graveYard = duelWithUser.getEnemyBoard().getGraveyard();
        for (int i = 1; i <= 5; i++) {
            if (spellAndTrapTerritory.get(i) != null) {
                graveYard.add(spellAndTrapTerritory.get(i));
                spellAndTrapTerritory.put(i, null);
            }
        }
        return true;
    }

    public boolean darkHoleActivate() {
        if (!spellEffectCanActivate.darkHoleCanActivate()) {
            return false;
        }
        HashMap<Integer, MonsterCard> myMonsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        HashMap<Integer, MonsterCard> enemyMonsterTerritory = duelWithUser.getEnemyBoard().getMonsterTerritory();
        ArrayList<Card> myGraveYard = duelWithUser.getMyBoard().getGraveyard();
        ArrayList<Card> enemyGraveYard = duelWithUser.getEnemyBoard().getGraveyard();
        for (int i = 1; i <= 5; i++) {
            if (myMonsterTerritory.get(i) != null) {
                myGraveYard.add(myMonsterTerritory.get(i));
                myMonsterTerritory.put(i, null);
            }
            if (enemyMonsterTerritory.get(i) != null) {
                enemyGraveYard.add(enemyMonsterTerritory.get(i));
                enemyMonsterTerritory.put(i, null);
            }
        }
        return true;
    }

    public void swordsOfRevealingLightActivate() {
        ArrayList<Card> playerHand = duelWithUser.getMyBoard().getPlayerHand();
        HashMap<Integer, Card> spellAndTrapTerritory = duelWithUser.getMyBoard().getSpellAndTrapTerritory();
        SpellCard spellCard = (SpellCard) duelWithUser.getMyBoard().getSelectedCard();
        if (playerHand.contains(spellCard)) {
            playerHand.remove(spellCard);
            for (int i = 1; i <= 5; i++) {
                if (spellAndTrapTerritory.get(i) == null) {
                    spellAndTrapTerritory.put(i, spellCard);
                    spellCard.setFacedUp(true);
                }
            }
        } else {
            spellCard.setFacedUp(true);
        }
        spellCard.setStartEffectTurn(duelWithUser.getTurnCounter());
        HashMap<Integer, MonsterCard> opponentMonsterTerritory = duelWithUser.getEnemyBoard().getMonsterTerritory();
        for (int i = 1; i <= 5; i++) {
            if (opponentMonsterTerritory.get(i) != null) {
                opponentMonsterTerritory.get(i).setFacedUp(true);
            }
        }
    }

    public void spellAbsorption() {
        HashMap<Integer, Card> mySpellAndTrapTerritory = duelWithUser.getMyBoard().getSpellAndTrapTerritory();
        HashMap<Integer, Card> enemySpellAndTrapTerritory = duelWithUser.getEnemyBoard().getSpellAndTrapTerritory();
        int myLp = duelWithUser.getMyBoard().getLP();
        int enemyLp = duelWithUser.getEnemyBoard().getLP();
        for (int i = 1; i <= 5; i++) {
            if (mySpellAndTrapTerritory.get(i) != null) {
                if (mySpellAndTrapTerritory.get(i).equals("Spell Absorption") && mySpellAndTrapTerritory.get(i).getIsFacedUp()) {
                    duelWithUser.getMyBoard().setLP(myLp + 500);
                }
            }
            if (enemySpellAndTrapTerritory.get(i) != null) {
                if (enemySpellAndTrapTerritory.get(i).equals("Spell Absorption") && enemySpellAndTrapTerritory.get(i).getIsFacedUp()) {
                    duelWithUser.getEnemyBoard().setLP(enemyLp + 500);
                }
            }
        }
    }


    public void supplySquadEffect(int player) {
        HashMap<Integer, Card> spellTerritory;
        if (player == 1) {
            spellTerritory = duelWithUser.getMyBoard().getSpellAndTrapTerritory();
        } else {
            spellTerritory = duelWithUser.getEnemyBoard().getSpellAndTrapTerritory();
        }
        for (int i = 1; i < 6; i++) {
            Card card = spellTerritory.get(i);
            if (card.getName().equals("Supply Squad")) {
                if (card.getIsFacedUp()) {
                    card.setStartEffectTurn(duelWithUser.getTurnCounter());
                }
            }
        }
    }

}

