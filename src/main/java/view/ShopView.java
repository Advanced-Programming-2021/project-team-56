package view;

import controller.ShopController;
import controller.SoundPlayer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.Card;
import model.User;
import model.enums.MenuURL;
import model.enums.SoundURL;
import view.components.NodeEditor;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;


public class ShopView {

    public Card currentCard;
    public ScrollPane cardsScrollPane;
    public GridPane cardsGridPane;
    public HBox cardsHBox;
    public Label cardNameLabel;
    public Label cardPriceLabel;
    public ImageView cardImage;
    public Label numberOfCardLabel;
    public Button buyButton;
    public Label errorLabel;
    public Button backButton;
    public Label capitalLabel;

    @FXML
    public void initialize() {

        cardsGridPane.setStyle("-fx-background-color: #0E061E");
        cardsScrollPane.setContent(cardsGridPane);
//        numberOfCardLabel.setText(ShopController.getInstance().getNumberOfCardInUsersCards("Battle OX"));
        setHBoxBackGround();
        addCards();
        setOnMouseEnteredAndExited(buyButton);
        setOnMouseEnteredAndExited(backButton);
        capitalLabel.setText(String.valueOf(User.getCurrentUser().getMoney()));
    }

    private void addCards() {
        int row = 0;
        int column = 0;
        for (int i = 0; i < 76; i++) {
            if (column == 5) {
                column = 0;
                row++;
            }
            Rectangle rectangle = new Rectangle(160, 210);
            setOnMouseEnteredAndExited(rectangle);
            setOnMouseClicked(rectangle, i);
            Image image = new Image(Card.getCards().get(i).getImageURL());
            rectangle.setFill(new ImagePattern(image));
            cardsGridPane.add(rectangle, column++, row);
            GridPane.setMargin(rectangle, new Insets(29));
        }
    }

    private void setHBoxBackGround() {
        BackgroundSize backgroundSize = new BackgroundSize(1200,
                1050,
                true,
                true,
                false,
                true);
        Image image = new Image(getClass().getResource("/images/Black-Purple-Hexagon-Background.png").toExternalForm());
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                backgroundSize);
        cardsHBox.setBackground(new Background(backgroundImage));
    }

    private void setOnMouseEnteredAndExited(Node node) {
        node.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                SoundPlayer.getInstance().playAudioClip(SoundURL.BUTTON_HOVER);
                DropShadow borderGlow = new DropShadow();
                borderGlow.setColor(Color.RED);
                borderGlow.setWidth(70);
                borderGlow.setHeight(70);
                node.setEffect(borderGlow);
            }
        });
        node.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                node.setEffect(null);
            }
        });
    }

    private void setOnMouseClicked(Rectangle rectangle, int cardIndex) {
        rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                errorLabel.setText("");
                buyButton.setVisible(true);
                currentCard = Card.getCards().get(cardIndex);
                cardNameLabel.setText(Card.getCards().get(cardIndex).getName());
                cardPriceLabel.setText(String.valueOf(Card.getCards().get(cardIndex).getPrice()));
                Image image = new Image(Card.getCards().get(cardIndex).getImageURL());
                cardImage.setImage(image);
                numberOfCardLabel.setText(ShopController.getInstance()
                        .getNumberOfCardInUsersCards(Card.getCards().get(cardIndex).getName()));
                if (Integer.parseInt(cardPriceLabel.getText()) > Integer.parseInt(capitalLabel.getText())) {
                    buyButton.setVisible(false);
                }
            }
        });
    }

    public void buyClicked(MouseEvent mouseEvent) {
        String result = ShopController.getInstance().buyCard(currentCard.getName());
        if (result.equals("")) {
            errorLabel.setText("Buy successful");
            capitalLabel.setText(String.valueOf(User.getCurrentUser().getMoney()));
            int numberOfCard = Integer.parseInt(numberOfCardLabel.getText()) + 1;
            numberOfCardLabel.setText(String.valueOf(numberOfCard));
        }
        else errorLabel.setText(result);
    }

    public void backClicked(MouseEvent mouseEvent) throws IOException {
        FxmlController.getInstance().setSceneFxml(MenuURL.MAIN);
    }
}
