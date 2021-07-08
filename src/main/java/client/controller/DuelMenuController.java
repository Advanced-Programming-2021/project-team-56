package controller;

import model.Deck;
import model.User;

public class DuelMenuController {

    private static DuelMenuController duelMenuController;

    private DuelMenuController() {
    }

    public static DuelMenuController getInstance() {
        if (duelMenuController == null)
            duelMenuController = new DuelMenuController();
        return duelMenuController;
    }

    public String canUsersDuel(String firstPlayerUsername, String secondPlayerUsername, String numberOfRounds) {
        User firstPlayer = User.getUserByUsername(firstPlayerUsername);
        User secondPlayer = User.getUserByUsername(secondPlayerUsername);
        if (!User.isThisUsernameAlreadyTaken(secondPlayerUsername)) {
            return "there is no player with this username";
        }
        if (!isPlayersDeckActive(firstPlayer)) {
            return firstPlayerUsername + " has no active deck";
        }
        if (!isPlayersDeckActive(secondPlayer)) {
            return secondPlayerUsername + " has no active deck";
        }
        if (!isPlayersDeckValid(firstPlayer)) {
            return firstPlayerUsername + "'s deck is invalid";
        }
        if (!isPlayersDeckValid(secondPlayer)) {
            return secondPlayerUsername + "'s deck is invalid";
        }
        if (!isRoundsNumberValid(numberOfRounds)) {
            return "number of rounds is not supported";
        }
        return "duel is valid";
    }

    private boolean isPlayersDeckActive(User user) {
        for (Deck deck : user.getDecks()) {
            if (deck.isDeckActivated()) {
                return true;
            }
        }
        return false;
    }

    private boolean isPlayersDeckValid(User user) {
        for (Deck deck : user.getDecks()) {
            if (deck.isDeckActivated()) {
                return deck.isDeckValid();
            }
        }
        return false;
    }

    private boolean isRoundsNumberValid(String numberOfRounds) {
        return numberOfRounds.equals("1") || numberOfRounds.equals("3");
    }
}
