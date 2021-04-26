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
        if (User.isThisUsernameAlreadyTaken(secondPlayerUsername)) {
            if (isPlayersDeckActive(firstPlayer)) {
                if (isPlayersDeckActive(secondPlayer)) {
                    if (isPlayersDeckValid(firstPlayer)) {
                        if (isPlayersDeckValid(secondPlayer)) {
                            if (isRoundsNumberValid(numberOfRounds)) {
                                return "duel is valid";
                            }
                            return "number of rounds is not supported";
                        }
                        return secondPlayerUsername + "'s deck is invalid";
                    }
                    return firstPlayerUsername + "'s deck is invalid";
                }
                return secondPlayerUsername + " has no active deck";
            }
            return firstPlayerUsername + " has no active deck";
        }
        return "there is no player with this username";
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
                if (deck.isDeckValid()) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    private boolean isRoundsNumberValid(String numberOfRounds) {
        if (numberOfRounds.equals("1") || numberOfRounds.equals("3")) {
            return true;
        }
        return false;
    }

    public String canUserDuel(String username, String numberOfRounds) {
        User user = User.getUserByUsername(username);
        if (isPlayersDeckActive(user)) {
            if (isPlayersDeckValid(user)) {
                if (isRoundsNumberValid(numberOfRounds)) {
                    return "duel is valid";
                }
                return "number of rounds is not supported";
            }
            return username + "'s deck is invalid";
        }
        return username + " has no active deck";
    }
}
