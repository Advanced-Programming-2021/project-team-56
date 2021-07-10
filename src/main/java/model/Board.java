package model;

import javafx.scene.image.ImageView;
import view.duel.DuelView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static view.duel.DuelView.*;

public class Board {
    private final ArrayList<Card> graveyard = new ArrayList<>();
    private final Deck playerDeck;
    private final HashMap<Integer, Card> spellAndTrapTerritory = new HashMap<>();
    private final HashMap<Integer, MonsterCard> monsterTerritory = new HashMap<>();
    private final ArrayList<Card> playerHand = new ArrayList<>();
    private final User user;
    private SpellCard fieldSpell;
    private Card selectedCard;
    private int LP = 8000;
    private int lastSummonedOrSetTurn;
    private int startedTurn;
    private boolean isItEffectedByTimeSeal;
    private boolean isItEffectedByMagicCylinder;
    private boolean isItAttackNegated;
    private boolean isItEffectedByMirrorFace;
    private boolean isItEffectedBySoleiman;
    private boolean isItMySummon;
    private boolean isMyMonsterInDangerOfTrapHole;
    private boolean amIAffectedByTrapHole;


    private final HashMap<Integer, GameCard> spellAndTrapTerritoryGUI = new HashMap<>();
    private final HashMap<Integer, GameCard> monsterTerritoryGUI = new HashMap<>();
    private final ArrayList<GameCard> playerHandGUI = new ArrayList<>();


    public Board(User user) {
        this.playerDeck = new Deck(user.getActiveDeck());
        this.user = user;
        this.monsterTerritory.put(5, null);
        this.monsterTerritory.put(3, null);
        this.monsterTerritory.put(1, null);
        this.monsterTerritory.put(2, null);
        this.monsterTerritory.put(4, null);
        this.spellAndTrapTerritory.put(5, null);
        this.spellAndTrapTerritory.put(3, null);
        this.spellAndTrapTerritory.put(1, null);
        this.spellAndTrapTerritory.put(2, null);
        this.spellAndTrapTerritory.put(4, null);
        //TODO
        //TODO
//        this.monsterTerritoryGUI.put(5, null);
//        this.monsterTerritoryGUI.put(3, null);
//        this.monsterTerritoryGUI.put(1, null);
//        this.monsterTerritoryGUI.put(2, null);
//        this.monsterTerritoryGUI.put(4, null);
//        this.spellAndTrapTerritoryGUI.put(5, null);
//        this.spellAndTrapTerritoryGUI.put(3, null);
//        this.spellAndTrapTerritoryGUI.put(1, null);
//        this.spellAndTrapTerritoryGUI.put(2, null);
//        this.spellAndTrapTerritoryGUI.put(4, null);
        //TODO
        //TODO
    }

    public void setPlayerHandForFirstPlayer() {
        Collections.shuffle(playerDeck.getMainDeck());
        for (int i = 0; i < 5; i++) {
            Card newCard = playerDeck.getMainDeck().get(0);
            if (newCard.getName().equals("Scanner")) {
                ((MonsterCard) newCard).setItScanner(true);
            }
            playerHand.add(newCard);
            playerDeck.getMainDeck().remove(0);
        }
    }

    public void setPlayerHandForSecondPlayer() {
        Collections.shuffle(playerDeck.getMainDeck());
        for (int i = 0; i < 5; i++) {
            int index = playerDeck.getMainDeck().size() - 1;
            Card newCard = playerDeck.getMainDeck().get(index);
            if (newCard.getName().equals("Scanner")) {
                ((MonsterCard) newCard).setItScanner(true);
            }
            playerHand.add(newCard);
            playerDeck.getMainDeck().remove(index);
        }
    }

    public Card getSelectedCard() {
        return selectedCard;
    }

    public User getUser() {
        return user;
    }

    public SpellCard getFieldSpell() {
        return fieldSpell;
    }

    public void setFieldSpell(SpellCard fieldSpell) {
        this.fieldSpell = fieldSpell;
    }

    public int getLP() {
        return LP;
    }

    public HashMap<Integer, MonsterCard> getMonsterTerritory() {
        return monsterTerritory;
    }

    public HashMap<Integer, Card> getSpellAndTrapTerritory() {
        return spellAndTrapTerritory;
    }

    public void setSelectedCard(Card selectedCard) {
        this.selectedCard = selectedCard;
    }

    public ArrayList<Card> getMainDeck() {
        return playerDeck.getMainDeck();
    }

    public ArrayList<Card> getPlayerHand() {
        return playerHand;
    }

    public ArrayList<Card> getGraveyard() {
        return graveyard;
    }

    public int getLastSummonedOrSetTurn() {
        return lastSummonedOrSetTurn;
    }

    public void setLastSummonedOrSetTurn(int turn) {
        lastSummonedOrSetTurn = turn;
    }

    public void setLP(int LP) {
        this.LP = LP;
    }

    public void increaseLP(int amount) {
        this.LP += amount;
    }

    public boolean isItEffectedByTimeSeal() {
        return isItEffectedByTimeSeal;
    }

    public void setItEffectedByTimeSeal(boolean itEffectedByTimeSeal) {
        isItEffectedByTimeSeal = itEffectedByTimeSeal;
    }

    public boolean isItEffectedByMagicCylinder() {
        return isItEffectedByMagicCylinder;
    }

    public void setItEffectedByMagicCylinder(boolean itEffectedByMagicCylinder) {
        isItEffectedByMagicCylinder = itEffectedByMagicCylinder;
    }

    public boolean isItAttackNegated() {
        return isItAttackNegated;
    }

    public void setItAttackNegated(boolean itAttackNegated) {
        isItAttackNegated = itAttackNegated;
    }

    public void setStartedTurn(int startedTurn) {
        this.startedTurn = startedTurn;
    }

    public int getStartedTurn() {
        return startedTurn;
    }

    public boolean isItEffectedByMirrorFace() {
        return isItEffectedByMirrorFace;
    }

    public void setItEffectedByMirrorFace(boolean itEffectedByMirrorFace) {
        isItEffectedByMirrorFace = itEffectedByMirrorFace;
    }

    public boolean isItEffectedBySoleiman() {
        return isItEffectedBySoleiman;
    }

    public void setItEffectedBySoleiman(boolean itEffectedBySoleiman) {
        isItEffectedBySoleiman = itEffectedBySoleiman;
    }

    public boolean isItMySummon() {
        return isItMySummon;
    }

    public void setItMySummon(boolean itMySummon) {
        isItMySummon = itMySummon;
    }

    public boolean isMyMonsterInDangerOfTrapHole() {
        return isMyMonsterInDangerOfTrapHole;
    }

    public void setMyMonsterInDangerOfTrapHole(boolean myMonsterInDangerOfTrapHole) {
        isMyMonsterInDangerOfTrapHole = myMonsterInDangerOfTrapHole;
    }

    public boolean isAmIAffectedByTrapHole() {
        return amIAffectedByTrapHole;
    }

    public void setAmIAffectedByTrapHole(boolean amIAffectedByTrapHole) {
        this.amIAffectedByTrapHole = amIAffectedByTrapHole;
    }
}
