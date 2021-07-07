package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClientThread extends Thread {
    Socket client;

    ClientThread(Socket inSocket) {
        client = inSocket;
    }

    public void run() {
        try {
            DataInputStream inStream = new DataInputStream(client.getInputStream());
            DataOutputStream outStream = new DataOutputStream(client.getOutputStream());
            String clientMessage = "", serverMessage = "";
            while (!clientMessage.equals("bye")) {
                clientMessage = inStream.readUTF();
                outStream.writeUTF(serverMessage);
                outStream.flush();
            }
            inStream.close();
            outStream.close();
            client.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println("Client - exit!! ");
        }
    }
}
