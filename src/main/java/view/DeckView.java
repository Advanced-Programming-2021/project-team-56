package view;
import controller.DeckMenuController;
import controller.SoundPlayer;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import model.User;
import model.enums.MenuURL;
import model.enums.SoundURL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static view.MainGUI.editMenuButtons;
import static view.components.NodeEditor.editNode;

public class DeckView {

    public Button backButton;
    public ScrollPane scrollPane;
    public ImageView newDeckButton;


    @FXML
    public void initialize() {
        VBox deckListVBox = instantiateDeckListVBox();
        editMenuButtons(new ArrayList<>(Collections.singletonList(backButton)));
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(deckListVBox);
    }

    private VBox instantiateDeckListVBox() {
        VBox deckListVBox = new VBox();
        //TODO test deck adding
        for (int i = 0; i < 20; i++) {
            DeckMenuController.getInstance().createDeck("Deck" + (i + 1), User.getCurrentUser().getUsername());
        }

        String allDecksInformation = DeckMenuController.getInstance().getUsersDeckInformation();
        if (allDecksInformation.length() == 0) {
            return deckListVBox;
        }
        String[] decksInformation = allDecksInformation.split("\n");
        for (int i = 0; i < decksInformation.length; i++) {
            Label rowNumberLabel = makeRowNumberLabel(i + 1);
            String[] splitDeckInfo = decksInformation[i].split(" ");
            Label deckNameLabel = makeDeckNameLabel(splitDeckInfo[0]);
            Label deckNumberOfCardsLabel = makeDeckNumberOfCardsLabel(splitDeckInfo[1]);
            HBox deckEditAndDeleteHBox = makeDeckEditAndDeleteHBox(splitDeckInfo[0]);
            HBox deckInformationHBox = makeDeckInformationHBox(rowNumberLabel, deckNameLabel,
                    deckNumberOfCardsLabel, deckEditAndDeleteHBox);
            deckListVBox.getChildren().add(deckInformationHBox);
        }
        return deckListVBox;
    }

    private Label makeRowNumberLabel(int number) {
        Label rowNumberLabel = new Label("-" + number);
        rowNumberLabel.setAlignment(Pos.CENTER_LEFT);
        rowNumberLabel.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 40px");
        rowNumberLabel.setPrefSize(100, 100);
        rowNumberLabel.setTextFill(Paint.valueOf("#D4B1F5"));
        return rowNumberLabel;
    }

    private Label makeDeckNameLabel(String deckName) {
        Label deckNameLabel = new Label(deckName);
        editLabel(deckNameLabel);
        deckNameLabel.setPrefSize(600, 100);
        deckNameLabel.setTextFill(Paint.valueOf("#8bd6ff"));
        return deckNameLabel;
    }

    private Label makeDeckNumberOfCardsLabel(String deckNumberOfCards) {
        Label deckNumberOfCardsLabel = new Label(deckNumberOfCards);
        editLabel(deckNumberOfCardsLabel);
        deckNumberOfCardsLabel.setPrefSize(300, 100);
        deckNumberOfCardsLabel.setTextFill(Paint.valueOf("#D4B1F5"));
        return deckNumberOfCardsLabel;
    }

    private Label makeLabel() {
        return null;
    }

    private void editLabel(Label label) {
        label.setAlignment(Pos.CENTER);
        label.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 30px");
    }

    private HBox makeDeckEditAndDeleteHBox(String deckName) {
        Image editImage = new Image("/images/Edit-Pencil.png");
        Image trashBinImage = new Image("/images/TrashBinResized.png");
        Image activateTextImage = new Image ("/images/Texts/ActivateText.png");
        ImageView trashImageView = new ImageView(trashBinImage);
        trashImageView.setFitWidth(50);
        trashImageView.setFitHeight(72.344944774851316907391673746814);
        ImageView editImageView = new ImageView(editImage);
        editImageView.setFitWidth(100);
        editImageView.setFitHeight(80);
        ImageView activateTextImageView = new ImageView(activateTextImage);
        activateTextImageView.setFitWidth(200);
        editImageView.setFitHeight(50);
        putCommandsOnImageViews(trashImageView, editImageView, activateTextImageView, deckName);
        HBox deckEditAndDeleteHBox = new HBox(50, activateTextImageView, editImageView, trashImageView);
        deckEditAndDeleteHBox.setAlignment(Pos.CENTER_RIGHT);
        deckEditAndDeleteHBox.setPrefSize(500, 100);
        return deckEditAndDeleteHBox;
    }

