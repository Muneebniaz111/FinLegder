package finledger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegisterUser extends JFrame {

    JTextField tfIdNumber, tfName, tfCountry, tfStatus, tfBalance;
    JComboBox<String> cbIdType, cbWalletType;
    JRadioButton rbMale, rbFemale;
    ButtonGroup bgGender;
    Font font = new Font("Times New Roman", Font.PLAIN, 16);

    RegisterUser() {
        setTitle("FinLedger - Register User");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600); // Bigger frame for image + form
        setLocationRelativeTo(null); // Center screen
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 247, 250));

        // ===== Left Panel for Form =====
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(245, 247, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        int y = 0;

        // Title
        JLabel title = new JLabel("REGISTER USER");
        title.setFont(new Font("Times New Roman", Font.BOLD, 26));
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 2;
        formPanel.add(title, gbc);
        gbc.gridwidth = 1;
        y++;

        // Identity Type
        JLabel lblIdType = new JLabel("Identity Type");
        lblIdType.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = y;
        formPanel.add(lblIdType, gbc);

        String[] ids = {"CNIC", "B/Form"};
        cbIdType = new JComboBox<>(ids);
        cbIdType.setFont(font);
        gbc.gridx = 1;
        formPanel.add(cbIdType, gbc);
        y++;

        // Identity Number
        JLabel lblIdNumber = new JLabel("Identity Number");
        lblIdNumber.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = y;
        formPanel.add(lblIdNumber, gbc);

        tfIdNumber = new JTextField(20);
        tfIdNumber.setFont(font);
        gbc.gridx = 1;
        formPanel.add(tfIdNumber, gbc);
        y++;

        // Full Name
        JLabel lblName = new JLabel("Full Name");
        lblName.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = y;
        formPanel.add(lblName, gbc);

        tfName = new JTextField(20);
        tfName.setFont(font);
        gbc.gridx = 1;
        formPanel.add(tfName, gbc);
        y++;

        // Gender
        JLabel lblGender = new JLabel("Gender");
        lblGender.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = y;
        formPanel.add(lblGender, gbc);

        JPanel genderPanel = new JPanel();
        genderPanel.setBackground(new Color(245, 247, 250));
        rbMale = new JRadioButton("Male");
        rbMale.setFont(font);
        rbMale.setBackground(new Color(245, 247, 250));
        rbFemale = new JRadioButton("Female");
        rbFemale.setFont(font);
        rbFemale.setBackground(new Color(245, 247, 250));
        bgGender = new ButtonGroup();
        bgGender.add(rbMale);
        bgGender.add(rbFemale);
        genderPanel.add(rbMale);
        genderPanel.add(rbFemale);
        gbc.gridx = 1;
        formPanel.add(genderPanel, gbc);
        y++;

        // Country
        JLabel lblCountry = new JLabel("Country");
        lblCountry.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = y;
        formPanel.add(lblCountry, gbc);

        tfCountry = new JTextField(20);
        tfCountry.setFont(font);
        gbc.gridx = 1;
        formPanel.add(tfCountry, gbc);
        y++;

        // Wallet Type
        JLabel lblWallet = new JLabel("Wallet Type");
        lblWallet.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = y;
        formPanel.add(lblWallet, gbc);

        String[] wallets = {"Savings Wallet", "Current Wallet", "Crypto Wallet"};
        cbWalletType = new JComboBox<>(wallets);
        cbWalletType.setFont(font);
        gbc.gridx = 1;
        formPanel.add(cbWalletType, gbc);
        y++;

        // Account Status
        JLabel lblStatus = new JLabel("Account Status");
        lblStatus.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = y;
        formPanel.add(lblStatus, gbc);

        tfStatus = new JTextField(20);
        tfStatus.setFont(font);
        gbc.gridx = 1;
        formPanel.add(tfStatus, gbc);
        y++;

        // Initial Balance
        JLabel lblBalance = new JLabel("Initial Balance");
        lblBalance.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = y;
        formPanel.add(lblBalance, gbc);

        tfBalance = new JTextField(20);
        tfBalance.setFont(font);
        gbc.gridx = 1;
        formPanel.add(tfBalance, gbc);
        y++;

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 247, 250));
        JButton btnRegister = new JButton("Register");
        btnRegister.setFont(font);
        btnRegister.setBackground(new Color(10, 40, 70));
        btnRegister.setForeground(Color.WHITE);
        JButton btnBack = new JButton("Back");
        btnBack.setFont(font);
        btnBack.setBackground(Color.GRAY);
        btnBack.setForeground(Color.WHITE);
        buttonPanel.add(btnRegister);
        buttonPanel.add(btnBack);
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(buttonPanel, gbc);

        // ===== Right Panel for Image =====
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/fifth.png"));
        Image i3 = i1.getImage().getScaledInstance(500, 550, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(i3));
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(new Color(245, 247, 250));
        imagePanel.add(imageLabel);

        // ===== Add Panels to Frame =====
        add(formPanel, BorderLayout.CENTER);
        add(imagePanel, BorderLayout.EAST);

        // ===== Button Actions =====
        btnRegister.addActionListener(e -> {
            String gender = null;
            if (rbMale.isSelected()) gender = "Male";
            else if (rbFemale.isSelected()) gender = "Female";

            String idType = (String) cbIdType.getSelectedItem();
            String idNumber = tfIdNumber.getText();
            String name = tfName.getText();
            String country = tfCountry.getText();
            String walletType = (String) cbWalletType.getSelectedItem();
            String status = tfStatus.getText();
            String balance = tfBalance.getText();

            try {
                Conn c = new Conn();
                String query = "INSERT INTO users(identity_type,identity_number,full_name,gender,country,wallet_type,account_status,initial_balance) "
                        + "VALUES('" + idType + "','" + idNumber + "','" + name + "','" + gender + "','" + country + "','" + walletType + "','" + status + "','" + balance + "')";
                c.s.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "User Registered Successfully");
                new TransactionDesk().setVisible(true);
                setVisible(false);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        btnBack.addActionListener(e -> {
            new TransactionDesk().setVisible(true);
            setVisible(false);
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new RegisterUser();
    }
}