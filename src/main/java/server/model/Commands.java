package server.model;

public enum Commands {
    SetAttackPosition("set --position attack"),
    SetDefencePosition("set --position defence"),
    Set("set"),
    Summon("summon"),
    ActivateEffect("activate effect"),
    FlipSummon("flip-summon"),
    AttackDirect("attack direct"),
    SpecialSummon("special summon"),
    Select("select"),
    CardShowSelected("card show --selected"),
    ShowGraveyard("show graveyard"),
    Surrender("surrender"),
    DisSelect("select -d"),
    NextPhase("next phase");
    private final String command;

    Commands(String command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return command;
    }
}

