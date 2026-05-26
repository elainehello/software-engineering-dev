package payroll.roles;

import payroll.core.Employee;

public class Intern extends Employee {

    private final String university;
    private final int contractWeeks;
    private double hourlyRate;
    private int hoursPerWeek;


    public Intern(String employeeId, String fullName, String department,
                  String university, int contractWeeks,
                  double hourlyRate, int hoursPerWeek) {
        super(employeeId, fullName, department, 0); // Interns don't have a fixed monthly salary, so we set it to 0
        this.university = university;
        this.contractWeeks = contractWeeks;
        this.hourlyRate = hourlyRate;
        this.hoursPerWeek = Math.min(hoursPerWeek, 40); // capped at 40h/week
    }

    // Getters
    public String getUniversity() {
        return university;
    }

    public int getContactWeeks() {
        return contractWeeks;
    }

    // A (Polymorphism) IE - Intern pay is purely hourly
    @Override
    public double calculateMonthlyPay() {
        double weeksPerMonth = contractWeeks / 12.0 * 3;
        return hourlyRate * hoursPerWeek * weeksPerMonth;
    }

    @Override
    public String getPaySlipSummary() {
        return String.format(
                "  %-20s | Role: %-10s | Uni: %-25s | $%.2f/hr x %dh | Monthly: $%.2f",
                getFullName(), getRole(), university,
                hourlyRate, hoursPerWeek, calculateMonthlyPay());
    }
}
