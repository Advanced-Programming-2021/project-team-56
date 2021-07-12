package controller.duel.phases;

import controller.duel.DuelWithUser;
import controller.duel.effects.SpellEffectActivate;
import controller.duel.effects.SpellEffectCanActivate;
import controller.duel.effects.TrapEffectActivate;
import controller.duel.effects.TrapEffectCanActivate;
import model.Card;
import model.MonsterCard;
import model.SpellCard;
import model.TrapCard;
import view.duel.DuelView;
import view.duel.EffectView;
import model.Output;

import java.util.ArrayList;
import java.util.HashMap;

public class MainPhase1Controller {

    private static MainPhase1Controller mainPhase1Controller;

    private final DuelWithUser duelWithUser;
    private final EffectView effectView;
    private final SpellEffectActivate spellEffectActivate;
    private final OpponentPhase opponentPhase;

    private Card effectCard;
    private boolean isSummoningInProcess;

    {
        duelWithUser = DuelWithUser.getInstance();
        effectView = EffectView.getInstance();
        spellEffectActivate = SpellEffectActivate.getInstance();
        opponentPhase = OpponentPhase.getInstance();
    }

    private MainPhase1Controller() {

    }

    public static MainPhase1Controller getInstance() {
        if (mainPhase1Controller == null)
            mainPhase1Controller = new MainPhase1Controller();
        return mainPhase1Controller;
    }

    public String summon(boolean isItSet) {
        if (duelWithUser.getMyBoard().getSelectedCard() == null) {
            return Output.NoCardIsSelectedYet.toString();
        }
        if (!canThisCardBeSummoned()) {
            return "you can’t summon this card";
        }
        if (isMonsterTerritoryFull()) {
            return "monster card zone is full";
        }
        if (duelWithUser.getTurnCounter() == duelWithUser.getLastSummonedOrSetTurn()) {
            return "you already summoned/set on this turn";
        }
        MonsterCard monsterCard = (MonsterCard) duelWithUser.getMyBoard().getSelectedCard();
        if (!canThisCardBeNormalSummoned(monsterCard.getName())) {
            return "this card can't be normal summoned";
        }
        if (monsterCard.getName().equals("Beast King Barbaros")) {
            return beastKingBarbaros(isItSet);
        }
        if (monsterCard.getName().equals("Terratiger, the Empowered Warrior")) {
            summon(monsterCard, false, isItSet);
            if (!isItSet) {
                terraTigerTheEmpoweredWarrior();
            }
            return Output.SummonedSuccessfully.toString();
        }
        if (monsterCard.getLevel() < 5) {
            summon(monsterCard, false, isItSet);
            return Output.SummonedSuccessfully.toString();
        }
        if (monsterCard.getLevel() < 7) {
            if (!areThereEnoughCardsToTribute(1)) {
                return "there are not enough cards for tribute";
            }
            DuelView.summonWithTribute = true;
            DuelView.numberOfTributes = 1;
            return "tribute 1 monster\nto summon this card";
        } else {
            if (!areThereEnoughCardsToTribute(2)) {
                return "there are not enough cards for tribute";
            }
            DuelView.summonWithTribute = true;
            DuelView.numberOfTributes = 2;
            return "tribute 2 monster\nto summon this card";
        }
    }

    public String set() {
        if (duelWithUser.getMyBoard().getSelectedCard() == null) {
            return Output.NoCardIsSelectedYet.toString();
        }
        Card card = duelWithUser.getMyBoard().getSelectedCard();
        if (card instanceof MonsterCard) {
            return summon(true);
        } else {
            boolean isItFieldSpell = false;
            if (!isCardInMyHand(card)) {
                return "you can’t set this card";
            }
            if (isMySpellAndTrapTerritoryFull()) {
                return "spell card zone is full";
            }
            if (card instanceof SpellCard) {
                ((SpellCard) card).setSetTurn(duelWithUser.getTurnCounter());
                if (((SpellCard) card).getIcon().equals("Field")) {
                    isItFieldSpell = true;
                }
            } else {
                ((TrapCard) card).setSetTurn(duelWithUser.getTurnCounter());
            }
            dropSpellAndTrapOnTheGround(card, isItFieldSpell);
            duelWithUser.getMyBoard().setSelectedCard(null);
            return "set successfully";
        }
    }

