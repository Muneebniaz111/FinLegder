package finledger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class FinLedger extends JFrame implements ActionListener {

    JButton next;

    FinLedger() {
        setTitle("FinLedger");
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        URL imageUrl = FinLedger.class.getResource("/icons/first.jpg");

        if (imageUrl != null) {

            ImageIcon originalIcon = new ImageIcon(imageUrl);
            Image scaledImage = originalIcon.getImage().getScaledInstance(
                    getWidth(), getHeight(), Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            JLabel imageLabel = new JLabel(scaledIcon);
            imageLabel.setBounds(0, 0, getWidth(), getHeight());
            imageLabel.setLayout(null);
            add(imageLabel);

            JLabel text = new JLabel(
                    "FinLedger – Distributed Transaction & Wallet Service");
            text.setBounds(20, 430, 1000, 90);
            text.setForeground(Color.WHITE);
            text.setFont(new Font("Serif", Font.PLAIN, 35));
            imageLabel.add(text);

            // Blinking Text
            Timer blinkTimer = new Timer(600,
                    e -> text.setVisible(!text.isVisible()));
            blinkTimer.start();

            // Next Button
            next = new JButton("Next");
            next.setBounds(1000, 450, 150, 40);
            next.setBackground(Color.WHITE);
            next.setForeground(Color.BLACK);
            next.setFont(new Font("Serif", Font.PLAIN, 24));
            next.addActionListener(this);
            imageLabel.add(next);

        } else {
            JLabel errorLabel = new JLabel(
                    "Image not found!", SwingConstants.CENTER);
            errorLabel.setFont(errorLabel.getFont().deriveFont(32.0f));
            errorLabel.setBounds(0, 0, getWidth(), getHeight());
            add(errorLabel);
        }

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
        new Login();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FinLedger::new);
    }
}