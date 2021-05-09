package controller.duel;

import java.util.ArrayList;
import java.util.Collections;

public class FirstToGoDeterminerController {

    private static FirstToGoDeterminerController firstToGoDeterminerController;

    private FirstToGoDeterminerController() {

    }

    public static FirstToGoDeterminerController getInstance() {
        if (firstToGoDeterminerController == null)
            firstToGoDeterminerController = new FirstToGoDeterminerController();
        return firstToGoDeterminerController;
    }

    public String getRandomMiniGameName() {
        ArrayList<String> miniGameNames = new ArrayList<>();
        miniGameNames.add("RockPaperScissors");
        miniGameNames.add("CoinToss");
        Collections.shuffle(miniGameNames);
        return miniGameNames.get(0);
    }

    public String getWinnerCommand(String firstPlayerCommand, String secondPlayerCommand) {
        if (firstPlayerCommand.equals("rock")) {
            if (secondPlayerCommand.equals("scissors")) {
                return firstPlayerCommand;
            }
            return secondPlayerCommand;
        }
        if (firstPlayerCommand.equals("paper")) {
            if (secondPlayerCommand.equals("rock")) {
                return firstPlayerCommand;
            }
            return secondPlayerCommand;
        }
        if (secondPlayerCommand.equals("paper")) {
            return firstPlayerCommand;
        }
        return secondPlayerCommand;
    }

    public String getCoinTossWinnerCommand() {
        ArrayList<String> coinTossPossibilities = new ArrayList<>();
        coinTossPossibilities.add("heads");
        coinTossPossibilities.add("tails");
        Collections.shuffle(coinTossPossibilities);
        return coinTossPossibilities.get(0);
    }
}
