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

    private String changePassword(String password) {

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

    public String verifyOrder(String command) {

    }

    private String showCurrentMenu() {

    }
}
