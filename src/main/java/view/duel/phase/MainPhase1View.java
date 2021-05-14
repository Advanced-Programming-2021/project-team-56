package view.duel.phase;

import controller.duel.DuelWithUser;
import controller.duel.phases.MainPhase1Controller;
import view.LoginMenuView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static view.duel.phase.BattlePhaseView.increaseLP;

public class MainPhase1View {
    private static MainPhase1View mainPhase1;
    static Pattern setPosition = java.util.regex.Pattern.compile("^set -- position (attack|defence)$");
    static Pattern attack = Pattern.compile("^attack (\\d+)$");

    private MainPhase1View() {

    }

    public static MainPhase1View getInstance() {
        if (mainPhase1 == null) {
            mainPhase1 = new MainPhase1View();
        }
        return mainPhase1;
    }

    public void run() {
        DuelWithUser duelWithUser = DuelWithUser.getInstance();
        System.out.println("phase: Main Phase 1");
        System.out.print(duelWithUser.showField());
        MainPhase1Controller mainPhase1Controller = MainPhase1Controller.getInstance();
        while (true) {
            String command = LoginMenuView.scan.nextLine().trim();
            if (command.equals("next phase")) {
                break;
            }
            if (command.equals("summon")) {
                System.out.println(mainPhase1Controller.summon());
                System.out.print(duelWithUser.showField());
                continue;
            }
            if (command.equals("set")) {
                System.out.println(mainPhase1Controller.set());
                System.out.print(duelWithUser.showField());
                continue;
            }
            if (command.equals("activate effect")) {
                System.out.println(mainPhase1Controller.activateSpell());
                System.out.print(duelWithUser.showField());
                continue;
            }
            Matcher matcher = setPosition.matcher(command);
            if (matcher.find()) {
                if (matcher.group(1).equals("attack")) {
                    System.out.println(mainPhase1Controller.changeToAttackPosition());
                } else {
                    System.out.println(mainPhase1Controller.changeToDefencePosition());
                }
                System.out.print(duelWithUser.showField());
                continue;
            }
            if (command.equals("flip-summon")) {
                System.out.println(mainPhase1Controller.flipSummon());
                System.out.print(duelWithUser.showField());
                continue;
            }
            matcher = attack.matcher(command);
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
            if (command.equals("ritual summon")) {

            }
            if (command.equals("special summon")) {
                System.out.println(MainPhase1Controller.getInstance().specialSummon());
            }
            if (command.equals("card show --selected")) {
                System.out.println(duelWithUser.showSelectedCard());
                continue;
            }
            if (command.equals("show graveyard")) {
                System.out.print(duelWithUser.showGraveYard());
                continue;
            }
            matcher = increaseLP.matcher(command);
            if (matcher.find()) {
                System.out.println(duelWithUser.increaseMyLP(matcher.group(1)));
            }
            System.out.println("invalid command");
        }
    }
}
