package payroll.engine;

import payroll.core.Employee;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// ── POLYMORPHISM IN ACTION ────────────────────────────────────────────────────
// This engine only knows about Employee — never Manager, Engineer, or Intern.
// Every calculation, sort, and report works through the abstract base type.
public class PayrollEngine {

    private final List<Employee> roster = new ArrayList<>();
    private final String         companyName;

    public PayrollEngine(String companyName) {
        this.companyName = companyName;
    }

    public void addEmployee(Employee employee) {
        roster.add(employee);
    }

    public void runMonthlyPayroll() {
        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.printf ("║  %s — MONTHLY PAYROLL RUN%n", companyName);
        System.out.println("╚══════════════════════════════════════════════════╝");

        double totalPayout = 0;

        for (Employee e : roster) {
            // calculateMonthlyPay() calls the correct version for each type
            // — this is polymorphism: same call, different behaviour per class
            System.out.println(e.getPaySlipSummary());
            totalPayout += e.calculateMonthlyPay();
        }

        System.out.println("──────────────────────────────────────────────────");
        System.out.printf ("  Total headcount : %d employees%n", roster.size());
        System.out.printf ("  Total payout    : $%.2f%n", totalPayout);
        System.out.println("══════════════════════════════════════════════════");
    }

    public void printRosterByDepartment() {
        System.out.println("\n── Roster sorted by department ──");
        roster.stream()
                .sorted(Comparator.comparing(Employee::getDepartment))
                .forEach(e -> System.out.println("  " + e));
    }

    public double getTotalMonthlyBudget() {
        return roster.stream()
                .mapToDouble(Employee::calculateMonthlyPay)
                .sum();
    }
}