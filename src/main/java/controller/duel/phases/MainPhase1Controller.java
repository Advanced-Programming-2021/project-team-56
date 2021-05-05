package controller.duel.phases;

import controller.duel.DuelWithUser;
import model.Card;
import model.MonsterCard;
import view.LoginMenuView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainPhase1Controller {
    private static MainPhase1Controller mainPhase1;


    private MainPhase1Controller() {

    }

    public static MainPhase1Controller getInstance() {
        if (mainPhase1 == null) {
            mainPhase1 = new MainPhase1Controller();
        }
        return mainPhase1;
    }

    public String summon() {
        DuelWithUser duelWithUser = DuelWithUser.getInstance();
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
        if (monsterCard.getLevel() < 5) {
            normalSummon(monsterCard);
            return "summoned successfully";
        }
        if (!areThereEnoughCardsToTribute()) {
            return "there are not enough cards for tribute";
        }
        if (monsterCard.getLevel() < 7) {
            int firstAddress = Integer.parseInt(LoginMenuView.scan.nextLine().trim());
            if (firstAddress < 1 || firstAddress > 5){
                return "invalid selection";
            }
            if (!isAddressValid(firstAddress)) {
                return "there no monsters on this address";
            } else {
                tribute(firstAddress);
                normalSummon(monsterCard);
                return "summoned successfully";
            }
        } else {
            int firstAddress = Integer.parseInt(LoginMenuView.scan.nextLine().trim());
            if (firstAddress < 1 || firstAddress > 5){
                return "invalid selection";
            }
            int secondAddress = Integer.parseInt(LoginMenuView.scan.nextLine().trim());
            if (secondAddress < 1 || secondAddress > 5){
                return "invalid selection";
            }
            if (firstAddress == secondAddress) {
                return "there is no monster on one of these addresses";
            }
            if (isAddressValid(firstAddress) && isAddressValid(secondAddress)) {
                tribute(firstAddress);
                tribute(secondAddress);
                normalSummon(monsterCard);
                return "summoned successfully";
            } else {
                return "there is no monster on one of these addresses";
            }
        }
    }

    public String set() {
        DuelWithUser duelWithUser = DuelWithUser.getInstance();
        if (duelWithUser.getMyBoard().getSelectedCard() == null) {
            return "no card is selected yet";
        }
        Card card = duelWithUser.getMyBoard().getSelectedCard();
        if (card instanceof MonsterCard) {
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
        DuelWithUser duelWithUser = DuelWithUser.getInstance();
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
        if (duelWithUser.getTurnCounter() == monsterCard.getLastTimeChangedPositionTurn()) {
            return "you already changed this card position in this turn";
        }
        changePosition(true);
        return "monster card position changed successfully";
    }

    public String changeToDefencePosition() {
        DuelWithUser duelWithUser = DuelWithUser.getInstance();
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
        DuelWithUser duelWithUser = DuelWithUser.getInstance();
        MonsterCard monsterCard = (MonsterCard) duelWithUser.getMyBoard().getSelectedCard();
        monsterCard.setInAttackPosition(position);
        monsterCard.setLastTimeChangedPositionTurn(duelWithUser.getTurnCounter());
        duelWithUser.getMyBoard().setSelectedCard(null);
    }

    public String flipSummon() {
        DuelWithUser duelWithUser = DuelWithUser.getInstance();
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
        monsterCard.setFacedUp(true);
        monsterCard.setSummonedTurn(duelWithUser.getTurnCounter());
        duelWithUser.getMyBoard().setSelectedCard(null);
        return "flip summoned successfully";
    }

    private boolean isMonsterTerritoryFull() {
        for (int i = 1; i < 6; i++) {
            if (DuelWithUser.getInstance().getMyBoard().getMonsterTerritory().get(i) == null) {
                return false;
            }
        }
        return true;
    }

    private boolean areThereEnoughCardsToTribute() {
        MonsterCard monster = (MonsterCard) DuelWithUser.getInstance().getMyBoard().getSelectedCard();
        int tributes;
        if (monster.getLevel() > 6) {
            tributes = 2;
        } else {
            tributes = 1;
        }
        for (int i = 1; i < 6; i++) {
            if (DuelWithUser.getInstance().getMyBoard().getMonsterTerritory().get(i) != null) {
                tributes--;
            }
        }
        if (tributes <= 0) {
            return true;
        }
        return false;
    }

    private boolean canThisCardBeSummoned() {
        Card card = DuelWithUser.getInstance().getMyBoard().getSelectedCard();
        if (!isCardInMyHand(card)) {
            return false;
        }
        if (!(card instanceof MonsterCard)) {
            return false;
        }
        //todo
        return true;
    }

    private boolean isCardInMyHand(Card card) {
        ArrayList<Card> playerHand = DuelWithUser.getInstance().getMyBoard().getPlayerHand();
        for (int i = 0; i < playerHand.size(); i++) {
            if (playerHand.get(i) == card) {
                return true;
            }
        }
        return false;
    }

    private void placeMonsterOnTheField(MonsterCard monsterCard) {
        HashMap<Integer, MonsterCard> monsterTerritory = DuelWithUser.getInstance().getMyBoard().getMonsterTerritory();
        for (int i = 1; i < 6; i++) {
            if (monsterTerritory.get(i) == null) {
                monsterTerritory.put(i, monsterCard);
                return;
            }
        }
    }

    private void drawCardFromPlayerHand(MonsterCard monsterCard) {
        ArrayList<Card> playerHand = DuelWithUser.getInstance().getMyBoard().getPlayerHand();
        for (int i = 0; i < playerHand.size(); i++) {
            if (playerHand.get(i) == monsterCard) {
                playerHand.remove(i);
                return;
            }
        }
    }

    private void normalSummon(MonsterCard monsterCard) {
        DuelWithUser duelWithUser = DuelWithUser.getInstance();
        placeMonsterOnTheField(monsterCard);
        drawCardFromPlayerHand(monsterCard);
        duelWithUser.getMyBoard().setSelectedCard(null);
        duelWithUser.getMyBoard().setLastSummonedOrSetTurn(duelWithUser.getTurnCounter());
        monsterCard.setSummonedTurn(duelWithUser.getTurnCounter());
        monsterCard.setInAttackPosition(true);
        monsterCard.setFacedUp(true);
    }

    private boolean isAddressValid(int address) {
        DuelWithUser duelWithUser = DuelWithUser.getInstance();
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        if (monsterTerritory.get(address) != null) {
            return true;
        } else {
            return false;
        }
    }

    private void tribute(int address) {
        DuelWithUser duelWithUser = DuelWithUser.getInstance();
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        ArrayList<Card> graveyard = duelWithUser.getMyBoard().getGraveyard();
        graveyard.add(monsterTerritory.get(address));
        monsterTerritory.put(address, null);
    }

    public boolean isCardInMyMonsterTerritory() {
        DuelWithUser duelWithUser = DuelWithUser.getInstance();
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        Card card = duelWithUser.getMyBoard().getSelectedCard();
        for (int i = 1; i < 6; i++) {
            if (monsterTerritory.get(i) == card) {
                return true;
            }
        }
        return false;
    }

}
