package AdvancedATMSimulation;

import java.io.*;
import java.security.MessageDigest;
import java.util.*;

class AccountLockedException extends Exception {
    public AccountLockedException(String msg) {
        super(msg);
    }
}

class Account {
    String username;
    String pinHash;
    double balance;
    int failedAttempts = 0;
    boolean locked = false;
    List<String> transactions = new ArrayList<>();

    public Account(String username, String pin, double balance) {
        this.username = username;
        this.pinHash = hashPin(pin);
        this.balance = balance;
    }

    public static String hashPin(String pin) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(pin.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash)
                sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void authenticate(String pin) throws AccountLockedException {
        if (locked) throw new AccountLockedException("Account Locked!");

        if (hashPin(pin).equals(pinHash)) {
            failedAttempts = 0;
        } else {
            failedAttempts++;
            if (failedAttempts >= 3) {
                locked = true;
                throw new AccountLockedException("Account Locked after 3 attempts!");
            }
            throw new RuntimeException("Invalid PIN!");
        }
    }

    public void withdraw(double amount) {
        if (amount > 10000) {
            System.out.println("Withdraw limit exceeded!");
            return;
        }
        if (balance >= amount) {
            balance -= amount;
            transactions.add("Withdraw: " + amount);
        } else {
            System.out.println("Insufficient balance!");
        }
    }

    public void deposit(double amount) {
        balance += amount;
        transactions.add("Deposit: " + amount);
    }

    public void miniStatement() {
        System.out.println("\n--- Mini Statement ---");
        transactions.stream().skip(Math.max(0, transactions.size() - 5))
                .forEach(System.out::println);
    }
}

public class ATMSystem {
    static Map<String, Account> accounts = new HashMap<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        accounts.put("user1", new Account("user1", "1234", 50000));
        accounts.put("user2", new Account("user2", "5678", 30000));

        System.out.print("Enter username: ");
        String user = sc.nextLine();

        Account acc = accounts.get(user);
        if (acc == null) {
            System.out.println("User not found!");
            return;
        }

        try {
            System.out.print("Enter PIN: ");
            acc.authenticate(sc.nextLine());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        while (true) {
            System.out.println("\n1.Deposit 2.Withdraw 3.Balance 4.Statement 5.Exit");
            int ch = sc.nextInt();

            switch (ch) {
                case 1:
                    acc.deposit(sc.nextDouble());
                    break;
                case 2:
                    acc.withdraw(sc.nextDouble());
                    break;
                case 3:
                    System.out.println("Balance: " + acc.balance);
                    break;
                case 4:
                    acc.miniStatement();
                    break;
                case 5:
                    System.exit(0);
            }
        }
    }
}
