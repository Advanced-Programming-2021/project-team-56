package view.duel;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import view.MainGUI;

public class MainDuelView {

    public AnchorPane root;
    public AnchorPane fieldAnchorPane;

    @FXML
    public void initialize() {
//        root.setStyle("-fx-background-image: url(../resources/images/Duel/Field/Converted/fie_normal.png); -fx-background-size: cover");
        changeFieldImage(null);

    }

    private void changeFieldImage(String fieldURL) {
        BackgroundSize backgroundSize = new BackgroundSize(1680, 1050, true, true, false, true);
        fieldAnchorPane.setBackground(new Background(new BackgroundImage(new Image("/images/Duel/Field/fie_normal.png")
                , BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize)));
    }
}
