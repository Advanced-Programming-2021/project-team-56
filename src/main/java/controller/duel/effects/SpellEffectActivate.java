package controller.duel.effects;

import controller.duel.DuelWithUser;
import model.*;

import server.model.Card;
import view.duel.EffectView;

import java.util.ArrayList;
import java.util.HashMap;

public class SpellEffectActivate {

    private static SpellEffectActivate spellEffectActivate;

    private final DuelWithUser duelWithUser;
    private final EffectView effectView;
    private final SpellEffectCanActivate spellEffectCanActivate;

    private SpellEffectActivate() {

    }

    public static SpellEffectActivate getInstance() {
        if (spellEffectActivate == null)
            spellEffectActivate = new SpellEffectActivate();
        return spellEffectActivate;
    }

    {
        duelWithUser = DuelWithUser.getInstance();
        effectView = EffectView.getInstance();
        spellEffectCanActivate = SpellEffectCanActivate.getInstance();
    }

    public void spellCaller(String spellName) {
        switch (spellName) {
            case "Advanced Ritual Art":
                advancedRitualArt();
                getRidOfSpell();
                break;
            case "Terraforming":
                terraformingActivate();
                getRidOfSpell();
                break;
            case "Change of Heart":
                changeOfHeartActivate();
                getRidOfSpell();
                break;
            case "Harpie's Feather Duster":
                harpiesFeatherDusterActivate();
                getRidOfSpell();
                break;
            case "Raigeki":
                raigekiActivate();
                getRidOfSpell();
                break;
            case "Pot of Greed":
                potOfGreedActivate();
                getRidOfSpell();
                break;
            case "Dark Hole":
                darkHoleActivate();
                getRidOfSpell();
                break;
            case "Monster Reborn":
                monsterRebornActivate();
                getRidOfSpell();
                break;
            case "Swords of Revealing Light":
                swordsOfRevealingLightActivate();
                break;
            case "Sword of Dark Destruction":
                swordOfDarkDestructionActivate1();
                break;
            case "Black Pendant":
            case "United We Stand":
                blackPendantAndUnitedWeStandActivate();
                break;
            case "Magnum Shield":
                magnumShieldActivate1();
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
        duelWithUser.getMyBoard().getGraveyard().add(spell);
        duelWithUser.getMyBoard().setSelectedCard(null);
        return "spell activated";
    }

    private void monsterRebornActivate() {
        boolean isMyGraveyardEmpty = spellEffectCanActivate.isThereNoMonsterInGraveyard(1);
        boolean isEnemyGraveyardEmpty = spellEffectCanActivate.isThereNoMonsterInGraveyard(2);
        MonsterCard monsterCard;
        if (!isMyGraveyardEmpty && !isEnemyGraveyardEmpty) {
            monsterCard = choosingCardFromBothYards(false);
        } else if (!isMyGraveyardEmpty) {
            monsterCard = choosingCardFromMyYard(false);
        } else {
            monsterCard = choosingCardFromEnemyYard(false);
        }
        monsterReborn(monsterCard, true);
    }

    public void scannerEffect() {
        if (!isScannerInMyBoard()) {
            return;
        }
        boolean isMyGraveyardEmpty = spellEffectCanActivate.isThereNoMonsterInGraveyard(1);
        boolean isEnemyGraveyardEmpty = spellEffectCanActivate.isThereNoMonsterInGraveyard(2);
        MonsterCard monsterCard;
        if (!isMyGraveyardEmpty && !isEnemyGraveyardEmpty) {
            monsterCard = choosingCardFromBothYards(true);
        } else if (!isMyGraveyardEmpty) {
            monsterCard = choosingCardFromMyYard(true);
        } else if (!isEnemyGraveyardEmpty) {
            monsterCard = choosingCardFromEnemyYard(true);
        } else {
            return;
        }
        scanTheAttributesForScanner(monsterCard);
    }

    private boolean isScannerInMyBoard() {
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        for (int i = 1; i < 6; i++) {
            MonsterCard monster = monsterTerritory.get(i);
            if (monster != null && monster.isItScanner()) {
                return true;
            }
        }
        return false;
    }

    private void scanTheAttributesForScanner(MonsterCard monsterCard) {
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        for (int i = 1; i < 6; i++) {
            MonsterCard monster = monsterTerritory.get(i);
            if (monster != null && monster.isItScanner()) {
                monster.setName(monsterCard.getName());
                monster.setAttack(monsterCard.getAttack());
                monster.setDefence(monsterCard.getDefence());
                monster.setAttribute(monsterCard.getAttribute());
                monster.setCardType(monsterCard.getCardType());
                monster.setLevel(monsterCard.getLevel());
                monster.setDescription(monsterCard.getDescription());
            }
        }
    }

    private MonsterCard choosingCardFromBothYards(boolean isItScanner) {
        MonsterCard monsterCard;
        ArrayList<Card> myGraveyard = duelWithUser.getMyBoard().getGraveyard();
        ArrayList<Card> enemyGraveyard = duelWithUser.getEnemyBoard().getGraveyard();
        while (true) {
            if (isItScanner) {
                effectView.output("choose a card from graveyard to scan it's attributes for your scanner");
            }
            effectView.showGraveyardForCardsEffects(true, true);
            int address = effectView.getAddress() - 1;
            if (isAddressValid(myGraveyard.size() + enemyGraveyard.size(), address)) {
                if (address > myGraveyard.size()) {
                    address -= myGraveyard.size();
                    if (enemyGraveyard.get(address) instanceof MonsterCard) {
                        monsterCard = (MonsterCard) enemyGraveyard.get(address);
                        if (!isItScanner) {
                            enemyGraveyard.remove(address);
                        }
                    } else {
                        effectView.output(Output.InvalidSelection.toString());
                        continue;
                    }
                } else {
                    if (myGraveyard.get(address) instanceof MonsterCard) {
                        monsterCard = (MonsterCard) myGraveyard.get(address);
                        if (!isItScanner) {
                            myGraveyard.remove(address);
                        }
                    } else {
                        effectView.output(Output.InvalidSelection.toString());
                        continue;
                    }
                }
                break;
            }
            effectView.output(Output.InvalidSelection.toString());
        }
        return monsterCard;
    }

    private MonsterCard choosingCardFromMyYard(boolean isItScanner) {
        ArrayList<Card> myGraveyard = duelWithUser.getMyBoard().getGraveyard();
        MonsterCard monsterCard;
        while (true) {
            if (isItScanner) {
                effectView.output("choose a card from graveyard to scan it's attributes for your scanner");
            }
            effectView.showGraveyardForCardsEffects(true, false);
            int address = effectView.getAddress() - 1;
            if (isAddressValid(myGraveyard.size(), address)) {
                if (myGraveyard.get(address) instanceof MonsterCard) {
                    monsterCard = (MonsterCard) myGraveyard.get(address);
                    if (!isItScanner) {
                        myGraveyard.remove(address);
                    }
                    break;
                }
            }
            effectView.output(Output.InvalidSelection.toString());
        }
        return monsterCard;
    }

    private MonsterCard choosingCardFromEnemyYard(boolean isItScanner) {
        ArrayList<Card> enemyGraveyard = duelWithUser.getEnemyBoard().getGraveyard();
        MonsterCard monsterCard;
        while (true) {
            if (isItScanner) {
                effectView.output("choose a card from graveyard to scan it's attributes for your scanner");
            }
            effectView.showGraveyardForCardsEffects(false, true);
            int address = effectView.getAddress() - 1;
            if (isAddressValid(enemyGraveyard.size(), address)) {
                if (enemyGraveyard.get(address) instanceof MonsterCard) {
                    monsterCard = (MonsterCard) enemyGraveyard.get(address);
                    if (!isItScanner) {
                        enemyGraveyard.remove(address);
                    }
                    break;
                }
            }
            effectView.output(Output.InvalidSelection.toString());
        }
        return monsterCard;
    }

    private boolean isAddressValid(int max, int input) {
        return input < max && input != 0;
    }

    public void monsterReborn(MonsterCard monsterCard, boolean isItMonsterRebornEffect) {
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        monsterCard.setFacedUp(true);
        monsterCard.setLastTimeChangedPositionTurn(duelWithUser.getTurnCounter());
        monsterCard.setItControlledByChangeOfHeart(false);
        monsterCard.setStartEffectTurn(-1);
        monsterCard.setLastTimeAttackedTurn(0);
        monsterCard.setSummonedTurn(0);
        if (isItMonsterRebornEffect) {
            while (true) {
                effectView.output("do you want to summon " + monsterCard.getName() + " in attack position or defence position?");
                String input = effectView.input();
                if (input.equals("attack position")) {
                    monsterCard.setInAttackPosition(true);
                    break;
                } else if (input.equals("defence position")) {
                    monsterCard.setInAttackPosition(false);
                    break;
                } else {
                    effectView.output(Output.InvalidCommand.toString());
                }
            }
        }
        for (int i = 1; i < 6; i++) {
            if (monsterTerritory.get(i) == null) {
                monsterTerritory.put(i, monsterCard);
                break;
            }
        }
        effectView.output(Output.SummonedSuccessfully.toString());
        if (monsterCard.isItScanner()) {
            scannerEffect();
        }
    }

    private void changeOfHeartActivate() {
        HashMap<Integer, MonsterCard> enemyMonsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        HashMap<Integer, MonsterCard> myMonsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        while (true) {
            effectView.output("which monster would you mind to takeover?");
            int address = effectView.getAddress();
            if (address > 5 || address < 1) {
                effectView.output(Output.InvalidSelection.toString());
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
    }

    private void terraformingActivate() {
        ArrayList<Card> mainDeck = duelWithUser.getMyBoard().getMainDeck();
        while (true) {
            effectView.showDeck();
            int address = effectView.getAddress() - 1;
            if (address < 0 || address >= mainDeck.size()) {
                effectView.output(Output.InvalidSelection.toString());
            } else if (!(mainDeck.get(address) instanceof SpellCard)) {
                effectView.output("selected card isn't a spell card");
            } else if (!((SpellCard) mainDeck.get(address)).getIcon().equals("Field")) {
                effectView.output("selected card isn't a field spell ");
            } else {
                duelWithUser.getMyBoard().getPlayerHand().add(mainDeck.get(address));
                effectView.output("selected card successfully");
                return;
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

    private void advancedRitualArt() {
        int output = spellEffectCanActivate.canAdvancedRitualArtActivate();
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
                effectView.output(Output.InvalidCommand.toString());
            }
        }
//        Collections.shuffle(duelWithUser.getMyBoard().getMainDeck());
        effectView.output(Output.SummonedSuccessfully.toString());
    }

    private void ritualSummon(boolean attackPosition, int address) {
        ArrayList<Card> playerHand = duelWithUser.getMyBoard().getPlayerHand();
        MonsterCard monsterCard = null;
        String cardName;
        if (address == 1) {
            cardName = "Crab Turtle";
        } else {
            cardName = "Skull Guardian";
        }
        for (int i = 0; i < playerHand.size(); i++) {
            if (playerHand.get(i).getName().equals(cardName)) {
                monsterCard = (MonsterCard) playerHand.get(i);
                playerHand.remove(i);
                break;
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
        while (totalLevel > 0) {
            effectView.output("please make a tribute for ritual summon from list below");
            effectView.showDeck();
            int address = effectView.getAddress() - 1;
            if (address < 0 || address >= mainDeck.size()) {
                effectView.output(Output.InvalidSelection.toString());
            } else if (!isItValidTribute(address)) {
                effectView.output("selected card can't be tributed");
            } else {
                MonsterCard tribute = ((MonsterCard) mainDeck.get(address));
                totalLevel -= tribute.getLevel();
                effectView.output(tribute.getName() + " was removed from deck");
                duelWithUser.getMyBoard().getGraveyard().add(tribute);
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

    public void potOfGreedActivate() {
        ArrayList<Card> mainDeck = duelWithUser.getMyBoard().getMainDeck();
        ArrayList<Card> playerHand = duelWithUser.getMyBoard().getPlayerHand();
        for (int i = 1; i <= 2; i++) {
            playerHand.add(mainDeck.get(mainDeck.size() - 1));
            mainDeck.remove(mainDeck.size() - 1);
        }
    }

    public void raigekiActivate() {
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getEnemyBoard().getMonsterTerritory();
        ArrayList<Card> graveYard = duelWithUser.getEnemyBoard().getGraveyard();
        for (int i = 1; i <= 5; i++) {
            if (monsterTerritory.get(i) != null) {
                duelWithUser.afterDeathEffect(i, monsterTerritory.get(i));
                graveYard.add(monsterTerritory.get(i));
                monsterTerritory.put(i, null);
            }
        }
    }

    public void harpiesFeatherDusterActivate() {
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
    }

    public void darkHoleActivate() {
        int counter = 1;
        while (counter <= 2) {
            HashMap<Integer, MonsterCard> monsterTerritory;
            ArrayList<Card> graveYard;
            if (counter == 1) {
                monsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
                graveYard = duelWithUser.getMyBoard().getGraveyard();
            } else {
                monsterTerritory = duelWithUser.getEnemyBoard().getMonsterTerritory();
                graveYard = duelWithUser.getEnemyBoard().getGraveyard();
            }
            for (int i = 1; i <= 5; i++) {
                if (monsterTerritory.get(i) != null) {
                    duelWithUser.afterDeathEffect(i, monsterTerritory.get(i));
                    graveYard.add(monsterTerritory.get(i));
                    monsterTerritory.put(i, null);
                }
            }
            counter++;
        }
    }

    public void swordsOfRevealingLightActivate() {
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

    public void swordOfDarkDestructionActivate1() {
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        while (true) {
            int address = effectView.getAddress();
            if (address > 5 || address < 1) {
                effectView.output(Output.InvalidSelection.toString());
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

    public void blackPendantAndUnitedWeStandActivate() {
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        while (true) {
            int address = effectView.getAddress();
            if (address > 5 || address < 1) {
                effectView.output(Output.InvalidSelection.toString());
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

    public void magnumShieldActivate1() {
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        while (true) {
            int address = effectView.getAddress();
            if (address > 5 || address < 1) {
                effectView.output(Output.InvalidSelection.toString());
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