    private void dropSpellAndTrapOnTheGround(Card card, boolean isItFieldSpell) {
        if (isItFieldSpell) {
            SpellCard fieldSpell = duelWithUser.getMyBoard().getFieldSpell();
            if (fieldSpell != null) {
                duelWithUser.getMyBoard().getGraveyard().add(fieldSpell);
            }
            duelWithUser.getMyBoard().setFieldSpell((SpellCard) card);
        } else {
            HashMap<Integer, Card> spellTerritory = duelWithUser.getMyBoard().getSpellAndTrapTerritory();
            for (int i = 1; i < 6; i++) {
                if (spellTerritory.get(i) == null) {
                    spellTerritory.put(i, card);
                    break;
                }
            }
        }
        ArrayList<Card> playerHand = duelWithUser.getMyBoard().getPlayerHand();
        for (int i = 0; i < playerHand.size(); i++) {
            if (playerHand.get(i) == card) {
                playerHand.remove(i);
                break;
            }
        }
    }

    public String changePosition(boolean inAttackPosition) {
        if (duelWithUser.getMyBoard().getSelectedCard() == null) {
            return Output.NoCardIsSelectedYet.toString();
        }
        if (!isCardInMyMonsterTerritory()) {
            return "you can’t change this card position";
        }
        MonsterCard monsterCard = (MonsterCard) duelWithUser.getMyBoard().getSelectedCard();
        if (monsterCard.getIsInAttackPosition() == inAttackPosition) {
            return "this card is already\nin the wanted position";
        }
        if (!monsterCard.getIsFacedUp()) {
            return "this card is faced down\nso you can't change its position";
        }
        if (duelWithUser.getTurnCounter() == monsterCard.getLastTimeChangedPositionTurn()) {
            return "you already changed\nthis card position in this turn";
        }
        monsterCard.setInAttackPosition(inAttackPosition);
        monsterCard.setLastTimeChangedPositionTurn(duelWithUser.getTurnCounter());
        duelWithUser.getMyBoard().setSelectedCard(null);
        return "monster card position\nchanged successfully";
    }

    public String flipSummon() {
        if (duelWithUser.getMyBoard().getSelectedCard() == null) {
            return Output.NoCardIsSelectedYet.toString();
        }
        if (!isCardInMyMonsterTerritory()) {
            return Output.YouCantFlipSummonThisCard.toString();
        }
        MonsterCard monsterCard = (MonsterCard) duelWithUser.getMyBoard().getSelectedCard();
        if (monsterCard.getSummonedTurn() == duelWithUser.getTurnCounter()) {
            return Output.YouCantFlipSummonThisCard.toString();
        }
        if (monsterCard.getIsFacedUp()) {
            return Output.YouCantFlipSummonThisCard.toString();
        }
        if (monsterCard.getName().equals("Man-Eater Bug")) {
            BattlePhaseController.getInstance().manEaterBugEffect(false);
        }
        if (monsterCard.isItScanner()) {
            spellEffectActivate.scannerEffect();
        }
        monsterCard.setFacedUp(true);
        monsterCard.setInAttackPosition(true);
        monsterCard.setSummonedTurn(duelWithUser.getTurnCounter());
        duelWithUser.getMyBoard().setSelectedCard(null);
        return "flip summoned successfully";
    }

    private boolean isMonsterTerritoryFull() {
        for (int i = 1; i < 6; i++) {
            if (duelWithUser.getMyBoard().getMonsterTerritory().get(i) == null) {
                return false;
            }
        }
        return true;
    }

    private boolean areThereEnoughCardsToTribute(int tributes) {
        for (int i = 1; i < 6; i++) {
            if (duelWithUser.getMyBoard().getMonsterTerritory().get(i) != null) {
                tributes--;
            }
        }
        return tributes <= 0;
    }

    private boolean canThisCardBeSummoned() {
        Card card = duelWithUser.getMyBoard().getSelectedCard();
        if (!isCardInMyHand(card)) {
            return false;
        }
        return card instanceof MonsterCard;
    }

