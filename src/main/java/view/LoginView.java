package view;

import com.gilecode.yagson.YaGson;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import model.ClientSocket;
import model.enums.MenuURL;
import model.User;
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
        if (serverResponse.equals("User logged in successfully!")) {
            try {
                User.setCurrentUser((User) getUserFromServer(userNameField.getText()));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        errorLabel.setText(serverResponse);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (serverResponse.equals(LOGIN_SUCCESSFUL.value)) {
            Timeline goToMainMenuTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                try {
                    FxmlController.getInstance().setSceneFxml(MenuURL.MAIN);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));
            goToMainMenuTimeline.play();
        }
    }

    private Object getUserFromServer(String username) throws IOException, ClassNotFoundException {
        ClientSocket.dataOutputStream.writeUTF("Get-User " + username);
        ClientSocket.dataOutputStream.flush();
        YaGson yaGson = new YaGson();
        return yaGson.fromJson(ClientSocket.dataInputStream.readUTF(), User.class);
    }

    public void backClicked(MouseEvent mouseEvent) throws IOException {
        FxmlController.getInstance().setSceneFxml(MenuURL.ENTRANCE);
    }

    public void signUpClicked(MouseEvent mouseEvent) throws IOException {
        FxmlController.getInstance().setSceneFxml(MenuURL.SIGNUP);
    }
}
