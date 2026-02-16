package HotelReservation;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

// ================= CUSTOM EXCEPTIONS =================

class RoomNotAvailableException extends Exception {
    public RoomNotAvailableException(String message) {
        super(message);
    }
}

class InvalidDateException extends Exception {
    public InvalidDateException(String message) {
        super(message);
    }
}

// ================= DISCOUNT STRATEGY =================

interface DiscountStrategy {
    double applyDiscount(double amount);
}

class NoDiscount implements DiscountStrategy {
    public double applyDiscount(double amount) {
        return amount;
    }
}

class SeasonalDiscount implements DiscountStrategy {
    public double applyDiscount(double amount) {
        return amount * 0.9; // 10% discount
    }
}

class PremiumCustomerDiscount implements DiscountStrategy {
    public double applyDiscount(double amount) {
        return amount * 0.85; // 15% discount
    }
}

// ================= ROOM CLASS =================

class Room {
    private int roomNumber;
    private String type;
    private double pricePerNight;
    private boolean isBooked;

    public Room(int roomNumber, String type, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.pricePerNight = pricePerNight;
        this.isBooked = false;
    }

    public synchronized void bookRoom() throws RoomNotAvailableException {
        if (isBooked) {
            throw new RoomNotAvailableException("Room already booked!");
        }
        isBooked = true;
    }

    public synchronized void checkoutRoom() {
        isBooked = false;
    }

    public int getRoomNumber() { return roomNumber; }
    public String getType() { return type; }
    public double getPricePerNight() { return pricePerNight; }
    public boolean isBooked() { return isBooked; }

    @Override
    public String toString() {
        return "Room " + roomNumber + " | Type: " + type +
                " | ₹" + pricePerNight + "/night | Available: " + !isBooked;
    }
}

// ================= BOOKING CLASS =================

class Booking {
    private Room room;
    private String customerName;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private double totalAmount;

    public Booking(Room room, String customerName,
                   LocalDate checkIn, LocalDate checkOut,
                   double totalAmount) {
        this.room = room;
        this.customerName = customerName;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalAmount = totalAmount;
    }

    public Room getRoom() { return room; }
    public double getTotalAmount() { return totalAmount; }
    public String getCustomerName() { return customerName; }

    @Override
    public String toString() {
        return "\nBooking Details:" +
                "\nCustomer: " + customerName +
                "\nRoom: " + room.getRoomNumber() +
                "\nCheck-in: " + checkIn +
                "\nCheck-out: " + checkOut +
                "\nTotal: ₹" + totalAmount;
    }
}

// ================= MAIN APPLICATION =================

public class HotelReservationSystem {

    static Scanner sc = new Scanner(System.in);
    static Map<Integer, Room> rooms = new ConcurrentHashMap<>();
    static Map<Integer, Booking> bookings = new HashMap<>();

    public static void main(String[] args) {

        // Predefined Rooms
        rooms.put(101, new Room(101, "Standard", 2000));
        rooms.put(102, new Room(102, "Deluxe", 3500));
        rooms.put(201, new Room(201, "Suite", 5000));

        while (true) {
            System.out.println("\n===== HOTEL RESERVATION SYSTEM =====");
            System.out.println("1. View Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Check-out");
            System.out.println("4. Cancel Booking");
            System.out.println("5. Exit");

            int choice = sc.nextInt();

            try {
                switch (choice) {
                    case 1: viewRooms(); break;
                    case 2: bookRoom(); break;
                    case 3: checkout(); break;
                    case 4: cancelBooking(); break;
                    case 5: System.exit(0);
                    default: System.out.println("Invalid option!");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // ================= VIEW ROOMS =================
    static void viewRooms() {
        rooms.values().forEach(System.out::println);
    }

    // ================= BOOK ROOM =================
    static void bookRoom() throws Exception {

        System.out.print("Enter Room Number: ");
        int roomNo = sc.nextInt();
        sc.nextLine();

        Room room = rooms.get(roomNo);

        if (room == null) {
            throw new RoomNotAvailableException("Room does not exist!");
        }

        System.out.print("Customer Name: ");
        String name = sc.nextLine();

        System.out.print("Check-in date (YYYY-MM-DD): ");
        LocalDate checkIn = LocalDate.parse(sc.next());

        System.out.print("Check-out date (YYYY-MM-DD): ");
        LocalDate checkOut = LocalDate.parse(sc.next());

        if (checkOut.isBefore(checkIn)) {
            throw new InvalidDateException("Check-out must be after check-in!");
        }

        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
        double baseAmount = nights * room.getPricePerNight();

        // Select Discount Strategy
        System.out.println("Select Discount:");
        System.out.println("1. No Discount");
        System.out.println("2. Seasonal Discount (10%)");
        System.out.println("3. Premium Customer (15%)");

        int discountChoice = sc.nextInt();

        DiscountStrategy strategy;

        switch (discountChoice) {
            case 2: strategy = new SeasonalDiscount(); break;
            case 3: strategy = new PremiumCustomerDiscount(); break;
            default: strategy = new NoDiscount();
        }

        double finalAmount = strategy.applyDiscount(baseAmount);

        room.bookRoom();

        Booking booking = new Booking(room, name, checkIn, checkOut, finalAmount);
        bookings.put(roomNo, booking);

        System.out.println("Booking Successful!");
        System.out.println(booking);
    }

    // ================= CHECKOUT =================
    static void checkout() {

        System.out.print("Enter Room Number: ");
        int roomNo = sc.nextInt();

        Booking booking = bookings.get(roomNo);

        if (booking == null) {
            System.out.println("No booking found.");
            return;
        }

        System.out.println("Processing Payment...");
        System.out.println("Payment Successful! ₹" + booking.getTotalAmount());

        booking.getRoom().checkoutRoom();
        bookings.remove(roomNo);

        System.out.println("Check-out completed.");
    }

    // ================= CANCEL BOOKING =================
    static void cancelBooking() {

        System.out.print("Enter Room Number: ");
        int roomNo = sc.nextInt();

        Booking booking = bookings.get(roomNo);

        if (booking == null) {
            System.out.println("No booking found.");
            return;
        }

        double refund = booking.getTotalAmount() * 0.8; // 80% refund

        booking.getRoom().checkoutRoom();
        bookings.remove(roomNo);

        System.out.println("Booking Cancelled.");
        System.out.println("Refund Amount: ₹" + refund);
    }
}
