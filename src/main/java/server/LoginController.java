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
        User user = ServerUsers.getUserByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            return LOGIN_FAILED.value;
        }
        return LOGIN_SUCCESSFUL.value;
    }

    public synchronized String register(String username, String password, String nickname) {
        if (ServerUsers.isThisUsernameAlreadyTaken(username)) {
            return "user with username " + "\"" + username + "\"" + " already exists";
        }
        if (ServerUsers.isThisNicknameAlreadyTaken(nickname)) {
            return "user with nickname " + "\"" + nickname + "\"" + " already exists";
        }
        ServerUsers.getUsers().add(new User(nickname, password, username));
        return SIGNUP_SUCCESSFUL.value;
    }
}
