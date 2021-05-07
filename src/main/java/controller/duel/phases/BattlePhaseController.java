package controller.duel.phases;

import controller.duel.DuelWithUser;
import model.Board;
import model.Card;
import model.MonsterCard;
import model.Update;

import java.util.ArrayList;
import java.util.HashMap;

import static model.EffectType.MONSTER_FLIP;

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
        if (card == null) {
            return "no card is selected yet";
        }
        if (!MainPhase1Controller.getInstance().isCardInMyMonsterTerritory()) {
            return "you can’t attack with this card";
        }
        monsterCard = (MonsterCard) card;
        if (monsterCard.getName().equals("The Calculator")) {
            theCalculatorEffect(monsterCard);
        }
        if (monsterCard.getLastTimeAttackedTurn() == duelWithUser.getTurnCounter()) {
            return "this card already attacked";
        }
        enemyMonsterCard = duelWithUser.getEnemyBoard().getMonsterTerritory().get(address);
        if (enemyMonsterCard == null) {
            return "there is no card to attack here";
        }
        if (enemyMonsterCard.getName().equals("The Calculator")) {
            theCalculatorEffect(enemyMonsterCard);
        }
        monsterCard.setLastTimeAttackedTurn(duelWithUser.getTurnCounter());
        if (enemyMonsterCard.getName().equals("Texchanger")){
            if (enemyMonsterCard.getStartEffectTurn() != duelWithUser.getTurnCounter()){
                enemyMonsterCard.setStartEffectTurn(duelWithUser.getTurnCounter());
                return "your attack was blocked";
            }
        }
        int myMonsterAttack = monsterCard.getFinalAttack();
        if (enemyMonsterCard.getName().equals("Exploder Dragon")) {
            return exploderDragonEffect(address);
        }
        if (enemyMonsterCard.getIsInAttackPosition()) {
            int enemyMonsterAttack = enemyMonsterCard.getFinalAttack();
            if (enemyMonsterAttack == myMonsterAttack) {
                destroyMyCard(monsterCard);
                destroyEnemyCard(address);
                return "both you and your opponent monster cards are destroyed and no one receives damage";
            } else if (myMonsterAttack > enemyMonsterAttack) {
                int damage = myMonsterAttack - enemyMonsterAttack;
                int enemyLife = duelWithUser.getEnemyBoard().getLP();
                duelWithUser.getEnemyBoard().setLP(enemyLife - damage);
                destroyEnemyCard(address);
                if (enemyMonsterCard.getName().equals("Yomi Ship")) {
                    destroyMyCard(monsterCard);
                    return "both you and your opponent monster cards are destroyed and you receive" + damage + " damage";
                }
                return "your opponent’s monster is destroyed and your opponent receives " + damage + " battle damage";
            } else {
                int damage = enemyMonsterAttack - myMonsterAttack;
                int myLife = duelWithUser.getMyBoard().getLP();
                duelWithUser.getMyBoard().setLP(myLife - damage);
                destroyMyCard(monsterCard);
                return "Your monster card is destroyed and you received " + damage + " battle damage";
            }
        } else {
            int enemyMonsterDefence = enemyMonsterCard.getFinalDefence();
            boolean shouldFlipSummonOccur = !enemyMonsterCard.getIsFacedUp();
            enemyMonsterCard.setFacedUp(true);
            if (myMonsterAttack > enemyMonsterDefence) {
                destroyEnemyCard(address);
                if (enemyMonsterCard.getCardEffect().getType() == MONSTER_FLIP && shouldFlipSummonOccur) {
                    enemyMonsterCard.getCardEffect().activateEffect(enemyMonsterCard, Update.getInstance());
                }
                if (enemyMonsterCard.getName().equals("Yomi Ship")) {
                    destroyMyCard(monsterCard);
                    return "both you and your opponent monster cards are destroyed and no one receives damage";
                }
                return "the defense position monster is destroyed";
            } else if (myMonsterAttack == enemyMonsterDefence) {
                if (enemyMonsterCard.getCardEffect().getType() == MONSTER_FLIP && shouldFlipSummonOccur) {
                    enemyMonsterCard.getCardEffect().activateEffect(enemyMonsterCard, Update.getInstance());
                }
                return "no card is destroyed";
            } else {
                int damage = enemyMonsterDefence - myMonsterAttack;
                int myLife = duelWithUser.getMyBoard().getLP();
                duelWithUser.getMyBoard().setLP(myLife - damage);
                if (enemyMonsterCard.getCardEffect().getType() == MONSTER_FLIP && shouldFlipSummonOccur) {
                    enemyMonsterCard.getCardEffect().activateEffect(enemyMonsterCard, Update.getInstance());
                }
                return "no card is destroyed and you received " + damage + " battle damage";
            }
        }
    }

    public String exploderDragonEffect(int address) {
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
}
