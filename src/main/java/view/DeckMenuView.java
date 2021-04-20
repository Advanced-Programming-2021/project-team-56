package view;
import model.User;

public class DeckMenuView {

    private static DeckMenuView deckMenuView;

    private DeckMenuView() {
    }

    public static DeckMenuView getInstance() {
        if (deckMenuView == null)
            deckMenuView = new DeckMenuView();
        return deckMenuView;
    }
    public void run(String username) {

    }
}
