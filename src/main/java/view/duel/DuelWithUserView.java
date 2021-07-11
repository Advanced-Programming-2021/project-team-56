package view.duel;

import controller.duel.DuelWithUser;
import model.Commands;
import model.Output;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DuelWithUserView {
    static Pattern increaseLP = Pattern.compile("^increase --LP (\\d+)$");
    static Pattern setWinner = Pattern.compile("^duel set-winner (\\S+)$");

    private static DuelWithUserView duelWithUserView;

    private DuelWithUserView() {

    }

    public static DuelWithUserView getInstance() {
        if (duelWithUserView == null)
            duelWithUserView = new DuelWithUserView();
        return duelWithUserView;
    }

    public boolean isItValidInAllOfThePhases(String command) {
        DuelWithUser duelWithUser = DuelWithUser.getInstance();
        if (command.equals(Commands.DisSelect.toString())) {
            System.out.println(duelWithUser.deselectCard());
            return true;
        } else if (command.startsWith(Commands.Select.toString())) {
          //  System.out.println(duelWithUser.selectCard(command));
            return true;
        } else if (command.equals(Commands.CardShowSelected.toString())) {
            System.out.println(duelWithUser.showSelectedCard());
            return true;
        } else if (command.equals(Commands.ShowGraveyard.toString())) {
            showGraveYardView();
            return true;
        }
        return false;
    }

    private void showGraveYardView() {
//        String command;
//        while (true) {
//            command = LoginMenuView.scan.nextLine();
//            if (command.equals("show")) {
//                System.out.print(DuelWithUser.getInstance().showGraveYard());
//                continue;
//            }
//            if (command.equals("back")) {
//                break;
//            }
//            System.out.println(Output.InvalidCommand);
//        }
    }

    public String cheatCodeExecute(String command) {
        DuelWithUser duelWithUser = DuelWithUser.getInstance();
        Matcher matcher = increaseLP.matcher(command);
        if (matcher.find()) {
            System.out.println(duelWithUser.increaseMyLP(Integer.parseInt(matcher.group(1))));
            return Output.TheGameContinues.toString();
        } else if (command.equals(Commands.Surrender.toString())) {
            return Output.ILost.toString();
        }
        matcher = setWinner.matcher(command);
        if (matcher.find()) {
            if (duelWithUser.isNicknameValid(matcher.group(1))) {
                return duelWithUser.setWinner(matcher.group(1));
            }
            System.out.println(Output.InvalidNickname);
            return Output.TheGameContinues.toString();
        }
        return Output.InvalidCommand.toString();
    }

    public void printEndMessage(String output) {
        System.out.println(output);
    }
}
