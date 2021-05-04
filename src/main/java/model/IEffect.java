package model;

public interface IEffect {

    boolean canEffectActivate(Card card);
    void activateEffect(Card card);
}
