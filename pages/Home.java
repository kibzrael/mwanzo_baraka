package pages;

import javax.swing.*;

import components.Frame;
import components.Panel;

import java.awt.*;

import javax.swing.border.EmptyBorder;

public class Home extends Frame {

    public Home() {
        setup();
    }

    private void setup() {

        JPanel panel = new Panel();
        panel.setLayout(new GridLayout(1, 2, 50, 0));

        // Logo
        JLabel logo = new JLabel();
        logo.setIcon(new ImageIcon("images/logo-sm.png"));
        logo.setHorizontalAlignment(JLabel.CENTER);
        panel.add(logo);

        panel.setBorder(new EmptyBorder(75, 0, 75, 0));

        this.add(panel);
    }
}
