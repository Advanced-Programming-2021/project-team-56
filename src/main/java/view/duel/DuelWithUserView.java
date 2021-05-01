package view.duel;

import controller.DeckMenuController;
import controller.duel.DuelWithUserController;
import model.User;
import view.LoginMenuView;

public class DuelWithUserView {

    private static DuelWithUserView duelWithUserView;

    private DuelWithUserView() {
    }

    public static DuelWithUserView getInstance() {
        if (duelWithUserView == null)
            duelWithUserView = new DuelWithUserView();
        return duelWithUserView;
    }

    public void run(String username, String secondPlayerUsername, String rounds) {
        DuelWithUserController.getInstance().setUpGame(username, secondPlayerUsername);
        while (true){
            String command = LoginMenuView.scan.nextLine().trim();
        }
    }
}
