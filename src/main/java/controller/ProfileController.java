package controller;

public class ProfileController {

    private static ProfileController profileController;

    private ProfileController() {
    }

    public static ProfileController getInstance() {
        if (profileController == null)
            profileController = new ProfileController();
        return profileController;
    }

    private String changePassword(String password) {

    }

    private String changeNickname(String nickname) {

    }

    public String verifyOrder(String command) {

    }

    private String showCurrentMenu() {

    }
}
