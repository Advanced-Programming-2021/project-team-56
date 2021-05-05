package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Card {
    private boolean isFacedUp;
    private String name;
    private String description;
    private int price;
    private static ArrayList<Card> cards = new ArrayList<>();
    private int id;

    //TODO set IEffectID for monster cards without effect 0 in CSV File and with Effects, their own effectID and set it here
    private Board currentBoard;
    private Board opponentBoard;
    private int IEffectID;
    private IEffect cardEffect;
    private ArrayList<Card> effectedCards = new ArrayList<>();

    public Card() {
    }

    public Card(String name, String description, int price, int id) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.id = id;
    }

    public static ArrayList<Card> getCards() {
        return cards;
    }

    public ArrayList<Card> getEffectedCards() {
        return effectedCards;
    }

    public int getId(){
        return id;
    }

    public IEffect getCardEffect() {
        return cardEffect;
    }

    public boolean getIsFacedUp() {
        return this.isFacedUp;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Board getCurrentBoard() {
        return currentBoard;
    }

    public Board getOpponentBoard() {
        return opponentBoard;
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

    public void setBoard(Board board) {
        this.currentBoard = board;
    }

    public void setOpponentBoard(Board opponentBoard) {
        this.opponentBoard = opponentBoard;
    }

    public void setCardEffect() {
        //TODO Amirali begir bzar to excel
        cardEffect = Effect.getInstance().getAllEffects().get(IEffectID);
    }

    public int getPrice() {
        return price;
    }

    public static Card getCardByName(String cardName) {
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getName().equals(cardName)) {
                return cards.get(i);
            }
        }
        return null;
    }

    public void activateEffectOfCard(Update update) {
        if (this.cardEffect.canEffectActivate(this, update)) {
            this.cardEffect.activateEffect(this, update);
        }
        if (this.cardEffect.canDeActive(this, update)) {
            this.cardEffect.deActive(this, update);
        }
    }

    public boolean canEffectOfCardActivate(Update update) {
        return this.cardEffect.canEffectActivate(this, update);
    }

    public void setFacedUp(boolean isFacedUp) {
        this.isFacedUp = isFacedUp;
    }

}
