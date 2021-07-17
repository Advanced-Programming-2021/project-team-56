package server.serverController;

import model.enums.AvatarURL;
import server.model.ServerUsers;
import server.User;

import java.util.Random;

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
        User user = new User(nickname, password, username);
        setUsersRandomAvatarURL(user);
        ServerUsers.getUsers().add(user);
        return SIGNUP_SUCCESSFUL.value;
    }

    public void setUsersRandomAvatarURL(User user) {
        AvatarURL[] avatarURLs = AvatarURL.class.getEnumConstants();
        int avatarURLsLength = AvatarURL.class.getEnumConstants().length;
        int randomNumber = new Random().nextInt(avatarURLsLength);
        user.setAvatarURL(avatarURLs[randomNumber].value);
    }
}
