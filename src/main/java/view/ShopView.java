package view;

import controller.ShopController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShopView {

    private static ShopView shopView;
    static Pattern buyCard = Pattern.compile("^shop buy ([\\S][\\S ]*)$");
    static Pattern menuEnter = Pattern.compile("^menu enter (?:Duel|Deck|Scoreboard|Profile|Shop|Import/Export)$");
    static Pattern increaseMoneyCheat = Pattern.compile("^increase --money (\\d+)$");

    private ShopView() {
    }

    public static ShopView getInstance() {
        if (shopView == null)
            shopView = new ShopView();
        return shopView;
    }

    public void run(String username) {
        while (true) {
            String command = LoginMenuView.scan.nextLine().trim();
            if (command.startsWith("card show ")) {
                System.out.println(ShopController.getInstance().checkCardShowCommand(command));
                continue;
            }
            if (command.equals("shop show --all")) {
                System.out.print(ShopController.getInstance().showAllCards());
                continue;
            }
            if (command.equals("menu exit")) {
                return;
            }
            if (command.equals("menu show-current")) {
                System.out.println("Shop Menu");
                continue;
            }
            Matcher matcher = buyCard.matcher(command);
            if (matcher.find()) {
                String result = ShopController.getInstance().buyCard(matcher.group(1), username);
                if (!result.equals("")) {
                    System.out.println(result);
                }
                continue;
            }
            matcher = increaseMoneyCheat.matcher(command);
            if (matcher.find()) {
                System.out.println(ShopController.getInstance().increaseMoney(username, matcher.group(1)));
                continue;
            }
            matcher = menuEnter.matcher(command);
            if (matcher.find()){
                System.out.println("menu navigation is not possible");
                continue;
            }
            System.out.println("invalid command");
        }
    }
}
