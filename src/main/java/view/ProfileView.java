package view;

import model.User;

public class ProfileView {

    private static ProfileView profileView;

    private ProfileView() {
    }

    public static ProfileView getInstance() {
        if (profileView == null)
            profileView = new ProfileView();
        return profileView;
    }

    public void run(User user) {

    }
}
