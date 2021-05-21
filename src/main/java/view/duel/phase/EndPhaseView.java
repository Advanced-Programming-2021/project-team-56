package view.duel.phase;

import controller.duel.DuelWithUser;
import view.LoginMenuView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static view.duel.phase.BattlePhaseView.increaseLP;
import static view.duel.phase.BattlePhaseView.setWinner;

public class EndPhaseView {

    private static EndPhaseView endPhase;

    static Pattern attack = Pattern.compile("^attack (\\d+)$");

    private EndPhaseView() {

    }

    public static EndPhaseView getInstance() {
        if (endPhase == null)
            endPhase = new EndPhaseView();
        return endPhase;
    }

    public void run() {
        DuelWithUser duelWithUser = DuelWithUser.getInstance();
        System.out.println("phase: End Phase\n" + duelWithUser.showField());
        while (true) {
            String command = LoginMenuView.scan.nextLine().trim();
            if (command.equals("next phase")) {
                break;
            }
            if (command.equals("activate effect")) {
                System.out.println("you can’t activate an effect on this turn");
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
            Matcher matcher = attack.matcher(command);
            if (matcher.find()) {
                System.out.println("you can’t do this action in this phase");
                continue;
            }
            if (command.equals("attack direct")) {
                System.out.println("you can’t do this action in this phase");
                continue;
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
            //TODO Win cheat
//            matcher = setWinner.matcher(command);
//            if (matcher.find()) {
//                if (duelWithUser.isNicknameValid(matcher.group(1)).equals("yes")) {
//                    return duelWithUser.setWinner(matcher.group(1));
//                }
//                System.out.println("invalid nickname");
//                continue;
//            }
            System.out.println("invalid command");
        }
        String nickname = duelWithUser.getEnemyBoard().getUser().getNickname();
        System.out.println("its " + nickname + "’s turn");
    }

}
