package view.duel.phase;

public class StandByPhaseView {
    private static StandByPhaseView standByPhase;

    private StandByPhaseView() {

    }

    public static StandByPhaseView getInstance() {
        if (standByPhase == null) {
            standByPhase = new StandByPhaseView();
        }
        return standByPhase;
    }

    public void run(){

    }
}
