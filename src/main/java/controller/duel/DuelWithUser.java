package controller.duel;

import model.Board;
import model.Card;
import model.MonsterCard;
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
                    if (!DrawPhaseView.getInstance().run()) {

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

    public String showField() {
        String field = getEnemyBoard().getUser().getNickname() + ":" + getEnemyBoard().getLP() + "\n";
        for (int i = 0; i < getEnemyBoard().getPlayerHand().size(); i++) {
            field += "\tc";
        }
        field += "\n";
        ///////////////////number of main deck
        field = showCards(field, "enemy's spell and trap");
        field += "\n";
        field = showCards(field, "enemy's monster");
        field += "\n";
        field += getEnemyBoard().getGraveyard().size() + "\t\t\t\t\t\t";
        if (getEnemyBoard().getFieldSpell() == null) field += "E\n\n\n";
        else field += "O\n\n\n";
        field += "--------------------------\n\n\n";
        if (getMyBoard().getFieldSpell() == null) field += "E\t\t\t\t\t\t" + getMyBoard().getGraveyard().size() + "\n";
        else field += "O\t\t\t\t\t\t" + getMyBoard().getGraveyard().size() + "\n";
        field = showCards(field, "my monster");
        field += "\n";
        field = showCards(field, "my spell and trap");
        field += "\n";
        ///////////////////number of main deck
        for (int i = 0; i < getMyBoard().getPlayerHand().size(); i++) {
            field += "\tc";
        }
        field += "\n";
        field += getMyBoard().getUser().getNickname() + ":" + getMyBoard().getLP() + "\n";
        return field;
    }

    private String showSpellAndTrap(Card spellOrTrapCard, String field) {
        if (spellOrTrapCard == null) {
            field += "\tE";
        } else {
            if (spellOrTrapCard.getIsFacedUp()) field += "\tO";
            else field += "\tH";
        }
        return field;
    }

    private String showMonster(MonsterCard monster, String field) {
        if (monster == null) {
            field += "\tE";
        } else {
            if (monster.getIsInAttackPosition()) {
                field += "\tOO";
            } else {
                if (monster.getIsFacedUp()) field += "\tDO";
                else field += "\tDH";
            }
        }
        return field;
    }

    private String showCards(String field, String state) {
        if (state.equals("enemy's spell and trap")) {
            field = showSpellAndTrap(getEnemyBoard().getSpellAndTrapTerritory().get(4), field);
            field = showSpellAndTrap(getEnemyBoard().getSpellAndTrapTerritory().get(2), field);
            field = showSpellAndTrap(getEnemyBoard().getSpellAndTrapTerritory().get(1), field);
            field = showSpellAndTrap(getEnemyBoard().getSpellAndTrapTerritory().get(3), field);
            field = showSpellAndTrap(getEnemyBoard().getSpellAndTrapTerritory().get(5), field);
        }
        if (state.equals("enemy's monster")) {
            field = showMonster(getEnemyBoard().getMonsterTerritory().get(4), field);
            field = showMonster(getEnemyBoard().getMonsterTerritory().get(2), field);
            field = showMonster(getEnemyBoard().getMonsterTerritory().get(1), field);
            field = showMonster(getEnemyBoard().getMonsterTerritory().get(3), field);
            field = showMonster(getEnemyBoard().getMonsterTerritory().get(5), field);
        }
        if (state.equals("my monster")) {
            field = showMonster(getMyBoard().getMonsterTerritory().get(5), field);
            field = showMonster(getMyBoard().getMonsterTerritory().get(3), field);
            field = showMonster(getMyBoard().getMonsterTerritory().get(1), field);
            field = showMonster(getMyBoard().getMonsterTerritory().get(2), field);
            field = showMonster(getMyBoard().getMonsterTerritory().get(4), field);
        }
        if (state.equals("my spell and trap")) {
            field = showSpellAndTrap(getMyBoard().getSpellAndTrapTerritory().get(5), field);
            field = showSpellAndTrap(getMyBoard().getSpellAndTrapTerritory().get(3), field);
            field = showSpellAndTrap(getMyBoard().getSpellAndTrapTerritory().get(1), field);
            field = showSpellAndTrap(getMyBoard().getSpellAndTrapTerritory().get(2), field);
            field = showSpellAndTrap(getMyBoard().getSpellAndTrapTerritory().get(4), field);
        }
        return field;
    }
}
