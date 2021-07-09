package view;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientSocket {
    public static DataOutputStream dataOutputStream;
    public static DataInputStream dataInputStream;
    public static Socket socket;

    public static void initialize() {
        try {
            socket = new Socket("localhost", 8888);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
