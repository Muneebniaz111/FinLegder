package finledger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.math.BigDecimal;

public class PaymentGatewayService extends JFrame implements ActionListener {

    private JTextField tfWalletNumber, tfAmount;
    private JButton btnPay, btnCancel;

    public PaymentGatewayService() {

        setTitle("Payment Gateway - FinLedger");
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        // Fonts
        Font labelFont = new Font("Times New Roman", Font.PLAIN, 16);
        Font headingFont = new Font("Times New Roman", Font.BOLD, 20);
        Font buttonFont = new Font("Times New Roman", Font.PLAIN, 16);
        Font textFieldFont = new Font("Times New Roman", Font.PLAIN, 16);

        // Heading
        JLabel heading = new JLabel("Payment Gateway Service");
        heading.setFont(headingFont);
        heading.setBounds(150, 30, 300, 30);
        add(heading);

        // Wallet Number Label & Field
        JLabel lblWalletNumber = new JLabel("Wallet Number:");
        lblWalletNumber.setFont(labelFont);
        lblWalletNumber.setBounds(60, 120, 200, 30);
        add(lblWalletNumber);

        tfWalletNumber = new JTextField();
        tfWalletNumber.setFont(textFieldFont);
        tfWalletNumber.setBounds(260, 120, 180, 30);
        add(tfWalletNumber);

        // Amount Label & Field
        JLabel lblAmount = new JLabel("Amount:");
        lblAmount.setFont(labelFont);
        lblAmount.setBounds(60, 180, 200, 30);
        add(lblAmount);

        tfAmount = new JTextField();
        tfAmount.setFont(textFieldFont);
        tfAmount.setBounds(260, 180, 180, 30);
        add(tfAmount);

        // Process Payment Button
        btnPay = new JButton("Process Payment");
        btnPay.setFont(buttonFont);
        btnPay.setBounds(80, 260, 160, 35);
        btnPay.setBackground(Color.BLACK);
        btnPay.setForeground(Color.WHITE);
        btnPay.addActionListener(this);
        add(btnPay);

        // Cancel Button
        btnCancel = new JButton("Cancel");
        btnCancel.setFont(buttonFont);
        btnCancel.setBounds(270, 260, 160, 35);
        btnCancel.setBackground(Color.BLACK);
        btnCancel.setForeground(Color.WHITE);
        btnCancel.addActionListener(this);
        add(btnCancel);

        // Frame Settings
        setSize(550, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btnPay) {

            String walletNumber = tfWalletNumber.getText().trim();
            String amountStr = tfAmount.getText().trim();

            if (walletNumber.isEmpty() || amountStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter both Wallet Number and Amount.");
                return;
            }

            try {
                BigDecimal paymentAmount = new BigDecimal(amountStr);
                Conn conn = new Conn();

                // Check wallet balance
                String fetch = "SELECT balance FROM Wallets WHERE wallet_number=?";
                PreparedStatement pst = conn.c.prepareStatement(fetch);
                pst.setString(1, walletNumber);
                ResultSet rs = pst.executeQuery();

                if (!rs.next()) {
                    JOptionPane.showMessageDialog(null, "Wallet not found!");
                    return;
                }

                BigDecimal balance = rs.getBigDecimal("balance");
                if (paymentAmount.compareTo(balance) > 0) {
                    JOptionPane.showMessageDialog(null, "Insufficient Balance!");
                    return;
                }

                BigDecimal newBalance = balance.subtract(paymentAmount);

                // Update Wallet Balance
                String update = "UPDATE Wallets SET balance=? WHERE wallet_number=?";
                PreparedStatement pst2 = conn.c.prepareStatement(update);
                pst2.setBigDecimal(1, newBalance);
                pst2.setString(2, walletNumber);
                pst2.executeUpdate();

                // Insert into Payment Table
                String insertPayment = "INSERT INTO payment(wallet_number,amount) VALUES(?,?)";
                PreparedStatement pst3 = conn.c.prepareStatement(insertPayment);
                pst3.setString(1, walletNumber);
                pst3.setBigDecimal(2, paymentAmount);
                pst3.executeUpdate();

                // Insert into Transactions Table
                String transaction = "INSERT INTO transactions(wallet_number,amount,transaction_type) VALUES(?,?,?)";
                PreparedStatement pst4 = conn.c.prepareStatement(transaction);
                pst4.setString(1, walletNumber);
                pst4.setBigDecimal(2, paymentAmount);
                pst4.setString(3, "PAYMENT");
                pst4.executeUpdate();

                JOptionPane.showMessageDialog(null, "Payment Successful!");

                tfWalletNumber.setText("");
                tfAmount.setText("");

            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "Invalid amount format!");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        }

        if (ae.getSource() == btnCancel) {
            new TransactionDesk().setVisible(true);
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new PaymentGatewayService();
    }
}