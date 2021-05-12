package controller.duel.phases;

import controller.duel.DuelWithUser;
import controller.duel.effects.SpellEffectActivate;
import model.Card;
import model.MonsterCard;
import model.SpellCard;
import view.duel.EffectView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainPhase1Controller {
    private static MainPhase1Controller mainPhase1;
    private DuelWithUser duelWithUser = DuelWithUser.getInstance();
    private EffectView effectView = EffectView.getInstance();
    private BattlePhaseController battlePhaseController = BattlePhaseController.getInstance();
    private SpellEffectActivate spellEffectActivate = SpellEffectActivate.getInstance();


    private MainPhase1Controller() {

    }

    public static MainPhase1Controller getInstance() {
        if (mainPhase1 == null) {
            mainPhase1 = new MainPhase1Controller();
        }
        return mainPhase1;
    }

    public String summon() {
        if (duelWithUser.getMyBoard().getSelectedCard() == null) {
            return "no card is selected yet";
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
            monsterCard.setAttack(1900);
            summon(monsterCard, false);
            return "summoned successfully";
        }
        if (monsterCard.getName().equals("Terratiger, the Empowered Warrior")) {
            terraTigerTheEmpoweredWarrior();
            return "summoned successfully";
        }
        if (monsterCard.getLevel() < 5) {
            summon(monsterCard, false);
            return "summoned successfully";
        }
        if (monsterCard.getLevel() < 7) {
            if (!areThereEnoughCardsToTribute(1)) {
                return "there are not enough cards for tribute";
            }
            int firstAddress = effectView.getAddress();
            if (firstAddress < 1 || firstAddress > 5) {
                return "invalid selection";
            }
            if (!isAddressValid(firstAddress)) {
                return "there no monsters on this address";
            } else {
                tribute(firstAddress);
                summon(monsterCard, false);
                return "summoned successfully";
            }
        } else {
            if (!areThereEnoughCardsToTribute(2)) {
                return "there are not enough cards for tribute";
            }
            int firstAddress = effectView.getAddress();
            if (firstAddress < 1 || firstAddress > 5) {
                return "invalid selection";
            }
            int secondAddress = effectView.getAddress();
            if (secondAddress < 1 || secondAddress > 5) {
                return "invalid selection";
            }
            if (firstAddress == secondAddress) {
                return "there is no monster on one of these addresses";
            }
            if (isAddressValid(firstAddress) && isAddressValid(secondAddress)) {
                tribute(firstAddress);
                tribute(secondAddress);
                summon(monsterCard, false);
                return "summoned successfully";
            } else {
                return "there is no monster on one of these addresses";
            }
        }
    }

    public String set() {
        if (duelWithUser.getMyBoard().getSelectedCard() == null) {
            return "no card is selected yet";
        }
        Card card = duelWithUser.getMyBoard().getSelectedCard();
        if (card instanceof MonsterCard) {
            MonsterCard monsterCard = (MonsterCard) card;
            if (isMonsterTerritoryFull()) {
                return "monster card zone is full";
            }
            if (duelWithUser.getTurnCounter() == duelWithUser.getLastSummonedOrSetTurn()) {
                return "you already summoned/set on this turn";
            }
        } else {
            if (isMonsterTerritoryFull()) {
                return "monster card zone is full";
            }
        }
        return "";
    }

    public String changeToAttackPosition() {
        if (duelWithUser.getMyBoard().getSelectedCard() == null) {
            return "no card is selected yet";
        }
        if (!isCardInMyMonsterTerritory()) {
            return "you can’t change this card position";
        }
        MonsterCard monsterCard = (MonsterCard) duelWithUser.getMyBoard().getSelectedCard();
        if (monsterCard.getIsInAttackPosition()) {
            return "this card is already in the wanted position";
        }
        if (!monsterCard.getIsFacedUp()){
            return "this card is faced down so you can't change its position";
        }
        if (duelWithUser.getTurnCounter() == monsterCard.getLastTimeChangedPositionTurn()) {
            return "you already changed this card position in this turn";
        }
        changePosition(true);
        return "monster card position changed successfully";
    }

    public String changeToDefencePosition() {
        if (duelWithUser.getMyBoard().getSelectedCard() == null) {
            return "no card is selected yet";
        }
        if (!isCardInMyMonsterTerritory()) {
            return "you can’t change this card position";
        }
        MonsterCard monsterCard = (MonsterCard) duelWithUser.getMyBoard().getSelectedCard();
        if (!monsterCard.getIsInAttackPosition()) {
            return "this card is already in the wanted position";
        }
        if (duelWithUser.getTurnCounter() == monsterCard.getLastTimeChangedPositionTurn()) {
            return "you already changed this card position in this turn";
        }
        if (duelWithUser.getTurnCounter() == monsterCard.getLastTimeAttackedTurn()) {
            return "you already attacked with this card in this turn";
        }
        changePosition(false);
        return "monster card position changed successfully";
    }

    private void changePosition(boolean position) {
        MonsterCard monsterCard = (MonsterCard) duelWithUser.getMyBoard().getSelectedCard();
        monsterCard.setInAttackPosition(position);
        monsterCard.setLastTimeChangedPositionTurn(duelWithUser.getTurnCounter());
        duelWithUser.getMyBoard().setSelectedCard(null);
    }

    public String flipSummon() {
        if (duelWithUser.getMyBoard().getSelectedCard() == null) {
            return "no card is selected yet";
        }
        if (!isCardInMyMonsterTerritory()) {
            return "you can’t change this card position";
        }
        MonsterCard monsterCard = (MonsterCard) duelWithUser.getMyBoard().getSelectedCard();
        if (monsterCard.getSummonedTurn() == duelWithUser.getTurnCounter()) {
            return "you can’t flip summon this card";
        }
        if (monsterCard.getIsFacedUp()) {
            return "you can’t flip summon this card";
        }
        if (monsterCard.getName().equals("Man-Eater Bug")) {
            battlePhaseController.manEaterBugEffect(false);
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
        if (tributes <= 0) {
            return true;
        }
        return false;
    }

    private boolean canThisCardBeSummoned() {
        Card card = duelWithUser.getMyBoard().getSelectedCard();
        if (!isCardInMyHand(card)) {
            return false;
        }
        if (!(card instanceof MonsterCard)) {
            return false;
        }
        return true;
    }

    private boolean canThisCardBeNormalSummoned(String name) {
        if (name.equals("Gate Guardian")) {
            return false;
        }
        if (name.equals("Skull Guardian")) {
            return false;
        }
        if (name.equals("Crab Turtle")) {
            return false;
        }
        return true;
    }

    public String specialSummon() {
        Card card = duelWithUser.getMyBoard().getSelectedCard();
        if (card == null) {
            return "no card is selected yet";
        }
        if (!canThisCardBeSummoned()) {
            return "this card can't be special summoned";
        }
        MonsterCard monsterCard = (MonsterCard) card;
        if (monsterCard.getName().equals("The Tricky")) {
            return specialSummonTheTricky();
        }
        if (monsterCard.getName().equals("Gate Guardian")) {
            return specialSummonGateGuardian();
        }
        return "";
    }

    private String specialSummonGateGuardian() {
        if (!areThereEnoughCardsToTribute(3)) {
            return "there is no way you could special summon a monster";
        }
        int firstAddress = effectView.getAddress();
        if (firstAddress < 1 || firstAddress > 5) {
            return "invalid selection";
        }
        int secondAddress = effectView.getAddress();
        if (secondAddress < 1 || secondAddress > 5) {
            return "invalid selection";
        }
        int thirdAddress = effectView.getAddress();
        if (thirdAddress < 1 || thirdAddress > 5) {
            return "invalid selection";
        }
        if (firstAddress == secondAddress || secondAddress == thirdAddress || thirdAddress == firstAddress) {
            return "invalid selection";
        }
        tribute(firstAddress);
        tribute(secondAddress);
        tribute(thirdAddress);
        summon((MonsterCard) duelWithUser.getMyBoard().getSelectedCard(), true);
        return "special summon of Gate Guardian was successful";
    }

    private String specialSummonTheTricky() {
        if (!isThereAnyOtherCardOnHand()) {
            return "there is no way you could special summon a monster";
        }
        int address = effectView.getAddress();
        if (address > duelWithUser.getMyBoard().getPlayerHand().size() || address < 1) {
            return "invalid selection";
        }
        if (!isItAnotherCard(address)) {
            return "there is no card on the address";
        }
        tributeFromHand(address);
        summon((MonsterCard) duelWithUser.getMyBoard().getSelectedCard(), true);
        return "special summon of The Tricky was successful";
    }

    private boolean isItAnotherCard(int address) {
        MonsterCard monsterCard = (MonsterCard) duelWithUser.getMyBoard().getSelectedCard();
        ArrayList<Card> playerHand = duelWithUser.getMyBoard().getPlayerHand();
        if (playerHand.get(address - 1) != monsterCard) {
            return true;
        }
        return false;
    }

    private boolean isThereAnyOtherCardOnHand() {
        MonsterCard monsterCard = (MonsterCard) duelWithUser.getMyBoard().getSelectedCard();
        ArrayList<Card> playerHand = duelWithUser.getMyBoard().getPlayerHand();
        for (int i = 0; i < playerHand.size(); i++) {
            if (playerHand.get(i) != monsterCard) {
                return true;
            }
        }
        return false;
    }

    private boolean isCardInMyHand(Card card) {
        ArrayList<Card> playerHand = duelWithUser.getMyBoard().getPlayerHand();
        for (int i = 0; i < playerHand.size(); i++) {
            if (playerHand.get(i) == card) {
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

    private void drawCardFromPlayerHand(MonsterCard monsterCard) {
        ArrayList<Card> playerHand = duelWithUser.getMyBoard().getPlayerHand();
        for (int i = 0; i < playerHand.size(); i++) {
            if (playerHand.get(i) == monsterCard) {
                playerHand.remove(i);
                return;
            }
        }
    }

    private void summon(MonsterCard monsterCard, boolean isSpecialSummon) {
        placeMonsterOnTheField(monsterCard);
        drawCardFromPlayerHand(monsterCard);
        duelWithUser.getMyBoard().setSelectedCard(null);
        monsterCard.setSummonedTurn(duelWithUser.getTurnCounter());
        monsterCard.setInAttackPosition(true);
        monsterCard.setFacedUp(true);
        if (!isSpecialSummon) {
            duelWithUser.getMyBoard().setLastSummonedOrSetTurn(duelWithUser.getTurnCounter());
        }
    }

    private boolean isAddressValid(int address) {
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        if (monsterTerritory.get(address) != null) {
            return true;
        } else {
            return false;
        }
    }

    private void tribute(int address) {
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        ArrayList<Card> graveyard = duelWithUser.getMyBoard().getGraveyard();
        graveyard.add(monsterTerritory.get(address));
        monsterTerritory.put(address, null);
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
                    effectView.output("invalid selection");
                } else {
                    if (playerHand.get(address) instanceof MonsterCard) {
                        MonsterCard monsterCard = (MonsterCard) playerHand.get(address);
                        if (monsterCard.getLevel() <= 4) {
                            summon(monsterCard, true);
                            monsterCard.setInAttackPosition(false);
                            effectView.output("special summon of" + monsterCard.getName() + "was successful");
                            return;
                        }
                    }
                    effectView.output("you can't special summon this card");
                }
            } else {
                effectView.output("invalid command");
            }
        }
    }

    private boolean canTigerEffectBeActivated() {
        ArrayList<Card> playerHand = duelWithUser.getMyBoard().getPlayerHand();
        for (int i = 0; i < playerHand.size(); i++) {
            if ((playerHand.get(i) instanceof MonsterCard)) {
                MonsterCard monsterCard = (MonsterCard) playerHand.get(i);
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
            return "no card is selected yet";
        }
        if (!(card instanceof SpellCard)) {
            return "activate effect is only for spell cards.";
        }
        SpellCard spellCard = (SpellCard) card;
        if (!spellCard.getIsFacedUp()) {
            return "you have already activated this card";
        }
        //till this line we check possibility of
        //activating spell card (generally)
        if (spellCard.getIcon().equals("Field")) {
            SpellCard fieldSpell = duelWithUser.getMyBoard().getFieldSpell();
            if (fieldSpell != null) {
                duelWithUser.getMyBoard().getGraveyard().add(fieldSpell);
            }
            duelWithUser.getMyBoard().setFieldSpell(spellCard);
            spellCard.setFacedUp(true);
            duelWithUser.getMyBoard().setSelectedCard(null);
            spellEffectActivate.spellAbsorption();
            return "spell activated";
        } else {
            if (isMySpellAndTrapTerritoryFull()) {
                return "spell card zone is full";
            } else {
                return spellEffectActivate.spellCaller(spellCard.getName());
            }
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
}
