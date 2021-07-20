package model.enums;

public enum SoundURL {

    BACKGROUND("/sounds/mp3/Call of Duty Black Ops - Multiplayer Menu Theme"),
    BUTTON_HOVER("/sounds/mp3/button-hover.mp3"),
    BUZZER("/sounds/mp3/buzzer.wav");

    public String value;

    SoundURL(String value) {
        this.value = value;
    }
}
