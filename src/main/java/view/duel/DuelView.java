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
import view.components.NodeEditor;

import java.io.IOException;

public class DuelView extends Application {

    public TextField opponentUserName;
    public ComboBox numberOfRoundsComboBox;
    public Button startButton;
    public Button backButton;
    public Label errorLabel;

    public void initialize() {
        ObservableList<Integer> items = FXCollections.observableArrayList(1, 3);
        numberOfRoundsComboBox.setItems(items);
        NodeEditor.editNode(1, startButton, backButton);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane pane = FXMLLoader.load(getClass().getResource("/fxml/duel.fxml"));
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void backClicked(MouseEvent mouseEvent) throws IOException {
        FxmlController.getInstance().setSceneFxml(MenuURL.MAIN);
    }

    public void startClicked(MouseEvent mouseEvent) {
        if (opponentUserName.getText().equals("")) {
            errorLabel.setText("opponent's username field is empty");
        } else if (numberOfRoundsComboBox.getValue() == null) {
            errorLabel.setText("please set number of rounds");
        } else {
            String result = DuelMenuController.getInstance().canUsersDuel(User.getCurrentUser().getUsername(), opponentUserName.getText());
            errorLabel.setText(result);
         //   if (result.equals("duel is valid")) ///////////////
        }
    }
}
