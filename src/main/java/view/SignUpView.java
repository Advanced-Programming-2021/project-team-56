package view;

import controller.LoginMenuController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.enums.MenuURL;

import java.io.IOException;

public class SignUpView {

    public Button signUpButton;
    public Button backButton;
    public Label errorLabel;
    public TextField userNameField;
    public TextField nickNameField;
    public TextField passWordField;

    public void backClicked(MouseEvent mouseEvent) throws IOException {
        FxmlController.getInstance().setSceneFxml(MenuURL.ENTRANCE);
    }

    public void signUpClicked(MouseEvent mouseEvent) {
        if (userNameField.getText().equals("")) {
            errorLabel.setText("username field is empty");
            return;
        }
        if (nickNameField.getText().equals("")) {
            errorLabel.setText("nickname field is empty");
            return;
        }
        if (passWordField.getText().equals("")) {
            errorLabel.setText("password field is empty");
            return;
        }
        String result = LoginMenuController.getInstance().register(userNameField.getText(), passWordField.getText(), nickNameField.getText());
        errorLabel.setText(result);
    }
}
