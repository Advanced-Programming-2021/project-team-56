package view;

import controller.LoginMenuController;
import sun.tools.jar.Main;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMenuView {

    private static LoginMenuView loginMenuView;

    private LoginMenuView() {
    }

    public static LoginMenuView getInstance() {
        if (loginMenuView == null)
            loginMenuView = new LoginMenuView();
        return loginMenuView;
    }

    public static Scanner scan = new Scanner(System.in);
    public void run() {
        String command;
        while (true) {
            command = scan.nextLine();
            command = command.trim();

            if (command.equals("menu show-current")) {
                System.out.println("Login Menu");
                continue;
            }
            if (command.startsWith("user create")) {
                createUser(command)
            }
        }
    }

    public static Matcher getMatcher(String command, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(command);
        return matcher;
    }

    private void createUser(String command) {
        String username, password, nickname;
        String regex = "--username (\\S+)";
        Matcher matcher = getMatcher(command, regex);
        if (matcher.find()) {
            username = matcher.group(1);
        }else {
            System.out.println("invalid command");
            return;
        }

        regex = "--password (\\S+)";
        matcher = getMatcher(command, regex);
        if (matcher.find()) {
            password = matcher.group(1);
        } else {
            System.out.println("invalid command");
            return;
        }
        regex = "--nickname (\\S+)";
        matcher = getMatcher(command, regex);
        if (matcher.find()) {
            nickname = matcher.group(1);
        } else {
            System.out.println("invalid command");
            return;
        }
        LoginMenuController.register

    }
}
