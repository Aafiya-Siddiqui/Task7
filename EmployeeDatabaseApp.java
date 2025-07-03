import java.sql.*;
import java.util.Scanner;

public class EmployeeDatabaseApp {
    static final String DB_URL = "jdbc:mysql://localhost:3306/employee_db";
    static final String USER = "root"; // Change if needed
    static final String PASS = "password"; // Change if needed

    static Connection conn;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected to database.");

            int choice;
            do {
                System.out.println("\n1. Add Employee\n2. View Employees\n3. Update Employee\n4. Delete Employee\n5. Exit");
                System.out.print("Enter choice: ");
                choice = sc.nextInt();

                switch (choice) {
                    case 1: addEmployee(); break;
                    case 2: viewEmployees(); break;
                    case 3: updateEmployee(); break;
                    case 4: deleteEmployee(); break;
                    case 5: System.out.println("Exiting..."); break;
                    default: System.out.println("Invalid choice.");
                }
            } while (choice != 5);

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void addEmployee() throws SQLException {
        System.out.print("Enter name: ");
        String name = sc.next();
        System.out.print("Enter department: ");
        String dept = sc.next();
        System.out.print("Enter salary: ");
        double salary = sc.nextDouble();

        String sql = "INSERT INTO employees (name, department, salary) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, name);
        stmt.setString(2, dept);
        stmt.setDouble(3, salary);
        stmt.executeUpdate();
        System.out.println("Employee added successfully.");
    }

    static void viewEmployees() throws SQLException {
        String sql = "SELECT * FROM employees";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        System.out.println("\nID\tName\tDepartment\tSalary");
        while (rs.next()) {
            System.out.println(rs.getInt("id") + "\t" + rs.getString("name") + "\t" +
                               rs.getString("department") + "\t" + rs.getDouble("salary"));
        }
    }

    static void updateEmployee() throws SQLException {
        System.out.print("Enter ID to update: ");
        int id = sc.nextInt();
        System.out.print("Enter new name: ");
        String name = sc.next();
        System.out.print("Enter new department: ");
        String dept = sc.next();
        System.out.print("Enter new salary: ");
        double salary = sc.nextDouble();

        String sql = "UPDATE employees SET name=?, department=?, salary=? WHERE id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, name);
        stmt.setString(2, dept);
        stmt.setDouble(3, salary);
        stmt.setInt(4, id);
        int rows = stmt.executeUpdate();
        System.out.println(rows > 0 ? "Employee updated." : "Employee not found.");
    }

    static void deleteEmployee() throws SQLException {
        System.out.print("Enter ID to delete: ");
        int id = sc.nextInt();

        String sql = "DELETE FROM employees WHERE id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        int rows = stmt.executeUpdate();
        System.out.println(rows > 0 ? "Employee deleted." : "Employee not found.");
    }
}
