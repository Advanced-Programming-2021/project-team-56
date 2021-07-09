package view;

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
import static model.enums.ProcessResult.LOGIN_SUCCESSFUL;

public class LoginView {

    public TextField userNameField;
    public PasswordField passWordField;
    public Button loginButton;
    public Label errorLabel;
    public Button backButton;
    public Label signupLabel;

    @FXML
    public void initialize() {
        ArrayList<Button> buttons = new ArrayList<>(Arrays.asList(backButton));
        MainGUI.editMenuButtons(buttons);
        NodeEditor.editNode(0.8, userNameField, passWordField, loginButton, signupLabel);
    }

    public void loginClicked(MouseEvent mouseEvent) throws IOException {
        if (userNameField.getText().equals("")) {
            errorLabel.setText(EMPTY_FIELD_USERNAME.value);
            return;
        }
        if (passWordField.getText().equals("")) {
            errorLabel.setText(EMPTY_FIELD_PASSWORD.value);
            return;
        }
        ClientSocket.dataOutputStream.writeUTF("Login " + userNameField.getText() +
                " " + passWordField.getText());
        ClientSocket.dataOutputStream.flush();
        String serverResponse = ClientSocket.dataInputStream.readUTF();
        errorLabel.setText(serverResponse);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (serverResponse.equals(LOGIN_SUCCESSFUL.value)) {
            FxmlController.getInstance().setSceneFxml(MenuURL.MAIN);
        }
    }

    public void backClicked(MouseEvent mouseEvent) throws IOException {
        FxmlController.getInstance().setSceneFxml(MenuURL.ENTRANCE);
    }

    public void signUpClicked(MouseEvent mouseEvent) throws IOException {
        FxmlController.getInstance().setSceneFxml(MenuURL.SIGNUP);
    }
}
