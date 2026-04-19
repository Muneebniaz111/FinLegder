package finledger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TransactionDesk extends JFrame implements ActionListener {

    private JPanel contentPane;

    // Buttons
    JButton btnRegisterUser, btnUserProfile, btnWallet, btnFundTransfer,
            btnSearchWallet, btnUpdateWallet, btnUpdateTransaction,
            btnPaymentGateway, btnDepartment, btnStaff, btnAdmin,
            btnNotification, btnLogout;

    public static void main(String[] args) {
        new TransactionDesk();
    }

    public TransactionDesk() {

        setTitle("FinLedger Transaction Desk");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);

        Font btnFont = new Font("Times New Roman", Font.PLAIN, 16);

        // Background Image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/fourth.png"));
        Image i3 = i1.getImage().getScaledInstance(650, 520, Image.SCALE_SMOOTH);
        JLabel image = new JLabel(new ImageIcon(i3));
        image.setBounds(300, 40, 650, 520);
        contentPane.add(image);

        // ===== Create Buttons =====
        btnRegisterUser = createButton("Register User", 30, btnFont);
        btnUserProfile = createButton("User Profile", 70, btnFont);
        btnWallet = createButton("Wallet Accounts", 110, btnFont);
        btnFundTransfer = createButton("Fund Transfer", 150, btnFont);
        btnSearchWallet = createButton("Search Wallet", 190, btnFont);
        btnUpdateWallet = createButton("Update Wallet Status", 230, btnFont);
        btnUpdateTransaction = createButton("Update Transaction Status", 270, btnFont);
        btnPaymentGateway = createButton("Payment Gateway Service", 310, btnFont);
        btnDepartment = createButton("Transaction Departments", 350, btnFont);
        btnStaff = createButton("Staff Directory", 390, btnFont);
        btnAdmin = createButton("Admin Information", 430, btnFont);
        btnNotification = createButton("Notifications", 470, btnFont);
        btnLogout = createButton("Logout", 510, btnFont);
        btnLogout.setBackground(Color.RED);

        // ===== Add Buttons to Content Pane =====
        contentPane.add(btnRegisterUser);
        contentPane.add(btnUserProfile);
        contentPane.add(btnWallet);
        contentPane.add(btnFundTransfer);
        contentPane.add(btnSearchWallet);
        contentPane.add(btnUpdateWallet);
        contentPane.add(btnUpdateTransaction);
        contentPane.add(btnPaymentGateway);
        contentPane.add(btnDepartment);
        contentPane.add(btnStaff);
        contentPane.add(btnAdmin);
        contentPane.add(btnNotification);
        contentPane.add(btnLogout);

        contentPane.setBackground(Color.WHITE);
        setVisible(true);
    }

    // Helper method to create a button and register ActionListener
    private JButton createButton(String text, int yPosition, Font font) {
        JButton btn = new JButton(text);
        btn.setBounds(10, yPosition, 230, 35);
        btn.setBackground(Color.BLACK);
        btn.setForeground(Color.WHITE);
        btn.setFont(font);
        btn.addActionListener(this); // Register action listener
        return btn;
    }

    // Handle button clicks
    @Override
    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();

        switch (command) {
            case "Register User":
                new RegisterUser().setVisible(true);
                setVisible(false);
                break;
            case "User Profile":
                new UserProfile().setVisible(true);
                setVisible(false);
                break;
            case "Wallet Accounts":
                new WalletAccounts().setVisible(true);
                setVisible(false);
                break;
            case "Search Wallet":
                new SearchWallet().setVisible(true);
                setVisible(false);
                break;
            case "Fund Transfer":
                new ReFund().setVisible(true);
                setVisible(false);
                break;
            case "Update Wallet Status":
                new UpdateWalletStatus().setVisible(true);
                setVisible(false);
                break;
            case "Update Transaction Status":
                new TransactionStatus().setVisible(true);
                setVisible(false);
                break;
            case "Payment Gateway Service":
                new PaymentGatewayService().setVisible(true);
                setVisible(false);
                break; 
            case "Transaction Departments":
                new TransactionDepartment().setVisible(true);
                setVisible(false);
                break; 
            case "Staff Directory":
                new StaffDirectory().setVisible(true);
                setVisible(false);
                break;
            case "Admin Information":
                new AdminInfo().setVisible(true);
                setVisible(false);
                break;
            case "Logout":
                new FinLedger().setVisible(true);
                setVisible(false);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Unknown action: " + command);
        }
    }
}