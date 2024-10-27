import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

// This is the student entity class
class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private int age;

    // Constructor
    public Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // Display student details
    @Override
    public String toString() {
        return "Student ID: " + id + ", Name: " + name + ", Age: " + age;
    }
}

// This is the student management system for managing students
class StudentManagementSystem {
    private ArrayList<Student> students;
    private final String fileName = "students.dat";

    // Constructor
    public StudentManagementSystem() {
        students = new ArrayList<>();
        loadStudentsFromFile();
    }

    // Add a new student
    public void addStudent(int id, String name, int age) {
        Student student = new Student(id, name, age);
        students.add(student);
        System.out.println("Student added successfully.");
        saveStudentsToFile();
    }

    // View all students
    public void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

    // Update a student's details
    public void updateStudent(int id, String newName, int newAge) {
        for (Student student : students) {
            if (student.getId() == id) {
                student.setName(newName);
                student.setAge(newAge);
                System.out.println("Student updated successfully.");
                saveStudentsToFile();
                return;
            }
        }
        System.out.println("Student not found.");
    }

    // Delete a student record
    public void deleteStudent(int id) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == id) {
                students.remove(i);
                System.out.println("Student deleted successfully.");
                saveStudentsToFile();
                return;
            }
        }
        System.out.println("Student not found.");
    }

    // Save the student list to a file
    private void saveStudentsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving students to file.");
        }
    }

    // Load the student list from a file
    @SuppressWarnings("unchecked") // Suppress unchecked cast warning
    private void loadStudentsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            students = (ArrayList<Student>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No previous student records found.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading students from file.");
        }
    }
}

// This is the main class containing the entry point
public class StudentApp {  // Renamed from `Student` to `StudentApp`
    public static void main(String[] args) {
        StudentManagementSystem sms = new StudentManagementSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Student Management System ---");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter student ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    System.out.print("Enter student name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter student age: ");
                    int age = scanner.nextInt();
                    sms.addStudent(id, name, age);
                    break;
                case 2:
                    sms.viewStudents();
                    break;
                case 3:
                    System.out.print("Enter student ID to update: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    System.out.print("Enter new student name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter new student age: ");
                    int newAge = scanner.nextInt();
                    sms.updateStudent(updateId, newName, newAge);
                    break;
                case 4:
                    System.out.print("Enter student ID to delete: ");
                    int deleteId = scanner.nextInt();
                    sms.deleteStudent(deleteId);
                    break;
                case 5:
                    System.out.println("Exiting system.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
