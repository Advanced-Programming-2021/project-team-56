package controller;

import model.User;

public class ProfileController {

    private static ProfileController profileController;

    private ProfileController() {
    }

    public static ProfileController getInstance() {
        if (profileController == null)
            profileController = new ProfileController();
        return profileController;
    }

    public String isPasswordValid(String password, String username) {
        User user = User.getUserByUsername(username);
        if (user.getPassword().equals(password)) {
            return "isValid";
        } else {
            return "isNotValid";
        }
    }

    public String changePassword(String password, String username) {
        User user = User.getUserByUsername(username);
        user.setPassword(password);
        return "password changed successfully";
    }

    public String changeNickname(String username, String nickname) {
        if (User.isThisNicknameAlreadyTaken(nickname)) {
            return "user with nickname " + nickname + " already exists";
        } else {
            User user = User.getUserByUsername(username);
            user.setNickname(nickname);
            return "nickname changed successfully";
        }
    }

}
