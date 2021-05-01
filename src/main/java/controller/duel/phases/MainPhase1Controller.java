package controller.duel.phases;

public class MainPhase1Controller {
    private static MainPhase1Controller mainPhase1;

    private MainPhase1Controller() {

    }

    public static MainPhase1Controller getInstance() {
        if (mainPhase1 == null) {
            mainPhase1 = new MainPhase1Controller();
        }
        return mainPhase1;
    }

}
