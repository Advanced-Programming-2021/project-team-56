package controller.duel.phases;

import controller.duel.DuelWithUser;
import controller.duel.effects.SpellEffectActivate;
import model.Board;
import model.Card;
import model.MonsterCard;
import model.Output;
import view.duel.EffectView;

import java.util.ArrayList;
import java.util.HashMap;


public class BattlePhaseController {

    private static BattlePhaseController battlePhase;

    private final DuelWithUser duelWithUser;
    private final EffectView effectView;
    private final SpellEffectActivate spellEffectActivate;
    private final OpponentPhase opponentPhase;

    public MonsterCard myMonster;
    public MonsterCard enemyMonster;

    {
        duelWithUser = DuelWithUser.getInstance();
        effectView = EffectView.getInstance();
        spellEffectActivate = SpellEffectActivate.getInstance();
        opponentPhase = OpponentPhase.getInstance();
    }

    private BattlePhaseController() {
    }

    public static BattlePhaseController getInstance() {
        if (battlePhase == null)
            battlePhase = new BattlePhaseController();
        return battlePhase;
    }

    public String attackUser() {
        String result = isTargetingMonsterOrUserPossible(0, true);
        if (!result.equals(Output.TheGameContinues.toString())) {
            return result;
        }
        MonsterCard monster = (MonsterCard) duelWithUser.getMyBoard().getSelectedCard();
        if (!isEnemyMonsterTerritoryEmpty()) {
            return "you can’t attack the opponent directly";
        }
        effectFinalDamage();
        if (doesEnemyTerritoryIncludeMessengerOfPeace() && myMonster.getFinalAttack() >= 1500) {
            return "you can't attack with this card\ndue to the effect of messenger of peace";
        }
        opponentPhase.startChainLink();
        monster.setLastTimeAttackedTurn(duelWithUser.getTurnCounter());
        if (duelWithUser.getMyBoard().isItEffectedByMagicCylinder()) {
            return magicCylinderConverseDamage(monster);
        }
        if (duelWithUser.getMyBoard().isItEffectedByMirrorFace()) {
            duelWithUser.getMyBoard().setItEffectedByMirrorFace(false);
            return "your faced up cards were destroyed";
        }
        if (duelWithUser.getMyBoard().isItAttackNegated()) {
            duelWithUser.getMyBoard().setItAttackNegated(false);
            return "your attack was blocked";
        }
        int myMonsterAttack = monster.getFinalAttack();
        int enemyLife = duelWithUser.getEnemyBoard().getLP();
        duelWithUser.getEnemyBoard().setLP(enemyLife - myMonsterAttack);
        return "your opponent receives\n" + myMonsterAttack + " battle damage";
    }

    private String magicCylinderConverseDamage(MonsterCard monster) {
        int myMonsterAttack = monster.getFinalAttack();
        monster.setLastTimeAttackedTurn(duelWithUser.getTurnCounter());
        duelWithUser.getMyBoard().setItEffectedByMagicCylinder(false);
        int myLife = duelWithUser.getMyBoard().getLP();
        duelWithUser.getEnemyBoard().setLP(myLife - myMonsterAttack);
        return "you receive " + myMonsterAttack + " battle damage";
    }

    private String isTargetingMonsterOrUserPossible(int address, boolean isUserUnderAttack) {
        Card card = duelWithUser.getMyBoard().getSelectedCard();
        if (card == null) {
            return "no card is selected yet";
        }
        if (!MainPhase1Controller.getInstance().isCardInMyMonsterTerritory()) {
            return "you can’t attack with this card";
        }
        myMonster = (MonsterCard) card;
        if (!myMonster.getIsInAttackPosition()) {
            return "this card is not in attack position";
        }
        if (isThereSwordOfRevealingLight()) {
            return "you can't attack because of effect of\n swords of revealing light";
        }
        if (myMonster.getLastTimeAttackedTurn() == duelWithUser.getTurnCounter()) {
            return "this card already attacked";
        }
        if (!isUserUnderAttack) {
            enemyMonster = duelWithUser.getEnemyBoard().getMonsterTerritory().get(address);
            if (enemyMonster == null) {
                return "there is no card to attack here";
            }
            if (enemyMonster.getName().equals("Command knight")) {
                if (isThereAnyOtherCardOnMonsterTerritory()) {
                    return "you can't attack this card while\nthere are other monsters on the combat";
                }
            }
        }
        return Output.TheGameContinues.toString();
    }

