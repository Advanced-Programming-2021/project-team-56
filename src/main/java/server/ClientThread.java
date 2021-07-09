package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClientThread extends Thread {
    private final Socket client;
    private DataInputStream inStream;
    private DataOutputStream outStream;

    public ClientThread(Socket inSocket) {
        client = inSocket;
    }

    public void run() {
        try {
            inStream = new DataInputStream(client.getInputStream());
            outStream = new DataOutputStream(client.getOutputStream());
            String clientMessage = "";
            String serverMessage = "";
            while (!clientMessage.equals("bye")) {
                clientMessage = inStream.readUTF();
                outStream.writeUTF(process(clientMessage));
                outStream.flush();
            }
            inStream.close();
            outStream.close();
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Client - exit!! ");
        }
    }

    public String process(String clientMessage) {
        if (clientMessage.startsWith("Login")) {
            String[] token = clientMessage.split(" ");
            return LoginController.getInstance().logIn(token[1], token[2]);
        } else if (clientMessage.startsWith("Sign-Up")) {
            String[] token = clientMessage.split(" ");
            return LoginController.getInstance().register(token[1], token[2], token[3]);
        }
        return "";
    }
}
