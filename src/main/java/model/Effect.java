package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Effect {

    private static Effect effect;
    private static HashMap<Integer, IEffect> allEffects;

    static {
        allEffects = new HashMap<>();
    }

    private Effect() {

    }

    public static Effect getInstance() {
        if (effect == null)
            effect = new Effect();
        return effect;
    }

    //TODO Write all effects
    IEffect effectCommandKnight = new IEffect() {
        @Override
        public boolean canEffectActivate(Card card) {

            return true;
        }

        @Override
        public void activateEffect(Card card) {

        }
    };

    public HashMap<Integer, IEffect> getAllEffects() {
        //TODO put all IEffects
        allEffects.put(0, null);
        allEffects.put(1, effectCommandKnight);

        return allEffects;
    }

}
