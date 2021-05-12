package controller.duel.phases;

import controller.duel.DuelWithUser;
import controller.duel.effects.SpellEffectActivate;
import model.Board;
import model.Card;
import model.MonsterCard;
import view.duel.EffectView;

import java.util.ArrayList;
import java.util.HashMap;


public class BattlePhaseController {
    private static BattlePhaseController battlePhase;
    public MonsterCard myMonster;
    public MonsterCard enemyMonster;
    private DuelWithUser duelWithUser = DuelWithUser.getInstance();
    private SpellEffectActivate spellEffectActivate = SpellEffectActivate.getInstance();
    private EffectView effectView = EffectView.getInstance();

    private BattlePhaseController() {
    }

    public static BattlePhaseController getInstance() {
        if (battlePhase == null) {
            battlePhase = new BattlePhaseController();
        }
        return battlePhase;
    }

    public String attackCard(int address) {
        String result = isTargetingMonsterPossible(address);
        if (!result.equals("continue the process")) {
            return result;
        }
        effectFinalDamage();
        if (doesEnemyTerritoryIncludeMessengerOfPeace() && myMonster.getFinalAttack() >= 1500) {
            return "you can't attack with this card due to the effect of messenger of peace";
        }
        myMonster.setLastTimeAttackedTurn(duelWithUser.getTurnCounter());
        if (enemyMonster.getName().equals("Texchanger") && texchangerEffect()) {
            duelWithUser.getMyBoard().setSelectedCard(null);
            return "your attack was blocked";
        }
        if (enemyMonster.getName().equals("Exploder Dragon")) {
            result = exploderDragonEffectUnderAttack(address);
        } else if (enemyMonster.getIsInAttackPosition()) {
            result = isEnemyMonsterInAttackPosition(address);
        } else if (!enemyMonster.getIsInAttackPosition()) {
            result = isEnemyMonsterInDefencePosition(address);
        }
        duelWithUser.getMyBoard().setSelectedCard(null);
        return result;
    }

    private void effectFinalDamage() {
        beforeBattleEffects();
        if (myMonster.getName().equals("The Calculator")) {
            theCalculatorEffect(myMonster);
        }
        if (enemyMonster.getName().equals("The Calculator")) {
            theCalculatorEffect(enemyMonster);
        }
        if (enemyMonster.getName().equals("Suijin")) {
            suijinEffect();
        }
    }

