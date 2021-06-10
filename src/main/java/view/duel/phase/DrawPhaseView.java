package view.duel.phase;

import controller.duel.DuelWithUser;
import controller.duel.phases.DrawPhaseController;
import controller.duel.phases.OpponentPhase;
import model.Commands;
import model.Output;
import view.LoginMenuView;

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
        System.out.println("phase: draw phase");
        String result = DrawPhaseController.getInstance().run();
        System.out.println(result);
        System.out.print(duelWithUser.showField());
        if (result.equals("No cards is in your deck")) {
            return Output.ILost.toString();
        }
        while (true) {
            String command = LoginMenuView.scan.nextLine().trim();
            if (command.equals(Commands.NextPhase.toString())) {
                break;
            } else if (isThisActionNotAllowed(command)) {
                System.out.println(Output.YouCantDoThisAction);
                continue;
            } else if (command.equals("surrender")) {
                return Output.ILost.toString();
            } else if (command.equals("select -d")) {
                System.out.println(duelWithUser.deselectCard());
                continue;
            } else if (command.startsWith("select")) {
                System.out.println(duelWithUser.selectCard(command));
                continue;
            } else if (command.equals("card show --selected")) {
                System.out.println(duelWithUser.showSelectedCard());
                continue;
            } else if (command.equals("show graveyard")) {
                MainPhase1View.showGraveYardView();
                continue;
            } else if (command.startsWith("select --hand") || command.startsWith("select --force")) {
                checkForceDrawCommand(command);
                continue;
            }
            Matcher matcher = increaseLP.matcher(command);
            if (matcher.find()) {
                System.out.println(duelWithUser.increaseMyLP(matcher.group(1)));
                continue;
            }
            matcher = setWinner.matcher(command);
            if (matcher.find()) {
                if (duelWithUser.isNicknameValid(matcher.group(1)).equals("yes")) {
                    return duelWithUser.setWinner(matcher.group(1));
                }
                System.out.println("invalid nickname");
                continue;
            }
            System.out.println(Output.InvalidCommand);
        }
        OpponentPhase.getInstance().startChainLink();
        return Output.TheGameContinues.toString();
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
        System.out.println(Output.InvalidCommand);
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
