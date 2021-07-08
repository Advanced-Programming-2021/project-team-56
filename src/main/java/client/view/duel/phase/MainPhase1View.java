package view.duel.phase;

import controller.duel.DuelWithUser;
import controller.duel.phases.MainPhase1Controller;
import controller.duel.phases.OpponentPhase;
import model.Commands;
import model.Output;
import view.duel.DuelWithUserView;

import java.util.regex.Matcher;

import static view.duel.phase.BattlePhaseView.*;

public class MainPhase1View {

    private static MainPhase1View mainPhase1View;
    private final DuelWithUser duelWithUser;
    private final MainPhase1Controller mainPhase1Controller;

    {
        duelWithUser = DuelWithUser.getInstance();
        mainPhase1Controller = MainPhase1Controller.getInstance();
    }

    private MainPhase1View() {

    }

    public static MainPhase1View getInstance() {
        if (mainPhase1View == null) {
            mainPhase1View = new MainPhase1View();
        }
        return mainPhase1View;
    }

    public String run() {
        DuelWithUserView duelWithUserView = DuelWithUserView.getInstance();
        System.out.print(duelWithUser.showField());
        while (true) {
//            String command = LoginMenuView.scan.nextLine().trim();
//            if (command.equals(Commands.NextPhase.toString())) {
//                break;
//            } else if (isThisActionNotAllowed(command)) {
//                System.out.println(Output.YouCantDoThisAction);
//                continue;
//            } else if (shouldIShowTheFiled(command)) {
//                System.out.print(duelWithUser.showField());
//                continue;
//            } else if (duelWithUserView.isItValidInAllOfThePhases(command)) {
//                continue;
//            }
//            String result = duelWithUserView.cheatCodeExecute(command);
//            if (result.equals(Output.InvalidCommand.toString())) {
//                System.out.println(result);
//            } else if (!result.equals(Output.TheGameContinues.toString())) {
//                return result;
//            }
        }
//        OpponentPhase.getInstance().startChainLink();
//        return Output.TheGameContinues.toString();
    }

    private boolean isThisActionNotAllowed(String command) {
        Matcher matcher = attack.matcher(command);
        if (matcher.find()) {
            return true;
        }
        return command.equals(Commands.AttackDirect.toString());
    }

    private boolean shouldIShowTheFiled(String command) {
        if (command.equals(Commands.Summon.toString())) {
            System.out.println(mainPhase1Controller.summon(false));
            return true;
        } else if (command.equals(Commands.Set.toString())) {
            System.out.println(mainPhase1Controller.set());
            return true;
        } else if (command.equals(Commands.ActivateEffect.toString())) {
            mainPhase1Controller.activateSpell();
            return true;
        } else if (command.equals(Commands.SetAttackPosition.toString())) {
            System.out.println(mainPhase1Controller.changePosition(true));
            return true;
        } else if (command.equals(Commands.SetDefencePosition.toString())) {
            System.out.println(mainPhase1Controller.changePosition(false));
            return true;
        } else if (command.equals(Commands.FlipSummon.toString())) {
            System.out.println(mainPhase1Controller.flipSummon());
            return true;
        } else if (command.equals(Commands.SpecialSummon.toString())) {
            System.out.println(mainPhase1Controller.specialSummon());
            return true;
        }
        return false;
    }
}
