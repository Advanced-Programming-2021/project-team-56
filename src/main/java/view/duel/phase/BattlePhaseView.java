package view.duel.phase;

public class BattlePhaseView {
    private static BattlePhaseView battlePhase;

    private BattlePhaseView() {

    }

    public static BattlePhaseView getInstance() {
        if (battlePhase == null) {
            battlePhase = new BattlePhaseView();
        }
        return battlePhase;
    }

    public void run(){

    }
}
