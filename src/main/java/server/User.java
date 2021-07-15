package server;

import model.*;

import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private String nickname;
    private static User currentUser;
    private String avatarURL;
    private int score;
    private int money = 100000;
    private final ArrayList<Deck> decks = new ArrayList<>();
    private final ArrayList<Card> userAllCards = new ArrayList<>();
    private final ArrayList<Integer> playerLP = new ArrayList<>();

    public User(String nickname, String password, String username) {
        setNickname(nickname);
        setUsername(username);
        setPassword(password);
        setAvatarURL(avatarURL);
        currentUser = this;
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

    public void clearLP() {
        playerLP.clear();
    }

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public void increaseMoney(int prize) {
        this.money += prize;
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

    public ArrayList<Card> getUserAllCards() {
        return userAllCards;
    }

    public static void setCurrentUser(User currentUser) {
        User.currentUser = currentUser;
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

    public Deck getActiveDeck() {
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

    public String getNumberOfCardsInUsersAllCards(String cardName) {
        int cardCounter = 0;
        for (Card card : userAllCards) {
            if (cardName.equals(card.getName())) {
                cardCounter++;
            }
        }
        return Integer.toString(cardCounter);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public int getScore() {
        return score;
    }

    public void increaseScore(int score) {
        this.score += score;
    }
}
