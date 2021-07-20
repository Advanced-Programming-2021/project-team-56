package server.serverController;

import server.model.Card;
import server.model.ServerUsers;
import server.User;

import java.util.HashMap;

public class ShopController {

    private static ShopController shopController;

    private ShopController() {
    }

    public static ShopController getInstance() {
        if (shopController == null)
            shopController = new ShopController();
        return shopController;
    }

    public String buyCard(String username, String cardName) {
        Card card = Card.getCardByName(cardName);
        User user = ServerUsers.getUserByUsername(username);
        HashMap<String, Integer> shopCards = Card.getShopCards();
        if (shopCards.get(cardName) == 0) {
            return "right now this card is not available in shop";
        }
        if (card.getPrice() > user.getMoney()) {
            return "not enough money";
        }
        user.decreaseMoney(card.getPrice());
        user.addCardToUserAllCards(card);
        shopCards.put(cardName, shopCards.get(cardName) - 1);
        return "";
    }
}


