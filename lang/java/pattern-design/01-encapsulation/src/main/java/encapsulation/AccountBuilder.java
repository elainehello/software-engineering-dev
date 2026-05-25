package encapsulation;

public class AccountBuilder {

    // Required fields
    final String accountNumber;
    final String owner;

    // Optional field with sensible defaults
    String accountType    = "CHECKING";
    double initialBalance = 0.0;
    double overdraftLimit = 0.0;

    // Constructor only requires the mandatory fields
    public AccountBuilder(String accountNumber, String owner) {
        if (accountNumber == null || accountNumber.isBlank()) {
            throw new IllegalArgumentException("Account number is required.");
        }
        if (owner == null || owner.isBlank()) {
            throw new IllegalArgumentException("Owner name is required.");
        }
        this.accountNumber = accountNumber;
        this.owner         = owner;
    }
    
    // ── Optional setters return `this` so calls can be chained ───────────────
    public AccountBuilder accountType(String type) {
        if (!type.equals("CHECKING") && !type.equals("SAVINGS")) {
            throw new IllegalArgumentException("Type must be CHECKING or SAVINGS.");
        }
        this.accountType = type;
        return this;
    }

    public AccountBuilder initialBalance(double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative.");
        }
        this.initialBalance = balance;
        return this;
    }

    public AccountBuilder overdraftLimit(double limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("Overdraft limit cannot be negative.");
        }
        this.overdraftLimit = limit;
        return this;
    }

    // ── Terminal method — builds the final immutable BankAccount ─────────────
    public BankAccount build() {
        return new BankAccount(this);
    }
}
