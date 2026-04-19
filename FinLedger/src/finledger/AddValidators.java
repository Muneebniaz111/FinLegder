package finledger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;

public class AddValidators extends JFrame implements ActionListener {

    JTextField tfName, tfExperience, tfEngineVersion, tfNodeLocation;
    JComboBox<String> cbValidatorType, cbValidationEngine, cbStatus;
    JButton btnAdd, btnCancel;

    public AddValidators() {

        setTitle("Add Validator - FinLedger");
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        Font labelFont = new Font("Times New Roman", Font.PLAIN, 16);
        Font headingFont = new Font("Times New Roman", Font.BOLD, 20);

        // ================= HEADING =================
        JLabel heading = new JLabel("Add New Validator");
        heading.setFont(headingFont);
        heading.setBounds(180, 20, 250, 30);
        add(heading);

        // ================= VALIDATOR NAME =================
        JLabel lblName = new JLabel("Validator Name");
        lblName.setFont(labelFont);
        lblName.setBounds(60, 80, 150, 30);
        add(lblName);

        tfName = new JTextField();
        tfName.setBounds(250, 80, 180, 30);
        add(tfName);

        // ================= EXPERIENCE =================
        JLabel lblExperience = new JLabel("Experience (Years)");
        lblExperience.setFont(labelFont);
        lblExperience.setBounds(60, 130, 150, 30);
        add(lblExperience);

        tfExperience = new JTextField();
        tfExperience.setBounds(250, 130, 180, 30);
        add(tfExperience);

        // ================= VALIDATOR TYPE =================
        JLabel lblValidatorType = new JLabel("Validator Type");
        lblValidatorType.setFont(labelFont);
        lblValidatorType.setBounds(60, 180, 150, 30);
        add(lblValidatorType);

        String[] types = {"Human", "AI_Engine", "BlockChain_Node"};
        cbValidatorType = new JComboBox<>(types);
        cbValidatorType.setBounds(250, 180, 180, 30);
        cbValidatorType.setBackground(Color.WHITE);
        cbValidatorType.setFont(labelFont);
        add(cbValidatorType);

        // ================= VALIDATION ENGINE =================
        JLabel lblEngine = new JLabel("Validation Engine");
        lblEngine.setFont(labelFont);
        lblEngine.setBounds(60, 230, 150, 30);
        add(lblEngine);

        String[] engines = {"RuleBasedEngine", "RiskScoringEngine", "MLDetectionModel"};
        cbValidationEngine = new JComboBox<>(engines);
        cbValidationEngine.setBounds(250, 230, 180, 30);
        cbValidationEngine.setBackground(Color.WHITE);
        cbValidationEngine.setFont(labelFont);
        add(cbValidationEngine);

        // ================= ENGINE VERSION =================
        JLabel lblEngineVersion = new JLabel("Engine Version");
        lblEngineVersion.setFont(labelFont);
        lblEngineVersion.setBounds(60, 280, 150, 30);
        add(lblEngineVersion);

        tfEngineVersion = new JTextField();
        tfEngineVersion.setBounds(250, 280, 180, 30);
        add(tfEngineVersion);

        // ================= STATUS =================
        JLabel lblStatus = new JLabel("Status");
        lblStatus.setFont(labelFont);
        lblStatus.setBounds(60, 330, 150, 30);
        add(lblStatus);

        String[] statuses = {"Active", "Suspended", "Maintenance"};
        cbStatus = new JComboBox<>(statuses);
        cbStatus.setBounds(250, 330, 180, 30);
        cbStatus.setBackground(Color.WHITE);
        cbStatus.setFont(labelFont);
        add(cbStatus);

        // ================= NODE LOCATION =================
        JLabel lblNodeLocation = new JLabel("Node Location");
        lblNodeLocation.setFont(labelFont);
        lblNodeLocation.setBounds(60, 380, 150, 30);
        add(lblNodeLocation);

        tfNodeLocation = new JTextField();
        tfNodeLocation.setBounds(250, 380, 180, 30);
        add(tfNodeLocation);

        // ================= BUTTONS =================
        btnAdd = new JButton("Add Validator");
        btnAdd.setBounds(80, 440, 150, 30);
        btnAdd.setBackground(Color.BLACK);
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(labelFont);
        btnAdd.addActionListener(this);
        add(btnAdd);

        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(260, 440, 150, 30);
        btnCancel.setBackground(Color.BLACK);
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFont(labelFont);
        btnCancel.addActionListener(this);
        add(btnCancel);

        // ================= IMAGE =================
        ImageIcon icon = new ImageIcon(getClass().getResource("/icons/eleven.png"));
        Image img = icon.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
        JLabel lblImage = new JLabel(new ImageIcon(img));
        lblImage.setBounds(500, 120, 400, 300);
        add(lblImage);

        // ================= FRAME SETTINGS =================
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == btnAdd) {
            try {

                String name = tfName.getText();
                int experience = Integer.parseInt(tfExperience.getText());
                String type = (String) cbValidatorType.getSelectedItem();
                String engine = (String) cbValidationEngine.getSelectedItem();
                String engineVersion = tfEngineVersion.getText();
                String status = (String) cbStatus.getSelectedItem();
                String location = tfNodeLocation.getText();

                Conn conn = new Conn();

                String query = "INSERT INTO Validator " +
                        "(validator_name, experience_years, validator_type, validation_engine, engine_version, status, node_location) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement pst = conn.c.prepareStatement(query);

                pst.setString(1, name);
                pst.setInt(2, experience);
                pst.setString(3, type);
                pst.setString(4, engine);
                pst.setString(5, engineVersion);
                pst.setString(6, status);
                pst.setString(7, location);

                pst.executeUpdate();

                JOptionPane.showMessageDialog(null, "Validator Added Successfully!");
                setVisible(false);

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
        SwingUtilities.invokeLater(AddValidators::new);
    }
}