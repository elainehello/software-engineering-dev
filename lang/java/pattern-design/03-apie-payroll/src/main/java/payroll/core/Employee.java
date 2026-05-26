package payroll.core;

// -- (Abstraction) PIE
// Abstract because a plain "Employee" doesn't exist in the real world.
// You always hire a Manager, Engineer, or Intern - never just an "Employee"
// Abstract (forces) every subclass (inherited) to define (HOW) their pay is calculated.
public abstract class Employee implements Payable {

    // API (Encapsulation)
    // Sensitive fields are private - subclasses use getters, not direct access
    private final String employeeId;
    private final String fullName;
    private final String department;
    private double baseSalary; // mutable - can get a raise
    private int vacationDays;

    // Constructor - only accessible to subclasses (protected) access modifier
    protected Employee(String employeeId, String fullName,
                       String department, double baseSalary) {
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.department = department;
        this.baseSalary = baseSalary;
        this.vacationDays = 15; // default for all employees
    }

    // Getters - controlled read access
    public String getEmployeeId() {
        return employeeId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDepartment() {
        return department;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public int getVacationDays() {
        return vacationDays;
    }

    // Setter with validation - no negative salaries
    public void applyRaise(double percentage) {
        if (percentage <= 0 || percentage > 50) {
            throw new IllegalArgumentException("Raise must be between 0% and 50%, Got: " + percentage + "%");
        }
        this.baseSalary += this.baseSalary * (percentage / 100);
        System.out.printf("  [Raise Applied] %s's new salary: $%.2f%n", fullName, baseSalary);
    }

    public void addVacationDays(int days) {
        if (days > 0) {
            this.vacationDays += days;
        }
    }

    // -- (Abstraction) PIE: subclasses MUST define their own pay calculation
    @Override
    public abstract double calculateMonthlyPay();

    // -- Shared behaviour all employees get for free via inheritance
    public String getRole() {
        // Returns the actual class name - Engineer, Manager, etc
        return this.getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return String.format("[%s] %s | %s | Dept: %s | Base: $%.2f",
                getRole(), employeeId, fullName, department, baseSalary);
    }
}
