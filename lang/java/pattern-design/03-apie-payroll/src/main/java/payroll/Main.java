package payroll;

import payroll.engine.PayrollEngine;
import payroll.roles.Engineer;
import payroll.roles.Intern;
import payroll.roles.Manager;

public class Main {

    public static void main(String[] args) {

        PayrollEngine engine = new PayrollEngine("Acme Corp");

        // ── Build the team ────────────────────────────────────────────────────
        Manager cto = new Manager("MGR-001", "Sarah Connor",
                "Engineering", 12000.00, 24000.00);

        Engineer senior = new Engineer("ENG-001", "John Reese",
                "Engineering", 8000.00, "Java / Spring Boot");

        Engineer mid = new Engineer("ENG-002", "Ada Lovelace",
                "Engineering", 6500.00, "Python / Django");

        Intern intern = new Intern("INT-001", "Alan Turing",
                "Engineering", "MIT", 24, 25.00, 30);

        // ── Configure individual states (encapsulation: controlled mutation) ──
        senior.setPerformanceScore(4.8);
        senior.logOvertimeHours(20);
        mid.setPerformanceScore(3.5);
        cto.addTeamMember(senior);
        cto.addTeamMember(mid);
        cto.addTeamMember(intern);

        // ── Register everyone with the engine ─────────────────────────────────
        engine.addEmployee(cto);
        engine.addEmployee(senior);
        engine.addEmployee(mid);
        engine.addEmployee(intern);

        // ── Run payroll — engine treats all as Employee (polymorphism) ─────────
        engine.runMonthlyPayroll();

        // ── Raise example — encapsulation validates input ──────────────────────
        System.out.println("\n── Applying raises ──");
        senior.applyRaise(10);
        mid.applyRaise(7.5);

        // ── Re-run payroll to see updated numbers ─────────────────────────────
        engine.runMonthlyPayroll();

        // ── Roster view ───────────────────────────────────────────────────────
        engine.printRosterByDepartment();

        System.out.printf("%n── Total monthly budget: $%.2f%n",
                engine.getTotalMonthlyBudget());
    }
}