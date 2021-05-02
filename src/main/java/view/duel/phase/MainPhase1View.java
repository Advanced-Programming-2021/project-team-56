package view.duel.phase;

import controller.duel.phases.MainPhase1Controller;
import view.LoginMenuView;

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

    public void run() {
        System.out.println("phase: Main Phase 1");
        while (true){
            String command = LoginMenuView.scan.nextLine().trim();
            if (command.equals("next phase")) {
                break;
            }
            if (command.equals("summon")){
                System.out.println(MainPhase1Controller.getInstance().summon());
                continue;
            }
            if (command.equals("set")){
                System.out.println(MainPhase1Controller.getInstance().set());
                continue;
            }
        }
    }
}
