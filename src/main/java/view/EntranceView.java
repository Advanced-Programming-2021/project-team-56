package view;

import controller.LoginMenuController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import model.enums.MenuURL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class EntranceView {

    public Button loginButton;
    public Button signUpButton;
    public Button exitButton;

    @FXML
    public void initialize() {
        editButtons();
    }

    private void editButtons() {
        ArrayList<Button> buttons = new ArrayList<>(Arrays.asList(loginButton, signUpButton, exitButton));
        MainGUI.editMenuButtons(buttons);
    }

    public void signUpClicked(MouseEvent mouseEvent) throws IOException {
        FxmlController.getInstance().setSceneFxml(MenuURL.SIGNUP);
    }

    public void exitClicked(MouseEvent mouseEvent) {
        LoginMenuController.getInstance().updateJson();
        System.exit(0);
    }

    public void loginClicked(MouseEvent mouseEvent) throws IOException {
        FxmlController.getInstance().setSceneFxml(MenuURL.LOGIN);
    }

}
