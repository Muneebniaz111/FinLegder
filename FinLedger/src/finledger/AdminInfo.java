package finledger;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class AdminInfo extends JFrame {

    private JPanel contentPane;
    private JTable table;

    // 🔐 Master Password
    private static final String MASTER_PASSWORD = "admin@123";

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                AdminInfo frame = new AdminInfo();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public AdminInfo() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 520);

        // ✅ CENTER FRAME
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // TABLE
        table = new JTable();
        table.setBounds(0, 34, 800, 300);
        contentPane.add(table);

        // LOAD BUTTON
        JButton btnLoad = new JButton("Load Admins");
        btnLoad.setBounds(150, 400, 130, 30);
        styleButton(btnLoad);
        contentPane.add(btnLoad);
        btnLoad.addActionListener(e -> loadAdmins());

        // ADD BUTTON
        JButton btnAdd = new JButton("Add Admin");
        btnAdd.setBounds(300, 400, 130, 30);
        styleButton(btnAdd);
        contentPane.add(btnAdd);
        btnAdd.addActionListener(e -> addAdmin());

        // DELETE BUTTON
        JButton btnDelete = new JButton("Delete Admin");
        btnDelete.setBounds(450, 400, 140, 30);
        styleButton(btnDelete);
        contentPane.add(btnDelete);
        btnDelete.addActionListener(e -> deleteAdmin());

        // BACK BUTTON
        JButton btnBack = new JButton("Back");
        btnBack.setBounds(610, 400, 120, 30);
        styleButton(btnBack);
        contentPane.add(btnBack);
        btnBack.addActionListener(e -> {
            new TransactionDesk().setVisible(true);
            setVisible(false);
        });

        // HEADERS
        addLabel("Username", 200);
        addLabel("Password", 450);

        getContentPane().setBackground(Color.WHITE);

        // AUTO LOAD
        loadAdmins();
    }

    // -------- LOAD ADMINS --------
    private void loadAdmins() {
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT username, password FROM login");
            table.setModel(DbUtils.resultSetToTableModel(rs));
            c.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -------- ADD ADMIN --------
    private void addAdmin() {

        JTextField username = new JTextField();
        JPasswordField password = new JPasswordField();

        Object[] fields = {
            "Username:", username,
            "Password:", password
        };

        int option = JOptionPane.showConfirmDialog(
                this, fields, "Add Admin", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                String user = username.getText().trim();
                String pass = new String(password.getPassword());

                if (user.isEmpty() || pass.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Fields cannot be empty!");
                    return;
                }

                Conn c = new Conn();

                String checkQuery = "SELECT * FROM login WHERE username = ?";
                PreparedStatement checkStmt = c.c.prepareStatement(checkQuery);
                checkStmt.setString(1, user);

                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(this,
                            "Username already exists! Try another.");
                    c.closeConnection();
                    return;
                }

                String insertQuery = "INSERT INTO login (username, password) VALUES (?, ?)";
                PreparedStatement ps = c.c.prepareStatement(insertQuery);

                ps.setString(1, user);
                ps.setString(2, pass);

                ps.executeUpdate();

                JOptionPane.showMessageDialog(this, "Admin Added Successfully");

                c.closeConnection();
                loadAdmins();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error Adding Admin");
            }
        }
    }

    // -------- DELETE ADMIN --------
    private void deleteAdmin() {

        JPasswordField masterPass = new JPasswordField();

        int auth = JOptionPane.showConfirmDialog(
                this,
                new Object[]{"Enter Master Password:", masterPass},
                "Authentication Required",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (auth != JOptionPane.OK_OPTION) return;

        String enteredMaster = new String(masterPass.getPassword());

        if (!enteredMaster.equals(MASTER_PASSWORD)) {
            JOptionPane.showMessageDialog(this, "Incorrect Master Password!");
            return;
        }

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        Object[] fields = {
            "Username:", usernameField,
            "Password:", passwordField
        };

        int input = JOptionPane.showConfirmDialog(
                this,
                fields,
                "Enter Admin Credentials to Delete",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (input != JOptionPane.OK_OPTION) return;

        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fields cannot be empty!");
            return;
        }

        try {
            Conn c = new Conn();

            String sql = "DELETE FROM login WHERE username = ? AND password = ? LIMIT 1";
            PreparedStatement ps = c.c.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Admin Deleted Successfully");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password!");
            }

            c.closeConnection();
            loadAdmins();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -------- UI HELPERS --------
    private void addLabel(String text, int x) {
        JLabel label = new JLabel(text);
        label.setBounds(x, 11, 100, 14);
        contentPane.add(label);
    }

    private void styleButton(JButton btn) {
        btn.setBackground(Color.BLACK);
        btn.setForeground(Color.WHITE);
    }
}