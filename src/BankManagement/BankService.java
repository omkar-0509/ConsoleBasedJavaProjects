package BankManagement;

import java.util.ArrayList;

public class BankService {

    ArrayList<BankAccount> accounts = new ArrayList<>();

    // Create account
    public void createAccount(BankAccount account) {
        accounts.add(account);
        System.out.println("Account Created Successfully!");
    }

    // Find account
    public BankAccount findAccount(int accNo) {
        for (BankAccount acc : accounts) {
            if (acc.getAccountNumber() == accNo) {
                return acc;
            }
        }
        return null;
    }

    // View account
    public void viewAccount(int accNo) {
        BankAccount acc = findAccount(accNo);
        if (acc != null) {
            acc.display();
        } else {
            System.out.println("Account Not Found");
        }
    }
}
