package view;

import model.ClientSocket;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import model.User;
import model.enums.AvatarURL;
import model.enums.MenuURL;
import view.components.NodeEditor;

import java.io.IOException;

public class ProfileView {
    public static TextField currentPassWordField;
    public ImageView avatarChangeArrow;
    public ImageView userAvatar;
    public Label userNameLabel;
    public Label actionLabel;
    public Label currentInfoTitle;
    public Label currentInfoValue;
    public Label textFieldTitle;
    public TextField newInfoField;
    public Button changeInfoButton;
    public Label errorLabel;
    public Pane currentInfoPane;
    public GridPane avatarImagesGridPane;

    @FXML
    public void initialize() {
        userNameLabel.setText(User.getCurrentUser().getUsername());
        userAvatar.setImage(new Image(User.getCurrentUser().getAvatarURL()));
        currentInfoValue.setText(User.getCurrentUser().getNickname());
        editAvatarsGridPane();
        editAvatarChangeArrow();
    }

    private void editAvatarChangeArrow() {
        NodeEditor.editNode(1, avatarChangeArrow);
        avatarChangeArrow.setOnMouseClicked(event -> {
            avatarImagesGridPane.setVisible(!avatarImagesGridPane.isVisible());
        });
    }

    private void editAvatarsGridPane() {
        avatarImagesGridPane.setVisible(false);
        avatarImagesGridPane.getStylesheets().add("/CSS/profile.css");
        avatarImagesGridPane.setOnMouseExited(event -> {
            avatarImagesGridPane.setVisible(false);
        });
        AvatarURL[] avatarURLS = AvatarURL.class.getEnumConstants();
        int maxRowImagesNumber = (int) Math.ceil((double) avatarURLS.length / 2);
        avatarImagesGridPane.setPrefWidth(205 * maxRowImagesNumber + 45);
        for (int i = 0, k = 0; i < 2; i++, k += maxRowImagesNumber) {
            for (int j = 0; j < maxRowImagesNumber; j++) {
                if (j + k == avatarURLS.length) break;
                Circle imageCircle = new Circle(100);
                imageCircle.setFill(new ImagePattern(new Image(avatarURLS[j + k].value)));
                setChangeAvatarOnClicked(imageCircle, avatarURLS[j + k].value);
                Circle backgroundCircle = new Circle(100);
                backgroundCircle.setFill(Paint.valueOf("#500274"));
                NodeEditor.editNode(0.1, imageCircle);
                avatarImagesGridPane.add(backgroundCircle, j, i);
                avatarImagesGridPane.add(imageCircle, j, i);
            }
        }
    }

    private void setChangeAvatarOnClicked(Circle imageCircle, String avatarURL) {
        imageCircle.setOnMouseClicked(event -> {
            User.getCurrentUser().setAvatarURL(avatarURL);
            userAvatar.setImage(new Image(avatarURL));
            try {
                ClientSocket.dataOutputStream.writeUTF("Change-Avatar " + User.getCurrentUser().getUsername() +
                        " " + avatarURL);
                ClientSocket.dataOutputStream.flush();
                String serverResponse = ClientSocket.dataInputStream.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void loadChangePasswordMenu(MouseEvent mouseEvent) {
        actionLabel.setText("Change Password:");
        currentInfoTitle.setText("Current Password:");
        currentInfoValue.setText(User.getCurrentUser().getPassword());
        textFieldTitle.setText("New Password");
        changeInfoButton.setText("Change Password");
        currentInfoPane.getChildren().remove(currentInfoValue);
        TextField currentPasswordField = new TextField();
        currentPasswordField.setPrefWidth(362);
        currentPasswordField.setPrefHeight(21);
        String cssAddress = getClass().getResource("/CSS/profile.css").toExternalForm();
        currentPasswordField.getStylesheets().add(cssAddress);
        currentInfoPane.getChildren().add(currentPasswordField);
        currentPasswordField.setLayoutX(currentPasswordField.getLayoutX() + 266);
        errorLabel.setText("");
        ProfileView.currentPassWordField = currentPasswordField;
    }

    public void loadChangeNickNameMenu(MouseEvent mouseEvent) throws IOException {
        FxmlController.getInstance().setSceneFxml(MenuURL.PROFILE);
        userNameLabel.setText(User.getCurrentUser().getUsername());
        currentInfoValue.setText(User.getCurrentUser().getNickname());
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
            errorLabel.setText("Nickname's field is empty");
            return;
        }
        try {
            ClientSocket.dataOutputStream.writeUTF("Change-Nickname " + User.getCurrentUser().getUsername() +
                    " " + newInfoField.getText());
            ClientSocket.dataOutputStream.flush();
            String serverResponse = ClientSocket.dataInputStream.readUTF();
            errorLabel.setText(serverResponse);
            if (serverResponse.equals("nickname changed successfully")){
                User.getCurrentUser().setNickname(newInfoField.getText());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void changePassWord() {
        if (newInfoField.getText().equals("")) {
            errorLabel.setText("Password's field is empty");
            return;
        }
        if (currentPassWordField.getText().equals("")) {
            errorLabel.setText("current password's field is empty");
            return;
        }
        try {
            ClientSocket.dataOutputStream.writeUTF("Change-Password " + currentPassWordField.getText() +
                    " " + newInfoField.getText() + " " + User.getCurrentUser().getUsername());
            ClientSocket.dataOutputStream.flush();
            String serverResponse = ClientSocket.dataInputStream.readUTF();
            errorLabel.setText(serverResponse);
            if (serverResponse.equals("password changed successfully")){
                User.getCurrentUser().setPassword(newInfoField.getText());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
