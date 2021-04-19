import view.LoginMenuView;

import java.util.Scanner;

public class Main {
    public void main(String[] args) {
        LoginMenuView loginMenuView = LoginMenuView.getInstance();
        loginMenuView.run();
    }
}
