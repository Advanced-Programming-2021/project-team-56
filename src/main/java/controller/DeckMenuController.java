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

    private String addCard(String deckName, String cardName) {

    }
}
