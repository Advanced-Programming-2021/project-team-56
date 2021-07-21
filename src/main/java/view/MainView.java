package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.ClientSocket;
import model.enums.MenuURL;
import model.User;

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
    public Button chatRoomButton;
    public Button backButton;
    public Button logoutButton;
    public Pane root;

    @FXML
    public void initialize() {
        editButtons();
    }

    private void editButtons() {
        ArrayList<Button> buttons = new ArrayList<>(Arrays.asList(duelButton, deckButton, shopButton, profileButton,
                scoreboardButton, chatRoomButton, backButton, logoutButton));
        MainGUI.editMenuButtons(buttons);

    }

    public void goToDuelMenu(MouseEvent mouseEvent) throws IOException {
        FxmlController.getInstance().setSceneFxml(DUEL_PREPARATION);
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
        FxmlController.getInstance().setSceneFxml(SHOP);
    }

    public void goToChatRoom(MouseEvent mouseEvent) throws IOException {
        FxmlController.getInstance().setSceneFxml(CHAT_ROOM);
    }

    public void goBackToLoginMenu(MouseEvent mouseEvent) throws IOException {
        FxmlController.getInstance().setSceneFxml(ENTRANCE);
    }

    public void logout(MouseEvent mouseEvent) throws IOException {
        try {
            ClientSocket.dataOutputStream.writeUTF("Log-Out " + User.getCurrentUser().getUsername());
            ClientSocket.dataOutputStream.flush();
            ClientSocket.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        User.setCurrentUser(null);
        FxmlController.getInstance().setSceneFxml(MenuURL.LOGIN);
    }
}
