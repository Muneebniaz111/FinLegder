package finledger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Dashboard extends JFrame implements ActionListener{

    Dashboard() {

        setTitle("FinLedger Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(null);

        // Get screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Load background image
        ImageIcon icon = new ImageIcon(
                ClassLoader.getSystemResource("icons/third.jpg"));

        Image scaledImage = icon.getImage().getScaledInstance(
                screenSize.width,
                screenSize.height,
                Image.SCALE_SMOOTH);

        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JLabel image = new JLabel(scaledIcon);
        image.setBounds(0, 0, screenSize.width, screenSize.height);
        image.setLayout(null);
        add(image);

        // Welcome Text
        JLabel text = new JLabel("THE FIN-LEDGER GROUP WELCOMES YOU");
        text.setBounds(200, 80, 1000, 50);
        text.setFont(new Font("Montserrat Bold", Font.PLAIN, 46));
        text.setForeground(Color.WHITE);
        image.add(text);

        // ===== MENU BAR =====
        JMenuBar mb = new JMenuBar();
        mb.setBounds(0, 0, screenSize.width, 60);
        mb.setBackground(new Color(10, 25, 74));   // Dark theme
        mb.setOpaque(true);
        mb.setBorderPainted(false);
        image.add(mb);

        // FIN-LEDGER Menu
        JMenu hotel = new JMenu("FIN-LEDGER");
        hotel.setForeground(new Color(0, 128, 0));
        hotel.setFont(new Font("Times New Roman Bold", Font.PLAIN, 20));
        mb.add(hotel);

        JMenuItem reception = new JMenuItem("TRANSACTION-DESK");
        reception.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        reception.addActionListener(this);
        hotel.add(reception);

        // ADMIN Menu
        JMenu admin = new JMenu("ADMIN");
        admin.setForeground(new Color(140, 146, 172));
        admin.setFont(new Font("Times New Roman Bold", Font.PLAIN, 18));
        mb.add(admin);

        JMenuItem addstaff = new JMenuItem("ADD-STAFF");
        addstaff.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        addstaff.addActionListener(this);
        admin.add(addstaff);

        JMenuItem createwallet = new JMenuItem("CREATE-WALLET");
        createwallet.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        createwallet.addActionListener(this);
        admin.add(createwallet);

        JMenuItem addvalidators = new JMenuItem("ADD-VALIDATORS");
        addvalidators.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        addvalidators.addActionListener(this);
        admin.add(addvalidators);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if(ae.getActionCommand().equals("ADD-STAFF"))
        {
            new AddStaff();
        }else if(ae.getActionCommand().equals("CREATE-WALLET"))
        {
            new CreateWallet();
        }
        else if(ae.getActionCommand().equals("ADD-VALIDATORS"))
        {
            new AddValidators();
        }        
        else if(ae.getActionCommand().equals("TRANSACTION-DESK"))
        {
            new TransactionDesk();
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Dashboard::new);
    }
}