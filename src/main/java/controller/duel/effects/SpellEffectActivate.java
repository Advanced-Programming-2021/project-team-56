package controller.duel.effects;

import view.duel.EffectView;

public class SpellEffectActivate {

    private static SpellEffectActivate spellEffectActivate;
    private SpellEffectCanActivate spellEffectCanActivate = SpellEffectCanActivate.getInstance();
    private EffectView effectView = EffectView.getInstance();

    private SpellEffectActivate() {

    }

    public static SpellEffectActivate getInstance() {
        if (spellEffectActivate == null)
            spellEffectActivate = new SpellEffectActivate();
        return spellEffectActivate;
    }

    public void advancedRitualArt() {
        int output = spellEffectCanActivate.canAdvancedRitualArtActivate();
        if (output == 0) {
            effectView.output("preparations of this spell are not done yet");
        }
        effectView.showDeck();
        if (output == 1){

        }else {

        }
    }
}
