package controller;

import server.model.Card;
import server.model.Deck;
import server.model.MonsterCard;
import server.model.User;

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

    public String showUsersDecks(String username) {
        User user = User.getUserByUsername(username);
        String result = "Decks:\nActive deck:\n";
        StringBuilder activeDeck = new StringBuilder();
        StringBuilder inActiveDecks = new StringBuilder();
        for (Deck deck : user.getDecks()) {
            String validity;
            if (deck.getMainDeck().size() < 40) validity = "invalid";
            else validity = "valid";
            if (deck.isDeckActivated()) {
                activeDeck.append(deck.getDeckName()).append(": main deck ")
                        .append(deck.getMainDeck().size()).append(", side deck ")
                        .append(deck.getSideDeck().size()).append(", ").append(validity).append("\n");
            } else {
                inActiveDecks.append(deck.getDeckName()).append(": main deck ")
                        .append(deck.getMainDeck().size()).append(", side deck ")
                        .append(deck.getSideDeck().size()).append(", ").append(validity).append("\n");
            }
        }
        result += activeDeck + "Other decks:\n" + inActiveDecks;
        return result;
    }

    public String showMainOrSideDeck(String deckName, String username, String mainOrSide) {
        User user = User.getUserByUsername(username);
        if (!user.isDeckWithThisNameExistent(deckName)) {
            return "deck with name " + deckName + " does not exist\n";
        }
        Deck deck = user.getDeckByDeckName(deckName);
        ArrayList<Card> cards;
        if (mainOrSide.equals("side")) cards = deck.getSideDeck();
        else cards = deck.getMainDeck();
        Comparator<Card> comparator = Comparator.comparing(Card::getName);
        cards.sort(comparator);
        String result = "Deck: " + deckName + "\nSide deck:\nMonsters:\n";
        StringBuilder monsters = new StringBuilder();
        StringBuilder spellsAndTraps = new StringBuilder();
        for (Card card : cards) {
            if (card instanceof MonsterCard) {
                monsters.append(card.getName()).append(": ").append(card.getDescription()).append("\n");
            } else {
                spellsAndTraps.append(card.getName()).append(": ").append(card.getDescription()).append("\n");
            }
        }
        result += monsters + "Spells and Traps:\n" + spellsAndTraps;
        return result;
    }

    public String showCards(String username) {
        User user = User.getUserByUsername(username);
        StringBuilder result = new StringBuilder();
        for (Card card : user.getUserAllCards()) {
            result.append(card.getName()).append(":").append(card.getDescription()).append("\n");
        }
        return result.toString();
    }
}
