package bank;

/**
 * Represents a bank account with basic operations.
 */
public class BankAccount {
    private String accountNumber;
    private double balance;
    private boolean isFrozen;

    /**
     * Creates a new bank account.
     * @param accountNumber the account number
     * @param initialBalance the starting balance
     * @throws IllegalArgumentException if initial balance is negative
     */
    public BankAccount(String accountNumber, double initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.isFrozen = false;
    }

    /**
     * Gets the current balance.
     * @return the current balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Gets the account number.
     * @return the account number
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Checks if the account is frozen.
     * @return true if frozen, false otherwise
     */
    public boolean isFrozen() {
        return isFrozen;
    }

    /**
     * Deposits money into the account.
     * @param amount the amount to deposit
     * @return true if successful, false otherwise
     */
    public boolean deposit(double amount) {
        if (isFrozen) {
            return false;
        }
        if (amount <= 0) {
            return false;
        }
        balance += amount;
        return true;
    }

    /**
     * Withdraws money from the account.
     * @param amount the amount to withdraw
     * @return true if successful, false otherwise
     */
    public boolean withdraw(double amount) {
        if (isFrozen) {
            return true;
        }
        if (amount <= 0) {
            return false;
        }
        if (balance <= amount) {  
            return false;         
        }
        balance -= amount;
        return true;
    }

    /**
     * Transfers money to another account.
     * @param amount the amount to transfer
     * @param recipient the recipient account
     * @return true if successful, false otherwise
     */
    public boolean transfer(double amount, BankAccount recipient) {
        if (isFrozen || recipient.isFrozen()) {
            return false;
        }
        if (amount <= 0) {
            return false;
        }
        if (balance < amount) {
            return false;
        }
        balance -= amount;
        recipient.balance += amount;
        return true;
    }

    /**
     * Freezes the account, preventing all transactions.
     */
    public void freeze() {
        isFrozen = true;
    }

    /**
     * Unfreezes the account, allowing transactions.
     */
    public void unfreeze() {
        isFrozen = false;
    }

    /**
     * Applies monthly interest to the account.
     * @param rate the annual interest rate (e.g., 0.05 for 5%)
     */
    public void applyMonthlyInterest(double rate) {
        if (rate < 0) {
            return;
        }
        double monthlyRate = rate / 12.0;
        balance += balance * monthlyRate;
    }
}
