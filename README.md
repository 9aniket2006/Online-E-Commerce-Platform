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
├── lib/
│   └── ojdbc17.jar
│
└── README.md
```

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
```

### JDBC Driver

The Oracle JDBC driver used in the project is:

`ojdbc17.jar`

The driver is stored inside:

`lib/`

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
```

### Run

After successful compilation, run:

```powershell
java -cp ".;..\lib\ojdbc17.jar" LoginFrame
```

The Login screen will open.

## Sample Login Credentials

### Admin

```text
Email: admin@gmail.com
Password: admin123
```

### Seller

```text
Email: seller@gmail.com
Password: seller123
```

### Buyer

```text
Email: buyer@gmail.com
Password: buyer123
```

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

Concepts used:

- ArrayList
- List
- Generics

Example:

```java
ProductList<String>
```

## Multithreading and Synchronization

The project demonstrates multithreading using Java's `Thread` class.

Synchronization is used to safely process orders and manage shared data.

## DAO

Data Access Object (DAO) classes are used to separate database operations from the application interface.

DAO classes include:

- ProductDAO
- UserDAO
- OrderDAO
- CartDAO

## JDBC

JDBC is used to connect the Java application with Oracle Database.

JDBC is used for:

- Connecting to the database
- Executing SQL queries
- Inserting data
- Retrieving data
- Updating data
- Deleting data
- Processing orders

## Application Flow

```text
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
```

## Project Highlights

- Role-based user access
- Java Swing graphical interface
- Oracle database integration
- JDBC connectivity
- Product management
- Shopping cart
- Order processing
- Order history
- DAO architecture
- OOP implementation
- Collections and Generics
- Multithreading and Synchronization

## Team Members

| Name | Role |
|---|---|
| Utkarsh | Team Member |
| Aniket Yadav | Team Member |
| Rajat Sharma | Team Member |
| Avnish Solanki | Team Member |

## Author

Developed as an academic project for the GUVI Project Review.

**Project:** Online E-Commerce Platform