package view;

import controller.ProfileController;
import model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileView {

    private static ProfileView profileView;
    static Pattern menuEnter = Pattern.compile("^menu enter (?:Login|Duel|Deck|Scoreboard|Profile|Shop|Import/Export)$");
    static Pattern changeNickname = Pattern.compile("^profile change --nickname (\\S+)$");

    private ProfileView() {
    }

    public static ProfileView getInstance() {
        if (profileView == null)
            profileView = new ProfileView();
        return profileView;
    }

    public void run(String username) {
        ProfileController profileController = ProfileController.getInstance();
        String command;
        while (true) {
            command = LoginMenuView.scan.nextLine().trim();
            Matcher matcher = menuEnter.matcher(command);
            if (matcher.find()) {
                System.out.println("menu navigation is not possible");
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
                System.out.println(profileController.checkProfileChangePasswordCommand(username, command));
            }
        }
    }

    private void checkProfileChangeNicknameCommand(String username, String command) {
        Matcher matcher = changeNickname.matcher(command);
        if (matcher.find()) {
            String newNickname = matcher.group(1);
            System.out.println(ProfileController.getInstance().changeNickname(username, newNickname));
        } else {
            System.out.println("invalid command");
        }
    }


}
