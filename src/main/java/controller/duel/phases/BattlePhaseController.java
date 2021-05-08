package controller.duel.phases;

import controller.duel.DuelWithUser;
import model.Board;
import model.Card;
import model.MonsterCard;

import java.util.ArrayList;
import java.util.HashMap;


public class BattlePhaseController {
    private static BattlePhaseController battlePhase;
    public MonsterCard monsterCard;
    public MonsterCard enemyMonsterCard;

    private BattlePhaseController() {
    }

    public static BattlePhaseController getInstance() {
        if (battlePhase == null) {
            battlePhase = new BattlePhaseController();
        }
        return battlePhase;
    }

    public String attackCard(int address) {
        DuelWithUser duelWithUser = DuelWithUser.getInstance();
        Card card = duelWithUser.getMyBoard().getSelectedCard();
        MainPhase1Controller mainPhase1Controller = MainPhase1Controller.getInstance();
        if (card == null) {
            return "no card is selected yet";
        }
        if (!mainPhase1Controller.isCardInMyMonsterTerritory()) {
            return "you can’t attack with this card";
        }
        monsterCard = (MonsterCard) card;
        if (monsterCard.getLastTimeAttackedTurn() == duelWithUser.getTurnCounter()) {
            return "this card already attacked";
        }
        monsterCard.setLastTimeAttackedTurn(duelWithUser.getTurnCounter());
        if (monsterCard.getName().equals("The Calculator")) {
            theCalculatorEffect(monsterCard);
        }
        enemyMonsterCard = duelWithUser.getEnemyBoard().getMonsterTerritory().get(address);
        if (enemyMonsterCard == null) {
            return "there is no card to attack here";
        }
        if (enemyMonsterCard.getName().equals("Suijin")) {
            suijinEffect();
        }
        if (enemyMonsterCard.getName().equals("The Calculator")) {
            theCalculatorEffect(enemyMonsterCard);
        }
        if (enemyMonsterCard.getName().equals("Texchanger")) {
            if (enemyMonsterCard.getStartEffectTurn() != duelWithUser.getTurnCounter()) {
                enemyMonsterCard.setStartEffectTurn(duelWithUser.getTurnCounter());
                return "your attack was blocked";
            }
        }
        int myMonsterAttack = monsterCard.getFinalAttack();
        if (enemyMonsterCard.getName().equals("Exploder Dragon")) {
            return exploderDragonEffectUnderAttack(address);
        }
        if (enemyMonsterCard.getIsInAttackPosition()) {
            int enemyMonsterAttack = enemyMonsterCard.getFinalAttack();
            if (enemyMonsterAttack == myMonsterAttack) {
                if (monsterCard.getName().equals("Marshmallon") && enemyMonsterCard.getName().equals("Marshmallon")) {
                    return "no card is destroyed";
                }
                if (monsterCard.getName().equals("Marshmallon")) {
                    return "your opponent’s monster is destroyed and no one receives damage";
                }
                if (enemyMonsterCard.getName().equals("Marshmallon")) {
                    return "your monster card is destroyed and no one receives damage";
                }
                destroyMyCard(monsterCard);
                destroyEnemyCard(address);
                return "both you and your opponent monster cards are destroyed and no one receives damage";
            } else if (myMonsterAttack > enemyMonsterAttack) {
                int damage = myMonsterAttack - enemyMonsterAttack;
                int enemyLife = duelWithUser.getEnemyBoard().getLP();
                duelWithUser.getEnemyBoard().setLP(enemyLife - damage);
                if (enemyMonsterCard.getName().equals("Marshmallon")) {
                    return "no card is destroyed and your opponent receives " + damage + " battle damage";
                }
                destroyEnemyCard(address);
                if (enemyMonsterCard.getName().equals("Yomi Ship")) {
                    destroyMyCard(monsterCard);
                    return "both you and your opponent monster cards are destroyed and you receive" + damage + " damage";
                }
                return "your opponent’s monster is destroyed and your opponent receives " + damage + " battle damage";
            } else {
                if (!monsterCard.getName().equals("Exploder Dragon")) {
                    int damage = enemyMonsterAttack - myMonsterAttack;
                    int myLife = duelWithUser.getMyBoard().getLP();
                    duelWithUser.getMyBoard().setLP(myLife - damage);
                    if (monsterCard.getName().equals("Marshmallon")) {
                        return "no card is destroyed and you received " + damage + " battle damage";
                    }
                    destroyMyCard(monsterCard);
                    return "Your monster card is destroyed and you received " + damage + " battle damage";
                } else {
                    destroyMyCard(monsterCard);
                    destroyEnemyCard(address);
                    return "both you and your opponent monster cards are destroyed and no one receives damage";
                }
            }
        } else {
            int enemyMonsterDefence = enemyMonsterCard.getFinalDefence();
            boolean shouldFlipSummonOccur = !enemyMonsterCard.getIsFacedUp();
            enemyMonsterCard.setFacedUp(true);
            if (myMonsterAttack > enemyMonsterDefence) {
                if (enemyMonsterCard.getName().equals("Marshmallon")) {
                    if (shouldFlipSummonOccur) {
                        int myLife = duelWithUser.getMyBoard().getLP();
                        duelWithUser.getMyBoard().setLP(myLife - 1000);
                        return "opponent’s monster was Marshmallon and no card is destroyed and you receive 1000 damage";
                    }
                    return "no card is destroyed";
                }
                destroyEnemyCard(address);
                if (enemyMonsterCard.getName().equals("Man-Eater Bug") && shouldFlipSummonOccur) {
                    duelWithUser.manEaterBugEffect(true);
                } else if (enemyMonsterCard.getName().equals("Yomi Ship")) {
                    destroyMyCard(monsterCard);
                    return "both you and your opponent monster cards are destroyed and no one receives damage";
                }
                return "the defense position monster is destroyed";
            } else if (myMonsterAttack == enemyMonsterDefence) {
                if (enemyMonsterCard.getName().equals("Man-Eater Bug") && shouldFlipSummonOccur) {
                    duelWithUser.manEaterBugEffect(true);
                }
                return "no card is destroyed";
            } else {
                if (monsterCard.getName().equals("Exploder Dragon")) {
                    if (enemyMonsterCard.getName().equals("Man-Eater Bug") && shouldFlipSummonOccur) {
                        duelWithUser.manEaterBugEffect(true);
                    }
                    return "no one receives damage";
                }
                int damage = enemyMonsterDefence - myMonsterAttack;
                int myLife = duelWithUser.getMyBoard().getLP();
                duelWithUser.getMyBoard().setLP(myLife - damage);
                if (enemyMonsterCard.getName().equals("Man-Eater Bug") && shouldFlipSummonOccur) {
                    duelWithUser.manEaterBugEffect(true);
                }
                if (enemyMonsterCard.getName().equals("Marshmallon") && shouldFlipSummonOccur) {
                    int myLife1 = duelWithUser.getMyBoard().getLP();
                    duelWithUser.getMyBoard().setLP(myLife1 - 1000);
                    damage += 1000;
                    return "no card is destroyed and you received " + damage + "damage";
                }
                return "no card is destroyed and you received " + damage + " battle damage";
            }
        }
    }

