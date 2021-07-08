package view;

import controller.SoundPlayer;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.enums.SoundURL;

import java.awt.*;
import java.util.ArrayList;

public class MainGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private static Stage stage;
    private static Scene scene;

    public static Scene getScene() {
        return scene;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setScene(Scene scene) {
        MainGUI.scene = scene;
    }

    public static void setStage(Stage stage) {
        MainGUI.stage = stage;
    }

    public static void editMenuButtons(ArrayList<Button> buttons) {
        for (Button button : buttons) {
            TranslateTransition buttonsTransition = new TranslateTransition(Duration.seconds(0.8), button);
            buttonsTransition.setFromX(button.getLayoutX() - 400);
            buttonsTransition.setToX(button.getLayoutX());
            buttonsTransition.play();
            button.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    button.setEffect(new Glow(0.6));
                    button.setLayoutY(button.getLayoutY() - 5);
                    SoundPlayer.getInstance().playAudioClip(SoundURL.BUTTON_HOVER);
                    try {
                        new Robot().mouseMove((int) MouseInfo.getPointerInfo().getLocation().getX(),
                                (int) MouseInfo.getPointerInfo().getLocation().getY() - 2);
                    } catch (AWTException e) {
                        e.printStackTrace();
                    }
                }
            });
            button.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    button.setEffect(null);
                    button.setLayoutY(button.getLayoutY() + 5);
                }
            });

        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent fxml = FXMLLoader.load(getClass().getResource("/fxml/entrance.fxml"));
        Scene scene = instantiateScene(fxml);
        setScene(scene);
        editStage(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private Scene instantiateScene(Parent fxml) {
        Image image = new Image("/images/Cursor/Cursor1.png");
        Scene scene = new Scene(fxml);
        scene.setCursor(new ImageCursor(image,  image.getWidth() / 2,
                image.getHeight() / 2));
        return scene;
    }

    private void editStage(Stage stage) {
        setStage(stage);
        stage.setFullScreen(true);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setFullScreenExitHint(null);
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
    }


}
