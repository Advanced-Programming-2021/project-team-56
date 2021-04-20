package view;

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
        while (true) {
            String command = LoginMenuView.scan.nextLine();
            command = command.trim();
            if (command.startsWith("deck create")) {
                createDeck(username);
            }
        }
    }
}
