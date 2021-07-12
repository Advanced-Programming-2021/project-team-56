package view.duel;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.enums.MenuURL;
import view.FxmlController;
import view.components.NodeEditor;

import java.io.IOException;

public class DuelRoundPreparationView {

    private static String winnerUsername;
    private static String looserUsername;
    public Button leftButton;
    public Button rightButton;
    public Label label;

    public static void setWinnerUsername(String winnerUsername) {
        DuelRoundPreparationView.winnerUsername = winnerUsername;
    }

    public static void setLooserUsername(String looserUsername) {
        DuelRoundPreparationView.looserUsername = looserUsername;
    }

    @FXML
    public void initialize() {
        label.setText( looserUsername + ", Please choose the first player to go!");
        leftButton.setText(looserUsername);
        rightButton.setText(winnerUsername);
        NodeEditor.editNode(0.6, leftButton, rightButton);
        leftButton.setOnMouseClicked(event -> {
            DuelView.setNewRoundFirstPlayerUsername(looserUsername);
            try {
                FxmlController.getInstance().setSceneFxml(MenuURL.DUEL);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        rightButton.setOnMouseClicked(event -> {
            DuelView.setNewRoundFirstPlayerUsername(winnerUsername);
            try {
                FxmlController.getInstance().setSceneFxml(MenuURL.DUEL);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
