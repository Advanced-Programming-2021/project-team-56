package server.model.enums;

public enum SoundURL {

    BUTTON_HOVER("/sounds/mp3/button-hover.mp3"),
    BUZZER("/sounds/mp3/buzzer.wav");

    public String value;

    SoundURL(String value) {
        this.value = value;
    }
}
