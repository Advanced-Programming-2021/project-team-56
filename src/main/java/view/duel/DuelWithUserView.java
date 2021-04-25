package view.duel;

import model.User;

public class DuelWithUserView {

    private static DuelWithUserView duelWithUserView;

    private DuelWithUserView() {
    }

    public static DuelWithUserView getInstance() {
        if (duelWithUserView == null)
            duelWithUserView = new DuelWithUserView();
        return duelWithUserView;
    }

    public void run(User user) {

    }
}
