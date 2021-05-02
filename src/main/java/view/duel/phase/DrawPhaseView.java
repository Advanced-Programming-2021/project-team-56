package view.duel.phase;

import controller.duel.phases.DrawPhaseController;
import view.LoginMenuView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DrawPhaseView {
    private static DrawPhaseView drawPhase;
    static Pattern setPosition = Pattern.compile("^set -- position (attack|defence)$");
    static Pattern attack = Pattern.compile("^attack (\\d+)$");

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
            if (command.equals("summon")) {
                System.out.println("action not allowed in this phase");
                continue;
            }
            if (command.equals("set")) {
                System.out.println("you can’t do this action in this phase");
                continue;
            }
            Matcher matcher = setPosition.matcher(command);
            if (matcher.find()){
                System.out.println("you can’t do this action in this phase");
                continue;
            }
            if (command.equals("flip-summon")){
                System.out.println("you can’t do this action in this phase");
                continue;
            }
            matcher = attack.matcher(command);
            if (matcher.find()){
                System.out.println("you can’t do this action in this phase");
                continue;
            }
            if (command.equals("attack direct")){
                System.out.println("you can’t do this action in this phase");
                continue;
            }
            System.out.println("invalid command");
        }
        return true;
    }
}
