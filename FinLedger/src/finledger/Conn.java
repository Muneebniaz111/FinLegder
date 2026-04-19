package finledger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

public class Conn {
    public Connection c;    // Connection object
    public Statement s;     // Statement object

    // Constructor
    public Conn() {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establish connection (replace username/password if needed)
            c = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/Finledger", 
                "root", 
                "abcd123!"
            );
            
            // Create Statement object
            s = c.createStatement();
            
            System.out.println("Database connected successfully!");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failed.");
            e.printStackTrace();
        }
    }

    // Optional: Close connection
    public void closeConnection() {
        try {
            if (s != null) s.close();
            if (c != null) c.close();
            System.out.println("Connection closed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Test connection
    public static void main(String[] args) {
        Conn conn = new Conn();
        // You can now use conn.s to execute SQL queries
        conn.closeConnection();
    }
}