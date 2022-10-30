package components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

public class Button extends JButton {
    public Button(String text) {
        this.setBackground(new Color(232, 113, 33));
        this.setBorder(null);
        this.setForeground(Color.WHITE);
        this.setFont(new Font("Serif", Font.BOLD, 18));
        this.setText(text);
    }

}
