package pages;

import java.sql.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import components.CloseableFrame;
import components.Panel;
import components.Button;

public class MakeContribution extends CloseableFrame {

    Connection connection;

    JComboBox<String> memberSelector;
    JTextField amountField;

    public MakeContribution(JFrame back) {
        super(back);
        setup();
    }

    private void setup() {
        String dbUrl = "jdbc:mysql://localhost:3306/mwanzo_baraka";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(dbUrl, "root",
                    "kibzrael");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        JPanel panel = new Panel();
        panel.setLayout(new GridLayout(1, 2, 50, 0));

        // Logo
        JLabel logo = new JLabel();
        logo.setIcon(new ImageIcon("images/logo-sm.png"));
        logo.setHorizontalAlignment(JLabel.CENTER);
        panel.add(logo);

        // Form
        JPanel contributionPanel = new Panel();
        contributionPanel.setLayout(null);

        // Members
        JLabel title = new JLabel("Contributions");
        title.setFont(new Font("Serif", Font.BOLD, 36));
        title.setBounds(0, 50, 500, 60);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        String members[] = { "23343", "34545", "34545" };

        JLabel membersLabel = new JLabel("Member National ID");
        membersLabel.setFont(new Font("Serif", Font.BOLD, 24));
        membersLabel.setBounds(0, 150, 600, 30);
        memberSelector = new JComboBox<String>(members);
        memberSelector.setFont(new Font("Serif", Font.PLAIN, 18));
        memberSelector.setBackground(Color.white);
        memberSelector.setBounds(0, 200, 500, 50);

        // Amount
        JLabel amountLabel = new JLabel("Amount");
        amountLabel.setFont(new Font("Serif", Font.BOLD, 24));
        amountLabel.setBounds(0, 270, 600, 30);
        amountField = new JTextField(40);
        amountField.setBorder(BorderFactory.createCompoundBorder(
                amountField.getBorder(),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        amountField.setFont(new Font("Serif", Font.PLAIN, 18));
        amountField.setBounds(0, 320, 500, 50);

        JButton confirmButton = new Button("Confirm Payment");
        confirmButton.setBackground(new Color(232, 113, 33));
        confirmButton.setBounds(0, 430, 500, 60);
        confirmButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });
        contributionPanel.add(title);
        contributionPanel.add(membersLabel);
        contributionPanel.add(memberSelector);
        contributionPanel.add(amountLabel);
        contributionPanel.add(amountField);
        contributionPanel.add(confirmButton);

        panel.setBorder(new EmptyBorder(75, 0, 75, 0));
        panel.add(contributionPanel);

        this.add(panel);
    }
}
