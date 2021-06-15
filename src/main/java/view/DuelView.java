package view;

public class DuelView {

    private static DuelView duelView;

    public static DuelView getInstance() {
        if (duelView == null)
            duelView = new DuelView();
        return duelView;
    }
}
