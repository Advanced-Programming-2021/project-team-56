package model;

import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> mainDeck;
    private ArrayList<Card> sideDeck;
    private boolean isActivated;
    private String deckName;


    public Deck(String deckName) {
        this.deckName = deckName;
    }

    public String getDeckName() {
        return this.deckName;
    }

    public void setDeckActive() {
        this.isActivated = true;
    }

    public void setDeckInactive() {
        this.isActivated = false;
    }

    public boolean isDeckActivated() {
        return this.isActivated;
    }

    public void addCardToSideDeck(String cardName) {

    }

    public void addCardToMainDeck(String cardName) {

    }
}
