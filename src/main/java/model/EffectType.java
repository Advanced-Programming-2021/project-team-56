package model;

public enum EffectType {
    MONSTER_TRIGGER(1),
    MONSTER_FLIP(1),
    MONSTER_CONTINUOUS(1),
    MONSTER_SPARKY(1),
    MONSTER_QUICK(2),
    SPELL_NORMAL(1),
    SPELL_EQUIP(1),
    SPELL_CONTINUOUS(1),
    SPELL_FIELD(1),
    SPELL_RITUAL(1),
    SPELL_QUICK(2),
    TRAP_NORMAL(2),
    TRAP_CONTINUOUS(2),
    TRAP_COUNTER(3);

    public int speed;

    EffectType(int speed) {
        this.speed = speed;
    }
}
