package controller.duel;

public class DuelWithUserController {

    private static DuelWithUserController duelWithUserController;

    private DuelWithUserController() {
    }

    public static DuelWithUserController getInstance() {
        if (duelWithUserController == null)
            duelWithUserController = new DuelWithUserController();
        return duelWithUserController;
    }
}
