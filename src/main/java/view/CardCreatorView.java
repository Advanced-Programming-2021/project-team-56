package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import model.Card;
import model.*;
import model.enums.MenuURL;
import view.components.NodeEditor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class CardCreatorView {
    public Button continueButton;
    public Button backButton;
    public Button createButton;
    public Button descriptionsButton;

    public HBox fundamentalsHBox;
    public HBox spellAndTrapHBox;
    public HBox monsterHBox1;
    public HBox monsterHBox2;

    public ComboBox fundamentalCardTypeComboBox;
    public TextField cardNameTextField;
    public TextField priceTextField;


    public ComboBox spellAndTrapTypeComboBox;
    public ComboBox spellAndTrapIconComboBox;

    public ComboBox monsterCardTypeComboBox;
    public ComboBox monsterAttributeComboBox;
    public TextField monsterATKTextField;
    public TextField monsterDEFTextField;
    public TextField monsterLevelTextField;
    private Label descriptionLabel = null;
    public ScrollPane descriptionsScrollPane;
    public GridPane descriptionsGridPane;

    private String imageURL = "/images/Cards/mystery-card.png";

    @FXML
    public void initialize() {
        editDescriptionsScrollPane();
        onlyShowFundamentals();
        editButtons();
        ObservableList<String> items = FXCollections.observableArrayList("Monster", "Spell/Trap");
        fundamentalCardTypeComboBox.setItems(items);

        ObservableList<String> spellAndTrapTypes = FXCollections.observableArrayList("Trap", "Spell");
        spellAndTrapTypeComboBox.setItems(spellAndTrapTypes);

        ObservableList<String> spellAndTrapIcons = FXCollections.observableArrayList("Normal", "Counter", "Continuous",
                "Quick-play", "Field", "Equip", "Ritual");
        spellAndTrapIconComboBox.setItems(spellAndTrapIcons);

        ObservableList<String> attributes = FXCollections.observableArrayList("FIRE", "WATER", "WIND", "EARTH", "LIGHT", "DARK");
        monsterAttributeComboBox.setItems(attributes);

        ObservableList<String> monsterTypes = FXCollections.observableArrayList("Normal", "Effect", "Ritual");
        monsterCardTypeComboBox.setItems(monsterTypes);
    }

    private void onlyShowFundamentals() {
        spellAndTrapHBox.setVisible(false);
        descriptionsScrollPane.setVisible(false);
        monsterHBox1.setVisible(false);
        monsterHBox2.setVisible(false);
    }

    private void editButtons() {
        descriptionsButton.setOnMouseClicked(event -> descriptionsScrollPane.setVisible(true));
        descriptionsScrollPane.setOnMouseExited(event -> descriptionsScrollPane.setVisible(false));
        NodeEditor.editNode(0.6, createButton, continueButton);
        MainGUI.editMenuButtons(new ArrayList<>(Collections.singletonList(backButton)));
        backButton.setOnMouseClicked(event -> {
            try {
                FxmlController.getInstance().setSceneFxml(MenuURL.SHOP);
            } catch (IOException ignored) {
            }
        });
        setOnMouseClickedForContinueButton();
    }

    private void setOnMouseClickedForContinueButton() {
        continueButton.setOnMouseClicked(event -> {
            if (descriptionLabel != null) {
                if (fundamentalCardTypeComboBox.getValue() != null) {
                    if (!cardNameTextField.getText().equals("")) {
                        if (priceTextField.getText().matches("^\\d+$")) {
                            fundamentalsHBox.setVisible(false);
                            String cardType = (String) fundamentalCardTypeComboBox.getValue();
                            if (cardType.equals("Monster")) {
                                monsterHBox1.setVisible(true);
                                monsterHBox2.setVisible(true);
                                changeContinueButtonOnMouseClickedForMonsterCards();
                            } else {
                                spellAndTrapHBox.setVisible(true);
                                changeContinueButtonOnMouseClickedForSpellAndTrapCards();
                            }
                        }
                    }
                }
            }
        });
    }

    private void changeContinueButtonOnMouseClickedForMonsterCards() {
        continueButton.setOnMouseClicked(event -> {
            if (monsterCardTypeComboBox.getValue() != null) {
                if (monsterAttributeComboBox.getValue() != null) {
                    if (monsterATKTextField.getText().length() != 0) {
                        if (monsterDEFTextField.getText().length() != 0) {
                            if (monsterLevelTextField.getText().matches("\\d")) {
                                setOnMouseClickedForCreateButtonForMonsterCard();
                            }
                        }
                    }
                }
            }
        });
    }

    private void changeContinueButtonOnMouseClickedForSpellAndTrapCards() {
        continueButton.setOnMouseClicked(event -> {
            if (spellAndTrapIconComboBox.getValue() != null) {
                if (spellAndTrapTypeComboBox.getValue() != null) {
                    String spellAndTrapType = (String) spellAndTrapTypeComboBox.getValue();
                    if (spellAndTrapType.equals("Spell")) {
                        setOnMouseClickedForCreateButtonForSpellCard();
                    } else {
                        setOnMouseClickedForCreateButtonForTrapCard();
                    }
                }
            }
        });
    }

    private void setOnMouseClickedForCreateButtonForTrapCard() {
        createButton.setOnMouseClicked(event -> {
            System.out.println("Trap");
            TrapCard trapCard = new TrapCard(instantiateTrapCard());
            trapCard.setType((String) spellAndTrapTypeComboBox.getValue());
            trapCard.setIcon((String) spellAndTrapIconComboBox.getValue());
            User.getCurrentUser().getUserAllCards().add(trapCard);
            goBackToShop();
        });
    }

    private void setOnMouseClickedForCreateButtonForSpellCard() {
        createButton.setOnMouseClicked(event -> {
            SpellCard spellCard = new SpellCard(instantiateSpellCard());
            spellCard.setIcon((String) spellAndTrapIconComboBox.getValue());
            spellCard.setType((String) spellAndTrapTypeComboBox.getValue());
            User.getCurrentUser().getUserAllCards().add(spellCard);
            goBackToShop();
        });
    }

    private void setOnMouseClickedForCreateButtonForMonsterCard() {
        createButton.setOnMouseClicked(event -> {
            MonsterCard monsterCard = new MonsterCard(instantiateMonsterCard());
            monsterCard.setAttack(Integer.parseInt(monsterATKTextField.getText()));
            monsterCard.setDefence(Integer.parseInt(monsterDEFTextField.getText()));
            monsterCard.setLevel(Integer.parseInt(monsterLevelTextField.getText()));
            monsterCard.setAttribute((String) monsterAttributeComboBox.getValue());
            monsterCard.setCardType((String) monsterCardTypeComboBox.getValue());
            User.getCurrentUser().getUserAllCards().add(monsterCard);
            goBackToShop();
        });
    }

    private TrapCard instantiateTrapCard() {
        return new TrapCard(getCardByDescription(descriptionLabel.getText()), descriptionLabel.getText(),
                imageURL, Integer.parseInt(priceTextField.getText()));
    }

    private MonsterCard instantiateMonsterCard() {
        return new MonsterCard(getCardByDescription(descriptionLabel.getText()), descriptionLabel.getText(),
                imageURL, Integer.parseInt(priceTextField.getText()));
    }

    private SpellCard instantiateSpellCard() {

        return new SpellCard(getCardByDescription(descriptionLabel.getText()), descriptionLabel.getText(),
                imageURL, Integer.parseInt(priceTextField.getText()));
    }

    private void goBackToShop() {
        try {
            FxmlController.getInstance().setSceneFxml(MenuURL.SHOP);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getCardByDescription(String description) {
        for (Card card : Card.getCards()) {
            if (card.getDescription().equals(description)) {
                return card.getName();
            }
        }
        return "";
    }

    private void editDescriptionsScrollPane() {
        descriptionsScrollPane.setFitToWidth(true);
        for (int i = 0; i < Card.getCards().size(); i++) {
            Label label = new Label();
            label.setPadding(new Insets(0, 0, 0, 5));
            label.setPrefWidth(descriptionsScrollPane.getPrefWidth());
            label.setPrefHeight(60);
            label.setStyle("-fx-border-color:  #DBBEF6; -fx-border-width: 3; -fx-text-fill: white; -fx-font-size: 11");
            label.setText(Card.getCards().get(i).getDescription());
            descriptionsGridPane.add(label, 0, i);
            NodeEditor.editNode(0.6, label);
            label.setOnMouseClicked(event -> {
                descriptionLabel = label;
                descriptionsScrollPane.setVisible(false);
            });
        }
    }
}
