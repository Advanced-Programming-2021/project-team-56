package view;

import controller.DeckMenuController;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import server.Card;
import model.Deck;
import server.User;
import model.enums.MenuURL;
import view.components.NodeEditor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static view.MainGUI.editMenuButtons;

public class DeckView {

    private static Deck deck;
    public AnchorPane mainDeckAnchorPane;
    public ScrollPane userCardsScrollPane;
    public Label deckNameLabel;
    public ImageView clickedCardImage;
    public Button backButton;
    public AnchorPane sideDeckAnchorPane;
    public VBox addCardToDeckVBox;
    public Label addCardToDeckTextLabel;
    public Button addCardToDeckBackButton;
    public Button removeButton;
    public Button mainDeckAddButton;
    public Button sideDeckAddButton;
    public HBox addCardToDeckHBox;
    private HashMap<String, Label> userDeckCards;

    {
        userDeckCards = new HashMap<>();
    }

    public static void setDeckName(String deckName) {
        deck = User.getCurrentUser().getDeckByDeckName(deckName);
    }

    @FXML
    public void initialize() {
        makeUserCardsScrollPane();
        deckNameLabel.setText(deck.getDeckName());
        initializeMainDeckCards();
        initializeSideDeckCards();
        editButtons();
        editAddCardToDeckVBox();
    }

    private void makeUserCardsScrollPane() {
        GridPane userCardsGridPane = makeUserCardsGridPane();
        userCardsScrollPane.setContent(userCardsGridPane);
        userCardsScrollPane.setFitToWidth(true);
        userCardsScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    }

    public void initializeMainDeckCards() {
        ArrayList<Card> mainDeckCards = deck.getMainDeck();
        for (int i = 0; i < mainDeckCards.size(); i++) {
            ImageView cardImageView = instantiateCardImage(mainDeckCards.get(i), i, false);
            addCardImageToMainDeck(cardImageView, i);
        }
    }

    private void initializeSideDeckCards() {
        ArrayList<Card> sideDeckCards = deck.getSideDeck();
        for (int i = 0; i < sideDeckCards.size(); i++) {
            ImageView cardImageView = instantiateCardImage(sideDeckCards.get(i), i, true);
            sideDeckAnchorPane.getChildren().add(cardImageView);
            if (i != 0) {
                cardImageView.setLayoutX(sideDeckAnchorPane.getChildren().get(i - 1).getLayoutX() + 35);
            }
        }
    }

    private void editAddCardToDeckVBox() {
        addCardToDeckVBox.setVisible(false);
        addCardToDeckBackButton.setOnMouseClicked(event -> addCardToDeckVBox.setVisible(false));
    }

    private ImageView instantiateCardImage(Card card, int cardIndex, boolean isSideDeckCardImageView) {
        ImageView cardImageView = new ImageView(card.getImageURL());
        cardImageView.setFitWidth(70);
        cardImageView.setFitHeight(110);
        cardImageView.setLayoutY(cardImageView.getLayoutY() + 25);
        cardImageView.setLayoutX(cardImageView.getLayoutX() + 25);
        cardImageView.setOnMouseClicked(event -> {
            clickedCardImage.setImage(cardImageView.getImage());
            removeButton.setVisible(true);
            removeButton.setOnMouseClicked(anotherEvent -> {
                removeButton.setVisible(false);
                clickedCardImage.setImage(null);
                if (isSideDeckCardImageView) {
                    deck.getSideDeck().remove(cardIndex);
                    sideDeckAnchorPane.getChildren().clear();
                    initializeSideDeckCards();
                } else {
                    deck.getMainDeck().remove(cardIndex);
                    mainDeckAnchorPane.getChildren().clear();
                    initializeMainDeckCards();
                }
                for (int i = 0; i < deck.getDeckCards().size(); i++) {
                    if (deck.getDeckCards().get(i).getName().equals(card.getName())) {
                        deck.getDeckCards().remove(i);
                        break;
                    }
                }
                deck.getUserCards().add(card);
                Label label = userDeckCards.get(card.getName());
                label.setText(String.valueOf(Integer.parseInt(label.getText()) + 1));
            });
        });
        NodeEditor.editNode(0.6, cardImageView);
        return cardImageView;
    }

    private void addCardImageToMainDeck(ImageView cardImageView, int cardImageIndex) {
        mainDeckAnchorPane.getChildren().add(cardImageView);
        if (cardImageIndex < 20) {
            if (cardImageIndex != 0) {
                cardImageView.setLayoutX(mainDeckAnchorPane.getChildren().get(cardImageIndex - 1).getLayoutX() + 35);
            }
        } else if (cardImageIndex < 40) {
            if (cardImageIndex != 20) {
                cardImageView.setLayoutX(mainDeckAnchorPane.getChildren().get(cardImageIndex - 21).getLayoutX() + 35);
            }
            cardImageView.setLayoutY(mainDeckAnchorPane.getChildren().get(0).getLayoutY() + 145);
        } else {
            if (cardImageIndex != 40) {
                cardImageView.setLayoutX(mainDeckAnchorPane.getChildren().get(cardImageIndex - 41).getLayoutX() + 35);
            }
            cardImageView.setLayoutY(mainDeckAnchorPane.getChildren().get(20).getLayoutY() + 145);
        }
    }

