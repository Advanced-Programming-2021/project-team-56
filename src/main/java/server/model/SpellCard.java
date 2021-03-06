package server.model;

import java.util.ArrayList;

public class SpellCard extends Card {
    private String type;
    private String icon;
    private String status;
    private int setTurn;
    private final static ArrayList<SpellCard> spellCards = new ArrayList<>();

    public SpellCard() {

    }

    public SpellCard(String name, String description, String imageURL, int price) {
        super(name, description, imageURL, price);
    }

    public SpellCard(Card card) {
        super(card.getName(), card.getDescription(), card.getImageURL(), card.getPrice());
        SpellCard spellCard = (SpellCard) card;
        this.type = spellCard.getType();
        this.icon = spellCard.getIcon();
        this.status = spellCard.getStatus();
    }

    public static ArrayList<SpellCard> getSpellCards() {
        return spellCards;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public String getIcon() {
        return icon;
    }

    public int getSetTurn() {
        return setTurn;
    }

    public void setSetTurn(int setTurn) {
        this.setTurn = setTurn;
    }

    @Override
    public String toString() {
        return "Name: " + this.getName() + "\n" +
                "Spell" + "\n" +
                "Type" + this.getType() + "\n" +
                "Description: " + this.getDescription();
    }
}
