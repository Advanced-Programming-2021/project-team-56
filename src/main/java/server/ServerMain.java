package server;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
    public static void main(String[] args) throws Exception {
        try {
            ExcelUtils.getInstance().run();
            Data.getInstance().readFromJson();
            ServerSocket server = new ServerSocket(7788);
            System.out.println("Server Started Running....");
            while (true) {
                Socket client = server.accept();
                ClientThread sct = new ClientThread(client);
                sct.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
