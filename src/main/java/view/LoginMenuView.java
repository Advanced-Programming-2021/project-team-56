package view;

import controller.LoginMenuController;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static view.MainMenuView.menuEnter;

public class LoginMenuView {

    private static LoginMenuView loginMenuView;
    static Pattern loginUser = Pattern.compile("^user login (\\S+) (\\S+) (\\S+) (\\S+)$");

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
            command = scan.nextLine().trim();
            if (command.equals("menu show-current")) {
                System.out.println("Login Menu");
                continue;
            }
            if (command.startsWith("user create")) {
                System.out.println(LoginMenuController.getInstance().createUser(command));
                continue;
            }
            if (command.startsWith("user login")) {
                loginUser(command);
                continue;
            }
            if (command.equals("menu exit")) {
                break;
            }
            Matcher matcher = menuEnter.matcher(command);
            if (matcher.find()) {
                System.out.println("please login first");
                continue;
            }
            System.out.println("invalid command");
        }
    }

    private void loginUser(String command) {
        LoginMenuController loginMenuController = LoginMenuController.getInstance();
        String result = "";
        String username = "";
        Matcher matcher = loginUser.matcher(command);
        if (matcher.find()) {
            if (matcher.group(1).equals("--username") && matcher.group(3).equals("--password")) {
                username = matcher.group(2);
                result = loginMenuController.logIn(username, matcher.group(4));
            }
            if (matcher.group(1).equals("--password") && matcher.group(3).equals("--username")) {
                username = matcher.group(4);
                result = loginMenuController.logIn(username, matcher.group(2));
            }
        }
        if (result.equals("")) {
            System.out.println("invalid command");
        } else {
            System.out.println(result);
            if (result.equals("user logged in successfully!")) {
                MainMenuView.getInstance().run(username);
            }
        }
    }
}
