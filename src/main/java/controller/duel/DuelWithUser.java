package controller.duel;

import controller.duel.effects.SpellEffectActivate;
import controller.duel.effects.SpellEffectCanActivate;
import model.Board;
import model.Card;
import model.MonsterCard;
import model.User;
import view.duel.DuelWithUserView;
import view.duel.FirstToGoDeterminerView;
import view.duel.phase.*;

import java.util.ArrayList;
import java.util.HashMap;
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
    static Pattern selectFieldCard = Pattern.compile("^select (?:--opponent --field|--field --opponent|--field)$");
    private final SpellEffectCanActivate spellEffectCanActivate = SpellEffectCanActivate.getInstance();
    private final SpellEffectActivate spellEffectActivate = SpellEffectActivate.getInstance();
    private int phaseCounter = 1;
    private int turnCounter;
    private static DuelWithUser duelWithUser;
    private final static DuelWithUserView duelWithUserView = DuelWithUserView.getInstance();
    private Board[] boards = new Board[2];

    private DuelWithUser() {
    }

    public static DuelWithUser getInstance() {
        if (duelWithUser == null)
            duelWithUser = new DuelWithUser();
        return duelWithUser;
    }

    public void run(String firstPlayerUsername, String secondPlayerUsername, String rounds) {
        if (rounds.equals("3")) {
            int numberOfWinsPlayer1 = 0;
            int numberOfWinsPlayer2 = 0;
            while (numberOfWinsPlayer1 != 2 && numberOfWinsPlayer2 != 2) {
                setUpGame(firstPlayerUsername, secondPlayerUsername);
                if (phaseCaller(firstPlayerUsername) == 1) {
                    numberOfWinsPlayer1++;
                    duelWithUserView.printEndMessage(singleRoundWin(firstPlayerUsername,
                            numberOfWinsPlayer1, numberOfWinsPlayer2));
                } else {
                    numberOfWinsPlayer2++;
                    duelWithUserView.printEndMessage(singleRoundWin(secondPlayerUsername,
                            numberOfWinsPlayer2, numberOfWinsPlayer1));
                }
            }
            if (numberOfWinsPlayer1 == 2) {
                duelWithUserView.printEndMessage(threeRoundWinner(firstPlayerUsername,
                        secondPlayerUsername, numberOfWinsPlayer1, numberOfWinsPlayer2));
            } else {
                duelWithUserView.printEndMessage(threeRoundWinner(secondPlayerUsername,
                        firstPlayerUsername, numberOfWinsPlayer2, numberOfWinsPlayer1));
            }
        } else {
            setUpGame(firstPlayerUsername, secondPlayerUsername);
            if (phaseCaller(firstPlayerUsername) == 1) {
                duelWithUserView.printEndMessage(oneRoundWin(firstPlayerUsername, secondPlayerUsername));
            } else {
                duelWithUserView.printEndMessage(oneRoundWin(secondPlayerUsername, firstPlayerUsername));
            }
        }
    }

    public int phaseCaller(String firstPlayerUsername) {
        while (true) {
            switch (phaseCounter) {
                case 1:
                    if (!DrawPhaseView.getInstance().run()) {
                        if (getMyBoard().getUser().getUsername().equals(firstPlayerUsername)) {
                            return 2;
                        }
                        return 1;
                    }
                    break;
                case 2:
                    String result1 = StandByPhaseView.getInstance().run();
                    if (result1.equals("I won")) {
                        getMyBoard().getUser().getPlayerLP().add(getMyBoard().getLP());
                        if (getMyBoard().getUser().getUsername().equals(firstPlayerUsername)) {
                            return 1;
                        }
                        return 2;
                    } else if (result1.equals("I lost")) {
                        getEnemyBoard().getUser().getPlayerLP().add(getEnemyBoard().getLP());
                        if (getEnemyBoard().getUser().getUsername().equals(firstPlayerUsername)) {
                            return 1;
                        }
                        return 2;
                    }
                    break;
                case 3:
                    MainPhase1View.getInstance().run();
                    break;
                case 4:
                    String result2 = BattlePhaseView.getInstance().run();
                    if (result2.equals("I won")) {
                        getMyBoard().getUser().getPlayerLP().add(getMyBoard().getLP());
                        if (getMyBoard().getUser().getUsername().equals(firstPlayerUsername)) {
                            return 1;
                        }
                        return 2;
                    } else if (result2.equals("I lost")) {
                        getEnemyBoard().getUser().getPlayerLP().add(getEnemyBoard().getLP());
                        if (getEnemyBoard().getUser().getUsername().equals(firstPlayerUsername)) {
                            return 1;
                        }
                        return 2;
                    }
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
                turnCounter++;
            }
        }
    }

    public void setUpGame(String firstPlayerUsername, String secondPlayerUsername) {
        boards[0] = new Board(User.getUserByUsername(firstPlayerUsername));
        boards[1] = new Board(User.getUserByUsername(secondPlayerUsername));
        boards[0].setCardsOpponentBoard(boards[1]);
        boards[1].setCardsOpponentBoard(boards[0]);
        String firstPlayerToGoUsername = FirstToGoDeterminerView
                .getInstance().determineFirstPlayerToGo(firstPlayerUsername, secondPlayerUsername);
        if (firstPlayerToGoUsername.equals(firstPlayerUsername)) {
            turnCounter = 0;
        } else {
            turnCounter = 1;
        }
        boards[0].setPlayerHand();
        boards[1].setPlayerHand();
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
            return selectMyHandCard(matcher);
        }
        if (selectFieldCard.matcher(command).find()) {
            return selectFieldCard(command);
        }
        return "invalid command";
    }

    private String selectMonster(Matcher matcher, String whichPlayer) {
        int monsterNumber = Integer.parseInt(matcher.group(1));
        if (monsterNumber > 5 || monsterNumber == 0) {
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
        if (spellOrTrapNumber > 5 || spellOrTrapNumber == 0) {
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

    private String selectMyHandCard(Matcher matcher) {
        int myHandCardsAddress = Integer.parseInt(matcher.group(1));
        if (myHandCardsAddress > getMyBoard().getPlayerHand().size() || myHandCardsAddress == 0) {
            return "invalid selection";
        }
        getMyBoard().setSelectedCard(getMyBoard().getPlayerHand().get(myHandCardsAddress - 1));
        return "card selected";
    }

    private String selectFieldCard(String command) {
        if (command.equals("select --field")) {
            if (getMyBoard().getFieldSpell() == null) {
                return "no card found in the given position";
            }
            getMyBoard().setSelectedCard(getMyBoard().getFieldSpell());
            return "card selected";
        }
        if (command.equals("select --field --opponent") || command.equals("select --opponent --field")) {
            if (getEnemyBoard().getFieldSpell() == null) {
                return "no card found in the given position";
            }
            getMyBoard().setSelectedCard(getEnemyBoard().getFieldSpell());
            return "card selected";
        }
        return "this is never going to be returned";
    }

    public String deselectCard() {
        if (getMyBoard().getSelectedCard() == null) {
            return "no card is selected yet";
        }
        getMyBoard().setSelectedCard(null);
        return "card deselected";
    }

    public String showField() {
        StringBuilder field = new StringBuilder(getEnemyBoard().getUser().getNickname() + ":" + getEnemyBoard().getLP() + "\n");
        for (int i = 0; i < getEnemyBoard().getPlayerHand().size(); i++) {
            field.append("\tc");
        }
        field.append("\n").append(getEnemyBoard().getMainDeck().size()).append("\n");
        field = new StringBuilder(showCards(field.toString(), "enemy's spell and trap"));
        field.append("\n");
        field = new StringBuilder(showCards(field.toString(), "enemy's monster"));
        field.append("\n").append(getEnemyBoard().getGraveyard().size()).append("\t\t\t\t\t\t");
        if (getEnemyBoard().getFieldSpell() == null) {
            field.append("E\n\n");
        } else {
            field.append("O\n\n");
        }
        field.append("--------------------------\n\n");
        if (getMyBoard().getFieldSpell() == null) {
            field.append("E\t\t\t\t\t\t").append(getMyBoard().getGraveyard().size()).append("\n");
        } else {
            field.append("O\t\t\t\t\t\t").append(getMyBoard().getGraveyard().size()).append("\n");
        }
        field = new StringBuilder(showCards(field.toString(), "my monster"));
        field.append("\n");
        field = new StringBuilder(showCards(field.toString(), "my spell and trap"));
        field.append("\n").append("\t\t\t\t\t\t").append(getMyBoard().getMainDeck().size());
        for (int i = 0; i < getMyBoard().getPlayerHand().size(); i++) {
            field.append("\tc");
        }
        field.append("\n");
        field.append(getMyBoard().getUser().getNickname()).append(":").append(getMyBoard().getLP()).append("\n");
        return field.toString();
    }

    private String showSpellAndTrap(Card spellOrTrapCard, String field) {
        if (spellOrTrapCard == null) {
            field += "\tE";
        } else {
            if (spellOrTrapCard.getIsFacedUp()) {
                field += "\tO";
            } else {
                field += "\tH";
            }
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
                if (monster.getIsFacedUp()) {
                    field += "\tDO";
                } else {
                    field += "\tDH";
                }
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

    public String showGraveYard() {
        StringBuilder graveYard = new StringBuilder();
        if (getMyBoard().getGraveyard().size() == 0) {
            graveYard = new StringBuilder("graveyard empty\n");
        } else {
            for (Card card : getMyBoard().getGraveyard()) {
                graveYard.append(card.getName()).append(":").append(card.getDescription()).append("\n");
            }
        }
        return graveYard.toString();
    }

    public String showSelectedCard() {
        Card selectedCard = getMyBoard().getSelectedCard();
        if (selectedCard == null) {
            return "no card is selected yet";
        } else {
            for (int i = 1; i <= 5; i++) {
                if (selectedCard == getEnemyBoard().getMonsterTerritory().get(i) && !selectedCard.getIsFacedUp()) {
                    return "card is not visible";
                }
                if (selectedCard == getEnemyBoard().getSpellAndTrapTerritory().get(i) && !selectedCard.getIsFacedUp()) {
                    return "card is not visible";
                }
                if (selectedCard == getEnemyBoard().getFieldSpell() && !selectedCard.getIsFacedUp()) {
                    return "card is not visible";
                }
            }
            return selectedCard.getName() + ":" + selectedCard.getDescription();
        }
    }

    public int getLastSummonedOrSetTurn() {
        return getMyBoard().getLastSummonedOrSetTurn();
    }

    public int getTurnCounter() {
        return turnCounter;
    }

    private String singleRoundWin(String winnerUsername, int winnerScore, int looserScore) {
        return winnerUsername + " won the game and the score is: " + winnerScore + "-" + looserScore;
    }

    private String oneRoundWin(String winnerSideUsername, String loserSideUsername) {
        User winner = User.getUserByUsername(winnerSideUsername);
        winner.increaseScore(1000);
        winner.increaseMoney(winner.getMaxLP() + 1000);
        winner.clearLP();
        User loser = User.getUserByUsername(loserSideUsername);
        loser.increaseMoney(100);
        loser.clearLP();
        return winnerSideUsername + " won the whole match";
    }

    private String threeRoundWinner(String winnerUsername, String looserUsername, int winnerScore, int looserScore) {
        User winner = User.getUserByUsername(winnerUsername);
        winner.increaseScore(3000);
        winner.increaseMoney(3000 + 3 * winner.getMaxLP());
        winner.clearLP();
        User loser = User.getUserByUsername(looserUsername);
        loser.clearLP();
        loser.increaseMoney(300);
        return winnerUsername + " won the whole match with score: " + winnerScore + "-" + looserScore;
    }

    public void afterDeathEffect(int player, MonsterCard monsterCard) {
        if (player == 1) {
            if (spellEffectCanActivate.isThereSupplySquad(1)) {
                spellEffectActivate.supplySquadEffect(1);
            }
        } else {
            if (spellEffectCanActivate.isThereSupplySquad(2)) {
                spellEffectActivate.supplySquadEffect(2);
            }
        }
        if (monsterCard.getEquipId().size() != 0) {
            int counter = 1;
            while (counter <= 2){
                HashMap<Integer, Card> spellTerritory;
                ArrayList<Card> graveyard;
                if (counter == 1){
                    spellTerritory = duelWithUser.getMyBoard().getSpellAndTrapTerritory();
                    graveyard = duelWithUser.getMyBoard().getGraveyard();
                }else {
                    spellTerritory = duelWithUser.getEnemyBoard().getSpellAndTrapTerritory();
                    graveyard = duelWithUser.getEnemyBoard().getGraveyard();
                }
                for (int i = 1; i < 6 ; i++) {
                    Card spell = spellTerritory.get(i);
                    if (spell != null) {
                        if (monsterCard.getEquipId().contains(spell.getEquipID())){
                            graveyard.add(spell);
                            spellTerritory.put(i, null);
                        }
                    }
                }
                counter++;
            }
        }
    }

}
