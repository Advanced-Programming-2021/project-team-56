package view;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import model.enums.MenuURL;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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
        editButtons();
    }

    private void editButtons() {
        ArrayList<Button> buttons = new ArrayList<>(Arrays.asList(duelButton, deckButton, shopButton, profileButton,
                scoreboardButton, importExportButton, backButton));
        for (Button button : buttons) {
            button.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    button.setEffect(new Glow(0.6));
                    button.setLayoutY(button.getLayoutY() - 5);
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
