package view.duel.phase;

public class DrawPhaseView {
    private static DrawPhaseView drawPhase;

    private DrawPhaseView() {

    }

    public static DrawPhaseView getInstance() {
        if (drawPhase == null) {
            drawPhase = new DrawPhaseView();
        }
        return drawPhase;
    }

    public void run(){

    }
}
