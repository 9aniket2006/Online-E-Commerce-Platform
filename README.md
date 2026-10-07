# Online E-Commerce Platform

## Project Overview

The Online E-Commerce Platform is a Java-based desktop application developed to provide a simple online shopping system.

The system supports three types of users:

- Admin
- Seller
- Buyer

The application uses Java Swing for the graphical user interface and Oracle Database for storing and managing data.

## Features

### Admin
- Manage users
- Manage products
- View all customer orders

### Seller
- Add products
- View own products
- Manage product inventory
- View orders related to their products

### Buyer
- View available products
- Add products to cart
- Place orders
- View order history

## Technologies Used

- Java
- Java Swing
- JDBC
- Oracle Database 11g XE
- SQL
- Git & GitHub
- VS Code

## Project Structure

```text
Online-E-Commerce-Platform/
│
├── SRC/
│   ├── LoginFrame.java
│   ├── RegisterFrame.java
│   ├── BuyerDashboard.java
│   ├── SellerDashboard.java
│   ├── AdminDashboard.java
│   ├── ProductFrame.java
│   ├── CartFrame.java
│   ├── OrdersFrame.java
│   ├── SellerProductsFrame.java
│   ├── AddProductFrame.java
│   ├── SellerOrdersFrame.java
│   ├── AdminUsersFrame.java
│   ├── AdminProductsFrame.java
│   ├── AdminOrdersFrame.java
│   └── DBConnection.java
│
├── database/
│   └── database.sql
│
├── screenshots/
│
├── lib/
│   └── ojdbc17.jar
│
└── README.md

## Database

The project uses Oracle Database 11g XE for storing and managing application data.

### Main Tables

- USERS
- PRODUCTS
- ORDERS
- ORDER_ITEMS
- CART

### Database Script

The complete database setup script is available in:

`database/database.sql`

## JDBC Configuration

The application uses JDBC to connect Java with Oracle Database.

### Database Connection

```text
jdbc:oracle:thin:@localhost:1521:XE

## Requirements

Before running the project, make sure you have:

- Java JDK installed
- Oracle Database 11g XE installed
- Oracle JDBC Driver (`ojdbc17.jar`)
- VS Code or any Java IDE

## Database Setup

1. Start Oracle Database 11g XE.

2. Open your Oracle SQL environment.

3. Connect to your Oracle database.

4. Open the following file:

`database/database.sql`

5. Execute the SQL script.

The script creates the required tables, sequences, and sample data.

## Compile and Run

### Compile

Open the terminal inside the `SRC` folder and run:

```powershell
javac -cp ".;..\lib\ojdbc17.jar" *.java
java -cp ".;..\lib\ojdbc17.jar" LoginFrame

## Sample Login Credentials

### Admin

```text
Email: admin@gmail.com
Password: admin123

Email: seller@gmail.com
Password: seller123

Email: buyer@gmail.com
Password: buyer123

## OOP Concepts Used

The project demonstrates the following Object-Oriented Programming concepts:

- Classes and Objects
- Inheritance
- Polymorphism
- Interface
- Encapsulation
- Exception Handling


## Collections and Generics

The project uses Java Collections and Generics for managing product data.

Technologies/concepts used:

- ArrayList
- List
- Generics

Example:

```java
ProductList<String>

Login / Register
       |
       v
   User Role
       |
   +---+---+
   |   |   |
   v   v   v
Admin Seller Buyer
 |     |     |
 v     v     v
Manage Add   Browse
Users  Product Products
 |     |     |
Products     Cart
 |     |     |
Orders Orders Place Order
              |
              v
         Order History