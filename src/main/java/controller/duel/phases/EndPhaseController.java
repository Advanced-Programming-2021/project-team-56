package controller.duel.phases;

public class EndPhaseController {
    private static EndPhaseController endPhase;

    private EndPhaseController() {

    }

    public static EndPhaseController getInstance() {
        if (endPhase == null) {
            endPhase = new EndPhaseController();
        }
        return endPhase;
    }
}
