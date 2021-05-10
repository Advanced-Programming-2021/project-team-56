package controller.duel.effects;

import controller.duel.DuelWithUser;
import model.Board;
import model.Card;
import model.MonsterCard;

public class SpellEffectActivate {

    private static SpellEffectActivate spellEffectActivate;

    private SpellEffectActivate() {

    }

    public static SpellEffectActivate getInstance() {
        if (spellEffectActivate == null)
            spellEffectActivate = new SpellEffectActivate();
        return spellEffectActivate;
    }

    public void yamiActivate() {
        Board board1 = DuelWithUser.getInstance().getMyBoard();
        Board board2 = DuelWithUser.getInstance().getEnemyBoard();
        if (SpellEffectCanActivate.getInstance().yamiCanActivate(board1) || SpellEffectCanActivate.getInstance().yamiCanActivate(board2)) {
            int repeat;
            if (SpellEffectCanActivate.getInstance().yamiCanActivate(board1) && SpellEffectCanActivate.getInstance().yamiCanActivate(board2))
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

    public void forestActivate() {
        Board board1 = DuelWithUser.getInstance().getMyBoard();
        Board board2 = DuelWithUser.getInstance().getEnemyBoard();
        if (SpellEffectCanActivate.getInstance().forestCanActivate(board1) || SpellEffectCanActivate.getInstance().forestCanActivate(board2)) {
            int repeat;
            if (SpellEffectCanActivate.getInstance().forestCanActivate(board1) && SpellEffectCanActivate.getInstance().forestCanActivate(board2))
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
        Board board1 = DuelWithUser.getInstance().getMyBoard();
        Board board2 = DuelWithUser.getInstance().getEnemyBoard();
        for (int i = 1; i <= 2; i++) {
            Board board;
            if (i == 1) board = board1;
            else board = board2;
            if (SpellEffectCanActivate.getInstance().closedForestCanActivate(board)) {
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
        Board board1 = DuelWithUser.getInstance().getMyBoard();
        Board board2 = DuelWithUser.getInstance().getEnemyBoard();
        if (SpellEffectCanActivate.getInstance().umiirukaCanActive(board1) || SpellEffectCanActivate.getInstance().umiirukaCanActive(board2)) {
            int repeat;
            if (SpellEffectCanActivate.getInstance().umiirukaCanActive(board1) && SpellEffectCanActivate.getInstance().umiirukaCanActive(board2))
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
