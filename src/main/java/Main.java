import controller.LoginMenuController;
import view.LoginMenuView;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        ExcelUtils.getInstance().run();
        LoginMenuController.getInstance().readFromJson();
        LoginMenuView.getInstance().run();
        LoginMenuController.getInstance().updateJson();
    }
}