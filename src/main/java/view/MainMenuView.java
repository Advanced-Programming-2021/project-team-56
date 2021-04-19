package view;

import model.User;

public class MainMenuView {

    private static MainMenuView mainMenuView;

    private MainMenuView() {
    }

    public static MainMenuView getInstance() {
        if (mainMenuView == null)
            mainMenuView = new MainMenuView();
        return mainMenuView;
    }

    public void run(String username) {

    }
}
