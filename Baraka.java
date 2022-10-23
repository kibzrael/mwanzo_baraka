import javax.swing.*;

import pages.Authentication;

public class Baraka {

    public static void main(String[] args) {

        // Fetch Login Status from db
        Boolean loggedIn = false;

        if (loggedIn) {
            // Open Home
        } else {
            // Open Login Page
            JFrame frame = new Authentication();
            // JFrame frame = new Register();
            frame.setVisible(true);
        }
    }
}
