package model;

public enum Output {
    InvalidCommand("invalid command"),
    NoCardIsSelectedYet("no card is selected yet"),
    SummonedSuccessfully("summoned successfully"),
    CardIsNotVisible("card is not visible"),
    ILost("I lost"),
    IWon("I won"),
    ThisCardCantBeSpecialSummoned("this card can't be special summoned"),
    TheGameContinues("the game continues"),
    YouCantFlipSummonThisCard("you can't flip summon this card"),
    ItsNotYourTurnToPLayThisKindOfMove("it’s not your turn to play this kind of moves"),
    InvalidSelection("invalid selection");

    private final String output;

    Output(String output) {
        this.output = output;
    }

    @Override
    public String toString() {
        return output;
    }
}
