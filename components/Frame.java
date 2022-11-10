package components;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.*;

public class Frame extends JFrame {
    // A frame with the common attributes
    public Frame() {
        this.setTitle("Mwanzo Baraka");
        this.setSize(1250, 750);
        this.getContentPane().setBackground(Color.WHITE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        ImageIcon image = new ImageIcon("images/favicon.png");
        this.setIconImage(image.getImage());
    }
}
