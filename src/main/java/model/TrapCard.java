package model;

import java.util.ArrayList;

public class TrapCard extends Card {
    private static ArrayList<TrapCard> trapCards = new ArrayList<>();
    private String icon;
    private String status;
    private String type;
    private int setTurn;

    public TrapCard() {

    }

    public TrapCard(Card card) {
        super(card.getName(), card.getDescription(), card.getPrice());
        TrapCard trapCard = (TrapCard) card;
        this.type = trapCard.getType();
        this.icon = trapCard.getIcon();
        this.status = trapCard.getStatus();
    }

    public static ArrayList<TrapCard> getTrapCards() {
        return trapCards;
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

    public void setSetTurn(int turn) {
        this.setTurn = turn;
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
        return this.setTurn;
    }

    @Override
    public String toString() {
        return "Name: " + this.getName() + "\n" +
                "Trap" + "\n" +
                "Type" + this.getType() + "\n" +
                "Description: " + this.getDescription();
    }
}
