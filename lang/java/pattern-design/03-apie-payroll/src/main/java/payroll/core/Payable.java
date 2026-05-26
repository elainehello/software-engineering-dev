package payroll.core;

// Interface/Contract - anything that can be paid must implement this interface
public interface Payable {

    double calculateMonthlyPay();
    String getPaySlipSummary();
}
