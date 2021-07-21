package server.model;

import server.User;

import java.util.ArrayList;

public class ServerUsers {
    private final static ArrayList<User> users = new ArrayList<>();
    private final static ArrayList<User> onlineUsers = new ArrayList<>();

    public static synchronized ArrayList<User> getUsers() {
        return users;
    }

    public static ArrayList<User> getOnlineUsers() {
        return onlineUsers;
    }

    public static boolean isThisUsernameAlreadyTaken(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username))
                return true;
        }
        return false;
    }

    public static synchronized User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    public static boolean isThisNicknameAlreadyTaken(String nickname) {
        for (User user : users) {
            if (user.getNickname().equals(nickname))
                return true;
        }
        return false;
    }
}