    private String isTargetingMonsterPossible(int address) {
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
        if (isThereSwordOfRevealingLight()) {
            return "you can't attack because of effect of swords of revealing light";
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
        return "continue the process";
    }

    private boolean doesEnemyTerritoryIncludeMessengerOfPeace() {
        HashMap<Integer, Card> spellTerritory = duelWithUser.getEnemyBoard().getSpellAndTrapTerritory();
        for (int i = 1; i < 6; i++) {
            if (spellTerritory.get(i).getName().equals("Messenger of peace")) {
                if (spellTerritory.get(i).getIsFacedUp()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isThereSwordOfRevealingLight() {
        HashMap<Integer, Card> spellTerritory = duelWithUser.getEnemyBoard().getSpellAndTrapTerritory();
        for (int i = 1; i < 6; i++) {
            if (spellTerritory.get(i).getName().equals("Swords of Revealing Light")) {
                if (spellTerritory.get(i).getIsFacedUp()) {
                    return true;
                }
            }
        }
        return false;
    }


    private String isEnemyMonsterInAttackPosition(int address) {
        int myMonsterAttack = myMonster.getFinalAttack();
        int enemyMonsterAttack = enemyMonster.getFinalAttack();
        if (enemyMonsterAttack == myMonsterAttack) {
            return bothSideHaveEqualAttack(address);
        } else if (myMonsterAttack > enemyMonsterAttack) {
            return myAttackIsHigherThanEnemyAttack(address);
        } else {
            return myAttackIsLowerThanEnemyAttack(address);
        }
    }

    private String isEnemyMonsterInDefencePosition(int address) {
        int myMonsterAttack = myMonster.getFinalAttack();
        int enemyMonsterDefence = enemyMonster.getFinalDefence();
        boolean shouldFlipSummonOccur = !enemyMonster.getIsFacedUp();
        enemyMonster.setFacedUp(true);
        if (myMonsterAttack > enemyMonsterDefence) {
            return myAttackIsHigherThanEnemyDefence(address, shouldFlipSummonOccur);
        } else if (myMonsterAttack == enemyMonsterDefence) {
            return myAttackIsEqualToEnemyDefence(shouldFlipSummonOccur);
        } else {
            return myAttackIsLowerThanEnemyDefence(shouldFlipSummonOccur);
        }
    }

    private String exploderDragonEffectUnderAttack(int address) {
        if (enemyMonster.getIsInAttackPosition()) {
            return defendingExploderDragonInAttackPosition(address);
        } else {
            return defendingExploderDragonInDefencePosition(address);
        }
    }

    private String defendingExploderDragonInAttackPosition(int address) {
        int myMonsterAttack = myMonster.getFinalAttack();
        int enemyMonsterAttack = enemyMonster.getFinalAttack();
        if (enemyMonsterAttack > myMonsterAttack) {
            if (myMonster.getName().equals("Exploder Dragon")) {
                destroyMyMonster(myMonster);
                destroyEnemyMonster(address);
                return "both you and your opponent monster cards are destroyed and no one receives damage";
            }
            int damage = enemyMonsterAttack - myMonsterAttack;
            int myLife = duelWithUser.getMyBoard().getLP();
            duelWithUser.getMyBoard().setLP(myLife - damage);
            if (myMonster.getName().equals("Marshmallon")) {
                ;
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

    private String defendingExploderDragonInDefencePosition(int address) {
        int myMonsterAttack = myMonster.getFinalAttack();
        enemyMonster.setFacedUp(true);
        int enemyMonsterDefence = enemyMonster.getFinalDefence();
        if (enemyMonsterDefence > myMonsterAttack) {
            if (myMonster.getName().equals("Exploder Dragon")) {
                return "both you and your opponent monster cards are destroyed and no one receives damage";
            }
            int damage = enemyMonsterDefence - myMonsterAttack;
            int myLife = duelWithUser.getMyBoard().getLP();
            duelWithUser.getMyBoard().setLP(myLife - damage);
        } else if (enemyMonsterDefence == myMonsterAttack) {
            return "no card is destroyed";
        }
        destroyMyMonster(myMonster);
        destroyEnemyMonster(address);
        return "both you and your opponent monster cards are destroyed and no one receives damage";
    }

    public String attackUser() {
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
        if (isThereSwordOfRevealingLight()) {
            return "you can't attack because of effect of swords of revealing light";
        }
        effectFinalDamage();
        if (doesEnemyTerritoryIncludeMessengerOfPeace() && myMonster.getFinalAttack() >= 1500) {
            return "you can't attack with this card due to the effect of messenger of peace";
        }
        int myMonsterAttack = monsterCard.getFinalAttack();
        int myEnemyLife = duelWithUser.getEnemyBoard().getLP();
        monsterCard.setLastTimeAttackedTurn(duelWithUser.getTurnCounter());
        duelWithUser.getEnemyBoard().setLP(myEnemyLife - myMonsterAttack);
        return "you opponent receives " + myMonsterAttack + " battle damage";
    }

    private boolean isEnemyMonsterTerritoryEmpty() {
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getEnemyBoard().getMonsterTerritory();
        for (int i = 1; i < 6; i++) {
            if (monsterTerritory.get(i) != null) {
                return false;
            }
        }
        return true;
    }

    private void destroyEnemyMonster(int address) {
        ArrayList<Card> graveyard = duelWithUser.getEnemyBoard().getGraveyard();
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getEnemyBoard().getMonsterTerritory();
        MonsterCard monsterCard = monsterTerritory.get(address);
        graveyard.add(monsterCard);
        monsterTerritory.put(address, null);
        duelWithUser.afterDeathEffect(2, monsterCard);
    }

    private void destroyMyMonster(MonsterCard monsterCard) {
        ArrayList<Card> graveyard = duelWithUser.getMyBoard().getGraveyard();
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        for (int i = 1; i < 6; i++) {
            if (monsterTerritory.get(i) == monsterCard) {
                graveyard.add(monsterTerritory.get(i));
                monsterTerritory.put(i, null);
                return;
            }
        }
        duelWithUser.afterDeathEffect(1, monsterCard);
    }

    public void beforeBattleEffects() {
        commandKnightEffect();
        spellEffectActivate.yamiActivate();
        spellEffectActivate.forestActivate();
        spellEffectActivate.closedForestActivate();
        spellEffectActivate.umiirukaActivate();
        spellEffectActivate.swordOfDarkDestructionActivate2();
        spellEffectActivate.blackPendantActivate();
        spellEffectActivate.unitedWeStandActivate();
        spellEffectActivate.magnumShieldActivate2();
    }

    private void commandKnightEffect() {
        Board board1 = duelWithUser.getMyBoard();
        Board board2 = duelWithUser.getEnemyBoard();
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
        Board board1 = duelWithUser.getMyBoard();
        Board board2 = duelWithUser.getEnemyBoard();
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
            enemyMonster.setStartEffectTurn(-5);
            myMonster.setFinalAttack(0);
        }
    }

    private boolean texchangerEffect() {
        if (enemyMonster.getStartEffectTurn() != duelWithUser.getTurnCounter()) {
            enemyMonster.setStartEffectTurn(duelWithUser.getTurnCounter());
            return true;
        }
        return false;
    }

    private boolean isThereAnyOtherCardOnMonsterTerritory() {
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getEnemyBoard().getMonsterTerritory();
        for (int i = 1; i < 6; i++) {
            if (monsterTerritory.get(i) != enemyMonster && monsterTerritory.get(i) != null) {
                return true;
            }
        }
        return false;
    }

    private String bothSideHaveEqualAttack(int address) {
        if (myMonster.getName().equals("Marshmallon") && enemyMonster.getName().equals("Marshmallon")) {
            return "no card is destroyed";
        }
        if (myMonster.getName().equals("Marshmallon")) {
            if (enemyMonster.getName().equals("Yomi Ship")) {
                return marshmelloAgainstDragonOrShip(address);
            }
            destroyEnemyMonster(address);
            return "your opponent’s monster is destroyed and no one receives damage";
        }
        if (enemyMonster.getName().equals("Marshmallon")) {
            if (myMonster.getName().equals("Exploder Dragon")) {
                return marshmelloAgainstDragonOrShip(address);
            }
            destroyMyMonster(myMonster);
            return "your monster card is destroyed and no one receives damage";
        }
        destroyMyMonster(myMonster);
        destroyEnemyMonster(address);
        return "both you and your opponent monster cards are destroyed and no one receives damage";
    }

    private String myAttackIsHigherThanEnemyAttack(int address) {
        int myMonsterAttack = myMonster.getFinalAttack();
        int enemyMonsterAttack = enemyMonster.getFinalAttack();
        int damage = myMonsterAttack - enemyMonsterAttack;
        int enemyLife = duelWithUser.getEnemyBoard().getLP();
        duelWithUser.getEnemyBoard().setLP(enemyLife - damage);
        if (enemyMonster.getName().equals("Marshmallon")) {
            return "no card is destroyed and your opponent receives " + damage + " battle damage";
        }
        destroyEnemyMonster(address);
        if (enemyMonster.getName().equals("Yomi Ship")) {
            destroyMyMonster(myMonster);
            return "both you and your opponent monster cards are destroyed and opponent receives" + damage + " damage";
        }
        return "your opponent’s monster is destroyed and your opponent receives " + damage + " battle damage";
    }

    private String myAttackIsLowerThanEnemyAttack(int address) {
        int myMonsterAttack = myMonster.getFinalAttack();
        int enemyMonsterAttack = enemyMonster.getFinalAttack();
        if (myMonster.getName().equals("Exploder Dragon")) {
            destroyMyMonster(myMonster);
            destroyEnemyMonster(address);
            return "both you and your opponent monster cards are destroyed and no one receives damage";
        } else {
            int damage = enemyMonsterAttack - myMonsterAttack;
            int myLife = duelWithUser.getMyBoard().getLP();
            duelWithUser.getMyBoard().setLP(myLife - damage);
            if (myMonster.getName().equals("Marshmallon")) {
                return "no card is destroyed and you received " + damage + " battle damage";
            }
            destroyMyMonster(myMonster);
            return "Your monster card is destroyed and you received " + damage + " battle damage";
        }
    }

    private String myAttackIsHigherThanEnemyDefence(int address, boolean shouldFlipSummonOccur) {
        if (myMonster.getName().equals("Marshmallon")) {
            if (enemyMonster.getName().equals("Marshmallon")) {
                return marshmelloFlipSummons(shouldFlipSummonOccur);
            }
            if (enemyMonster.getName().equals("Yomi Ship")) {
                return yomishipDestroyedInDefencePosition(address);
            }
            if (enemyMonster.getName().equals("Man-Eater Bug") && shouldFlipSummonOccur) {
                manEaterBugEffect(true);
            }
        }
        if (myMonster.getName().equals("Exploder Dragon")) {
            if (enemyMonster.getName().equals("Marshmallon")) {
                return marshmelloFlipSummons(shouldFlipSummonOccur);
            }
            if (enemyMonster.getName().equals("Yomi Ship")) {
                return yomishipDestroyedInDefencePosition(address);
            }
            if (enemyMonster.getName().equals("Man-Eater Bug") && shouldFlipSummonOccur) {
                manEaterBugEffect(true);
            }
        }
        if (enemyMonster.getName().equals("Marshmallon")) {
            return marshmelloFlipSummons(shouldFlipSummonOccur);
        }
        destroyEnemyMonster(address);
        if (enemyMonster.getName().equals("Man-Eater Bug") && shouldFlipSummonOccur) {
            manEaterBugEffect(true);
        } else if (enemyMonster.getName().equals("Yomi Ship")) {
            destroyMyMonster(myMonster);
            return "both you and your opponent monster cards are destroyed and no one receives damage";
        }
        return "the defense position monster is destroyed";
    }

    private String myAttackIsEqualToEnemyDefence(boolean shouldFlipSummonOccur) {
        if (enemyMonster.getName().equals("Man-Eater Bug") && shouldFlipSummonOccur) {
            manEaterBugEffect(true);
        }
        return "no card is destroyed";
    }

    private String myAttackIsLowerThanEnemyDefence(boolean shouldFlipSummonOccur) {
        int enemyMonsterDefence = enemyMonster.getFinalDefence();
        int myMonsterAttack = myMonster.getFinalAttack();
        if (myMonster.getName().equals("Exploder Dragon")) {
            if (enemyMonster.getName().equals("Man-Eater Bug") && shouldFlipSummonOccur) {
                manEaterBugEffect(true);
            }
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
            manEaterBugEffect(true);
        }
        if (enemyMonster.getName().equals("Marshmallon") && shouldFlipSummonOccur) {
            int myLife1 = duelWithUser.getMyBoard().getLP();
            duelWithUser.getMyBoard().setLP(myLife1 - 1000);
            damage += 1000;
            return "no card is destroyed and you received " + damage + "damage";
        }
        return "no card is destroyed and you received " + damage + " battle damage";
    }

    private String yomishipDestroyedInDefencePosition(int address) {
        destroyEnemyMonster(address);
        destroyMyMonster(myMonster);
        return "both you and your opponent monster cards are destroyed and no one receives damage";
    }

    private String marshmelloFlipSummons(boolean shouldFlipSummonOccur) {
        if (shouldFlipSummonOccur) {
            int myLife = duelWithUser.getMyBoard().getLP();
            duelWithUser.getMyBoard().setLP(myLife - 1000);
            return "no card is destroyed and you received " + 1000 + "damage";
        }
        return "no card is destroyed";
    }

    private String marshmelloAgainstDragonOrShip(int address) {
        destroyEnemyMonster(address);
        destroyMyMonster(myMonster);
        return "both you and your opponent monster cards are destroyed and no one receives damage";
    }

    public void manEaterBugEffect(boolean isItUnderAttack) {
        if (!canManEaterBugEffectsBeActivated()) {
            effectView.output("this card effect can't be activated");
        } else {
            while (true) {
                effectView.output("do you want to activate this card effect?");
                String result = effectView.input();
                if (result.equals("yes")) {
                    int address = effectView.getAddress();
                    if (address > 5 || address < 1) {
                        effectView.output("invalid selection");
                        continue;
                    }
                    MonsterCard monsterCard;
                    if (isItUnderAttack) {
                        monsterCard = duelWithUser.getMyBoard().getMonsterTerritory().get(address);
                    } else {
                        monsterCard = duelWithUser.getEnemyBoard().getMonsterTerritory().get(address);
                    }
                    if (monsterCard == null) {
                        effectView.output("there is no monster on the address");
                        continue;
                    }
                    if (isItUnderAttack) {
                        destroyMyMonster(monsterCard);
                    } else {
                        destroyEnemyMonster(address);
                    }
                    effectView.output("Man-Eater Bug destroyed " + monsterCard.getName());
                    return;
                } else if (result.equals("cancel")) {
                    effectView.output("ok");
                    return;
                } else {
                    effectView.output("invalid command");
                    continue;
                }
            }
        }
    }

    private boolean canManEaterBugEffectsBeActivated() {
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getEnemyBoard().getMonsterTerritory();
        for (int i = 1; i < 6; i++) {
            if (monsterTerritory.get(i) != null) {
                return true;
            }
        }
        return false;
    }

}
