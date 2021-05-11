package view.duel.phase;

import controller.duel.phases.StandByPhaseController;
import view.LoginMenuView;

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

    public void run() {
        System.out.println("phase: standby phase view");
        StandByPhaseController.getInstance().run();
        while (true) {
            String command = LoginMenuView.scan.nextLine().trim();
            if (command.equals("activate effect")) {
                System.out.println("you can’t activate an effect on this turn");
                continue;
            }
        }
    }
}
