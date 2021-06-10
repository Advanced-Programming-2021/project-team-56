package view.duel.phase;

import controller.duel.DuelWithUser;
import controller.duel.phases.OpponentPhase;
import controller.duel.phases.StandByPhaseController;
import model.Commands;
import model.Output;
import view.LoginMenuView;
import view.duel.DuelWithUserView;

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
        DuelWithUserView duelWithUserView = DuelWithUserView.getInstance();
        System.out.println("phase: standby phase");
        System.out.print(duelWithUser.showField());
        String result = StandByPhaseController.getInstance().run();
        if (!result.equals(Output.TheGameContinues.toString())) {
            return result;
        }
        while (true) {
            String command = LoginMenuView.scan.nextLine().trim();
            if (command.equals(Commands.NextPhase.toString())) {
                break;
            } else if (isThisActionNotAllowed(command)) {
                System.out.println(Output.YouCantDoThisAction);
                continue;
            } else if (duelWithUserView.isItValidInAllOfThePhases(command)) {
                continue;
            }
            result = duelWithUserView.cheatCodeExecute(command);
            if (!result.equals(Output.TheGameContinues.toString())) {
                return result;
            }
            System.out.println(Output.InvalidCommand);
        }
        OpponentPhase.getInstance().startChainLink();
        return Output.TheGameContinues.toString();
    }

    private boolean isThisActionNotAllowed(String command) {
        if (command.equals(Commands.ActivateEffect.toString())) {
            return true;
        } else if (command.equals(Commands.Summon.toString())) {
            return true;
        } else if (command.equals(Commands.Set.toString())) {
            return true;
        } else if (command.equals(Commands.SetAttackPosition.toString()) || command.equals(Commands.SetDefencePosition.toString())) {
            return true;
        } else if (command.equals(Commands.FlipSummon.toString())) {
            return true;
        } else if (command.equals(Commands.AttackDirect.toString())) {
            return true;
        }
        Matcher matcher = attack.matcher(command);
        return matcher.find();
    }
}
