import controller.LoginMenuController;
import view.MainGUI;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        LoginMenuController.getInstance().readFromJson();
        MainGUI.main(args);
    }
}