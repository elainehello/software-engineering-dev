package payroll.roles;

import payroll.core.Employee;

import java.util.ArrayList;
import java.util.List;

// Manager inherits from Employee and adds team leadership responsabilities
public class Manager extends Employee {

    private final List<Employee> teamMembers = new ArrayList<>();
    private double annualBonus;
    private int teamSize;

    public Manager(String employeeId, String fullName,
                   String department, double baseSalary, double annualBonus) {
        super(employeeId, fullName, department, baseSalary); // super() does the Employee constructor to initialize inherited fields
        this.annualBonus = annualBonus;
    }

    public void addTeamMember(Employee employee) {
        teamMembers.add(employee);
        this.teamSize = teamMembers.size();
    }

    public List<Employee> getTeamMembers() {
        return List.copyOf(teamMembers); // Return an unmodifiable copy of the team members list
    }

    public void setAnnualBonus(double bonus) {
        if (bonus < 0) {
            throw new IllegalArgumentException("Annual bonus cannot be negative, Got: " + bonus);
        }
        this.annualBonus = bonus;
    }

    // A (Polymorphism) IE - Manager-specific pay formula/equation
    @Override
    public double calculateMonthlyPay() {
        double monthlyBonus = annualBonus / 12;
        double teamAllowance = teamSize * 200.0; // $200 per direct report
        return getBaseSalary() + monthlyBonus + teamAllowance;
    }

    @Override
    public String getPaySlipSummary() {
        return String.format(
                "  %-20s | Role: %-10s | Team: %-3d members | Bonus/mo: $%-8.2f | Monthly: $%.2f",
                getFullName(), getRole(), teamSize, annualBonus / 12, calculateMonthlyPay());
    }
}
