package model.enums;

public enum Phase {

    DRAW(1),
    STANDBY(2),
    MAIN_PHASE1(3);

    public int value;

    Phase(int value) {
        this.value = value;
    }
}
