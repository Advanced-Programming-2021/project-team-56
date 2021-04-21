import data.ExcelUtils;
import model.MonsterCard;
import view.LoginMenuView;

import java.io.IOException;

public class Main {
    public void main(String[] args) throws IOException {
        ExcelUtils.getInstance().run();
        LoginMenuView.getInstance().run();
    }
}
