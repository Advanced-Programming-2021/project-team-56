package controller.duel.effects;

import model.*;

import controller.duel.DuelWithUser;
import server.model.Card;
import server.model.MonsterCard;
import server.model.SpellCard;
import view.duel.EffectView;

import java.util.ArrayList;
import java.util.HashMap;


public class SpellEffectCanActivate {

    private static SpellEffectCanActivate spellEffectCanActivate;

    private final DuelWithUser duelWithUser;
    private final EffectView effectView;

    {
        duelWithUser = DuelWithUser.getInstance();
        effectView = EffectView.getInstance();
    }

    private SpellEffectCanActivate() {

    }

    public static SpellEffectCanActivate getInstance() {
        if (spellEffectCanActivate == null)
            spellEffectCanActivate = new SpellEffectCanActivate();
        return spellEffectCanActivate;
    }

    public boolean checkSpellPossibility(String name) {
        switch (name) {
            case "Advanced Ritual Art":
                return canAdvancedRitualArtActivate() != 0;
            case "Pot of Greed":
                return potOfGreedCanActivate();
            case "Harpie's Feather Duster":
                return TrapEffectCanActivate.getInstance().canIActivateSpaceTyphoonOrTwinTwister();
            case "Change of Heart":
                return canChangeOfHeartActivate();
            case "Terraforming":
                return canTeraformingActivate();
            case "Raigeki":
                return raigekiCanActivate();
            case "Dark Hole":
                return darkHoleCanActivate();
            case "Black Pendant":
            case "United We Stand":
                return blackPendantAndUnitedWeStandCanActivate();
            case "Magnum Shield":
                return magnumShieldCanActivate();
            case "Sword of Dark Destruction":
                return swordOfDarkDestructionCanActivate();
            case "Monster Reborn":
                return canIActivateMonsterReborn();
        }
        return true;
    }

    public boolean yamiCanActivate(Board board) {
        return board.getFieldSpell() != null && board.getFieldSpell().getName().equals("Yami") && board.getFieldSpell().getIsFacedUp();
    }

    public boolean forestCanActivate(Board board) {
        return board.getFieldSpell() != null && board.getFieldSpell().getName().equals("Forest") && board.getFieldSpell().getIsFacedUp();
    }

    public boolean closedForestCanActivate(Board board) {
        return board.getFieldSpell() != null && board.getFieldSpell().getName().equals("closedForest") && board.getFieldSpell().getIsFacedUp();
    }

    public boolean umiirukaCanActive(Board board) {
        return board.getFieldSpell() != null && board.getFieldSpell().getName().equals("UMIIRUKA") && board.getFieldSpell().getIsFacedUp();
    }

    public int canAdvancedRitualArtActivate() {
        if (isMyMonsterTerritoryFull()) {
            return 0;
        }
        if (!doseHandIncludeIt("Skull Guardian") && !doseHandIncludeIt("Crab Turtle")) {
            return 0;
        }
        if (!areThereEnoughTributeFromDeck(7)) {
            return 0;
        }
        if (doseHandIncludeIt("Crab Turtle") && doseHandIncludeIt("Skull Guardian") && areThereEnoughTributeFromDeck(8)) {
            while (true) {
                effectView.output("Crab Turtle or Skull Guardian ?");
                String input = effectView.input();
                if (input.equals("Crab Turtle")) {
                    return 1;
                }
                if (input.equals("Skull Guardian")) {
                    return 2;
                }
                effectView.output(Output.InvalidCommand.toString());
            }
        }
        if (doseHandIncludeIt("Skull Guardian")) {
            return 2;
        }
        if (doseHandIncludeIt("Crab Turtle") && areThereEnoughTributeFromDeck(8)) {
            return 1;
        }
        return 0;
    }

    private boolean canIActivateMonsterReborn() {
        if (isMyMonsterTerritoryFull()) {
            return false;
        }
        boolean isMyGraveyardEmpty = spellEffectCanActivate.isThereNoMonsterInGraveyard(1);
        boolean isEnemyGraveyardEmpty = spellEffectCanActivate.isThereNoMonsterInGraveyard(2);
        return !isMyGraveyardEmpty || !isEnemyGraveyardEmpty;
    }

