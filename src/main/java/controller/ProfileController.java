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

    public String changeNickname(String username, String nickname) {
        if (User.isThisNicknameAlreadyTaken(nickname)) {
            return "user with nickname " + nickname + " already exists";
        } else {
            User.getUserByUsername(username).setNickname(nickname);
            return "nickname changed successfully";
        }
    }

    public String changePasswords(String newPassword, String username) {
        if (User.getCurrentUser().getPassword().equals(newPassword)) {
            return "please enter a new password";
        }
        User.getUserByUsername(username).setPassword(newPassword);
        return "password changed successfully";
    }

    public void changeUserAvatar(String newAvatarURL) {
        User.getCurrentUser().setAvatarURL(newAvatarURL);
    }

}
