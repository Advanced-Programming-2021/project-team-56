package controller.duel;

import controller.duel.phases.BattlePhaseController;
import model.Board;
import model.Card;
import model.MonsterCard;
import model.User;
import view.LoginMenuView;
import view.duel.DuelWithUserView;
import view.duel.FirstToGoDeterminerView;
import view.duel.phase.*;

import java.util.HashMap;
import java.util.Locale;
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

    private int phaseCounter = 1;
    private int turnCounter;
    private static DuelWithUser duelWithUserController;
    private Board[] boards = new Board[2];
    private String numberOfRounds;

    private DuelWithUser() {
    }

    public static DuelWithUser getInstance() {
        if (duelWithUserController == null)
            duelWithUserController = new DuelWithUser();
        return duelWithUserController;
    }

    public void run(String firstPlayerUsername, String secondPlayerUsername, String rounds) {
        //TODO Explaining -> added the drawPhase No cards game ending
        DuelWithUserView duelWithUserView = DuelWithUserView.getInstance();
        this.numberOfRounds = rounds;
        if (rounds.equals("3")) {
            int numberOfWinsPlayer1 = 0;
            int numberOfWinsPlayer2 = 0;
            while (numberOfWinsPlayer1 != 2 && numberOfWinsPlayer2 != 2) {
                setUpGame(firstPlayerUsername, secondPlayerUsername);
                if (phaseCaller() == 1) {
                    numberOfWinsPlayer1++;
                    duelWithUserView.print(singleRoundWin(firstPlayerUsername));
                } else {
                    numberOfWinsPlayer2++;
                    duelWithUserView.print(singleRoundWin(secondPlayerUsername));
                }
            }
            if (numberOfWinsPlayer1 == 2) {
                duelWithUserView.print(threeRoundWinner(firstPlayerUsername, secondPlayerUsername));
            } else {
                duelWithUserView.print(threeRoundWinner(secondPlayerUsername, firstPlayerUsername));
            }
        } else {
            setUpGame(firstPlayerUsername, secondPlayerUsername);
            if (phaseCaller() == 1) {
                duelWithUserView.print(oneRoundWin(firstPlayerUsername, secondPlayerUsername));
            } else {
                duelWithUserView.print(oneRoundWin(secondPlayerUsername, firstPlayerUsername));
            }
        }
    }

    public int phaseCaller() {
        //TODO What should this method return ? 42, 55: phaseCaller() == 1
        while (true) {
            switch (phaseCounter) {
                case 1:
                    ////////TOdo
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
                    String result = BattlePhaseView.getInstance().run();
                    if (result.equals("I won")) {
                        getMyBoard().getUser().getPlayerLP().add(getMyBoard().getLP());
                    } else if (result.equals("I lost")) {
                        getEnemyBoard().getUser().getPlayerLP().add(getEnemyBoard().getLP());
                    } else {

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
        //TODO First the deck game cards are shuffled then we will decide the first player to go page 33 game doc
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
        field.append("\n");
        ///////////////////number of main deck
        field = new StringBuilder(showCards(field.toString(), "enemy's spell and trap"));
        field.append("\n");
        field = new StringBuilder(showCards(field.toString(), "enemy's monster"));
        field.append("\n");
        field.append(getEnemyBoard().getGraveyard().size()).append("\t\t\t\t\t\t");
        if (getEnemyBoard().getFieldSpell() == null) {
            field.append("E\n\n\n");
        } else {
            field.append("O\n\n\n");
        }
        field.append("--------------------------\n\n\n");
        if (getMyBoard().getFieldSpell() == null) {
            field.append("E\t\t\t\t\t\t").append(getMyBoard().getGraveyard().size()).append("\n");
        } else {
            field.append("O\t\t\t\t\t\t").append(getMyBoard().getGraveyard().size()).append("\n");
        }
        field = new StringBuilder(showCards(field.toString(), "my monster"));
        field.append("\n");
        field = new StringBuilder(showCards(field.toString(), "my spell and trap"));
        field.append("\n");
        ///////////////////number of main deck
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
        String graveYard = "";
        if (getMyBoard().getGraveyard().size() == 0) {
            graveYard = "graveyard empty\n";
        } else {
            for (Card card : getMyBoard().getGraveyard()) {
                graveYard += card.getName() + ":" + card.getDescription() + "\n";
            }
        }
        return graveYard;
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

    private String singleRoundWin(String winnerUsername) {
        return winnerUsername + " won the game and the score is: ";
    }

    //TODO Explaining -> changed the endGameStuff
    private String oneRoundWin(String winnerSideUsername, String loserSideUsername) {
        User winner = User.getUserByUsername(winnerSideUsername);
        winner.increaseScore(1000);
        winner.increaseMoney(winner.getMaxLP() + 1000);
        winner.clearLP();
        User loser = User.getUserByUsername(loserSideUsername);
        loser.increaseMoney(100);
        loser.clearLP();
        //TODO What is Score-1 o Score-2 page 42 phase1
        return winnerSideUsername + " won the whole match with score: ";
    }

    private String threeRoundWinner(String winnerSideUsername, String loserSideUsername) {
        User winner = User.getUserByUsername(winnerSideUsername);
        winner.increaseScore(3000);
        winner.increaseMoney(3000 + 3 * winner.getMaxLP());
        winner.clearLP();
        User loser = User.getUserByUsername(loserSideUsername);
        loser.clearLP();
        loser.increaseMoney(300);
        //TODO What is Score-1 o Score-2 page 42 phase1
        return winnerSideUsername + " won the whole match with score: ";
    }

    public int manEaterBugEffect(boolean isItUnderAttack) {
        if (!canManEaterBugEffectsBeActivated()) {
            System.out.println("this card effect can't be activated");
            return -100;
        } else {
            while (true) {
                System.out.println("do you want to activate this card effect?");
                String result = LoginMenuView.scan.nextLine().trim();
                if (result.equals("yes")) {
                    int address = MainPhase1View.getInstance().getAddress();
                    if (address > 5 || address < 1) {
                        System.out.println("invalid selection");
                        continue;
                    }
                    MonsterCard monsterCard = getEnemyBoard().getMonsterTerritory().get(address);
                    if (monsterCard == null) {
                        System.out.println("there is no monster on the address");
                        continue;
                    }
                    return address;
                } else if (result.equals("no")) {
                    System.out.println("ok");
                    return -100;
                } else {
                    System.out.println("invalid command");
                }
            }
        }
    }

    private boolean canManEaterBugEffectsBeActivated() {
        DuelWithUser duelWithUser = DuelWithUser.getInstance();
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getEnemyBoard().getMonsterTerritory();
        for (int i = 1; i < 6; i++) {
            if (monsterTerritory.get(i) != null) {
                return true;
            }
        }
        return false;
    }
}
