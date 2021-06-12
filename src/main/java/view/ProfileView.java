package view;

import controller.ProfileController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import model.User;
import model.enums.MenuURL;
import view.FxmlController;

import java.io.IOException;

public class ProfileView {
    public ImageView userAvatar;
    public Label userNameLabel;
    public Label actionLabel;
    public Label currentInfoTitle;
    public Label currentInfoValue;
    public Label textFieldTitle;
    public TextField newInfoField;
    public Button changeInfoButton;
    public Label errorLabel;

    @FXML
    public void initialize() {
        userNameLabel.setText(User.getCurrentUser().getUsername());
        //todo : set avatar image
        currentInfoValue.setText(User.getCurrentUser().getNickname());
    }


    public void loadChangePasswordMenu(MouseEvent mouseEvent) {
        actionLabel.setText("Change Password:");
        currentInfoTitle.setText("current password:");
        currentInfoValue.setText(User.getCurrentUser().getPassword());
        textFieldTitle.setText("new password");
        changeInfoButton.setText("change password");
        errorLabel.setText("");
    }

    public void loadChangeNickNameMenu(MouseEvent mouseEvent) {
        actionLabel.setText("Change Nickname:");
        currentInfoTitle.setText("current nickname:");
        currentInfoValue.setText(User.getCurrentUser().getNickname());
        textFieldTitle.setText("new nickname");
        changeInfoButton.setText("change nickname");
        errorLabel.setText("");
    }

    public void backClicked(MouseEvent mouseEvent) throws IOException {
        FxmlController.getInstance().setSceneFxml(MenuURL.MAIN);
    }

    public void changeInfoClicked(MouseEvent mouseEvent) {
        if (changeInfoButton.getText().equals("change nickname")) changeNickName();
        else changePassWord();
    }

    private void changeNickName() {
        if (newInfoField.getText().equals("")) {
            errorLabel.setText("nickname's field is empty");
            return;
        }
        errorLabel.setText(ProfileController.getInstance().changeNickname(User.getCurrentUser().getUsername(), newInfoField.getText()));
    }

    private void changePassWord() {
        if (newInfoField.getText().equals("")) {
            errorLabel.setText("nickname's field is empty");
            return;
        }
        errorLabel.setText(ProfileController.getInstance().changePasswords(newInfoField.getText(), User.getCurrentUser().getUsername()));
    }
}
