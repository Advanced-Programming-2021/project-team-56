package controller.duel;

public class DuelWithAIController {

    private static DuelWithAIController duelWithAIController;

    private DuelWithAIController() {
    }

    public static DuelWithAIController getInstance() {
        if (duelWithAIController == null)
            duelWithAIController = new DuelWithAIController();
        return  duelWithAIController;
    }

    private int turnCounter;
    private int phase;

}
