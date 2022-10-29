package pages;

import java.sql.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

import components.Frame;
import components.Panel;

public class MakeContribution extends Frame {

    Connection connection;

    JComboBox<String> memberSelector;

    public MakeContribution() {
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
        String members[] = { "23343", "34545", "34545" };

        JLabel membersLabel = new JLabel("Member National ID");
        membersLabel.setFont(new Font("Serif", Font.BOLD, 24));
        membersLabel.setBounds(0, 50, 600, 30);
        memberSelector = new JComboBox<String>(members);
        memberSelector.setBorder(BorderFactory.createCompoundBorder(
                memberSelector.getBorder(),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        memberSelector.setFont(new Font("Serif", Font.PLAIN, 18));
        memberSelector.setBounds(0, 100, 500, 50);

        contributionPanel.add(membersLabel);
        contributionPanel.add(memberSelector);

        panel.setBorder(new EmptyBorder(75, 0, 75, 0));
        panel.add(contributionPanel);

        this.add(panel);
    }
}
