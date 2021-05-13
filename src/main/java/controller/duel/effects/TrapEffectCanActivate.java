package controller.duel.effects;

public class TrapEffectCanActivate {

    private static TrapEffectCanActivate trapEffectCanActivate;

    private TrapEffectCanActivate() {

    }

    public static TrapEffectCanActivate getInstance() {
        if (trapEffectCanActivate == null)
            trapEffectCanActivate = new TrapEffectCanActivate();
        return trapEffectCanActivate;
    }
}
