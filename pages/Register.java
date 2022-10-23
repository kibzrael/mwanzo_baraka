package pages;

import javax.swing.*;

import components.Frame;
import components.Panel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.border.EmptyBorder;

public class Register extends Frame {

    JLabel regFee;

    String amount;

    JTextField memberField;

    String email;
    String password;
    String type;

    public Register(String em, String pass) {
        email = em;
        password = pass;
        setup();
    }

    private void setup() {

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
            if (button.isSelected()) {
                type = button.getText();
                if (type == "Group") {
                    amount = "5000";

                } else if (type == "Individual") {
                    amount = "2000";
                }
                regFee = new JLabel("Ksh. " + amount);

            }
        }
        // Members
        JLabel membersLabel = new JLabel("Group Name");
        membersLabel.setFont(new Font("Serif", Font.BOLD, 24));
        membersLabel.setBounds(0, 200, 600, 30);
        memberField = new JTextField(40);
        memberField.setBorder(BorderFactory.createCompoundBorder(
                memberField.getBorder(),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        memberField.setFont(new Font("Serif", Font.PLAIN, 18));
        memberField.setBounds(0, 250, 500, 50);
        // Registration fee
        JLabel regFeeLabel = new JLabel("Registration Fee:");
        regFeeLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        regFeeLabel.setBounds(0, 350, 250, 30);
        regFee = new JLabel("Ksh. " + amount);
        regFee.setFont(new Font("Serif", Font.BOLD, 24));
        regFee.setBounds(250, 350, 200, 30);
        // Button
        JButton confirmButton = new JButton("Confirm Payment");
        confirmButton.setBackground(new Color(232, 113, 33));
        confirmButton.setBorder(null);
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFont(new Font("Serif", Font.BOLD, 18));
        confirmButton.setBounds(0, 430, 500, 60);
        confirmButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                details();

            }
        });

        registerPanel.add(membershipLabel);
        registerPanel.add(group);
        registerPanel.add(individual);
        registerPanel.add(membersLabel);
        registerPanel.add(memberField);
        registerPanel.add(regFeeLabel);
        registerPanel.add(regFee);
        registerPanel.add(confirmButton);

        panel.setBorder(new EmptyBorder(75, 0, 75, 0));
        panel.add(registerPanel);

        this.add(panel);
    }

    private void details() {
        JFrame details = new Details(email, password, type == "Group");
        details.setVisible(true);
        this.dispose();
    }

}
