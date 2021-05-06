package controller.duel.phases;

import controller.duel.DuelWithUser;
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
        if (monsterCard.getLastTimeAttackedTurn() == duelWithUser.getTurnCounter()) {
            return "this card already attacked";
        }
        enemyMonsterCard = duelWithUser.getEnemyBoard().getMonsterTerritory().get(address);
        if (enemyMonsterCard == null) {
            return "there is no card to attack here";
        }
        monsterCard.setLastTimeAttackedTurn(duelWithUser.getTurnCounter());
        int myMonsterAttack = monsterCard.getFinalAttack();
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
                if (enemyMonsterCard.getCardEffect().getType() == MONSTER_FLIP && shouldFlipSummonOccur){
                    enemyMonsterCard.getCardEffect().activateEffect(enemyMonsterCard, Update.getInstance());
                }
                return "the defense position monster is destroyed";
            } else if (myMonsterAttack == enemyMonsterDefence) {
                if (enemyMonsterCard.getCardEffect().getType() == MONSTER_FLIP && shouldFlipSummonOccur){
                    enemyMonsterCard.getCardEffect().activateEffect(enemyMonsterCard, Update.getInstance());
                }
                return "no card is destroyed";
            } else {
                int damage = enemyMonsterDefence - myMonsterAttack;
                int myLife = duelWithUser.getMyBoard().getLP();
                duelWithUser.getMyBoard().setLP(myLife - damage);
                if (enemyMonsterCard.getCardEffect().getType() == MONSTER_FLIP && shouldFlipSummonOccur){
                    enemyMonsterCard.getCardEffect().activateEffect(enemyMonsterCard, Update.getInstance());
                }
                return "no card is destroyed and you received " + damage + " battle damage";
            }
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


}
