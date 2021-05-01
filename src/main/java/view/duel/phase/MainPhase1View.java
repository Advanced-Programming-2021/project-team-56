package view.duel.phase;

public class MainPhase1View {
    private static MainPhase1View mainPhase1;

    private MainPhase1View() {

    }

    public static MainPhase1View getInstance() {
        if (mainPhase1 == null) {
            mainPhase1 = new MainPhase1View();
        }
        return mainPhase1;
    }

    public void run(){

    }
}
