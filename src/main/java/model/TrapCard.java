package model;

import java.util.ArrayList;

public class TrapCard extends Card {
    private static ArrayList<TrapCard> trapCards = new ArrayList<>();
    private String icon;
    private String status;
    private String type;

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

    public static void setTrapCards(ArrayList<TrapCard> allTrapCards) {
        trapCards = allTrapCards;
    }
}
