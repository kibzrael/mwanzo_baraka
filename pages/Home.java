package pages;

import javax.swing.*;

import components.Frame;
import components.Panel;
import components.Button;

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

        // Buttons
        JButton registerButton = new Button("Register Member");
        registerButton.setBounds(0, 100, 500, 60);
        registerButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                register();
            }

        });

        JButton contributionButton = new Button("Make Contribution");
        contributionButton.setBounds(0, 190, 500, 60);
        contributionButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                contribution();
            }

        });

        JButton issueLoanButton = new Button("Issue Loan");
        issueLoanButton.setBounds(0, 280, 500, 60);
        issueLoanButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                issueLoan();
            }

        });

        JButton payLoanButton = new Button("Loan Repayment");
        payLoanButton.setBounds(0, 370, 500, 60);
        payLoanButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                loanRepayment();
            }

        });

        JButton reportsButton = new Button("View Reports");
        reportsButton.setBounds(0, 460, 500, 60);
        reportsButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                reports();
            }

        });

        homePanel.add(titleLabel);
        homePanel.add(registerButton);
        homePanel.add(contributionButton);
        homePanel.add(issueLoanButton);
        homePanel.add(payLoanButton);
        homePanel.add(reportsButton);

        panel.setBorder(new EmptyBorder(75, 0, 75, 0));
        panel.add(homePanel);

        this.add(panel);
    }

    private void register() {
        JFrame frame = new Register(this);
        frame.setVisible(true);
        this.dispose();
    }

    private void contribution() {
        JFrame frame = new MakeContribution(this);
        frame.setVisible(true);
        this.dispose();
    }

    private void issueLoan() {
        JFrame frame = new IssueLoan(this);
        frame.setVisible(true);
        this.dispose();
    }

    private void loanRepayment() {
        JFrame frame = new LoanRepayment(this);
        frame.setVisible(true);
        this.dispose();
    }

    private void reports() {
        JFrame frame = new Reports(this);
        frame.setVisible(true);
        this.dispose();
    }
}
