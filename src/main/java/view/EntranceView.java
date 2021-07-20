package view;

import controller.SoundPlayer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.ImageCursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import server.User;
import model.enums.MenuURL;
import model.enums.SoundURL;
import server.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class EntranceView {

    public static MediaPlayer musicPlayer;
    private static boolean isMusicPlayed = false;
    public Button mainMenuButton;
    public Button loginButton;
    public Button signUpButton;
    public Button exitButton;

    @FXML
    public void initialize() {
        editButtons();
        if (!isMusicPlayed) {
          //  playMusic();
            isMusicPlayed = true;
        }
    }

    private void editButtons() {
        ArrayList<Button> buttons = new ArrayList<>(Arrays.asList(mainMenuButton, loginButton, signUpButton, exitButton));
        MainGUI.editMenuButtons(buttons);
    }

    public void goToMainMenu(MouseEvent mouseEvent) throws IOException {
        if (User.getCurrentUser() != null) {
            FxmlController.getInstance().setSceneFxml(MenuURL.MAIN);
        } else {
            Image image = new Image("/images/Cursor/X.png");
            MainGUI.getScene().setCursor(new ImageCursor(image, image.getWidth() / 2, image.getHeight() / 2));
            Timeline changeCursorBack = new Timeline(new KeyFrame(Duration.millis(500), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Image image = new Image("/images/Cursor/Cursor1.png");
                    MainGUI.getScene().setCursor(new ImageCursor(image, image.getWidth() / 2, image.getHeight() / 2));
                }
            }));
            changeCursorBack.play();
            SoundPlayer.getInstance().playAudioClip(SoundURL.BUZZER);
        }
    }

    public void signUpClicked(MouseEvent mouseEvent) throws IOException {
        FxmlController.getInstance().setSceneFxml(MenuURL.SIGNUP);
    }

    public void exitClicked(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void loginClicked(MouseEvent mouseEvent) throws IOException {
        FxmlController.getInstance().setSceneFxml(MenuURL.LOGIN);
    }

    private void playMusic() {
        Media music = new Media(getClass().getResource("/sounds/mp3/Call of Duty Black Ops - Multiplayer Menu Theme.mp3").toExternalForm());
        musicPlayer = new MediaPlayer(music);
        musicPlayer.setAutoPlay(true);
        musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        musicPlayer.play();
    }
}
