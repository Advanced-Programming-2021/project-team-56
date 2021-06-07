import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class JavaFXClass extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent fxml = FXMLLoader.load(getClass().getResource("javafxTest.fxml"));
        Scene scene = new Scene(fxml);
        editStage(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();
        System.out.println("");
    }

    private void editStage(Stage stage) {
        JavaFXClass.stage = stage;
        stage.initStyle(StageStyle.UTILITY);
        stage.setAlwaysOnTop(true);
    }


}
