package server;

import java.util.ArrayList;

public class ServerUser {
    private final static ArrayList<ServerUser> users = new ArrayList<>();
    private String username;
    private String password;
    private String nickname;
    private int score = 0;

    public ServerUser(String nickname, String password, String username){
        setUsername(username);
        setNickname(nickname);
        setPassword(password);
    }

    public static ArrayList<ServerUser> getUsers() {
        return users;
    }

    public static boolean isThisUsernameAlreadyTaken(String username) {
        for (ServerUser user : users) {
            if (user.getUsername().equals(username))
                return true;
        }
        return false;
    }

    public static synchronized ServerUser getUserByUsername(String username) {
        for (ServerUser user : users) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    public static boolean isThisNicknameAlreadyTaken(String nickname) {
        for (ServerUser user : users) {
            if (user.getNickname().equals(nickname))
                return true;
        }
        return false;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void increaseScore(int matchPoint) {
        this.score += matchPoint;
    }

    public int getScore() {
        return this.score;
    }
}
