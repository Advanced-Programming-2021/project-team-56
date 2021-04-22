package model;

import java.util.ArrayList;

public class MonsterCard extends Card {
    private int level;
    private String attribute;
    private String type;
    private int attack;
    private int defence;
    private boolean isInAttackPosition;
    private String cardType;
    private String monsterType;

    private static ArrayList<MonsterCard> monsterCards = new ArrayList<>();

    public static ArrayList<MonsterCard> getMonsterCards() {
        return monsterCards;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public void setMonsterType(String monsterType){
        this.monsterType = monsterType;
    }

    public void setCardType(String cardType){
        this.cardType = cardType;
    }

    public void setAttack(int attack){
        this.attack = attack;
    }

    public void setDefence(int defence){
        this.defence = defence;
    }

    public static void setMonsterCards(ArrayList<MonsterCard> allMonsterCards){
        monsterCards = allMonsterCards;
    }

}
