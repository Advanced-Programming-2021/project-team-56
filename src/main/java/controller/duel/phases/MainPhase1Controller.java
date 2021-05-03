package controller.duel.phases;

import controller.duel.DuelWithUser;
import model.MonsterCard;

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
        if (isMonsterTerritoryFull()) {
            return "monster card zone is full";
        }
        if (duelWithUser.getTurnCounter() == duelWithUser.getLastSummonedOrSetTurn()) {
            return "you already summoned/set on this turn";
        }
        if (!areThereEnoughCardsToTribute()){
            return "there are not enough cards for tribute";
        }
        return "";
    }

    public String set(){
        DuelWithUser duelWithUser = DuelWithUser.getInstance();
        if (duelWithUser.getMyBoard().getSelectedCard() == null) {
            return "no card is selected yet";
        }
        if (isMonsterTerritoryFull()) {
            return "monster card zone is full";
        }
        if (duelWithUser.getTurnCounter() == duelWithUser.getLastSummonedOrSetTurn()) {
            return "you already summoned/set on this turn";
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
        if(monster.getLevel() > 6){
            tributes = 2;
        }else {
            tributes = 1;
        }
        for (int i = 1; i < 6; i++) {
            if (DuelWithUser.getInstance().getMyBoard().getMonsterTerritory().get(i) != null) {
                tributes--;
            }
        }
        if (tributes <= 0){
            return true;
        }
        return false;
    }
}
