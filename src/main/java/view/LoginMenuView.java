package view;

import sun.tools.jar.Main;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMenuView {
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
}
