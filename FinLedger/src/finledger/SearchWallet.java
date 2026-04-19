package finledger;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import net.proteanit.sql.DbUtils;

public class SearchWallet extends JFrame implements ActionListener {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    private JPanel contentPane;
    private JTextField tfWalletNumber;
    private JTable table;
    private JCheckBox checkActive;
    private JButton btnLoad, btnBack;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                SearchWallet frame = new SearchWallet();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public SearchWallet() {
        setTitle("Search Wallet - FinLedger");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ================== FIXED SIZE AND CENTER ==================
        setSize(800, 550); // Fixed size
        setLocationRelativeTo(null); // Center the frame

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(Color.WHITE);

        Font labelFont = new Font("Times New Roman", Font.PLAIN, 16);
        Font headingFont = new Font("Times New Roman", Font.BOLD, 20);

        // ================= HEADING =================
        JLabel heading = new JLabel("Search Wallet");
        heading.setFont(headingFont);
        heading.setBounds(300, 10, 200, 30);
        contentPane.add(heading);

        // ================= WALLET NUMBER =================
        JLabel lblWalletNumber = new JLabel("Wallet Number:");
        lblWalletNumber.setFont(labelFont);
        lblWalletNumber.setBounds(50, 70, 150, 25);
        contentPane.add(lblWalletNumber);

        tfWalletNumber = new JTextField();
        tfWalletNumber.setBounds(200, 70, 150, 25);
        tfWalletNumber.setFont(labelFont);
        contentPane.add(tfWalletNumber);

        // ================= ONLY ACTIVE =================
        checkActive = new JCheckBox("Only display Active Wallets");
        checkActive.setFont(labelFont);
        checkActive.setBounds(400, 70, 220, 25);
        checkActive.setBackground(Color.WHITE);
        contentPane.add(checkActive);

        // ================= TABLE =================
        table = new JTable();
        table.setFont(labelFont);
        table.setRowHeight(22);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 120, 680, 300);
        contentPane.add(scrollPane);

        // ================= BUTTONS =================
        btnLoad = new JButton("Load Wallets");
        btnLoad.setBounds(200, 450, 150, 35);
        btnLoad.setBackground(Color.BLACK);
        btnLoad.setForeground(Color.WHITE);
        btnLoad.setFont(labelFont);
        btnLoad.addActionListener(this);
        contentPane.add(btnLoad);

        btnBack = new JButton("Back");
        btnBack.setBounds(400, 450, 150, 35);
        btnBack.setBackground(Color.BLACK);
        btnBack.setForeground(Color.WHITE);
        btnBack.setFont(labelFont);
        btnBack.addActionListener(this);
        contentPane.add(btnBack);
    }

    // ================= BUTTON EVENTS =================
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btnLoad) {
            try {
                Conn conn = new Conn();
                String walletNumber = tfWalletNumber.getText().trim();

                String sql = "SELECT wallet_number, owner_name, balance, wallet_type, status, created_on " +
                             "FROM Wallets WHERE wallet_number LIKE ?";
                if (checkActive.isSelected()) {
                    sql += " AND status = 'Active'";
                }

                pst = conn.c.prepareStatement(sql);
                pst.setString(1, "%" + walletNumber + "%");
                rs = pst.executeQuery();

                table.setModel(DbUtils.resultSetToTableModel(rs));

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }

        } else if (ae.getSource() == btnBack) {
            new TransactionDesk().setVisible(true);
            this.setVisible(false);
        }
    }
}