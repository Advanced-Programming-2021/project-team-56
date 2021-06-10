package view.duel.phase;

import controller.duel.DuelWithUser;
import controller.duel.phases.MainPhase1Controller;
import controller.duel.phases.OpponentPhase;
import model.Commands;
import model.Output;
import view.LoginMenuView;

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
        System.out.print(duelWithUser.showField());
        while (true) {
            String command = LoginMenuView.scan.nextLine().trim();
            if (command.equals(Commands.NextPhase.toString())) {
                break;
            } else if (isThisActionNotAllowed(command)) {
                System.out.println(Output.YouCantDoThisAction);
                continue;
            } else if (command.equals(Commands.DisSelect.toString())) {
                System.out.println(duelWithUser.deselectCard());
                continue;
            } else if (command.startsWith(Commands.Select.toString())) {
                System.out.println(duelWithUser.selectCard(command));
                continue;
            } else if (command.equals(Commands.CardShowSelected.toString())) {
                System.out.println(duelWithUser.showSelectedCard());
                continue;
            } else if (command.equals(Commands.ShowGraveyard.toString())) {
                showGraveYardView();
                continue;
            } else if (command.equals(Commands.Surrender.toString())) {
                return Output.ILost.toString();
            } else if (shouldIShowTheFiled(command)) {
                System.out.print(duelWithUser.showField());
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
                System.out.println(Output.InvalidNickname);
                continue;
            }
            System.out.println(Output.InvalidCommand);
        }
        OpponentPhase.getInstance().startChainLink();
        return Output.TheGameContinues.toString();
    }

    public static void showGraveYardView() {
        String command;
        while (true) {
            command = LoginMenuView.scan.nextLine();
            if (command.equals("show")) {
                System.out.print(DuelWithUser.getInstance().showGraveYard());
                continue;
            }
            if (command.equals("back")) {
                break;
            }
            System.out.println(Output.InvalidCommand);
        }
    }

    private boolean isThisActionNotAllowed(String command) {
        Matcher matcher = attack.matcher(command);
        if (matcher.find()) {
            return true;
        }
        if (command.equals(Commands.AttackDirect.toString())) {
            return true;
        }
        return false;
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
