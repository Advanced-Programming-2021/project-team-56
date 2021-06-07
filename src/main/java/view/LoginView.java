package view;

import controller.LoginMenuController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.enums.MenuURL;

import java.io.IOException;

public class LoginView {

    public Button backButton;
    public Button loginButton;
    public TextField passWordField;
    public TextField userNameField;
    public Label errorLabel;

    public void loginClicked(MouseEvent mouseEvent) {
        if (userNameField.getText().equals("")) {
            errorLabel.setText("username field is empty");
            return;
        }
        if (passWordField.getText().equals("")) {
            errorLabel.setText("password field is empty");
            return;
        }
        String result = LoginMenuController.getInstance().logIn(userNameField.getText(), passWordField.getText());
        errorLabel.setText(result);
        if (result.equals("user logged in successfully!")) {
            //////////////////
        }
    }

    public void backClicked(MouseEvent mouseEvent) throws IOException {
        FxmlController.getInstance().setSceneFxml(MenuURL.ENTRANCE);
    }
}
