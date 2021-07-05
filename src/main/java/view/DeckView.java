package view;

import controller.DeckMenuController;
import controller.SoundPlayer;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.Card;
import model.Deck;
import model.User;
import model.enums.MenuURL;
import model.enums.SoundURL;
import view.components.NodeEditor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static view.MainGUI.editMenuButtons;

public class DeckView {

    private static Deck deck;
    public AnchorPane firstRowMainDeckAnchorPane;
    public AnchorPane secondRowMainDeckAnchorPane;
    public AnchorPane thirdRowMainDeckAnchorPane;
    public ScrollPane userCardsScrollPane;
    public Label deckNameLabel;
    public ImageView clickedCardImage;
    public Button backButton;
    public AnchorPane sideDeckAnchorPane;
    public VBox errorVBox;
    public Label errorLabel;
    public Button errorVBoxBackButton;
    private HashMap<String, Label> userDeckCards;

    {
        userDeckCards = new HashMap<>();
    }

    public static void setDeckName(String deckName) {
        deck = User.getCurrentUser().getDeckByDeckName(deckName);
    }

    @FXML
    public void initialize() {
        editErrorVBox();
        GridPane userCardsGridPane = makeUserCardsGridPane();
        userCardsScrollPane.setContent(userCardsGridPane);
        userCardsScrollPane.setFitToWidth(true);
        userCardsScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        deckNameLabel.setText(deck.getDeckName());
        editButton();
        initializeDeckCards();
//        fillAnchorPane(firstRowMainDeckAnchorPane);
//        fillAnchorPane(secondRowMainDeckAnchorPane);
//        fillAnchorPane(thirdRowMainDeckAnchorPane);
    }

    private void editErrorVBox() {
        errorVBox.setVisible(false);
        errorVBoxBackButton.setOnMouseClicked(event -> errorVBox.setVisible(false));
        NodeEditor.editNode(0.6, errorVBoxBackButton);
    }

    public void initializeDeckCards() {
        ArrayList<Card> mainDeckCards = deck.getMainDeck();
        for (int i = 0; i < mainDeckCards.size(); i++) {
            ImageView cardImageView = new ImageView(mainDeckCards.get(i).getImageURL());
            cardImageView.setFitWidth(70);
            cardImageView.setFitHeight(110);
            cardImageView.setLayoutY(cardImageView.getLayoutY() + 25);
            cardImageView.setLayoutX(cardImageView.getLayoutX() + 25);
            if (i > 19) {



                if (i > 39) {
//                    fillAnchorPane(thirdRowMainDeckAnchorPane);
                    thirdRowMainDeckAnchorPane.getChildren().add(cardImageView);
                    if (i != 40) {
                        System.out.println(i);
                        System.out.println(i - 41);
                        cardImageView.setLayoutX(thirdRowMainDeckAnchorPane.getChildren().get(i - 41).getLayoutX() + 35);
                    }
                    cardImageView.setOnMouseClicked(event -> {
                        cardImageView.toFront();
                        clickedCardImage.setImage(cardImageView.getImage());
                    });
                    cardImageView.setOnMouseEntered(event -> cardImageView.setEffect(new Glow(0.5)));

                    int finalI = i;
                    cardImageView.setOnMouseExited(event -> {
                        cardImageView.setEffect(null);
                        if (!cardImageView.equals(thirdRowMainDeckAnchorPane.getChildren().get(finalI - 40))) {
                            for (int k = 0; k < mainDeckCards.size() - finalI - 1; k++) {
                                ImageView frontImages = (ImageView) thirdRowMainDeckAnchorPane.getChildren().get(finalI - 40);
                                frontImages.toFront();
                            }
                        }
                    });
                }


//                secondRowMainDeckAnchorPane.getChildren().add(cardImageView);


            }



//            firstRowMainDeckAnchorPane.getChildren().add(cardImageView);
        }



//        for (int i = 0; i < 20; i++) {
//            ImageView imageView = new ImageView("/images/Back-card.jpg");
//            imageView.setFitWidth(70);
//            imageView.setFitHeight(110);
//            anchorPane.getChildren().add(imageView);
//            imageView.setLayoutY(imageView.getLayoutY() + 25);
//            imageView.setLayoutX(imageView.getLayoutX() + 25);
//            if (i != 0) {
//                imageView.setLayoutX(anchorPane.getChildren().get(i -1).getLayoutX() + 35);
//            }
//            imageView.setOnMouseClicked(event -> imageView.toFront());
//            imageView.setOnMouseEntered(event -> imageView.setEffect(new Glow(1)));
//            int finalI = i;
//            imageView.setOnMouseExited(event -> {
//                imageView.setEffect(null);
//                if (!imageView.equals(anchorPane.getChildren().get(finalI))) {
//                    for (int k = 0; k < 19 - finalI; k++) {
//                        ImageView frontImages = (ImageView) anchorPane.getChildren().get(finalI);
//                        frontImages.toFront();
//                    }
//                }
//            });
//        }
    }

