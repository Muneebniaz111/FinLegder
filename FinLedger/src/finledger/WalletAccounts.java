package finledger;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import net.proteanit.sql.DbUtils;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;

public class WalletAccounts extends JFrame implements ActionListener {

    JTable table;
    JButton btnLoad, btnBack;

    public WalletAccounts() {

        setTitle("FinLedger - Wallet Accounts");
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        Font headingFont = new Font("Times New Roman", Font.BOLD, 22);
        Font labelFont = new Font("Times New Roman", Font.PLAIN, 16);

        // ================= HEADING =================
        JLabel heading = new JLabel("Wallet Accounts");
        heading.setFont(headingFont);
        heading.setBounds(420, 20, 250, 40);
        add(heading);

        // ================= TABLE =================
        table = new JTable();
        table.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 15));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(40, 100, 700, 350);
        add(scrollPane);

        // ================= COLUMN HEADERS =================
        JLabel lblWalletNumber = new JLabel("Wallet Number");
        lblWalletNumber.setFont(labelFont);
        lblWalletNumber.setBounds(50, 70, 120, 20);
        add(lblWalletNumber);

        JLabel lblOwnerName = new JLabel("Owner Name");
        lblOwnerName.setFont(labelFont);
        lblOwnerName.setBounds(180, 70, 120, 20);
        add(lblOwnerName);

        JLabel lblBalance = new JLabel("Balance");
        lblBalance.setFont(labelFont);
        lblBalance.setBounds(310, 70, 100, 20);
        add(lblBalance);

        JLabel lblType = new JLabel("Wallet Type");
        lblType.setFont(labelFont);
        lblType.setBounds(420, 70, 120, 20);
        add(lblType);

        JLabel lblStatus = new JLabel("Status");
        lblStatus.setFont(labelFont);
        lblStatus.setBounds(540, 70, 100, 20);
        add(lblStatus);

        JLabel lblDate = new JLabel("Created On");
        lblDate.setFont(labelFont);
        lblDate.setBounds(640, 70, 100, 20);
        add(lblDate);

        // ================= BUTTONS =================
        btnLoad = new JButton("Load Wallets");
        btnLoad.setBounds(200, 480, 150, 35);
        btnLoad.setBackground(Color.BLACK);
        btnLoad.setForeground(Color.WHITE);
        btnLoad.setFont(labelFont);
        btnLoad.addActionListener(this);
        add(btnLoad);

        btnBack = new JButton("Back");
        btnBack.setBounds(400, 480, 150, 35);
        btnBack.setBackground(Color.BLACK);
        btnBack.setForeground(Color.WHITE);
        btnBack.setFont(labelFont);
        btnBack.addActionListener(this);
        add(btnBack);

        // ================= IMAGE =================
        ImageIcon icon = new ImageIcon(getClass().getResource("/icons/twelve.jpg"));
        Image img = icon.getImage().getScaledInstance(350, 300, Image.SCALE_SMOOTH);
        JLabel image = new JLabel(new ImageIcon(img));
        image.setBounds(760, 120, 350, 300);
        add(image);

        // ================= FRAME SETTINGS =================
        setSize(1150, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // ================= BUTTON EVENTS =================
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == btnLoad) {

            try {

                Conn conn = new Conn();

                String query = "SELECT wallet_number, owner_name, balance, wallet_type, status, created_on FROM Wallets";

                ResultSet rs = conn.s.executeQuery(query);

                table.setModel(DbUtils.resultSetToTableModel(rs));

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (ae.getSource() == btnBack) {

            new TransactionDesk().setVisible(true);
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WalletAccounts::new);
    }
}