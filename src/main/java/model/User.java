package model;

import java.util.ArrayList;

public class User {

    private static ArrayList<User> users = new ArrayList<>();
    private String username;
    private String password;
    private int score = 0;
    private String nickname;
    private int money = 100000;
    private ArrayList<Deck> decks = new ArrayList<>();
    private ArrayList<Card> userAllCards = new ArrayList<>();

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

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public void increaseMoney(int prize) {
        this.money += prize;
    }

    public void increaseScore(int matchPoint) {
        this.score += matchPoint;
    }

    public void decreaseMoney(int payment) {
        this.money -= payment;
    }

    public int getMoney() {
        return this.money;
    }

    public String getPassword() {
        return this.password;
    }

    public ArrayList<Card> getUserAllCards() {
        return userAllCards;
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
            }
        }
        targetDeck.setDeckActive();
    }

    public void addCardToUserAllCards(Card card) {
        if (card instanceof SpellCard) {
            SpellCard spellCard = new SpellCard(card);
            userAllCards.add(spellCard);
        } else if (card instanceof TrapCard) {
            TrapCard trapCard = new TrapCard(card);
            userAllCards.add(trapCard);
        } else {
            MonsterCard monsterCard = new MonsterCard(card);
            userAllCards.add(monsterCard);
        }
        for (Deck deck : decks) {
            deck.getUserCards().add(card);
        }
    }

}
