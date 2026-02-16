package ECommerce_System;
import java.util.*;

//================= CUSTOM EXCEPTION =================
class OutOfStockException extends Exception {
 public OutOfStockException(String message) {
     super(message);
 }
}

//================= ENUMS =================
enum Role {
 ADMIN,
 CUSTOMER
}

enum Category {
 ELECTRONICS,
 FASHION,
 HOME,
 BOOKS,
 GROCERY
}

//================= PRODUCT CLASS =================
class Product {
 private int id;
 private String name;
 private String brand;
 private String description;
 private Category category;
 private double price;
 private double discount;
 private double rating;
 private int stock;
 private String seller;

 public Product(int id, String name, String brand, String description,
                Category category, double price, double discount,
                double rating, int stock, String seller) {
     this.id = id;
     this.name = name;
     this.brand = brand;
     this.description = description;
     this.category = category;
     this.price = price;
     this.discount = discount;
     this.rating = rating;
     this.stock = stock;
     this.seller = seller;
 }

 public int getId() { return id; }
 public String getName() { return name; }
 public Category getCategory() { return category; }
 public double getPrice() { return price; }
 public double getRating() { return rating; }
 public int getStock() { return stock; }

 public double getFinalPrice() {
     return price - (price * discount / 100);
 }

 public void reduceStock(int quantity) throws OutOfStockException {
     if (quantity > stock) {
         throw new OutOfStockException("Only " + stock + " items left!");
     }
     stock -= quantity;
 }

 @Override
 public String toString() {
     return "\n----------------------------------" +
             "\nID: " + id +
             "\nName: " + name +
             "\nBrand: " + brand +
             "\nCategory: " + category +
             "\nSeller: " + seller +
             "\nRating: ⭐ " + rating +
             "\nOriginal Price: ₹" + price +
             "\nDiscount: " + discount + "%" +
             "\nFinal Price: ₹" + getFinalPrice() +
             "\nStock Left: " + stock +
             "\nDescription: " + description +
             "\n----------------------------------";
 }
}

//================= USER CLASS =================
class User {
 private String username;
 private String password;
 private Role role;
 private List<Product> cart = new ArrayList<>();
 private List<String> orders = new ArrayList<>();

 public User(String username, String password, Role role) {
     this.username = username;
     this.password = password;
     this.role = role;
 }

 public String getUsername() { return username; }
 public Role getRole() { return role; }

 public boolean authenticate(String password) {
     return this.password.equals(password);
 }

 public List<Product> getCart() { return cart; }

 public void addToCart(Product product) {
     cart.add(product);
 }

 public void clearCart() {
     cart.clear();
 }

 public void addOrder(String orderDetails) {
     orders.add(orderDetails);
 }

 public void viewOrders() {
     if (orders.isEmpty()) {
         System.out.println("No orders found.");
     } else {
         orders.forEach(System.out::println);
     }
 }
}

//================= MAIN APPLICATION =================
public class ECommerceApp {

 static Scanner sc = new Scanner(System.in);
 static Map<String, User> users = new HashMap<>();
 static Map<Integer, Product> products = new HashMap<>();
 static int productIdCounter = 1;

 public static void main(String[] args) {

     // Default Admin
     users.put("admin", new User("admin", "admin123", Role.ADMIN));

     while (true) {
         System.out.println("\n===== AMAZON / FLIPKART STYLE SYSTEM =====");
         System.out.println("1. Register");
         System.out.println("2. Login");
         System.out.println("3. Exit");
         System.out.print("Choose: ");
         int choice = sc.nextInt();

         switch (choice) {
             case 1 -> register();
             case 2 -> login();
             case 3 -> System.exit(0);
             default -> System.out.println("Invalid option!");
         }
     }
 }

 // ================= REGISTER =================
 static void register() {
     System.out.print("Username: ");
     String username = sc.next();
     System.out.print("Password: ");
     String password = sc.next();

     if (users.containsKey(username)) {
         System.out.println("Username already exists!");
         return;
     }

     users.put(username, new User(username, password, Role.CUSTOMER));
     System.out.println("Registration successful!");
 }

 // ================= LOGIN =================
 static void login() {
     System.out.print("Username: ");
     String username = sc.next();
     System.out.print("Password: ");
     String password = sc.next();

     User user = users.get(username);

     if (user == null || !user.authenticate(password)) {
         System.out.println("Invalid credentials!");
         return;
     }

     if (user.getRole() == Role.ADMIN) {
         adminMenu();
     } else {
         customerMenu(user);
     }
 }

