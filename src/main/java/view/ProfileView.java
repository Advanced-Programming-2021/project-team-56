package view;

import model.User;

public class ProfileView {

    public void run(String username) {
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
            if (command.startsWith("")) {

            }
        }
    }
}
