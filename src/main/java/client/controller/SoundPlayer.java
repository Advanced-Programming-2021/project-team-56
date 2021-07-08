package controller;

import javafx.scene.media.AudioClip;
import model.enums.SoundURL;

public class SoundPlayer {

    private static SoundPlayer soundPlayer;

    private SoundPlayer() {
    }

    public static SoundPlayer getInstance() {
        if (soundPlayer == null)
            soundPlayer = new SoundPlayer();
        return soundPlayer;
    }

    public void playAudioClip(SoundURL soundURL) {
       AudioClip audio = new AudioClip(SoundPlayer.class.getResource(soundURL.value).toExternalForm());
       new Thread(audio::play).start();
    }
}
