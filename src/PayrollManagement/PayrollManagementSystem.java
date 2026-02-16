package PayrollManagement;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

// ================= INTERFACE =================

interface Payable {
    double calculateSalary();
}

// ================= ABSTRACT EMPLOYEE =================

abstract class Employee implements Payable {
    protected int id;
    protected String name;
    protected String department;

    public Employee(int id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }

    public abstract double calculateSalary();

    public double calculateTax(double salary) {
        return salary * 0.1; // 10% tax
    }

    public void generatePayslip() {
        double gross = calculateSalary();
        double tax = calculateTax(gross);
        double net = gross - tax;

        System.out.println("\n----- PAYSLIP -----");
        System.out.println("Employee ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Department: " + department);
        System.out.println("Gross Salary: ₹" + gross);
        System.out.println("Tax (10%): ₹" + tax);
        System.out.println("Net Salary: ₹" + net);
        System.out.println("-------------------\n");
    }

    @Override
    public String toString() {
        return id + " | " + name + " | " + department;
    }
}

// ================= FULL TIME EMPLOYEE =================

class FullTimeEmployee extends Employee {

    private double monthlySalary;

    public FullTimeEmployee(int id, String name, String department, double monthlySalary) {
        super(id, name, department);
        this.monthlySalary = monthlySalary;
    }

    @Override
    public double calculateSalary() {
        return monthlySalary;
    }
}

// ================= PART TIME EMPLOYEE =================

class PartTimeEmployee extends Employee {

    private double hourlyRate;
    private int hoursWorked;

    public PartTimeEmployee(int id, String name, String department,
                            double hourlyRate, int hoursWorked) {
        super(id, name, department);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    @Override
    public double calculateSalary() {
        return hourlyRate * hoursWorked;
    }
}

// ================= MAIN SYSTEM =================

public class PayrollManagementSystem {

    static Scanner sc = new Scanner(System.in);
    static Map<Integer, Employee> employees = new HashMap<>();
    static int idCounter = 1;

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n===== PAYROLL MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Search Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. Generate Payslip");
            System.out.println("6. Export Payroll to File");
            System.out.println("7. Exit");

            int choice = sc.nextInt();

            switch (choice) {
                case 1: addEmployee(); break;
                case 2: viewEmployees(); break;
                case 3: searchEmployee(); break;
                case 4: deleteEmployee(); break;
                case 5: generatePayslip(); break;
                case 6: exportToFile(); break;
                case 7: System.exit(0);
                default: System.out.println("Invalid choice!");
            }
        }
    }

    // ================= ADD EMPLOYEE =================
    static void addEmployee() {

        sc.nextLine();

        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("Department: ");
        String dept = sc.nextLine();

        System.out.println("1. Full-Time");
        System.out.println("2. Part-Time");
        int type = sc.nextInt();

        Employee emp;

        if (type == 1) {
            System.out.print("Monthly Salary: ");
            double salary = sc.nextDouble();
            emp = new FullTimeEmployee(idCounter++, name, dept, salary);
        } else {
            System.out.print("Hourly Rate: ");
            double rate = sc.nextDouble();
            System.out.print("Hours Worked: ");
            int hours = sc.nextInt();
            emp = new PartTimeEmployee(idCounter++, name, dept, rate, hours);
        }

        employees.put(emp.getId(), emp);
        System.out.println("Employee added successfully!");
    }

    // ================= VIEW EMPLOYEES =================
    static void viewEmployees() {

        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }

        employees.values().forEach(System.out::println);
    }

    // ================= SEARCH EMPLOYEE =================
    static void searchEmployee() {

        System.out.print("Enter Employee ID: ");
        int id = sc.nextInt();

        Employee emp = employees.get(id);

        if (emp == null) {
            System.out.println("Employee not found.");
        } else {
            System.out.println(emp);
        }
    }

    // ================= DELETE EMPLOYEE =================
    static void deleteEmployee() {

        System.out.print("Enter Employee ID: ");
        int id = sc.nextInt();

        if (employees.remove(id) != null) {
            System.out.println("Employee deleted.");
        } else {
            System.out.println("Employee not found.");
        }
    }

    // ================= GENERATE PAYSLIP =================
    static void generatePayslip() {

        System.out.print("Enter Employee ID: ");
        int id = sc.nextInt();

        Employee emp = employees.get(id);

        if (emp == null) {
            System.out.println("Employee not found.");
        } else {
            emp.generatePayslip(); // Polymorphism
        }
    }

    // ================= EXPORT TO FILE =================
    static void exportToFile() {

        try (FileWriter writer = new FileWriter("payroll_report.txt")) {

            for (Employee emp : employees.values()) {
                double gross = emp.calculateSalary();
                double tax = emp.calculateTax(gross);
                double net = gross - tax;

                writer.write("Employee ID: " + emp.getId() + "\n");
                writer.write("Name: " + emp.getName() + "\n");
                writer.write("Department: " + emp.getDepartment() + "\n");
                writer.write("Gross Salary: ₹" + gross + "\n");
                writer.write("Tax: ₹" + tax + "\n");
                writer.write("Net Salary: ₹" + net + "\n");
                writer.write("---------------------------\n");
            }

            System.out.println("Payroll exported to payroll_report.txt");

        } catch (IOException e) {
            System.out.println("Error writing file.");
        }
    }
}
