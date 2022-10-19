package pages;

import java.sql.*;
import javax.swing.*;

import components.Frame;
import components.Panel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.EmptyBorder;

public class Details extends Frame {

    Connection connection;

    JTextField memberField;
    JTextField phoneField;

    String email;
    String password;

    public Details(String em, String pass) {
        email = em;
        password = pass;
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

        // Members
        JLabel membersLabel = new JLabel("Member Name");
        membersLabel.setFont(new Font("Serif", Font.BOLD, 24));
        membersLabel.setBounds(0, 50, 600, 30);
        memberField = new JTextField(40);
        memberField.setBorder(BorderFactory.createCompoundBorder(
                memberField.getBorder(),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        memberField.setFont(new Font("Serif", Font.PLAIN, 18));
        memberField.setBounds(0, 100, 500, 50);
        // PhoneNumber
        JLabel phoneLabel = new JLabel("Phone Number");
        phoneLabel.setFont(new Font("Serif", Font.BOLD, 24));
        phoneLabel.setBounds(0, 170, 600, 30);
        phoneField = new JTextField(40);
        phoneField.setBorder(BorderFactory.createCompoundBorder(
                phoneField.getBorder(),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        phoneField.setFont(new Font("Serif", Font.PLAIN, 18));
        phoneField.setBounds(0, 220, 500, 50);
        // Gender
        JLabel genderLabel = new JLabel("Gender");
        genderLabel.setFont(new Font("Serif", Font.BOLD, 24));
        genderLabel.setBounds(0, 320, 600, 50);
        JRadioButton male = new JRadioButton("Male");
        male.setFont(new Font("Serif", Font.BOLD, 18));
        male.setHorizontalAlignment(SwingConstants.CENTER);
        male.setFocusable(false);
        male.setBounds(0, 390, 200, 50);
        JRadioButton female = new JRadioButton("Female");
        female.setFont(new Font("Serif", Font.BOLD, 18));
        female.setHorizontalAlignment(SwingConstants.CENTER);
        female.setFocusable(false);
        female.setBounds(250, 390, 200, 50);
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(male);
        buttonGroup.add(female);
        // Button
        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(new Color(232, 113, 33));
        submitButton.setBorder(null);
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Serif", Font.BOLD, 18));
        submitButton.setBounds(0, 490, 500, 60);
        submitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String sql = "insert into users(email, password, name, phone) values (?,?,?,?)";
                try {
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, email);
                    statement.setString(2, password);
                    statement.setString(3, memberField.getText());
                    statement.setString(4, phoneField.getText());
                    statement.executeUpdate();
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }

        });

        registerPanel.add(membersLabel);
        registerPanel.add(memberField);
        registerPanel.add(phoneLabel);
        registerPanel.add(phoneField);
        registerPanel.add(genderLabel);
        registerPanel.add(male);
        registerPanel.add(female);
        registerPanel.add(submitButton);

        panel.setBorder(new EmptyBorder(75, 0, 75, 0));
        panel.add(registerPanel);

        this.add(panel);
    }
}
