package model.enums;

public enum MenuURL {


    ENTRANCE("/fxml/entrance.fxml"),
    LOGIN("/fxml/login.fxml"),
    MAIN("/fxml/main.fxml"),
    DUEL("/fxml/duel/duel.fxml"),
    DECK_LIST("/fxml/deck/deckList.fxml"),
    DECK("/fxml/deck/deck.fxml"),
    SIGNUP("/fxml/signup.fxml"),
    SCOREBOARD("/fxml/scoreboard.fxml"),
    PROFILE("/fxml/profile.fxml"),
    SHOP("/fxml/shop.fxml"),
    DUEL_PREPARATION("/fxml/duel/duelPreparation.fxml"),
    ROCK_PAPER_SCISSORS("/fxml/rockPaperScissors.fxml");



    public String value;

    MenuURL(String value) {
        this.value = value;
    }
    }
