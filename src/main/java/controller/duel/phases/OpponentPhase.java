package controller.duel.phases;

import controller.duel.DuelWithUser;
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
    static Pattern attack = Pattern.compile("^attack (\\d+)$");

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
        if (!canIActivateSpaceTyphoonOrTwinTwister()) {
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
            if (command.equals("activate effect")) {
                if (activateEffect()){
                    return;
                }else {
                    continue;
                }
            }
            effectView.output("invalid command");
        }
    }

    public boolean canIActivateSpaceTyphoonOrTwinTwister() {
        HashMap<Integer, Card> spellAndTrapTerritory = duelWithUser.getMyBoard().getSpellAndTrapTerritory();
        for (int i = 1; i < 6; i++) {
            Card spell = spellAndTrapTerritory.get(i);
            if (spell.getName().equals("Mystical space typhoon") || spell.getName().equals("Twin Twisters")) {
                if (doEnemyHaveSpellOnMyTerritory()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean activateEffect() {
        HashMap<Integer, Card> spellAndTrapTerritory = duelWithUser.getMyBoard().getSpellAndTrapTerritory();
        int address = effectView.getAddress();
        if (address > 5 || address < 1) {
            effectView.output("invalid selection");
            return false;
        }
        Card card = spellAndTrapTerritory.get(address - 1);
        if (card == null){
            effectView.output("there is no spell or trap on this address");
            return false;
        }
        if (card instanceof SpellCard){
            SpellCard spell = (SpellCard) card;
            if (!spell.getIcon().equals("quickPlay")){
                effectView.output("this card can't be played in opponent turn");
            }
            if (canIActivateSpaceTyphoonOrTwinTwister() || canIActivateRingOfDefence()){

            }
        }else {
            TrapCard trap = (TrapCard) card;
        }
        return true;
    }

    private boolean doEnemyHaveSpellOnMyTerritory() {
        HashMap<Integer, Card> spellAndTrapTerritory = duelWithUser.getEnemyBoard().getSpellAndTrapTerritory();
        for (int i = 1; i < 6; i++) {
            if (spellAndTrapTerritory.get(i) != null) {
                return true;
            }
        }
        return false;
    }

    private boolean canIActivateRingOfDefence(){
        return true;
    }
}
