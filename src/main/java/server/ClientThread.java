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
                outStream.writeUTF("");
                outStream.flush();
                clientMessage = inStream.readUTF();
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
}
