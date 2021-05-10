package controller.duel.effects;

import controller.duel.DuelWithUser;
import model.Board;
import model.Card;
import model.MonsterCard;

import view.duel.EffectView;

import java.util.ArrayList;
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

    public String spellCaller(String spellName){
        switch (spellName){
            case "Advanced Ritual Art":
                if (advancedRitualArt()){
                    return "spell activated";
                }else {
                    return "preparations of this spell are not done yet";
                }
        }
        return "";
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

    public boolean advancedRitualArt() {
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
        effectView.output("summoned successfully");
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
}

