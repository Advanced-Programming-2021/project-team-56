package controller;

import model.Card;
import model.Deck;
import model.MonsterCard;
import model.User;

import java.util.ArrayList;
import java.util.Comparator;

public class DeckMenuController {

    private static DeckMenuController deckMenuController;

    private DeckMenuController() {
    }

    public static DeckMenuController getInstance() {
        if (deckMenuController == null)
            deckMenuController = new DeckMenuController();
        return deckMenuController;
    }

    public String createDeck(String deckName) {
        User user = User.getCurrentUser();
        if (user.isDeckWithThisNameExistent(deckName)) {
            return "deck with name " + deckName + " already exists";
        }
        Deck newDeck = new Deck(deckName);
        for (Card card : user.getUserAllCards()) {
            newDeck.getUserCards().add(card);
        }
        user.addDeckToDecks(newDeck);
        return "deck created successfully!";
    }

    public void deleteDeck(String deckName) {
        User user = User.getCurrentUser();
        user.removeDeckFromDecks(user.getDeckByDeckName(deckName));
    }

    public void setActive(String deckName) {
        User user = User.getCurrentUser();
        Deck targetDeck = user.getDeckByDeckName(deckName);
        user.setDeckActivate(targetDeck);
    }

    public String addToDeck(String deckName, String cardName, boolean isAddToSide) {
        User user = User.getCurrentUser();
        Deck deck = user.getDeckByDeckName(deckName);
        if (isAddToSide) {
            if (deck.isSideDeckFull()) {
                return "Side deck is full";
            }
        } else {
            if (deck.isMainDeckFull()) {
                return "Main deck is full";
            }
        }
        if (deck.isThreeCardsWithThisNameInDeck(cardName)) {
            return "There are already three cards with name\n" + cardName + "\nin deck " + deckName;
        }
        if (isAddToSide) {
            deck.addCardToSideDeck(cardName);
        } else {
            deck.addCardToMainDeck(cardName);
        }
        return "Card added successfully";
    }

    public String removeFromSideDeck(String deckName, String cardName, String username) {
        User user = User.getUserByUsername(username);
        if (!user.isDeckWithThisNameExistent(deckName)) {
            return "deck with name " + deckName + " does not exist";
        }
        Deck deck = user.getDeckByDeckName(deckName);
        for (Card card : deck.getSideDeck()) {
            if (card.getName().equals(cardName)) {
                deck.getSideDeck().remove(card);
                deck.getDeckCards().remove(card);
                deck.getUserCards().add(card);
                return "card removed from deck successfully";
            }
        }
        return "card with name " + cardName + " does not exist in side deck";
    }

    public String removeFromMainDeck(String deckName, String cardName) {
        User user = User.getCurrentUser();
        Deck deck = user.getDeckByDeckName(deckName);
        for (Card card : deck.getMainDeck()) {
            if (card.getName().equals(cardName)) {
                deck.getMainDeck().remove(card);
                deck.getDeckCards().remove(card);
                deck.getUserCards().add(card);
                return "card removed from deck successfully";
            }
        }
        return "card with name " + cardName + " does not exist in main deck";
    }

    public String getUsersDeckInformation() {
        StringBuilder decksInformation = new StringBuilder();
        for (Deck deck : User.getCurrentUser().getDecks()) {
            decksInformation.append(deck.getDeckName()).append(" ")
                    .append(deck.getDeckCards().size()).append("\n");
        }
        return decksInformation.toString();
    }
}
