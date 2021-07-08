package view.duel;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;
import view.components.NodeEditor;

public class RockPaperScissorsView {

    public static User user1;
    public static User user2;
    public Label turnLabel;
    public VBox resultVBox;
    public Label winnerLabel;
    public Label questionLabel;
    public Button button1;
    public Button button2;
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

    public void initialize() {
        setOnMouseEnteredAndExited(rockImageView, RockPaperScissors.ROCK);
        setOnMouseEnteredAndExited(paperImageView, RockPaperScissors.PAPER);
        setOnMouseEnteredAndExited(scissorsImageView, RockPaperScissors.SCISSORS);
        turnLabel.setText(user1.getUsername() + "'s Turn");
        button1.setText(user1.getUsername());
        button2.setText(user2.getUsername());
        NodeEditor.editNode(1, button1, button2);
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
            turnLabel.setText(user2.getUsername() + "'s Turn");
        } else {
            rockPaperScissors2 = RockPaperScissors.ROCK;
            decideResult();
        }
    }

    public void paperClicked(MouseEvent mouseEvent) {
        if (rockPaperScissors1 == null) {
            rockPaperScissors1 = RockPaperScissors.PAPER;
            turnLabel.setText(user2.getUsername() + "'s Turn");
        } else {
            rockPaperScissors2 = RockPaperScissors.PAPER;
            decideResult();
        }
    }

    public void scissorsClicked(MouseEvent mouseEvent) {
        if (rockPaperScissors1 == null) {
            rockPaperScissors1 = RockPaperScissors.SCISSORS;
            turnLabel.setText(user2.getUsername() + "'s Turn");
        } else {
            rockPaperScissors2 = RockPaperScissors.SCISSORS;
            decideResult();
        }
    }

    private void decideResult() {
        if (rockPaperScissors1 == rockPaperScissors2) {
            rockPaperScissors1 = null;
            turnLabel.setText(user1.getUsername());
        } else if (rockPaperScissors1 == RockPaperScissors.ROCK && rockPaperScissors2 == RockPaperScissors.PAPER)
            showResultVBox(user2, user1);
        else if (rockPaperScissors1 == RockPaperScissors.ROCK && rockPaperScissors2 == RockPaperScissors.SCISSORS)
            showResultVBox(user1, user2);
        else if (rockPaperScissors1 == RockPaperScissors.PAPER && rockPaperScissors2 == RockPaperScissors.SCISSORS)
            showResultVBox(user2, user1);
        else if (rockPaperScissors1 == RockPaperScissors.PAPER && rockPaperScissors2 == RockPaperScissors.ROCK)
            showResultVBox(user1, user2);
        else if (rockPaperScissors1 == RockPaperScissors.SCISSORS && rockPaperScissors2 == RockPaperScissors.ROCK)
            showResultVBox(user2, user1);
        else showResultVBox(user1, user2);
    }

    private void showResultVBox(User winner, User looser) {
        resultVBox.setVisible(true);
        winnerLabel.setText(winner.getUsername() + " Won");
        questionLabel.setText(winner.getUsername() + ", Please choose the first player to go:");
    }


    public void button1Clicked(MouseEvent mouseEvent) {
        //todo
    }

    public void button2Clicked(MouseEvent mouseEvent) {
        //todo
    }
}
