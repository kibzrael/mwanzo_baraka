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

public class Reports extends CloseableFrame {

    Connection connection;

    JLabel totalIncomeLabel;
    JLabel orgIncomeLabel;

    public Reports(JFrame back) {
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
        JLabel title = new JLabel("Reports");
        title.setFont(new Font("Serif", Font.BOLD, 36));
        title.setBounds(0, 50, 500, 60);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        totalIncomeLabel = new JLabel("Total Income: ");
        totalIncomeLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        totalIncomeLabel.setBounds(0, 150, 600, 30);

        orgIncomeLabel = new JLabel("Organization Income: ");
        orgIncomeLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        orgIncomeLabel.setBounds(0, 200, 600, 30);

        JButton membersButton = new Button("Members");
        membersButton.setBounds(0, 270, 225, 50);
        JButton groupsButton = new Button("Groups");
        groupsButton.setBounds(250, 270, 225, 50);

        JButton contributionsButton = new Button("Contributions");
        contributionsButton.setBounds(0, 340, 225, 50);
        JButton loansButton = new Button("Loans");
        loansButton.setBounds(250, 340, 225, 50);

        JButton repaymentsButtion = new Button("Repayments");
        repaymentsButtion.setBounds(0, 410, 225, 50);
        JButton dividendsButton = new Button("Dividends");
        dividendsButton.setBounds(250, 410, 225, 50);

        contributionPanel.add(title);
        contributionPanel.add(totalIncomeLabel);
        contributionPanel.add(orgIncomeLabel);
        contributionPanel.add(membersButton);
        contributionPanel.add(groupsButton);
        contributionPanel.add(contributionsButton);
        contributionPanel.add(loansButton);
        contributionPanel.add(repaymentsButtion);
        contributionPanel.add(dividendsButton);

        panel.setBorder(new EmptyBorder(75, 0, 75, 0));
        panel.add(contributionPanel);

        this.add(panel);
    }
}
