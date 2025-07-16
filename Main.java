import java.util.*;
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
}