    private String exploderDragonEffectUnderAttack(int address) {
        DuelWithUser duelWithUser = DuelWithUser.getInstance();
        int myMonsterAttack = monsterCard.getFinalAttack();
        int enemyMonsterAttack = enemyMonsterCard.getFinalAttack();
        if (enemyMonsterCard.getIsInAttackPosition() && enemyMonsterAttack > myMonsterAttack) {
            int damage = enemyMonsterAttack - myMonsterAttack;
            int myLife = duelWithUser.getMyBoard().getLP();
            duelWithUser.getMyBoard().setLP(myLife - damage);
            destroyMyCard(monsterCard);
            return "Your monster card is destroyed and you received " + damage + " battle damage";
        } else {
            destroyMyCard(monsterCard);
            destroyEnemyCard(address);
            return "both you and your opponent monster cards are destroyed and no one receives damage";
        }
    }

    private void kjkj(){

    }

    public String attackUser() {
        DuelWithUser duelWithUser = DuelWithUser.getInstance();
        Card card = duelWithUser.getMyBoard().getSelectedCard();
        if (card == null) {
            return "no card is selected yet";
        }
        if (!MainPhase1Controller.getInstance().isCardInMyMonsterTerritory()) {
            return "you can’t attack with this card";
        }
        MonsterCard monsterCard = (MonsterCard) card;
        if (monsterCard.getLastTimeAttackedTurn() == duelWithUser.getTurnCounter()) {
            return "this card already attacked";
        }
        if (!isEnemyMonsterTerritoryEmpty()) {
            return "you can’t attack the opponent directly";
        }
        int myMonsterAttack = monsterCard.getFinalAttack();
        int myEnemyLife = duelWithUser.getEnemyBoard().getLP();
        monsterCard.setLastTimeAttackedTurn(duelWithUser.getTurnCounter());
        duelWithUser.getEnemyBoard().setLP(myEnemyLife - myMonsterAttack);
        return "you opponent receives " + myMonsterAttack + " battle damage";
    }

