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
                int changed = 0;
                for (Card effectedMonsterCard : card.getEffectedCards()) {
                    if (monsterCard == effectedMonsterCard) {
                        changed = 1;
                        break;
                    }
                }
                if (changed == 0) {
                    monsterCard.increaseFinalAttack(400);
                    card.getEffectedCards().add(monsterCard);
                }
            }
        }

        @Override
        public EffectType getType() {
            return EffectType.MONSTER_CONTINUOUS;
        }

        @Override
        public boolean canDeActive(Card card, Update update) {
            return card.getCurrentBoard().getGraveyard().contains(card);
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
            MonsterCard monsterCard = (MonsterCard) card;
            if (update.getGameCommandType() == GameCommandType.AFTER_DAMAGE && monsterCard == BattlePhaseController.getInstance().enemyMonsterCard) {
                if (monsterCard.getIsInAttackPosition() && BattlePhaseController.getInstance().monsterCard.getAttack() > monsterCard.getAttack()) {
                    return true;
                }
                if (!monsterCard.getIsInAttackPosition() && BattlePhaseController.getInstance().monsterCard.getAttack() > monsterCard.getDefence()) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public void activateEffect(Card card, Update update) {
            card.getOpponentBoard().getGraveyard().add(BattlePhaseController.getInstance().monsterCard);
            for (int i = 1; i <= 5; i++) {
                if (BattlePhaseController.getInstance().monsterCard == card.getOpponentBoard().getMonsterTerritory().get(i)) {
                    card.getOpponentBoard().getMonsterTerritory().put(i, null);
                    break;
                }
            }
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
            if (update.getGameCommandType() == GameCommandType.BEFORE_DAMAGE) {
                if (BattlePhaseController.getInstance().enemyMonsterCard == card && card.getStartEffectTurn() == -1) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public void activateEffect(Card card, Update update) {
            BattlePhaseController.getInstance().monsterCard.setFinalAttack(0);
            Board myBoard = card.getCurrentBoard();
            Board opponentBoard = card.getOpponentBoard();
            card.setStartEffectTurn(DuelWithUser.getInstance().getTurnCounter());
            card.getEffectedCards().add(BattlePhaseController.getInstance().monsterCard);
            for (int i = 1; i <= 5; i++) {
                if (myBoard.getMonsterTerritory().get(i) != null) {
                    myBoard.getMonsterTerritory().get(i).getEffectedCards().remove(BattlePhaseController.getInstance().monsterCard);
                }
                if (myBoard.getSpellAndTrapTerritory().get(i) != null) {
                    myBoard.getSpellAndTrapTerritory().get(i).getEffectedCards().remove(BattlePhaseController.getInstance().monsterCard);
                }
                if (opponentBoard.getMonsterTerritory().get(i) != null) {
                    opponentBoard.getMonsterTerritory().get(i).getEffectedCards().remove(BattlePhaseController.getInstance().monsterCard);
                }
                if (opponentBoard.getSpellAndTrapTerritory().get(i) != null) {
                    opponentBoard.getSpellAndTrapTerritory().get(i).getEffectedCards().remove(BattlePhaseController.getInstance().monsterCard);
                }
            }
        }

        @Override
        public EffectType getType() {
            ////////////////TOdo
            return null;
        }

        @Override
        public boolean canDeActive(Card card, Update update) {
            return update.getGameCommandType() == GameCommandType.END_TURN && card.getStartEffectTurn() == DuelWithUser.getInstance().getTurnCounter();
        }

        @Override
        public void deActive(Card card, Update update) {
            MonsterCard monsterCard = (MonsterCard) card.getEffectedCards().get(0);
            monsterCard.setFinalAttack(monsterCard.getAttack());
        }
    };

    IEffect effectMan_EaterBug = new IEffect() {
        @Override
        public boolean canEffectActivate(Card card, Update update) {
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

    IEffect effectGateGuardian = new IEffect() {
        @Override
        public boolean canEffectActivate(Card card, Update update) {
            return false;
        }

        @Override
        public void activateEffect(Card card, Update update) {

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
