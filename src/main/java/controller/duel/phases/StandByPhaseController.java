package controller.duel.phases;

import controller.duel.DuelWithUser;
import model.Card;
import model.MonsterCard;
import view.duel.EffectView;

import java.util.ArrayList;
import java.util.HashMap;

public class StandByPhaseController {
    private static StandByPhaseController standByPhase;
    private DuelWithUser duelWithUser = DuelWithUser.getInstance();
    private EffectView effectView = EffectView.getInstance();

    private StandByPhaseController() {

    }

    public static StandByPhaseController getInstance() {
        if (standByPhase == null) {
            standByPhase = new StandByPhaseController();
        }
        return standByPhase;
    }

    public void run(){
        askWhetherMessengerOfPeaceContinues();
        bringBackMyMonsters();
    }

    public void askWhetherMessengerOfPeaceContinues() {
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

    public void bringBackMyMonsters() {
        HashMap<Integer, MonsterCard> enemyMonsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        HashMap<Integer, MonsterCard> myMonsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        for (int i = 1; i < 6; i++) {
            if (enemyMonsterTerritory.get(i).isItControlledByChangeOfHeart()) {
                MonsterCard monsterCard = enemyMonsterTerritory.get(i);
                for (int j = 1; i < 6; i++) {
                    if (myMonsterTerritory.get(j) == null) {
                        monsterCard.setItControlledByChangeOfHeart(false);
                        myMonsterTerritory.put(j, monsterCard);
                    }
                }
                enemyMonsterTerritory.put(i, null);
            }
        }
    }
}
