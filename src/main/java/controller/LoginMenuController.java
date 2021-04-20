package controller;

import model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMenuController {

    private static LoginMenuController loginMenuController;
    static Pattern createUser1 = Pattern.compile("^user create --username (\\S+) --password (\\S+) --nickname (\\S+)$");
    static Pattern createUser2 = Pattern.compile("^user create --username (\\S+) --nickname (\\S+) --password (\\S+)$");
    static Pattern createUser3 = Pattern.compile("^user create --password (\\S+) --nickname (\\S+) --username (\\S+)$");
    static Pattern createUser4 = Pattern.compile("^user create --password (\\S+) --username (\\S+) --nickname (\\S+)$");
    static Pattern createUser5 = Pattern.compile("^user create --nickname (\\S+) --username (\\S+) --password (\\S+)$");
    static Pattern createUser6 = Pattern.compile("^user create --nickname (\\S+) --password (\\S+) --username (\\S+)$");

    private LoginMenuController() {
    }

    public static LoginMenuController getInstance() {
        if (loginMenuController == null)
            loginMenuController = new LoginMenuController();
        return loginMenuController;
    }


    public String logIn(String username, String password) {
        User user = User.getUserByUsername(username);
        if (user == null) {
            return "Username and password didn’t match!";
        }
        if (!user.getPassword().equals(password)) {
            return "Username and password didn’t match!";
        }
        return "user logged in successfully!";
    }

    public String register(String username, String password, String nickname) {
        if (User.isThisUsernameAlreadyTaken(username)) {
            return "user with username " + username + " already exists";
        }
        if (User.isThisNicknameAlreadyTaken(nickname)) {
            return "user with nickname " + nickname + " already exists";
        }
        new User(username, nickname, password);
        return "user created successfully!";
    }

    public String createUser(String command) {
        String result = "";
        Matcher matcher = createUser1.matcher(command);
        if (matcher.find()) {
            result = register(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        matcher = createUser2.matcher(command);
        if (matcher.find()) {
            result = register(matcher.group(1), matcher.group(3), matcher.group(2));
        }
        matcher = createUser3.matcher(command);
        if (matcher.find()) {
            result = register(matcher.group(3), matcher.group(1), matcher.group(2));
        }
        matcher = createUser4.matcher(command);
        if (matcher.find()) {
            result = register(matcher.group(2), matcher.group(1), matcher.group(3));
        }
        matcher = createUser5.matcher(command);
        if (matcher.find()) {
            result = register(matcher.group(2), matcher.group(3), matcher.group(1));
        }
        matcher = createUser6.matcher(command);
        if (matcher.find()) {
            result = register(matcher.group(3), matcher.group(2), matcher.group(1));
        }
        if (result.equals("")) return "invalid command";
        else return result;
    }
}
