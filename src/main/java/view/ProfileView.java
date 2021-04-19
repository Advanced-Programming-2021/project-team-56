package view;

import controller.ProfileController;
import model.User;

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

    public void run(User user) {
    public void run(String username) {
        User user = User.getUserByUsername(username);
        String command;
        while (true) {
            command = LoginMenuView.scan.nextLine();
            command = command.trim();
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

    private void checkProfileChangeNicknameCommand(String username, String command) {
        Matcher matcher = LoginMenuView.getMatcher(command, "^profile change --nickname (\\S+)$");
        if (matcher.find()) {
            String newNickname = matcher.group(1);
            String result = ProfileController.changeNickname(username, newNickname);
            System.out.println(result);
        } else {
            System.out.println("invalid command");
        }
    }

    private void checkProfileChangePasswordCommand(String username, String command) {
        Matcher matcher = LoginMenuView.getMatcher(command, "");
    }
}
