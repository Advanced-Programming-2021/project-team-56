package view;

import server.LoginController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.enums.MenuURL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static model.enums.MenuURL.*;

public class MainView {

    public Button duelButton;
    public Button deckButton;
    public Button shopButton;
    public Button profileButton;
    public Button scoreboardButton;
    public Button importExportButton;
    public Button backButton;
    public Button logoutButton;
    public Pane root;

    @FXML
    public void initialize() {
        editButtons();
    }

    private void editButtons() {
        ArrayList<Button> buttons = new ArrayList<>(Arrays.asList(duelButton, deckButton, shopButton, profileButton,
                scoreboardButton, importExportButton, backButton, logoutButton));
        MainGUI.editMenuButtons(buttons);

    }

    public void goToDuelMenu(MouseEvent mouseEvent) {
    }

    public void goToDeckMenu(MouseEvent mouseEvent) throws IOException {
        FxmlController.getInstance().setSceneFxml(DECK_LIST);
    }

    public void goToScoreboard(MouseEvent mouseEvent) throws IOException {
        FxmlController.getInstance().setSceneFxml(SCOREBOARD);
    }

    public void goToProfileMenu(MouseEvent mouseEvent) throws IOException {
        FxmlController.getInstance().setSceneFxml(MenuURL.PROFILE);
    }

    public void goToShopMenu(MouseEvent mouseEvent) throws IOException {
//        ShopView.currentCard = Card.getCardByName("Battle OX");
        FxmlController.getInstance().setSceneFxml(SHOP);
    }

    public void goToImportExportMenu(MouseEvent mouseEvent) {
    }

    public void goBackToLoginMenu(MouseEvent mouseEvent) throws IOException {
        FxmlController.getInstance().setSceneFxml(MenuURL.ENTRANCE);
    }

    public void logout(MouseEvent mouseEvent) throws IOException {
        LoginController.getInstance().logout();
        FxmlController.getInstance().setSceneFxml(MenuURL.LOGIN);
    }
}
