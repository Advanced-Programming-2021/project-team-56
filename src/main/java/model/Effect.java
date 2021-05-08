package model;

import controller.duel.DuelWithUser;
import controller.duel.phases.BattlePhaseController;
import view.LoginMenuView;

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

    public HashMap<Integer, IEffect> getAllEffects() {
        //TODO put all IEffects
        allEffects.put(0, null);
        return allEffects;
    }

}
