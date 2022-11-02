package pages;

import java.sql.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import components.CloseableFrame;
import components.Panel;
import components.Button;

public class MakeContribution extends CloseableFrame {

    Connection connection;

    JComboBox<String> memberSelector;
    String members[] = {};
    String groups[] = {};

    JTextField amountField;

    JLabel error;

    public MakeContribution(JFrame back) {
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

        // Members
        JLabel title = new JLabel("Contributions");
        title.setFont(new Font("Serif", Font.BOLD, 36));
        title.setBounds(0, 50, 500, 60);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        members = fetchMembers();

        JLabel membersLabel = new JLabel("Member National ID");
        membersLabel.setFont(new Font("Serif", Font.BOLD, 24));
        membersLabel.setBounds(0, 150, 600, 30);
        memberSelector = new JComboBox<String>(members);
        memberSelector.setFont(new Font("Serif", Font.PLAIN, 18));
        memberSelector.setBackground(Color.white);
        memberSelector.setBounds(0, 200, 500, 50);

        // Amount
        JLabel amountLabel = new JLabel("Amount");
        amountLabel.setFont(new Font("Serif", Font.BOLD, 24));
        amountLabel.setBounds(0, 270, 600, 30);
        amountField = new JTextField(40);
        amountField.setBorder(BorderFactory.createCompoundBorder(
                amountField.getBorder(),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        amountField.setFont(new Font("Serif", Font.PLAIN, 18));
        amountField.setBounds(0, 320, 500, 50);

        JButton confirmButton = new Button("Confirm Payment");
        confirmButton.setBackground(new Color(232, 113, 33));
        confirmButton.setBounds(0, 430, 500, 60);
        confirmButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                confirm();
            }
        });
        error = new JLabel("");
        error.setFont(new Font("Serif", Font.PLAIN, 21));
        error.setForeground(Color.red);
        error.setBounds(0, 510, 500, 20);
        error.setHorizontalAlignment(SwingConstants.CENTER);

        contributionPanel.add(title);
        contributionPanel.add(membersLabel);
        contributionPanel.add(memberSelector);
        contributionPanel.add(amountLabel);
        contributionPanel.add(amountField);
        contributionPanel.add(confirmButton);
        contributionPanel.add(error);

        panel.setBorder(new EmptyBorder(75, 0, 75, 0));
        panel.add(contributionPanel);

        this.add(panel);
    }

    private String[] fetchMembers() {
        String members[] = {};
        String sql = "select national_id,group_name from members";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                List<String> idList = new ArrayList<String>(
                        Arrays.asList(members));
                idList.add(result.getString("national_id"));
                members = idList.toArray(members);
                //
                List<String> groupList = new ArrayList<String>(
                        Arrays.asList(groups));
                groupList.add(result.getString("group_name"));
                groups = groupList.toArray(groups);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return members;
    }

    private void confirm() {

        String memberSql = "insert into contributions(member_id, amount) values (?,?)";
        String groupSql = "insert into contributions(group_name, amount) values (?,?)";
        try {
            int index = memberSelector.getSelectedIndex();
            int amount = Integer.parseInt(amountField.getText());
            if (amount < 1000) {
                error.setText("Minimum amount is 1000");
                return;
            }
            Boolean group = groups[index] != null;
            PreparedStatement statement = connection.prepareStatement(memberSql);
            statement.setString(1, members[index]);
            if (group) {
                statement.setInt(2, amount - 200);
            } else {
                statement.setInt(2, amount);
            }
            statement.executeUpdate();
            if (group) {
                PreparedStatement grpStatement = connection.prepareStatement(groupSql);
                grpStatement.setString(1, groups[index]);
                grpStatement.setInt(2, 200);
                grpStatement.executeUpdate();
            }
        } catch (java.lang.NumberFormatException e) {
            error.setText("Please Enter a valid amount");
            return;
        }

        catch (SQLException e1) {
            e1.printStackTrace();
        }
        close();
    }
}