    private boolean canThisCardBeNormalSummoned(String name) {
        if (name.equals("Gate Guardian")) {
            return false;
        }
        if (name.equals("Skull Guardian")) {
            return false;
        }
        return !name.equals("Crab Turtle");
    }

    public String specialSummon() {
        Card card = duelWithUser.getMyBoard().getSelectedCard();
        if (card == null) {
            return Output.NoCardIsSelectedYet.toString();
        } else if (!canThisCardBeSummoned()) {
            return Output.ThisCardCantBeSpecialSummoned.toString();
        }
        MonsterCard monsterCard = (MonsterCard) card;
        if (monsterCard.getName().equals("The Tricky")) {
            return specialSummonTheTricky();
        } else if (monsterCard.getName().equals("Gate Guardian")) {
            return specialSummonGateGuardian();
        }
        return Output.ThisCardCantBeSpecialSummoned.toString();
    }

    private String specialSummonGateGuardian() {
        if (!areThereEnoughCardsToTribute(3)) {
            return "there is no way you could special summon a monster";
        }
        int firstAddress = effectView.getAddress();
        if (firstAddress < 1 || firstAddress > 5) {
            return Output.InvalidSelection.toString();
        }
        int secondAddress = effectView.getAddress();
        if (secondAddress < 1 || secondAddress > 5) {
            return Output.InvalidSelection.toString();
        }
        int thirdAddress = effectView.getAddress();
        if (thirdAddress < 1 || thirdAddress > 5) {
            return Output.InvalidSelection.toString();
        }
        if (firstAddress == secondAddress || secondAddress == thirdAddress || thirdAddress == firstAddress) {
            return Output.InvalidSelection.toString();
        }
        if (!isAddressValid(firstAddress) || !isAddressValid(secondAddress) || !isAddressValid(thirdAddress)) {
            return "there is no monster on one of these addresses";
        }
        tribute(firstAddress);
        tribute(secondAddress);
        tribute(thirdAddress);
        summon((MonsterCard) duelWithUser.getMyBoard().getSelectedCard(), true, false);
        return "special summon of Gate Guardian was successful";
    }

    private String specialSummonTheTricky() {
        ArrayList<Card> playerHand = duelWithUser.getMyBoard().getPlayerHand();
        if (!isThereAnyOtherCardOnHand()) {
            return "there is no way you could special summon a monster";
        }
        int address = effectView.getAddress();
        if (address > playerHand.size() || address < 1) {
            return Output.InvalidSelection.toString();
        }
        if (!isItAnotherCard(address)) {
            return "there is no card on the address";
        }
        tributeFromHand(address);
        summon((MonsterCard) duelWithUser.getMyBoard().getSelectedCard(), true, false);
        return "special summon of The Tricky was successful";
    }

    private boolean isItAnotherCard(int address) {
        MonsterCard monsterCard = (MonsterCard) duelWithUser.getMyBoard().getSelectedCard();
        ArrayList<Card> playerHand = duelWithUser.getMyBoard().getPlayerHand();
        return playerHand.get(address - 1) != monsterCard;
    }

    private boolean isThereAnyOtherCardOnHand() {
        MonsterCard monsterCard = (MonsterCard) duelWithUser.getMyBoard().getSelectedCard();
        ArrayList<Card> playerHand = duelWithUser.getMyBoard().getPlayerHand();
        for (Card card : playerHand) {
            if (card != monsterCard) {
                return true;
            }
        }
        return false;
    }

    private boolean isCardInMyHand(Card card) {
        ArrayList<Card> playerHand = duelWithUser.getMyBoard().getPlayerHand();
        for (Card value : playerHand) {
            if (value == card) {
                return true;
            }
        }
        return false;
    }

    private void placeMonsterOnTheField(MonsterCard monsterCard) {
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        for (int i = 1; i < 6; i++) {
            if (monsterTerritory.get(i) == null) {
                monsterTerritory.put(i, monsterCard);
                return;
            }
        }
    }

    private void drawCardFromPlayerHand(Card card) {
        ArrayList<Card> playerHand = duelWithUser.getMyBoard().getPlayerHand();
        for (int i = 0; i < playerHand.size(); i++) {
            if (playerHand.get(i) == card) {
                playerHand.remove(i);
                return;
            }
        }
    }