    private boolean areThereEnoughTributeFromDeck(int totalLevel) {
        ArrayList<Card> mainDeck = duelWithUser.getMyBoard().getMainDeck();
        for (Card card : mainDeck) {
            if (card instanceof MonsterCard) {
                totalLevel -= ((MonsterCard) card).getLevel();
            }
        }
        return totalLevel <= 0;
    }

    private boolean doseHandIncludeIt(String cardName) {
        ArrayList<Card> playerHand = duelWithUser.getMyBoard().getPlayerHand();
        for (Card card : playerHand) {
            if (card.getName().equals(cardName)) {
                return true;
            }
        }
        return false;
    }

    public boolean canTeraformingActivate() {
        ArrayList<Card> mainDeck = duelWithUser.getMyBoard().getMainDeck();
        for (Card card : mainDeck) {
            if (card instanceof SpellCard) {
                if (((SpellCard) card).getIcon().equals("Field")) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean potOfGreedCanActivate() {
        ArrayList<Card> mainDeck = duelWithUser.getMyBoard().getMainDeck();
        return mainDeck.size() >= 2;
    }

    public boolean canChangeOfHeartActivate() {
        if (!raigekiCanActivate()) {
            return false;
        }
        return !isMyMonsterTerritoryFull();
    }

    public boolean raigekiCanActivate() {
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getEnemyBoard().getMonsterTerritory();
        for (int i = 1; i <= 5; i++) {
            if (monsterTerritory.get(i) != null) {
                return true;
            }
        }
        return false;
    }

    public boolean darkHoleCanActivate() {
        HashMap<Integer, MonsterCard> monsterTerritory1 = duelWithUser.getMyBoard().getMonsterTerritory();
        HashMap<Integer, MonsterCard> monsterTerritory2 = duelWithUser.getEnemyBoard().getMonsterTerritory();
        for (int i = 1; i <= 5; i++) {
            if (monsterTerritory1.get(i) != null || monsterTerritory2.get(i) != null) return true;
        }
        return false;
    }

    public boolean isThereSupplySquad(int player) {
        HashMap<Integer, Card> spellTerritory;
        if (player == 1) {
            spellTerritory = duelWithUser.getMyBoard().getSpellAndTrapTerritory();
        } else {
            spellTerritory = duelWithUser.getEnemyBoard().getSpellAndTrapTerritory();
        }
        for (int i = 1; i < 6; i++) {
            Card card = spellTerritory.get(i);
            if (card == null) {
                continue;
            }
            if (card.getName().equals("Supply Squad")) {
                if (card.getIsFacedUp()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isThereNoMonsterInGraveyard(int player) {
        ArrayList<Card> graveyard;
        if (player == 1) {
            graveyard = duelWithUser.getMyBoard().getGraveyard();
        } else if (player == 2) {
            graveyard = duelWithUser.getEnemyBoard().getGraveyard();
        } else {
            return true;
        }
        for (Card card : graveyard) {
            if (card instanceof MonsterCard) {
                return false;
            }
        }
        return true;
    }

    public boolean swordOfDarkDestructionCanActivate() {
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        for (int i = 1; i <= 5; i++) {
            MonsterCard myMonster = monsterTerritory.get(i);
            if (myMonster != null) {
                if (myMonster.getMonsterType().equals("Fiend") || myMonster.getMonsterType().equals("Spellcaster")) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean blackPendantAndUnitedWeStandCanActivate() {
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        for (int i = 1; i <= 5; i++) {
            if (monsterTerritory.get(i) != null) {
                return true;
            }
        }
        return false;
    }

    public boolean magnumShieldCanActivate() {
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        for (int i = 1; i <= 5; i++) {
            if (monsterTerritory.get(i) != null) {
                if (monsterTerritory.get(i).getMonsterType().equals("Warrior")) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isMyMonsterTerritoryFull() {
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        for (int i = 1; i < 6; i++) {
            if (monsterTerritory.get(i) == null) {
                return false;
            }
        }
        return true;
    }
}
