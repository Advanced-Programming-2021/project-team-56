package view;

import java.util.Scanner;

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

            if (command.startsWith(""))
        }
    }


}
