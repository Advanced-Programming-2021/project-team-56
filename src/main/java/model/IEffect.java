package model;

public interface IEffect {

    boolean canEffectActivate(Card card, Update update);
    void activateEffect(Card card, Update update);
    EffectType getType();
    boolean canDeActive(Card card, Update update);
    void deActive(Card card, Update update);
}
