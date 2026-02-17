## 1. Advanced ATM Simulation
```
🔄 Workflow

The Advanced ATM Simulation works as follows:
System initializes predefined user accounts with username, hashed PIN, and balance.
User enters username and system verifies if account exists.
User enters PIN, which is hashed using SHA-256 and matched with stored hash.
If PIN is incorrect 3 times, account gets locked automatically.
After successful login, user sees ATM menu options.
User can perform Deposit, Withdraw, Check Balance, or View Mini Statement.
Deposit adds amount and records transaction.
Withdraw checks limit (≤ 10000) and sufficient balance before deducting.
Mini Statement displays last 5 transactions.
User exits system when operation is complete.

🛠 Concepts Used (One Line Explanation)

Class & Object – Account and ATMSystem represent real-world banking entities.
Encapsulation – Sensitive data like PIN hash and balance are managed inside the Account class.
Custom Exception – AccountLockedException created to handle account locking logic.
Exception Handling – try-catch blocks handle authentication and security errors.
Hashing (SHA-256) – Secure PIN storage using MessageDigest.
Collections (HashMap) – Used to store and manage multiple accounts.
ArrayList – Maintains transaction history dynamically.
Streams API – Displays last 5 transactions efficiently.
Control Statements – Loop and switch used for menu-driven console system.
Basic Security Mechanism – Account locks after 3 failed login attempts.
```
## Advanced Banking System
```
📌 Project Description

The Advanced Banking System allows users to:
Create a new bank account
Login securely using account number and PIN
Deposit money
Withdraw money with balance validation
Transfer money between accounts
Check account balance
View transaction history
Auto-lock account after 3 incorrect PIN attempts

🔄 Workflow

The Advanced Banking System works as follows:
User starts the application and sees main menu (Create Account / Login / Exit).
If user selects Create Account, a new Savings Account is created with unique account number.
User logs in using account number and PIN.
System authenticates the PIN and locks account after 3 wrong attempts.
After successful login, user sees banking operations menu.
User can Deposit money, which increases balance and records transaction.
User can Withdraw money; system checks balance before deduction.
User can Transfer money to another account after validating receiver.
User can Check Balance anytime.
User can View Transaction History stored in ArrayList.
User logs out or exits the system.

🛠 Concepts Used (One Line Explanation)

Class & Object – Account, SavingsAccount, and BankingApp represent banking entities.
Abstraction – Abstract class Account defines common structure for all account types.
Inheritance – SavingsAccount extends the abstract Account class.
Encapsulation – Account data like balance and PIN are protected within the class.
Polymorphism – Method overriding possible for different account types in future.
Custom Exception – InsufficientBalanceException handles withdrawal errors.
Exception Handling – try-catch blocks manage transaction errors safely.
Collections (HashMap) – Stores all accounts dynamically.
ArrayList – Maintains transaction history.
Control Statements – Switch and loops manage menu-driven interface.
Basic Security Logic – Account locks after 3 failed login attempts.
```
