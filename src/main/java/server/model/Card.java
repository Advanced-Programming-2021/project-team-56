package server.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Card {
    private boolean isFacedUp;
    private String name;
    private String description;
    private int startEffectTurn = -1;
    private int price;
    private String imageURL;
    private int equipID;
    public static int id = 0;
    private final static ArrayList<Card> cards = new ArrayList<>();
    private final static HashMap<String, Integer> cardsAmount = new HashMap<>();

    public Card() {
    }

    public static HashMap<String, Integer> getShopCards() {
        return cardsAmount;
    }

    public void setEquipID(int equipID) {
        this.equipID = equipID;
    }

    public Card(String name, String description, String imageURL, int price) {
        this.name = name;
        this.description = description;
        this.imageURL = imageURL;
        this.price = price;
    }

    public static ArrayList<Card> getCards() {
        return cards;
    }

    public boolean getIsFacedUp() {
        return this.isFacedUp;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getStartEffectTurn() {
        return startEffectTurn;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setStartEffectTurn(int startEffectTurn) {
        this.startEffectTurn = startEffectTurn;
    }

    public int getPrice() {
        return price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public static Card getCardByName(String cardName) {
        for (Card card : cards) {
            if (card.getName().equals(cardName)) {
                return card;
            }
        }
        return null;
    }

    public void setFacedUp(boolean isFacedUp) {
        this.isFacedUp = isFacedUp;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getEquipID() {
        return equipID;
    }

}
