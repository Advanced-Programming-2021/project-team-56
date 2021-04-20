package view;

import controller.ProfileController;

import java.util.regex.Matcher;

public class ProfileView {

    private static ProfileView profileView;

    private ProfileView() {
    }

    public static ProfileView getInstance() {
        if (profileView == null)
            profileView = new ProfileView();
        return profileView;
    }

    public void run(String username) {
        String command;
        while (true) {
            command = LoginMenuView.scan.nextLine().trim();
            if (command.startsWith("menu enter")) {
                checkMenuEnterCommand(command);
                continue;
            }
            if (command.equals("menu exit")) {
                return;
            }
            if (command.equals("menu show-current")) {
                System.out.println("Profile Menu");
                continue;
            }
            if (command.startsWith("profile change --nickname")) {
                checkProfileChangeNicknameCommand(username, command);
                continue;
            }
            if (command.startsWith("profile change")) {
                checkProfileChangePasswordCommand(username, command);
            }
        }
    }

    private void checkMenuEnterCommand(String command) {
        String regex = "^menu enter (?:Login|Duel|Deck|Scoreboard|Profile|Shop|Import/Export)$";
        if (LoginMenuView.getMatcher(command, regex).find()) {
            System.out.println("menu navigation is not possible");
        } else {
            System.out.println("invalid command");
        }
    }

    private void checkProfileChangeNicknameCommand(String username, String command) {
        Matcher matcher = LoginMenuView.getMatcher(command, "^profile change --nickname (\\S+)$");
        if (matcher.find()) {
            String newNickname = matcher.group(1);
            String result = ProfileController.getInstance().changeNickname(username, newNickname);
            System.out.println(result);
        } else {
            System.out.println("invalid command");
        }
    }

    private void checkProfileChangePasswordCommand(String username, String command) {
        Matcher matcher = LoginMenuView.getMatcher(command, "^profile change --password --current (\\S+) --new (\\S+)$");
        if (matcher.find()) {
            checkPasswords(matcher.group(1), matcher.group(2), username);
            return;
        }
        matcher = LoginMenuView.getMatcher(command, "^profile change --current (\\S+) --password --new (\\S+)$");
        if (matcher.find()) {
            checkPasswords(matcher.group(1), matcher.group(2), username);
            return;
        }
        matcher = LoginMenuView.getMatcher(command, "^profile change --current (\\S+) --new (\\S+) --password$");
        if (matcher.find()) {
            checkPasswords(matcher.group(1), matcher.group(2), username);
            return;
        }
        matcher = LoginMenuView.getMatcher(command, "^profile change --password --new (\\S+) --current (\\S+)$");
        if (matcher.find()) {
            checkPasswords(matcher.group(2), matcher.group(1), username);
            return;
        }
        matcher = LoginMenuView.getMatcher(command, "^profile change --new (\\S+) --password --current (\\S+)$");
        if (matcher.find()) {
            checkPasswords(matcher.group(2), matcher.group(1), username);
            return;
        }
        matcher = LoginMenuView.getMatcher(command, "^profile change --new (\\S+) --current (\\S+) --password$");
        if (matcher.find()) {
            checkPasswords(matcher.group(2), matcher.group(1), username);
            return;
        }
        System.out.println("invalid command");
    }

    private void checkPasswords(String currentPassword, String newPassword, String username) {
        if (ProfileController.getInstance().isPasswordValid(currentPassword, username).equals("isValid")) {
            if (currentPassword.equals(newPassword)) {
                System.out.println("please enter a new password");
            } else {
                String result = ProfileController.getInstance().changePassword(newPassword, username);
                System.out.println(result);
            }
        } else {
            System.out.println("current password is invalid");
        }
    }
}
