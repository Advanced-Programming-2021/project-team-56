package controller;

public class LoginMenuController {

    private static LoginMenuController loginMenuController;

    private LoginMenuController() {
    }

    public static LoginMenuController getInstance() {
        if (loginMenuController == null)
            loginMenuController = new LoginMenuController();
        return loginMenuController;
    }


    public String logIn() {

    }

    public String register(String username, String password, String nickname) {

    }
}
