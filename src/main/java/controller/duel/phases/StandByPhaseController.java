package controller.duel.phases;

import controller.duel.DuelWithUser;
import controller.duel.effects.SpellEffectCanActivate;
import model.Card;
import model.MonsterCard;
import view.duel.EffectView;

import java.util.ArrayList;
import java.util.HashMap;

public class StandByPhaseController {
    private static StandByPhaseController standByPhase;
    private DuelWithUser duelWithUser = DuelWithUser.getInstance();
    private EffectView effectView = EffectView.getInstance();
    private SpellEffectCanActivate spellEffectCanActivate = SpellEffectCanActivate.getInstance();

    private StandByPhaseController() {

    }

    public static StandByPhaseController getInstance() {
        if (standByPhase == null) {
            standByPhase = new StandByPhaseController();
        }
        return standByPhase;
    }

    public String run() {
        askWhetherMessengerOfPeaceContinues();
        bringBackMyMonsters();

        return supplySquad();
    }

    private String supplySquad() {
        //TODO Ask whats the logic of didAnyCardDestroyed (which code is not related to the name)
        if (spellEffectCanActivate.isThereSupplySquad(1)) {
            if (didAnyCardDestroyed(1)) {
                return supplySquadDrawCard(1);
            }
        }
        if (spellEffectCanActivate.isThereSupplySquad(2)) {
            if (didAnyCardDestroyed(2)) {
                return supplySquadDrawCard(2);
            }
        }
        return "the game continuous";
    }

    private boolean didAnyCardDestroyed(int player) {
        HashMap<Integer, Card> spellTerritory;
        if (player == 1) {
            spellTerritory = duelWithUser.getMyBoard().getSpellAndTrapTerritory();
        } else {
            spellTerritory = duelWithUser.getEnemyBoard().getSpellAndTrapTerritory();
        }
        for (int i = 1; i < 6; i++) {
            Card card = spellTerritory.get(i);
            if (card.getName().equals("Supply Squad")) {
                if (card.getIsFacedUp()) {
                    if (card.getStartEffectTurn() != -1) {
                        card.setStartEffectTurn(-1);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private String supplySquadDrawCard(int player) {
        ArrayList<Card> playerHand;
        ArrayList<Card> mainDeck;
        if (player == 1) {
            playerHand = duelWithUser.getMyBoard().getPlayerHand();
            mainDeck = duelWithUser.getMyBoard().getMainDeck();
        } else {
            playerHand = duelWithUser.getEnemyBoard().getPlayerHand();
            mainDeck = duelWithUser.getEnemyBoard().getMainDeck();
        }
        if (mainDeck.size() == 0) {
            if (player == 1) {
                return "I lost";
            } else {
                return "I won";
            }
        }
        playerHand.add(mainDeck.get(mainDeck.size() - 1));
        mainDeck.remove(mainDeck.size() - 1);
        return "the game continuous";
    }

    private void askWhetherMessengerOfPeaceContinues() {
        if (doIHaveItOnMyField()) {
            if (duelWithUser.getMyBoard().getLP() > 100) {
                while (true) {
                    effectView.output("do you want to pay 100 lp?");
                    String input = effectView.input();
                    if (input.equals("cancel")) {
                        destroyMyMessengers();
                    } else if (input.equals("yes")) {
                        effectView.output("ok");
                        return;
                    } else {
                        effectView.output("invalid command");
                    }
                }
            }
        }
    }

    private void destroyMyMessengers() {
        HashMap<Integer, Card> spellTerritory = duelWithUser.getMyBoard().getSpellAndTrapTerritory();
        ArrayList<Card> graveyard = duelWithUser.getMyBoard().getGraveyard();
        for (int i = 1; i < 6; i++) {
            if (spellTerritory.get(i).getName().equals("Messenger of peace")) {
                if (spellTerritory.get(i).getIsFacedUp()) {
                    graveyard.add(spellTerritory.get(i));
                    spellTerritory.put(i, null);
                }
            }
        }
    }

    private boolean doIHaveItOnMyField() {
        HashMap<Integer, Card> spellTerritory = duelWithUser.getMyBoard().getSpellAndTrapTerritory();
        for (int i = 1; i < 6; i++) {
            if (spellTerritory.get(i).getName().equals("Messenger of peace")) {
                if (spellTerritory.get(i).getIsFacedUp()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void bringBackMyMonsters() {
        HashMap<Integer, MonsterCard> enemyMonsterTerritory = duelWithUser.getEnemyBoard().getMonsterTerritory();
        HashMap<Integer, MonsterCard> myMonsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        for (int i = 1; i < 6; i++) {
            MonsterCard monsterCard = enemyMonsterTerritory.get(i);
            if (monsterCard != null) {
                if (monsterCard.isItControlledByChangeOfHeart()) {
                    for (int j = 1; j < 6; j++) {
                        if (myMonsterTerritory.get(j) == null) {
                            monsterCard.setItControlledByChangeOfHeart(false);
                            myMonsterTerritory.put(j, monsterCard);
                            break;
                        }
                    }
                    enemyMonsterTerritory.put(i, null);
                }
            }
        }
    }

    private void heraldOfCreationEffect() {
        ArrayList<Card> playerHand = duelWithUser.getMyBoard().getPlayerHand();
        ArrayList<Card> graveyard = duelWithUser.getMyBoard().getGraveyard();
        if (canHeraldOfCreationPickUpAMonster()) {
            while (true) {
                effectView.output("do you want to pick up a monster from your graveyard?");
                String input = effectView.input();
                if (input.equals("yes")) {
                    effectView.output("chose which card do you want to tribute");
                    int address = effectView.getAddress();
                    if (address > playerHand.size() || address < 1){
                        effectView.output("invalid selection");
                    }else{

                    }
                } else if (input.equals("cancel")) {
                    break;
                } else {
                    effectView.output("invalid command");
                }
            }
        }
    }

    private boolean canHeraldOfCreationPickUpAMonster() {
        ArrayList<Card> graveyard = duelWithUser.getMyBoard().getGraveyard();
        for (int i = 0; i < graveyard.size(); i++) {
            if (graveyard.get(i) instanceof MonsterCard) {
                if (((MonsterCard) graveyard.get(i)).getLevel() >= 7) {
                    return true;
                }
            }
        }
        return false;
    }
}
