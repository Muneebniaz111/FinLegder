package finledger;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import net.proteanit.sql.DbUtils; // make sure rs2xml.jar is added

public class UserProfile extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private Font font = new Font("Times New Roman", Font.PLAIN, 16);

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UserProfile frame = new UserProfile();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Close method
     */
    public void close() {
        this.dispose();
    }

    /**
     * Create the frame.
     */
    public UserProfile() {
        setTitle("FinLedger - User Profile");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600); // frame size
        setLocationRelativeTo(null); // center the frame

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBackground(Color.WHITE);
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // ===== Table =====
        table = new JTable();
        table.setFont(font);
        table.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 16));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 40, 980, 450);
        contentPane.add(scrollPane);

        // ===== Column Labels =====
        JLabel lblId = new JLabel("ID");
        lblId.setBounds(20, 11, 50, 14);
        lblId.setFont(font);
        contentPane.add(lblId);

        JLabel lblIdentityType = new JLabel("Identity Type");
        lblIdentityType.setBounds(80, 11, 100, 14);
        lblIdentityType.setFont(font);
        contentPane.add(lblIdentityType);

        JLabel lblIdentityNumber = new JLabel("Identity Number");
        lblIdentityNumber.setBounds(190, 11, 120, 14);
        lblIdentityNumber.setFont(font);
        contentPane.add(lblIdentityNumber);

        JLabel lblName = new JLabel("Full Name");
        lblName.setBounds(320, 11, 100, 14);
        lblName.setFont(font);
        contentPane.add(lblName);

        JLabel lblGender = new JLabel("Gender");
        lblGender.setBounds(430, 11, 60, 14);
        lblGender.setFont(font);
        contentPane.add(lblGender);

        JLabel lblCountry = new JLabel("Country");
        lblCountry.setBounds(500, 11, 80, 14);
        lblCountry.setFont(font);
        contentPane.add(lblCountry);

        JLabel lblWallet = new JLabel("Wallet Type");
        lblWallet.setBounds(600, 11, 100, 14);
        lblWallet.setFont(font);
        contentPane.add(lblWallet);

        JLabel lblStatus = new JLabel("Account Status");
        lblStatus.setBounds(710, 11, 120, 14);
        lblStatus.setFont(font);
        contentPane.add(lblStatus);

        JLabel lblBalance = new JLabel("Initial Balance");
        lblBalance.setBounds(840, 11, 120, 14);
        lblBalance.setFont(font);
        contentPane.add(lblBalance);

        // ===== Buttons =====
        JButton btnLoadData = new JButton("Load Data");
        btnLoadData.setBounds(300, 510, 150, 30);
        btnLoadData.setBackground(Color.BLACK);
        btnLoadData.setForeground(Color.WHITE);
        btnLoadData.setFont(font);
        contentPane.add(btnLoadData);

        JButton btnBack = new JButton("Back");
        btnBack.setBounds(500, 510, 150, 30);
        btnBack.setBackground(Color.BLACK);
        btnBack.setForeground(Color.WHITE);
        btnBack.setFont(font);
        contentPane.add(btnBack);

        // ===== Button Actions =====
        btnLoadData.addActionListener(e -> loadUserData());
        btnBack.addActionListener(e -> {
            new TransactionDesk().setVisible(true);
            setVisible(false);
        });
    }

    /**
     * Load users from database
     */
    private void loadUserData() {
        try {
            Conn c = new Conn();
            String query = "SELECT id, identity_type, identity_number, full_name, gender, country, wallet_type, account_status, initial_balance FROM users";
            ResultSet rs = c.s.executeQuery(query);
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load user data", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}