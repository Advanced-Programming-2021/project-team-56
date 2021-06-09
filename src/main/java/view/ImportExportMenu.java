package view;

import controller.ShopController;
import view.duel.phase.Output;

import java.util.regex.Matcher;

public class ImportExportMenu {
    private static ImportExportMenu importExportMenu;

    private ImportExportMenu() {

    }

    public static ImportExportMenu getInstance() {
        if (importExportMenu == null) {
            importExportMenu = new ImportExportMenu();
        }
        return importExportMenu;
    }

    public void run(){
        while (true) {
            String command = LoginMenuView.scan.nextLine().trim();
            if (command.equals("menu exit")) {
                return;
            }
            if (command.equals("menu show-current")) {
                System.out.println("Import/Export menu");
                continue;
            }
            System.out.println(Output.InvalidCommand);
        }
    }
}
