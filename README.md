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
## Bank Management
```
📌 Project Description

The Bank Management System is a simple console-based Java application that allows users to:
Create a bank account
View account details
Deposit money
Withdraw money
Manage multiple accounts

🔄 Workflow

User starts the application and sees the main menu.
User selects Create Account and enters account number and holder name.
Account is stored in an ArrayList.
User can View Account details using account number.
User can Deposit money, which increases the account balance.
User can Withdraw money if sufficient balance is available.
If account is not found or balance is insufficient, system shows an error message.
User exits the system when finished.

🛠 Concepts Used (One Line Explanation)

Class & Object – BankAccount, BankService, and BankApp represent system components.
Encapsulation – Account details are private and accessed through getters.
ArrayList – Stores multiple bank accounts dynamically.
Method Creation – Separate methods for deposit, withdraw, and account search.
Loop & Switch – Used for menu-driven console interaction.
Basic Validation Logic – Checks account existence and sufficient balance.
```

## ECommerce System
```
📌 Project Description

The E-Commerce System is a console-based Java application that simulates an Amazon/Flipkart-style platform with Admin and Customer roles.

It allows:
User registration and login
Role-based access (Admin / Customer)
Admin to add and manage products
Customers to browse and search products
Add products to cart
Checkout and place orders
Order history tracking
Stock management with exception handling

🔄 Workflow

User registers or logs in.
System authenticates credentials.
If Admin → can add and view products.
If Customer → can browse, search, add to cart, and checkout.
Products are sorted by rating when displayed.
During checkout, total amount is calculated.
If stock is insufficient, custom exception is thrown.
Order is stored in user order history after successful checkout.

🛠 Concepts Used (One Line Explanation)

Class & Object – Product, User, and ECommerceApp represent system components.
Encapsulation – Product and User data are controlled using private variables.
Custom Exception – OutOfStockException handles stock errors.
Enums – Role and Category improve readability and type safety.
Collections (HashMap) – Stores users and products efficiently.
ArrayList – Used for cart and order history storage.
Streams API – Used for sorting, filtering, and total calculations.
Comparator – Sorts products by rating (highest first).
Exception Handling (try-catch) – Manages runtime stock issues.
Role-Based Access Control – Different menus for Admin and Customer.
Menu-Driven System (Switch + Loop) – Console interaction logic.
