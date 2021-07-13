package model;

public enum Commands {
    SetAttackPosition("set --position attack"),
    SetDefencePosition("set --position defence"),
    Set("set"),
    Summon("summon"),
    FlipSummon("flip-summon"),
    AttackDirect("attack direct"),
    Select("select"),
    DisSelect("select -d");
    private final String command;

    Commands(String command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return command;
    }
}