    private void putCommandsOnImageViews(ImageView trashImageView, ImageView editDeckImageView,
                                         ImageView activateTextImageView, String deckName) {
        editTrashImageView(trashImageView, deckName);
        editEditDeckImageView(editDeckImageView);
        editActivateTextImageView(activateTextImageView, deckName);
    }

    private void editTrashImageView(ImageView trashImageView, String deckName) {
        trashImageView.setOnMouseEntered(event -> {
            trashImageView.setBlendMode(BlendMode.RED);
            trashImageView.setEffect(new Glow(0.5));
            SoundPlayer.getInstance().playAudioClip(SoundURL.BUTTON_HOVER);
        });
        setOnMouseExitedForImageViews(trashImageView);
        trashImageView.setOnMouseClicked(event -> {
            DeckMenuController.getInstance().deleteDeck(deckName);
            VBox greatGrandParentVBox = (VBox) trashImageView.getParent().getParent().getParent();
            greatGrandParentVBox.getChildren().remove(trashImageView.getParent().getParent());
        });
    }

    private void editEditDeckImageView(ImageView editDeckImageView) {
        editDeckImageView.setOnMouseEntered(event -> {
            editDeckImageView.setBlendMode(BlendMode.GREEN);
            editDeckImageView.setEffect(new Glow(0.5));
            SoundPlayer.getInstance().playAudioClip(SoundURL.BUTTON_HOVER);
        });
        setOnMouseExitedForImageViews(editDeckImageView);
        editDeckImageView.setOnMouseClicked(event -> {
            //TODO go to Deck page
        });
    }

    private void editActivateTextImageView(ImageView activateTextImageView, String deckName) {
        activateTextImageView.setOnMouseEntered(event -> {
            activateTextImageView.setBlendMode(BlendMode.GREEN);
            activateTextImageView.setEffect(new Glow(0.5));
            SoundPlayer.getInstance().playAudioClip(SoundURL.BUTTON_HOVER);
        });
        setOnMouseExitedForImageViews(activateTextImageView);
        activateTextImageView.setOnMouseClicked(event -> {
            DeckMenuController.getInstance().setActive(deckName);
            removeBorderHighlightFromAllDecks();
            activateTextImageView.getParent().getParent().setStyle("-fx-border-color: #ffffff; -fx-border-width: 6;" +
                    " -fx-background-color: black");
        });
    }

    private void setOnMouseExitedForImageViews(ImageView imageView) {
        imageView.setOnMouseExited(event -> {
            imageView.setEffect(null);
            imageView.setBlendMode(BlendMode.SRC_OVER);
        });
    }

    private void removeBorderHighlightFromAllDecks() {
        VBox contentVBox = (VBox) scrollPane.getContent();
        for (Node children : contentVBox.getChildren()) {
            children.setStyle("-fx-border-color: #9b00ff; -fx-border-width: 6; -fx-background-color: black");
        }
    }

    private HBox makeDeckInformationHBox(Label rowNumberLabel, Label deckNameLabel,
                                         Label deckNumberOfCardsLabel, HBox deckEditAndDeleteHBox) {
        HBox deckInformationHBox = new HBox(rowNumberLabel, deckNameLabel, deckNumberOfCardsLabel, deckEditAndDeleteHBox);
        editNode(1, deckInformationHBox);
        deckInformationHBox.setPadding(new Insets(10, 5, 10, 5));
        deckInformationHBox.setPrefSize(1500, 100);
        //TODO Highlight the active deck
        if (User.getCurrentUser().getDeckByDeckName(deckNameLabel.getText()).isDeckActivated()) {
            deckInformationHBox.setStyle("-fx-border-color: #ffffff; -fx-border-width: 6; -fx-background-color: black");
        } else {
            deckInformationHBox.setStyle("-fx-border-color: #9b00ff; -fx-border-width: 6; -fx-background-color: black");
        }
        return deckInformationHBox;
    }

    public void goToMainMenu(MouseEvent mouseEvent) throws IOException {
        FxmlController.getInstance().setSceneFxml(MenuURL.MAIN);
    }
}