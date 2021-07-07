package view.duel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class DuelView {

    public TextField opponentUserName;
    public ComboBox numberOfRoundsComboBox;

    public void initialize() {
        ObservableList<Integer> items = FXCollections.observableArrayList(1, 3);
        numberOfRoundsComboBox.setItems(items);
    }
}
