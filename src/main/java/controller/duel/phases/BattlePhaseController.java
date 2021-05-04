package controller.duel.phases;

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

}
