package finledger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {

    JTextField username;
    JPasswordField password;
    JButton login, cancel;

    Login() {

        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        Font font = new Font("Times New Roman", Font.PLAIN, 16);

        // ================= USERNAME =================
        JLabel userLabel = new JLabel("Username");
        userLabel.setBounds(40, 20, 100, 30);
        userLabel.setFont(font);
        add(userLabel);

        username = new JTextField();
        username.setBounds(150, 20, 150, 30);
        username.setFont(font);
        add(username);

        // ================= PASSWORD =================
        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(40, 70, 100, 30);
        passLabel.setFont(font);
        add(passLabel);

        password = new JPasswordField();
        password.setBounds(150, 70, 150, 30);
        password.setFont(font);
        add(password);

        // ================= LOGIN BUTTON =================
        login = new JButton("Login");
        login.setBounds(40, 150, 120, 30);
        login.setBackground(Color.BLACK);
        login.setForeground(Color.WHITE);
        login.setFont(font);
        login.addActionListener(this);
        add(login);

        // ================= CANCEL BUTTON =================
        cancel = new JButton("Cancel");
        cancel.setBounds(180, 150, 120, 30);
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        cancel.setFont(font);
        cancel.addActionListener(this);
        add(cancel);

        // ================= IMAGE =================
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("icons/second.jpg"));
        Image scaledImage = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        JLabel image = new JLabel(new ImageIcon(scaledImage));
        image.setBounds(350, 10, 200, 200);
        add(image);

        // ================= FRAME SETTINGS =================
        setBounds(500, 200, 600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == login) {

            String user = username.getText();
            String pass = new String(password.getPassword());

            try {

                Conn c = new Conn();

                String query = "SELECT * FROM login WHERE username=? AND password=?";
                PreparedStatement pst = c.c.prepareStatement(query);

                pst.setString(1, user);
                pst.setString(2, pass);

                ResultSet rs = pst.executeQuery();

                if (rs.next()) {

                    JOptionPane.showMessageDialog(null, "Login Successful");

                    setVisible(false);
                    new Dashboard();

                } else {

                    JOptionPane.showMessageDialog(null, "Invalid Username or Password");

                }

                rs.close();
                pst.close();
                c.c.close();

            } catch (Exception e) {

                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Database Error");

            }
        }

        if (ae.getSource() == cancel) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Login::new);
    }
}