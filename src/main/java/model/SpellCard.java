package model;

import java.util.ArrayList;

public class SpellCard extends Card {
    private String type;
    private String icon;
    private String status;
    private ArrayList<Card> effectedCards;
    private static ArrayList<SpellCard> spellCards = new ArrayList<>();

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

    public static void setSpellCards(ArrayList<SpellCard> allSpellCards) {
        spellCards = allSpellCards;
    }
}
