package view.duel;

import controller.DuelMenuController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.User;
import model.enums.MenuURL;
import view.FxmlController;
import view.MainGUI;
import view.components.NodeEditor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class DuelPreparationView {

    public TextField opponentUserName;
    public ComboBox numberOfRoundsComboBox;
    public Button startButton;
    public Button backButton;
    public Label errorLabel;

    public void initialize() {
        ObservableList<Integer> items = FXCollections.observableArrayList(1, 3);
        numberOfRoundsComboBox.setItems(items);
        NodeEditor.editNode(1, startButton);
        MainGUI.editMenuButtons(new ArrayList<Button>(Collections.singleton(backButton)));
    }

    public void backClicked(MouseEvent mouseEvent) throws IOException {
        FxmlController.getInstance().setSceneFxml(MenuURL.MAIN);
    }

    public void startClicked(MouseEvent mouseEvent) throws IOException {
        if (opponentUserName.getText().equals("")) {
            errorLabel.setText("Opponent's username field is empty");
        } else if (numberOfRoundsComboBox.getValue() == null) {
            errorLabel.setText("Please set number of rounds");
        } else if (opponentUserName.getText().equals(User.getCurrentUser().getUsername())) {
            errorLabel.setText("Please enter another player's username");
        } else {
            String result = DuelMenuController.getInstance().canUsersDuel(User.getCurrentUser().getUsername(), opponentUserName.getText());
            DuelView.setNumberOfRounds((int) numberOfRoundsComboBox.getValue());
            errorLabel.setText(result);
            if (result.equals("Duel is valid")) {
                RockPaperScissorsView.user1 = User.getCurrentUser();
                RockPaperScissorsView.user2 = User.getUserByUsername(opponentUserName.getText());
                FxmlController.getInstance().setSceneFxml(MenuURL.ROCK_PAPER_SCISSORS);
            }
        }
    }
}
