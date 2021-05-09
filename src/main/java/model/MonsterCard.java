package model;

import java.util.ArrayList;

public class MonsterCard extends Card {
    private int level;
    private String attribute;
    private int attack;
    private int finalAttack;
    private int defence;
    private int finalDefence;
    private boolean isInAttackPosition;
    private int lastTimeChangedPositionTurn;
    private int lastTimeAttackedTurn;
    private int summonedTurn;
    private String cardType;
    private String monsterType;

    public MonsterCard() {

    }

    public MonsterCard(Card card) {
        super(card.getName(), card.getDescription(), card.getPrice());
        MonsterCard monsterCard = (MonsterCard) card;
        this.attack = monsterCard.getAttack();
        this.defence = monsterCard.getDefence();
        this.level = monsterCard.getLevel();
        this.monsterType = monsterCard.getMonsterType();
        this.attribute = monsterCard.getAttribute();
        this.cardType = monsterCard.getCardType();
        setFinalAttack(this.attack);
        setFinalDefence(this.defence);
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

    public void setFinalDefence(int defence) {
        this.finalDefence = defence;
    }

    public void setFinalAttack(int attack) {
        this.finalAttack = attack;
    }

    public void increaseFinalAttack(int deltaAttack) {
        this.finalAttack += deltaAttack;
    }

    public void decreaseFinalAttack(int deltaAttack) {
        this.finalAttack -= deltaAttack;
    }

    public void increaseFinalDefence(int deltaDefence) {
        this.finalDefence += deltaDefence;
    }

    public void decreaseFinalDefence(int deltaDefence) {
        this.finalDefence -= deltaDefence;
    }

    public void setMonsterType(String monsterType) {
        this.monsterType = monsterType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public int getAttack() {
        return attack;
    }

    public int getFinalDefence() {
        return finalDefence;
    }

    public int getFinalAttack() {
        return finalAttack;
    }

    public int getDefence() {
        return defence;
    }

    public int getLevel() {
        return level;
    }

    public String getMonsterType() {
        return monsterType;
    }

    public String getAttribute() {
        return attribute;
    }

    public String getCardType() {
        return cardType;
    }

    public boolean getIsInAttackPosition() {
        return this.isInAttackPosition;
    }

    public void setSummonedTurn(int summonedTurn) {
        this.summonedTurn = summonedTurn;
    }

    public void setInAttackPosition(boolean isInAttackPosition){
        this.isInAttackPosition = isInAttackPosition;
    }

    public int getLastTimeChangedPositionTurn() {
        return lastTimeChangedPositionTurn;
    }

    public int getLastTimeAttackedTurn() {
        return lastTimeAttackedTurn;
    }

    public void setLastTimeChangedPositionTurn(int lastTimeChangedPositionTurn) {
        this.lastTimeChangedPositionTurn = lastTimeChangedPositionTurn;
    }

    public int getSummonedTurn() {
        return summonedTurn;
    }

    public void setLastTimeAttackedTurn(int lastTimeAttackedTurn) {
        this.lastTimeAttackedTurn = lastTimeAttackedTurn;
    }

    @Override
    public String toString() {
        return "Name: " + this.getName() + "\n" +
                "Level: " + this.getLevel() + "\n" +
                "Type: " + this.getCardType() + "\n" +
                "ATK: " + this.getAttack() + "\n" +
                "DEF: " + this.getDefence() + "\n" +
                "Description: " + this.getDescription();
    }
}
