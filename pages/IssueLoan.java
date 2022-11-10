package pages;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.stream.IntStream;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import components.CloseableFrame;
import components.Panel;
import components.Button;

public class IssueLoan extends CloseableFrame {

    Connection connection;

    JComboBox<String> memberSelector;
    String[] members;
    String[] groups = {};

    JComboBox<String> periodSelector;
    int[] years;
    JTextField amountField;

    JLabel error;

    int maxPeriod = 5;

    public IssueLoan(JFrame back) {
        super(back);
        setup();
    }

    private void setup() {
        // Mysql Connection
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
        JPanel issueLoanPanel = new Panel();
        issueLoanPanel.setLayout(null);

        // Title
        JLabel title = new JLabel("Loan");
        title.setFont(new Font("Serif", Font.BOLD, 36));
        title.setBounds(0, 0, 500, 60);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        // Select member/group
        members = fetchMembers();

        JLabel membersLabel = new JLabel("Member ID/Group Name");
        membersLabel.setFont(new Font("Serif", Font.BOLD, 24));
        membersLabel.setBounds(0, 100, 600, 30);
        memberSelector = new JComboBox<String>(members);
        memberSelector.setFont(new Font("Serif", Font.PLAIN, 18));
        memberSelector.setBackground(Color.white);
        memberSelector.setBounds(0, 150, 500, 50);

        // Amount
        JLabel amountLabel = new JLabel("Amount");
        amountLabel.setFont(new Font("Serif", Font.BOLD, 24));
        amountLabel.setBounds(0, 220, 600, 30);
        amountField = new JTextField(40);
        amountField.setBorder(BorderFactory.createCompoundBorder(
                amountField.getBorder(),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        amountField.setFont(new Font("Serif", Font.PLAIN, 18));
        amountField.setBounds(0, 270, 500, 50);

        // Select repayment period
        years = IntStream.rangeClosed(1, maxPeriod).toArray();
        String strYears[] = Arrays.stream(years)
                .mapToObj(String::valueOf)
                .toArray(String[]::new);
        JLabel periodLabel = new JLabel("Repayment Period (Years)");
        periodLabel.setFont(new Font("Serif", Font.BOLD, 24));
        periodLabel.setBounds(0, 340, 600, 30);
        periodSelector = new JComboBox<String>(strYears);
        periodSelector.setFont(new Font("Serif", Font.PLAIN, 18));
        periodSelector.setBackground(Color.white);
        periodSelector.setBounds(0, 390, 500, 50);

        JButton confirmButton = new Button("Submit Request");
        confirmButton.setBackground(new Color(232, 113, 33));
        confirmButton.setBounds(0, 460, 500, 60);
        confirmButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                submit();
            }
        });

        // Display in case of error
        error = new JLabel("");
        error.setFont(new Font("Serif", Font.PLAIN, 21));
        error.setForeground(Color.red);
        error.setBounds(0, 540, 500, 20);
        error.setHorizontalAlignment(SwingConstants.CENTER);

        issueLoanPanel.add(title);
        issueLoanPanel.add(membersLabel);
        issueLoanPanel.add(memberSelector);
        issueLoanPanel.add(amountLabel);
        issueLoanPanel.add(amountField);
        issueLoanPanel.add(periodLabel);
        issueLoanPanel.add(periodSelector);
        issueLoanPanel.add(confirmButton);
        issueLoanPanel.add(error);

        panel.setBorder(new EmptyBorder(50, 0, 50, 0));
        panel.add(issueLoanPanel);

