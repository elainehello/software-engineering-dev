package encapsulation;

public class Main {

    public static void main(String[] args) {

        System.out.println("=== 1. Building accounts with the Builder pattern ===");

        BankAccount checking = new AccountBuilder("ACC-001", "Alice")
                .accountType("CHECKING")
                .initialBalance(1000.00)
                .overdraftLimit(200.00)
                .build();

        BankAccount savings = new AccountBuilder("ACC-002", "Bob")
                .accountType("SAVINGS")
                .initialBalance(500.00)
                .build();   // overdraftLimit defaults to 0

        System.out.println(checking);
        System.out.println(savings);

        // ── Immutability demo ────────────────────────────────────────────────
        System.out.println("\n=== 2. Immutable fields — these won't compile if uncommented ===");
        // checking.accountNumber = "HACKED";  // ← compile error: field is final
        // checking.owner = "Hacker";          // ← compile error: field is final
        System.out.println("  accountNumber and owner cannot be changed after creation.");

        // ── Normal transactions ──────────────────────────────────────────────
        System.out.println("\n=== 3. Deposits and withdrawals ===");
        checking.deposit(500.00);
        checking.withdraw(300.00);
        checking.withdraw(1300.00);   // uses overdraft limit
        System.out.println(checking);

        // ── Validation in setters/methods ────────────────────────────────────
        System.out.println("\n=== 4. Validation catches bad input ===");
        try {
            checking.deposit(-50);
        } catch (IllegalArgumentException e) {
            System.out.println("  Caught: " + e.getMessage());
        }

        try {
            checking.withdraw(9999);
        } catch (IllegalStateException e) {
            System.out.println("  Caught: " + e.getMessage());
        }

        // ── Frozen account ───────────────────────────────────────────────────
        System.out.println("\n=== 5. Frozen account blocks all transactions ===");
        savings.freeze();
        try {
            savings.deposit(100);
        } catch (IllegalStateException e) {
            System.out.println("  Caught: " + e.getMessage());
        }
        savings.unfreeze();
        savings.deposit(100);
        System.out.println(savings);
    }
}