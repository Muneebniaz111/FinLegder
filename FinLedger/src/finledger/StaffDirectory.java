package finledger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class StaffDirectory extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private JButton btnLoadData, btnBack;

    public StaffDirectory() {
        setTitle("Staff Directory - FinLedger");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 650); // increased frame size
        setLocationRelativeTo(null); // Center the frame

        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBackground(Color.WHITE);
        setContentPane(contentPane);

        Font labelFont = new Font("Times New Roman", Font.PLAIN, 16);
        Font buttonFont = new Font("Times New Roman", Font.PLAIN, 16);

        // ==================== Table ====================
        table = new JTable();
        table.setFont(labelFont);
        table.setRowHeight(30); // increased row height
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 20, 750, 500); // expanded table size
        contentPane.add(scrollPane);

        // ==================== Buttons ====================
        btnLoadData = new JButton("Load Data");
        btnLoadData.setFont(buttonFont);
        btnLoadData.setBackground(Color.BLACK);
        btnLoadData.setForeground(Color.WHITE);
        btnLoadData.setBounds(200, 540, 150, 40); // repositioned
        btnLoadData.addActionListener(e -> loadData());
        contentPane.add(btnLoadData);

        btnBack = new JButton("Back");
        btnBack.setFont(buttonFont);
        btnBack.setBackground(Color.BLACK);
        btnBack.setForeground(Color.WHITE);
        btnBack.setBounds(400, 540, 150, 40); // repositioned
        btnBack.addActionListener(e -> {
            new TransactionDesk().setVisible(true);
            setVisible(false);
        });
        contentPane.add(btnBack);

        // ==================== Image on Right ====================
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/icons/tenth.png")); // replace with your image path
            Image img = icon.getImage().getScaledInstance(450, 550, Image.SCALE_SMOOTH); // larger image
            JLabel lblImage = new JLabel(new ImageIcon(img));
            lblImage.setBounds(800, 20, 450, 550); // repositioned
            contentPane.add(lblImage);
        } catch (Exception ex) {
            System.out.println("Image not found! Skipping image.");
        }

        setVisible(true);
    }

    // ==================== Load Data Method ====================
    private void loadData() {
        try {
            Conn c = new Conn(); // your DB connection class
            String query = "SELECT name AS Name, dob AS DOB, gender AS Gender, designation AS Designation, salary AS Salary, phone AS Phone, email AS Email, cnic AS CNIC FROM Staff";
            ResultSet rs = c.s.executeQuery(query);

            // Create table model
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Name");
            model.addColumn("DOB");
            model.addColumn("Gender");
            model.addColumn("Designation");
            model.addColumn("Salary");
            model.addColumn("Phone");
            model.addColumn("Email");
            model.addColumn("CNIC");

            while (rs.next()) {
                String name = rs.getString("Name");
                String dob = rs.getString("DOB");
                String gender = rs.getString("Gender");
                String designation = rs.getString("Designation");
                String salary = rs.getString("Salary");
                String phone = rs.getString("Phone");
                String email = rs.getString("Email");
                String cnic = rs.getString("CNIC");

                model.addRow(new Object[]{name, dob, gender, designation, salary, phone, email, cnic});
            }

            table.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading staff data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StaffDirectory::new);
    }
}