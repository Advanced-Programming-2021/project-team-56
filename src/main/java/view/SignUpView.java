package view;

import controller.LoginMenuController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.enums.MenuURL;

import java.io.IOException;

import static model.enums.Errors.*;

public class SignUpView {


    public TextField userNameField;
    public TextField nickNameField;
    public PasswordField passWordField;
    public Text loginLabel;
    public Label errorLabel;
    public Button signUpButton;

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
        String result = LoginMenuController.getInstance()
                .register(userNameField.getText(), passWordField.getText(), nickNameField.getText());
        errorLabel.setText(result);
    }

    public void loginClicked(MouseEvent mouseEvent) throws IOException {
        FxmlController.getInstance().setSceneFxml(MenuURL.LOGIN);
    }
}
