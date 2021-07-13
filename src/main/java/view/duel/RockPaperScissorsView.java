package view.duel;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import model.User;
import model.enums.MenuURL;
import view.FxmlController;
import view.MainGUI;
import view.components.NodeEditor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class RockPaperScissorsView {

    public static User firstUser;
    public static User secondUser;

    public Label turnLabel;
    public VBox resultVBox;
    public Label winnerLabel;
    public Label questionLabel;
    public Button button1;
    public Button button2;
    public Button backButton;
    public HBox resultVBoxButtonsHBox;
    private RockPaperScissors rockPaperScissors1 = null;
    private RockPaperScissors rockPaperScissors2 = null;
    public ImageView rockImageView;
    public ImageView paperImageView;
    public ImageView scissorsImageView;

    enum RockPaperScissors {
        ROCK("rock"), PAPER("paper"), SCISSORS("scissors");

        public String value;

        RockPaperScissors(String value) {
            this.value = value;
        }
    }

    public static void setFirstUser(User firstUser) {
        RockPaperScissorsView.firstUser = firstUser;
    }

    public static void setSecondUser(User secondUser) {
        RockPaperScissorsView.secondUser = secondUser;
    }

    public void initialize() {
        setOnMouseEnteredAndExited(rockImageView, RockPaperScissors.ROCK);
        setOnMouseEnteredAndExited(paperImageView, RockPaperScissors.PAPER);
        setOnMouseEnteredAndExited(scissorsImageView, RockPaperScissors.SCISSORS);
        turnLabel.setText(firstUser.getUsername() + "'s Turn");
        button1.setText(firstUser.getUsername());
        button2.setText(secondUser.getUsername());
        NodeEditor.editNode(1, button1, button2);
        MainGUI.editMenuButtons(new ArrayList<Button>(Collections.singletonList(backButton)));
        resultVBox.setVisible(false);
    }

    private void setOnMouseEnteredAndExited(ImageView imageView, RockPaperScissors rockPaperScissors) {
        imageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                imageView.setImage(new Image("/images/RockPaperScissors/" + rockPaperScissors.value + "2.jpg"));
            }
        });
        imageView.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                imageView.setImage(new Image("/images/RockPaperScissors/" + rockPaperScissors.value + "1.jpg"));
            }
        });
    }

    public void rockClicked(MouseEvent mouseEvent) {
        if (rockPaperScissors1 == null) {
            rockPaperScissors1 = RockPaperScissors.ROCK;
            turnLabel.setText(secondUser.getUsername() + "'s Turn");
        } else {
            rockPaperScissors2 = RockPaperScissors.ROCK;
            decideResult();
        }
    }

    public void paperClicked(MouseEvent mouseEvent) {
        if (rockPaperScissors1 == null) {
            rockPaperScissors1 = RockPaperScissors.PAPER;
            turnLabel.setText(secondUser.getUsername() + "'s Turn");
        } else {
            rockPaperScissors2 = RockPaperScissors.PAPER;
            decideResult();
        }
    }

    public void scissorsClicked(MouseEvent mouseEvent) {
        if (rockPaperScissors1 == null) {
            rockPaperScissors1 = RockPaperScissors.SCISSORS;
            turnLabel.setText(secondUser.getUsername() + "'s Turn");
        } else {
            rockPaperScissors2 = RockPaperScissors.SCISSORS;
            decideResult();
        }
    }

    public void backClicked(MouseEvent mouseEvent) throws IOException {
        FxmlController.getInstance().setSceneFxml(MenuURL.DUEL_PREPARATION);
    }

    private void decideResult() {
        if (rockPaperScissors1 == rockPaperScissors2) {
            rockPaperScissors1 = null;
            showDrawResult();
            turnLabel.setText(firstUser.getUsername() + "'s Turn");
        } else if (rockPaperScissors1 == RockPaperScissors.ROCK && rockPaperScissors2 == RockPaperScissors.PAPER)
            showResultVBox(secondUser, firstUser);
        else if (rockPaperScissors1 == RockPaperScissors.ROCK && rockPaperScissors2 == RockPaperScissors.SCISSORS)
            showResultVBox(firstUser, secondUser);
        else if (rockPaperScissors1 == RockPaperScissors.PAPER && rockPaperScissors2 == RockPaperScissors.SCISSORS)
            showResultVBox(secondUser, firstUser);
        else if (rockPaperScissors1 == RockPaperScissors.PAPER && rockPaperScissors2 == RockPaperScissors.ROCK)
            showResultVBox(firstUser, secondUser);
        else if (rockPaperScissors1 == RockPaperScissors.SCISSORS && rockPaperScissors2 == RockPaperScissors.ROCK)
            showResultVBox(secondUser, firstUser);
        else showResultVBox(firstUser, secondUser);
    }

    private void showDrawResult() {
        turnLabel.setVisible(false);
        resultVBoxButtonsHBox.setVisible(false);
        resultVBox.setVisible(true);
        questionLabel.setText("Draw, please try again!");
        Timeline hideDrawResultTimeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            resultVBoxButtonsHBox.setVisible(true);
            turnLabel.setVisible(true);
            resultVBox.setVisible(false);
        }));
        hideDrawResultTimeline.play();
    }

    private void showResultVBox(User winner, User looser) {
        turnLabel.setVisible(false);
        resultVBox.setVisible(true);
        winnerLabel.setText(winner.getUsername() + " Won");
        questionLabel.setText(winner.getUsername() + ", Please choose the first player to go:");
    }


    public void button1Clicked(MouseEvent mouseEvent) throws IOException {
        DuelView.setPlayers(button1.getText(), button2.getText());
        DuelView.setIsBeginningOfARound(true);
        FxmlController.getInstance().setSceneFxml(MenuURL.DUEL);
    }

    public void button2Clicked(MouseEvent mouseEvent) throws IOException {
        DuelView.setPlayers(button2.getText(), button1.getText());
        DuelView.setIsBeginningOfARound(true);
        FxmlController.getInstance().setSceneFxml(MenuURL.DUEL);
    }
}
