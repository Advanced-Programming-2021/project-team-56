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
    private final SpellEffectCanActivate spellEffectCanActivate = SpellEffectCanActivate.getInstance();
    private final EffectView effectView = EffectView.getInstance();
    private final DuelWithUser duelWithUser = DuelWithUser.getInstance();

    private SpellEffectActivate() {

    }

    public static SpellEffectActivate getInstance() {
        if (spellEffectActivate == null)
            spellEffectActivate = new SpellEffectActivate();
        return spellEffectActivate;
    }

    public void spellCaller(String spellName) {
        switch (spellName) {
            case "Advanced Ritual Art":
                if (advancedRitualArt()) {
                    effectView.output(getRidOfSpell());
                } else {
                    effectView.output("preparations of this spell are not done yet");
                }
                break;
            case "Terraforming":
                if (terraformingActivate()) {
                    effectView.output(getRidOfSpell());
                } else {
                    effectView.output("preparations of this spell are not done yet");
                }
                break;
            case "Change of Heart":
                if (changeOfHeartActivate()) {
                    effectView.output(getRidOfSpell());
                } else {
                    effectView.output("preparations of this spell are not done yet");
                }
                break;
            case "Harpie’s Feather Duster":
                if (harpiesFeatherDusterActivate()) {
                    effectView.output(getRidOfSpell());
                } else {
                    effectView.output("preparations of this spell are not done yet");
                }
                break;
            case "Raigeki":
                if (raigekiActivate()) {
                    effectView.output(getRidOfSpell());
                } else {
                    effectView.output("preparations of this spell are not done yet");
                }
                break;
            case "Pot of Greed":
                if (potOfGreedActivate()) {
                    effectView.output(getRidOfSpell());
                } else {
                    effectView.output("preparations of this spell are not done yet");
                }
                break;
            case "Dark Hole":
                if (darkHoleActivate()) {
                    effectView.output(getRidOfSpell());
                } else {
                    effectView.output("preparations of this spell are not done yet");
                }
                break;
            case "Monster Reborn":
                if (monsterRebornActivate()) {
                    effectView.output(getRidOfSpell());
                } else {
                    effectView.output("preparations of this spell are not done yet");
                }
                break;
            case "Swords of Revealing Light":
                swordsOfRevealingLightActivate();
                effectView.output("spell activated");
                break;
            case "Sword of Dark Destruction":
                if (swordOfDarkDestructionActivate1()) {
                    effectView.output("spell activated");
                } else {
                    effectView.output("preparations of this spell are not done yet");
                }
                break;
            case "Black Pendant":
            case "United We Stand":
                if (blackPendantAndUnitedWeStandActivate()) {
                    effectView.output("spell activated");
                } else {
                    effectView.output("preparations of this spell are not done yet");
                }
                break;
            case "Magnum Shield":
                if (magnumShieldActivate1()) {
                    effectView.output("spell activated");
                } else {
                    effectView.output("preparations of this spell are not done yet");
                }
                break;
        }
    }

    private String getRidOfSpell() {
        HashMap<Integer, Card> spellAndTrapTerritory = duelWithUser.getMyBoard().getSpellAndTrapTerritory();
        Card spell = duelWithUser.getMyBoard().getSelectedCard();
        for (int i = 1; i < 6; i++) {
            if (spellAndTrapTerritory.get(i) == spell) {
                spellAndTrapTerritory.put(i, null);
                break;
            }
        }
        spellEffectActivate.spellAbsorption();
        duelWithUser.getMyBoard().getGraveyard().add(spell);
        duelWithUser.getMyBoard().setSelectedCard(null);
        return "spell activated";
    }

    private boolean monsterRebornActivate() {
        ArrayList<Card> myGraveyard = duelWithUser.getMyBoard().getGraveyard();
        ArrayList<Card> enemyGraveyard = duelWithUser.getEnemyBoard().getGraveyard();
        boolean isMyGraveyardEmpty = spellEffectCanActivate.isThereMonsterInGraveyard(1);
        boolean isEnemyGraveyardEmpty = spellEffectCanActivate.isThereMonsterInGraveyard(2);
        MonsterCard monsterCard;
        int address;
        if (isMyGraveyardEmpty && isEnemyGraveyardEmpty) {
            effectView.showGraveyardForMonsterRebornAndScannerAndCallOfHunted(true, true);
            address = effectView.getAddress();
            if (address > myGraveyard.size()) {
                address -= myGraveyard.size();
                monsterCard = (MonsterCard) enemyGraveyard.get(address - 1);
                enemyGraveyard.remove(address - 1);
            } else {
                monsterCard = (MonsterCard) myGraveyard.get(address - 1);
                myGraveyard.remove(address - 1);
            }
        } else if (isMyGraveyardEmpty) {
            effectView.showGraveyardForMonsterRebornAndScannerAndCallOfHunted(true, false);
            address = effectView.getAddress();
            monsterCard = (MonsterCard) myGraveyard.get(address - 1);
            myGraveyard.remove(address - 1);
        } else if (isEnemyGraveyardEmpty) {
            effectView.showGraveyardForMonsterRebornAndScannerAndCallOfHunted(false, true);
            address = effectView.getAddress();
            monsterCard = (MonsterCard) enemyGraveyard.get(address - 1);
            enemyGraveyard.remove(address - 1);
        } else {
            return false;
        }
        monsterReborn(monsterCard, true);
        return true;
    }

    public void monsterReborn(MonsterCard monsterCard, boolean isItMonsterRebornEffect) {
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        monsterCard.setFacedUp(true);
        monsterCard.setItControlledByChangeOfHeart(false);
        monsterCard.setStartEffectTurn(-1);
        monsterCard.setLastTimeAttackedTurn(0);
        monsterCard.setSummonedTurn(0);
        monsterCard.setLastTimeChangedPositionTurn(duelWithUser.getTurnCounter());
        monsterCard.setItScanner(monsterCard.getName().equals("Scanner"));
        if (isItMonsterRebornEffect) {
            while (true) {
                effectView.output("do you want to summon it in attack position or defence position?");
                String input = effectView.input();
                if (input.equals("attack position")) {
                    monsterCard.setInAttackPosition(true);
                    break;
                } else if (input.equals("defence position")) {
                    monsterCard.setInAttackPosition(false);
                    break;
                } else {
                    effectView.output("invalid command");
                }
            }
        }
        for (int i = 1; i < 6; i++) {
            if (monsterTerritory.get(i) == null) {
                monsterTerritory.put(i, monsterCard);
                break;
            }
        }
        effectView.output("summoned successfully");
    }

    private boolean changeOfHeartActivate() {
        if (!spellEffectCanActivate.raigekiCanActivate()) {
            return false;
        }
        HashMap<Integer, MonsterCard> enemyMonsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        HashMap<Integer, MonsterCard> myMonsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        for (int i = 1; i < 6; i++) {
            if (myMonsterTerritory.get(i) == null) {
                break;
            }
            if (i == 5) {
                return false;
            }
        }
        while (true) {
            effectView.output("which monster would you mind to takeover?");
            int address = effectView.getAddress();
            if (address > 5 || address < 1) {
                effectView.output("invalid selection");
            } else if (enemyMonsterTerritory.get(address) == null) {
                effectView.output("there is no monster on the address");
            } else {
                MonsterCard monsterCard = enemyMonsterTerritory.get(address);
                monsterCard.setItControlledByChangeOfHeart(true);
                for (int i = 1; i < 6; i++) {
                    if (myMonsterTerritory.get(i) == null) {
                        myMonsterTerritory.put(i, monsterCard);
                        break;
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
                ritualSummon(false, output);
                break;
            } else {
                effectView.output("invalid command");
            }
        }
        Collections.shuffle(duelWithUser.getMyBoard().getMainDeck());
        effectView.output("summoned successfully");
        return true;
    }

    private void ritualSummon(boolean attackPosition, int address) {
        ArrayList<Card> playerHand = duelWithUser.getMyBoard().getPlayerHand();
        MonsterCard monsterCard = null;
        if (address == 1) {
            for (int i = 0; i < playerHand.size(); i++) {
                if (playerHand.get(i).getName().equals("Crab Turtle")) {
                    monsterCard = (MonsterCard) playerHand.get(i);
                    playerHand.remove(i);
                    break;
                }
            }
        } else {
            for (int i = 0; i < playerHand.size(); i++) {
                if (playerHand.get(i).getName().equals("Skull Guardian")) {
                    monsterCard = (MonsterCard) playerHand.get(i);
                    playerHand.remove(i);
                    break;
                }
            }
        }
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        for (int i = 1; i < 6; i++) {
            if (monsterTerritory.get(i) == null) {
                monsterTerritory.put(i, monsterCard);
                break;
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
        return mainDeck.get(address) instanceof MonsterCard;
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
        for (int i = 1; i <= 2; i++) {
            playerHand.add(mainDeck.get(mainDeck.size() - 1));
            mainDeck.remove(mainDeck.size() - 1);
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
                duelWithUser.afterDeathEffect(i, monsterTerritory.get(i));
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
        ArrayList<Card> graveyard = duelWithUser.getEnemyBoard().getGraveyard();
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getEnemyBoard().getMonsterTerritory();
        for (int i = 1; i <= 5; i++) {
            Card spell = spellAndTrapTerritory.get(i);
            if (spell != null) {
                if (spell.getName().equals("Call of the Haunted") && spell.getIsFacedUp()) {
                    for (int j = 1; j < 6; j++) {
                        MonsterCard monster = monsterTerritory.get(j);
                        if (monster != null && !monster.isItControlledByChangeOfHeart() && monster.isItHunted()) {
                            graveyard.add(monster);
                            monsterTerritory.put(j, null);
                            break;
                        }
                    }
                }
                graveyard.add(spell);
                spellAndTrapTerritory.put(i, null);
            }
        }
        Card fieldSpell = duelWithUser.getEnemyBoard().getFieldSpell();
        if (fieldSpell != null) {
            graveyard.add(fieldSpell);
            duelWithUser.getEnemyBoard().setFieldSpell(null);
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
                duelWithUser.afterDeathEffect(i, myMonsterTerritory.get(i));
                myGraveYard.add(myMonsterTerritory.get(i));
                myMonsterTerritory.put(i, null);
            }
            if (enemyMonsterTerritory.get(i) != null) {
                duelWithUser.afterDeathEffect(i, enemyMonsterTerritory.get(i));
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
            Card mySpell = mySpellAndTrapTerritory.get(i);
            if (mySpell != null) {
                if (mySpell.getName().equals("Spell Absorption") && mySpell.getIsFacedUp()) {
                    duelWithUser.getMyBoard().setLP(myLp + 500);
                }
            }
            Card enemySpell = enemySpellAndTrapTerritory.get(i);
            if (enemySpell != null) {
                if (enemySpell.getName().equals("Spell Absorption") && enemySpell.getIsFacedUp()) {
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

    public boolean swordOfDarkDestructionActivate1() {
        if (!spellEffectCanActivate.swordOfDarkDestructionCanActivate()) {
            return false;
        }
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        while (true) {
            int address = effectView.getAddress();
            if (address > 5 || address < 1) {
                effectView.output("invalid selection");
            } else {
                MonsterCard monster = monsterTerritory.get(address);
                if (monster == null) {
                    effectView.output("there is no monster on the address");
                } else {
                    if (!monster.getMonsterType().equals("Fiend") && !monster.getMonsterType().equals("Spellcaster")) {
                        effectView.output("the monster's type is not fiend or spellcaster");
                    } else {
                        duelWithUser.getMyBoard().getSelectedCard().setFacedUp(true);
                        duelWithUser.getMyBoard().getSelectedCard().setEquipID(Card.id);
                        monsterTerritory.get(address).getEquipId().add(Card.id);
                        Card.id++;
                        break;
                    }
                }
            }
        }
        return true;
    }

    public void swordOfDarkDestructionActivate2() {
        int counter = 1;
        while (counter <= 2) {
            HashMap<Integer, MonsterCard> monsterTerritory;
            HashMap<Integer, Card> spellTerritory;
            if (counter == 1) {
                monsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
                spellTerritory = duelWithUser.getMyBoard().getSpellAndTrapTerritory();
            } else {
                monsterTerritory = duelWithUser.getEnemyBoard().getMonsterTerritory();
                spellTerritory = duelWithUser.getEnemyBoard().getSpellAndTrapTerritory();
            }
            for (int i = 1; i <= 5; i++) {
                Card spell = spellTerritory.get(i);
                if (spellTerritory.get(i) != null) {
                    if (spell.getName().equals("Sword of Dark Destruction") && spell.getIsFacedUp()) {
                        for (int j = 1; j <= 5; j++) {
                            MonsterCard monster = monsterTerritory.get(j);
                            if (monster != null) {
                                if (monster.getEquipId().contains(spell.getEquipID())) {
                                    monster.increaseFinalAttack(400);
                                    monster.decreaseFinalDefence(200);
                                }
                            }
                        }
                    }
                }
            }
            counter++;
        }
    }

    public boolean blackPendantAndUnitedWeStandActivate() {
        if (!spellEffectCanActivate.blackPendantAndUnitedWeStandCanActivate()) {
            return false;
        }
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        while (true) {
            int address = effectView.getAddress();
            if (address > 5 || address < 1) {
                effectView.output("invalid selection");
            } else {
                if (monsterTerritory.get(address) == null) {
                    effectView.output("there is no monster on the address");
                } else {
                    duelWithUser.getMyBoard().getSelectedCard().setFacedUp(true);
                    duelWithUser.getMyBoard().getSelectedCard().setEquipID(Card.id);
                    monsterTerritory.get(address).getEquipId().add(Card.id);
                    Card.id++;
                    break;
                }
            }
        }
        return true;
    }

    public void blackPendantActivate() {
        int counter = 1;
        while (counter <= 2) {
            HashMap<Integer, MonsterCard> monsterTerritory;
            HashMap<Integer, Card> spellTerritory;
            if (counter == 1) {
                monsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
                spellTerritory = duelWithUser.getMyBoard().getSpellAndTrapTerritory();
            } else {
                monsterTerritory = duelWithUser.getEnemyBoard().getMonsterTerritory();
                spellTerritory = duelWithUser.getEnemyBoard().getSpellAndTrapTerritory();
            }
            for (int i = 1; i <= 5; i++) {
                Card spell = spellTerritory.get(i);
                if (spell != null) {
                    if (spell.getName().equals("Black Pendant") && spell.getIsFacedUp()) {
                        for (int j = 1; j <= 5; j++) {
                            MonsterCard monster = monsterTerritory.get(j);
                            if (monster != null) {
                                if (monster.getEquipId().contains(spell.getEquipID())) {
                                    monster.increaseFinalAttack(500);
                                }
                            }
                        }
                    }
                }
            }
            counter++;
        }
    }

    public void unitedWeStandActivate() {
        int counter = 1;
        while (counter <= 2) {
            HashMap<Integer, MonsterCard> monsterTerritory;
            HashMap<Integer, Card> spellTerritory;
            if (counter == 1) {
                monsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
                spellTerritory = duelWithUser.getMyBoard().getSpellAndTrapTerritory();
            } else {
                monsterTerritory = duelWithUser.getEnemyBoard().getMonsterTerritory();
                spellTerritory = duelWithUser.getEnemyBoard().getSpellAndTrapTerritory();
            }
            for (int i = 1; i <= 5; i++) {
                Card spell = spellTerritory.get(i);
                if (spell != null) {
                    if (spell.getName().equals("United We Stand") && spell.getIsFacedUp()) {
                        for (int j = 1; j <= 5; j++) {
                            MonsterCard monster = monsterTerritory.get(j);
                            if (monster != null) {
                                if (monster.getEquipId().contains(spell.getEquipID())) {
                                    int numberOfMonsters = 0;
                                    for (int k = 1; k <= 5; k++) {
                                        if (monsterTerritory.get(k) != null) {
                                            numberOfMonsters++;
                                        }
                                    }
                                    monster.increaseFinalAttack(numberOfMonsters * 800);
                                }
                            }
                        }
                    }
                }
            }
            counter++;
        }
    }

    public boolean magnumShieldActivate1() {
        if (!spellEffectCanActivate.magnumShieldCanActivate()) {
            return false;
        }
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        while (true) {
            int address = effectView.getAddress();
            if (address > 5 || address < 1) {
                effectView.output("invalid selection");
            } else {
                if (monsterTerritory.get(address) == null) {
                    effectView.output("there is no monster on the address");
                } else {
                    if (!monsterTerritory.get(address).getMonsterType().equals("Warrior")) {
                        effectView.output("the monster's type is not warrior");
                    } else {
                        duelWithUser.getMyBoard().getSelectedCard().setFacedUp(true);
                        duelWithUser.getMyBoard().getSelectedCard().setEquipID(Card.id);
                        monsterTerritory.get(address).getEquipId().add(Card.id);
                        Card.id++;
                        break;
                    }
                }
            }
        }
        return true;
    }

    public void magnumShieldActivate2() {
        int counter = 1;
        while (counter <= 2) {
            HashMap<Integer, MonsterCard> monsterTerritory;
            HashMap<Integer, Card> spellTerritory;
            if (counter == 1) {
                monsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
                spellTerritory = duelWithUser.getMyBoard().getSpellAndTrapTerritory();
            } else {
                monsterTerritory = duelWithUser.getEnemyBoard().getMonsterTerritory();
                spellTerritory = duelWithUser.getEnemyBoard().getSpellAndTrapTerritory();
            }
            for (int i = 1; i <= 5; i++) {
                Card spell = spellTerritory.get(i);
                if (spell != null) {
                    if (spell.getName().equals("Magnum Shield") && spell.getIsFacedUp()) {
                        for (int j = 1; j <= 5; j++) {
                            MonsterCard monster = monsterTerritory.get(j);
                            if (monster != null) {
                                if (monster.getEquipId().contains(spell.getEquipID())) {
                                    if (monster.getIsInAttackPosition()) {
                                        monster.increaseFinalAttack(monster.getFinalDefence());
                                    } else {
                                        monster.increaseFinalDefence(monster.getFinalAttack());
                                    }
                                }
                            }
                        }
                    }
                }
            }
            counter++;
        }
    }
}

