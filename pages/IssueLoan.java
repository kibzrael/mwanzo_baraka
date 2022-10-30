package pages;

import java.sql.*;
import java.util.Arrays;
import java.util.stream.IntStream;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import components.CloseableFrame;
import components.Panel;
import components.Button;

public class IssueLoan extends CloseableFrame {

    Connection connection;

    JComboBox<String> memberSelector;
    JComboBox<String> periodSelector;
    JTextField amountField;

    int maxPeriod = 3;

    public IssueLoan(JFrame back) {
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
        JLabel title = new JLabel("Loan");
        title.setFont(new Font("Serif", Font.BOLD, 36));
        title.setBounds(0, 0, 500, 60);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        String members[] = { "23343", "34545", "34545" };

        JLabel membersLabel = new JLabel("Member/Group ID");
        membersLabel.setFont(new Font("Serif", Font.BOLD, 24));
        membersLabel.setBounds(0, 100, 600, 30);
        memberSelector = new JComboBox<String>(members);
        memberSelector.setFont(new Font("Serif", Font.PLAIN, 18));
        memberSelector.setBackground(Color.white);
        memberSelector.setBounds(0, 150, 500, 50);

        // Amount
        JLabel amountLabel = new JLabel("Amount");
        amountLabel.setFont(new Font("Serif", Font.BOLD, 24));
        amountLabel.setBounds(0, 220, 600, 30);
        amountField = new JTextField(40);
        amountField.setBorder(BorderFactory.createCompoundBorder(
                amountField.getBorder(),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        amountField.setFont(new Font("Serif", Font.PLAIN, 18));
        amountField.setBounds(0, 270, 500, 50);

        int years[] = IntStream.rangeClosed(1, maxPeriod).toArray();
        String strYears[] = Arrays.stream(years)
                .mapToObj(String::valueOf)
                .toArray(String[]::new);
        JLabel periodLabel = new JLabel("Repayment Period (Years)");
        periodLabel.setFont(new Font("Serif", Font.BOLD, 24));
        periodLabel.setBounds(0, 340, 600, 30);
        periodSelector = new JComboBox<String>(strYears);
        periodSelector.setFont(new Font("Serif", Font.PLAIN, 18));
        periodSelector.setBackground(Color.white);
        periodSelector.setBounds(0, 390, 500, 50);

        JButton confirmButton = new Button("Submit Request");
        confirmButton.setBackground(new Color(232, 113, 33));
        confirmButton.setBounds(0, 460, 500, 60);
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
        contributionPanel.add(periodLabel);
        contributionPanel.add(periodSelector);
        contributionPanel.add(confirmButton);

        panel.setBorder(new EmptyBorder(75, 0, 75, 0));
        panel.add(contributionPanel);

        this.add(panel);
    }
}
