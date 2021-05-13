package controller.duel.effects;

public class TrapEffectActivate {

    private static TrapEffectActivate trapEffectActivate;

    private TrapEffectActivate() {

    }

    public static TrapEffectActivate getInstance() {
        if (trapEffectActivate == null)
            trapEffectActivate = new TrapEffectActivate();
        return trapEffectActivate;
    }
}
