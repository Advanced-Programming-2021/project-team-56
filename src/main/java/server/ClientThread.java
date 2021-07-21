package server;

import com.gilecode.yagson.YaGson;
import server.model.Card;
import server.model.ServerUsers;
import server.serverController.*;

import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread {
    private final Socket client;
    private User user;

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
            Data.getInstance().updateJson();
            System.out.println("Client - exit!! ");
            ServerUsers.getOnlineUsers().remove(user);
        }
    }

    public String process(String clientMessage) {
        String[] token = clientMessage.split(" ");
        if (clientMessage.startsWith("Login")) {
            user = ServerUsers.getUserByUsername(token[1]);
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
        } else if (clientMessage.startsWith("Get-Cards")) {
            return sendCards();
        } else if (clientMessage.startsWith("Log-Out")) {
            return LoginController.getInstance().logOut(token[1]);
        } else if (clientMessage.startsWith("Change-Avatar")) {
            ServerUsers.getUserByUsername(token[1]).setAvatarURL(token[2]);
            return "Avatar Changed Successfully";
        } else if (clientMessage.startsWith("Sell-Card")) {
            return ShopController.getInstance().sell(token[1], token[2]);
        } else if (clientMessage.startsWith("Get-Stock")) {
            return sendStock();
        } else if (clientMessage.startsWith("Chat")) {
            return ChatController.getInstance().processChatCommand(clientMessage);
        } else if (clientMessage.equals("Get-Number-Of-LoggedIn")) {
            return String.valueOf(ServerUsers.getOnlineUsers().size());
        }
        return "";
    }

    private String sendUser(String username) {
        YaGson yaGson = new YaGson();
        return yaGson.toJson(ServerUsers.getUserByUsername(username));
    }

    private String sendCards() {
        YaGson yaGson = new YaGson();
        return yaGson.toJson(Card.getCards());
    }

    private String sendStock(){
        YaGson yaGson = new YaGson();
        return yaGson.toJson(Card.getShopCards());
    }
}
