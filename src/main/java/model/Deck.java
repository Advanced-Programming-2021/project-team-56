package model;

import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> mainDeck;
    private ArrayList<Card> sideDeck;
    private ArrayList<Card> deckCards;
    private boolean isActivated;
    private String deckName;

    {
        mainDeck = new ArrayList<>();
        sideDeck = new ArrayList<>();
        deckCards = new ArrayList<>();
    }

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

    public boolean isMainDeckFull() {
        if (mainDeck.size() == 60) {
            return true;
        }
        return false;
    }

    public boolean isSideDeckFull() {
        if (sideDeck.size() == 15) {
            return true;
        }
        return false;
    }

    public boolean isThreeCardsWithThisNameInDeck(String cardName) {
        int cardCounter = 0;
        for (Card card : deckCards) {
            if (card.getName().equals(cardName)) {
                cardCounter++;
                if (cardCounter == 3) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isCardWithThisNameInDeck(String cardName) {
        //TODO Shit code
        for (Card card : deckCards) {
            if (card.getName().equals(cardName)) {
                return true;
            }
        }
        return false;
    }

    public void addCardToSideDeck(String cardName) {

    }

    public void addCardToMainDeck(String cardName) {

    }
}