    private void fillAnchorPane(AnchorPane anchorPane) {

    }

    private GridPane makeUserCardsGridPane() {
        //TODO there is a gap between curt of the something.. ask the gang wtf is going on
        GridPane gridPane = new GridPane();
        editGridPane(gridPane);

        for (int i = 0; i < deck.getUserCards().size(); i++) {
            Card card = deck.getUserCards().get(i);
            if (!userDeckCards.containsKey(card.getName())) {
                Rectangle cardImageRectangle = new Rectangle(200, 291.6864608076009501187648456057);
                editCardImageRectangle(cardImageRectangle, card);

                Label numberOfCardInUserDeckCards = new Label("1");
                numberOfCardInUserDeckCards.setStyle("-fx-text-fill: white; -fx-font-family: 'Times New Roman'; -fx-font-size: 25px");
                userDeckCards.put(card.getName(), numberOfCardInUserDeckCards);
                Circle backgroundCircle = new Circle(30, Paint.valueOf("#0E061E"));
                Circle magicCircle = new Circle(30, new ImagePattern(new Image("/images/Magic-Circle.png")));
                StackPane numberLabel = new StackPane(backgroundCircle, magicCircle, numberOfCardInUserDeckCards);
                HBox userDeckCardHBox = new HBox(5, cardImageRectangle, numberLabel);
                userDeckCardHBox.setAlignment(Pos.CENTER);
                gridPane.add(userDeckCardHBox, 0, i);
            } else {
                Label numberOfCardsLabel = userDeckCards.get(card.getName());
                numberOfCardsLabel.setText(String.valueOf(Integer.parseInt(numberOfCardsLabel.getText()) + 1));
            }
        }
        return gridPane;
    }

    private void editGridPane(GridPane gridPane) {
        gridPane.setPadding(new Insets(10, 5, 10, 10));
        gridPane.setVgap(5);
        gridPane.setStyle("-fx-background-color: #0E061E");
    }

    private void editCardImageRectangle(Rectangle rectangle, Card card) {
        rectangle.setFill(new ImagePattern(new Image(card.getImageURL())));
        rectangle.setOnMouseClicked(event -> {
            clickedCardImage.setImage(new Image(card.getImageURL()));
            Label numberOfCardLabel = userDeckCards.get(card.getName());
            if (Integer.parseInt(numberOfCardLabel.getText()) > 0) {
                //TODO Refactor
                String result = DeckMenuController.getInstance().addToDeck(deck.getDeckName(), card.getName(), false);
                if (result.equals("card added successfully")) {
                    numberOfCardLabel.setText(String.valueOf(Integer.parseInt(numberOfCardLabel.getText()) - 1));
                } else {
                    errorLabel.setText(result);
                    errorVBox.setVisible(true);
                }
            } else {
                errorLabel.setText("You dont have any card of this type anymore");
                errorVBox.setVisible(true);
            }
        });
        decorateCardImageRectangle(rectangle);
    }

    private void decorateCardImageRectangle(Rectangle rectangle) {
        rectangle.setOnMouseEntered(event -> {
            rectangle.setEffect(new Glow(0.3));
            SoundPlayer.getInstance().playAudioClip(SoundURL.BUTTON_HOVER);
        });
        rectangle.setOnMouseExited(event -> rectangle.setEffect(null));
    }

    private void editButton() {
        editMenuButtons(new ArrayList<>(Collections.singletonList(backButton)));
        backButton.setOnMouseClicked(event -> {
            try {
                FxmlController.getInstance().setSceneFxml(MenuURL.DECK_LIST);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
