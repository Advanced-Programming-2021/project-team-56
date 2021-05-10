package controller.duel.effects;

public class SpellEffectCanActivate {

    private static SpellEffectCanActivate spellEffectCanActivate;

    private SpellEffectCanActivate() {

    }

    public static SpellEffectCanActivate getInstance() {
        if (spellEffectCanActivate == null)
            spellEffectCanActivate = new SpellEffectCanActivate();
        return spellEffectCanActivate;
    }
}
