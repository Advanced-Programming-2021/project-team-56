import view.ClientSocket;
import view.MainGUI;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        ClientSocket.initialize();
        MainGUI.main(args);
    }
}