    private boolean isEnemyMonsterTerritoryEmpty() {
        DuelWithUser duelWithUser = DuelWithUser.getInstance();
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getEnemyBoard().getMonsterTerritory();
        for (int i = 1; i < 6; i++) {
            if (monsterTerritory.get(i) != null) {
                return false;
            }
        }
        return true;
    }

    private void destroyEnemyCard(int address) {
        DuelWithUser duelWithUser = DuelWithUser.getInstance();
        ArrayList<Card> graveyard = duelWithUser.getEnemyBoard().getGraveyard();
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getEnemyBoard().getMonsterTerritory();
        graveyard.add(monsterTerritory.get(address));
        monsterTerritory.put(address, null);
    }

    private void destroyMyCard(MonsterCard monsterCard) {
        DuelWithUser duelWithUser = DuelWithUser.getInstance();
        ArrayList<Card> graveyard = duelWithUser.getMyBoard().getGraveyard();
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        int address = 1;
        for (int i = 1; i < 6; i++) {
            if (monsterTerritory.get(i) == monsterCard) {
                address = i;
            }
        }
        graveyard.add(monsterTerritory.get(address));
        monsterTerritory.put(address, null);
    }

    public void beforeBattleEffects() {
        commandKnightEffect();
    }

    private void commandKnightEffect() {
        Board board1 = DuelWithUser.getInstance().getMyBoard();
        Board board2 = DuelWithUser.getInstance().getEnemyBoard();
        for (int i = 1; i <= 5; i++) {
            if (board1.getMonsterTerritory().get(i) != null && board1.getMonsterTerritory().get(i).getName().equals("Command Knight") && board1.getMonsterTerritory().get(i).getIsFacedUp()) {
                for (int j = 1; j <= 5; j++) {
                    if (j != i && board1.getMonsterTerritory().get(j) != null) {
                        board1.getMonsterTerritory().get(j).increaseFinalAttack(400);
                    }
                }
            }
            if (board2.getMonsterTerritory().get(i) != null && board2.getMonsterTerritory().get(i).getName().equals("Command Knight") && board2.getMonsterTerritory().get(i).getIsFacedUp()) {
                for (int j = 1; j <= 5; j++) {
                    if (j != i && board2.getMonsterTerritory().get(j) != null) {
                        board2.getMonsterTerritory().get(j).increaseFinalAttack(400);
                    }
                }
            }
        }
    }

    public void afterBattleEffects() {
        Board board1 = DuelWithUser.getInstance().getMyBoard();
        Board board2 = DuelWithUser.getInstance().getEnemyBoard();
        for (int i = 1; i <= 5; i++) {
            if (board1.getMonsterTerritory().get(i) != null) {
                board1.getMonsterTerritory().get(i).setFinalAttack(board1.getMonsterTerritory().get(i).getAttack());
                board1.getMonsterTerritory().get(i).setFinalDefence(board1.getMonsterTerritory().get(i).getDefence());
            }
            if (board2.getMonsterTerritory().get(i) != null) {
                board2.getMonsterTerritory().get(i).setFinalAttack(board1.getMonsterTerritory().get(i).getAttack());
                board2.getMonsterTerritory().get(i).setFinalDefence(board1.getMonsterTerritory().get(i).getDefence());
            }
        }
    }

    private void theCalculatorEffect(MonsterCard monsterCard) {
        int sumOfLevels = 0;
        for (int i = 1; i <= 5; i++) {
            MonsterCard monsterCard1 = monsterCard.getCurrentBoard().getMonsterTerritory().get(i);
            if (monsterCard1 != null && monsterCard1 != monsterCard && monsterCard1.getIsFacedUp()) {
                sumOfLevels += monsterCard1.getLevel();
            }
        }
        monsterCard.setFinalAttack(300 * sumOfLevels);
    }

    private void suijinEffect() {
        if (enemyMonsterCard.getStartEffectTurn() == -1 && enemyMonsterCard.getIsFacedUp()) {
            enemyMonsterCard.setStartEffectTurn(DuelWithUser.getInstance().getTurnCounter());
            monsterCard.setFinalAttack(0);
        }
    }
}
