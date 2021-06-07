package view;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import controller.LoginMenuController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.User;
import model.enums.MenuURL;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class EntranceView {

    public Button signUpButton;
    public Button exitButton;
    public Button loginButton;

    public void signUpClicked(MouseEvent mouseEvent) throws IOException {
        FxmlController.getInstance().setSceneFxml(MenuURL.SIGNUP);
    }

    public void exitClicked(MouseEvent mouseEvent) {
        LoginMenuController.getInstance().updateJson();
        System.exit(0);
    }

    public void loginClicked(MouseEvent mouseEvent) throws IOException {
        FxmlController.getInstance().setSceneFxml(MenuURL.LOGIN);
    }

}
