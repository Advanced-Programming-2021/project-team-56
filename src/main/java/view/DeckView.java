package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class DeckView {

    private static String deckName;
    public ImageView firstImage;
    public ImageView secondImage;
    public AnchorPane anchorPane;
    public AnchorPane anchorPane1;
    public AnchorPane anchorPane11;

    public static void setDeckName(String deckName) {
        DeckView.deckName = deckName;
    }

    @FXML
    public void initialize() {
        fillAnchorPane(anchorPane);
        fillAnchorPane(anchorPane1);
        fillAnchorPane(anchorPane11);
    }

    public void fillAnchorPane(AnchorPane anchorPane) {
        for (int i = 0; i < 20; i++) {
            ImageView imageView = new ImageView("/images/Back-card.jpg");
            imageView.setFitWidth(70);
            imageView.setFitHeight(110);
            anchorPane.getChildren().add(imageView);
            imageView.setLayoutY(imageView.getLayoutY() + 25);
            imageView.setLayoutX(imageView.getLayoutX() + 25);
            if (i != 0) {
                imageView.setLayoutX(anchorPane.getChildren().get(i -1).getLayoutX() + 35);
            }
            imageView.setOnMouseClicked(event -> {
                imageView.toFront();
            });
            imageView.setOnMouseEntered(event -> {
                imageView.setEffect(new Glow(1));
            });
            int finalI = i;
            imageView.setOnMouseExited(event -> {
                imageView.setEffect(null);
                if (!imageView.equals(anchorPane.getChildren().get(finalI))) {
                    for (int k = 0; k < 19 - finalI; k++) {
                        ImageView frontImages = (ImageView) anchorPane.getChildren().get(finalI);
                        frontImages.toFront();
                    }
                }
            });
        }
    }
}
