package view;

import controller.LoginMenuController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import model.enums.MenuURL;

import java.io.IOException;

import static model.enums.Errors.*;
import static model.enums.ProcessResult.LOGIN_SUCCESSFUL;

public class LoginView {


    public TextField userNameField;
    public PasswordField passWordField;
    public Button loginButton;
    public Text signUpLabel;
    public Label errorLabel;
    public Button backButton;

    public void loginClicked(MouseEvent mouseEvent) throws IOException {
        if (userNameField.getText().equals("")) {
            errorLabel.setText(EMPTY_FIELD_USERNAME.value);
            return;
        }
        if (passWordField.getText().equals("")) {
            errorLabel.setText(EMPTY_FIELD_PASSWORD.value);
            return;
        }
        String result = LoginMenuController.getInstance().logIn(userNameField.getText(), passWordField.getText());
        errorLabel.setText(result);
        if (result.equals(LOGIN_SUCCESSFUL.value)) {
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
