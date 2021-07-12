package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import model.enums.MenuURL;
import view.components.NodeEditor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class CardCreatorView {
    public Button continueButton;
    public Button backButton;
    public Button createButton;
    
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
    public ComboBox monsterMonsterTypeComboBox;
    public TextField monsterATKTextField;
    public TextField monsterDEFTextField;
    public TextField monsterLevelTextField;

    @FXML
    public void initialize() {
        onlyShowFundamentals();
        editButtons();
        ObservableList<String> items = FXCollections.observableArrayList("Monster", "Spell/Trap");
        fundamentalCardTypeComboBox.setItems(items);
//        ObservableList<String> items = FXCollections.observableArrayList("Monster", "Spell/Trap");
//        fundamentalCardTypeComboBox.setItems(items);
//        numberOfRoundsComboBox.getValue() == null
    }

    private void onlyShowFundamentals() {
        spellAndTrapHBox.setVisible(false);
        monsterHBox1.setVisible(false);
        monsterHBox2.setVisible(false);
    }

    private void editButtons() {
        NodeEditor.editNode(0.6, createButton, continueButton);
        MainGUI.editMenuButtons(new ArrayList<>(Collections.singletonList(backButton)));
        backButton.setOnMouseClicked(event -> {
            try {
                FxmlController.getInstance().setSceneFxml(MenuURL.SHOP);
            } catch (IOException ignored) {}
        });
        continueButton.setOnMouseClicked(event -> {
//            if ()
        });
    }
}
