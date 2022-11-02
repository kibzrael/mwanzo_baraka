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

        //
        JLabel title = new JLabel("Reports");
        title.setFont(new Font("Serif", Font.BOLD, 36));
        title.setBounds(0, 50, 500, 60);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        int income = getIncome();
        double orgIncome = income * 0.6;

        totalIncomeLabel = new JLabel("<html>Total Income: <b>sh. " + String.valueOf(income) + "</b></html>");
        totalIncomeLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        totalIncomeLabel.setBounds(0, 150, 600, 30);

        orgIncomeLabel = new JLabel("<html>Organization Income: <b>sh. " + String.valueOf(orgIncome) + "</b></html>");
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

        JButton repaymentsButton = new Button("Repayments");
        repaymentsButton.setBounds(0, 410, 225, 50);
        JButton dividendsButton = new Button("Dividends");
        dividendsButton.setBounds(250, 410, 225, 50);

        JButton buttons[] = { membersButton, groupsButton, contributionsButton, loansButton, repaymentsButton,
                dividendsButton };
        for (JButton button : buttons) {
            button.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    System.out.println(button.getText());
                    String sql;
                    switch (button.getText()) {
                        case "Members":
                            sql = "SELECT national_id,name,phone,gender,reg_fee FROM members;";
                            break;
                        case "Groups":
                            sql = "SELECT name,reg_fee FROM member_groups;";
                            break;
                        case "Contributions":
                            sql = "SELECT member_id,group_name,amount,time FROM contributions";
                            break;
                        case "Loans":
                            sql = "SELECT * FROM loans";
                            break;
                        case "Repayments":
                            sql = "SELECT loan_id,installment,amount,interest,penalty,due FROM payments WHERE cleared";
                            break;
                        case "Dividends":
                            sql = "";
                            break;
                        default:
                            break;
                    }
                }
            });
        }

        contributionPanel.add(title);
        contributionPanel.add(totalIncomeLabel);
        contributionPanel.add(orgIncomeLabel);
        contributionPanel.add(membersButton);
        contributionPanel.add(groupsButton);
        contributionPanel.add(contributionsButton);
        contributionPanel.add(loansButton);
        contributionPanel.add(repaymentsButton);
        contributionPanel.add(dividendsButton);

        panel.setBorder(new EmptyBorder(75, 0, 75, 0));
        panel.add(contributionPanel);

        this.add(panel);
    }

    private int getIncome() {
        String sql = "SELECT (SUM(interest)+SUM(penalty)) AS income FROM mwanzo_baraka.payments WHERE cleared";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getInt("income");
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return 0;
    }
}
