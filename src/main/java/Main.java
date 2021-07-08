import controller.LoginMenuController;
import view.MainGUI;

import java.io.IOException;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8888);
        LoginMenuController.getInstance().readFromJson();
        MainGUI.main(args);
    }
}