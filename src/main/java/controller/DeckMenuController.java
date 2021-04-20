package controller;

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

    private String createDeck(String deckName) {

    }

    private String deleteDeck(String deckName) {

    }

    private String setActive(String deckName) {

    }

    private String addCard(String deckName, String cardName) {

    }
}
