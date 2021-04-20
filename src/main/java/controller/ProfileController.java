﻿package controller;

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

    public void changePassword(String password, String username) {
        User user = User.getUserByUsername(username);
        user.setPassword(password);
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
