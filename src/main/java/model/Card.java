package model;

import java.util.ArrayList;

public class Card {

    private static ArrayList<Card> cards;
    public static int id;

    static {
        cards = new ArrayList<>();
        id = 0;
    }

    private boolean isFacedUp;
    private String name;
    private String description;
    private int startEffectTurn = -1;
    private int price;
    private boolean isItScanner;
    private int equipID;

    public Card() {
    }

    public void setEquipID(int equipID) {
        this.equipID = equipID;
    }

    public Card(String name, String description, int price) {
        this.name = name;
        this.description = description;
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

    public void setItScanner(boolean isItScanner) {
        this.isItScanner = isItScanner;
    }

    public boolean getItScanner() {
        return isItScanner;
    }

    public int getEquipID() {
        return equipID;
    }

}
