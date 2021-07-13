package server;

import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread {
    private final Socket client;

    public ClientThread(Socket inSocket) {
        client = inSocket;
    }

    public void run() {
        try {
            DataInputStream inStream = new DataInputStream(client.getInputStream());
            DataOutputStream outStream = new DataOutputStream(client.getOutputStream());
            String clientMessage = "";
            String serverMessage;
            while (!clientMessage.equals("bye")) {
                clientMessage = inStream.readUTF();
                serverMessage = process(clientMessage);
                outStream.writeUTF(serverMessage);
                outStream.flush();
            }
            inStream.close();
            outStream.close();
            client.close();
        } catch (Exception ignored) {

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
        } else if (clientMessage.startsWith("Change-Password")) {
            String[] token = clientMessage.split(" ");
            return ProfileController.getInstance().changePasswords(token[1], token[2], token[3]);
        } else if (clientMessage.startsWith("Change-Username")) {
            String[] token = clientMessage.split(" ");
            return ProfileController.getInstance().changeNickname(token[1], token[2]);
        } else if (clientMessage.equals("Show-ScoreBoard")) {
            return ScoreBoardController.getInstance().showScoreBoard();
//        } else if (clientMessage.startsWith("Get-NickName")) {
//            String[] token = clientMessage.split(" ");
//            return LoginController.getInstance().getNickname(token[1]);
        } else if (clientMessage.startsWith("Get-User")) {
            String[] token = clientMessage.split(" ");
            sendUser(token[1]);
        }
        return "";
    }

    private Object sendUser(String username) {
        try {
            OutputStream outputStream = client.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(ServerUsers.getUserByUsername(username));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sendUser(username);
    }
}
