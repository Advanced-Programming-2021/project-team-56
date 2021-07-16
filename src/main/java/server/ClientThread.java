package server;

import com.gilecode.yagson.YaGson;
import server.model.Card;
import server.model.ServerUsers;
import serverController.LoginController;
import serverController.ProfileController;
import serverController.ScoreBoardController;
import serverController.ShopController;

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
        String[] token = clientMessage.split(" ");
        if (clientMessage.startsWith("Login")) {
            return LoginController.getInstance().logIn(token[1], token[2]);
        } else if (clientMessage.startsWith("Sign-Up")) {
            return LoginController.getInstance().register(token[1], token[2], token[3]);
        } else if (clientMessage.startsWith("Change-Password")) {
            return ProfileController.getInstance().changePasswords(token[1], token[2], token[3]);
        } else if (clientMessage.startsWith("Change-Username")) {
            return ProfileController.getInstance().changeNickname(token[1], token[2]);
        } else if (clientMessage.startsWith("Buy-Card")) {
            return ShopController.getInstance().buyCard(token[1], token[2]);
        } else if (clientMessage.equals("Show-ScoreBoard")) {
            return ScoreBoardController.getInstance().showScoreBoard();
        } else if (clientMessage.startsWith("Get-User")) {
            return sendUser(token[1]);
        }else if(clientMessage.startsWith("Get-Cards")){
            return sendCards();
        }
        return "";
    }

    private String sendUser(String username) {
        YaGson yaGson = new YaGson();
        return yaGson.toJson(ServerUsers.getUserByUsername(username));
    }

    private String sendCards(){
        YaGson yaGson = new YaGson();
        return yaGson.toJson(Card.getCards());
    }
}
