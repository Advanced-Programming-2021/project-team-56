package controller;

import model.Card;
import model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShopController {

    private static ShopController shopController;
    static Pattern cardShow = Pattern.compile("^card show ([\\S][\\S ]*)$");

    private ShopController() {
    }

    public static ShopController getInstance() {
        if (shopController == null)
            shopController = new ShopController();
        return shopController;
    }

    public String buyCard(String cardName) {
        Card card = Card.getCardByName(cardName);
        if (card.getPrice() > User.getCurrentUser().getMoney()) {
            return "not enough money";
        }
        User.getCurrentUser().decreaseMoney(card.getPrice());
        User.getCurrentUser().addCardToUserAllCards(card);
        return "";
    }

    public String increaseMoney(String username, String amount) {
        int moneyAmount = Integer.parseInt(amount);
        User user = User.getUserByUsername(username);
        user.increaseMoney(moneyAmount);
        return "your money increased by the value of " + amount + "!";
    }

    public String getNumberOfCardInUsersCards(String cardName) {
        return User.getCurrentUser().getNumberOfCardsInUsersAllCards(cardName);
    }
}


