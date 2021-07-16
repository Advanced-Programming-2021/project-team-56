package model;

import server.model.Card;

import java.util.ArrayList;

public class Deck {
    private final ArrayList<Card> mainDeck = new ArrayList<>();
    private final ArrayList<Card> sideDeck = new ArrayList<>();
    private final ArrayList<Card> deckCards = new ArrayList<>();
    private final ArrayList<Card> userCards = new ArrayList<>();
    private boolean isActivated = false;
    private String deckName;

    public Deck(String deckName) {
        this.deckName = deckName;
    }

    public Deck(Deck deck) {
        for (Card card : deck.getMainDeck()) {
            if (card instanceof MonsterCard) {
                mainDeck.add(new MonsterCard(card));
            } else if (card instanceof SpellCard) {
                mainDeck.add(new SpellCard(card));
            } else {
                mainDeck.add(new TrapCard(card));
            }
        }
        for (Card card : deck.getSideDeck()) {
            if (card instanceof MonsterCard) {
                sideDeck.add(new MonsterCard(card));
            } else if (card instanceof SpellCard) {
                sideDeck.add(new SpellCard(card));
            } else {
                sideDeck.add(new TrapCard(card));
            }
        }
    }

    public String getDeckName() {
        return this.deckName;
    }

    public ArrayList<Card> getUserCards() {
        return userCards;
    }

    public String getNumberOfCardsInUserCards(String cardName) {
        int cardCount = 0;
        for (Card card : userCards) {
            if (card.getName().equals(cardName)) {
                cardCount++;
            }
        }
        return String.valueOf(cardCount);
    }

    public void setDeckActive() {
        this.isActivated = true;
    }

    public void setDeckInactive() {
        this.isActivated = false;
    }

    public ArrayList<Card> getMainDeck() {
        return mainDeck;
    }

    public ArrayList<Card> getSideDeck() {
        return sideDeck;
    }

    public ArrayList<Card> getDeckCards() {
        return deckCards;
    }

    public boolean isDeckActivated() {
        return this.isActivated;
    }

    public boolean isDeckValid() {
        return this.mainDeck.size() >= 40;
    }

    public boolean isMainDeckFull() {
        return mainDeck.size() == 60;
    }

    public boolean isSideDeckFull() {
        return sideDeck.size() == 15;
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

    public boolean isCardWithThisNameExistent(String cardName) {
        for (Card card : userCards) {
            if (card.getName().equals(cardName)) {
                return true;
            }
        }
        return false;
    }

    public void addCardToSideDeck(String cardName) {
        for (Card card : userCards) {
            if (card.getName().equals(cardName)) {
                sideDeck.add(card);
                deckCards.add(card);
                userCards.remove(card);
                return;
            }
        }
    }

    public void addCardToMainDeck(String cardName) {
        for (Card card : userCards) {
            if (card.getName().equals(cardName)) {
                mainDeck.add(card);
                deckCards.add(card);
                userCards.remove(card);
                return;
            }
        }
    }
}
