package controller;

import model.Deck;
import model.User;

public class DeckMenuController {

    private static DeckMenuController deckMenuController;

    private DeckMenuController() {
    }

    public static DeckMenuController getInstance() {
        if (deckMenuController == null)
            deckMenuController = new DeckMenuController();
        return deckMenuController;
    }

    public String showUsersDecks(String username) {

    }

    public String showUsersDeck(String username, String deckName) {

    }

    public String createDeck(String deckName, String username) {
        User user = User.getUserByUsername(username);
        if (user.isDeckWithThisNameExistent(deckName)) {
            return "deck with name " + deckName + " already exists";
        }
        Deck newDeck = new Deck(deckName);
        user.addDeckToDecks(newDeck);
        return "deck created successfully!";
    }

    public String deleteDeck(String deckName, String username) {
        User user = User.getUserByUsername(username);
        if (user.isDeckWithThisNameExistent(deckName)) {
            user.removeDeckFromDecks(user.getDeckByDeckName(deckName));
            return "deck deleted successfully";
        }
        return "deck with name " + deckName + " does not exist";
    }

    public String setActive(String deckName, String username) {
        User user = User.getUserByUsername(username);
        if (user.isDeckWithThisNameExistent(deckName)) {
            Deck targetDeck = user.getDeckByDeckName(deckName);
            user.setDeckActivate(targetDeck);
            return "deck activated successfully";
        }
        return "deck with name " + deckName + " does not exist";
    }
    //TODO This to add card methods has so much in common, use another method to reduce the duplicated codes
    public String addCardToSideDeck(String deckName, String cardName, String username) {
        User user = User.getUserByUsername(username);
        if (user.isCardWithThisNameExistent(cardName)) {
            if (user.isDeckWithThisNameExistent(deckName)) {
                Deck deck = user.getDeckByDeckName(deckName);
                if (!deck.isSideDeckFull()) {
                    if (!deck.isThreeCardsWithThisNameInDeck(cardName)) {
                        deck.addCardToSideDeck(cardName);
                        return "card added successfully";
                    }
                    return "there are already three cards with name " + cardName + " in deck " + deckName;
                }
                return "side deck is full";
            }
            return "deck with name " + deckName + " does not exist";
        }
        return "card with name " + cardName + " does not exists";
    }

    public String addCardToMainDeck(String deckName, String cardName, String username) {
        User user = User.getUserByUsername(username);
        if (user.isCardWithThisNameExistent(cardName)) {
            if (user.isDeckWithThisNameExistent(deckName)) {
                Deck deck = user.getDeckByDeckName(deckName);
                if (!deck.isMainDeckFull()) {
                    if (!deck.isThreeCardsWithThisNameInDeck(cardName)) {
                        deck.addCardToMainDeck(cardName);
                        return "card added successfully";
                    }
                    return "there are already three cards with name " + cardName + " in deck " + deckName;
                }
                return "main deck is full";
            }
            return "deck with name " + deckName + " does not exist";
        }
        return "card with name " + cardName + " does not exists";
    }
}
