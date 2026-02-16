package BankManagement;

public class BankAccount {

    private int accountNumber;
    private String holderName;
    private double balance;

    // Constructor
    public BankAccount(int accountNumber, String holderName, double balance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
    }

    // Getters
    public int getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    // Deposit
    public void deposit(double amount) {
        balance += amount;
    }

    // Withdraw
    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }

    // Display
    public void display() {
        System.out.println(accountNumber + " | " + holderName + " | Balance: ₹" + balance);
    }
}