package view;

import controller.ScoreBoardController;
import controller.SoundPlayer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import model.enums.MenuURL;
import model.enums.SoundURL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static model.enums.MenuURL.MAIN;

public class ScoreBoardView {

    @FXML
    private Button backButton;
    @FXML
    private ScrollPane scoreboardScrollPane;

    @FXML
    public void initialize() {
        setScrollPaneContent();
        editButtons();
    }

    private void editButtons() {
        ArrayList<Button> buttons = new ArrayList<>(Collections.singletonList(backButton));
        MainGUI.editMenuButtons(buttons);
    }

    private void setScrollPaneContent() {
        VBox scoreboardVBox = instantiateScoreboardVBox();
        scoreboardScrollPane.setFitToWidth(true);
        scoreboardScrollPane.setContent(scoreboardVBox);
    }

    private VBox instantiateScoreboardVBox() {
        VBox scoreboardVBox = new VBox();
//        scoreboardVBox.setBlendMode(BlendMode.SRC_ATOP);
//        scoreboardVBox.setSpacing(10);
        scoreboardVBox.setPadding(new Insets(1, 2, 1, 2));
        makeScoreboard(scoreboardVBox, ScoreBoardController.getInstance().showScoreBoard());
        scoreboardVBox.setStyle("-fx-background-color: black");
        return scoreboardVBox;
    }

    private void makeScoreboard(VBox scoreboardVBox, String scoreboard) {
        HBox titleHBox = instantiateTitleHBox();
        scoreboardVBox.getChildren().add(titleHBox);
        String[] scoreboardIndividuals = scoreboard.split("\n");
        for (String scoreboardIndividual : scoreboardIndividuals) {
            Matcher matcher = Pattern.compile("^\\d+- (\\w+): \\d+$").matcher(scoreboardIndividual);
            matcher.find();
            Label nicknameLabel = instantiateNicknameLabel(matcher.group(1));
            boolean isUserNickname = ScoreBoardController.getInstance()
                    .getCurrentUserNickName().equals(matcher.group(1));

            String[] individualRank = scoreboardIndividual.split(" ");
            Label rankLabel = instantiateRankLabel(individualRank[0]);

            String[] individualScore = scoreboardIndividual.split(": ");
            Label scoreLabel = instantiateScoreLabel(individualScore[1]);

            HBox individualLabelHBox = new HBox(rankLabel, nicknameLabel, scoreLabel);
            editIndividualHBox(individualLabelHBox, isUserNickname);
            scoreboardVBox.getChildren().add(individualLabelHBox);
            scoreboardVBox.setAlignment(Pos.CENTER);
        }
    }

    private Label instantiateRankLabel(String rank) {
        Label rankLabel = new Label(rank);
        rankLabel.setPrefSize(506.66666666666666666666666666667, 100);
        rankLabel.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 40px");
//        rankLabel.setTextFill(Paint.valueOf("#551A8B"));
        rankLabel.setTextFill(Paint.valueOf("#D4B1F5"));
        rankLabel.setPadding(new Insets(0, 0, 0, 7));
        rankLabel.setAlignment(Pos.BASELINE_LEFT);
        return rankLabel;
    }

    private Label instantiateNicknameLabel(String nickname) {
        Label nicknameLabel = new Label(nickname);
        nicknameLabel.setPrefSize(506.66666666666666666666666666667, 100);
//        nicknameLabel.setTextFill(Paint.valueOf("#3C01D6"));
        nicknameLabel.setTextFill(Paint.valueOf("#8bd6ff"));
//        nicknameLabel.setTextFill(Paint.valueOf("#D4B1F5"));
        nicknameLabel.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 30px");
        nicknameLabel.setAlignment(Pos.CENTER);
        return nicknameLabel;
    }

    private Label instantiateScoreLabel(String score) {
        Label scoreLabel = new Label(score);
        scoreLabel.setPrefSize(506.66666666666666666666666666667, 100);
        scoreLabel.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 30px");
        scoreLabel.setTextFill(Paint.valueOf("#D4B1F5"));
        scoreLabel.setAlignment(Pos.CENTER);
        return scoreLabel;
    }

    private void editIndividualHBox(HBox individualHBox, boolean isUsersHBox) {
        individualHBox.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                individualHBox.setEffect(new Glow(1));
                SoundPlayer.getInstance().playAudioClip(SoundURL.BUTTON_HOVER);
            }
        });
        individualHBox.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                individualHBox.setEffect(null);
            }
        });
        individualHBox.setSpacing(100);
        individualHBox.setAlignment(Pos.CENTER);
        individualHBox.setPadding(new Insets(10,5,10, 5));
        individualHBox.setPrefSize(1520, 100);
        if (isUsersHBox) {
            individualHBox.setStyle("-fx-border-color: white; -fx-border-width: 10px");
        } else {
            individualHBox.setStyle("-fx-border-color: #9b00ff; -fx-border-width: 5px");
        }
    }

    private HBox instantiateTitleHBox() {
        Label rankTitleLabel = instantiateRankLabel("Rank");
        Label nicknameTitleLabel = instantiateNicknameLabel("Nickname");
        Label scoreTitleLabel = instantiateScoreLabel("Score");
        HBox titleHBox = new HBox(rankTitleLabel, nicknameTitleLabel, scoreTitleLabel);
        editIndividualHBox(titleHBox, false);
        return titleHBox;
    }

    @FXML
    private void goBackToMain(MouseEvent mouseEvent) throws IOException {
        FxmlController.getInstance().setSceneFxml(MAIN);
    }
}
