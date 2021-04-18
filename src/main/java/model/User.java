package model;

import java.util.ArrayList;

public class User {

    private String password;
    private String username;
    private int score;
    private String nickname;
    private ArrayList<User> users;
    private int money;
    private ArrayList<Deck> decks;


    public boolean isThisUsernameAlreadyTaken(String username) {

    }

    public String getUsername() {

    }

    public String getNickname() {

    }

    public boolean isThisNicknameAlreadyTaken(String nickname) {

    }

    public int getScore() {

    }

    public void increaseMoney(int prize) {

    }

    public void increaseScore(int matchPoint) {

    }

    public void decreaseMoney(int payment) {

    }

    public int getMoney() {

    }

    public String getPassword() {

    }

    public User getUserByUsername() {

    }

    public void setPassword() {

    }

    public void setNickname() {

    }

    public User(String username, String nickname, String password) {

    }

    public boolean isThisDeckNameAlreadyTaken(String deckName) {

    }

    public Deck getDeckByDeckName(String deckName) {

    }

    public void addCardToDeck(String deckNAme, String cardName) {

    }
}
