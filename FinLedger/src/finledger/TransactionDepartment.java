package finledger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class TransactionDepartment extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private JLabel lblModule;
    private JLabel lblBudget;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                TransactionDepartment frame = new TransactionDepartment();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public TransactionDepartment() {
        setTitle("Transaction Department - FinLedger");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 550);
        setLocationRelativeTo(null); // Center the frame

        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBackground(Color.WHITE);
        setContentPane(contentPane);

        Font fontPlain = new Font("Times New Roman", Font.PLAIN, 16);
        Font fontBold = new Font("Times New Roman", Font.BOLD, 16);

        // Table
        table = new JTable();
        table.setFont(fontPlain);
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 40, 700, 350);
        contentPane.add(scrollPane);

        // Labels
        lblModule = new JLabel("Module");
        lblModule.setFont(fontBold);
        lblModule.setBounds(50, 11, 100, 20);
        contentPane.add(lblModule);

        lblBudget = new JLabel("Budget");
        lblBudget.setFont(fontBold);
        lblBudget.setBounds(500, 11, 100, 20);
        contentPane.add(lblBudget);

        // Load Data button
        JButton btnLoadData = new JButton("Load Data");
        btnLoadData.setFont(fontPlain);
        btnLoadData.setBackground(Color.BLACK);
        btnLoadData.setForeground(Color.WHITE);
        btnLoadData.setBounds(100, 410, 120, 30);
        btnLoadData.addActionListener(e -> loadData());
        contentPane.add(btnLoadData);

        // Add / Update Module button
        JButton btnUpdate = new JButton("Add / Update Module");
        btnUpdate.setFont(fontPlain);
        btnUpdate.setBackground(Color.BLACK);
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setBounds(280, 410, 180, 30);
        btnUpdate.addActionListener(e -> addOrUpdateModule());
        contentPane.add(btnUpdate);

        // Back button
        JButton btnBack = new JButton("Back");
        btnBack.setFont(fontPlain);
        btnBack.setBackground(Color.BLACK);
        btnBack.setForeground(Color.WHITE);
        btnBack.setBounds(500, 410, 120, 30);
        btnBack.addActionListener(e -> {
            try {
                new TransactionDesk().setVisible(true);
                setVisible(false);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        contentPane.add(btnBack);

        // Load data initially
        loadData();
    }

    private void loadData() {
        try {
            Conn c = new Conn(); // Your DB connection class
            String query = "SELECT module_name AS Module, budget AS Budget FROM transaction_department";
            ResultSet rs = c.s.executeQuery(query);

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("");
            model.addColumn("");

            while (rs.next()) {
                String module = rs.getString("Module");
                String budget = rs.getString("Budget");
                model.addRow(new Object[]{module, budget});
            }

            table.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading data: " + e.getMessage());
        }
    }

    private void addOrUpdateModule() {
        try {
            String module = JOptionPane.showInputDialog(this, "Enter Module Name:");
            if (module == null || module.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Module cannot be empty!");
                return;
            }

            String budgetStr = JOptionPane.showInputDialog(this, "Enter Budget (numbers only):");
            if (budgetStr == null || budgetStr.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Budget cannot be empty!");
                return;
            }

            double budget;
            try {
                budget = Double.parseDouble(budgetStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Budget must be a number!");
                return;
            }

            Conn c = new Conn();

            // Check if module exists
            String checkQuery = "SELECT * FROM transaction_department WHERE module_name=?";
            PreparedStatement pstCheck = c.c.prepareStatement(checkQuery);
            pstCheck.setString(1, module);
            ResultSet rs = pstCheck.executeQuery();

            if (rs.next()) {
                // Module exists → update budget
                String updateQuery = "UPDATE transaction_department SET budget=? WHERE module_name=?";
                PreparedStatement pstUpdate = c.c.prepareStatement(updateQuery);
                pstUpdate.setDouble(1, budget);
                pstUpdate.setString(2, module);
                pstUpdate.executeUpdate();
                JOptionPane.showMessageDialog(this, "Module updated successfully!");
            } else {
                // Module does not exist → insert new
                String insertQuery = "INSERT INTO transaction_department(module_name, budget) VALUES(?,?)";
                PreparedStatement pstInsert = c.c.prepareStatement(insertQuery);
                pstInsert.setString(1, module);
                pstInsert.setDouble(2, budget);
                pstInsert.executeUpdate();
                JOptionPane.showMessageDialog(this, "New module added successfully!");
            }

            loadData(); // Refresh table

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding/updating module: " + e.getMessage());
        }
    }
}