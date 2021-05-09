package controller.duel.phases;

import controller.duel.DuelWithUser;
import model.Board;
import model.Card;
import model.MonsterCard;

import java.util.ArrayList;
import java.util.HashMap;


public class BattlePhaseController {
    private static BattlePhaseController battlePhase;
    public MonsterCard myMonster;
    public MonsterCard enemyMonster;

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
        myMonster = (MonsterCard) card;
        if (!myMonster.getIsInAttackPosition()) {
            return "this card is not in attack position";
        }
        if (myMonster.getLastTimeAttackedTurn() == duelWithUser.getTurnCounter()) {
            return "this card already attacked";
        }
        enemyMonster = duelWithUser.getEnemyBoard().getMonsterTerritory().get(address);
        if (enemyMonster == null) {
            return "there is no card to attack here";
        }
        if (enemyMonster.getName().equals("Command knight")) {
            if (isThereAnyOtherCardOnMonsterTerritory()) {
                return "you can't attack this card while there are other monsters on the combat";
            }
        }
        //till this line we check whether
        // selected card can attack or not
        // (generally)
        myMonster.setLastTimeAttackedTurn(duelWithUser.getTurnCounter());
        if (myMonster.getName().equals("The Calculator")) {
            theCalculatorEffect(myMonster);
        }
        if (enemyMonster.getName().equals("Suijin")) {
            suijinEffect();
        }
        if (enemyMonster.getName().equals("The Calculator")) {
            theCalculatorEffect(enemyMonster);
        }
        if (enemyMonster.getName().equals("Texchanger") && texchangerEffect()) {
            duelWithUser.getMyBoard().setSelectedCard(null);
            return "your attack was blocked";
        }
        //till this line the effects
        // just effect the final damage
        //(before damage calculation)
        int myMonsterAttack = myMonster.getFinalAttack();
        if (enemyMonster.getName().equals("Exploder Dragon")) {
            String result = exploderDragonEffectUnderAttack(address);
            duelWithUser.getMyBoard().setSelectedCard(null);
            return result;
        }
        if (enemyMonster.getIsInAttackPosition()) {
            int enemyMonsterAttack = enemyMonster.getFinalAttack();
            if (enemyMonsterAttack == myMonsterAttack) {
                return bothSideHaveEqualAttack(address);
            } else if (myMonsterAttack > enemyMonsterAttack) {
                return myAttackIsHigherThanEnemyAttack(address);
            } else {
                return myAttackIsLowerThanEnemyAttack(address);
            }
        } else {
            int enemyMonsterDefence = enemyMonster.getFinalDefence();
            boolean shouldFlipSummonOccur = !enemyMonster.getIsFacedUp();
            enemyMonster.setFacedUp(true);
            if (myMonsterAttack > enemyMonsterDefence) {
                if (myMonster.getName().equals("Marshmallon")) {
                    if (enemyMonster.getName().equals("Marshmallon")) {
                        duelWithUser.getMyBoard().setSelectedCard(null);
                        if (shouldFlipSummonOccur) {
                            int myLife = duelWithUser.getMyBoard().getLP();
                            duelWithUser.getMyBoard().setLP(myLife - 1000);
                            return "no card is destroyed and you received " + 1000 + "damage";
                        }
                        return "no card is destroyed";
                    }
                    if (enemyMonster.getName().equals("Yomi Ship")) {
                        destroyEnemyMonster(address);
                        destroyMyMonster(myMonster);
                        duelWithUser.getMyBoard().setSelectedCard(null);
                        return "both you and your opponent monster cards are destroyed and opponent receives damage";
                    }
                    //todo man eater
                }
                if (myMonster.getName().equals("Exploder Dragon")) {

                }
                if (enemyMonster.getName().equals("Marshmallon")) {
                    if (shouldFlipSummonOccur) {
                        int myLife = duelWithUser.getMyBoard().getLP();
                        duelWithUser.getMyBoard().setLP(myLife - 1000);
                        duelWithUser.getMyBoard().setSelectedCard(null);
                        return "opponent’s monster was Marshmallon and no card is destroyed and you receive 1000 damage";
                    }
                    duelWithUser.getMyBoard().setSelectedCard(null);
                    return "no card is destroyed";
                }
                destroyEnemyMonster(address);
                if (enemyMonster.getName().equals("Man-Eater Bug") && shouldFlipSummonOccur) {
                    duelWithUser.manEaterBugEffect(true);
                } else if (enemyMonster.getName().equals("Yomi Ship")) {
                    destroyMyMonster(myMonster);
                    duelWithUser.getMyBoard().setSelectedCard(null);
                    return "both you and your opponent monster cards are destroyed and no one receives damage";
                }
                duelWithUser.getMyBoard().setSelectedCard(null);
                return "the defense position monster is destroyed";
            } else if (myMonsterAttack == enemyMonsterDefence) {
                return myAttackIsEqualToEnemyDefence(shouldFlipSummonOccur);
            } else {
                return myAttackIsLowerThanEnemyDefence(shouldFlipSummonOccur);
            }
        }
    }

    private String exploderDragonEffectUnderAttack(int address) {
        DuelWithUser duelWithUser = DuelWithUser.getInstance();
        int myMonsterAttack = myMonster.getFinalAttack();
        int enemyMonsterAttack = enemyMonster.getFinalAttack();
        if (enemyMonster.getIsInAttackPosition() && enemyMonsterAttack > myMonsterAttack) {
            int damage = enemyMonsterAttack - myMonsterAttack;
            int myLife = duelWithUser.getMyBoard().getLP();
            duelWithUser.getMyBoard().setLP(myLife - damage);
            if (myMonster.getName().equals("Marshmallon")) {
                return "no card is destroyed";
            }
            destroyMyMonster(myMonster);
            return "Your monster card is destroyed and you received " + damage + " battle damage";
        } else {
            destroyMyMonster(myMonster);
            destroyEnemyMonster(address);
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

    private void destroyEnemyMonster(int address) {
        DuelWithUser duelWithUser = DuelWithUser.getInstance();
        ArrayList<Card> graveyard = duelWithUser.getEnemyBoard().getGraveyard();
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getEnemyBoard().getMonsterTerritory();
        graveyard.add(monsterTerritory.get(address));
        monsterTerritory.put(address, null);
    }

    private void destroyMyMonster(MonsterCard monsterCard) {
        DuelWithUser duelWithUser = DuelWithUser.getInstance();
        ArrayList<Card> graveyard = duelWithUser.getMyBoard().getGraveyard();
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        for (int i = 1; i < 6; i++) {
            if (monsterTerritory.get(i) == monsterCard) {
                graveyard.add(monsterTerritory.get(i));
                monsterTerritory.put(i, null);
                return;
            }
        }
    }

    public void beforeBattleEffects() {
        commandKnightEffect();
    }

    private void commandKnightEffect() {
        Board board1 = DuelWithUser.getInstance().getMyBoard();
        Board board2 = DuelWithUser.getInstance().getEnemyBoard();
        for (int i = 1; i <= 5; i++) {
            MonsterCard myMonster = board1.getMonsterTerritory().get(i);
            if (myMonster != null && myMonster.getName().equals("Command Knight") && myMonster.getIsFacedUp()) {
                for (int j = 1; j <= 5; j++) {
                    if (board1.getMonsterTerritory().get(j) != null) {
                        board1.getMonsterTerritory().get(j).increaseFinalAttack(400);
                    }
                }
            }
            MonsterCard enemyMonster = board2.getMonsterTerritory().get(i);
            if (enemyMonster != null && enemyMonster.getName().equals("Command Knight") && enemyMonster.getIsFacedUp()) {
                for (int j = 1; j <= 5; j++) {
                    if (board2.getMonsterTerritory().get(j) != null) {
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
            MonsterCard myMonster = board1.getMonsterTerritory().get(i);
            if (myMonster != null) {
                myMonster.setFinalAttack(myMonster.getAttack());
                myMonster.setFinalDefence(myMonster.getDefence());
            }
            MonsterCard enemyMonster = board2.getMonsterTerritory().get(i);
            if (enemyMonster != null) {
                enemyMonster.setFinalAttack(enemyMonster.getAttack());
                enemyMonster.setFinalDefence(enemyMonster.getDefence());
            }
        }
    }

    private void theCalculatorEffect(MonsterCard monsterCard) {
        int sumOfLevels = 0;
        for (int i = 1; i <= 5; i++) {
            MonsterCard monsterCard1 = monsterCard.getCurrentBoard().getMonsterTerritory().get(i);
            if (monsterCard1 != null && monsterCard1.getIsFacedUp()) {
                sumOfLevels += monsterCard1.getLevel();
            }
        }
        monsterCard.setFinalAttack(300 * sumOfLevels);
    }

    private void suijinEffect() {
        if (enemyMonster.getStartEffectTurn() == -1) {
            enemyMonster.setStartEffectTurn(0);
            myMonster.setFinalAttack(0);
        }
    }

    private boolean texchangerEffect() {
        DuelWithUser duelWithUser = DuelWithUser.getInstance();
        if (enemyMonster.getStartEffectTurn() != duelWithUser.getTurnCounter()) {
            enemyMonster.setStartEffectTurn(duelWithUser.getTurnCounter());
            return true;
        }
        return false;
    }

    private boolean isThereAnyOtherCardOnMonsterTerritory() {
        DuelWithUser duelWithUser = DuelWithUser.getInstance();
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getEnemyBoard().getMonsterTerritory();
        for (int i = 1; i < 6; i++) {
            if (monsterTerritory.get(i) != enemyMonster && monsterTerritory.get(i) != null) {
                return true;
            }
        }
        return false;
    }

    private String marshmelloAgainstDragonOrShip(int address) {
        DuelWithUser duelWithUser = DuelWithUser.getInstance();
        destroyEnemyMonster(address);
        destroyMyMonster(myMonster);
        duelWithUser.getMyBoard().setSelectedCard(null);
        return "both you and your opponent monster cards are destroyed and no one receives damage";
    }

    private String bothSideHaveEqualAttack(int address) {
        DuelWithUser duelWithUser = DuelWithUser.getInstance();
        if (myMonster.getName().equals("Marshmallon") && enemyMonster.getName().equals("Marshmallon")) {
            duelWithUser.getMyBoard().setSelectedCard(null);
            return "no card is destroyed";
        }
        if (myMonster.getName().equals("Marshmallon")) {
            if (enemyMonster.getName().equals("Yomi Ship")) {
                return marshmelloAgainstDragonOrShip(address);
            }
            destroyEnemyMonster(address);
            duelWithUser.getMyBoard().setSelectedCard(null);
            return "your opponent’s monster is destroyed and no one receives damage";
        }
        if (enemyMonster.getName().equals("Marshmallon")) {
            if (myMonster.getName().equals("Exploder Dragon")) {
                return marshmelloAgainstDragonOrShip(address);
            }
            destroyMyMonster(myMonster);
            duelWithUser.getMyBoard().setSelectedCard(null);
            return "your monster card is destroyed and no one receives damage";
        }
        destroyMyMonster(myMonster);
        destroyEnemyMonster(address);
        duelWithUser.getMyBoard().setSelectedCard(null);
        return "both you and your opponent monster cards are destroyed and no one receives damage";
    }

    private String myAttackIsHigherThanEnemyAttack(int address) {
        int myMonsterAttack = myMonster.getFinalAttack();
        int enemyMonsterAttack = enemyMonster.getFinalAttack();
        DuelWithUser duelWithUser = DuelWithUser.getInstance();
        int damage = myMonsterAttack - enemyMonsterAttack;
        int enemyLife = duelWithUser.getEnemyBoard().getLP();
        duelWithUser.getEnemyBoard().setLP(enemyLife - damage);
        if (enemyMonster.getName().equals("Marshmallon")) {
            duelWithUser.getMyBoard().setSelectedCard(null);
            return "no card is destroyed and your opponent receives " + damage + " battle damage";
        }
        destroyEnemyMonster(address);
        if (enemyMonster.getName().equals("Yomi Ship")) {
            destroyMyMonster(myMonster);
            duelWithUser.getMyBoard().setSelectedCard(null);
            return "both you and your opponent monster cards are destroyed and opponent receives" + damage + " damage";
        }
        duelWithUser.getMyBoard().setSelectedCard(null);
        return "your opponent’s monster is destroyed and your opponent receives " + damage + " battle damage";
    }

    private String myAttackIsLowerThanEnemyAttack(int address) {
        DuelWithUser duelWithUser = DuelWithUser.getInstance();
        int myMonsterAttack = myMonster.getFinalAttack();
        int enemyMonsterAttack = enemyMonster.getFinalAttack();
        if (myMonster.getName().equals("Exploder Dragon")) {
            destroyMyMonster(myMonster);
            destroyEnemyMonster(address);
            duelWithUser.getMyBoard().setSelectedCard(null);
            return "both you and your opponent monster cards are destroyed and no one receives damage";
        } else {
            int damage = enemyMonsterAttack - myMonsterAttack;
            int myLife = duelWithUser.getMyBoard().getLP();
            duelWithUser.getMyBoard().setLP(myLife - damage);
            if (myMonster.getName().equals("Marshmallon")) {
                duelWithUser.getMyBoard().setSelectedCard(null);
                return "no card is destroyed and you received " + damage + " battle damage";
            }
            destroyMyMonster(myMonster);
            duelWithUser.getMyBoard().setSelectedCard(null);
            return "Your monster card is destroyed and you received " + damage + " battle damage";
        }
    }

    private String myAttackIsHigherThanEnemyDefence() {

    }

    private String myAttackIsEqualToEnemyDefence(boolean shouldFlipSummonOccur) {
        DuelWithUser duelWithUser = DuelWithUser.getInstance();
        if (enemyMonster.getName().equals("Man-Eater Bug") && shouldFlipSummonOccur) {
            duelWithUser.manEaterBugEffect(true);
        }
        duelWithUser.getMyBoard().setSelectedCard(null);
        return "no card is destroyed";
    }

    private String myAttackIsLowerThanEnemyDefence(boolean shouldFlipSummonOccur) {
        int enemyMonsterDefence = enemyMonster.getFinalDefence();
        int myMonsterAttack = myMonster.getFinalAttack();
        DuelWithUser duelWithUser = DuelWithUser.getInstance();
        if (myMonster.getName().equals("Exploder Dragon")) {
            if (enemyMonster.getName().equals("Man-Eater Bug") && shouldFlipSummonOccur) {
                duelWithUser.manEaterBugEffect(true);
            }
            duelWithUser.getMyBoard().setSelectedCard(null);
            if (enemyMonster.getName().equals("Marshmallon") && shouldFlipSummonOccur) {
                int myLife = duelWithUser.getMyBoard().getLP();
                duelWithUser.getMyBoard().setLP(myLife - 1000);
                return "no card is destroyed and you received " + 1000 + "damage";
            }
            return "no one receives damage";
        }
        int damage = enemyMonsterDefence - myMonsterAttack;
        int myLife = duelWithUser.getMyBoard().getLP();
        duelWithUser.getMyBoard().setLP(myLife - damage);
        if (enemyMonster.getName().equals("Man-Eater Bug") && shouldFlipSummonOccur) {
            duelWithUser.manEaterBugEffect(true);
        }
        duelWithUser.getMyBoard().setSelectedCard(null);
        if (enemyMonster.getName().equals("Marshmallon") && shouldFlipSummonOccur) {
            int myLife1 = duelWithUser.getMyBoard().getLP();
            duelWithUser.getMyBoard().setLP(myLife1 - 1000);
            damage += 1000;
            return "no card is destroyed and you received " + damage + "damage";
        }
        return "no card is destroyed and you received " + damage + " battle damage";
    }

}
