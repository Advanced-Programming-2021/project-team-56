package model;

import java.util.ArrayList;

public class Card {
    private boolean isFacedUp;
    private String name;
    private String description;
    private int startEffectTurn = -1;
    private int price;
    private static ArrayList<Card> cards = new ArrayList<>();
    private boolean isItScanner;
    private int equipID;
    public static int id = 0;
    private boolean isItInChainLink;

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

    public boolean isItInChainLink() {
        return isItInChainLink;
    }

    public void setItInChainLink(boolean itInChainLink) {
        isItInChainLink = itInChainLink;
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
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getName().equals(cardName)) {
                return cards.get(i);
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
