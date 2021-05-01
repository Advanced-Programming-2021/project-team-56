package controller.duel.phases;

public class StandByPhaseController {
    private static StandByPhaseController standByPhase;

    private StandByPhaseController() {

    }

    public static StandByPhaseController getInstance() {
        if (standByPhase == null) {
            standByPhase = new StandByPhaseController();
        }
        return standByPhase;
    }
}
