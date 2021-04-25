package controller;

public class DuelMenuController {

    private static DuelMenuController duelMenuController;

    public static DuelMenuController getInstance() {
        if (duelMenuController == null)
            duelMenuController = new DuelMenuController();
        return duelMenuController;
    }
}
