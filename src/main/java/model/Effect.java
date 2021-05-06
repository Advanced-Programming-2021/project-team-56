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
        public boolean canEffectActivate(Card card, Update update) {
            if (card.getIsFacedUp()) {
                return true;
            }
            return false;
        }

        @Override
        public void activateEffect(Card card, Update update) {
            for (MonsterCard monsterCard : card.getCurrentBoard().getMonsterTerritory().values()) {
                for (Card effectedMonsterCard : card.getEffectedCards()) {
                    if (monsterCard != effectedMonsterCard) {
                        monsterCard.increaseFinalAttack(400);
                        card.getEffectedCards().add(monsterCard);
                    }
                }
            }
        }

        @Override
        public EffectType getType() {
            return EffectType.MONSTER_CONTINUOUS;
        }

        @Override
        public boolean canDeActive(Card card, Update update) {
            //TODO How to use update for canDeactivation
            return true;
        }

        @Override
        public void deActive(Card card, Update update) {
            for (Card effectedCard : card.getEffectedCards()) {
                MonsterCard downCastMonsterCard = (MonsterCard) effectedCard;
                downCastMonsterCard.decreaseFinalAttack(400);
                card.getEffectedCards().remove(effectedCard);
            }
        }
    };

    IEffect effectYomiShip = new IEffect() {
        @Override
        public boolean canEffectActivate(Card card, Update update) {
            //TODO How to use update for canDeactivation
            return true;
        }

        @Override
        public void activateEffect(Card card, Update update) {
            //TODO Ok MIKONIM
        }

        @Override
        public EffectType getType() {
            return EffectType.MONSTER_TRIGGER;
        }

        @Override
        public boolean canDeActive(Card card, Update update) {
            return false;
        }

        @Override
        public void deActive(Card card, Update update) {

        }
    };

    IEffect effectSuijin = new IEffect() {
        @Override
        public boolean canEffectActivate(Card card, Update update) {
            return false;
        }

        @Override
        public void activateEffect(Card card, Update update) {
            //TODO Final attack has been changed to 0 for one turn
        }

        @Override
        public EffectType getType() {
            return null;
        }

        @Override
        public boolean canDeActive(Card card, Update update) {
            return false;
        }

        @Override
        public void deActive(Card card, Update update) {

        }
    };

    IEffect effectMan_EaterBug = new IEffect() {
        @Override
        public boolean canEffectActivate(Card card, Update update) {
            //TODO check if it changed position from face down to face up
            return false;
        }

        @Override
        public void activateEffect(Card card, Update update) {

        }

        @Override
        public EffectType getType() {
            return EffectType.MONSTER_FLIP;
        }

        @Override
        public boolean canDeActive(Card card, Update update) {
            return false;
        }

        @Override
        public void deActive(Card card, Update update) {

        }
    };

    public HashMap<Integer, IEffect> getAllEffects() {
        //TODO put all IEffects
        allEffects.put(0, null);
        allEffects.put(1, effectCommandKnight);
        allEffects.put(2, effectYomiShip);
        allEffects.put(3, effectSuijin);
        allEffects.put(4, effectMan_EaterBug);

        return allEffects;
    }

}
