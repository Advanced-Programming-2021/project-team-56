package model.popup;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.MainGUI;

import java.awt.*;

public interface IPopup {

    Stage popupStage = makePopupStage();
    VBox popupVBox = makePopupVBox();
    Text popupText = makePopupText();
    Button backButton = makeBackButton();
    Scene popupScene = makePopupScene();

    void setPopupText(Text popupText);

    void showPopup() throws AWTException;

    static Stage makePopupStage() {
        Stage popupStage = new Stage();
        popupStage.setResizable(false);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initOwner(MainGUI.getStage());
        popupStage.initStyle(StageStyle.UNDECORATED);
        return popupStage;
    }

    static VBox makePopupVBox() {
        VBox popupVBox = new VBox(20);
        popupVBox.setAlignment(Pos.CENTER);
        popupVBox.setPrefWidth(300);
        popupVBox.setPrefHeight(100);
        return popupVBox;
    }

    static Text makePopupText() {
        Text popupText = new Text();
        popupText.setFill(Paint.valueOf("white"));
        popupText.resize(60, 50);
        return popupText;
    }

    static Button makeBackButton() {
        Button backButton = new Button("Back");
        editButton(backButton);
        backButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                popupStage.close();
            }
        });
        return backButton;
    }

    static void editButton(Button button) {
        button.setPrefWidth(100);
        button.setPrefHeight(25);
        button.setStyle("-fx-background-color: #efb92e");
        button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                button.setEffect(new Glow(0.5));
            }
        });
        button.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                button.setEffect(null);
            }
        });
    }

    static Scene makePopupScene() {
        Scene popupScene = new Scene(popupVBox, 400, 300);
        setCursorForScene(popupScene);
        popupScene.getStylesheets().add("/CSS/sample.css");
        popupVBox.getChildren().add(popupText);
        popupVBox.getChildren().add(backButton);
        popupStage.setScene(popupScene);
        return popupScene;
    }

    static void setCursorForScene(Scene popupScene) {
//        javafx.scene.image.Image image = new Image("/Images/Pacman/Open.png");
//        popupScene.setCursor(new ImageCursor(image, image.getWidth() / 2,
//                image.getHeight() / 2));
    }

    static void moveMouseToPopup() throws AWTException {
//        new Robot().mouseMove(780, 400);
    }

}
