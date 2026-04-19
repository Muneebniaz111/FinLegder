DROP DATABASE IF EXISTS finlegder;
DROP DATABASE IF EXISTS Finledger;

show databases;

CREATE DATABASE Finledger;

USE Finledger;

create table login(username varchar(25), password varchar(25));

insert into login values('muneeb','mn123@');

select * from login;

create table Staff (
    name varchar(100) NOT NULL,
    dob DATE NOT NULL,
    gender ENUM('Male','Female','Other') NOT NULL,
    designation VARCHAR(100) NOT NULL,
    salary DECIMAL(15,2) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100),
    cnic VARCHAR(15) UNIQUE NOT NULL
);
describe Staff;
select * from Staff;

create table Wallets (
    wallet_number VARCHAR(50) PRIMARY KEY,
    owner_name VARCHAR(100),
    balance DECIMAL(15,2),
    wallet_type VARCHAR(50),
    status VARCHAR(20),
    created_on DATE
);
describe Wallets;
select * from Wallets;

create table Validator (
    validator_id INT AUTO_INCREMENT PRIMARY KEY,
    validator_name VARCHAR(100),
    experience_years INT,
    validator_type VARCHAR(50),
    validation_engine VARCHAR(100),
    engine_version VARCHAR(50),
    status VARCHAR(50),
    node_location VARCHAR(100)
);
describe Validator;
select * from Validator;

create table users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    identity_type VARCHAR(50) NOT NULL,
    identity_number VARCHAR(50) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    gender VARCHAR(10),
    country VARCHAR(50),
    wallet_type VARCHAR(50),
    account_status VARCHAR(50),
    initial_balance DECIMAL(15,2) DEFAULT 0.00,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
describe users;
select * from users;

CREATE TABLE payment (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    wallet_number VARCHAR(50),
    amount DECIMAL(15,2),
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (wallet_number) REFERENCES Wallets(wallet_number)
);
DESCRIBE payment;
select * from payment;

CREATE TABLE refunds (
    refund_id INT AUTO_INCREMENT PRIMARY KEY,
    wallet_number VARCHAR(50),
    amount DECIMAL(15,2),
    refund_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (wallet_number) REFERENCES Wallets(wallet_number)
);
DESCRIBE refunds;
select * from refunds;

create table transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    wallet_number VARCHAR(50),
    amount DECIMAL(15,2),
    transaction_type VARCHAR(20),
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
describe transactions;
select * from transactions;

CREATE TABLE transaction_department (
    id INT AUTO_INCREMENT PRIMARY KEY,
    module_name VARCHAR(50) NOT NULL,
    budget DECIMAL(15,2) NOT NULL
);

INSERT INTO transaction_department (module_name, budget) VALUES
('Settlement', 500000.00),
('Compliance', 250000.00),
('Risk', 300000.00),
('Payments', 450000.00);

select * from transaction_department;