    public void summon(MonsterCard monster, boolean isSpecialSummon, boolean isItSet) {
        placeMonsterOnTheField(monster);
        drawCardFromPlayerHand(monster);
        duelWithUser.getMyBoard().setSelectedCard(null);
        monster.setSummonedTurn(duelWithUser.getTurnCounter());
        monster.setInAttackPosition(!isItSet);
        monster.setFacedUp(!isItSet);
        if (!isSpecialSummon) {
            duelWithUser.getMyBoard().setLastSummonedOrSetTurn(duelWithUser.getTurnCounter());
        }
        isSummoningInProcess = true;
        duelWithUser.getMyBoard().setItMySummon(true);
        if (monster.getAttack() >= 1000) {
            duelWithUser.getMyBoard().setMyMonsterInDangerOfTrapHole(true);
        }
        opponentPhase.startChainLink();
        spawnKill(monster);
        if (monster.isItScanner()) {
            spellEffectActivate.scannerEffect();
        }
        duelWithUser.getMyBoard().setMyMonsterInDangerOfTrapHole(false);
        duelWithUser.getMyBoard().setItMySummon(false);
        isSummoningInProcess = false;
    }

    private void spawnKill(MonsterCard monster) {
        if (duelWithUser.getMyBoard().isItEffectedBySoleiman() || duelWithUser.getMyBoard().isAmIAffectedByTrapHole()) {
            duelWithUser.getMyBoard().getGraveyard().add(monster);
            HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
            for (int i = 1; i < 6; i++) {
                if (monsterTerritory.get(i) == monster) {
                    monsterTerritory.put(i, null);
                    break;
                }
            }
            duelWithUser.getMyBoard().setAmIAffectedByTrapHole(false);
            duelWithUser.getMyBoard().setItEffectedBySoleiman(false);
        }
    }

    private boolean isAddressValid(int address) {
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        return monsterTerritory.get(address) != null;
    }

    public void tribute(int address) {
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        ArrayList<Card> graveyard = duelWithUser.getMyBoard().getGraveyard();
        MonsterCard monsterCard = monsterTerritory.get(address);
        graveyard.add(monsterCard);
        monsterTerritory.put(address, null);
        duelWithUser.afterDeathEffect(address, monsterCard);
        if (DuelView.numberOfTributes == 0) {
            summon((MonsterCard) DuelWithUser.getInstance().getMyBoard().getSelectedCard(), false, false);
        }
    }

    private void tributeFromHand(int address) {
        ArrayList<Card> playerHand = duelWithUser.getMyBoard().getPlayerHand();
        ArrayList<Card> graveyard = duelWithUser.getMyBoard().getGraveyard();
        graveyard.add(playerHand.get(address - 1));
        playerHand.remove(address - 1);
    }

    public boolean isCardInMyMonsterTerritory() {
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        Card card = duelWithUser.getMyBoard().getSelectedCard();
        for (int i = 1; i < 6; i++) {
            if (monsterTerritory.get(i) == card) {
                return true;
            }
        }
        return false;
    }

    private void terraTigerTheEmpoweredWarrior() {
        if (!canTigerEffectBeActivated()) {
            effectView.output("there is no way you could special summon a monster");
            return;
        }
        while (true) {
            effectView.output("do you want to activate this card effect?");
            String result = effectView.input();
            if (result.equals("cancel")) {
                effectView.output("ok");
                return;
            } else if (result.equals("yes")) {
                ArrayList<Card> playerHand = duelWithUser.getMyBoard().getPlayerHand();
                int address = effectView.getAddress();
                if (address > playerHand.size() || address < 1) {
                    effectView.output(Output.InvalidSelection.toString());
                } else {
                    if (playerHand.get(address - 1) instanceof MonsterCard) {
                        MonsterCard monsterCard = (MonsterCard) playerHand.get(address - 1);
                        if (monsterCard.getLevel() <= 4) {
                            summon(monsterCard, true, false);
                            monsterCard.setInAttackPosition(false);
                            monsterCard.setLastTimeChangedPositionTurn(duelWithUser.getTurnCounter());
                            effectView.output("special summon of " + monsterCard.getName() + " was successful");
                            return;
                        }
                    }
                    effectView.output("you can't special summon this card");
                }
            } else {
                effectView.output(Output.InvalidCommand.toString());
            }
        }
    }

