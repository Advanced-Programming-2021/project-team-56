package view;

public class DuelMenuView {

    private static DuelMenuView duelMenuView;

    public static DuelMenuView getInstance() {
        if (duelMenuView == null)
            duelMenuView = new DuelMenuView();
        return duelMenuView;
    }
}
