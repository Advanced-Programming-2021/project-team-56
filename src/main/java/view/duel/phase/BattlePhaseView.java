package view.duel.phase;

import controller.duel.DuelWithUser;
import controller.duel.phases.BattlePhaseController;
import model.Commands;
import model.Output;
import view.LoginMenuView;
import view.duel.DuelWithUserView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BattlePhaseView {

    private static BattlePhaseView battlePhaseView;

    private final DuelWithUser duelWithUser;
    private final BattlePhaseController battlePhaseController;

    static Pattern attack = Pattern.compile("^attack (\\d+)$");

    {
        duelWithUser = DuelWithUser.getInstance();
        battlePhaseController = BattlePhaseController.getInstance();
    }

    private BattlePhaseView() {

    }

    public static BattlePhaseView getInstance() {
        if (battlePhaseView == null) {
            battlePhaseView = new BattlePhaseView();
        }
        return battlePhaseView;
    }

    public String run() {
        DuelWithUserView duelWithUserView = DuelWithUserView.getInstance();
        System.out.print("phase: battle phase\n" + duelWithUser.showField());
        while (true) {
            battlePhaseController.afterBattleEffects();
            String command = LoginMenuView.scan.nextLine().trim();
            if (command.equals(Commands.NextPhase.toString())) {
                break;
            } else if (duelWithUserView.isItValidInAllOfThePhases(command)) {
                continue;
            } else if (isThisActionNotAllowed(command)) {
                System.out.println(Output.YouCantDoThisAction);
                continue;
            }
            Matcher matcher = attack.matcher(command);
            if (matcher.find()) {
                System.out.println(battlePhaseController.attackCard(Integer.parseInt(matcher.group(1))));
                String result = afterAttack();
                if (!result.equals(Output.TheGameContinues.toString())) {
                    return result;
                }
                continue;
            }
            if (command.equals(Commands.AttackDirect.toString())) {
                System.out.println(battlePhaseController.attackUser());
                String result = afterAttack();
                if (!result.equals(Output.TheGameContinues.toString())) {
                    return result;
                }
                continue;
            }
            String result = duelWithUserView.cheatCodeExecute(command);
            if (!result.equals(Output.TheGameContinues.toString())) {
                return result;
            }
            System.out.println(Output.InvalidCommand);
        }
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
        } else return command.equals(Commands.FlipSummon.toString());
    }

    private boolean isGameOver() {
        DuelWithUser duelWithUser = DuelWithUser.getInstance();
        if (duelWithUser.getEnemyBoard().getLP() <= 0 || duelWithUser.getMyBoard().getLP() <= 0) {
            return true;
        }
        return false;
    }

    private String afterAttack() {
        System.out.print(duelWithUser.showField());
        if (isGameOver()) {
            if (duelWithUser.getMyBoard().getLP() <= 0) {
                return Output.ILost.toString();
            } else {
                return Output.IWon.toString();
            }
        }
        return Output.TheGameContinues.toString();
    }
}
