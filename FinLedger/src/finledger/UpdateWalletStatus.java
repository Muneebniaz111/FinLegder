package finledger;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class UpdateWalletStatus extends JFrame {

    Connection conn = null;
    PreparedStatement pst = null;
    private JPanel contentPane;
    private JTextField txtBalance;
    private JTextField txtWalletType;
    private JTextField txtStatus;
    private Choice cWallets;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UpdateWalletStatus frame = new UpdateWalletStatus();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public UpdateWalletStatus() {
        setTitle("Update Wallet Status - FinLedger");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 450);
        setLocationRelativeTo(null); // Center the frame
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(Color.WHITE);

        Font labelFont = new Font("Times New Roman", Font.PLAIN, 16);
        Font headingFont = new Font("Times New Roman", Font.BOLD, 20);

        // ================= IMAGE =================
        try {
            ImageIcon i1 = new ImageIcon(getClass().getResource("/icons/third.jpg"));
            Image img = i1.getImage().getScaledInstance(500, 350, Image.SCALE_DEFAULT);
            JLabel lblImage = new JLabel(new ImageIcon(img));
            lblImage.setBounds(400, 80, 550, 250);
            contentPane.add(lblImage);
        } catch (Exception e) {
            System.out.println("Image not found: /icons/seventh.jpg");
        }

        // ================= HEADING =================
        JLabel lblUpdateWallet = new JLabel("Update Wallet Status");
        lblUpdateWallet.setFont(headingFont);
        lblUpdateWallet.setBounds(85, 11, 300, 34);
        contentPane.add(lblUpdateWallet);

        // ================= WALLET NUMBER =================
        JLabel lblWalletNumber = new JLabel("Wallet Number:");
        lblWalletNumber.setFont(labelFont);
        lblWalletNumber.setBounds(27, 87, 120, 20);
        contentPane.add(lblWalletNumber);

        cWallets = new Choice();
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT wallet_number FROM Wallets");
            while(rs.next()) {
                cWallets.add(rs.getString("wallet_number"));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        cWallets.setBounds(160, 84, 140, 25);
        contentPane.add(cWallets);

        // ================= BALANCE =================
        JLabel lblBalance = new JLabel("Balance:");
        lblBalance.setFont(labelFont);
        lblBalance.setBounds(27, 130, 120, 20);
        contentPane.add(lblBalance);

        txtBalance = new JTextField();
        txtBalance.setBounds(160, 127, 140, 25);
        txtBalance.setFont(labelFont);
        contentPane.add(txtBalance);

        // ================= WALLET TYPE =================
        JLabel lblWalletType = new JLabel("Wallet Type:");
        lblWalletType.setFont(labelFont);
        lblWalletType.setBounds(27, 173, 120, 20);
        contentPane.add(lblWalletType);

        txtWalletType = new JTextField();
        txtWalletType.setBounds(160, 170, 140, 25);
        txtWalletType.setFont(labelFont);
        contentPane.add(txtWalletType);

        // ================= STATUS =================
        JLabel lblStatus = new JLabel("Status:");
        lblStatus.setFont(labelFont);
        lblStatus.setBounds(27, 216, 120, 20);
        contentPane.add(lblStatus);

        txtStatus = new JTextField();
        txtStatus.setBounds(160, 213, 140, 25);
        txtStatus.setFont(labelFont);
        contentPane.add(txtStatus);

        // ================= BUTTONS =================
        JButton btnCheck = new JButton("Check");
        btnCheck.setBounds(120, 270, 100, 30);
        btnCheck.setBackground(Color.BLACK);
        btnCheck.setForeground(Color.WHITE);
        btnCheck.setFont(labelFont);
        btnCheck.addActionListener(e -> checkWallet());
        contentPane.add(btnCheck);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.setBounds(60, 320, 100, 30);
        btnUpdate.setBackground(Color.BLACK);
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setFont(labelFont);
        btnUpdate.addActionListener(e -> updateWallet());
        contentPane.add(btnUpdate);

        JButton btnBack = new JButton("Back");
        btnBack.setBounds(180, 320, 100, 30);
        btnBack.setBackground(Color.BLACK);
        btnBack.setForeground(Color.WHITE);
        btnBack.setFont(labelFont);
        btnBack.addActionListener(e -> {
            new TransactionDesk().setVisible(true); // Go back to TransactionDesk
            this.setVisible(false);
        });
        contentPane.add(btnBack);
    }

    private void checkWallet() {
        try {
            String selectedWallet = cWallets.getSelectedItem();
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT * FROM Wallets WHERE wallet_number = '" + selectedWallet + "'");
            if(rs.next()) {
                txtBalance.setText(rs.getString("balance"));
                txtWalletType.setText(rs.getString("wallet_type"));
                txtStatus.setText(rs.getString("status"));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void updateWallet() {
        try {
            String selectedWallet = cWallets.getSelectedItem();
            Conn c = new Conn();
            String sql = "UPDATE Wallets SET status = '" + txtStatus.getText() + "' WHERE wallet_number = '" + selectedWallet + "'";
            c.s.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Wallet Status Updated Successfully!");
            // Stay on same frame
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}