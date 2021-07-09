package controller;

import server.model.Card;
import server.model.User;

public class ShopController {

    private static ShopController shopController;

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


