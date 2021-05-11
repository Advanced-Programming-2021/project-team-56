package view.duel;

public class DuelWithUserView {

    private static DuelWithUserView duelWithUserView;

    private DuelWithUserView() {

    }

    public static DuelWithUserView getInstance() {
        if (duelWithUserView == null)
            duelWithUserView = new DuelWithUserView();
        return duelWithUserView;
    }

    public void printEndMessage(String output) {
        System.out.println(output);
    }
}
