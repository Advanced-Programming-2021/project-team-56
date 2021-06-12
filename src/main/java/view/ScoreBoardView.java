package view;

import controller.ScoreBoardController;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import model.enums.MenuURL;

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
        String scoreBoard = ScoreBoardController.getInstance().showScoreBoard();
        System.out.print(scoreBoard);
        VBox scoreboardVBox = new VBox();
        makeScoreboard(scoreboardVBox, scoreBoard);
        scoreboardScrollPane.setFitToWidth(true);
        scoreboardVBox.setStyle("-fx-background-color: black");
        scoreboardScrollPane.setContent(scoreboardVBox);
    }

    private void makeScoreboard(VBox scoreboardVBox, String scoreboard) {

//        HBox titles = new HBox();
//        scoreboardVBox.getChildren().add();



        String[] scoreboardIndividuals = scoreboard.split("\n");
        for (String scoreboardIndividual : scoreboardIndividuals) {

            String[] individualRank = scoreboardIndividual.split(" ");
            Label rankLabel = new Label(individualRank[0]);
            rankLabel.setPrefSize(506.66666666666666666666666666667, 100);
            rankLabel.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 40px");
            rankLabel.setTextFill(Paint.valueOf("#551A8B"));
            rankLabel.setPadding(new Insets(0, 0, 0, 7));
            rankLabel.setAlignment(Pos.BASELINE_LEFT);

            String[] individualScore = scoreboardIndividual.split(": ");
            Label scoreLabel = new Label(individualScore[1]);
            scoreLabel.setPrefSize(506.66666666666666666666666666667, 100);
            scoreLabel.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 30px");
            scoreLabel.setTextFill(Paint.valueOf("#D4B1F5"));
            scoreLabel.setAlignment(Pos.CENTER);

            Matcher matcher = Pattern.compile("^\\d+- (\\w+): \\d+$").matcher(scoreboardIndividual);
            matcher.find();
            Label nicknameLabel = new Label(matcher.group(1));
            nicknameLabel.setPrefSize(506.66666666666666666666666666667, 100);
            nicknameLabel.setTextFill(Paint.valueOf("#3C01D6"));
            nicknameLabel.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 30px");
            nicknameLabel.setAlignment(Pos.CENTER);


            HBox individualLabelHBox = new HBox(rankLabel, nicknameLabel, scoreLabel);
            individualLabelHBox.setSpacing(100);
            individualLabelHBox.setAlignment(Pos.CENTER);
            individualLabelHBox.setStyle("-fx-border-color: #9234be; -fx-border-width: 5px; -fx-border-radius: 5");
            individualLabelHBox.setPadding(new Insets(5,0,0, 0));
            individualLabelHBox.setPrefSize(1520, 100);
            scoreboardVBox.getChildren().add(individualLabelHBox);

            scoreboardVBox.setAlignment(Pos.CENTER);
        }
    }

    @FXML
    private void goBackToMain(MouseEvent mouseEvent) throws IOException {
        FxmlController.getInstance().setSceneFxml(MAIN);
    }
}
