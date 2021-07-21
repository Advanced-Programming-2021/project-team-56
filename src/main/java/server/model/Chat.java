package server.model;

import java.util.ArrayList;

public class Chat {

    private static Chat chat;

    private ArrayList<String> chats;
    private String pinnedMessage = "";

    {
        chats = new ArrayList<>();
    }

    private Chat() {

    }

    public static Chat getInstance() {
        if (chat == null)
            chat = new Chat();
        return chat;
    }

    public ArrayList<String> getChats() {
        return chats;
    }

    public String getPinnedMessage() {
        return pinnedMessage;
    }

    public void setPinnedMessage(String pinnedMessage) {
        this.pinnedMessage = pinnedMessage;
    }
}
