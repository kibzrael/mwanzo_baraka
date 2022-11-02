package pages;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import components.CloseableFrame;
import components.Panel;
import components.Button;

public class LoanRepayment extends CloseableFrame {

    Connection connection;

    JComboBox<String> loanSelector;
    String[] loans;

    int installment;
    int amount;
    int penalty = 0;

    JLabel amountLabel;
    JLabel installmentLabel;
    JLabel penaltyLabel;

    public LoanRepayment(JFrame back) {
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
        JPanel repaymentPanel = new Panel();
        repaymentPanel.setLayout(null);

        // Members
        JLabel title = new JLabel("Loan Repayment");
        title.setFont(new Font("Serif", Font.BOLD, 36));
        title.setBounds(0, 50, 500, 60);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        loans = fetchLoans();

        JLabel loansLabel = new JLabel("Loan ID");
        loansLabel.setFont(new Font("Serif", Font.BOLD, 24));
        loansLabel.setBounds(0, 150, 600, 30);
        loanSelector = new JComboBox<String>(loans);
        loanSelector.setFont(new Font("Serif", Font.PLAIN, 18));
        loanSelector.setBackground(Color.white);
        loanSelector.setBounds(0, 200, 500, 50);
        loanSelector.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                loanDetails();
            }
        });

        // installment
        installmentLabel = new JLabel("Installment:  Due on: ");
        installmentLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        installmentLabel.setBounds(0, 270, 600, 30);

        // Amount
        amountLabel = new JLabel("Amount: ");
        amountLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        amountLabel.setBounds(0, 320, 600, 30);

        // penalty
        penaltyLabel = new JLabel("<html>Penalty: <b>N/A</b></html>");
        penaltyLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        penaltyLabel.setBounds(0, 370, 600, 30);

        JButton confirmButton = new Button("Confirm Payment");
        confirmButton.setBackground(new Color(232, 113, 33));
        confirmButton.setBounds(0, 430, 500, 60);
        confirmButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                confirm();
            }
        });

        repaymentPanel.add(title);
        repaymentPanel.add(loansLabel);
        repaymentPanel.add(loanSelector);
        repaymentPanel.add(installmentLabel);
        repaymentPanel.add(amountLabel);
        repaymentPanel.add(penaltyLabel);
        repaymentPanel.add(confirmButton);

        panel.setBorder(new EmptyBorder(75, 0, 75, 0));
        panel.add(repaymentPanel);

        this.add(panel);
        loanDetails();
    }

    private String[] fetchLoans() {
        String loans[] = {};
        String sql = "select id from loans";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                List<String> idList = new ArrayList<String>(
                        Arrays.asList(loans));
                idList.add(String.valueOf(result.getInt("id")));
                loans = idList.toArray(loans);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return loans;
    }

    private void loanDetails() {
        String sql = "SELECT * FROM payments WHERE loan_id=? AND NOT cleared ORDER BY due ASC LIMIT 1";
        try {
            int loan_id = Integer.parseInt(loans[loanSelector.getSelectedIndex()]);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, loan_id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                installment = result.getInt("installment");
                amount = result.getInt("amount");
                int interest = result.getInt("interest");
                String due = result.getString("due");
                installmentLabel.setText("<html>Installment: <b>" + String.valueOf(installment) + "</b>  Due on: <b>"
                        + due + "</b></html>");
                amountLabel.setText("<html>Amount: <b>" + String.valueOf(amount + interest) + "</b></html>");
                if (LocalDate.now().isAfter(LocalDate.parse(due))) {
                    Double pen = amount * 0.1;
                    penalty = pen.intValue();
                    penaltyLabel.setText("<html>Penalty: <b>" + String.valueOf(penalty) + "</b></html>");
                }

            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    private void confirm() {
        String sql = "UPDATE payments SET penalty=?, cleared=? WHERE loan_id=? AND installment=?";
        try {
            int loan_id = Integer.parseInt(loans[loanSelector.getSelectedIndex()]);
            PreparedStatement statement = connection.prepareStatement(sql);
            if (penalty > 0) {
                statement.setInt(1, penalty);
            } else {
                statement.setNull(1, 0);
            }
            statement.setBoolean(2, true);
            statement.setInt(3, loan_id);
            statement.setInt(4, installment);
            statement.executeUpdate();

        } catch (SQLException e1) {
            e1.printStackTrace();
            return;
        }
        close();
    }
}
