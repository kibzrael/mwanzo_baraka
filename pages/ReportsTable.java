package pages;

import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import javax.swing.*;

import components.CloseableFrame;
import components.Panel;

import java.awt.*;

import javax.swing.table.DefaultTableModel;

public class ReportsTable extends CloseableFrame {
    JScrollPane scroll;

    Connection connection;

    public ReportsTable(JFrame back, String sql, String title) {
        super(back);
        setup(sql, title);
    }

    private void setup(String sql, String title) {
        // Mysql connection
        String dbUrl = "jdbc:mysql://localhost:3306/mwanzo_baraka";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(dbUrl, "root",
                    "kibzrael");
            // Execute the given sql
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            ResultSetMetaData metadata = result.getMetaData();

            // Setup the JTable
            DefaultTableModel model = new DefaultTableModel();
            JTable table = new JTable();
            table.setModel(model);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            table.setFillsViewportHeight(true);
            scroll = new JScrollPane(table);
            scroll.setHorizontalScrollBarPolicy(
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scroll.setVerticalScrollBarPolicy(
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

            String columnLabels[] = {};

            // Give Jtable labels from the result metadata
            for (int i = 1; i <= metadata.getColumnCount(); i++) {
                List<String> colList = new ArrayList<String>(
                        Arrays.asList(columnLabels));
                colList.add(metadata.getColumnLabel(i));
                columnLabels = colList.toArray(columnLabels);
            }
            model.setColumnIdentifiers(columnLabels);

            // Insert data from the result into the table
            while (result.next()) {
                Object[] row = {};
                for (String label : columnLabels) {
                    List<Object> rowList = new ArrayList<Object>(
                            Arrays.asList(row));
                    rowList.add(result.getObject(label));
                    row = rowList.toArray(row);
                }
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        JPanel panel = new Panel();
        panel.setLayout(new BorderLayout());
        panel.setBounds(100, 200, 1050, 500);

        // Title
        JLabel headline = new JLabel(title + " Report");
        headline.setFont(new Font("Serif", Font.BOLD, 36));
        headline.setBounds(200, 50, 850, 60);
        headline.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel description = new JLabel("A report on " + title
                + " for Mwanzo Baraka Organization",
                SwingConstants.RIGHT);
        description.setFont(new Font("Serif", Font.PLAIN, 24));
        description.setBounds(200, 130, 850, 30);
        description.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(scroll);

        this.setLayout(null);
        this.add(headline);
        this.add(description);
        this.add(panel);
    }

}
