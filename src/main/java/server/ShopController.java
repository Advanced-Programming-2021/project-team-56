package server;

import model.Card;

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

    public String getNumberOfCardInUsersCards(String cardName) {
        return User.getCurrentUser().getNumberOfCardsInUsersAllCards(cardName);
    }
}


