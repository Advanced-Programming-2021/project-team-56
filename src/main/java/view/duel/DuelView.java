package view.duel;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextFlow;
import model.User;
import model.enums.MenuURL;
import view.FxmlController;
import view.components.NodeEditor;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class DuelView {

    private static User firstPlayer;
    private static User secondPlayer;
    private static int numberOfRounds;

    public AnchorPane fieldAnchorPane;
    public Label opponentLPLabel;
    public Pane opponentLPBar;
    public ImageView clickedCardImageView;
    public Label myLPLabel;
    public Pane myLPBar;
    public Circle opponentMagicCircle;
    public Circle opponentAvatar;
    public Label opponentUsernameLabel;
    public Label opponentNicknameLabel;
    public Circle myMagicCircle;
    public Circle myAvatarCircle;
    public Label myUsernameLabel;
    public Label myNicknameLabel;
    public TextFlow textFlow;
    public HBox settingHBox;
    public Button backToMainMenuButton;
    public Button continueButton;
    public VBox settingVBox;

    public static void setPlayers(String firstPlayerName, String secondPlayerName) {
        firstPlayer = User.getUserByUsername(firstPlayerName);
        secondPlayer = User.getUserByUsername(secondPlayerName);
    }

    public static void setNumberOfRounds(int numberOfRounds) {
        DuelView.numberOfRounds = numberOfRounds;
    }

    @FXML
    public void initialize() {
//        root.setStyle("-fx-background-image: url(../resources/images/Duel/Field/Converted/fie_normal.png); -fx-background-size: cover");
        settingVBox.setVisible(false);
        initializePlayersInformation();
        editSettingHBox();
        //changeFieldImage(null);

    }

    private void initializePlayersInformation() {
        opponentMagicCircle.setFill(new ImagePattern(new Image("/images/Magic-Circle.png")));
        opponentAvatar.setFill(new ImagePattern(new Image(secondPlayer.getAvatarURL())));
        opponentUsernameLabel.setText(secondPlayer.getUsername());
        opponentNicknameLabel.setText(secondPlayer.getNickname());
        myMagicCircle.setFill(new ImagePattern(new Image("/images/Magic-Circle.png")));
        myAvatarCircle.setFill(new ImagePattern(new Image(firstPlayer.getAvatarURL())));
        myUsernameLabel.setText(firstPlayer.getUsername());
        myNicknameLabel.setText(firstPlayer.getNickname());
    }

    private void editSettingHBox() {
        NodeEditor.editNode(0.6, settingHBox);
        settingHBox.setOnMouseClicked(event -> {
            settingVBox.setVisible(true);
            AtomicBoolean isAnyButtonPressed = new AtomicBoolean(false);
            new Thread(() -> {
                continueButton.setOnMouseClicked(continueButtonEvent -> {
                    isAnyButtonPressed.set(true);
                    settingVBox.setVisible(false);
                });
                backToMainMenuButton.setOnMouseClicked(backButtonEvent -> {
                    isAnyButtonPressed.set(true);
                    try {
                        FxmlController.getInstance().setSceneFxml(MenuURL.MAIN);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }).start();
            while (!isAnyButtonPressed.get()) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void changeFieldImage(String fieldURL) {
        BackgroundSize backgroundSize = new BackgroundSize(1680, 1050, true, true, false, true);
        fieldAnchorPane.setBackground(new Background(new BackgroundImage(new Image("/images/Duel/Field/fie_normal.png")
                , BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize)));
    }
}
