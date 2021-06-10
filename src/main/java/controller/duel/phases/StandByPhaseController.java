package controller.duel.phases;

import controller.duel.DuelWithUser;
import controller.duel.effects.SpellEffectActivate;
import controller.duel.effects.SpellEffectCanActivate;
import model.Card;
import model.MonsterCard;
import model.Output;
import view.duel.EffectView;

import java.util.ArrayList;
import java.util.HashMap;

public class StandByPhaseController {

    private static StandByPhaseController standByPhase;
    private final DuelWithUser duelWithUser;
    private final EffectView effectView;
    private final SpellEffectCanActivate spellEffectCanActivate;
    private final SpellEffectActivate spellEffectActivate;

    private StandByPhaseController() {

    }

    {
        duelWithUser = DuelWithUser.getInstance();
        effectView = EffectView.getInstance();
        spellEffectCanActivate = SpellEffectCanActivate.getInstance();
        spellEffectActivate = SpellEffectActivate.getInstance();
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
        heraldOfCreationEffect();
        scannerEffect();
        return supplySquad();
    }

    private String supplySquad() {
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
        return Output.TheGameContinues.toString();
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
                return Output.ILost.toString();
            } else {
                return Output.IWon.toString();
            }
        }
        playerHand.add(mainDeck.get(mainDeck.size() - 1));
        mainDeck.remove(mainDeck.size() - 1);
        return Output.TheGameContinues.toString();
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
                        effectView.output(Output.InvalidCommand.toString());
                    }
                }
            }
        }
    }

    private void destroyMyMessengers() {
        HashMap<Integer, Card> spellTerritory = duelWithUser.getMyBoard().getSpellAndTrapTerritory();
        ArrayList<Card> graveyard = duelWithUser.getMyBoard().getGraveyard();
        for (int i = 1; i < 6; i++) {
            Card spell = spellTerritory.get(i);
            if (spell != null && spell.getIsFacedUp()) {
                if (spell.getName().equals("Messenger of peace")) {
                    graveyard.add(spellTerritory.get(i));
                    spellTerritory.put(i, null);
                }
            }
        }
    }

    private boolean doIHaveItOnMyField() {
        HashMap<Integer, Card> spellTerritory = duelWithUser.getMyBoard().getSpellAndTrapTerritory();
        for (int i = 1; i < 6; i++) {
            Card spell = spellTerritory.get(i);
            if (spell == null) {
                continue;
            }
            if (spell.getName().equals("Messenger of peace") && spell.getIsFacedUp()) {
                return true;
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
        if (!doIHaveHeraldOfCreationOnMyField()) {
            return;
        }
        ArrayList<Card> playerHand = duelWithUser.getMyBoard().getPlayerHand();
        ArrayList<Card> graveyard = duelWithUser.getMyBoard().getGraveyard();
        if (canHeraldOfCreationPickUpAMonster()) {
            while (true) {
                effectView.output("do you want to pick up a monster from your graveyard?");
                String input = effectView.input();
                if (input.equals("yes")) {
                    effectView.output("chose which card do you want to tribute");
                    int address = effectView.getAddress();
                    if (address > playerHand.size() || address < 1) {
                        effectView.output(Output.InvalidSelection.toString());
                    } else {
                        graveyard.add(playerHand.get(address - 1));
                        playerHand.remove(address - 1);
                        break;
                    }
                } else if (input.equals("cancel")) {
                    return;
                } else {
                    effectView.output(Output.InvalidCommand.toString());
                }
            }
            heraldOfCreationCardChoosing();
        }
    }

    private boolean doIHaveHeraldOfCreationOnMyField() {
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        for (int i = 1; i < 6; i++) {
            MonsterCard monster = monsterTerritory.get(i);
            if (monster != null && monster.getName().equals("Herald of Creation") && monster.getIsFacedUp()) {
                return true;
            }
        }
        return false;
    }

    private void heraldOfCreationCardChoosing() {
        ArrayList<Card> playerHand = duelWithUser.getMyBoard().getPlayerHand();
        ArrayList<Card> graveyard = duelWithUser.getMyBoard().getGraveyard();
        while (true) {
            effectView.output("choose your dead monster");
            effectView.showGraveyardForCardsEffects(true, false);
            int address = effectView.getAddress();
            if (address < 1 || address > graveyard.size()) {
                effectView.output(Output.InvalidSelection.toString());
                continue;
            }
            Card card = (graveyard.get(address - 1));
            if (!(card instanceof MonsterCard)) {
                effectView.output("chosen card is not a monster");
            } else if (((MonsterCard) card).getLevel() < 7) {
                effectView.output("the level of monster must be 7 or higher");
            } else {
                spellEffectActivate.monsterReborn((MonsterCard) card, false);
                card.setFacedUp(false);
                ((MonsterCard) card).setLastTimeChangedPositionTurn(0);
                playerHand.add(card);
                graveyard.remove(address - 1);
                return;
            }
        }
    }

    private boolean canHeraldOfCreationPickUpAMonster() {
        ArrayList<Card> graveyard = duelWithUser.getMyBoard().getGraveyard();
        for (Card card : graveyard) {
            if (card instanceof MonsterCard) {
                if (((MonsterCard) card).getLevel() >= 7) {
                    return true;
                }
            }
        }
        return false;
    }

    private void scannerEffect() {
        if (!isScannerInMyBoard()) {
            return;
        }
        boolean isMyGraveyardEmpty = spellEffectCanActivate.isThereMonsterInGraveyard(1);
        boolean isEnemyGraveyardEmpty = spellEffectCanActivate.isThereMonsterInGraveyard(2);
        ArrayList<Card> myGraveyard = duelWithUser.getMyBoard().getGraveyard();
        ArrayList<Card> enemyGraveyard = duelWithUser.getEnemyBoard().getGraveyard();
        int address;
        MonsterCard monsterCard;
        if (isMyGraveyardEmpty && isEnemyGraveyardEmpty) {
            effectView.showGraveyardForCardsEffects(true, true);
            address = effectView.getAddress();
            if (address > myGraveyard.size()) {
                address -= myGraveyard.size();
                monsterCard = (MonsterCard) enemyGraveyard.get(address - 1);
            } else {
                monsterCard = (MonsterCard) myGraveyard.get(address - 1);
            }
        } else if (isMyGraveyardEmpty) {
            effectView.showGraveyardForCardsEffects(true, false);
            address = effectView.getAddress();
            monsterCard = (MonsterCard) myGraveyard.get(address - 1);
        } else if (isEnemyGraveyardEmpty) {
            effectView.showGraveyardForCardsEffects(false, true);
            address = effectView.getAddress();
            monsterCard = (MonsterCard) enemyGraveyard.get(address - 1);
        } else {
            return;
        }
        scanTheAttributesForScanner(monsterCard);
    }

    private void scanTheAttributesForScanner(MonsterCard monsterCard) {
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        for (int i = 1; i < 6; i++) {
            MonsterCard scanner = monsterTerritory.get(i);
            if (scanner.getItScanner()) {
                scanner.setName(monsterCard.getName());
                scanner.setAttack(monsterCard.getAttack());
                scanner.setDefence(monsterCard.getDefence());
                scanner.setAttribute(monsterCard.getAttribute());
                scanner.setCardType(monsterCard.getCardType());
                scanner.setLevel(monsterCard.getLevel());
                scanner.setDescription(monsterCard.getDescription());
            }
        }
    }

    private boolean isScannerInMyBoard() {
        HashMap<Integer, MonsterCard> monsterTerritory = duelWithUser.getMyBoard().getMonsterTerritory();
        for (int i = 1; i < 6; i++) {
            if (monsterTerritory.get(i) == null) {
                continue;
            }
            if (monsterTerritory.get(i).getItScanner()) {
                return true;
            }
        }
        return false;
    }
}
