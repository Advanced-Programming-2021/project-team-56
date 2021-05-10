package controller.duel.effects;

public class SpellEffectActivate {

    private static SpellEffectActivate spellEffectActivate;

    private SpellEffectActivate() {

    }

    public static SpellEffectActivate getInstance() {
        if (spellEffectActivate == null)
            spellEffectActivate = new SpellEffectActivate();
            return spellEffectActivate;

    }
}
