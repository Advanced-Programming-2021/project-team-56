package controller.duel.phases;

public class MainPhase2Controller {
    private static MainPhase2Controller mainPhase2;

    private MainPhase2Controller() {

    }

    public static MainPhase2Controller getInstance() {
        if (mainPhase2 == null) {
            mainPhase2 = new MainPhase2Controller();
        }
        return mainPhase2;
    }
}
