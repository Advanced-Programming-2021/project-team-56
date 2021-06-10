package view.duel.phase;

import controller.duel.DuelWithUser;
import controller.duel.phases.EndPhaseController;
import controller.duel.phases.OpponentPhase;
import model.Commands;
import model.Output;
import view.LoginMenuView;

import java.util.regex.Matcher;

import static view.duel.phase.BattlePhaseView.*;

public class EndPhaseView {

    private static EndPhaseView endPhase;

    private EndPhaseView() {

    }

    public static EndPhaseView getInstance() {
        if (endPhase == null)
            endPhase = new EndPhaseView();
        return endPhase;
    }

    public String run() {
        DuelWithUser duelWithUser = DuelWithUser.getInstance();
        EndPhaseController.getInstance().run();
        System.out.print("phase: End Phase\n" + duelWithUser.showField());
        while (true) {
            String command = LoginMenuView.scan.nextLine().trim();
            if (command.equals(Commands.NextPhase.toString())) {
                break;
            } else if (isThisActionNotAllowed(command)) {
                System.out.println(Output.YouCantDoThisAction);
                continue;
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
            }
            Matcher matcher = increaseLP.matcher(command);
            if (matcher.find()) {
                System.out.println(duelWithUser.increaseMyLP(matcher.group(1)));
            }
            if (command.equals("surrender")) {
                return Output.ILost.toString();
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
        String nickname = duelWithUser.getEnemyBoard().getUser().getNickname();
        System.out.println("its " + nickname + "’s turn");
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
