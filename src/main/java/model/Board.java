package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Board {
    private ArrayList<Card> graveyard = new ArrayList<>();
    private Deck playerDeck;
    private HashMap<Integer, Card> spellAndTrapTerritory = new HashMap<>();
    private HashMap<Integer, MonsterCard> monsterTerritory = new HashMap<>();
    private ArrayList<Card> playerHand = new ArrayList<>();
    private User user;
    private Card fieldSpell;
    private Card selectedCard;
    private int LP = 8000;
    private int lastSummonedOrSetTurn;

    public Board(Deck playerDeck, User user) {
        this.playerDeck = new Deck(playerDeck);
        Collections.shuffle(this.playerDeck.getMainDeck());
        setCardsBoard(this);
        this.user = user;
        this.monsterTerritory.put(5, null);
        this.monsterTerritory.put(3, null);
        this.monsterTerritory.put(1, null);
        this.monsterTerritory.put(2, null);
        this.monsterTerritory.put(4, null);
        this.spellAndTrapTerritory.put(5, null);
        this.spellAndTrapTerritory.put(3, null);
        this.spellAndTrapTerritory.put(1, null);
        this.spellAndTrapTerritory.put(2, null);
        this.spellAndTrapTerritory.put(4, null);
    }

    public void setPlayerHand() {
        for (int i = 0; i < 5; i++) {
            playerHand.add(playerDeck.getMainDeck().get(i));
            playerDeck.getMainDeck().remove(i);
            //TOdo
        }
    }

    private void setCardsBoard(Board board) {
        ArrayList<Card> allCards = playerDeck.getMainDeck();
        for (Card card : allCards) {
            card.setBoard(board);
        }
    }

    ///////////////////?
    public void setCardsOpponentBoard(Board opponentBoard) {
        for (Card card : playerDeck.getMainDeck()) {
            card.setOpponentBoard(opponentBoard);
        }
        for (int i = 0; i < 5; i++) {
            playerDeck.getMainDeck().remove(0);
        }
    }

    public void addCardToGraveyard(Card card) {

    }

    public Card getSelectedCard() {
        return selectedCard;
    }

    public User getUser() {
        return user;
    }

    public Card getFieldSpell() {
        return fieldSpell;
    }

    public int getLP() {
        return LP;
    }

    public HashMap<Integer, MonsterCard> getMonsterTerritory() {
        return monsterTerritory;
    }

    public HashMap<Integer, Card> getSpellAndTrapTerritory() {
        return spellAndTrapTerritory;
    }

    public void setSelectedCard(Card selectedCard) {
        this.selectedCard = selectedCard;
    }

    public void addCardToMonsterTerritory(Card card) {

    }

    public ArrayList<Card> getMainDeck() {
        return playerDeck.getMainDeck();
    }

    public ArrayList<Card> getPlayerHand() {
        return playerHand;
    }

    public ArrayList<Card> getGraveyard() {
        return graveyard;
    }

    public int getLastSummonedOrSetTurn() {
        return lastSummonedOrSetTurn;
    }

    public void setLastSummonedOrSetTurn(int turn) {
        lastSummonedOrSetTurn = turn;
    }

    public void setLP(int LP) {
        this.LP = LP;
    }
}
