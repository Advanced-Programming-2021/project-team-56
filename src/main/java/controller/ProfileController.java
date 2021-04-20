package controller;

import model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileController {

    private static ProfileController profileController;
    static Pattern changePassword1 = Pattern.compile("^profile change --password --current (\\S+) --new (\\S+)$");
    static Pattern changePassword2 = Pattern.compile("^profile change --current (\\S+) --password --new (\\S+)$");
    static Pattern changePassword3 = Pattern.compile("^profile change --current (\\S+) --new (\\S+) --password$");
    static Pattern changePassword4 = Pattern.compile("^profile change --password --new (\\S+) --current (\\S+)$");
    static Pattern changePassword5 = Pattern.compile("^profile change --new (\\S+) --password --current (\\S+)$");
    static Pattern changePassword6 = Pattern.compile("^profile change --new (\\S+) --current (\\S+) --password$");

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

    private String checkPasswords(String currentPassword, String newPassword, String username) {
        if (User.getUserByUsername(username).getPassword().equals(currentPassword)) {
            if (currentPassword.equals(newPassword)) {
                return "please enter a new password";
            } else {
                User.getUserByUsername(username).setPassword(newPassword);
                return "password changed successfully";
            }
        } else {
            return "current password is invalid";
        }
    }

    public String checkProfileChangePasswordCommand(String username, String command) {
        Matcher matcher = changePassword1.matcher(command);
        if (matcher.find()) {
            return checkPasswords(matcher.group(1), matcher.group(2), username);
        }
        matcher = changePassword2.matcher(command);
        if (matcher.find()) {
            return checkPasswords(matcher.group(1), matcher.group(2), username);
        }
        matcher = changePassword3.matcher(command);
        if (matcher.find()) {
            return checkPasswords(matcher.group(1), matcher.group(2), username);
        }
        matcher = changePassword4.matcher(command);
        if (matcher.find()) {
            return checkPasswords(matcher.group(2), matcher.group(1), username);
        }
        matcher = changePassword5.matcher(command);
        if (matcher.find()) {
            return checkPasswords(matcher.group(2), matcher.group(1), username);
        }
        matcher = changePassword6.matcher(command);
        if (matcher.find()) {
            return checkPasswords(matcher.group(2), matcher.group(1), username);
        }
        return "invalid command";
    }

}
