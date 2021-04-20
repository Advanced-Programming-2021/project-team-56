package view;

public class ShopView {

    private static ShopView shopView;

    private ShopView() {
    }

    public static ShopView getInstance() {
        if (shopView == null)
            shopView = new ShopView();
        return shopView;
    }

    public void run(String username) {

    }
}
