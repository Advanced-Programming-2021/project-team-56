package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import model.enums.MenuURL;

import java.io.IOException;

public class FxmlController {

    private static FxmlController fxmlController;

    private FxmlController() {

    }

    public static FxmlController getInstance() {
        if (fxmlController == null)
            fxmlController = new FxmlController();
        return fxmlController;
    }

    public void setSceneFxml(MenuURL menuURL) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(menuURL.value));
        MainGUI.getScene().setRoot(root);
    }
}
