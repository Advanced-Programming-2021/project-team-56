package server;

import model.enums.AvatarURL;

import java.util.ArrayList;
import java.util.Random;

public class ServerUsers {
    private final static ArrayList<User> users = new ArrayList<>();

    public static ArrayList<User> getUsers() {
        return users;
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

    public void setUsersRandomAvatarURL() {
        AvatarURL[] avatarURLs = AvatarURL.class.getEnumConstants();
        int avatarURLsLength = AvatarURL.class.getEnumConstants().length;
        int randomNumber = new Random().nextInt(avatarURLsLength);
//        avatarURLs[randomNumber].value;
    }
}