 // ================= ADMIN MENU =================
 static void adminMenu() {
     while (true) {
         System.out.println("\n--- ADMIN MENU ---");
         System.out.println("1. Add Product");
         System.out.println("2. View Products");
         System.out.println("3. Logout");

         int choice = sc.nextInt();

         switch (choice) {
             case 1 -> addProduct();
             case 2 -> viewProducts();
             case 3 -> { return; }
             default -> System.out.println("Invalid option!");
         }
     }
 }

 // ================= ADD PRODUCT =================
 static void addProduct() {

     sc.nextLine();

     System.out.print("Product Name: ");
     String name = sc.nextLine();

     System.out.print("Brand: ");
     String brand = sc.nextLine();

     System.out.print("Description: ");
     String description = sc.nextLine();

     System.out.println("Select Category:");
     for (Category c : Category.values()) {
         System.out.println(c.ordinal() + 1 + ". " + c);
     }
     int catChoice = sc.nextInt();
     Category category = Category.values()[catChoice - 1];

     System.out.print("Price: ");
     double price = sc.nextDouble();

     System.out.print("Discount (%): ");
     double discount = sc.nextDouble();

     System.out.print("Rating (0-5): ");
     double rating = sc.nextDouble();

     System.out.print("Stock: ");
     int stock = sc.nextInt();

     sc.nextLine();
     System.out.print("Seller Name: ");
     String seller = sc.nextLine();

     Product product = new Product(
             productIdCounter++,
             name,
             brand,
             description,
             category,
             price,
             discount,
             rating,
             stock,
             seller
     );

     products.put(product.getId(), product);

     System.out.println("Product added successfully!");
 }

 // ================= VIEW PRODUCTS =================
 static void viewProducts() {
     if (products.isEmpty()) {
         System.out.println("No products available.");
         return;
     }

     products.values()
             .stream()
             .sorted(Comparator.comparing(Product::getRating).reversed())
             .forEach(System.out::println);
 }

 // ================= CUSTOMER MENU =================
 static void customerMenu(User user) {
     while (true) {
         System.out.println("\n--- CUSTOMER MENU ---");
         System.out.println("1. View Products");
         System.out.println("2. Search by Category");
         System.out.println("3. Add to Cart");
         System.out.println("4. View Cart");
         System.out.println("5. Checkout");
         System.out.println("6. View Orders");
         System.out.println("7. Logout");

         int choice = sc.nextInt();

         try {
             switch (choice) {
                 case 1 -> viewProducts();
                 case 2 -> searchByCategory();
                 case 3 -> addToCart(user);
                 case 4 -> viewCart(user);
                 case 5 -> checkout(user);
                 case 6 -> user.viewOrders();
                 case 7 -> { return; }
                 default -> System.out.println("Invalid option!");
             }
         } catch (OutOfStockException e) {
             System.out.println(e.getMessage());
         }
     }
 }

 // ================= SEARCH =================
 static void searchByCategory() {
     System.out.println("Select Category:");
     for (Category c : Category.values()) {
         System.out.println(c.ordinal() + 1 + ". " + c);
     }
     int choice = sc.nextInt();
     Category category = Category.values()[choice - 1];

     products.values()
             .stream()
             .filter(p -> p.getCategory() == category)
             .forEach(System.out::println);
 }

 // ================= ADD TO CART =================
 static void addToCart(User user) throws OutOfStockException {
     viewProducts();
     System.out.print("Enter Product ID: ");
     int id = sc.nextInt();

     Product product = products.get(id);

     if (product == null) {
         System.out.println("Product not found!");
         return;
     }

     System.out.print("Quantity: ");
     int quantity = sc.nextInt();

     product.reduceStock(quantity);

     for (int i = 0; i < quantity; i++) {
         user.addToCart(product);
     }

     System.out.println("Added to cart!");
 }

 // ================= VIEW CART =================
 static void viewCart(User user) {
     List<Product> cart = user.getCart();

     if (cart.isEmpty()) {
         System.out.println("Cart is empty.");
         return;
     }

     double total = 0;

     for (Product p : cart) {
         System.out.println(p.getName() + " - ₹" + p.getFinalPrice());
         total += p.getFinalPrice();
     }

     System.out.println("Total Amount: ₹" + total);
 }

 // ================= CHECKOUT =================
 static void checkout(User user) {
     List<Product> cart = user.getCart();

     if (cart.isEmpty()) {
         System.out.println("Cart is empty.");
         return;
     }

     double total = cart.stream()
             .mapToDouble(Product::getFinalPrice)
             .sum();

     String orderDetails = "Order placed | Total: ₹" + total + " | Items: " + cart.size();

     user.addOrder(orderDetails);
     user.clearCart();

     System.out.println("Checkout successful!");
     System.out.println(orderDetails);
 }
}
