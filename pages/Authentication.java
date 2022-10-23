package pages;

import java.sql.*;
import java.sql.Connection;
import javax.swing.*;

import components.Frame;
import components.Panel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.EmptyBorder;

public class Authentication extends Frame {

    int screen = 0;

    JLabel title;
    JTextField emailField;
    JTextField passwordField;
    JButton loginButton;
    JButton toggleLabel;

    JLabel error;

    JCheckBox rememberMe;

    Connection connection;

    public Authentication() {
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
        JPanel formPanel = new Panel();
        formPanel.setLayout(null);
        // Title
        title = new JLabel("Welcome Back");
        title.setFont(new Font("Serif", Font.BOLD, 48));
        title.setBounds(0, 50, 600, 60);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        // Email
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font("Serif", Font.BOLD, 21));
        emailLabel.setBounds(50, 170, 500, 20);

        emailField = new JTextField(40);
        emailField.setBorder(BorderFactory.createCompoundBorder(
                emailField.getBorder(),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        emailField.setFont(new Font("Serif", Font.PLAIN, 18));
        emailField.setBounds(50, 200, 500, 50);
        // Password
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Serif", Font.BOLD, 21));
        passwordLabel.setBounds(50, 270, 500, 20);
        passwordField = new JTextField(40);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                passwordField.getBorder(),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        passwordField.setFont(new Font("Serif", Font.PLAIN, 18));
        passwordField.setBounds(50, 300, 500, 50);
        // Remember Me
        rememberMe = new JCheckBox("Keep me logged in");
        rememberMe.setFont(new Font("Serif", Font.PLAIN, 18));
        rememberMe.setBounds(350, 370, 200, 20);
        rememberMe.setOpaque(false);
        rememberMe.setFocusable(false);
        // Button
        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(232, 113, 33));
        loginButton.setBorder(null);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Serif", Font.BOLD, 18));
        loginButton.setBounds(50, 430, 500, 60);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (screen == 0)
                    login();
                else
                    register();
            }
        });
        // Toggle
        toggleLabel = new JButton("Don't have an account? Register");
        toggleLabel.setFont(new Font("Serif", Font.ITALIC, 18));
        toggleLabel.setBounds(50, 510, 500, 40);
        toggleLabel.setBackground(null);
        toggleLabel.setBorder(null);
        toggleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        toggleLabel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggle();
            }
        });
        error = new JLabel("");
        error.setFont(new Font("Serif", Font.PLAIN, 21));
        error.setForeground(Color.red);
        error.setBounds(50, 550, 500, 20);

        formPanel.add(title);
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        formPanel.add(rememberMe);
        formPanel.add(loginButton);
        formPanel.add(toggleLabel);

        panel.setBorder(new EmptyBorder(75, 0, 75, 0));
        panel.add(formPanel);

        this.add(panel);
    }

    private void toggle() {
        if (screen == 0) {
            // Switch to signup
            title.setText("Welcome, Signup");
            loginButton.setText("Register");
            toggleLabel.setText("Already have an account? Login");
            screen = 1;
        } else {
            // Switch to login
            title.setText("Welcome back");
            loginButton.setText("Login");
            toggleLabel.setText("Don't have an account? Register");
            screen = 0;
        }
    }

    private void login() {
        // Implement Login
        String sql = "select * from users where email=? and password=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, emailField.getText());
            statement.setString(2, passwordField.getText());
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                // Correct password
                JFrame home = new Home();
                home.setVisible(true);
                this.dispose();
            } else {
                // Incorrect password
                error.setText("Wrong email/password.");
            }
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    private void register() {
        // Implement Register
        String email = emailField.getText();
        String password = passwordField.getText();
        JFrame registerFrame = new Register(email, password);
        registerFrame.setVisible(true);
        this.dispose();
    }

}
