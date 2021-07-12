package controller.duel;

import controller.duel.effects.SpellEffectActivate;
import controller.duel.effects.SpellEffectCanActivate;
import model.*;

import java.util.ArrayList;
import java.util.HashMap;

public class DuelWithUser {

    private static DuelWithUser duelWithUser;

    private int phaseCounter = 1;
    private int turnCounter;
    private int tempTurnCounter;
    private final Board[] boards = new Board[2];
    private int startTurn;
    private int currentRoundNumber = 1;

    private DuelWithUser() {
    }

    public static DuelWithUser getInstance() {
        if (duelWithUser == null)
            duelWithUser = new DuelWithUser();
        return duelWithUser;
    }

    public int getTurnCounter() {
        return turnCounter;
    }

    public void incrementTurnCounter() {
        turnCounter++;
    }

    public void setUpGame(String firstPlayerUsername, String secondPlayerUsername, int lastRoundResult) {
        boards[0] = new Board(User.getUserByUsername(firstPlayerUsername));
        boards[1] = new Board(User.getUserByUsername(secondPlayerUsername));
        turnCounter = 2;
        startTurn = turnCounter;
        boards[0].setStartedTurn(2);
        boards[1].setStartedTurn(3);
        boards[0].setPlayerHandForFirstPlayer();
        boards[1].setPlayerHandForSecondPlayer();
    }

    public Board getMyBoard() {
        return boards[(turnCounter + tempTurnCounter) % 2];
    }

    public Board getEnemyBoard() {
        return boards[(turnCounter + 1 + tempTurnCounter) % 2];
    }

    public void selectCard(Card card) {
        getMyBoard().setSelectedCard(card);
    }

    public String deselectCard() {
        if (getMyBoard().getSelectedCard() == null) {
            return Output.NoCardIsSelectedYet.toString();
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
        field.append("\n").append("\t\t\t\t\t\t").append(getMyBoard().getMainDeck().size()).append("\n");
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
            return Output.NoCardIsSelectedYet.toString();
        } else {
            for (int i = 1; i <= 5; i++) {
                if (selectedCard == getEnemyBoard().getMonsterTerritory().get(i) && !selectedCard.getIsFacedUp()) {
                    return Output.CardIsNotVisible.toString();
                }
                if (selectedCard == getEnemyBoard().getSpellAndTrapTerritory().get(i) && !selectedCard.getIsFacedUp()) {
                    return Output.CardIsNotVisible.toString();
                }
                if (selectedCard == getEnemyBoard().getFieldSpell() && !selectedCard.getIsFacedUp()) {
                    return Output.CardIsNotVisible.toString();
                }
            }
            if (selectedCard instanceof MonsterCard) {
                MonsterCard selectedMonsterCard = (MonsterCard) selectedCard;
                return selectedMonsterCard.getName() + ": ATK, DEF = " + selectedMonsterCard.getFinalAttack() +
                        ", " + selectedMonsterCard.getFinalDefence() + "\n" + selectedMonsterCard.getDescription();
            }
            return selectedCard.getName() + ":" + selectedCard.getDescription();
        }
    }

    public int getLastSummonedOrSetTurn() {
        return getMyBoard().getLastSummonedOrSetTurn();
    }

    public String singleRoundWin(String winnerUsername, int winnerScore, int looserScore) {
        return winnerUsername + " won the game\nand the score is: " + winnerScore + "-" + looserScore;
    }

    public void setLP() {
        getEnemyBoard().getUser().getPlayerLP().add(getEnemyBoard().getLP());
        getMyBoard().getUser().getPlayerLP().add(getMyBoard().getLP());
    }

    public String oneRoundWin(String winnerSideUsername, String loserSideUsername) {
        User winner = User.getUserByUsername(winnerSideUsername);
        winner.increaseScore(1000);
        winner.increaseMoney(winner.getMaxLP() + 1000);
        winner.clearLP();
        User loser = User.getUserByUsername(loserSideUsername);
        loser.increaseMoney(100);
        loser.clearLP();
        return winnerSideUsername + " won the whole match";
    }

