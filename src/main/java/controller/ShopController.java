package controller;

import model.Card;
import model.MonsterCard;
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

    public String checkCardShowCommand(String command) {
        Matcher matcher = cardShow.matcher(command);
        if (matcher.find()) {
            String cardName = matcher.group(1);
            for (Card card : Card.getCards()) {
                if (card.getName().equals(cardName)) {
                    return card.toString();
                }
            }
            return "card with this name, could not be found!";
        }
        return "invalid command";
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

    public  String buyCard(String cardName, String username) {
        if (!isThereAnyCardWithThisName(cardName)) {
            return "there is no card with this name";
        }
        Card card = Card.getCardByName(cardName);
        if (card.getPrice() > User.getUserByUsername(username).getMoney()) {
            return "not enough money";
        }
        User.getUserByUsername(username).decreaseMoney(card.getPrice());
        User.getUserByUsername(username).addCardToUserAllCards(card);
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

    public String increaseMoney(String username, String amount) {
        int moneyAmount = Integer.parseInt(amount);
        User user = User.getUserByUsername(username);
        user.increaseMoney(moneyAmount);
        return "your money increased by the value of " + amount + "!";
    }
}


