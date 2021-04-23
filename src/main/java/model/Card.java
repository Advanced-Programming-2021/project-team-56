package model;

import java.util.ArrayList;

public class Card {
    private boolean isFacedUp;
    private String name;
    private String description;
    private int cardNumber;
    private int price;
    private static ArrayList<Card> cards = new ArrayList<>();

    public static ArrayList<Card> getCards() {
        return cards;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
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

    public static void addSpellCards(ArrayList<SpellCard> spellCards) {
        cards.addAll(spellCards);
    }

    public static void addTrapCards(ArrayList<TrapCard> trapCards) {
        cards.addAll(trapCards);
    }

    public static void addMonsterCards(ArrayList<MonsterCard> monsterCards) {
        cards.addAll(monsterCards);
    }

    public int getPrice(){
        return price;
    }

    public static Card getCardByName(String cardName){
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getName().equals(cardName)){
                return cards.get(i);
            }
        }
        return null;
    }
}