    public String threeRoundWinner(String winnerUsername, String looserUsername, int winnerScore, int looserScore) {
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
        SpellEffectCanActivate spellEffectCanActivate = SpellEffectCanActivate.getInstance();
        SpellEffectActivate spellEffectActivate = SpellEffectActivate.getInstance();
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
            equipSpellRemoval(monsterCard);
        }
        if (monsterCard.isItHunted() && !monsterCard.isItControlledByChangeOfHeart()) {
            callOfHuntedRemoval(monsterCard);
        }
    }

    private void equipSpellRemoval(MonsterCard monsterCard) {
        int counter = 1;
        while (counter <= 2) {
            HashMap<Integer, Card> spellTerritory;
            ArrayList<Card> graveyard;
            if (counter == 1) {
                spellTerritory = duelWithUser.getMyBoard().getSpellAndTrapTerritory();
                graveyard = duelWithUser.getMyBoard().getGraveyard();
            } else {
                spellTerritory = duelWithUser.getEnemyBoard().getSpellAndTrapTerritory();
                graveyard = duelWithUser.getEnemyBoard().getGraveyard();
            }
            for (int i = 1; i < 6; i++) {
                Card spell = spellTerritory.get(i);
                if (spell != null) {
                    if (monsterCard.getEquipId().contains(spell.getEquipID())) {
                        graveyard.add(spell);
                        spellTerritory.put(i, null);
                    }
                }
            }
            counter++;
        }
        if (monsterCard.getEquipId().size() > 0) {
            monsterCard.getEquipId().subList(0, monsterCard.getEquipId().size()).clear();
        }
    }

    private void callOfHuntedRemoval(MonsterCard monsterCard) {
        int counter = 1;
        while (counter <= 2) {
            HashMap<Integer, Card> trapTerritory;
            ArrayList<Card> graveyard;
            if (counter == 1) {
                trapTerritory = duelWithUser.getMyBoard().getSpellAndTrapTerritory();
                graveyard = duelWithUser.getMyBoard().getGraveyard();
            } else {
                trapTerritory = duelWithUser.getEnemyBoard().getSpellAndTrapTerritory();
                graveyard = duelWithUser.getEnemyBoard().getGraveyard();
            }
            if (monsterCard.isItHunted() && !monsterCard.isItControlledByChangeOfHeart()) {
                for (int i = 1; i < 6; i++) {
                    Card trap = trapTerritory.get(i);
                    if (trap != null) {
                        if (trap.getName().equals("Call of the Haunted")) {
                            graveyard.add(trap);
                            trapTerritory.put(i, null);
                            break;
                        }
                    }
                }
            }
            counter++;
        }
    }

    public void increaseTempTurnCounter() {
        tempTurnCounter++;
    }

    public void decreaseTempTurnCounter() {
        tempTurnCounter--;
    }

    public void setTempTurnCounter(int tempTurnCounter) {
        this.tempTurnCounter = tempTurnCounter;
    }

    public String increaseMyLP(int amountOfLP) {
        getMyBoard().increaseLP(amountOfLP);
        return "LP of " + getMyBoard().getUser().getNickname() + " increased by " + amountOfLP;
    }

    public String setWinner(String nickname) {
        if (nickname.equals(getMyBoard().getUser().getNickname())) {
            return Output.IWon.toString();
        }
        return Output.ILost.toString();
    }

    public boolean isNicknameValid(String nickname) {
        if (getMyBoard().getUser().getNickname().equals(nickname)) {
            return true;
        }
        return getEnemyBoard().getUser().getNickname().equals(nickname);
    }

    public int getPhaseCounter() {
        return phaseCounter;
    }

    public Board[] getBoards() {
        return boards;
    }
}
