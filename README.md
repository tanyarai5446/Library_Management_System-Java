📚 Library Management System (LMS)
A robust, console-based backend application developed in Java and MySQL to manage library operations, inventory, and student records with high data integrity.

🛠️ Tech Stack
Language: Java (JDK 25.0.1)

Database: MySQL Server

Connectivity: JDBC (Java Database Connectivity) with MySQL Connector/J

Architecture: Modular N-Tier Design (Separation of Concerns)

🚀 Key Features
1. Role-Based Access Control (RBAC)
   Admin Dashboard: Full privileges to add/view books, register students, and manage the issuance and return of inventory.

Student Dashboard: Secure, restricted access to view available book inventory only.

2. Transactional Integrity & Relational Logic
   Atomic Updates: Simultaneously records book issuance in the transaction table while decrementing physical inventory in a single logical flow.

Student Mapping: Uses unique Student Registration Numbers to map transactions to specific student records across 4 normalized tables.

History Tracking: Implements SQL JOINs to generate complete borrowing histories for students.

3. Security & Best Practices
   SQL Injection Prevention: Utilizes PreparedStatements (Parameterized Queries) across all database operations.

Resource Management: Employs try-with-resources for automated connection handling to prevent memory leaks.

📊 Database Schema
The system utilizes a 4-table relational structure designed in 3rd Normal Form (3NF):

books: Tracks inventory (ID, Name, Author, Available Quantity).

students: Stores registered member data (ID, Name, Reg No, Email, Contact).

login: Manages authentication (Username, Password, Role).

booking_details: The transaction log linking Students and Books via Foreign Keys with borrow/return timestamps.

💻 How to Run
Setup Database: Execute the SQL schema in MySQL Workbench and seed the login table with an Admin user.

Configure Driver: Add the mysql-connector-j-9.5.0.jar to your project's external libraries.

Update Credentials: Ensure DBConnection.java reflects your local MySQL username and password.

Launch: Run Main.java and login with your credentials.

🔮 Future Roadmap
Automated Fine Calculation: Logic to compare borrow and return dates for penalty assessment.

JavaFX GUI: Transitioning from a CLI to a modern Graphical User Interface.

Email API: Automated notification system for book return reminders.