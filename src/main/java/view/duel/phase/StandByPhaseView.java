package view.duel.phase;

import controller.duel.DuelWithUser;
import controller.duel.phases.OpponentPhase;
import controller.duel.phases.StandByPhaseController;
import model.Output;
import view.LoginMenuView;

import java.util.regex.Matcher;

import static view.duel.phase.BattlePhaseView.*;

public class StandByPhaseView {

    private static StandByPhaseView standByPhase;

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
        System.out.println("phase: standby phase");
        System.out.print(duelWithUser.showField());
        String result = StandByPhaseController.getInstance().run();
        if (!result.equals("the game continuous")) {
            return result;
        }
        while (true) {
            String command = LoginMenuView.scan.nextLine().trim();
            if (command.equals("next phase")) {
                break;
            }
            if (command.equals("activate effect")) {
                System.out.println(Output.YouCantDoThisAction);
                continue;
            }
            if (command.equals("summon")) {
                System.out.println(Output.YouCantDoThisAction);
                continue;
            }
            if (command.equals("set")) {
                System.out.println(Output.YouCantDoThisAction);
                continue;
            }
            if (command.equals("set --position attack") || command.equals("set --position defence")) {
                System.out.println(Output.YouCantDoThisAction);
                continue;
            }
            if (command.equals("flip-summon")) {
                System.out.println(Output.YouCantDoThisAction);
                continue;
            }
            Matcher matcher = attack.matcher(command);
            if (matcher.find()) {
                System.out.println(Output.YouCantDoThisAction);
                continue;
            }
            if (command.equals("attack direct")) {
                System.out.println(Output.YouCantDoThisAction);
                continue;
            }
            if (command.equals("surrender")) {
                return Output.ILost.toString();
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
            System.out.println(Output.InvalidCommand);
        }
        OpponentPhase.getInstance().startChainLink();
        return Output.TheGameContinues.toString();
    }
}
