package controller;

public class ShopController {

    private static ShopController shopController;

    private ShopController() {
    }

    public static ShopController getInstance() {
        if (shopController == null)
            shopController = new ShopController();
        return shopController;
    }

    private String showAllCards() {

    }

    private String buyCard(String cardName) {

    }
}
