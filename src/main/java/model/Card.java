package model;

public class Card {
    private boolean isFacedUp;
    private String name;
    private String description;
    private int cardNumber;

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDescription(String description){
        this.description = description;
    }
}
