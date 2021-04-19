package controller;

import model.User;

public class LoginMenuController {

    private static LoginMenuController loginMenuController;

    private LoginMenuController() {
    }

    public static LoginMenuController getInstance() {
        if (loginMenuController == null)
            loginMenuController = new LoginMenuController();
        return loginMenuController;
    }


    public String logIn(String username, String password) {
        User user = User.getUserByUsername(username);
        if (user == null) {
            return "Username and password didn’t match!";
        }
        if (user.getPassword().equals(password)) {
            return "Username and password didn’t match!";
        }
        return "user logged in successfully!";
    }

    public String register(String username, String password, String nickname) {
        if (User.isThisUsernameAlreadyTaken(username)) {
            return "user with username " + username + " already exists";
        }
        if (User.isThisNicknameAlreadyTaken(nickname)) {
            return "user with nickname " + nickname + " already exists";
        }
        User user = new User(username, nickname, password);
        return "user created successfully!";
    }
}
