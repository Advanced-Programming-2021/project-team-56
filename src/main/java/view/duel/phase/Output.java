package view.duel.phase;

public enum Output {
    YouCantDoThisAction("you can’t do this action in this phase");
    private String output;
    Output(String output){
        this.output = output;
    }

    @Override
    public String toString(){
        return output;
    }
}
