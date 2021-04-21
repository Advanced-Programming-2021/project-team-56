import data.ExcelUtils;
import view.LoginMenuView;

import java.io.IOException;

public class Main {
    public void main(String[] args) throws IOException {
        ExcelUtils.getInstance().getRowCount();
        LoginMenuView.getInstance().run();
    }
}
