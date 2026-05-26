package payroll.roles;

import payroll.core.Employee;

// -- AP (Inheritance) E
// Engineer inherits all Employee fields and methods
// Adds its own: tech stack, performance bonus, overtime hours
public class Engineer extends Employee {

    private final String techStack; // e.g. "Java / Spring Boot"
    private double performanceScore; // 0.0 to 5.0
    private int overtimeHours; // monthly overtime hours

    public Engineer(String employeeId, String fullname,
                    String department, double baseSalary,
                    String techStack) {
        super(employeeId, fullname, department, baseSalary); // super() calls the Employee constructor to initialize inherited fields
        this.techStack = techStack;
        this.performanceScore = 3.0;
        this.overtimeHours = 0;
    }

    // API (Encapsulated) setters with validation
    public void setPerformanceScore(double score) {
        if (score < 0.0 || score > 5.0) {
            throw new IllegalArgumentException("Performance score must be between 0.0 and 5.0, Got: " + score);
        }
        this.performanceScore = score;
    }

    public void logOvertimeHours(int hours) {
        if (hours > 0) {
            this.overtimeHours += hours;
        }
    }

    // A (Polymorphism) IE - Engineer-specific pay formula/equation
    @Override
    public double calculateMonthlyPay() {
        double perfomanceBonus = getBaseSalary() * (performanceScore / 100);
        double overtimePay = overtimeHours * (getBaseSalary() / 160) * 1.5; // Assuming 160 working hours/month
        return getBaseSalary() + perfomanceBonus + overtimePay;
    }

    @Override
    public String getPaySlipSummary() {
        return String.format(
                "  %-20s | Role: %-10s | Stack: %-20s | Score: %.1f/5 | Monthly: $%.2f",
                getFullName(), getRole(), techStack, performanceScore, calculateMonthlyPay());
    }
}
