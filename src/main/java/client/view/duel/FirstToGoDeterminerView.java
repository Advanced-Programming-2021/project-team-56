package client.view.duel;

import java.util.regex.Pattern;

public class FirstToGoDeterminerView {

    private static FirstToGoDeterminerView firstToGoDeterminerView;

    private FirstToGoDeterminerView() {

    }

    public static FirstToGoDeterminerView getInstance() {
        if (firstToGoDeterminerView == null)
            firstToGoDeterminerView = new FirstToGoDeterminerView();
        return firstToGoDeterminerView;
    }

}
