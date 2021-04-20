package view;

import controller.LoginMenuController;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMenuView {

    private static LoginMenuView loginMenuView;
    static Pattern menuEnter = Pattern.compile("^menu enter (?:Login|Duel|Deck|Scoreboard|Profile|Shop|Import\\/Export)$");
    static Pattern createUser1 = Pattern.compile("^user create --username (\\S+) --password (\\S+) --nickname (\\S+)$");
    static Pattern createUser2 = Pattern.compile("^user create --username (\\S+) --nickname (\\S+) --password (\\S+)$");
    static Pattern createUser3 = Pattern.compile("^user create --password (\\S+) --nickname (\\S+) --username (\\S+)$");
    static Pattern createUser4 = Pattern.compile("^user create --password (\\S+) --username (\\S+) --nickname (\\S+)$");
    static Pattern createUser5 = Pattern.compile("^user create --nickname (\\S+) --username (\\S+) --password (\\S+)$");
    static Pattern createUser6 = Pattern.compile("^user create --nickname (\\S+) --password (\\S+) --username (\\S+)$");
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
            Matcher matcher = menuEnter.matcher(command);
            if (matcher.find()) {
                System.out.println("please login first");
                continue;
            }
            System.out.println("invalid command");
        }
    }

    private void createUser(String command) {
        LoginMenuController loginMenuController = LoginMenuController.getInstance();
        String result = "";
        Matcher matcher = createUser1.matcher(command);
        if (matcher.find()) {
            result = loginMenuController.register(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        matcher = createUser2.matcher(command);
        if (matcher.find()) {
            result = loginMenuController.register(matcher.group(1), matcher.group(3), matcher.group(2));
        }
        matcher = createUser3.matcher(command);
        if (matcher.find()) {
            result = loginMenuController.register(matcher.group(3), matcher.group(1), matcher.group(2));
        }
        matcher = createUser4.matcher(command);
        if (matcher.find()) {
            result = loginMenuController.register(matcher.group(2), matcher.group(1), matcher.group(3));
        }
        matcher = createUser5.matcher(command);
        if (matcher.find()) {
            result = loginMenuController.register(matcher.group(2), matcher.group(3), matcher.group(1));
        }
        matcher = createUser6.matcher(command);
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
        Matcher matcher = loginUser.matcher(command);
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
            MainMenuView.getInstance().run(username);
        }
    }
}
