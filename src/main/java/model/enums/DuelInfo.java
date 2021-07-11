package model.enums;

public enum DuelInfo {

    PHASE_DRAW("Draw phase"),
    PHASE_STANDBY("StandBy phase"),
    PHASE_MAIN1("Main phase1"),
    PHASE_BATTLE("Battle phase"),
    PHASE_MAIN2("Main phase2"),
    PHASE_END("End phase");

    public String value;

    DuelInfo(String value) {
        this.value = value;
    }
}
