package finledger;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class TransactionStatus extends JFrame implements ActionListener {

    private JTable table;
    private JTextField tfSearch;
    private JButton btnSearch, btnRefresh, btnBack;

    public TransactionStatus() {
        setTitle("Transaction Status - FinLedger");
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        Font labelFont = new Font("Times New Roman", Font.PLAIN, 16);
        Font headingFont = new Font("Times New Roman", Font.BOLD, 20);

        // Heading
        JLabel heading = new JLabel("Transaction Status");
        heading.setFont(headingFont);
        heading.setBounds(180, 20, 300, 30);
        add(heading);

        // Search label and field
        JLabel lblSearch = new JLabel("Search by Wallet or Transaction ID:");
        lblSearch.setFont(labelFont);
        lblSearch.setBounds(50, 70, 300, 25);
        add(lblSearch);

        tfSearch = new JTextField();
        tfSearch.setFont(labelFont);
        tfSearch.setBounds(320, 70, 180, 25);
        add(tfSearch);

        // Buttons
        btnSearch = new JButton("Search");
        btnSearch.setFont(labelFont);
        btnSearch.setBounds(510, 70, 100, 25);
        btnSearch.setBackground(Color.BLACK);
        btnSearch.setForeground(Color.WHITE);
        btnSearch.addActionListener(this);
        add(btnSearch);

        btnRefresh = new JButton("Refresh All");
        btnRefresh.setFont(labelFont);
        btnRefresh.setBounds(620, 70, 120, 25);
        btnRefresh.setBackground(Color.BLACK);
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.addActionListener(this);
        add(btnRefresh);

        btnBack = new JButton("Back");
        btnBack.setFont(labelFont);
        btnBack.setBounds(750, 70, 100, 25);
        btnBack.setBackground(Color.BLACK);
        btnBack.setForeground(Color.WHITE);
        btnBack.addActionListener(this);
        add(btnBack);

        // Table
        table = new JTable();
        table.setFont(labelFont);
        table.setRowHeight(25);
        table.getTableHeader().setFont(labelFont); // Header font
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 120, 800, 400);
        add(scrollPane);

        // Frame size and center
        setSize(920, 600);
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);

        loadAllTransactions();
    }

    private void loadAllTransactions() {
        try {
            Conn conn = new Conn();

            // Merge payments and refunds
            String query = "SELECT id AS TransactionID, wallet_number AS WalletNumber, amount AS Amount, 'PAYMENT' AS Type, transaction_date AS Date FROM transactions " +
                           "UNION ALL " +
                           "SELECT refund_id AS TransactionID, wallet_number AS WalletNumber, amount AS Amount, 'REFUND' AS Type, refund_date AS Date FROM refunds " +
                           "ORDER BY Date DESC";

            PreparedStatement pst = conn.c.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            populateTable(rs);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void searchTransactions(String keyword) {
        try {
            Conn conn = new Conn();

            String query = "SELECT id AS TransactionID, wallet_number AS WalletNumber, amount AS Amount, 'PAYMENT' AS Type, transaction_date AS Date FROM transactions " +
                           "WHERE wallet_number LIKE ? OR id LIKE ? " +
                           "UNION ALL " +
                           "SELECT refund_id AS TransactionID, wallet_number AS WalletNumber, amount AS Amount, 'REFUND' AS Type, refund_date AS Date FROM refunds " +
                           "WHERE wallet_number LIKE ? OR refund_id LIKE ? " +
                           "ORDER BY Date DESC";

            PreparedStatement pst = conn.c.prepareStatement(query);
            pst.setString(1, "%" + keyword + "%");
            pst.setString(2, "%" + keyword + "%");
            pst.setString(3, "%" + keyword + "%");
            pst.setString(4, "%" + keyword + "%");

            ResultSet rs = pst.executeQuery();

            populateTable(rs);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateTable(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        String[] columns = new String[columnCount];
        for (int i = 0; i < columnCount; i++) {
            columns[i] = rsmd.getColumnName(i + 1);
        }

        DefaultTableModel model = new DefaultTableModel(columns, 0);
        while (rs.next()) {
            Object[] row = new Object[columnCount];
            for (int i = 0; i < columnCount; i++) {
                row[i] = rs.getObject(i + 1);
            }
            model.addRow(row);
        }

        table.setModel(model);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btnSearch) {
            String keyword = tfSearch.getText().trim();
            if (!keyword.isEmpty()) {
                searchTransactions(keyword);
            } else {
                JOptionPane.showMessageDialog(null, "Please enter Wallet Number or Transaction ID to search.");
            }
        } else if (ae.getSource() == btnRefresh) {
            loadAllTransactions();
        } else if (ae.getSource() == btnBack) {
            new TransactionDesk().setVisible(true);
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new TransactionStatus();
    }
}