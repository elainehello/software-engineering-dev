package encapsulation;

public class BankAccount {

    // Inmutable fields (set once, never changed)
    private final String accountNumber;
    private final String owner;
    private final String accountType;   // "CHECKING" | "SAVINGS"

    // Mutable private field (controlled via setters)
    private double balance;
    private double overdraftLimit;      // Only for CHECKING accounts - max negative balance allowed
    private boolean frozen;             // If true, no transactions allowed

    // Private constructor (only Builder can create instances)
    BankAccount(AccountBuilder builder) {
        this.accountNumber = builder.accountNumber;
        this.owner = builder.owner;
        this.accountType = builder.accountType;
        this.balance = builder.initialBalance;
        this.overdraftLimit = builder.overdraftLimit;
        this.frozen = false;
    }

    // Getters (read-only access to all fields)
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwner() {
        return owner;
    }

    public String getAccountType() {
        return accountType;
    }

    public double getBalance() {
        return balance;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public boolean isFrozen() {
        return frozen;
    }

    // No setter for accountNumber or owner -- they are final

    // Setter with validation for overdraftLimit
    public void setOverdraftLimit(double limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("Overdraft limit cannot be negative");
        }
        this.overdraftLimit = limit;
    }

    // Business methods (deposit, withdraw) with encapsulated logic
    public void deposit(double amount) {
        if (frozen) {
            throw new IllegalStateException("Account is frozen. No transactions allowed.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive, Got: " + amount);
        }
        balance += amount;
        System.out.printf("  Deposited $%.2f -> new balance: $%.2f", amount, balance);
    }

    public void withdraw(double amount) {
        if (frozen) {
            throw new IllegalStateException("Account is frozen. No transactions allowed.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive, Got: " + amount);
        }
        if (balance - amount < -overdraftLimit) {
            throw new IllegalStateException(
                    "Insufficient funds. Cannot withdraw $%.2f with balance $%.2f and overdraft limit $%.2f".formatted(amount, balance, overdraftLimit));
        }
        balance -= amount;
        System.out.printf("  Withdrew $%.2f -> new balance: $%.2f", amount, balance);
    }

    public void freeze() {
        this.frozen = true;
        System.out.println("  Account frozen. No transactions allowed.");
    }
    public void unfreeze() {
        this.frozen = false;
        System.out.println("  Account unfrozen. Transactions allowed.");
    }

    @Override
    public String toString() {
        return String.format(
                "BankAccount { #%s | owner: %s | type: %s | balance: $%.2f | overdraft: $%.2f | frozen: %s }",
                accountNumber, owner, accountType, balance, overdraftLimit, frozen
        );
    }
}