    private boolean canTigerEffectBeActivated() {
        ArrayList<Card> playerHand = duelWithUser.getMyBoard().getPlayerHand();
        for (Card card : playerHand) {
            if ((card instanceof MonsterCard)) {
                MonsterCard monsterCard = (MonsterCard) card;
                if (monsterCard.getLevel() <= 4) {
                    return true;
                }
            }
        }
        return false;
    }

    public String activateSpell() {
        Card card = duelWithUser.getMyBoard().getSelectedCard();
        if (card == null) {
            return Output.NoCardIsSelectedYet.toString();
        } else if (card instanceof MonsterCard) {
            return "you can't activate this card";
        }
        effectCard = card;
        if (isSpellInMyHand()) {
            return activateSpellFromHand();
        } else {
            return activateSpellFromGround();
        }
        //till this line we check possibility of
        //activating spell card (generally)
    }

    private String activateSpellFromHand() {
        if (effectCard instanceof TrapCard) {
            if (TrapEffectCanActivate.getInstance().checkSpellAndTrapPossibility(effectCard.getName())) {
                if (isMySpellAndTrapTerritoryFull()) {
                    return "spell card zone is full";
                }
                effectCard.setFacedUp(true);
                TrapEffectActivate.getInstance().trapAndQuickSpellCaller(effectCard.getName());
                getRidOfTrapOrQuickPlaySpell(effectCard);
                dropSpellAndTrapOnTheGround(effectCard, false);
                return "spell activated";
            } else  return "preparations of this spell are not done yet";
        } else {
            SpellCard spell = (SpellCard) this.effectCard;
            if (spell.getIcon().equals("Field")) {
                dropSpellAndTrapOnTheGround(spell, true);
                spell.setFacedUp(true);
                duelWithUser.getMyBoard().setSelectedCard(null);
                drawCardFromPlayerHand(spell);
                return "spell activated";
            } else {
                if (isMySpellAndTrapTerritoryFull()) {
                    return "spell card zone is full";
                }
                if (!SpellEffectCanActivate.getInstance().checkSpellPossibility(spell.getName())) {
                    return "preparations of this spell are not done yet";
                }
                spell.setFacedUp(true);
                if (spell.getIcon().equals("Quick-play")) {
                    TrapEffectActivate.getInstance().trapAndQuickSpellCaller(spell.getName());
                    getRidOfTrapOrQuickPlaySpell(spell);
                    return "spell activated";
                } else {
                    dropSpellAndTrapOnTheGround(spell, false);
                    spellEffectActivate.spellCaller(spell.getName());
                    return "spell activated";
                }
            }
        }
    }

    private String activateSpellFromGround() {
        if (effectCard.getIsFacedUp()) {
            return "you have already activated this card";
        }
        if (effectCard instanceof TrapCard) {
            if (TrapEffectCanActivate.getInstance().checkSpellAndTrapPossibility(effectCard.getName())) {
                TrapEffectActivate.getInstance().trapAndQuickSpellCaller(effectCard.getName());
                getRidOfTrapOrQuickPlaySpell(effectCard);
                effectCard.setFacedUp(true);
                duelWithUser.getMyBoard().setSelectedCard(null);
                return "spell activated";
            } else return "preparations of this spell are not done yet";
        } else {
            if (((SpellCard) effectCard).getIcon().equals("Field")) {
                duelWithUser.getMyBoard().setSelectedCard(null);
                effectCard.setFacedUp(true);
                return "spell activated";
            } else {
                if (!SpellEffectCanActivate.getInstance().checkSpellPossibility(effectCard.getName())) {
                    return "preparations of this spell are not done yet";
                }
                effectCard.setFacedUp(true);
                if (((SpellCard) effectCard).getIcon().equals("Quick-play")) {
                    TrapEffectActivate.getInstance().trapAndQuickSpellCaller(effectCard.getName());
                    effectCard.setFacedUp(true);
                    return "spell activated";
                } else {
                    SpellEffectActivate.getInstance().spellCaller(effectCard.getName());
                    effectCard.setFacedUp(true);
                    return "spell activated";
                }
            }
        }
    }

