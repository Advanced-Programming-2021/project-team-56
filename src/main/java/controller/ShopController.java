package controller;

import model.Card;
import model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ShopController {

    private static ShopController shopController;

    private ShopController() {
    }

    public static ShopController getInstance() {
        if (shopController == null)
            shopController = new ShopController();
        return shopController;
    }

    public String showAllCards() {
        ArrayList<Card> cards = Card.getCards();
        Comparator<Card> comparator = Comparator.comparing(Card::getName);
        Collections.sort(cards, comparator);
        StringBuilder cardsDemo = new StringBuilder();
        for (int i = 0; i < cards.size(); i++) {
            cardsDemo.append(cards.get(i).getName() + ":" + cards.get(i).getPrice() + "\n");
        }
        return cardsDemo.toString();
    }

    public  String buyCard(String cardName, String customer) {
        if (!isThereAnyCardWithThisName(cardName)) {
            return "there is no card with this name";
        }
        Card card = Card.getCardByName(cardName);
        if (card.getPrice() > User.getUserByUsername(customer).getMoney()) {
            return "not enough money";
        }
        if (card.getName().equals("Scanner")){
            card.setItScanner(true);
        }
        User.getUserByUsername(customer).decreaseMoney(card.getPrice());
        User.getUserByUsername(customer).addCardToUserAllCards(card);
        return "";
    }

    private boolean isThereAnyCardWithThisName(String cardName) {
        for (int i = 0; i < Card.getCards().size(); i++) {
            if (Card.getCards().get(i).getName().equals(cardName)) {
                return true;
            }
        }
        return false;
    }
}
