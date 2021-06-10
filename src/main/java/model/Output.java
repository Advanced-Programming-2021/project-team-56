package model;

public enum Output {
    YouCantDoThisAction("you can’t do this action in this phase"),
    InvalidCommand("invalid command"),
    NoCardIsSelectedYet("no card is selected yet"),
    SummonedSuccessfully("summoned successfully"),
    CardSelected("card selected"),
    CardIsNotVisible("card is not visible"),
    Yes("yes"),
    No("no"),
    ILost("I lost"),
    IWon("I won"),
    TheGameContinues("the game continues"),
    MenuNavigationIsNotPossible("menu navigation is not possible"),
    YouCantFlipSummonThisCard("you can't flip summon this card"),
    ItsNotYourTurnToPLayThisKindOfMove("it’s not your turn to play this kind of moves"),
    InvalidSelection("invalid selection");

    private String output;

    Output(String output) {
        this.output = output;
    }

    @Override
    public String toString() {
        return output;
    }
}