        this.add(panel);
    }

    private String[] fetchMembers() {
        String members[] = {};
        String sql = "select national_id,group_name from members";
        try {
            // Fetch members from database
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                // Add member and group to array
                List<String> idList = new ArrayList<String>(
                        Arrays.asList(members));
                idList.add(result.getString("national_id"));
                members = idList.toArray(members);
                //
                List<String> groupList = new ArrayList<String>(
                        Arrays.asList(groups));
                groupList.add(result.getString("group_name"));
                groups = groupList.toArray(groups);
                //
                String group = result.getString("group_name");
                if (group != null && !Arrays.asList(members).contains(group)) {
                    // Add groups to the member list
                    idList = new ArrayList<String>(
                            Arrays.asList(members));
                    idList.add(group);
                    members = idList.toArray(members);
                    // Add null to groups list so that
                    // members and groups have same length
                    groupList = new ArrayList<String>(
                            Arrays.asList(groups));
                    groupList.add(null);
                    groups = groupList.toArray(groups);
                }
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return members;
    }

    private void submit() {

        try {
            int index = memberSelector.getSelectedIndex();
            int amount = Integer.parseInt(amountField.getText());

            Boolean isInGroup = false;
            Boolean group = Arrays.asList(groups).contains(members[index]);
            if (!group) {
                isInGroup = groups[index] != null;
            }
            int period = years[periodSelector.getSelectedIndex()];

            int maxPeriod;
            double interest;
            int maxRatio;

            // Calculate period, max loan and interest
            if (group) {
                maxPeriod = 5;
                interest = 0.8;
                maxRatio = 3;
            } else if (isInGroup) {
                maxPeriod = 4;
                interest = 1;
                maxRatio = 4;
            } else {
                maxPeriod = 3;
                interest = 1.2;
                maxRatio = 3;
            }

            if (period > maxPeriod) {
                error.setText("Maximum repayment period is " + maxPeriod + " years");
                return;
            }

            // Select total contribution by member/group
            PreparedStatement contributionStatement = connection
                    .prepareStatement("SELECT SUM(amount) AS sum_amount FROM contributions WHERE "
                            + (group ? "group_name=?" : "member_id=?"));
            contributionStatement.setString(1, members[index]);
            ResultSet result = contributionStatement.executeQuery();
            int contribution = 0;
            if (result.next()) {
                contribution = result.getInt("sum_amount");
            }

            int maxLoan = contribution * maxRatio;
            if (maxLoan < amount) {
                error.setText("Your maximum loan is " + String.valueOf(maxLoan));
                return;
            }

            String sql = "insert into loans(member_id,group_name,amount,period) values (?,?,?,?)";
            // Register the loan to the database
            PreparedStatement statement = connection.prepareStatement(sql);
            if (group) {
                statement.setNull(1, 0);
                statement.setString(2, members[index]);
            } else {
                statement.setInt(1, Integer.parseInt(members[index]));
                statement.setNull(2, 0);
            }
            statement.setInt(3, amount);
            statement.setInt(4, period);
            statement.executeUpdate();

            // Get the registered loan id
            PreparedStatement idStatement = connection
                    .prepareStatement("SELECT id FROM loans WHERE id= LAST_INSERT_ID()");
            ResultSet idResult = idStatement.executeQuery();
            idResult.next();

            // Calculate installments to be paid
            int months = period * 12;
            int loan_id = idResult.getInt("id");
            int installment = amount / months;
            Double interestPaid = installment * interest / 100;
            LocalDate date = LocalDate.now();
            String due_next = "";
            for (int i = 1; i <= months; i++) {
                LocalDate initial = date.plusMonths(i);
                // End of the month
                LocalDate dueDate = initial.withDayOfMonth(initial.getMonth().length(initial.isLeapYear()));
                if (i == 1)
                    due_next = dueDate.toString();
                // Register installments to the database
                PreparedStatement paymentStatement = connection
                        .prepareStatement(
                                "INSERT INTO payments (loan_id, installment, amount, interest, due) values (?,?,?,?,?);");
                paymentStatement.setInt(1, loan_id);
                paymentStatement.setInt(2, i);
                paymentStatement.setInt(3, installment);
                paymentStatement.setInt(4, interestPaid.intValue());
                paymentStatement.setString(5, dueDate.toString());
                paymentStatement.executeUpdate();
            }
            // Display the loan details, including the ID
            new LoanDetails(String.valueOf(loan_id), due_next, String.valueOf(installment)).setVisible(true);
            this.dispose();
        } catch (java.lang.NumberFormatException e) {
            error.setText("Please Enter a valid amount");
            return;
        } catch (SQLException e1) {
            e1.printStackTrace();
            error.setText("Error Processing your loan");
            return;
        }
    }
}
