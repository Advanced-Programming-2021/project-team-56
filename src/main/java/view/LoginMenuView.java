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
                createUser(command);
                continue;
            }
            if (command.startsWith("user login")) {
                loginUser(command);
                continue;
            }
            if (command.equals("menu exit")) {
                break;
            }
            String regex = "^menu enter (?:Duel|Deck|Scoreboard|Profile|Shop|Import\\/Export)$";
            Matcher matcher = getMatcher(command, regex);
            if (matcher.find()) {
                System.out.println("please login first");
                continue;
            }
            System.out.println("invalid command");
        }
    }

    public static Matcher getMatcher(String command, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(command);
        return matcher;
    }

    private void createUser(String command) {
        LoginMenuController loginMenuController = LoginMenuController.getInstance();
        String result = "";
        String regex = "^user create --uesername (\\S+) --password (\\S+) --nickname (\\S+)$";
        Matcher matcher = getMatcher(command, regex);
        if (matcher.find()) {
            result = loginMenuController.register(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        regex = "^user create --uesername (\\S+) --nickname (\\S+) --password (\\S+)$";
        matcher = getMatcher(command, regex);
        if (matcher.find()) {
            result = loginMenuController.register(matcher.group(1), matcher.group(3), matcher.group(2));
        }
        regex = "^user create --password (\\S+) --nickname (\\S+) --username (\\S+)$";
        matcher = getMatcher(command, regex);
        if (matcher.find()) {
            result = loginMenuController.register(matcher.group(3), matcher.group(1), matcher.group(2));
        }
        regex = "^user create --password (\\S+) --username (\\S+) --nickname (\\S+)$";
        matcher = getMatcher(command, regex);
        if (matcher.find()) {
            result = loginMenuController.register(matcher.group(2), matcher.group(1), matcher.group(3));
        }
        regex = "^user create --nickname (\\S+) --username (\\S+) --password (\\S+)$";
        matcher = getMatcher(command, regex);
        if (matcher.find()) {
            result = loginMenuController.register(matcher.group(2), matcher.group(3), matcher.group(1));
        }
        regex = "^user create --nickname (\\S+) --password (\\S+) --username (\\S+)$";
        matcher = getMatcher(command, regex);
        if (matcher.find()) {
            result = loginMenuController.register(matcher.group(3), matcher.group(2), matcher.group(1));
        }
        if (result.equals("")) System.out.println("invalid command");
        else System.out.println(result);
    }

    private void loginUser(String command) {
        LoginMenuController loginMenuController = LoginMenuController.getInstance();
        String result = "";
        String username = "";
        String regex = "^user login (\\S+) (\\S+) (\\S+) (\\S+)$";
        Matcher matcher = getMatcher(command, regex);
        if (matcher.find()) {
            if (matcher.group(1).equals("--username") && matcher.group(3).equals("--password")) {
                result = loginMenuController.logIn(matcher.group(2), matcher.group(4));
                username = matcher.group(2);
            }
            if (matcher.group(1).equals("--password") && matcher.group(3).equals("--username")) {
                result = loginMenuController.logIn(matcher.group(4), matcher.group(2));
                username = matcher.group(4);
            }
        }
        if (result.equals("")) {
            System.out.println("invalid command");
        } else {
            System.out.println(result);
        }
        if (result.equals("user logged in successfully!")) {
            MainMenuView mainMenuView = MainMenuView.getInstance();
            mainMenuView.run(username);
        }
    }
}
