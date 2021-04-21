package model;

public class Card {
    private boolean isFacedUp;
    private String name;
    private String description;
    private int cardNumber;
    private int price;

    public String getName() {
        return name;
    }

    public String getDescription(){
        return description;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setPrice(int price){
        this.price = price;
    }
}
