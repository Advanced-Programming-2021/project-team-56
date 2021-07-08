package model.enums;

public enum MenuURL {


    ENTRANCE("/fxml/entrance.fxml"),
    LOGIN("/fxml/login.fxml"),
    MAIN("/fxml/main.fxml"),
    MAIN_DUEL("/fxml/duel/mainDuel.fxml"),
    DECK_LIST("/fxml/deck/deckList.fxml"),
    DECK("/fxml/deck/deck.fxml"),
    SIGNUP("/fxml/signup.fxml"),
    SCOREBOARD("/fxml/scoreboard.fxml"),
    PROFILE("/fxml/profile.fxml"),
    SHOP("/fxml/shop.fxml");



    public String value;

    MenuURL(String value) {
        this.value = value;
    }
    }
