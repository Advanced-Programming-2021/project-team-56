package view.duel.phase;

import controller.duel.DuelWithUser;
import controller.duel.phases.DrawPhaseController;
import view.LoginMenuView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static view.duel.phase.BattlePhaseView.increaseLP;
import static view.duel.phase.BattlePhaseView.setWinner;

public class DrawPhaseView {
    private static DrawPhaseView drawPhase;
    static Pattern attack = Pattern.compile("^attack (\\d+)$");
    static Pattern forceDraw1 = Pattern.compile("^select --hand ([\\S][\\S ]*) --force$");
    static Pattern forceDraw2 = Pattern.compile("^select --force --hand ([\\S][\\S ]*)$");

    private DrawPhaseView() {

    }

    public static DrawPhaseView getInstance() {
        if (drawPhase == null) {
            drawPhase = new DrawPhaseView();
        }
        return drawPhase;
    }

    public String run() {
        DuelWithUser duelWithUser = DuelWithUser.getInstance();
        System.out.println("phase: draw phase");
        String result = DrawPhaseController.getInstance().run();
        System.out.println(result);
        System.out.print(duelWithUser.showField());
        if (result.equals("No cards is in your deck")) {
            return "I lost";
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
            if (command.equals("set --position attack") || command.equals("set --position defence")) {
                System.out.println("you can’t do this action in this phase");
                continue;
            }
            if (command.equals("flip-summon")) {
                System.out.println("you can’t do this action in this phase");
                continue;
            }
            if (command.equals("activate effect")) {
                //TODO
                System.out.println("you can’t activate an effect on this turn");
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
                MainPhase1View.showGraveYardView();
                continue;
            }
            matcher = increaseLP.matcher(command);
            if (matcher.find()) {
                System.out.println(duelWithUser.increaseMyLP(matcher.group(1)));
            }
            matcher = setWinner.matcher(command);
            if (matcher.find()) {
                if (duelWithUser.isNicknameValid(matcher.group(1)).equals("yes")) {
                    return duelWithUser.setWinner(matcher.group(1));
                }
                System.out.println("invalid nickname");
                continue;
            }
            if (command.startsWith("select --hand") || command.startsWith("select --force")) {
                checkForceDrawCommand(command);
                continue;
            }
            System.out.println("invalid command");
        }
        return "the game continues";
    }

    private void checkForceDrawCommand(String command) {
        Matcher matcher = forceDraw1.matcher(command);
        if (matcher.find()) {
            System.out.println(DrawPhaseController.getInstance().forceDraw(matcher.group(1)));
            return;
        }
        matcher = forceDraw2.matcher(command);
        if (matcher.find()) {
            System.out.println(DrawPhaseController.getInstance().forceDraw(matcher.group(1)));
            return;
        }
        System.out.println("invalid command");
    }
}
