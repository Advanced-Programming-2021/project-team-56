package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import model.enums.MenuURL;

import java.io.IOException;

public class MainView {

    public Button duelButton;
    public Button deckButton;
    public Button shopButton;
    public Button profileButton;
    public Button scoreboardButton;
    public Button importExportButton;
    public Button backButton;

    @FXML
    public void initialize() {
        MainGUI.getStage().setHeight(700);
        MainGUI.getStage().setWidth(1000);
    }

    public void goToDuelMenu(MouseEvent mouseEvent) {
    }

    public void goToDeckMenu(MouseEvent mouseEvent) {
    }

    public void goToScoreboard(MouseEvent mouseEvent) {
    }

    public void goToProfileMenu(MouseEvent mouseEvent) {
    }

    public void goToShopMenu(MouseEvent mouseEvent) {
    }

    public void goToImportExportMenu(MouseEvent mouseEvent) {
    }

    public void goBackToLoginMenu(MouseEvent mouseEvent) throws IOException {
        FxmlController.getInstance().setSceneFxml(MenuURL.LOGIN);
    }
}
