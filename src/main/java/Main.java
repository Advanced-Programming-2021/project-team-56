import view.LoginMenuView;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        ExcelUtils.getInstance().run();
        LoginMenuView.getInstance().run();
    }
}