    public String attackCard(int address) {
        String result = isTargetingMonsterOrUserPossible(address, false);
        if (!result.equals(Output.TheGameContinues.toString())) {
            return result;
        }
        effectFinalDamage();
        if (doesEnemyTerritoryIncludeMessengerOfPeace() && myMonster.getFinalAttack() >= 1500) {
            return "you can't attack with this card\ndue to the effect of messenger of peace";
        }
        opponentPhase.startChainLink();
        myMonster.setLastTimeAttackedTurn(duelWithUser.getTurnCounter());
        if (duelWithUser.getMyBoard().isItEffectedByMagicCylinder()) {
            return magicCylinderConverseDamage(myMonster);
        }
        if (duelWithUser.getMyBoard().isItEffectedByMirrorFace()) {
            duelWithUser.getMyBoard().setItEffectedByMirrorFace(false);
            return "your faced up cards were destroyed";
        }
        if (duelWithUser.getMyBoard().isItAttackNegated()) {
            duelWithUser.getMyBoard().setItAttackNegated(false);
            return "your attack was blocked";
        }
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
            theCalculatorEffect(1);
        }
        if (enemyMonster != null) {
            if (enemyMonster.getName().equals("The Calculator")) {
                theCalculatorEffect(2);
            }
            if (enemyMonster.getName().equals("Suijin")) {
                suijinEffect();
            }
        }
    }

    private boolean doesEnemyTerritoryIncludeMessengerOfPeace() {
        HashMap<Integer, Card> spellTerritory = duelWithUser.getEnemyBoard().getSpellAndTrapTerritory();
        for (int i = 1; i < 6; i++) {
            if (spellTerritory.get(i) != null && spellTerritory.get(i).getName().equals("Messenger of peace")) {
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
            if (spellTerritory.get(i) != null && spellTerritory.get(i).getName().equals("Swords of Revealing Light")) {
                if (spellTerritory.get(i).getIsFacedUp()) {
                    return true;
                }
            }
        }
        return false;
    }

    private String isEnemyMonsterInAttackPosition(int address) {
        //TODO Attack Position
        System.out.println("Attack position");
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
        //TODO Defence Position
        System.out.println("Defence position");
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
                return "both you and your opponent monster cards\nare destroyed and no one receives damage";
            }
            int damage = enemyMonsterAttack - myMonsterAttack;
            int myLife = duelWithUser.getMyBoard().getLP();
            duelWithUser.getMyBoard().setLP(myLife - damage);
            if (myMonster.getName().equals("Marshmallon")) {
                return "no card is destroyed";
            }
            destroyMyMonster(myMonster);
            return "Your monster card is destroyed\nand you received\n" + damage + " battle damage";
        } else {
            destroyMyMonster(myMonster);
            destroyEnemyMonster(address);
            return "both you and your opponent monster cards\n are destroyed and no one receives damage";
        }
    }

    private String defendingExploderDragonInDefencePosition(int address) {
        int myMonsterAttack = myMonster.getFinalAttack();
        enemyMonster.setFacedUp(true);
        int enemyMonsterDefence = enemyMonster.getFinalDefence();
        if (enemyMonsterDefence > myMonsterAttack) {
            if (myMonster.getName().equals("Exploder Dragon")) {
                return "both you and your opponent monster cards\nare destroyed and no one receives damage";
            }
            int damage = enemyMonsterDefence - myMonsterAttack;
            int myLife = duelWithUser.getMyBoard().getLP();
            duelWithUser.getMyBoard().setLP(myLife - damage);
        } else if (enemyMonsterDefence == myMonsterAttack) {
            return "no card is destroyed";
        }
        destroyMyMonster(myMonster);
        destroyEnemyMonster(address);
        return "both you and your opponent monster cards\nare destroyed and no one receives damage";
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
        int counter = 1;
        while (counter <= 2) {
            Board board;
            if (counter == 1) {
                board = duelWithUser.getMyBoard();
            } else {
                board = duelWithUser.getEnemyBoard();
            }
            for (int i = 1; i <= 5; i++) {
                MonsterCard monster = board.getMonsterTerritory().get(i);
                if (monster != null && monster.getName().equals("Command Knight") && monster.getIsFacedUp()) {
                    activateCommandKnightEffectOnBoardMonsters(board);
                }
            }
            counter++;
        }
    }

    private void activateCommandKnightEffectOnBoardMonsters(Board board) {
        for (int i = 1; i <= 5; i++) {
            MonsterCard boardMonster = board.getMonsterTerritory().get(i);
            if (boardMonster != null) {
                boardMonster.increaseFinalAttack(400);
            }
        }
    }

    public void afterBattleEffects() {
        //sets all the monsters ATK & DEF to default before giving any commands (e.g. attack / attack direct)
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

    private void theCalculatorEffect(int player) {
        HashMap<Integer, MonsterCard> monsterTerritory;
        if (player == 1) {
            monsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        } else {
            monsterTerritory = duelWithUser.getEnemyBoard().getMonsterTerritory();
        }
        int sumOfLevels = 0;
        for (int i = 1; i <= 5; i++) {
            MonsterCard monster = monsterTerritory.get(i);
            if (monster != null && monster.getIsFacedUp()) {
                sumOfLevels += monster.getLevel();
            }
        }
        if (player == 1) {
            myMonster.setFinalAttack(300 * sumOfLevels);
        } else {
            enemyMonster.setFinalAttack(300 * sumOfLevels);
        }
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
        //TODO
        System.out.println("My attack was Equal" + address);
        if (myMonster.getName().equals("Marshmallon") && enemyMonster.getName().equals("Marshmallon")) {
            return "no card is destroyed";
        }
        if (myMonster.getName().equals("Marshmallon")) {
            if (enemyMonster.getName().equals("Yomi Ship")) {
                return marshmelloAgainstDragonOrShip(address);
            }
            destroyEnemyMonster(address);
            return "your opponent’s monster is destroyed \nand no one receives damage";
        }
        if (enemyMonster.getName().equals("Marshmallon")) {
            if (myMonster.getName().equals("Exploder Dragon")) {
                return marshmelloAgainstDragonOrShip(address);
            }
            destroyMyMonster(myMonster);
            return "your monster card is destroyed\nand no one receives damage";
        }
        destroyMyMonster(myMonster);
        destroyEnemyMonster(address);
        return "both you and your opponent\nmonster cards are destroyed\nand no one receives damage";
    }

    private String myAttackIsHigherThanEnemyAttack(int address) {
        int myMonsterAttack = myMonster.getFinalAttack();
        int enemyMonsterAttack = enemyMonster.getFinalAttack();
        int damage = myMonsterAttack - enemyMonsterAttack;
        int enemyLife = duelWithUser.getEnemyBoard().getLP();
        duelWithUser.getEnemyBoard().setLP(enemyLife - damage);
        //TODO
        System.out.println("My attack was higher" + address + " EnemyLife: " + enemyLife);
        if (enemyMonster.getName().equals("Marshmallon")) {
            return "no card is destroyed\nand your opponent receives\n" + damage + " battle damage";
        }
        destroyEnemyMonster(address);
        if (enemyMonster.getName().equals("Yomi Ship")) {
            destroyMyMonster(myMonster);
            return "both you and your opponent monster cards\nare destroyed and opponent receives\n" + damage + " damage";
        }
        return "your opponent’s monster is destroyed\nand your opponent receives\n" + damage + " battle damage";
    }

    private String myAttackIsLowerThanEnemyAttack(int address) {
        int myMonsterAttack = myMonster.getFinalAttack();
        int enemyMonsterAttack = enemyMonster.getFinalAttack();
        if (myMonster.getName().equals("Exploder Dragon")) {
            destroyMyMonster(myMonster);
            destroyEnemyMonster(address);
            return "both you and your opponent monster cards\nare destroyed and no one receives damage";
        } else {
            int damage = enemyMonsterAttack - myMonsterAttack;
            int myLife = duelWithUser.getMyBoard().getLP();
            duelWithUser.getMyBoard().setLP(myLife - damage);
            if (myMonster.getName().equals("Marshmallon")) {
                return "no card is destroyed and you received\n" + damage + " battle damage";
            }
            destroyMyMonster(myMonster);
            //TODO
            System.out.println("My attack was lower" + address + " EnemyLife: " + myLife);
            return "Your monster card is destroyed\nand you received \n" + damage + " battle damage";
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
            return "both you and your opponent monster cards\nare destroyed and no one receives damage";
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
                return "no card is destroyed\nand you received " + 1000 + "damage";
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
            return "no card is destroyed and you received\n" + damage + "damage";
        }
        return "no card is destroyed and you received\n" + damage + " battle damage";
    }

    private String yomishipDestroyedInDefencePosition(int address) {
        destroyEnemyMonster(address);
        destroyMyMonster(myMonster);
        return "both you and your opponent monster cards\nare destroyed and no one receives damage";
    }

    private String marshmelloFlipSummons(boolean shouldFlipSummonOccur) {
        if (shouldFlipSummonOccur) {
            int myLife = duelWithUser.getMyBoard().getLP();
            duelWithUser.getMyBoard().setLP(myLife - 1000);
            return "no card is destroyed and you received\n" + 1000 + "damage";
        }
        return "no card is destroyed";
    }

    private String marshmelloAgainstDragonOrShip(int address) {
        destroyEnemyMonster(address);
        destroyMyMonster(myMonster);
        return "both you and your opponent monster cards\nare destroyed and no one receives damage";
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
                    effectView.output(Output.InvalidCommand.toString());
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
