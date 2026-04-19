package finledger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.math.BigDecimal;

public class ReFund extends JFrame implements ActionListener {

    private JTextField tfWalletNumber, tfAmount;
    private JButton btnRefund, btnBack;

    public ReFund() {

        setTitle("Refund Payment - FinLedger");
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        // Fonts
        Font labelFont = new Font("Times New Roman", Font.PLAIN, 16);
        Font headingFont = new Font("Times New Roman", Font.BOLD, 20);
        Font textFieldFont = new Font("Times New Roman", Font.PLAIN, 16);
        Font buttonFont = new Font("Times New Roman", Font.PLAIN, 16);

        // Heading
        JLabel heading = new JLabel("Payment Gateway Service");
        heading.setFont(headingFont);
        heading.setBounds(150, 30, 300, 30);
        add(heading);

        // Wallet Number
        JLabel l1 = new JLabel("Wallet Number");
        l1.setBounds(60, 100, 150, 30);
        l1.setFont(labelFont);
        add(l1);

        tfWalletNumber = new JTextField();
        tfWalletNumber.setFont(textFieldFont);
        tfWalletNumber.setBounds(220, 100, 180, 30);
        add(tfWalletNumber);

        // Amount
        JLabel l2 = new JLabel("Amount");
        l2.setBounds(60, 160, 150, 30);
        l2.setFont(labelFont);
        add(l2);

        tfAmount = new JTextField();
        tfAmount.setFont(textFieldFont);
        tfAmount.setBounds(220, 160, 180, 30);
        add(tfAmount);

        // Refund Button
        btnRefund = new JButton("Refund");
        btnRefund.setFont(buttonFont);
        btnRefund.setBounds(80, 260, 160, 35);
        btnRefund.setBackground(Color.BLACK);
        btnRefund.setForeground(Color.WHITE);
        btnRefund.addActionListener(this);
        add(btnRefund);

        // Back Button
        btnBack = new JButton("Back");
        btnBack.setFont(buttonFont);
        btnBack.setBounds(270, 260, 160, 35);
        btnBack.setBackground(Color.BLACK);
        btnBack.setForeground(Color.WHITE);
        btnBack.addActionListener(this);
        add(btnBack);

        // Frame settings
        setSize(500, 350);
        setLocationRelativeTo(null); // Center frame
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        if(ae.getSource() == btnRefund){

            String wallet = tfWalletNumber.getText();
            BigDecimal amount = new BigDecimal(tfAmount.getText());

            try {

                Conn conn = new Conn();

                String check = "SELECT * FROM payment WHERE wallet_number=? AND amount=?";
                PreparedStatement pst = conn.c.prepareStatement(check);

                pst.setString(1, wallet);
                pst.setBigDecimal(2, amount);

                ResultSet rs = pst.executeQuery();

                if(!rs.next()){
                    JOptionPane.showMessageDialog(null,"Payment not found");
                    return;
                }

                String update = "UPDATE Wallets SET balance = balance + ? WHERE wallet_number=?";
                PreparedStatement pst2 = conn.c.prepareStatement(update);

                pst2.setBigDecimal(1, amount);
                pst2.setString(2, wallet);

                pst2.executeUpdate();

                String refund = "INSERT INTO refunds(wallet_number,amount) VALUES(?,?)";
                PreparedStatement pst3 = conn.c.prepareStatement(refund);

                pst3.setString(1, wallet);
                pst3.setBigDecimal(2, amount);

                pst3.executeUpdate();

                JOptionPane.showMessageDialog(null,"Refund Successful");

            } catch(Exception e){
                e.printStackTrace();
            }

        }

        if(ae.getSource() == btnBack){
            new TransactionDesk().setVisible(true);
            setVisible(false);
        }

    }

    public static void main(String[] args){
        new ReFund();
    }
}