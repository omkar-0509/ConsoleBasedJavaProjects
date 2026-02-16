package AdvancedBankingSystem;

import java.util.*;

//Custom Exception
class InsufficientBalanceException extends Exception {
 public InsufficientBalanceException(String message) {
     super(message);
 }
}

//Abstract Account Class
abstract class Account {
 protected String accountNumber;
 protected String name;
 protected String pin;
 protected double balance;
 protected boolean isLocked = false;
 protected int wrongAttempts = 0;
 protected List<String> transactionHistory = new ArrayList<>();

 public Account(String accountNumber, String name, String pin, double balance) {
     this.accountNumber = accountNumber;
     this.name = name;
     this.pin = pin;
     this.balance = balance;
 }

 public boolean authenticate(String inputPin) {
     if (isLocked) {
         System.out.println("Account is locked due to multiple failed attempts.");
         return false;
     }

     if (pin.equals(inputPin)) {
         wrongAttempts = 0;
         return true;
     } else {
         wrongAttempts++;
         if (wrongAttempts >= 3) {
             isLocked = true;
             System.out.println("Account locked due to 3 incorrect PIN attempts.");
         }
         return false;
     }
 }

 public void deposit(double amount) {
     balance += amount;
     transactionHistory.add("Deposited: " + amount);
 }

 public void withdraw(double amount) throws InsufficientBalanceException {
     if (amount > balance) {
         throw new InsufficientBalanceException("Insufficient Balance!");
     }
     balance -= amount;
     transactionHistory.add("Withdrawn: " + amount);
 }

 public void showTransactions() {
     for (String t : transactionHistory) {
         System.out.println(t);
     }
 }

 public double getBalance() {
     return balance;
 }

 public String getAccountNumber() {
     return accountNumber;
 }
}

//Savings Account
class SavingsAccount extends Account {
 public SavingsAccount(String accountNumber, String name, String pin, double balance) {
     super(accountNumber, name, pin, balance);
 }
}

//Main Application
public class BankingApp {
 static Scanner sc = new Scanner(System.in);
 static Map<String, Account> accounts = new HashMap<>();

 public static void main(String[] args) {
     while (true) {
         System.out.println("\n==== Banking System ====");
         System.out.println("1. Create Account");
         System.out.println("2. Login");
         System.out.println("3. Exit");
         System.out.print("Choose option: ");
         int choice = sc.nextInt();

         switch (choice) {
             case 1:
                 createAccount();
                 break;
             case 2:
                 login();
                 break;
             case 3:
                 System.out.println("Thank you!");
                 System.exit(0);
             default:
                 System.out.println("Invalid option!");
         }
     }
 }

 static void createAccount() {
     System.out.print("Enter Name: ");
     String name = sc.next();
     System.out.print("Set PIN: ");
     String pin = sc.next();
     System.out.print("Initial Deposit: ");
     double balance = sc.nextDouble();

     String accNo = "ACC" + (accounts.size() + 1);
     Account acc = new SavingsAccount(accNo, name, pin, balance);
     accounts.put(accNo, acc);

     System.out.println("Account created successfully! Account Number: " + accNo);
 }

 static void login() {
     System.out.print("Enter Account Number: ");
     String accNo = sc.next();
     Account acc = accounts.get(accNo);

     if (acc == null) {
         System.out.println("Account not found!");
         return;
     }

     System.out.print("Enter PIN: ");
     String pin = sc.next();

     if (!acc.authenticate(pin)) {
         System.out.println("Authentication Failed!");
         return;
     }

     userMenu(acc);
 }

 static void userMenu(Account acc) {
     while (true) {
         System.out.println("\n---- Welcome " + acc.name + " ----");
         System.out.println("1. Deposit");
         System.out.println("2. Withdraw");
         System.out.println("3. Transfer");
         System.out.println("4. Check Balance");
         System.out.println("5. Transaction History");
         System.out.println("6. Logout");

         int choice = sc.nextInt();

         try {
             switch (choice) {
                 case 1:
                     System.out.print("Amount: ");
                     acc.deposit(sc.nextDouble());
                     break;

                 case 2:
                     System.out.print("Amount: ");
                     acc.withdraw(sc.nextDouble());
                     break;

                 case 3:
                     System.out.print("Enter Receiver Account No: ");
                     String receiverAccNo = sc.next();
                     Account receiver = accounts.get(receiverAccNo);

                     if (receiver == null) {
                         System.out.println("Receiver not found!");
                         break;
                     }

                     System.out.print("Amount: ");
                     double amount = sc.nextDouble();

                     acc.withdraw(amount);
                     receiver.deposit(amount);
                     System.out.println("Transfer Successful!");
                     break;

                 case 4:
                     System.out.println("Balance: " + acc.getBalance());
                     break;

                 case 5:
                     acc.showTransactions();
                     break;

                 case 6:
                     return;

                 default:
                     System.out.println("Invalid choice!");
             }
         } catch (InsufficientBalanceException e) {
             System.out.println(e.getMessage());
         }
     }
 }
}

