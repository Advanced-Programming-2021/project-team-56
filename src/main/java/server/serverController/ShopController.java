package server.serverController;

import server.model.Card;
import server.model.ServerUsers;
import server.User;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShopController {

    private static ShopController shopController;

    private ShopController() {
    }

    public static ShopController getInstance() {
        if (shopController == null)
            shopController = new ShopController();
        return shopController;
    }

    public String processBuyCard(String clientMessage) {
        Matcher matcher = Pattern.compile("Buy-Card (\\S+) \"([\\S\\s&&[^\"]]+)\"").matcher(clientMessage);
        if (matcher.find()) {
            return buyCard(matcher.group(1),matcher.group(2));
        }
        return "matcher failed";
    }

    public String processSellCard(String clientMessage) {
        Matcher matcher = Pattern.compile("Sell-Card (\\S+) \"([\\S\\s&&[^\"]]+)\"").matcher(clientMessage);
        if (matcher.find()) {
            return sell(matcher.group(1),matcher.group(2));
        }
        return "matcher failed";
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

    public String sell(String username, String cardName) {
        Card card = Card.getCardByName(cardName);
        User user = ServerUsers.getUserByUsername(username);
        HashMap<String, Integer> shopCards = Card.getShopCards();
        user.increaseMoney(card.getPrice());
        for (int i = 0; i < user.getUserAllCards().size(); i++) {
            if (user.getUserAllCards().get(i).getName().equals(cardName)) {
                user.getUserAllCards().remove(i);
                break;
            }
        }
        shopCards.put(cardName, shopCards.get(cardName) + 1);
        return "";
    }
}


