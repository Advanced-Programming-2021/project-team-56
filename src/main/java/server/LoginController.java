package server;

import static model.enums.ProcessResult.*;

public class LoginController {

    private static LoginController loginController;

    private LoginController() {
    }

    public static LoginController getInstance() {
        if (loginController == null)
            loginController = new LoginController();
        return loginController;
    }

    public String logIn(String username, String password) {
        User user = User.getUserByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            return LOGIN_FAILED.value;
        }
        User.setCurrentUser(user);
        return LOGIN_SUCCESSFUL.value;
    }

    public void logout() {
        User.setCurrentUser(null);
    }

    public String register(String username, String password, String nickname) {
        if (User.isThisUsernameAlreadyTaken(username)) {
            return "user with username " + "\"" + username + "\"" + " already exists";
        }
        if (User.isThisNicknameAlreadyTaken(nickname)) {
            return "user with nickname " + "\"" + nickname + "\"" + " already exists";
        }
        new User(username, nickname, password);
        return SIGNUP_SUCCESSFUL.value;
    }
}