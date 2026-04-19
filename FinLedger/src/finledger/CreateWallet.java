package finledger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;

public class CreateWallet extends JFrame implements ActionListener {

    JTextField tfWalletNumber, tfOwnerName, tfBalance, tfCreatedOn;
    JComboBox<String> cbWalletType, cbStatus;
    JButton btnAdd, btnCancel;

    public CreateWallet() {
        setTitle("Create Wallet - FinLedger");
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        Font labelFont = new Font("Times New Roman", Font.PLAIN, 16);
        Font headingFont = new Font("Times New Roman", Font.BOLD, 18);

        // ================= HEADING =================
        JLabel heading = new JLabel("Create New Wallet");
        heading.setFont(headingFont);
        heading.setBounds(150, 20, 250, 30);
        add(heading);

        // ================= WALLET NUMBER =================
        JLabel lblWalletNumber = new JLabel("Wallet Number");
        lblWalletNumber.setFont(labelFont);
        lblWalletNumber.setBounds(60, 80, 150, 30);
        add(lblWalletNumber);

        tfWalletNumber = new JTextField();
        tfWalletNumber.setBounds(250, 80, 150, 30);
        tfWalletNumber.setFont(labelFont);
        add(tfWalletNumber);

        // ================= OWNER NAME =================
        JLabel lblOwnerName = new JLabel("Owner Name");
        lblOwnerName.setFont(labelFont);
        lblOwnerName.setBounds(60, 130, 150, 30);
        add(lblOwnerName);

        tfOwnerName = new JTextField();
        tfOwnerName.setBounds(250, 130, 150, 30);
        tfOwnerName.setFont(labelFont);
        add(tfOwnerName);

        // ================= BALANCE =================
        JLabel lblBalance = new JLabel("Balance");
        lblBalance.setFont(labelFont);
        lblBalance.setBounds(60, 180, 150, 30);
        add(lblBalance);

        tfBalance = new JTextField();
        tfBalance.setBounds(250, 180, 150, 30);
        tfBalance.setFont(labelFont);
        add(tfBalance);

        // ================= WALLET TYPE =================
        JLabel lblWalletType = new JLabel("Wallet Type");
        lblWalletType.setFont(labelFont);
        lblWalletType.setBounds(60, 230, 150, 30);
        add(lblWalletType);

        String[] walletTypes = {"Savings", "Current", "Crypto"};
        cbWalletType = new JComboBox<>(walletTypes);
        cbWalletType.setBounds(250, 230, 150, 30);
        cbWalletType.setBackground(Color.WHITE);
        cbWalletType.setFont(labelFont);
        add(cbWalletType);

        // ================= STATUS =================
        JLabel lblStatus = new JLabel("Status");
        lblStatus.setFont(labelFont);
        lblStatus.setBounds(60, 280, 150, 30);
        add(lblStatus);

        String[] statusOptions = {"Active", "Inactive"};
        cbStatus = new JComboBox<>(statusOptions);
        cbStatus.setBounds(250, 280, 150, 30);
        cbStatus.setBackground(Color.WHITE);
        cbStatus.setFont(labelFont);
        add(cbStatus);

        // ================= CREATED ON =================
        JLabel lblCreatedOn = new JLabel("Created On (dd/MM/yyyy)");
        lblCreatedOn.setFont(labelFont);
        lblCreatedOn.setBounds(60, 330, 180, 30);
        add(lblCreatedOn);

        tfCreatedOn = new JTextField();
        tfCreatedOn.setBounds(250, 330, 150, 30);
        tfCreatedOn.setFont(labelFont);
        add(tfCreatedOn);

        // ================= BUTTONS =================
        btnAdd = new JButton("Add Wallet");
        btnAdd.setBounds(60, 380, 130, 30);
        btnAdd.setBackground(Color.BLACK);
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(labelFont);
        btnAdd.addActionListener(this);
        add(btnAdd);

        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(220, 380, 130, 30);
        btnCancel.setBackground(Color.BLACK);
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFont(labelFont);
        btnCancel.addActionListener(this);
        add(btnCancel);

        // ================= IMAGE / LOGO =================
        ImageIcon icon = new ImageIcon(getClass().getResource("/icons/twelve.jpg")); // Place image in src/icons/
        Image img = icon.getImage().getScaledInstance(550, 350, Image.SCALE_SMOOTH);
        JLabel lblImage = new JLabel(new ImageIcon(img));
        lblImage.setBounds(450, 70, 550, 350);
        add(lblImage);

        // ================= FRAME SETTINGS =================
        setSize(1050, 550);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btnAdd) {
            try {
                String walletNumber = tfWalletNumber.getText();
                String ownerName = tfOwnerName.getText();
                String balance = tfBalance.getText();
                String walletType = (String) cbWalletType.getSelectedItem();
                String status = (String) cbStatus.getSelectedItem();
                String createdOn = tfCreatedOn.getText();

                // Convert date from dd/MM/yyyy to yyyy-MM-dd
                String[] parts = createdOn.split("/");
                if (parts.length != 3) throw new Exception("Date must be in dd/MM/yyyy format");
                String formattedDate = parts[2] + "-" + parts[1] + "-" + parts[0];

                Conn conn = new Conn(); // your custom DB connection
                String query = "INSERT INTO Wallets (wallet_number, owner_name, balance, wallet_type, status, created_on) " +
                               "VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = conn.c.prepareStatement(query);
                pst.setString(1, walletNumber);
                pst.setString(2, ownerName);
                pst.setBigDecimal(3, new java.math.BigDecimal(balance));
                pst.setString(4, walletType);
                pst.setString(5, status);
                pst.setDate(6, java.sql.Date.valueOf(formattedDate));

                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "New Wallet added successfully!");
                this.setVisible(false);

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }

        } else if (ae.getSource() == btnCancel) {
            new Dashboard().setVisible(true);
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CreateWallet::new);
    }
}