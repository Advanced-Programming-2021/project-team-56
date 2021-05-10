package controller.duel.effects;

import model.Board;

public class SpellEffectCanActivate {

    private static SpellEffectCanActivate spellEffectCanActivate;

    private SpellEffectCanActivate() {

    }

    public static SpellEffectCanActivate getInstance() {
        if (spellEffectCanActivate == null)
            spellEffectCanActivate = new SpellEffectCanActivate();
        return spellEffectCanActivate;
    }

    public boolean yamiCanActivate(Board board) {
        return board.getFieldSpell().getName().equals("Yami") && board.getFieldSpell().getIsFacedUp();
    }

    public boolean forestCanActivate(Board board) {
        return board.getFieldSpell().getName().equals("Forest") && board.getFieldSpell().getIsFacedUp();
    }

    public boolean closedForestCanActivate(Board board) {
        return board.getFieldSpell().getName().equals("closedForest") && board.getFieldSpell().getIsFacedUp();
    }

    public boolean umiirukaCanActive(Board board) {
        return board.getFieldSpell().getName().equals("UMIIRUKA") && board.getFieldSpell().getIsFacedUp();
    }
}
