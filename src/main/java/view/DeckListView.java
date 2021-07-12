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
import javafx.scene.control.TextField;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import model.Card;
import model.Deck;
import model.User;
import model.enums.MenuURL;
import model.enums.SoundURL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static view.MainGUI.editMenuButtons;
import static view.components.NodeEditor.editNode;

public class DeckListView {

    public Button backButton;
    public ScrollPane scrollPane;
    public ImageView newDeckButton;
    public VBox deckCreationVBox;
    public Label deckCreationResultLabel;
    public TextField deckNameTextField;
    public Button deckCreationVBoxBackButton;
    public Button deckCreationVBoxCreateButton;


    @FXML
    public void initialize() {

        VBox deckListVBox = instantiateDeckListVBox();
        editMenuButtons(new ArrayList<>(Collections.singletonList(backButton)));
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(deckListVBox);
        editCreationVBoxButtons();
        deckCreationVBox.setVisible(false);
    }

    private VBox instantiateDeckListVBox() {
        VBox deckListVBox = new VBox();
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
        Label deckNumberOfCardsLabel = new Label("Cards: " + deckNumberOfCards);
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
        trashImageView.setFitWidth(25);
        trashImageView.setFitHeight(36);
        ImageView editImageView = new ImageView(editImage);
        editImageView.setFitWidth(50);
        editImageView.setFitHeight(40);
        ImageView activateTextImageView = new ImageView(activateTextImage);
        activateTextImageView.setFitWidth(100);
        activateTextImageView.setFitHeight(50);
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
            HBox deckInformationHBox = (HBox) editDeckImageView.getParent().getParent();
            Label deckNameLabel = (Label) deckInformationHBox.getChildren().get(1);
            DeckView.setDeckName(deckNameLabel.getText());
            try {
                FxmlController.getInstance().setSceneFxml(MenuURL.DECK);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        if (User.getCurrentUser().getDeckByDeckName(deckNameLabel.getText()).isDeckActivated()) {
            deckInformationHBox.setStyle("-fx-border-color: #ffffff; -fx-border-width: 6; -fx-background-color: black");
        } else {
            deckInformationHBox.setStyle("-fx-border-color: #9b00ff; -fx-border-width: 6; -fx-background-color: black");
        }
        return deckInformationHBox;
    }

    private void editCreationVBoxButtons() {
        newDeckButton.setOnMouseEntered(event -> {
            newDeckButton.setBlendMode(BlendMode.GREEN);
            newDeckButton.setEffect(new Glow(1));
            SoundPlayer.getInstance().playAudioClip(SoundURL.BUTTON_HOVER);
        });
        newDeckButton.setOnMouseExited(event -> {
            newDeckButton.setBlendMode(BlendMode.SRC_OVER);
            newDeckButton.setEffect(null);
        });
        newDeckButton.setOnMouseClicked(event -> {
            deckCreationVBox.setVisible(true);
        });
        editNode(0.6, deckCreationVBoxBackButton, deckCreationVBoxCreateButton);
    }

    public void goToMainMenu(MouseEvent mouseEvent) throws IOException {
        FxmlController.getInstance().setSceneFxml(MenuURL.MAIN);
    }

    public void makeDeckCreationVBoxInvisible(MouseEvent mouseEvent) {
        deckCreationResultLabel.setText("");
        deckNameTextField.setText("");
        deckCreationVBox.setVisible(false);
    }

    public void createDeck(MouseEvent mouseEvent) {
        if (deckNameTextField.getText().length() == 0) {
            deckCreationResultLabel.setText("Deck Name's Text field is empty");
        } else {
            if (deckNameTextField.getText().matches("\\S+")) {
                deckCreationResultLabel.setText(DeckMenuController.getInstance().createDeck(deckNameTextField.getText()));
                if (deckCreationResultLabel.getText().equals("deck created successfully!")) {
                    addNewDecksInformationHBox(deckNameTextField.getText());
                }
            } else {
                deckCreationResultLabel.setText("Invalid Deck Name format!");
            }
        }
    }

    private void addNewDecksInformationHBox(String deckName) {
        VBox parentVBox = (VBox) scrollPane.getContent();
        parentVBox.getChildren().add(makeDeckInformationHBox(makeRowNumberLabel(parentVBox.getChildren().size() + 1),
                makeDeckNameLabel(deckName), makeDeckNumberOfCardsLabel(Integer.toString(User.getCurrentUser().
                        getDeckByDeckName(deckName).getDeckCards().size())), makeDeckEditAndDeleteHBox(deckName)));
    }
}
