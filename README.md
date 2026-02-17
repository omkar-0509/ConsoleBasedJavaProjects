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
