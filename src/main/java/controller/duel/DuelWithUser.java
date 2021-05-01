package controller.duel;

import model.Board;
import model.User;
import view.duel.phase.*;

import java.util.regex.Pattern;

public class DuelWithUser {

    static Pattern selectMonster = Pattern.compile("");

    private int phaseCounter = 1;
    private int turnCounter = 1;

    private static DuelWithUser duelWithUserController;
    private Board[] boards = new Board[2];

    private DuelWithUser() {
    }

    public static DuelWithUser getInstance() {
        if (duelWithUserController == null)
            duelWithUserController = new DuelWithUser();
        return duelWithUserController;
    }

    public void run(String username, String secondPlayerUsername, String rounds){
        setUpGame(username, secondPlayerUsername);
        phaseCaller();
    }

    public void phaseCaller() {
        while (true) {
            switch (phaseCounter) {
                case 1:
                    DrawPhaseView.getInstance().run();
                    break;
                case 2:
                    StandByPhaseView.getInstance().run();
                    break;
                case 3:
                    MainPhase1View.getInstance().run();
                    break;
                case 4:
                    BattlePhaseView.getInstance().run();
                    break;
                case 5:
                    MainPhase2View.getInstance().run();
                    break;
                case 6:
                    EndPhaseView.getInstance().run();
                    break;
            }
            phaseCounter++;
            if (phaseCounter == 7) {
                phaseCounter -= 6;
            }
            turnCounter++;
        }
    }

    public void setUpGame(String firstPlayer, String secondPlayer) {
        boards[0] = new Board(User.getUserByUsername(firstPlayer).getActivatedDeck());
        boards[1] = new Board(User.getUserByUsername(secondPlayer).getActivatedDeck());
    }

    public Board getMyBoard(){
        return boards[turnCounter % 2];
    }

    public Board getEnemyBoard(){
        return boards[(turnCounter + 1) % 2];
    }

    public void selectCard(String command) {

    }
}
