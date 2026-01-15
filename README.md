Library Management System (LMS)
A robust, console-based backend application developed in Java and MySQL to manage library operations, inventory, and student records with high data integrity.

🛠️ Tech Stack
Language: Java (JDK 25.0.1)

Database: MySQL Server 8.0/9.0

Connectivity: JDBC (Java Database Connectivity) with MySQL Connector/J

IDE: IntelliJ IDEA Community Edition

🚀 Key Features
1. Role-Based Access Control (RBAC)
   Admin Dashboard: Full privileges to add books, register students, and manage issuance/returns.

Student Dashboard: Restricted access to view available inventory only.

2. Transactional Integrity
   Atomic Updates: Simultaneously records book issuance in the transaction table while decrementing physical inventory.

Unique Identification: Uses Student Registration Numbers to map transactions to specific student records.

3. Security
   SQL Injection Prevention: Utilizes PreparedStatements across all database operations to ensure secure data handling.

Modular Design: Implements Separation of Concerns by decoupling database connection logic from business operations.

📊 Database Schema
The system is built on 4 normalized tables to eliminate data redundancy:

books: Stores title, author, and available quantity.

students: Stores name, unique registration number, email, and contact details.

login: Manages authentication with usernames, passwords, and user types (ADMIN/STUDENT).

booking_details: Tracks all transactions, linking students to books via Foreign Keys with borrow and return timestamps.

💻 How to Run
Database Setup: Import the SQL schema and seed the login table with an admin user.

Driver Configuration: Ensure the mysql-connector-j-x.x.x.jar is added to the project library.

Connection: Update the credentials in DBConnection.java to match your local MySQL server.

Execution: Run the Main.java file and login with your credentials.

🔮 Future Roadmap
Automated Fine Calculation: Logic to detect overdue books and calculate penalties.

Graphical User Interface (GUI): Transitioning to JavaFX for a professional desktop experience.

Email API Integration: Automated notifications for book returns and account updates.