import view.LoginMenuView;

import java.util.Scanner;

public class Main {
    public static Scanner mainScanner = new Scanner(System.in);
    public void main(String[] args) {
        LoginMenuView loginMenuView = new LoginMenuView();
        loginMenuView.run();
    }
}
