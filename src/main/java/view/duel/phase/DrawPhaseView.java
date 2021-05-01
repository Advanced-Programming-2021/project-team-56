package view.duel.phase;

import controller.duel.phases.DrawPhaseController;
import view.LoginMenuView;

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

    public boolean run() {
        System.out.println("phase: draw phase");
        String result = DrawPhaseController.getInstance().run();
        System.out.println(result);
        if (result.equals("No cards is in your deck")) {
            return false;
        }
        while (true) {
            String command = LoginMenuView.scan.nextLine().trim();
            if (command.equals("next phase")) {
                break;
            }
            System.out.println("invalid command");
        }
        return true;
    }
}
