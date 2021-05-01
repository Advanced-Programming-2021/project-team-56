package view.duel.phase;

import controller.duel.DuelWithUser;
import controller.duel.phases.DrawPhaseController;
import view.LoginMenuView;

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

    public void run() {
        System.out.println("phase: End Phase");
        while (true) {
            String command = LoginMenuView.scan.nextLine().trim();
            if (command.equals("next phase")) {
                break;
            }
            System.out.println("invalid command");
        }
        String nickname = DuelWithUser.getInstance().getEnemyBoard().getUser().getNickname();
        System.out.println("its " + nickname + "’s turn");
    }
}
