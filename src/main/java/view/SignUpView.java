package view;

import controller.LoginMenuController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.enums.MenuURL;
import view.components.NodeEditor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static model.enums.Errors.*;

public class SignUpView {


    public TextField userNameField;
    public TextField nickNameField;
    public PasswordField passWordField;
    public Label loginLabel;
    public Label errorLabel;
    public Button signUpButton;
    public Button backButton;

    @FXML
    public void initialize() {
        ArrayList<Button> buttons = new ArrayList<>(Arrays.asList(backButton));
        MainGUI.editMenuButtons(buttons);
        NodeEditor.setNodesGlow(loginLabel, signUpButton, userNameField, nickNameField, passWordField);
    }



    public void backClicked(MouseEvent mouseEvent) throws IOException {
        FxmlController.getInstance().setSceneFxml(MenuURL.ENTRANCE);
    }

    public void signUpClicked(MouseEvent mouseEvent) {
        if (userNameField.getText().equals("")) {
            errorLabel.setText(EMPTY_FIELD_USERNAME.value);
            return;
        }
        if (nickNameField.getText().equals("")) {
            errorLabel.setText(EMPTY_FIELD_NICKNAME.value);
            return;
        }
        if (passWordField.getText().equals("")) {
            errorLabel.setText(EMPTY_FIELD_PASSWORD.value);
            return;
        }
        errorLabel.setText(LoginMenuController.getInstance()
                .register(userNameField.getText(), passWordField.getText(), nickNameField.getText()));
    }

    public void loginClicked(MouseEvent mouseEvent) throws IOException {
        FxmlController.getInstance().setSceneFxml(MenuURL.LOGIN);
    }
}
