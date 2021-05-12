package view.duel.phase;

import controller.duel.DuelWithUser;
import controller.duel.phases.StandByPhaseController;
import view.LoginMenuView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StandByPhaseView {

    private static StandByPhaseView standByPhase;
    static Pattern setPosition = Pattern.compile("^set --position (attack|defence)$");
    static Pattern attack = Pattern.compile("^attack (\\d+)$");



    private StandByPhaseView() {

    }

    public static StandByPhaseView getInstance() {
        if (standByPhase == null) {
            standByPhase = new StandByPhaseView();
        }
        return standByPhase;
    }

    public String run() {
        DuelWithUser duelWithUser = DuelWithUser.getInstance();
        System.out.println("phase: standby phase view");
        String result = StandByPhaseController.getInstance().run();
        if (!result.equals("the game continuous")){
            return result;
        }
        while (true) {
            String command = LoginMenuView.scan.nextLine().trim();
            if (command.equals("next phase")) {
                break;
            }
            if (command.equals("activate effect")) {
                //TODO QuickPlay Spells and TrapCards
                System.out.println("you can’t activate an effect on this turn");
                continue;
            }
            if (command.equals("summon")) {
                System.out.println("action not allowed in this phase");
                continue;
            }
            if (command.equals("set")) {
                System.out.println("you can’t do this action in this phase");
                continue;
            }
            if (command.equals("set --position attack") || command.equals("set --position defence")) {
                System.out.println("you can’t do this action in this phase");
                continue;
            }
            if (command.equals("flip-summon")) {
                System.out.println("you can’t do this action in this phase");
                continue;
            }
            Matcher matcher = attack.matcher(command);
            if (matcher.find()) {
                System.out.println("you can’t do this action in this phase");
                continue;
            }
            if (command.equals("attack direct")) {
                System.out.println("you can’t do this action in this phase");
                continue;
            }
            if (command.equals("surrender")) {
                return "I lost";
            }
            if (command.equals("select -d")) {
                System.out.println(duelWithUser.deselectCard());
                continue;
            }
            if (command.startsWith("select")) {
                System.out.println(duelWithUser.selectCard(command));
                continue;
            }
            if (command.equals("card show --selected")) {
                System.out.println(duelWithUser.showSelectedCard());
                continue;
            }
            if (command.equals("show graveyard")) {
                System.out.print(duelWithUser.showGraveYard());
                continue;
            }
            System.out.println("invalid command");
        }
        return "the game continuous";
    }
}
