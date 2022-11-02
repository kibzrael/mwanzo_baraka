package pages;

import javax.swing.*;

import components.Panel;
import components.Button;
import components.CloseableFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Enumeration;

import javax.swing.border.EmptyBorder;

public class Register extends CloseableFrame {

    Connection connection;

    JLabel regFee;

    String amount = "2000";

    JTextField groupField;

    String type;

    JLabel groupLabel;

    JLabel error;

    public Register(JFrame back) {
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
        JPanel registerPanel = new Panel();
        registerPanel.setLayout(null);
        // Membership
        JLabel membershipLabel = new JLabel("Membership");
        membershipLabel.setFont(new Font("Serif", Font.BOLD, 24));
        membershipLabel.setBounds(0, 50, 600, 50);
        JRadioButton group = new JRadioButton("Group");
        group.setFont(new Font("Serif", Font.BOLD, 18));
        group.setHorizontalAlignment(SwingConstants.CENTER);
        group.setFocusable(false);
        group.setBounds(0, 120, 200, 50);
        JRadioButton individual = new JRadioButton("Individual");
        individual.setFont(new Font("Serif", Font.BOLD, 18));
        individual.setHorizontalAlignment(SwingConstants.CENTER);
        individual.setFocusable(false);
        individual.setBounds(250, 120, 200, 50);
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(individual);
        buttonGroup.add(group);
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            button.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    if (button.isSelected()) {
                        type = button.getText();
                        if (type == "Group") {
                            amount = "5000";
                            groupField.setEnabled(true);

                        } else if (type == "Individual") {
                            amount = "2000";
                            groupField.setEnabled(false);
                        }
                        regFee.setText("Ksh. " + amount);

                    }
                }
            });

        }

        groupLabel = new JLabel("Group Name");
        groupLabel.setFont(new Font("Serif", Font.BOLD, 24));
        groupLabel.setBounds(0, 200, 600, 30);
        groupField = new JTextField(40);
        groupField.setBorder(BorderFactory.createCompoundBorder(
                groupField.getBorder(),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        groupField.setFont(new Font("Serif", Font.PLAIN, 18));
        groupField.setBounds(0, 250, 500, 50);
        // Registration fee
        JLabel regFeeLabel = new JLabel("Registration Fee:");
        regFeeLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        regFeeLabel.setBounds(0, 350, 250, 30);
        regFee = new JLabel("Ksh. " + amount);
        regFee.setFont(new Font("Serif", Font.BOLD, 24));
        regFee.setBounds(250, 350, 200, 30);
        // Button
        JButton confirmButton = new Button("Confirm Payment");
        confirmButton.setBackground(new Color(232, 113, 33));
        confirmButton.setBounds(0, 430, 500, 60);
        confirmButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                details();

            }
        });
        error = new JLabel("");
        error.setFont(new Font("Serif", Font.PLAIN, 21));
        error.setForeground(Color.red);
        error.setBounds(0, 510, 500, 20);
        error.setHorizontalAlignment(SwingConstants.CENTER);

        registerPanel.add(membershipLabel);
        registerPanel.add(group);
        registerPanel.add(individual);
        registerPanel.add(groupLabel);
        registerPanel.add(groupField);
        registerPanel.add(regFeeLabel);
        registerPanel.add(regFee);
        registerPanel.add(confirmButton);
        registerPanel.add(error);

        panel.setBorder(new EmptyBorder(75, 0, 75, 0));
        panel.add(registerPanel);

        this.add(panel);
    }

    private void details() {
        if (type == null || groupField.getText() == "") {
            error.setText("Please complete the form");
            return;
        }
        if (type == "Group") {
            String sql = "INSERT INTO member_groups (name, reg_fee) VALUES (?, ?)";
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, groupField.getText());
                statement.setInt(2, Integer.parseInt(amount));
                statement.executeUpdate();
            } catch (SQLIntegrityConstraintViolationException e) {
                error.setText("Group Name is already taken");
                return;
            }

            catch (SQLException e1) {
                e1.printStackTrace();
            } catch (java.lang.NullPointerException e) {
            }
        }
        // TODO: Ensure group name is unique

        JFrame details = new Details(type == "Group", groupField.getText());
        details.setVisible(true);
        this.dispose();
    }

}
