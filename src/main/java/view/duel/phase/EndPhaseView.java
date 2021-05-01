package view.duel.phase;

public class EndPhaseView {
    private static EndPhaseView endPhase;

    private EndPhaseView() {

    }

    public static EndPhaseView getInstance() {
        if (endPhase == null) {
            endPhase = new EndPhaseView();
        }
        return endPhase;
    }

    public void run(){

    }
}
