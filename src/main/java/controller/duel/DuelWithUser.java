package controller.duel;

import model.Board;
import model.User;
import view.duel.phase.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DuelWithUser {

    static Pattern selectMyMonster = Pattern.compile("^select --monster (\\d+)$");
    static Pattern selectMySpellOrTrap = Pattern.compile("^select --spell (\\d+)$");
    static Pattern selectOpponentMonster1 = Pattern.compile("^select --monster (\\d+) --opponent$");
    static Pattern selectOpponentMonster2 = Pattern.compile("^select --opponent --monster (\\d+)$");
    static Pattern selectOpponentSpellOrTrap1 = Pattern.compile("^select --spell (\\d+) --opponent$");
    static Pattern selectOpponentSpellOrTrap2 = Pattern.compile("^select --opponent --spell (\\d+)$");
    static Pattern selectMyHandCard = Pattern.compile("^select --hand (\\d+)$");

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

    public void run(String username, String secondPlayerUsername, String rounds) {
        setUpGame(username, secondPlayerUsername);
        phaseCaller();
    }

    public void phaseCaller() {
        while (true) {
            switch (phaseCounter) {
                case 1:
                    if (!DrawPhaseView.getInstance().run()){

                    }
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
        boards[0] = new Board(User.getUserByUsername(firstPlayer).getActivatedDeck(), User.getUserByUsername(firstPlayer));
        boards[1] = new Board(User.getUserByUsername(secondPlayer).getActivatedDeck(), User.getUserByUsername(secondPlayer));
    }

    public Board getMyBoard() {
        return boards[turnCounter % 2];
    }

    public Board getEnemyBoard() {
        return boards[(turnCounter + 1) % 2];
    }

    public String selectCard(String command) {
        Matcher matcher = selectMyMonster.matcher(command);
        if (matcher.find()) {
            return selectMonster(matcher, "me");
        }
        matcher = selectMySpellOrTrap.matcher(command);
        if (matcher.find()) {
            return selectSpellOrTrap(matcher, "me");
        }
        matcher = selectOpponentMonster1.matcher(command);
        if (matcher.find()) {
            return selectMonster(matcher, "opponent");
        }
        matcher = selectOpponentMonster2.matcher(command);
        if (matcher.find()) {
            return selectMonster(matcher, "opponent");
        }
        matcher = selectOpponentSpellOrTrap1.matcher(command);
        if (matcher.find()) {
            return selectSpellOrTrap(matcher, "opponent");
        }
        matcher = selectOpponentSpellOrTrap2.matcher(command);
        if (matcher.find()) {
            return selectSpellOrTrap(matcher, "opponent");
        }
        matcher = selectMyHandCard.matcher(command);
        if (matcher.find()) {
            if (Integer.parseInt(matcher.group(1)) > getMyBoard().getPlayerHand().size()) {
                return "invalid selection";
            }
            getMyBoard().setSelectedCard(getMyBoard().getPlayerHand().get(Integer.parseInt(matcher.group(1)) - 1));
            return "card selected";
        }
        if (command.equals("select --field")) {
            if (getMyBoard().getFieldSpell() == null) return "no card found in the given position";
            getMyBoard().setSelectedCard(getMyBoard().getFieldSpell());
            return "card selected";
        }
        if (command.equals("select --field --opponent") || command.equals("select --opponent --field")) {
            if (getEnemyBoard().getFieldSpell() == null) return "no card found in the given position";
            getMyBoard().setSelectedCard(getEnemyBoard().getFieldSpell());
            return "card selected";
        }
        return "invalid command";
    }

    private String selectMonster(Matcher matcher, String whichPlayer) {
        int monsterNumber = Integer.parseInt(matcher.group(1));
        if (monsterNumber > 5) {
            return "invalid selection";
        }
        if (whichPlayer.equals("me")) {
            if (getMyBoard().getMonsterTerritory().get(monsterNumber) == null) {
                return "no card found in the given position";
            }
            getMyBoard().setSelectedCard(getMyBoard().getMonsterTerritory().get(monsterNumber));
            return "card selected";
        }
        if (getEnemyBoard().getMonsterTerritory().get(monsterNumber) == null) {
            return "no card found in the given position";
        }
        getMyBoard().setSelectedCard(getEnemyBoard().getMonsterTerritory().get(monsterNumber));
        return "card selected";
    }

    private String selectSpellOrTrap(Matcher matcher, String whichPlayer) {
        int spellOrTrapNumber = Integer.parseInt(matcher.group(1));
        if (spellOrTrapNumber > 5) {
            return "invalid selection";
        }
        if (whichPlayer.equals("me")) {
            if (getMyBoard().getSpellAndTrapTerritory().get(spellOrTrapNumber) == null) {
                return "no card found in the given position";
            }
            getMyBoard().setSelectedCard(getMyBoard().getSpellAndTrapTerritory().get(spellOrTrapNumber));
            return "card selected";
        }
        if (getEnemyBoard().getSpellAndTrapTerritory().get(spellOrTrapNumber) == null) {
            return "no card found in the given position";
        }
        getMyBoard().setSelectedCard(getEnemyBoard().getSpellAndTrapTerritory().get(spellOrTrapNumber));
        return "card selected";
    }

}
