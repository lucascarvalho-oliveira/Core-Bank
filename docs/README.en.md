<p align="center">
  <a href="../README.md">
    <img src="https://img.shields.io/badge/Language-Português-green?style=for-the-badge">
  </a>
</p>

# CoreBank

Banking system developed in Java using layered architecture and MySQL for data persistence.

The project simulates digital banking features, allowing account creation, login, financial transactions, Pix key management, and transaction history tracking.

# Technologies Used

* Java
* MySQL
* JDBC
* Maven
* Object-Oriented Programming (OOP)

# Project Structure

```bash
src/
 ├── application/
 │    ├── Main.java
 │    └── controller/
 │
 ├── database/
 │    └── Conexao.java
 │
 ├── model/
 │    ├── Conta.java
 │    ├── ContaCorrente.java
 │    ├── ContaPoupanca.java
 │    ├── Pessoa.java
 │    ├── Pix.java
 │    ├── Transacao.java
 │    └── enums/
 │
 ├── repository/
 │
 └── service/
```

# Features

## Bank Account

* Current account creation
* Savings account creation
* Password login
* Balance inquiry
* Deposit
* Withdrawal
* Transfer
* Current account overdraft usage
* Transaction history

## Pix

* Pix key registration
* Pix key lookup
* Pix transfers

## Security

* CPF/CNPJ validation
* User authentication with password

# Database

## Database Creation

```sql
CREATE DATABASE db_CoreBank;
USE db_CoreBank;

CREATE TABLE pessoa(
id_pessoa INT PRIMARY KEY AUTO_INCREMENT,
nome VARCHAR(45) NOT NULL,
documento VARCHAR(15) NOT NULL,
tipo_pessoa VARCHAR(15) NOT NULL,
senha VARCHAR(50) NOT NULL
);

CREATE TABLE conta(
id_conta INT PRIMARY KEY AUTO_INCREMENT,
saldo DECIMAL(15, 2) NOT NULL DEFAULT 0.00,
limite DECIMAL(15, 2) NOT NULL DEFAULT 0.00,
tipo_conta VARCHAR(15) NOT NULL,
id_pessoa_fk INT,

FOREIGN KEY(id_pessoa_fk) REFERENCES pessoa(id_pessoa)
);

CREATE TABLE pix(
id_pix INT PRIMARY KEY AUTO_INCREMENT,
chave VARCHAR(45) NOT NULL,
tipo_pix VARCHAR(10) NOT NULL,
id_conta_fk INT,

FOREIGN KEY(id_conta_fk) REFERENCES conta(id_conta) ON DELETE CASCADE
);

CREATE TABLE transacao(
id_transacao INT PRIMARY KEY AUTO_INCREMENT,
data_hora DATETIME(0) NOT NULL,
valor DECIMAL(15, 2) NOT NULL,
saldo_conta DECIMAL(15, 2) NOT NULL,
tipo_transacao VARCHAR(45) NOT NULL,
id_conta_fk INT,

FOREIGN KEY(id_conta_fk) REFERENCES conta(id_conta)
);

CREATE TABLE investimento(
id_investimento INT PRIMARY KEY AUTO_INCREMENT,
valor DECIMAL(15, 2) NOT NULL,
data_hora DATETIME(0) NOT NULL,
id_conta_fk INT,

FOREIGN KEY(id_conta_fk) REFERENCES conta(id_conta)
);
```

# Project Setup

## 1. Clone the repository

```bash
git clone https://github.com/your-username/CoreBank.git
```

## 2. Configure the database

Run the SQL script above in MySQL.

## 3. Configure the database connection

File:

```bash
src/database/Conexao.java
```

Example:

```java
String url = "jdbc:mysql://localhost:3306/db_CoreBank";
String user = "root";
String password = "your_password";
```

## 4. Run the project

Using IntelliJ IDEA or another Java IDE.

Main class:

```bash
application/Main.java
```

# Applied Concepts

* Object-Oriented Programming
* Inheritance
* Polymorphism
* Encapsulation
* Abstraction
* Layered Architecture
* DAO/Repository Pattern
* Exception Handling
* Enumerations
* Data Persistence with JDBC

# Account Types

## Current Account

* Has overdraft limit
* Allows negative balance usage
* Can apply fees on overdraft usage

## Savings Account

* Does not use overdraft limit
* Operations limited to available balance

# Future Improvements

* Graphical interface
* REST API with Spring Boot
* Password encryption
* Automated tests
* JWT authentication
* Docker
* Cloud deployment

# Author

Developed by Lucas Carvalho.
