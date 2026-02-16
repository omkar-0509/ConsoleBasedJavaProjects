package MultiThreadedTicketBooking;

import java.util.*;
import java.util.concurrent.*;

// ================= CUSTOM EXCEPTION =================
class SeatNotAvailableException extends Exception {
    public SeatNotAvailableException(String message) {
        super(message);
    }
}

// ================= TICKET CLASS =================
class Ticket {
    private int seatNumber;
    private String user;

    public Ticket(int seatNumber, String user) {
        this.seatNumber = seatNumber;
        this.user = user;
    }

    public int getSeatNumber() { return seatNumber; }
    public String getUser() { return user; }

    @Override
    public String toString() {
        return "Seat " + seatNumber + " booked by " + user;
    }
}

// ================= TICKET BOOKING SERVICE =================
class TicketBookingService {

    private ConcurrentHashMap<Integer, Ticket> bookedSeats = new ConcurrentHashMap<>();
    private int totalSeats;

    public TicketBookingService(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    // THREAD SAFE BOOKING
    public synchronized void bookSeat(int seatNumber, String user)
            throws SeatNotAvailableException {

        if (seatNumber > totalSeats || seatNumber <= 0) {
            throw new SeatNotAvailableException("Invalid seat number!");
        }

        if (bookedSeats.containsKey(seatNumber)) {
            throw new SeatNotAvailableException("Seat already booked!");
        }

        // Simulate processing delay (race condition simulation)
        try { Thread.sleep(100); } catch (InterruptedException e) {}

        bookedSeats.put(seatNumber, new Ticket(seatNumber, user));
        System.out.println("SUCCESS: " + user + " booked seat " + seatNumber);
    }

    // THREAD SAFE CANCEL
    public synchronized void cancelSeat(int seatNumber, String user) {

        Ticket ticket = bookedSeats.get(seatNumber);

        if (ticket == null) {
            System.out.println("Seat not booked.");
            return;
        }

        if (!ticket.getUser().equals(user)) {
            System.out.println("You cannot cancel another user's booking.");
            return;
        }

        bookedSeats.remove(seatNumber);
        System.out.println("CANCELLED: Seat " + seatNumber + " by " + user);
    }

    public void showAvailableSeats() {
        System.out.println("\nAvailable Seats:");
        for (int i = 1; i <= totalSeats; i++) {
            if (!bookedSeats.containsKey(i)) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }

    public void showAllBookings() {
        if (bookedSeats.isEmpty()) {
            System.out.println("No bookings yet.");
            return;
        }

        bookedSeats.values().forEach(System.out::println);
    }
}

// ================= DEADLOCK SIMULATION =================
class DeadlockSimulation {

    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public void createDeadlock() {

        Thread t1 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("Thread 1 acquired Lock 1");
                try { Thread.sleep(100); } catch (InterruptedException e) {}
                synchronized (lock2) {
                    System.out.println("Thread 1 acquired Lock 2");
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (lock2) {
                System.out.println("Thread 2 acquired Lock 2");
                try { Thread.sleep(100); } catch (InterruptedException e) {}
                synchronized (lock1) {
                    System.out.println("Thread 2 acquired Lock 1");
                }
            }
        });

        t1.start();
        t2.start();
    }
}

// ================= MAIN APPLICATION =================
public class TicketBookingSystem {

    static Scanner sc = new Scanner(System.in);
    static TicketBookingService service = new TicketBookingService(10);

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(3);

        while (true) {
            System.out.println("\n===== MULTITHREADED TICKET BOOKING =====");
            System.out.println("1. Book Seat");
            System.out.println("2. Cancel Seat");
            System.out.println("3. Show Available Seats");
            System.out.println("4. Show All Bookings");
            System.out.println("5. Simulate Race Condition");
            System.out.println("6. Simulate Deadlock");
            System.out.println("7. Exit");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Enter Seat Number: ");
                    int seat = sc.nextInt();
                    System.out.print("Enter Username: ");
                    String user = sc.next();

                    executor.execute(() -> {
                        try {
                            service.bookSeat(seat, user);
                        } catch (Exception e) {
                            System.out.println("ERROR: " + e.getMessage());
                        }
                    });
                    break;

                case 2:
                    System.out.print("Enter Seat Number: ");
                    int cancelSeat = sc.nextInt();
                    System.out.print("Enter Username: ");
                    String cancelUser = sc.next();

                    executor.execute(() ->
                            service.cancelSeat(cancelSeat, cancelUser)
                    );
                    break;

                case 3:
                    service.showAvailableSeats();
                    break;

                case 4:
                    service.showAllBookings();
                    break;

                case 5:
                    simulateRaceCondition(executor);
                    break;

                case 6:
                    new DeadlockSimulation().createDeadlock();
                    break;

                case 7:
                    executor.shutdown();
                    System.exit(0);
            }
        }
    }

    // RACE CONDITION SIMULATION
    static void simulateRaceCondition(ExecutorService executor) {

        System.out.println("Simulating multiple users booking same seat...");

        executor.execute(() -> {
            try { service.bookSeat(5, "UserA"); }
            catch (Exception e) { System.out.println(e.getMessage()); }
        });

        executor.execute(() -> {
            try { service.bookSeat(5, "UserB"); }
            catch (Exception e) { System.out.println(e.getMessage()); }
        });
    }
}

