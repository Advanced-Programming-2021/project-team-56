﻿package model;

import java.util.ArrayList;

public class User {

    private static ArrayList<User> users;

    static {
        users = new ArrayList<>();
    }

    private String username;
    private String password;
    private int score;
    private String nickname;
    private int money;
    private ArrayList<Deck> decks;

    public User(String username, String nickname, String password) {
        setUsername(username);
        setPassword(password);
        setNickname(nickname);
        users.add(this);
        decks = new ArrayList<>();
    }

    public boolean isThisUsernameAlreadyTaken(String username) {

    }

    public String getUsername() {
        return this.username;
    }

    public String getNickname() {
        return this.nickname;
    }

    public boolean isThisNicknameAlreadyTaken(String nickname) {

    }

    public int getScore() {
        return this.score;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public void increaseMoney(int prize) {

    }

    public void increaseScore(int matchPoint) {

    }

    public void decreaseMoney(int payment) {

    }

    public int getMoney() {
        return this.money;
    }

    public String getPassword() {
        return this.password;
    }

    public static User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isThisDeckNameAlreadyTaken(String deckName) {

    }

    public Deck getDeckByDeckName(String deckName) {
        for (Deck deck : decks) {
            if (deck.getDeckName().equals(deckName))
                return deck;
        }
        return null;
    }

    public void addCardToDeck(String deckNAme, String cardName) {

    }
}
