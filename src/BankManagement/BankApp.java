package BankManagement;

import java.util.Scanner;

public class BankApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        BankService service = new BankService();

        while (true) {
            System.out.println("\n--- Bank Account Management ---");
            System.out.println("1. Create Account");
            System.out.println("2. View Account");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Account Number: ");
                    int accNo = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Holder Name: ");
                    String name = sc.nextLine();

                    service.createAccount(new BankAccount(accNo, name, 0));
                    break;

                case 2:
                    System.out.print("Enter Account Number: ");
                    service.viewAccount(sc.nextInt());
                    break;

                case 3:
                    System.out.print("Account Number: ");
                    int dAcc = sc.nextInt();

                    System.out.print("Amount: ");
                    double amount = sc.nextDouble();

                    BankAccount dAccount = service.findAccount(dAcc);
                    if (dAccount != null) {
                        dAccount.deposit(amount);
                        System.out.println("Deposit Successful");
                    } else {
                        System.out.println("Account Not Found");
                    }
                    break;

                case 4:
                    System.out.print("Account Number: ");
                    int wAcc = sc.nextInt();

                    System.out.print("Amount: ");
                    double wAmt = sc.nextDouble();

                    BankAccount wAccount = service.findAccount(wAcc);
                    if (wAccount != null && wAccount.withdraw(wAmt)) {
                        System.out.println("Withdrawal Successful");
                    } else {
                        System.out.println("Insufficient Balance / Account Not Found");
                    }
                    break;

                case 5:
                    System.out.println("Thank You for Banking!");
                    System.exit(0);

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }
}