    private void getRidOfTrapOrQuickPlaySpell(Card card) {
        if (!card.getName().equals("Call of the Haunted")) {
            HashMap<Integer, Card> spellAndTrapTerritory = duelWithUser.getMyBoard().getSpellAndTrapTerritory();
            for (int i = 1; i < 6; i++) {
                if (spellAndTrapTerritory.get(i) == card) {
                    spellAndTrapTerritory.put(i, null);
                }
            }
            duelWithUser.getMyBoard().getGraveyard().add(card);
        }
    }

    private boolean isMySpellAndTrapTerritoryFull() {
        for (int i = 1; i < 6; i++) {
            if (duelWithUser.getMyBoard().getSpellAndTrapTerritory().get(i) == null) {
                return false;
            }
        }
        return true;
    }

    private boolean isSpellInMyHand() {
        ArrayList<Card> playerHand = duelWithUser.getMyBoard().getPlayerHand();
        for (Card card : playerHand) {
            if (card == effectCard) {
                return true;
            }
        }
        return false;
    }

    private String beastKingBarbaros(boolean isItset) {
        effectView.output("choose on option from the list below");
        effectView.output("no tribute");
        effectView.output("two tributes");
        effectView.output("three tributes");
        String input = effectView.input();
        MonsterCard monsterCard = (MonsterCard) duelWithUser.getMyBoard().getSelectedCard();
        switch (input) {
            case "no tribute":
                monsterCard.setAttack(1900);
                summon(monsterCard, false, isItset);
                return "summoned successfully";
            case "two tributes":
                return summonBeastKingBarbarosWithTwoTribute(monsterCard, isItset);
            case "three tributes":
                return summoneBeastKingBarbarosWithThreeTribute(monsterCard);
            default:
                return Output.InvalidCommand.toString();
        }
    }

    private String summonBeastKingBarbarosWithTwoTribute(MonsterCard monsterCard, boolean isItSet) {
        if (!areThereEnoughCardsToTribute(2)) {
            return "there is no way you could special summon a monster";
        }
        int firstAddress = effectView.getAddress();
        if (firstAddress < 1 || firstAddress > 5) {
            return Output.InvalidSelection.toString();
        }
        int secondAddress = effectView.getAddress();
        if (secondAddress < 1 || secondAddress > 5) {
            return Output.InvalidSelection.toString();
        }
        if (firstAddress == secondAddress) {
            return Output.InvalidSelection.toString();
        }
        if (!isAddressValid(firstAddress) || !isAddressValid(secondAddress)) {
            return "there is no monster on one of these addresses";
        }
        tribute(firstAddress);
        tribute(secondAddress);
        summon(monsterCard, false, isItSet);
        return Output.SummonedSuccessfully.toString();
    }

    private String summoneBeastKingBarbarosWithThreeTribute(MonsterCard monsterCard) {
        if (!areThereEnoughCardsToTribute(3)) {
            return "there is no way you could summon this monster";
        }
        int firstAddress = effectView.getAddress();
        if (firstAddress < 1 || firstAddress > 5) {
            return Output.InvalidSelection.toString();
        }
        int secondAddress = effectView.getAddress();
        if (secondAddress < 1 || secondAddress > 5) {
            return Output.InvalidSelection.toString();
        }
        int thirdAddress = effectView.getAddress();
        if (thirdAddress < 1 || thirdAddress > 5) {
            return Output.InvalidSelection.toString();
        }
        if (firstAddress == secondAddress || secondAddress == thirdAddress || thirdAddress == firstAddress) {
            return Output.InvalidSelection.toString();
        }
        if (!isAddressValid(firstAddress) || !isAddressValid(secondAddress) || !isAddressValid(thirdAddress)) {
            return "there is no monster on one of these addresses";
        }
        tribute(firstAddress);
        tribute(secondAddress);
        tribute(thirdAddress);
        summon(monsterCard, false, false);
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getEnemyBoard().getMonsterTerritory();
        for (int i = 1; i < 6; i++) {
            if (monsterTerritory.get(i) != null) {
                duelWithUser.afterDeathEffect(2, monsterTerritory.get(i));
            }
        }
        return Output.SummonedSuccessfully.toString();
    }

    public boolean isSummoningInProcess() {
        return isSummoningInProcess;
    }

}
