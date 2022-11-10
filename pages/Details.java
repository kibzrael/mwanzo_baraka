package pages;

import java.sql.*;
import java.util.Enumeration;

import javax.swing.*;

import components.Frame;
import components.Panel;
import components.Button;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.EmptyBorder;

public class Details extends Frame {

    Connection connection;

    JTextField idField;
    JTextField memberField;
    JTextField phoneField;
    String gender;

    JLabel error;

    Boolean group;
    String groupName;

    public Details(Boolean gr, String name) {
        group = gr;
        groupName = name;
        setup();
    }

    private void setup() {
        // Mysql connection
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
        JPanel detailsPanel = new Panel();
        detailsPanel.setLayout(null);

        // Member
        JLabel idLabel = new JLabel("National ID");
        idLabel.setFont(new Font("Serif", Font.BOLD, 24));
        idLabel.setBounds(0, 0, 600, 30);
        idField = new JTextField(40);
        idField.setBorder(BorderFactory.createCompoundBorder(
                idField.getBorder(),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        idField.setFont(new Font("Serif", Font.PLAIN, 18));
        idField.setBounds(0, 50, 500, 50);

        JLabel membersLabel = new JLabel("Member Name");
        membersLabel.setFont(new Font("Serif", Font.BOLD, 24));
        membersLabel.setBounds(0, 120, 600, 30);
        memberField = new JTextField(40);
        memberField.setBorder(BorderFactory.createCompoundBorder(
                memberField.getBorder(),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        memberField.setFont(new Font("Serif", Font.PLAIN, 18));
        memberField.setBounds(0, 170, 500, 50);

        // PhoneNumber
        JLabel phoneLabel = new JLabel("Phone Number");
        phoneLabel.setFont(new Font("Serif", Font.BOLD, 24));
        phoneLabel.setBounds(0, 240, 600, 30);
        phoneField = new JTextField(40);
        phoneField.setBorder(BorderFactory.createCompoundBorder(
                phoneField.getBorder(),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        phoneField.setFont(new Font("Serif", Font.PLAIN, 18));
        phoneField.setBounds(0, 290, 500, 50);

        // Gender
        JLabel genderLabel = new JLabel("Gender");
        genderLabel.setFont(new Font("Serif", Font.BOLD, 24));
        genderLabel.setBounds(0, 360, 600, 50);
        JRadioButton male = new JRadioButton("Male");
        male.setFont(new Font("Serif", Font.BOLD, 18));
        male.setHorizontalAlignment(SwingConstants.CENTER);
        male.setFocusable(false);
        male.setBounds(0, 410, 200, 50);
        JRadioButton female = new JRadioButton("Female");
        female.setFont(new Font("Serif", Font.BOLD, 18));
        female.setHorizontalAlignment(SwingConstants.CENTER);
        female.setFocusable(false);
        female.setBounds(250, 410, 200, 50);
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(male);
        buttonGroup.add(female);
        // Handler function for when gender is changed
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            button.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    if (button.isSelected()) {
                        gender = button.getText();
                        System.out.println(gender);

                    }
                }
            });

        }
        // Button
        int buttonWidth = 200;
        if (!group) {
            buttonWidth = 500;
        }
        JButton submitButton = new Button("Submit");
        submitButton.setBounds(0, 480, buttonWidth, 60);
        JButton addButton = new Button("Add member");
        addButton.setBounds(250, 480, 200, 60);
        submitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int response = insert();
                if (response == 0)
                    home();
            }

        });
        addButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int response = insert();
                if (response == 0) {
                    idField.setText("");
                    memberField.setText("");
                    phoneField.setText("");
                    buttonGroup.clearSelection();
                }
            }

        });

        // Display in case of an error
        error = new JLabel("");
        error.setFont(new Font("Serif", Font.PLAIN, 21));
        error.setForeground(Color.red);
        error.setBounds(0, 560, 500, 20);
        error.setHorizontalAlignment(SwingConstants.CENTER);

        detailsPanel.add(idLabel);
        detailsPanel.add(idField);
        detailsPanel.add(membersLabel);
        detailsPanel.add(memberField);
        detailsPanel.add(phoneLabel);
        detailsPanel.add(phoneField);
        detailsPanel.add(genderLabel);
        detailsPanel.add(male);
        detailsPanel.add(female);
        detailsPanel.add(submitButton);
        if (group) {
            detailsPanel.add(addButton);
        }
        detailsPanel.add(error);

        panel.setBorder(new EmptyBorder(50, 0, 50, 0));
        panel.add(detailsPanel);

        this.add(panel);
    }

    private int insert() {
        if (gender == null || idField.getText() == "" || memberField.getText() == "" || phoneField.getText() == "") {
            error.setText("Please complete the form");
            return 1;
        }
        String sql = "insert into members(national_id, name, phone, gender, reg_fee, group_name) values (?,?,?,?,?,?)";
        try {
            // Register member in database
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, idField.getText());
            statement.setString(2, memberField.getText());
            statement.setString(3, phoneField.getText());
            statement.setString(4, gender);
            if (group) {
                statement.setNull(5, 0);
                statement.setString(6, groupName);
            } else {
                statement.setInt(5, 2000);
                statement.setNull(6, 0);
            }
            statement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            error.setText("National ID is already registered");
            return 1;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        error.setText("");
        return 0;
    }

    private void home() {
        JFrame home = new Home();
        home.setVisible(true);
        this.dispose();
    }
}
