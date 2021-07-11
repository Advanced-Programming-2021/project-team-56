package view.duel.phase;

import controller.duel.DuelWithUser;
import controller.duel.phases.DrawPhaseController;
import controller.duel.phases.OpponentPhase;
import model.Commands;
import model.Output;
import view.duel.DuelWithUserView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static view.duel.phase.BattlePhaseView.*;

public class DrawPhaseView {

    private static DrawPhaseView drawPhase;

    static Pattern forceDraw1 = Pattern.compile("^select --hand ([\\S][\\S ]*) --force$");
    static Pattern forceDraw2 = Pattern.compile("^select --force --hand ([\\S][\\S ]*)$");

    private DrawPhaseView() {

    }

    public static DrawPhaseView getInstance() {
        if (drawPhase == null)
            drawPhase = new DrawPhaseView();
        return drawPhase;
    }

    public String run() {
        DuelWithUser duelWithUser = DuelWithUser.getInstance();
        DuelWithUserView duelWithUserView = DuelWithUserView.getInstance();
        System.out.println("phase: draw phase");
        String result = "";
        System.out.println(result);
        System.out.print(duelWithUser.showField());
        if (result.equals("No cards is in your deck")) {
            return Output.ILost.toString();
        }
        while (true) {
//            String command = LoginMenuView.scan.nextLine().trim();
//            if (isItForceDrawCommand(command)) {
//                continue;
//            } else if (command.equals(Commands.NextPhase.toString())) {
//                break;
//            } else if (isThisActionNotAllowed(command)) {
//                System.out.println(Output.YouCantDoThisAction);
//                continue;
//            } else if (DuelWithUserView.getInstance().isItValidInAllOfThePhases(command)) {
//                continue;
//            }
//            result = duelWithUserView.cheatCodeExecute(command);
//            if (result.equals(Output.InvalidCommand.toString())) {
//                System.out.println(result);
//            } else if (!result.equals(Output.TheGameContinues.toString())) {
//                return result;
//            }
        }
//        OpponentPhase.getInstance().startChainLink();
//        return Output.TheGameContinues.toString();
    }

    private boolean isItForceDrawCommand(String command) {
        Matcher matcher = forceDraw1.matcher(command);
        if (matcher.find()) {
            System.out.println(DrawPhaseController.getInstance().forceDraw(matcher.group(1)));
            return true;
        }
        matcher = forceDraw2.matcher(command);
        if (matcher.find()) {
            System.out.println(DrawPhaseController.getInstance().forceDraw(matcher.group(1)));
            return true;
        }
        return false;
    }

    private boolean isThisActionNotAllowed(String command) {
        if (command.equals(Commands.Summon.toString())) {
            return true;
        } else if (command.equals(Commands.Set.toString())) {
            return true;
        } else if (command.equals(Commands.SetDefencePosition.toString()) || command.equals(Commands.SetAttackPosition.toString())) {
            return true;
        } else if (command.equals(Commands.FlipSummon.toString())) {
            return true;
        } else if (command.equals(Commands.ActivateEffect.toString())) {
            return true;
        } else if (command.equals(Commands.AttackDirect.toString())) {
            return true;
        }
        Matcher matcher = attack.matcher(command);
        return matcher.find();
    }
}
