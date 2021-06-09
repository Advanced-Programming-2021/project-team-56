package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.components.SceneSizeChangeListener;

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

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent fxml = FXMLLoader.load(getClass().getResource("/fxml/entrance.fxml"));
        Scene scene = new Scene(fxml);
        setScene(scene);
        editStage(primaryStage);
        primaryStage.setScene(scene);
//        primaryStage.setFullScreen(true);
//        letterbox(scene, (Pane) fxml);
        primaryStage.setMaximized(true);
//        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();

    }

    private void editStage(Stage stage) {
        setStage(stage);
        stage.initStyle(StageStyle.UTILITY);
    }

    private void letterbox(final Scene scene, final Pane contentPane) {
        final double initWidth  = scene.getWidth();
        final double initHeight = scene.getHeight();
        final double ratio = initWidth / initHeight;

        SceneSizeChangeListener sizeListener = new SceneSizeChangeListener(scene, ratio, initHeight, initWidth, contentPane);
        scene.widthProperty().addListener(sizeListener);
        scene.heightProperty().addListener(sizeListener);
    }


}
