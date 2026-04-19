# FinLedger - Digital Wallet System

A comprehensive Java-based digital wallet and financial management system built with NetBeans. FinLedger provides secure user authentication, wallet management, transaction processing, and administrative controls.

## 📋 Features

- **User Management**
  - User registration and authentication
  - Secure login system
  - User profile management
  - Staff directory and management

- **Wallet Operations**
  - Create and manage multiple wallets
  - Real-time wallet status updates
  - Search wallet functionality
  - Transaction history tracking

- **Transaction Processing**
  - Transaction desk for processing payments
  - Department-based transaction management
  - Transaction status monitoring
  - Refund management system

- **Payment Gateway Integration**
  - Integrated payment gateway service
  - Secure payment processing
  - Multi-payment method support

- **Administrative Controls**
  - Admin dashboard
  - Admin information management
  - Staff management and validation
  - System monitoring and reporting

## 🛠 Technology Stack

- **Language**: Java
- **IDE**: NetBeans
- **Database**: MySQL
- **Database Driver**: MySQL Connector/J 9.6.0
- **Architecture**: Desktop Application (Swing/AWT GUI)

## 📋 System Requirements

- Java Runtime Environment (JRE) 8 or higher
- MySQL Server 5.7 or higher
- NetBeans IDE (for development)
- Minimum 2GB RAM
- Windows/Linux/macOS compatible

## 🔧 Installation

### Prerequisites
1. Install Java Development Kit (JDK) 8 or higher
2. Install MySQL Server
3. Install NetBeans IDE

### Steps

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd FinLedger
   ```

2. **Database Setup**
   - Open MySQL command line or MySQL Workbench
   - Execute the SQL script to create the database:
     ```bash
     mysql -u root -p < DataBase/FinLedger.sql
     ```
   - Update database credentials in `Conn.java` if needed

3. **Open in NetBeans**
   - Open NetBeans IDE
   - Go to File → Open Project
   - Navigate to the FinLedger folder
   - Click Open Project

4. **Build and Run**
   - Right-click on the project in NetBeans
   - Select "Clean and Build"
   - Once build is complete, click "Run Project" or press F6

## 📁 Project Structure

```
FinLedger/
├── src/finledger/               # Main source code
│   ├── FinLedger.java          # Application entry point
│   ├── Login.java              # Login screen
│   ├── Dashboard.java          # Main dashboard
│   ├── CreateWallet.java       # Wallet creation module
│   ├── WalletAccounts.java     # Wallet management
│   ├── TransactionDesk.java    # Transaction processing
│   ├── TransactionDepartment.java # Department transactions
│   ├── TransactionStatus.java  # Transaction tracking
│   ├── AdminInfo.java          # Admin panel
│   ├── UserProfile.java        # User profile management
│   ├── AddStaff.java           # Staff management
│   ├── StaffDirectory.java     # Staff listing
│   ├── PaymentGatewayService.java # Payment integration
│   ├── ReFund.java             # Refund processing
│   ├── Conn.java               # Database connection
│   └── AddValidators.java      # Input validation
├── DataBase/                    # Database files
│   ├── FinLedger.sql           # Database schema
│   └── mysql-connector-j-9.6.0/ # MySQL JDBC driver
└── nbproject/                   # NetBeans project configuration

```

## 🔐 Key Classes

- **Conn.java** - Database connection management and SQL operations
- **FinLedger.java** - Main application entry point
- **Login.java** - User authentication
- **Dashboard.java** - Main application interface
- **CreateWallet.java** - New wallet creation
- **TransactionDesk.java** - Transaction processing
- **PaymentGatewayService.java** - Payment processing service
- **AdminInfo.java** - Administrative functions

## 🚀 Usage

1. **Launch the application**
   - Run the project from NetBeans
   - The login screen will appear

2. **User Login**
   - Enter valid credentials
   - Click Login

3. **Navigate Features**
   - Use the dashboard menu to access different modules
   - Create wallets, process transactions, manage accounts

4. **Admin Access**
   - Login with admin credentials
   - Access admin panel for system management

## 🔄 Database Configuration

Update the database connection details in `Conn.java`:

```java
// Database connection parameters
String url = "jdbc:mysql://localhost:3306/finledger";
String username = "root";
String password = "your_password";
```

## 📝 License

[Add your license information here]

## 👥 Contributors

[Add contributor information here]

## 📧 Support

For issues or questions, please contact the development team.

---

**Last Updated**: April 2026
