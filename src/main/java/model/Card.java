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
        for (int i = 0; i < spellCards.size(); i++) {
            cards.add(spellCards.get(i));
        }
    }

    public static void addTrapCards(ArrayList<TrapCard> trapCards) {
        for (int i = 0; i < trapCards.size(); i++) {
            cards.add(trapCards.get(i));
        }
    }

    public static void addMonsterCards(ArrayList<MonsterCard> monsterCards) {
        for (int i = 0; i < monsterCards.size(); i++) {
            cards.add(monsterCards.get(i));
        }
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
