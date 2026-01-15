import java.util.Scanner;
import java.sql.*; // Added for database operations

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LoginManager login = new LoginManager();
        LibraryOperations lib = new LibraryOperations();

        System.out.println("--- 📚 Library Management System Login ---");
        System.out.print("Username: ");
        String user = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();

        // 1. Authenticate user and get their role
        String role = login.verifyLogin(user, pass);

        if (role == null) {
            System.out.println("❌ Invalid username or password. Connection closed.");
        } else {
            System.out.println("✅ Login Successful! Welcome " + user + " (" + role + ")");

            // 2. Direct user to their specific menu based on role
            if (role.equalsIgnoreCase("ADMIN")) {
                runAdminMenu(sc, lib);
            } else {
                runStudentMenu(sc, lib);
            }
        }
        sc.close(); // Correctly placed at the end of execution
    }

    // --- ADMIN SPECIFIC TOOLS ---
    public static void runAdminMenu(Scanner sc, LibraryOperations lib) {
        while (true) {
            System.out.println("\n--- ADMIN DASHBOARD ---");
            System.out.println("1. Add New Book\n2. Issue Book to Student\n3. View All Books\n4. Register Student\n5. View all students\n6.View Student History\n7.Return book\n8.Logout");
            System.out.print("Action: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            if (choice == 1) {
                System.out.print("Book Name: "); String name = sc.nextLine();
                System.out.print("Author: "); String author = sc.nextLine();
                System.out.print("Quantity: "); int qty = sc.nextInt();
                lib.addBook(name, author, qty);
            } else if (choice == 2) {
                System.out.print("Enter Student Registration Number: ");
                String regNum = sc.nextLine();
                // Link Registration Number to Relational ID
                int stdId = lib.getStudentIdByReg(regNum);

                if (stdId != -1) {
                    System.out.print("Enter Book ID: "); int bookId = sc.nextInt();
                    lib.issueBook(stdId, bookId); // Synchronized Update
                } else {
                    System.out.println("❌ Error: Registration number not found.");
                }
            } else if (choice == 3) {
                lib.viewBooks();
            } else if (choice == 4) {
                System.out.print("Enter Student Name: "); String name = sc.nextLine();
                System.out.print("Enter Registration Number: "); String reg = sc.nextLine();
                System.out.print("Enter Email: "); String email = sc.nextLine();
                System.out.print("Enter Contact: "); String contact = sc.nextLine();
                lib.registerStudent(name, reg, email, contact);
            }
           else if (choice == 5) {
                lib.viewAllStudents();
            } else if (choice == 6) {
                System.out.print("Enter Registration Number to check history: ");
                String reg = sc.nextLine();
                lib.viewStudentHistory(reg);
            }else if (choice == 7) { // Return Book
                System.out.print("Enter Student Registration Number: ");
                String regNum = sc.nextLine();
                int stdId = lib.getStudentIdByReg(regNum); // Reuse your lookup method

                if (stdId != -1) {
                    System.out.print("Enter Book ID to return: ");
                    int bookId = sc.nextInt();
                    lib.returnBookByDetails(stdId, bookId);
                } else {
                    System.out.println("❌ Student not found.");
                }
            }
           else if (choice == 8) {
                System.out.println("Logging out...");
                break;
            }
        }
    }

    // --- STUDENT SPECIFIC TOOLS ---
    public static void runStudentMenu(Scanner sc, LibraryOperations lib) {
        while (true) {
            System.out.println("\n--- STUDENT DASHBOARD ---");
            System.out.println("1. View Available Books\n2. Logout");
            System.out.print("Action: ");
            int choice = sc.nextInt();
            if (choice == 1) {
                lib.viewBooks();
            } else if (choice == 2) {
                System.out.println("Logging out...");
                break;
            }
        }
    }
}