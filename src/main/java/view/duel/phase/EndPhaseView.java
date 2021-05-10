package view.duel.phase;

import controller.duel.DuelWithUser;
import view.LoginMenuView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EndPhaseView {
    private static EndPhaseView endPhase;
    static Pattern setPosition = java.util.regex.Pattern.compile("^set -- position (attack|defence)$");
    static Pattern attack = Pattern.compile("^attack (\\d+)$");

    private EndPhaseView() {

    }

    public static EndPhaseView getInstance() {
        if (endPhase == null) {
            endPhase = new EndPhaseView();
        }
        return endPhase;
    }

    public void run() {
        DuelWithUser duelWithUser = DuelWithUser.getInstance();
        System.out.println("phase: End Phase");
        while (true) {
            String command = LoginMenuView.scan.nextLine().trim();
            if (command.equals("next phase")) {
                break;
            }
            if (command.equals("activate effect")) {
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
            Matcher matcher = setPosition.matcher(command);
            if (matcher.find()) {
                System.out.println("you can’t do this action in this phase");
                continue;
            }
            if (command.equals("flip-summon")) {
                System.out.println("you can’t do this action in this phase");
                continue;
            }
            matcher = attack.matcher(command);
            if (matcher.find()) {
                System.out.println("you can’t do this action in this phase");
                continue;
            }
            if (command.equals("attack direct")) {
                System.out.println("you can’t do this action in this phase");
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
        String nickname = duelWithUser.getEnemyBoard().getUser().getNickname();
        System.out.println("its " + nickname + "’s turn");
    }
}
