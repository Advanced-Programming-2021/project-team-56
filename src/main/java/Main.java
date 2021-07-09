import view.ClientSocket;
import view.MainGUI;

import java.io.IOException;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws IOException {
        ClientSocket.initialize();
        MainGUI.main(args);
    }
}