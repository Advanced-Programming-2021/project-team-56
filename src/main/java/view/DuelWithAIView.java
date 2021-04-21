package view;

import model.User;

public class DuelWithAIView {

    private static DuelWithAIView duelWithAIView;

    private DuelWithAIView() {
    }

    public static DuelWithAIView getInstance() {
        if (duelWithAIView == null)
            duelWithAIView = new DuelWithAIView();
        return duelWithAIView;
    }

    public void run(User user) {

    }
}
