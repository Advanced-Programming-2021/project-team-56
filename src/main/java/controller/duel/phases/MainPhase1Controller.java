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
            normalSummon(monsterCard, duelWithUser);
            return "summoned successfully";
        }
        if (!areThereEnoughCardsToTribute()) {
            return "there are not enough cards for tribute";
        }
        if (monsterCard.getLevel() < 7) {
            int firstAddress = Integer.parseInt(LoginMenuView.scan.nextLine().trim());
            if (!isAddressValid(firstAddress, duelWithUser)) {
                return "there no monsters one this address";
            } else {
                normalSummon(monsterCard, duelWithUser);
                return "summoned successfully";
            }
        } else {
            int firstAddress = Integer.parseInt(LoginMenuView.scan.nextLine().trim());
            int secondAddress = Integer.parseInt(LoginMenuView.scan.nextLine().trim());
            if (firstAddress == secondAddress) {
                return "there is no monster on one of these addresses";
            }
            if (isAddressValid(firstAddress, duelWithUser) && isAddressValid(secondAddress, duelWithUser)) {
                tribute(duelWithUser, firstAddress);
                tribute(duelWithUser, secondAddress);
                normalSummon(monsterCard, duelWithUser);
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
        }else {
            if (isMonsterTerritoryFull()) {
                return "monster card zone is full";
            }
        }
        return "";
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
            if (playerHand.get(i).getId() == card.getId()) {
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
            if (playerHand.get(i).getId() == monsterCard.getId()) {
                playerHand.remove(i);
                return;
            }
        }
    }

    private void normalSummon(MonsterCard monsterCard, DuelWithUser duelWithUser) {
        placeMonsterOnTheField(monsterCard);
        drawCardFromPlayerHand(monsterCard);
        duelWithUser.getMyBoard().setSelectedCard(null);
        duelWithUser.getMyBoard().setLastSummonedOrSetTurn(duelWithUser.getTurnCounter());
        monsterCard.setSummonedTurn(duelWithUser.getTurnCounter());
        monsterCard.setInAttackPosition(true);
        monsterCard.setFacedUp(true);
    }

    private boolean isAddressValid(int address, DuelWithUser duelWithUser) {
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        for (int i = 1; i < 5; i++) {
            if (i == address) {
                if (monsterTerritory.get(i) == null) {
                    return false;
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    private void tribute(DuelWithUser duelWithUser, int address) {
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        ArrayList<Card> graveyard = duelWithUser.getMyBoard().getGraveyard();
        graveyard.add(monsterTerritory.get(address));
        monsterTerritory.put(address, null);
    }

}
