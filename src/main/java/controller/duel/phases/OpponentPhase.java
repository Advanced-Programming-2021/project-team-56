package controller.duel.phases;

import controller.duel.DuelWithUser;
import controller.duel.effects.SpellEffectActivate;
import controller.duel.effects.TrapEffectActivate;
import controller.duel.effects.TrapEffectCanActivate;
import model.Card;
import model.SpellCard;
import model.TrapCard;
import view.duel.EffectView;
import view.duel.phase.Output;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OpponentPhase {

    private static OpponentPhase opponentPhase;

    private final DuelWithUser duelWithUser;
    private final EffectView effectView;
    private final SpellEffectActivate spellEffectActivate;
    private final TrapEffectActivate trapEffectActivate;
    private final TrapEffectCanActivate trapEffectCanActivate;

    private ArrayList<Card> chainLink = new ArrayList<>();
    static Pattern attack = Pattern.compile("^attack (\\d+)$");

    {
        duelWithUser = DuelWithUser.getInstance();
        effectView = EffectView.getInstance();
        spellEffectActivate = SpellEffectActivate.getInstance();
        trapEffectActivate = TrapEffectActivate.getInstance();
        trapEffectCanActivate = TrapEffectCanActivate.getInstance();
    }

    private OpponentPhase() {

    }

    public static OpponentPhase getInstance() {
        if (opponentPhase == null)
            opponentPhase = new OpponentPhase();
        return opponentPhase;
    }

    public ArrayList<Card> getChainLink() {
        return chainLink;
    }

    public void run() {
        duelWithUser.increaseTempTurnCounter();
        if (!isItPossibleToAddACardToTheChain()) {
            duelWithUser.decreaseTempTurnCounter();
            return;
        }
        effectView.output("now it will be " + duelWithUser.getMyBoard().getUser().getUsername() + "’s turn");
        effectView.output1(duelWithUser.showField());
        effectView.output("do you want to activate your trap and spell?(yes/no)");
        while (true) {
            String input = effectView.input();
            if (input.equals("yes")) {
                break;
            } else if (input.equals("no")) {
                duelWithUser.decreaseTempTurnCounter();
                return;
            } else {
                effectView.output(Output.InvalidCommand.toString());
            }
        }
        while (true) {
            String command = effectView.input();
            if (command.equals("select -d")) {
                effectView.output(Output.ItsNotYourTurnToPLayThisKindOfMove.toString());
                continue;
            }
            if (command.startsWith("select")) {
                effectView.output(Output.ItsNotYourTurnToPLayThisKindOfMove.toString());
                continue;
            }
            if (command.equals("summon")) {
                effectView.output(Output.ItsNotYourTurnToPLayThisKindOfMove.toString());
                continue;
            }
            if (command.equals("set")) {
                effectView.output(Output.ItsNotYourTurnToPLayThisKindOfMove.toString());
                continue;
            }
            if (command.equals("set --position attack") || command.equals("set --position defence")) {
                effectView.output(Output.ItsNotYourTurnToPLayThisKindOfMove.toString());
                continue;
            }
            if (command.equals("flip-summon")) {
                effectView.output(Output.ItsNotYourTurnToPLayThisKindOfMove.toString());
                continue;
            }
            Matcher matcher = attack.matcher(command);
            if (matcher.find()) {
                effectView.output(Output.ItsNotYourTurnToPLayThisKindOfMove.toString());
                continue;
            }
            if (command.equals("attack direct")) {
                effectView.output(Output.ItsNotYourTurnToPLayThisKindOfMove.toString());
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
                }
                return;
            }
            effectView.output(Output.InvalidCommand.toString());
        }
    }

    private int activateEffect() {
        HashMap<Integer, Card> spellAndTrapTerritory = duelWithUser.getMyBoard().getSpellAndTrapTerritory();
        int address = effectView.getAddress();
        if (address > 5 || address < 1) {
            effectView.output(Output.InvalidSelection.toString());
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
                effectView.output("this card can't be played in opponent's turn");
                return 0;
            }
            if (trapEffectCanActivate.checkSpellAndTrapPossibility(spell.getName())) {
                if (spell.getSetTurn() < duelWithUser.getTurnCounter()) {
                    spellEffectActivate.spellAbsorption();
                    chainLink.add(spell);
                    spell.setItInChainLink(true);
                    return 1;
                }
            }
        } else {
            TrapCard trap = (TrapCard) card;
            if (trapEffectCanActivate.checkSpellAndTrapPossibility(trap.getName())) {
                if (trap.getSetTurn() < duelWithUser.getTurnCounter()) {
                    chainLink.add(trap);
                    trap.setItInChainLink(true);
                    if (isTrapCardCounterAttackType(trap.getName())) {
                        return 2;
                    }
                    return 1;
                }
            }
        }
        return 0;
    }

    private boolean isTrapCardCounterAttackType(String name) {
        switch (name) {
            case "Negate Attack":
            case "Solemn Warning":
            case "Magic Jammer":
                return true;
        }
        return false;
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
                getRidOfTrapOrQuickPlaySpell(card);
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

    private void getRidOfTrapOrQuickPlaySpell(Card card) {
        if (!card.getName().equals("Call of the Haunted")) {
            HashMap<Integer, Card> spellAndTrapTerritory = duelWithUser.getMyBoard().getSpellAndTrapTerritory();
            for (int i = 1; i < 6; i++) {
                if (spellAndTrapTerritory.get(i) == card) {
                    spellAndTrapTerritory.put(i, null);
                }
            }
            duelWithUser.getMyBoard().getGraveyard().add(card);
        }
    }

    private boolean isItPossibleToAddACardToTheChain() {
        HashMap<Integer, Card> spellAndTrapTerritory = duelWithUser.getMyBoard().getSpellAndTrapTerritory();
        for (int i = 1; i < 6; i++) {
            Card card = spellAndTrapTerritory.get(i);
            if (card != null && !card.isItInChainLink()) {
                if (card instanceof SpellCard) {
                    if (((SpellCard) card).getSetTurn() >= duelWithUser.getTurnCounter()) {
                        return false;
                    }
                } else{
                    if (((TrapCard) card).getSetTurn() >= duelWithUser.getTurnCounter()) {
                        return false;
                    }
                }
                if (trapEffectCanActivate.checkSpellAndTrapPossibility(card.getName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
