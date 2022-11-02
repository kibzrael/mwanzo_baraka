package pages;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import components.Frame;
import components.Panel;
import components.Button;

public class LoanDetails extends Frame {

    public LoanDetails(String loan_id, String due_next, String amount) {
        setup(loan_id, due_next, amount);
    }

    private void setup(String loan_id, String due_next, String amount) {
        JPanel panel = new Panel();
        panel.setLayout(new GridLayout(1, 2, 50, 0));

        // Logo
        JLabel logo = new JLabel();
        logo.setIcon(new ImageIcon("images/logo-sm.png"));
        logo.setHorizontalAlignment(JLabel.CENTER);
        panel.add(logo);

        JPanel loanDetailsPanel = new Panel();
        loanDetailsPanel.setLayout(null);

        JLabel title = new JLabel("Loan Details");
        title.setFont(new Font("Serif", Font.BOLD, 36));
        title.setBounds(0, 50, 500, 60);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel description = new JLabel("Congratulations, your loan has been Approved");
        description.setFont(new Font("Serif", Font.PLAIN, 24));
        description.setBounds(0, 150, 600, 30);
        description.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel idLabel = new JLabel("<html>Loan ID: <b>" + loan_id + "</b></html>");
        idLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        idLabel.setBounds(0, 220, 600, 30);

        JLabel dueLabel = new JLabel("<html>Next Payment: <b>" + due_next + "</b></html>");
        dueLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        dueLabel.setBounds(0, 270, 600, 30);

        JLabel amountLabel = new JLabel("<html>Installment Amount: <b>" + amount + "</b></html>");
        amountLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        amountLabel.setBounds(0, 320, 600, 30);

        JButton confirmButton = new Button("Confirm Details");
        confirmButton.setBackground(new Color(232, 113, 33));
        confirmButton.setBounds(0, 400, 500, 60);
        confirmButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                home();
            }
        });

        loanDetailsPanel.add(title);
        loanDetailsPanel.add(description);
        loanDetailsPanel.add(idLabel);
        loanDetailsPanel.add(dueLabel);
        loanDetailsPanel.add(amountLabel);
        loanDetailsPanel.add(confirmButton);

        panel.setBorder(new EmptyBorder(75, 0, 75, 0));
        panel.add(loanDetailsPanel);

        this.add(panel);
    }

    private void home() {
        new Home().setVisible(true);
        this.dispose();
    }
}
