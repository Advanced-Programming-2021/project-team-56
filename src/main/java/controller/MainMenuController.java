package controller ;

public class MainMenuController {

    private static MainMenuController mainMenuController;

    private MainMenuController() {
    }

    public static MainMenuController getInstance() {
        if (mainMenuController == null)
            mainMenuController = new MainMenuController();
        return mainMenuController;
    }

    private String showCurrentMenu() {

    }

    private String menuNavigator() {

    }
}
