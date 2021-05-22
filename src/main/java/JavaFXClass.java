import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFXClass extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent fxml = FXMLLoader.load(getClass().getResource("javafxTest.fxml"));
        new Scene(fxml);
        primaryStage.setScene(fxml.getScene());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
