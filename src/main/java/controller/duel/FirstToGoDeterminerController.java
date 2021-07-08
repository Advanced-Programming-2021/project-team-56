package controller.duel;


public class FirstToGoDeterminerController {

    private static FirstToGoDeterminerController firstToGoDeterminerController;

    private FirstToGoDeterminerController() {

    }

    public static FirstToGoDeterminerController getInstance() {
        if (firstToGoDeterminerController == null)
            firstToGoDeterminerController = new FirstToGoDeterminerController();
        return firstToGoDeterminerController;
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

}
