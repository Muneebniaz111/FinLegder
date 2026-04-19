package finledger;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.*;

public class AddStaff extends JFrame implements ActionListener {

    JTextField tfName, tfPhone, tfEmail, tfDob, tfSalary;
    JFormattedTextField tfCnic;
    JRadioButton rbMale, rbFemale, rbOther;
    JButton submit, Cancel;
    JComboBox<String> cbDesignation;

    public AddStaff() {
        setTitle("Add Staff - FinLedger");
        setLayout(null);

        Font labelFont = new Font("Times New Roman", Font.PLAIN, 17);
        Font fieldFont = new Font("Times New Roman", Font.PLAIN, 15);

        // ================= STAFF NAME =================
        JLabel lblName = new JLabel("STAFF NAME");
        lblName.setBounds(60, 30, 180, 30);
        lblName.setFont(labelFont);
        add(lblName);

        tfName = new JTextField();
        tfName.setBounds(250, 30, 250, 30);
        tfName.setFont(fieldFont);
        add(tfName);

        // ================= DATE OF BIRTH =================
        JLabel lblDob = new JLabel("DATE OF BIRTH");
        lblDob.setBounds(60, 80, 200, 30);
        lblDob.setFont(labelFont);
        add(lblDob);

        tfDob = new JTextField();
        tfDob.setBounds(250, 80, 250, 30);
        tfDob.setFont(fieldFont);
        add(tfDob);

        // ================= GENDER =================
        JLabel lblGender = new JLabel("GENDER");
        lblGender.setBounds(60, 130, 180, 30);
        lblGender.setFont(labelFont);
        add(lblGender);

        rbMale = new JRadioButton("Male");
        rbMale.setBounds(250, 130, 80, 30);
        rbMale.setFont(labelFont);
        rbMale.setBackground(Color.WHITE);

        rbFemale = new JRadioButton("Female");
        rbFemale.setBounds(340, 130, 100, 30);
        rbFemale.setFont(labelFont);
        rbFemale.setBackground(Color.WHITE);

        rbOther = new JRadioButton("Other");
        rbOther.setBounds(450, 130, 100, 30);
        rbOther.setFont(labelFont);
        rbOther.setBackground(Color.WHITE);

        ButtonGroup bgGender = new ButtonGroup();
        bgGender.add(rbMale);
        bgGender.add(rbFemale);
        bgGender.add(rbOther);
        rbMale.setSelected(true);

        add(rbMale);
        add(rbFemale);
        add(rbOther);

        // ================= DESIGNATION =================
        JLabel lblDesignation = new JLabel("DESIGNATION");
        lblDesignation.setBounds(60, 180, 180, 30);
        lblDesignation.setFont(labelFont);
        add(lblDesignation);

        String[] roles = {
                "Teller – Transaction Processing Officer",
                "Account Manager – Client Relationship Officer",
                "Admin – System Administrator",
                "Risk Officer – Compliance & Risk Control",
                "Blockchain Validator – Network Validator Node",
                "Wallet Support Officer – Customer Support",
                "Finance Officer – Treasury Operations",
                "Security Analyst – Cybersecurity Monitoring"
        };

        cbDesignation = new JComboBox<>(roles);
        cbDesignation.setBounds(250, 180, 250, 30);
        cbDesignation.setFont(fieldFont);
        cbDesignation.setMaximumRowCount(5);
        add(cbDesignation);

        // ================= SALARY =================
        JLabel lblSalary = new JLabel("SALARY");
        lblSalary.setBounds(60, 230, 180, 30);
        lblSalary.setFont(labelFont);
        add(lblSalary);

        tfSalary = new JTextField();
        tfSalary.setBounds(250, 230, 250, 30);
        tfSalary.setFont(fieldFont);
        add(tfSalary);

        // ================= PHONE =================
        JLabel lblPhone = new JLabel("PHONE NUMBER");
        lblPhone.setBounds(60, 280, 180, 30);
        lblPhone.setFont(labelFont);
        add(lblPhone);

        tfPhone = new JTextField();
        tfPhone.setBounds(250, 280, 250, 30);
        tfPhone.setFont(fieldFont);
        add(tfPhone);

        // ================= EMAIL =================
        JLabel lblEmail = new JLabel("EMAIL ADDRESS");
        lblEmail.setBounds(60, 330, 180, 30);
        lblEmail.setFont(labelFont);
        add(lblEmail);

        tfEmail = new JTextField();
        tfEmail.setBounds(250, 330, 250, 30);
        tfEmail.setFont(fieldFont);
        add(tfEmail);

        // ================= CNIC =================
        JLabel lblCnic = new JLabel("CNIC NUMBER");
        lblCnic.setBounds(60, 380, 180, 30);
        lblCnic.setFont(labelFont);
        add(lblCnic);

        try {
            MaskFormatter cnicFormatter = new MaskFormatter("#####-#######-#");
            cnicFormatter.setPlaceholderCharacter('_');
            tfCnic = new JFormattedTextField(cnicFormatter);
            tfCnic.setBounds(250, 380, 250, 30);
            tfCnic.setFont(fieldFont);
            add(tfCnic);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // ================= IMAGE =================
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/icons/tenth.png"));
            Image img = icon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
            JLabel lblImage = new JLabel(new ImageIcon(img));
            lblImage.setBounds(620, 30, 400, 400);
            add(lblImage);
        } catch (Exception ex) {
            System.out.println("Image not found! Skipping image.");
        }

        // ================= BUTTONS =================
        submit = new JButton("Submit");
        submit.setBounds(100, 450, 160, 40);
        submit.setBackground(Color.BLACK);
        submit.setForeground(Color.WHITE);
        submit.setFont(labelFont);
        submit.addActionListener(this);
        add(submit);

        Cancel = new JButton("Cancel");
        Cancel.setBounds(300, 450, 160, 40);
        Cancel.setBackground(Color.BLACK);
        Cancel.setForeground(Color.WHITE);
        Cancel.setFont(labelFont);
        Cancel.addActionListener(this);
        add(Cancel);

        // ================= FRAME SETTINGS =================
        getContentPane().setBackground(Color.WHITE);
        setSize(1050, 550);
        setLocationRelativeTo(null); // center
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {   // Submit button
            String name = tfName.getText();
            String dob = tfDob.getText();
            String salaryStr = tfSalary.getText();
            String phone = tfPhone.getText();
            String email = tfEmail.getText();
            String cnic = tfCnic.getText();
            String gender = rbMale.isSelected() ? "Male"
                    : rbFemale.isSelected() ? "Female"
                    : "Other";
            String designation = (String) cbDesignation.getSelectedItem();

            // ===== Validate salary =====
            java.math.BigDecimal salary;
            try {
                salary = new java.math.BigDecimal(salaryStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Salary must be a number!");
                return;
            }

            // ===== Validate date =====
            java.sql.Date sqlDate;
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date parsedDate = sdf.parse(dob);
                sqlDate = new java.sql.Date(parsedDate.getTime());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid Date format! Use DD/MM/YYYY");
                return;
            }

            // ===== Insert into database =====
            try {
                Conn conn = new Conn(); // your connection class
                String query = "INSERT INTO Staff (name, dob, gender, designation, salary, phone, email, cnic) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = conn.c.prepareStatement(query);
                pst.setString(1, name);
                pst.setDate(2, sqlDate);
                pst.setString(3, gender);
                pst.setString(4, designation);
                pst.setBigDecimal(5, salary);
                pst.setString(6, phone);
                pst.setString(7, email);
                pst.setString(8, cnic);

                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "New Staff added successfully!");
                setVisible(false);

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }

        } else if (ae.getSource() == Cancel) { // Cancel button
            new Dashboard().setVisible(true);
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AddStaff::new);
    }
}