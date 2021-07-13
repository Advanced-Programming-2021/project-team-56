import controller.LoginMenuController;
import controller.SoundPlayer;
import view.MainGUI;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        ExcelUtils.getInstance().run();
        LoginMenuController.getInstance().readFromJson();
        MainGUI.main(args);
    }
}