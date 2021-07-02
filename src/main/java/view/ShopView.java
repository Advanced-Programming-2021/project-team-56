package view;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
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

import java.net.URL;


public class ShopView {


    public ScrollPane cardsScrollPane;
    public GridPane cardsGridPane;
    public HBox cardsHBox;
    public Label cardNameLabel;
    public Label cardPriceLabel;
    public ImageView cardImage;
    public Label numberOfCardLabel;
    public Button buyButton;

    @FXML
    public void initialize() {
        //530b80
        cardsGridPane.setStyle("-fx-background-color: #41415D");
        setHBoxBackGround();
        addMonsterCards();
    }

    private void addMonsterCards() {
        int row = 0;
        int column = 0;
        for (int i = 0; i < 76; i++) {
            if (column == 5) {
                column = 0;
                row++;
            }
            Rectangle rectangle = new Rectangle(160,210);
            setOnMouseEnteredAndExited(rectangle);
            setOnMouseClicked(rectangle, i);
            Image image = new Image(Card.getCards().get(i).getImageURL());
            rectangle.setFill(new ImagePattern(image));
            cardsGridPane.add(rectangle, column++, row);
            GridPane.setMargin(rectangle, new Insets(30));
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
        BackgroundImage backgroundImage = new BackgroundImage(image,  BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                backgroundSize);
        cardsHBox.setBackground(new Background(backgroundImage));
    }

    private void setOnMouseEnteredAndExited(Rectangle rectangle) {
        rectangle.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                DropShadow borderGlow= new DropShadow();
                borderGlow.setColor(Color.RED);
                borderGlow.setWidth(70);
                borderGlow.setHeight(70);
                rectangle.setEffect(borderGlow);
            }
        });
        rectangle.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                rectangle.setEffect(null);
            }
        });
    }

    private void setOnMouseClicked(Rectangle rectangle, int cardIndex) {
        rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                cardNameLabel.setText(Card.getCards().get(cardIndex).getName());
                cardPriceLabel.setText(String.valueOf(Card.getCards().get(cardIndex).getPrice()));
                Image image = new Image(Card.getCards().get(cardIndex).getImageURL());
                cardImage.setImage(image);
                int numberOfCard = 0;
                for (Card card : User.getCurrentUser().getUserAllCards()) {
                    if (card.getName().equals(Card.getCards().get(cardIndex))) numberOfCard++;
                }
                numberOfCardLabel.setText(String.valueOf(numberOfCard));
            }
        });
    }
}
