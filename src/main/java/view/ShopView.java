package view;

import com.gilecode.yagson.YaGson;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import model.ClientSocket;
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
import javafx.scene.shape.Rectangle;
import server.model.Card;
import server.User;
import model.enums.MenuURL;
import model.enums.SoundURL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class ShopView {

    public Card currentCard;
    public HashMap<String, Integer> stocks = new HashMap<>();
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
    public Label stockLabel;
    public Button saleButton;
    private ArrayList<Card> cards;
    private Timeline getStockTimeLine;

    @FXML
    public void initialize() {
        cardsGridPane.setStyle("-fx-background-color: #0E061E");
        cardsScrollPane.setContent(cardsGridPane);
        setHBoxBackGround();
        getCardsFromServer();
        addCards();
        setOnMouseEnteredAndExited(buyButton);
        setOnMouseEnteredAndExited(backButton);
        capitalLabel.setText(String.valueOf(User.getCurrentUser().getMoney()));
        getStock();
        getStockTimeLine = new Timeline(new KeyFrame(Duration.seconds(5), event -> getStock()));
        getStockTimeLine.setCycleCount(Animation.INDEFINITE);
        getStockTimeLine.play();
    }

    private void getCardsFromServer() {
        try {
            ClientSocket.dataOutputStream.writeUTF("Get-Cards ");
            ClientSocket.dataOutputStream.flush();
            YaGson yaGson = new YaGson();
            cards = yaGson.fromJson(ClientSocket.dataInputStream.readUTF(), ArrayList.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            Image image = new Image(cards.get(i).getImageURL());
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
                currentCard = cards.get(cardIndex);
                cardNameLabel.setText(cards.get(cardIndex).getName());
                stockLabel.setText(String.valueOf(stocks.get(currentCard.getName())));
                cardPriceLabel.setText(String.valueOf(cards.get(cardIndex).getPrice()));
                Image image = new Image(cards.get(cardIndex).getImageURL());
                cardImage.setImage(image);
                numberOfCardLabel.setText(User.getCurrentUser().
                        getNumberOfCardsInUsersAllCards(cards.get(cardIndex).getName()));
                if (Integer.parseInt(cardPriceLabel.getText()) > Integer.parseInt(capitalLabel.getText())) {
                    buyButton.setVisible(false);
                }
            }
        });
    }

    public void getStock() {
        try {
            ClientSocket.dataOutputStream.writeUTF("Get-Stocks");
            ClientSocket.dataOutputStream.flush();
            YaGson yaGson = new YaGson();
            stocks = yaGson.fromJson(ClientSocket.dataInputStream.readUTF(), HashMap.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buyClicked(MouseEvent mouseEvent) {
        try {
            ClientSocket.dataOutputStream.writeUTF("Buy-Card " + User.getCurrentUser().getUsername() +
                    " \"" + currentCard.getName() + "\"");
            ClientSocket.dataOutputStream.flush();
            String serverResponse = ClientSocket.dataInputStream.readUTF();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (serverResponse.equals("")) {
                User.getCurrentUser().decreaseMoney(currentCard.getPrice());
                User.getCurrentUser().addCardToUserAllCards(currentCard);
                capitalLabel.setText(String.valueOf(User.getCurrentUser().getMoney()));
                int numberOfCard = Integer.parseInt(numberOfCardLabel.getText()) + 1;
                numberOfCardLabel.setText(String.valueOf(numberOfCard));
                errorLabel.setText(serverResponse);
            } else errorLabel.setText(serverResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void backClicked(MouseEvent mouseEvent) throws IOException {
        getStockTimeLine.stop();
        FxmlController.getInstance().setSceneFxml(MenuURL.MAIN);
    }

    public void sellClicked(MouseEvent mouseEvent) {
        try {
            ClientSocket.dataOutputStream.writeUTF("Sell-Card " + User.getCurrentUser().getUsername() +
                    " \"" + currentCard.getName() + "\"");
            ClientSocket.dataOutputStream.flush();
            String serverResponse = ClientSocket.dataInputStream.readUTF();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (serverResponse.equals("")) {
                User.getCurrentUser().increaseMoney(currentCard.getPrice());
                removeCardFromUserCards();
                errorLabel.setText("Sold successfully");
                capitalLabel.setText(String.valueOf(User.getCurrentUser().getMoney()));
                int numberOfCard = Integer.parseInt(numberOfCardLabel.getText()) - 1;
                numberOfCardLabel.setText(String.valueOf(numberOfCard));
            } else errorLabel.setText(serverResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeCardFromUserCards() {
        for (int i = 0; i < User.getCurrentUser().getUserAllCards().size(); i++) {
            if (User.getCurrentUser().getUserAllCards().get(i).equals(currentCard.getName())) {
                User.getCurrentUser().getUserAllCards().remove(i);
                return;
            }
        }
    }
}
