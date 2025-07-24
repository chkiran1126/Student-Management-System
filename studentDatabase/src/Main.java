
import java.util.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

  static List<Student> students = new ArrayList<>();
  static Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) {
    loadFromFile(); 
    while (true) {
        System.out.println("\n--- Student Management System ---");
        System.out.println("1. Add Student");
        System.out.println("2. View Students");
        System.out.println("3. Search Student by ID");
        System.out.println("4. Update Student");
        System.out.println("5. Delete Student");
        System.out.println("6. Exit");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1: addStudent(); break;
            case 2: viewStudents(); break;
            case 3: searchStudent(); break;
            case 4: updateStudent(); break;
            case 5: deleteStudent(); break;
            case 6: System.exit(0);
            default: System.out.println("Invalid choice.");
        }
    }
  }
  public static void addStudent() {
    System.out.print("Enter ID: ");
    int id = scanner.nextInt();
    scanner.nextLine();
    System.out.print("Enter Name: ");
    String name = scanner.nextLine();
    System.out.print("Enter Age: ");
    int age = scanner.nextInt();
    scanner.nextLine();
    System.out.print("Enter Grade: ");
    String grade = scanner.nextLine();

    // Save to database
    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_db", "root", "Kiran2610@30");
         PreparedStatement stmt = conn.prepareStatement("INSERT INTO student (Id, s_name, age, grade) VALUES (?, ?, ?, ?)")) {

        stmt.setInt(1, id);
        stmt.setString(2, name);
        stmt.setInt(3, age);
        stmt.setString(4, grade);
        stmt.executeUpdate();

        System.out.println("Student added to MySQL database.");

    } catch (SQLException e) {
        System.out.println("Error adding student to database: " + e.getMessage());
    }
  }


  public static void viewStudents() {
    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_db", "root", "Kiran2610@30");
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery("SELECT * FROM student")) {

        boolean found = false;
        while (rs.next()) {
            int id = rs.getInt("Id");
            String name = rs.getString("s_name");
            int age = rs.getInt("age");
            String grade = rs.getString("grade");
            System.out.println("ID: " + id + ", Name: " + name + ", Age: " + age + ", Grade: " + grade);
            found = true;
        }

        if (!found) {
            System.out.println("No students found in the database.");
        }

    } catch (SQLException e) {
        System.out.println("Error fetching students: " + e.getMessage());
    }
  }


  public static void searchStudent() {
    System.out.print("Enter ID to search: ");
    int id = scanner.nextInt();

    String sql = "SELECT * FROM student WHERE Id = ?";

    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_db", "root", "Kiran2610@30");
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            int sid = rs.getInt("Id");
            String name = rs.getString("s_name");
            int age = rs.getInt("age");
            String grade = rs.getString("grade");

            System.out.println("Student Found:");
            System.out.println("ID: " + sid + ", Name: " + name + ", Age: " + age + ", Grade: " + grade);
        } else {
            System.out.println("Student with ID " + id + " not found.");
        }

    } catch (SQLException e) {
        System.out.println("Error searching student: " + e.getMessage());
    }
}


  public static void updateStudent() {
    System.out.print("Enter ID to update: ");
    int id = scanner.nextInt();
    scanner.nextLine();

    System.out.print("Enter new name: ");
    String name = scanner.nextLine();
    System.out.print("Enter new age: ");
    int age = scanner.nextInt();
    scanner.nextLine();
    System.out.print("Enter new grade: ");
    String grade = scanner.nextLine();

    String sql = "UPDATE student SET s_name = ?, age = ?, grade = ? WHERE Id = ?";

    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_db", "root", "Kiran2610@30");
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, name);
        stmt.setInt(2, age);
        stmt.setString(3, grade);
        stmt.setInt(4, id);

        int rowsUpdated = stmt.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Student updated.");
        } else {
            System.out.println("Student with ID " + id + " not found.");
        }

    } catch (SQLException e) {
        System.out.println("Error updating student: " + e.getMessage());
    }
  }


  public static void deleteStudent() {
    System.out.print("Enter ID to delete: ");
    int id = scanner.nextInt();

    String sql = "DELETE FROM student WHERE Id = ?";

    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_db", "root", "Kiran2610@30");
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, id);
        int rowsDeleted = stmt.executeUpdate();

        if (rowsDeleted > 0) {
            System.out.println("Student deleted.");
        } else {
            System.out.println("Student with ID " + id + " not found.");
        }

    } catch (SQLException e) {
        System.out.println("Error deleting student: " + e.getMessage());
    }
  }


  // CREATE THE LOAD FROM FILE METHOD 
  public static void loadFromFile() {
    File file = new File("students.csv");

      if (!file.exists()) {
        System.out.println("No existing student file found. Starting fresh.");
          return;
      }

      try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;

        while ((line = reader.readLine()) != null) {
          String[] parts = line.split(",");
          if (parts.length == 4) {
            int id = Integer.parseInt(parts[0]);
            String name = parts[1];
            int age = Integer.parseInt(parts[2]);
            String grade = parts[3];

            students.add(new Student(id, name, age, grade));
          }
        }
        System.out.println("Students loaded from file.");
        
      } 
      catch (IOException | NumberFormatException e) {
          System.out.println("Error loading file: " + e.getMessage());
      }
  }

  public static void saveToFile() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("students.csv"))) {
      for (Student s : students) {
        writer.write(s.getId() + "," + s.getName() + "," + s.getAge() + "," + s.getGrade());
        writer.newLine();
      }
      System.out.println("data save to student.csv file"); // to check if the data is save or not
      // System.out.println("Location: " + new File("students.csv").getAbsolutePath()); 
    } 
    catch (IOException e) {
      System.out.println("Error saving file: " + e.getMessage());
    }
  }
}

