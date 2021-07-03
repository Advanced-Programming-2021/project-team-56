package model.enums;

public enum MenuURL {


    ENTRANCE("/fxml/entrance.fxml"),
    LOGIN("/fxml/login.fxml"),
    MAIN("/fxml/main.fxml"),
    DECK("/fxml/deck/deckList.fxml"),
    SIGNUP("/fxml/signup.fxml"),
    SCOREBOARD("/fxml/scoreboard.fxml"),
    PROFILE("/fxml/profile.fxml"),
    SHOP("/fxml/shop.fxml");



    public String value;

    MenuURL(String value) {
        this.value = value;
    }
    }
