package model;

import java.util.ArrayList;

public class User {

    private static User currentUser;
    private final static ArrayList<User> users = new ArrayList<>();
    private String username;
    private String password;
    private int score = 0;
    private String nickname;
    private int money = 100000;
    private final ArrayList<Deck> decks = new ArrayList<>();
    private final ArrayList<Card> userAllCards = new ArrayList<>();
    private final ArrayList<Integer> playerLP = new ArrayList<>();

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

    public ArrayList<Integer> getPlayerLP() {
        return playerLP;
    }

    public int getMaxLP() {
        int maxLP = playerLP.get(0);
        for (int i = 1; i < playerLP.size(); i++) {
            if (playerLP.get(i) > maxLP) {
                maxLP = playerLP.get(i);
            }
        }
        return maxLP;
    }

    public void clearLP(){
        playerLP.clear();
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

    public static User getCurrentUser() {
        return currentUser;
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

    public static void setCurrentUser(User currentUser) {
        User.currentUser = currentUser;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setMoney(int amount) {
        this.money = amount;
    }

    public boolean isDeckWithThisNameExistent(String deckName) {
        for (Deck deck : decks) {
            if (deck.getDeckName().equals(deckName)) {
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

    public Deck getActivatedDeck() {
        for (Deck deck : decks) {
            if (deck.isDeckActivated()) {
                return deck;
            }
        }
        return null;
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
