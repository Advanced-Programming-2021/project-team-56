package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
        Parent fxml = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        Scene scene = new Scene(fxml);
        setScene(scene);
        editStage(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void editStage(Stage stage) {
        setStage(stage);
        stage.initStyle(StageStyle.UTILITY);
    }





}
