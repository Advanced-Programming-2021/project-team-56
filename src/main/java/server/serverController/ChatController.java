package server.serverController;

import server.model.Chat;

import java.util.ArrayList;

public class ChatController {

    private static ChatController chatController;

    private ChatController() {

    }

    public static ChatController getInstance() {
        if (chatController == null)
            chatController = new ChatController();
            return chatController;
    }

    public String processChatCommand(String clientMessage) {
        if (clientMessage.equals("Chat get-Chats")) {
            return getChats();
        } else if (clientMessage.startsWith("Chat-New-Message")) {
            String[] clientSplitMessage = clientMessage.split("\"");
            return addNewMessage(clientSplitMessage[2], clientSplitMessage[1]);
        } else if (clientMessage.equals("Chat-get-Pinned-Message")) {
            return Chat.getInstance().getPinnedMessage();
        } else if (clientMessage.startsWith("Chat-set-Pinned-Message")) {
            return setNewPinnedMessage(clientMessage);
        } else if (clientMessage.startsWith("Chat-Delete-Message")) {
            return deleteMessage(clientMessage);
        } else if (clientMessage.startsWith("Chat-Edit-Message")) {
            return editMessage(clientMessage);
        }
        return "";
    }

    private String editMessage(String clientMessage) {
        String[] splitClientMessage = clientMessage.split("\"");
        String toBeEditedChat = "Username: " + splitClientMessage[2] + " Message: \"" + splitClientMessage[1] + "\"";
        String editedChat = "Username: " + splitClientMessage[2] + " Message: \"" + splitClientMessage[3] + "\"";
        ArrayList<String> chats = Chat.getInstance().getChats();
        for (int i = 0; i < chats.size(); i++) {
            if (chats.get(i).equals(toBeEditedChat)) {
                chats.set(i, editedChat);
                break;
            }
        }
        return "";
    }

    private String deleteMessage(String clientMessage) {
        String[] splitClientMessage = clientMessage.split("\"");
        String toBeDeletedChat = "Username: " + splitClientMessage[2] + " Message: \"" + splitClientMessage[1] + "\"";
        Chat.getInstance().getChats().remove(toBeDeletedChat);
        return "";
    }

    public synchronized String addNewMessage(String senderUsername, String clientMessage) {
        ArrayList<String> chats = Chat.getInstance().getChats();
        chats.add("Username: " + senderUsername + " Message: " + "\"" + clientMessage + "\"");
        return "";
    }

    private synchronized String setNewPinnedMessage(String clientMessage) {
        String[] splitClientMessage = clientMessage.split("\"");
        Chat.getInstance().setPinnedMessage(splitClientMessage[1]);
        return "";
    }

    private String getChats() {
        ArrayList<String> chats = Chat.getInstance().getChats();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < chats.size(); i++) {
            if (i != chats.size() - 1) {
                stringBuilder.append(chats.get(i)).append("\n");
            } else {
                stringBuilder.append(chats.get(i));
            }
        }
        return stringBuilder.toString();
    }
}
