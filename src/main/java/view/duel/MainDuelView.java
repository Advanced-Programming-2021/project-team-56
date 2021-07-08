package view.duel;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextFlow;
import model.User;
import view.MainGUI;

public class MainDuelView {

    private static User firstPlayer;
    private static User secondPlayer;

    public AnchorPane root;
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

    @FXML
    public void initialize() {
//        root.setStyle("-fx-background-image: url(../resources/images/Duel/Field/Converted/fie_normal.png); -fx-background-size: cover");
        initializePlayersInformation();
        changeFieldImage(null);

    }

    private void initializePlayersInformation() {
        //TODO gooz az user Begir!
        opponentMagicCircle.setFill(new ImagePattern(new Image("/images/Magic-Circle.png")));
        opponentAvatar.setFill(new ImagePattern(new Image("/images/Avatars/Maximillion-Pegasus.png")));
        opponentUsernameLabel.setText("Sample");
        opponentNicknameLabel.setText("Aliii!");

        myMagicCircle.setFill(new ImagePattern(new Image("/images/Magic-Circle.png")));
        myAvatarCircle.setFill(new ImagePattern(new Image("/images/Avatars/Alexis-Rhode.png")));
        myUsernameLabel.setText("Sample");
        myNicknameLabel.setText("Aliii!");
    }

    private void changeFieldImage(String fieldURL) {
        BackgroundSize backgroundSize = new BackgroundSize(1680, 1050, true, true, false, true);
        fieldAnchorPane.setBackground(new Background(new BackgroundImage(new Image("/images/Duel/Field/fie_normal.png")
                , BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize)));
    }
}
