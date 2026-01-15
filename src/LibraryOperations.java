import java.sql.*;
//THE BRAIN
public class LibraryOperations {
    // Method to add a new book to your MySQL table
    public void addBook(String name, String author, int qty) {
        // question marks are placeholders
        // ? are for Prepared Statement which protects against SQL Injection(a major security risk)
        String query = "INSERT INTO books (name, author_name, available_qty) VALUES (?, ?, ?)";
        //try with resources : automatically closes DB connection when code finishes, preventing memory leaks
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, name);// this replaces 1st ? with value of name variable, 1 is index of ?.
            pstmt.setString(2, author);// this replaces 2nd ? with valuse of author variable, 2 is for 2nd ?.
            pstmt.setInt(3, qty);// similar
            pstmt.executeUpdate();// for queries that change data (INSERT,UPDATE,DELETE). It returns number of rows updated
            System.out.println("✅ Book added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to fetch and display all books
    public void viewBooks() {
        String query = "SELECT * FROM books";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             // for SELECT queries, ResultSet temporary table in java's memory to hold fetched data from MYSQL
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("\n--- Library Inventory ---");
            while (rs.next()) { // loop moves pointer to next row of data, if no more rows it returns false and stops
                System.out.println("ID: " + rs.getInt("id") +
                        " | Name: " + rs.getString("name") +
                        " | Author: " + rs.getString("author_name") +
                        " | Qty: " + rs.getInt("available_qty"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void returnBook(int transactionId, int bookId) {
        String updateTransaction = "UPDATE booking_details SET return_date = CURRENT_TIMESTAMP WHERE id = ?";
        String updateInventory = "UPDATE books SET available_qty = available_qty + 1 WHERE id = ?";

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps1 = conn.prepareStatement(updateTransaction);
            ps1.setInt(1, transactionId);
            ps1.executeUpdate();

            PreparedStatement ps2 = conn.prepareStatement(updateInventory);
            ps2.setInt(1, bookId);
            ps2.executeUpdate();
            System.out.println("✅ Book returned and inventory updated!");
        } catch (SQLException e) { e.printStackTrace(); }
    }
    public void registerStudent(String name, String regNum, String email, String contact) {
        String query = "INSERT INTO students (std_name, reg_num, email, contact) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, name);
            pstmt.setString(2, regNum);
            pstmt.setString(3, email);
            pstmt.setString(4, contact);

            pstmt.executeUpdate();
            System.out.println("✅ Student registered with secure contact details!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void viewStudentRecords(int stdId) {
        String query = "SELECT b.name, bd.borrow_date, bd.return_date FROM booking_details bd " +
                "JOIN books b ON bd.book_id = b.id WHERE bd.std_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, stdId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("Book: " + rs.getString("name") + " | Borrowed: " + rs.getTimestamp("borrow_date"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }
    public int getStudentIdByReg(String regNum) {
        String query = "SELECT id FROM students WHERE reg_num = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, regNum);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getInt("id"); // Returns the internal database ID

        } catch (SQLException e) { e.printStackTrace(); }
        return -1; // Indicates student was not found
    }
    public void issueBook(int studentId, int bookId) {
        String insertRecord = "INSERT INTO booking_details (std_id, book_id, borrow_date) VALUES (?, ?, CURRENT_TIMESTAMP)";
        String updateInventory = "UPDATE books SET available_qty = available_qty - 1 WHERE id = ? AND available_qty > 0";

        try (Connection conn = DBConnection.getConnection()) {
            // Step 1: Record the transaction
            PreparedStatement psInsert = conn.prepareStatement(insertRecord);
            psInsert.setInt(1, studentId);
            psInsert.setInt(2, bookId);
            psInsert.executeUpdate();

            // Step 2: Update the book count
            PreparedStatement psUpdate = conn.prepareStatement(updateInventory);
            psUpdate.setInt(1, bookId);
            psUpdate.executeUpdate();

            System.out.println("✅ Success: Book issued and inventory decremented.");
        } catch (SQLException e) { e.printStackTrace(); }
    }
    // 1. View all registered students
    public void viewAllStudents() {
        String query = "SELECT * FROM students";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("\n--- Registered Students ---");
            while (rs.next()) {
                System.out.println("Reg No: " + rs.getString("reg_num") +
                        " | Name: " + rs.getString("std_name") +
                        " | Email: " + rs.getString("email") +
                        " | Contact: " + rs.getString("contact"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // 2. View specific student history with Book Names
    public void viewStudentHistory(String regNum) {
        // This query joins the student, their bookings, and the book names
        String query = "SELECT s.std_name, b.name, bd.borrow_date, bd.return_date " +
                "FROM students s " +
                "JOIN booking_details bd ON s.id = bd.std_id " +
                "JOIN books b ON bd.book_id = b.id " +
                "WHERE s.reg_num = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, regNum);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("\n--- Borrowing History for " + regNum + " ---");
            boolean hasHistory = false;
            while (rs.next()) {
                hasHistory = true;
                String returnStatus = (rs.getTimestamp("return_date") == null) ? "Still Issued" : rs.getTimestamp("return_date").toString();
                System.out.println("Book: " + rs.getString("name") +
                        " | Borrowed: " + rs.getTimestamp("borrow_date") +
                        " | Returned: " + returnStatus);
            }
            if (!hasHistory) System.out.println("No history found for this student.");
        } catch (SQLException e) { e.printStackTrace(); }
    }public void returnBookByDetails(int studentId, int bookId) {
        // We look for the record for this student and book that hasn't been returned yet
        String updateTransaction = "UPDATE booking_details SET return_date = CURRENT_TIMESTAMP " +
                "WHERE std_id = ? AND book_id = ? AND return_date IS NULL LIMIT 1";
        String updateInventory = "UPDATE books SET available_qty = available_qty + 1 WHERE id = ?";

        try (Connection conn = DBConnection.getConnection()) {
            // Step 1: Mark the book as returned
            PreparedStatement ps1 = conn.prepareStatement(updateTransaction);
            ps1.setInt(1, studentId);
            ps1.setInt(2, bookId);
            int rowsAffected = ps1.executeUpdate();

            if (rowsAffected > 0) {
                // Step 2: Only if the update worked, increase the stock
                PreparedStatement ps2 = conn.prepareStatement(updateInventory);
                ps2.setInt(1, bookId);
                ps2.executeUpdate();
                System.out.println("✅ Book returned successfully!");
            } else {
                System.out.println("❌ Error: No active issue record found for this student/book.");
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

}
