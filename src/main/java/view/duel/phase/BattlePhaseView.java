package view.duel.phase;

import controller.duel.DuelWithUser;
import controller.duel.phases.BattlePhaseController;
import view.LoginMenuView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BattlePhaseView {
    private static BattlePhaseView battlePhase;
    static Pattern attack = Pattern.compile("^attack (\\d+)$");
    private DuelWithUser duelWithUser = DuelWithUser.getInstance();

    private BattlePhaseView() {

    }

    public static BattlePhaseView getInstance() {
        if (battlePhase == null) {
            battlePhase = new BattlePhaseView();
        }
        return battlePhase;
    }

    public String run() {
        System.out.println("phase: battle phase");
        while (true) {
            BattlePhaseController.getInstance().afterBattleEffects();
            String command = LoginMenuView.scan.nextLine().trim();
            if (command.equals("next phase")) {
                break;
            }
            if (command.equals("select -d")) {
                System.out.println(duelWithUser.deselectCard());
                continue;
            }
            if (command.startsWith("select")) {
                System.out.println(duelWithUser.selectCard(command));
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
            if (command.equals("activate effect")) {
                System.out.println("you can’t activate an effect on this turn");
                continue;
            }
            Matcher matcher = attack.matcher(command);
            BattlePhaseController battlePhase = BattlePhaseController.getInstance();
            if (matcher.find()) {
                System.out.println(battlePhase.attackCard(Integer.parseInt(matcher.group(1))));
                System.out.print(duelWithUser.showField());
                if (isGameOver()) {
                    if (duelWithUser.getMyBoard().getLP() <= 0) {
                        return "I lost";
                    } else {
                        return "I won";
                    }
                }
                continue;
            }
            if (command.equals("attack direct")) {
                System.out.println(battlePhase.attackUser());
                System.out.print(duelWithUser.showField());
                if (isGameOver()) {
                    if (duelWithUser.getMyBoard().getLP() <= 0) {
                        return "I lost";
                    } else {
                        return "I won";
                    }
                }
                continue;
            }
            if (command.equals("surrender")) {
                return "I lost";
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
        return "the game continues";
    }

    private boolean isGameOver() {
        DuelWithUser duelWithUser = DuelWithUser.getInstance();
        if (duelWithUser.getEnemyBoard().getLP() <= 0 || duelWithUser.getMyBoard().getLP() <= 0) {
            return true;
        }
        return false;
    }
}
