package view.duel.phase;

public class MainPhase2View {
    private static MainPhase2View mainPhase2;

    private MainPhase2View() {

    }

    public static MainPhase2View getInstance() {
        if (mainPhase2 == null) {
            mainPhase2 = new MainPhase2View();
        }
        return mainPhase2;
    }

    public void run(){

    }

}
