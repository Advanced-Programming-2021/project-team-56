package controller;

import model.User;

public class ProfileController {

    public static String changePassword(String password) {

    }

    public static String changeNickname(String username, String nickname) {
        User user = User.getUserByUsername(username);
        if (User.isThisNicknameAlreadyTaken(nickname)) {
            return "user with nickname " + nickname + " already exists";
        } else {
            user.setNickname(nickname);
            return "nickname changed successfully";
        }
    }

    public static String verifyOrder(String command) {

    }

    public static String showCurrentMenu() {

    }
}
