package model;

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
    private ArrayList<Card> userAllCards;

    {
        decks = new ArrayList<>();
        userAllCards= new ArrayList<>();
    }

    public User(String username, String nickname, String password) {
        setUsername(username);
        setPassword(password);
        setNickname(nickname);
        users.add(this);
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    public static boolean isThisUsernameAlreadyTaken(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username))
                return true;
        }
        return false;
    }

    public static boolean isThisNicknameAlreadyTaken(String nickname) {
        for (User user : users) {
            if (user.getNickname().equals(nickname))
                return true;
        }
        return false;
    }

    public String getUsername() {
        return this.username;
    }

    public String getNickname() {
        return this.nickname;
    }

    public int getScore() {
        return this.score;
    }
//اینو کامنتش کردم باگ نخوره
//    public Card getUserCardByNameForDeckAdding(String cardName, Deck deck) {
//        //TODO if cards with the same names are the same object this is the code, if not check if the card is in the deck or not
//        //TODO before
//        for (Card card : userAllCards) {
//            if (card.getName().equals(cardName)) {
//                if (deck.isCardWithThisNameInDeck(cardName)) {
//
//                }
//            }
//        }
//    }

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

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isDeckWithThisNameExistent(String deckName) {
        for (Deck deck : decks) {
            if (deck.getDeckName().equals(deckName)) {
                return true;
            }
        }
        return false;
    }

    public boolean isCardWithThisNameExistent(String cardName) {
        for (Card card : userAllCards) {
            if (card.getName().equals(cardName)) {
                return true;
            }
        }
        return false;
    }

    public void addDeckToDecks(Deck deck) {
        this.decks.add(deck);
    }

    public void removeDeckFromDecks(Deck deck) {
        this.decks.remove(deck);
    }

    public Deck getDeckByDeckName(String deckName) {
        for (Deck deck : decks) {
            if (deck.getDeckName().equals(deckName))
                return deck;
        }
        return null;
    }

    public void setDeckActivate(Deck targetDeck) {
        for (Deck deck : decks) {
            if (deck.isDeckActivated()) {
                deck.setDeckInactive();
                targetDeck.setDeckActive();
                return;
            }
        }
    }

    public void addCardToDeck(String deckNAme, String cardName) {

    }
}
