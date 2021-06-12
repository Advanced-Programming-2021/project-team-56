package model.enums;

public enum MenuURL {


    ENTRANCE("/fxml/entrance.fxml"),
    LOGIN("/fxml/login.fxml"),
    MAIN("/fxml/main.fxml"),
    SIGNUP("/fxml/signup.fxml"),
    SCOREBOARD("/fxml/scoreboard.fxml"),
    PROFILE("/fxml/profile.fxml");



    public String value;

    MenuURL(String value) {
        this.value = value;
    }
    }
