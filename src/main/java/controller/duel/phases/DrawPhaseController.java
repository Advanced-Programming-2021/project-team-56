package controller.duel.phases;

public class DrawPhaseController {
    private static DrawPhaseController drawPhase;

    private DrawPhaseController() {
    }

    public static DrawPhaseController getInstance() {
        if (drawPhase == null) {
            drawPhase = new DrawPhaseController();
        }
        return drawPhase;
    }
}