    private void addCardImageToSideDeck(ImageView cardImageView, int cardImageIndex) {
        sideDeckAnchorPane.getChildren().add(cardImageView);
        if (cardImageIndex != 0) {
            cardImageView.setLayoutX(sideDeckAnchorPane.getChildren().get(cardImageIndex - 1).getLayoutX() + 35);
        }
    }

    private GridPane makeUserCardsGridPane() {
        GridPane gridPane = instantiateGridPane();
        ArrayList<Card> userAllCards = User.getCurrentUser().getUserAllCards();
        for (int i = 0; i < userAllCards.size(); i++) {
            Card card = userAllCards.get(i);
            if (!userDeckCards.containsKey(card.getName())) {
                Rectangle cardImageRectangle = instantiateCardImageRectangle(card);
                StackPane userCardsAmountStackPane = instantiateUserCardsAmountNode(card.getName());
                HBox userDeckCardHBox = new HBox(5, cardImageRectangle, userCardsAmountStackPane);
                userDeckCardHBox.setAlignment(Pos.CENTER);
                gridPane.add(userDeckCardHBox, 0, i);
            }
        }
        return gridPane;
    }

    private GridPane instantiateGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 5, 10, 10));
        gridPane.setVgap(5);
        gridPane.setStyle("-fx-background-color: #0E061E");
        return gridPane;
    }

    private Rectangle instantiateCardImageRectangle(Card card) {
        Rectangle rectangle = new Rectangle(200, 291.6864608076009501187648456057);
        rectangle.setFill(new ImagePattern(new Image(card.getImageURL())));
        rectangle.setOnMouseClicked(event -> {
            addCardToDeckHBox.setVisible(true);
            addCardToDeckVBox.setVisible(true);
            removeButton.setVisible(false);
            clickedCardImage.setImage(new Image(card.getImageURL()));
            Label numberOfCardLabel = userDeckCards.get(card.getName());
            if (Integer.parseInt(numberOfCardLabel.getText()) > 0) {
                addCardToDeckTextLabel.setText("Which deck would you like to add the card to");
                mainDeckAddButton.setOnMouseClicked(mouseEvent -> {
                    addCardToDeckHBox.setVisible(false);
                    String result = DeckMenuController.getInstance().addToDeck(deck.getDeckName(), card.getName(), false);
                    if (result.equals("Card added successfully")) {
                        numberOfCardLabel.setText(String.valueOf(Integer.parseInt(numberOfCardLabel.getText()) - 1));
                        addCardImageToMainDeck(instantiateCardImage(card, deck.getMainDeck().size() - 1, false), mainDeckAnchorPane.getChildren().size());
                    }
                    addCardToDeckTextLabel.setText(result);
                });
                sideDeckAddButton.setOnMouseClicked(mouseEvent -> {
                    addCardToDeckHBox.setVisible(false);
                    String result = DeckMenuController.getInstance().addToDeck(deck.getDeckName(), card.getName(), true);
                    if (result.equals("Card added successfully")) {
                        numberOfCardLabel.setText(String.valueOf(Integer.parseInt(numberOfCardLabel.getText()) - 1));
                        addCardImageToSideDeck(instantiateCardImage(card, deck.getSideDeck().size() - 1, true), sideDeckAnchorPane.getChildren().size());
                    }
                    addCardToDeckTextLabel.setText(result);
                });
            } else {
                addCardToDeckTextLabel.setText("You dont have a card of this type anymore");
                addCardToDeckHBox.setVisible(false);
            }
        });
        NodeEditor.editNode(0.3, rectangle);
        return rectangle;
    }

    private StackPane instantiateUserCardsAmountNode(String cardName) {
        Label numberOfCardInUserDeckCards = new Label(deck.getNumberOfCardsInUserCards(cardName));
        numberOfCardInUserDeckCards.setStyle("-fx-text-fill: white; -fx-font-family: 'Times New Roman'; -fx-font-size: 25px");
        userDeckCards.put(cardName, numberOfCardInUserDeckCards);
        Circle backgroundCircle = new Circle(30, Paint.valueOf("#0E061E"));
        Circle magicCircle = new Circle(30, new ImagePattern(new Image("/images/Magic-Circle.png")));
        return new StackPane(backgroundCircle, magicCircle, numberOfCardInUserDeckCards);
    }

    private void editButtons() {
        editMenuButtons(new ArrayList<>(Collections.singletonList(backButton)));
        backButton.setOnMouseClicked(event -> {
            try {
                FxmlController.getInstance().setSceneFxml(MenuURL.DECK_LIST);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        removeButton.setVisible(false);
        NodeEditor.editNode(0.6, removeButton, mainDeckAddButton, sideDeckAddButton, addCardToDeckBackButton);
    }
}
