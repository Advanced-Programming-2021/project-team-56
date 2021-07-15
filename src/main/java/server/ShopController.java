package server;

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
        if (card.getPrice() > user.getMoney()) {
            return "not enough money";
        }
        user.decreaseMoney(card.getPrice());
        user.addCardToUserAllCards(card);
        return "";
    }
}


