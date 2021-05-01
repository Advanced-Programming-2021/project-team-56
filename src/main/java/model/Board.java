package model;

import java.util.ArrayList;
import java.util.Collections;

public class Board {
    private ArrayList<Card> graveyardPlayer = new ArrayList<>();
    private Deck playerDeck = new Deck();
    private ArrayList<Card> spellAndTrapTerritory = new ArrayList<>();
    private ArrayList<Card> monsterTerritory = new ArrayList<>();
    private ArrayList<Card> playerHand = new ArrayList<>();
    private Card fieldSpell;
    private int LP = 8000;

    public Board(Deck playerDeck) {
        this.playerDeck = playerDeck;
        Collections.shuffle(this.playerDeck.getMainDeck());
        setPlayerHand();
    }

    private void setPlayerHand() {
        for (int i = 0; i < 5; i++) {
            playerHand.add(playerDeck.getMainDeck().get(i));
        }
    }

    public void addCardToGraveyard(Card card) {

    }

    public void addCardToMonsterTerritory(Card card) {

    }
}
