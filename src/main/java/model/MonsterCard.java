package model;

import java.util.ArrayList;

public class MonsterCard extends Card {
    private int level;
    private String attribute;
    private int attack;
    private int defence;
    private boolean isInAttackPosition;
    private int lastChangedPositionTurn;
    private int lastTimeAttackedTurn;
    private int summonedTurn;
    private String cardType;
    private String monsterType;

    public MonsterCard(){

    }

    public MonsterCard(Card card, int id){
        super(card.getName(), card.getDescription(), card.getPrice(), id);
        MonsterCard monsterCard = (MonsterCard) card;
        this.attack = monsterCard.getAttack();
        this.defence = monsterCard.getDefence();
        this.level = monsterCard.getLevel();
        this.monsterType = monsterCard.getMonsterType();
        this.attribute = monsterCard.getAttribute();
        this.cardType = monsterCard.getCardType();
    }

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

    public int getAttack(){
        return attack;
    }

    public int getDefence(){
        return defence;
    }

    public int getLevel(){
        return level;
    }

    public String getMonsterType(){
        return monsterType;
    }

    public String getAttribute(){
        return attribute;
    }

    public String getCardType(){
        return cardType;
    }

    public boolean getIsInAttackPosition() {
        return this.isInAttackPosition;
    }

}
