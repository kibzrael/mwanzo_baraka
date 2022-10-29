package pages;

import javax.swing.*;

import components.Frame;
import components.Panel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.EmptyBorder;

public class Home extends Frame {

    public Home() {
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
        JPanel homePanel = new Panel();
        homePanel.setLayout(null);

        // Members
        JLabel titleLabel = new JLabel("Home");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 36));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBounds(0, 0, 500, 50);
        // Button
        JButton contributionButton = new JButton("Make Contribution");
        contributionButton.setBackground(new Color(232, 113, 33));
        contributionButton.setBorder(null);
        contributionButton.setForeground(Color.WHITE);
        contributionButton.setFont(new Font("Serif", Font.BOLD, 18));
        contributionButton.setBounds(0, 100, 500, 60);
        contributionButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                contribution();
            }

        });

        JButton takeLoanButton = new JButton("Take Loan");
        takeLoanButton.setBackground(new Color(232, 113, 33));
        takeLoanButton.setBorder(null);
        takeLoanButton.setForeground(Color.WHITE);
        takeLoanButton.setFont(new Font("Serif", Font.BOLD, 18));
        takeLoanButton.setBounds(0, 200, 500, 60);

        JButton payLoanButton = new JButton("Repay Loan");
        payLoanButton.setBackground(new Color(232, 113, 33));
        payLoanButton.setBorder(null);
        payLoanButton.setForeground(Color.WHITE);
        payLoanButton.setFont(new Font("Serif", Font.BOLD, 18));
        payLoanButton.setBounds(0, 300, 500, 60);

        JButton reportsButton = new JButton("View Reports");
        reportsButton.setBackground(new Color(232, 113, 33));
        reportsButton.setBorder(null);
        reportsButton.setForeground(Color.WHITE);
        reportsButton.setFont(new Font("Serif", Font.BOLD, 18));
        reportsButton.setBounds(0, 400, 500, 60);

        homePanel.add(titleLabel);
        homePanel.add(contributionButton);
        homePanel.add(takeLoanButton);
        homePanel.add(payLoanButton);
        homePanel.add(reportsButton);

        panel.setBorder(new EmptyBorder(75, 0, 75, 0));
        panel.add(homePanel);

        this.add(panel);
    }

    private void contribution() {
        JFrame MakeContribution = new MakeContribution();
        MakeContribution.setVisible(true);
        this.dispose();
    }
}
