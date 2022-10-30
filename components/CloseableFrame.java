package components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class CloseableFrame extends Frame {

    JFrame previousFrame;

    public CloseableFrame(JFrame back) {
        previousFrame = back;
        JButton backButton = new JButton();
        backButton.setIcon(new ImageIcon("images/arrow_back.png"));
        backButton.setBackground(null);
        backButton.setFocusable(false);
        backButton.setBorder(null);
        backButton.setBounds(25, 50, 60, 40);
        backButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                close();
            }

        });
        this.add(backButton);
    }

    protected void close() {
        previousFrame.setVisible(true);
        this.dispose();
    }
}
