package view;

import controller.ProfileController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileView {

    private static ProfileView profileView;
    static Pattern menuEnter = Pattern.compile("^menu enter (?:Login|Duel|Deck|Scoreboard|Profile|Shop|Import/Export)$");
    static Pattern changeNickname = Pattern.compile("^profile change --nickname (\\S+)$");
    static Pattern changePassword1 = Pattern.compile("^profile change --password --current (\\S+) --new (\\S+)$");
    static Pattern changePassword2 = Pattern.compile("^profile change --current (\\S+) --password --new (\\S+)$");
    static Pattern changePassword3 = Pattern.compile("^profile change --current (\\S+) --new (\\S+) --password$");
    static Pattern changePassword4 = Pattern.compile("^profile change --password --new (\\S+) --current (\\S+)$");
    static Pattern changePassword5 = Pattern.compile("^profile change --new (\\S+) --password --current (\\S+)$");
    static Pattern changePassword6 = Pattern.compile("^profile change --new (\\S+) --current (\\S+) --password$");

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
        Matcher matcher = menuEnter.matcher(command);
        if (matcher.find()) {
            System.out.println("menu navigation is not possible");
        } else {
            System.out.println("invalid command");
        }
    }

    private void checkProfileChangeNicknameCommand(String username, String command) {
        Matcher matcher = changeNickname.matcher(command);
        if (matcher.find()) {
            String newNickname = matcher.group(1);
            String result = ProfileController.getInstance().changeNickname(username, newNickname);
            System.out.println(result);
        } else {
            System.out.println("invalid command");
        }
    }

    private void checkProfileChangePasswordCommand(String username, String command) {
        Matcher matcher = changePassword1.matcher(command);
        if (matcher.find()) {
            checkPasswords(matcher.group(1), matcher.group(2), username);
            return;
        }
        matcher = changePassword2.matcher(command);
        if (matcher.find()) {
            checkPasswords(matcher.group(1), matcher.group(2), username);
            return;
        }
        matcher = changePassword3.matcher(command);
        if (matcher.find()) {
            checkPasswords(matcher.group(1), matcher.group(2), username);
            return;
        }
        matcher = changePassword4.matcher(command);
        if (matcher.find()) {
            checkPasswords(matcher.group(2), matcher.group(1), username);
            return;
        }
        matcher = changePassword5.matcher(command);
        if (matcher.find()) {
            checkPasswords(matcher.group(2), matcher.group(1), username);
            return;
        }
        matcher = changePassword6.matcher(command);
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
