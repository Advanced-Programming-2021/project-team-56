package controller.duel.phases;

import controller.duel.DuelWithUser;
import model.Card;
import model.MonsterCard;

import java.util.HashMap;

public class BattlePhaseController {
    private static BattlePhaseController battlePhase;

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
        if (duelWithUser.getEnemyBoard().getMonsterTerritory().get(address) == null) {
            return "there is no card to attack here";
        }

        return "";
    }
}
