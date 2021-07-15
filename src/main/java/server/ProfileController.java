package server;

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
        if (ServerUsers.isThisNicknameAlreadyTaken(nickname)) {
            return "user with nickname " + nickname + " already exists";
        } else {
            ServerUsers.getUserByUsername(username).setNickname(nickname);
            return "nickname changed successfully";
        }
    }

    public String changePasswords(String currentPassWord, String newPassword, String username) {
        User user = ServerUsers.getUserByUsername(username);
        if (!user.getPassword().equals(currentPassWord)) {
            return "current password is invalid";
        }
        if (user.getPassword().equals(newPassword)) {
            return "please enter a new password";
        }
        user.setPassword(newPassword);
        return "password changed successfully";
    }
}
