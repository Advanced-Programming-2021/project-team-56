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
        ServerUser user = ServerUser.getUserByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            return LOGIN_FAILED.value;
        }
        return LOGIN_SUCCESSFUL.value;
    }

    public synchronized String register(String username, String password, String nickname) {
        if (ServerUser.isThisUsernameAlreadyTaken(username)) {
            return "user with username " + "\"" + username + "\"" + " already exists";
        }
        if (ServerUser.isThisNicknameAlreadyTaken(nickname)) {
            return "user with nickname " + "\"" + nickname + "\"" + " already exists";
        }
        new ServerUser(nickname, password, username);
        return SIGNUP_SUCCESSFUL.value;
    }

    public String getNickname(String username) {
        return ServerUser.getUserByUsername(username).getNickname();
    }

    public String getAvatarURL(String username) {
        return ServerUser.getUserByUsername(username).getAvatarURL();
    }
}
