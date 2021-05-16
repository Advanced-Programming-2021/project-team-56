package controller.duel.phases;

import controller.duel.DuelWithUser;
import controller.duel.effects.SpellEffectActivate;
import controller.duel.effects.TrapEffectActivate;
import controller.duel.effects.TrapEffectCanActivate;
import model.Card;
import model.SpellCard;
import model.TrapCard;
import view.duel.EffectView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OpponentPhase {
    private static OpponentPhase opponentPhase;
    private final DuelWithUser duelWithUser = DuelWithUser.getInstance();
    private final EffectView effectView = EffectView.getInstance();
    private ArrayList<Card> chainLink = new ArrayList<>();
    private SpellEffectActivate spellEffectActivate = SpellEffectActivate.getInstance();
    static Pattern attack = Pattern.compile("^attack (\\d+)$");
    private TrapEffectActivate trapEffectActivate = TrapEffectActivate.getInstance();
    private TrapEffectCanActivate trapEffectCanActivate = TrapEffectCanActivate.getInstance();

    private OpponentPhase() {

    }

    public ArrayList<Card> getChainLink() {
        return chainLink;
    }

    public static OpponentPhase getInstance() {
        if (opponentPhase == null) {
            opponentPhase = new OpponentPhase();
        }
        return opponentPhase;
    }

    public void run() {
        duelWithUser.increaseTempTurnCounter();
        if (!isItPossibleToAddACardToTheChain()) {
            return;
        }
        effectView.output("now it will be " + duelWithUser.getMyBoard().getUser().getUsername() + "’s turn");
        effectView.output1(duelWithUser.showField());
        effectView.output("do you want to activate your trap and spell?");
        while (true) {
            String input = effectView.input();
            if (input.equals("yes")) {
                break;
            } else if (input.equals("no")) {
                return;
            } else {
                effectView.output("invalid command");
            }
        }
        while (true) {
            String command = effectView.input();
            if (command.equals("select -d")) {
                effectView.output("it’s not your turn to play this kind of moves");
                continue;
            }
            if (command.startsWith("select")) {
                effectView.output("it’s not your turn to play this kind of moves");
                continue;
            }
            if (command.equals("summon")) {
                effectView.output("it’s not your turn to play this kind of moves");
                continue;
            }
            if (command.equals("set")) {
                effectView.output("it’s not your turn to play this kind of moves");
                continue;
            }
            if (command.equals("set --position attack") || command.equals("set --position defence")) {
                effectView.output("it’s not your turn to play this kind of moves");
                continue;
            }
            if (command.equals("flip-summon")) {
                effectView.output("it’s not your turn to play this kind of moves");
                continue;
            }
            Matcher matcher = attack.matcher(command);
            if (matcher.find()) {
                effectView.output("it’s not your turn to play this kind of moves");
                continue;
            }
            if (command.equals("attack direct")) {
                effectView.output("it’s not your turn to play this kind of moves");
                continue;
            }
            if (command.equals("cancel")) {
                break;
            }
            if (command.equals("activate effect")) {
                int result = activateEffect();
                if (result == 0) {
                    continue;
                } else if (result == 1) {
                    run();
                    break;
                } else {
                    break;
                }
            }
            effectView.output("invalid command");
        }
    }

    private int activateEffect() {
        HashMap<Integer, Card> spellAndTrapTerritory = duelWithUser.getMyBoard().getSpellAndTrapTerritory();
        int address = effectView.getAddress();
        if (address > 5 || address < 1) {
            effectView.output("invalid selection");
            return 0;
        }
        Card card = spellAndTrapTerritory.get(address - 1);
        if (card == null) {
            effectView.output("there is no spell or trap on this address");
            return 0;
        }
        if (card.isItInChainLink()) {
            effectView.output("this card is already in chain link");
            return 0;
        }
        if (card instanceof SpellCard) {
            SpellCard spell = (SpellCard) card;
            if (!spell.getIcon().equals("Quick-play")) {
                effectView.output("this card can't be played in opponent turn");
                return 0;
            }
            if (trapEffectCanActivate.checkSpellAndTrapPossibility(spell.getName())) {
                spellEffectActivate.spellAbsorption();
                chainLink.add(spell);
                spell.setItInChainLink(true);
                return 1;
            }
        } else {
            TrapCard trap = (TrapCard) card;
            //todo possible
            return 2;
        }
        return 0;
    }

    public void resolveTheChainLink() {
        while (chainLink.size() > 0) {
            Card card = chainLink.get(chainLink.size() - 1);
            chainLink.remove(chainLink.size() - 1);
            if (!isSpellOrTrapStillAlive(card)) {
                duelWithUser.decreaseTempTurnCounter();
                continue;
            }
            if ((card instanceof TrapCard)) {
                trapEffectActivate.trapAndQuickSpellCaller(card.getName());
                duelWithUser.decreaseTempTurnCounter();
                continue;
            }
            SpellCard spell = (SpellCard) card;
            if (spell.getIcon().equals("Quick-play")) {
                trapEffectActivate.trapAndQuickSpellCaller(spell.getName());
                getRidOfTrapOrQuickPlaySpell(spell);
            } else {
                spellEffectActivate.spellCaller(spell.getName());
            }
            duelWithUser.decreaseTempTurnCounter();
        }
        duelWithUser.setTempTurnCounter(0);
    }

    private boolean isSpellOrTrapStillAlive(Card card) {
        HashMap<Integer, Card> spellAndTrapTerritory = duelWithUser.getMyBoard().getSpellAndTrapTerritory();
        for (int i = 1; i < 6; i++) {
            if (spellAndTrapTerritory.get(i) == card) {
                return true;
            }
        }
        return false;
    }

    private void getRidOfTrapOrQuickPlaySpell(Card card){
        HashMap<Integer, Card> spellAndTrapTerritory = duelWithUser.getMyBoard().getSpellAndTrapTerritory();
        for (int i = 1; i < 6; i++) {
            if (spellAndTrapTerritory.get(i) == card){
                spellAndTrapTerritory.put(i, null);
            }
        }
        duelWithUser.getMyBoard().getGraveyard().add(card);
    }

    private boolean isItPossibleToAddACardToTheChain() {
        HashMap<Integer, Card> spellAndTrapTerritory = duelWithUser.getMyBoard().getSpellAndTrapTerritory();
        for (int i = 1; i < 5; i++) {
            Card card = spellAndTrapTerritory.get(i);
            if (card != null && !card.isItInChainLink()) {
                if (trapEffectCanActivate.checkSpellAndTrapPossibility(card.getName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
