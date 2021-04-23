package view;

import controller.ShopController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShopView {

    private static ShopView shopView;
    static Pattern buyCard = Pattern.compile("^shop buy ([\\S]+)$");
    static Pattern menuEnter = Pattern.compile("^menu enter (?:Login|Duel|Deck|Scoreboard|Profile|Shop|Import\\/Export)$");

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
            if (command.equals("shop show --all")) {
                System.out.print(ShopController.getInstance().showAllCards());
                continue;
            }
            if (command.equals("menu exit")) {
                return;
            }
            if (command.equals("menu show-current")) {
                System.out.println("shop menu");
                continue;
            }
            Matcher matcher = buyCard.matcher(command);
            if (matcher.find()) {
                if (!ShopController.getInstance().buyCard(matcher.group(1), username).equals("")) {
                    System.out.println(ShopController.getInstance().buyCard(matcher.group(1), username));
                }
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
