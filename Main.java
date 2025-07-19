import java.util.*;
import java.io.*;
public class Main {

  static List<Student> students = new ArrayList<>();
  static Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) {
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
    scanner.nextLine(); // Consume newline
    System.out.print("Enter Name: ");
    String name = scanner.nextLine();
    System.out.print("Enter Age: ");
    int age = scanner.nextInt();
    scanner.nextLine();
    System.out.print("Enter Grade: ");
    String grade = scanner.nextLine();

    students.add(new Student(id, name, age, grade));
    System.out.println("Student added successfully!");
  }

  public static void viewStudents() {
    if (students.isEmpty()) {
      System.out.println("No students found.");
    }else {
      for (Student s : students) {
        System.out.println(s);
      }
    }
  }

  public static void searchStudent() {
    System.out.print("Enter ID to search: ");
    int id = scanner.nextInt();
    for (Student s : students) {
      if (s.getId() == id) {
        System.out.println(s);
        return;
      }
    }
    System.out.println("Student not found.");
  }

  public static void updateStudent() {
    System.out.print("Enter ID to update: ");
    int id = scanner.nextInt();
    for (Student s : students) {
      if (s.getId() == id) {
        scanner.nextLine(); // clear buffer
        System.out.print("Enter new name: ");

        s.setName(scanner.nextLine());
        System.out.print("Enter new age: ");

        s.setAge(scanner.nextInt());
        scanner.nextLine();
        System.out.print("Enter new grade: ");
        
        s.setGrade(scanner.nextLine());
        System.out.println("Student updated.");
        return;
      }
    }
    System.out.println("Student not found.");
  }

  public static void deleteStudent() {
    System.out.print("Enter ID to delete: ");
    int id = scanner.nextInt();
    Iterator<Student> iterator = students.iterator();
    while (iterator.hasNext()) {
      if (iterator.next().getId() == id) {
        iterator.remove();
        System.out.println("Student deleted.");
        return;
      }
    }
    System.out.println("Student not found.");
  }

  // CREATE THE LOAD FORM FILE METHOD 
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
    } 
    catch (IOException e) {
      System.out.println("Error saving file: " + e.getMessage());
    }
  }
}

