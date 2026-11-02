import java.util.ArrayList;
import java.util.Scanner;

// Base class
abstract class Person {
    private String fullName;
    private int age;

    public Person(String fullName, int age) {
        this.fullName = fullName;
        this.age = age;
    }

    public String getFullName() {
        return fullName;
    }

    public int getAge() {
        return age;
    }

    public void displayInfo() {
        System.out.println("Full Name: " + fullName);
        System.out.println("Age: " + age);
    }
}

// Guest class
class Guest extends Person {
    private String cccd; // Citizen ID card

    public Guest(String fullName, int age, String cccd) {
        super(fullName, age);
        this.cccd = cccd;
    }

    public String getCccd() {
        return cccd;
    }

    @Override
    public void displayInfo() {
        System.out.println("--- Guest Info ---");
        System.out.println("CCCD: " + cccd);
        super.displayInfo();
    }
}

// Staff class
class Staff extends Person {
    private String licenseId;
    private String employeeId;

    public Staff(String fullName, int age, String licenseId, String employeeId) {
        super(fullName, age);
        this.licenseId = licenseId;
        this.employeeId = employeeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getLicenseId() {
        return licenseId;
    }

    @Override
    public void displayInfo() {
        System.out.println("--- Staff Info ---");
        System.out.println("Employee ID: " + employeeId);
        System.out.println("License ID: " + licenseId);
        super.displayInfo();
    }
}

// Room class
class Room {
    private String roomType;
    private double pricePerDay;

    public Room(String roomType) {
        this.roomType = roomType.toUpperCase();
        this.pricePerDay = calculatePricePerDay(this.roomType);
    }

    private double calculatePricePerDay(String type) {
        switch (type) {
            case "VIP1":
                return 900000.0;
            case "VIP2":
                return 700000.0;
            case "VIP3":
                return 500000.0;
            default:
                throw new IllegalArgumentException("Invalid room type.");
        }
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public double calculateRentalCost(int days) {
        if (days <= 0) {
            throw new IllegalArgumentException("Number of rental days must be greater than 0.");
        }
        return this.pricePerDay * days;
    }
}

// Main program
public class ex3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Guest> guests = new ArrayList<>();
        ArrayList<Staff> staffList = new ArrayList<>();

        // 1. Input Staff
        System.out.print("Enter number of staff members: ");
        int numStaff = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < numStaff; i++) {
            System.out.println("\nStaff " + (i + 1));
            System.out.print("Full name: ");
            String name = sc.nextLine();

            System.out.print("Age: ");
            int age = Integer.parseInt(sc.nextLine());

            System.out.print("License ID: ");
            String licenseId = sc.nextLine();

            System.out.print("Employee ID: ");
            String empId = sc.nextLine();

            staffList.add(new Staff(name, age, licenseId, empId));
        }

        // 2. Input Guests
        System.out.print("\nEnter number of guests: ");
        int numGuests = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < numGuests; i++) {
            System.out.println("\nGuest " + (i + 1));
            System.out.print("CCCD: ");
            String cccd = sc.nextLine();

            System.out.print("Full name: ");
            String name = sc.nextLine();

            System.out.print("Age: ");
            int age = Integer.parseInt(sc.nextLine());

            if (age < 0) {
                System.out.println("Invalid age.");
                i--;
                continue;
            }

            guests.add(new Guest(name, age, cccd));
        }

        // Display all guests
        System.out.println("\n=== Current Guest List ===");
        if (guests.isEmpty()) {
            System.out.println("No guests registered.");
        } else {
            for (Guest g : guests) {
                g.displayInfo();
                System.out.println();
            }
        }

        // 3. Remove a guest operation
        System.out.print("Enter CCCD of guest to remove: ");
        String removeCccd = sc.nextLine();
        boolean removed = false;

        for (int i = 0; i < guests.size(); i++) {
            if (guests.get(i).getCccd().equals(removeCccd)) {
                guests.remove(i);
                removed = true;
                System.out.println("Guest with CCCD " + removeCccd + " removed successfully.");
                break;
            }
        }

        if (!removed) {
            System.out.println("Guest with CCCD " + removeCccd + " not found.");
        }

        // Display guest list after removal
        System.out.println("\n=== Guest List After Removal ===");
        if (guests.isEmpty()) {
            System.out.println("No guests currently registered.");
        } else {
            for (Guest g : guests) {
                g.displayInfo();
                System.out.println();
            }
        }

        // 4. Room Rental Cost Calculation
        System.out.println("\n=== Room Rental Calculation ===");
        try {
            System.out.print("Enter room type (VIP1, VIP2, VIP3): ");
            String roomType = sc.nextLine();

            System.out.print("Enter number of rental days: ");
            int days = Integer.parseInt(sc.nextLine());

            Room room = new Room(roomType);
            double totalCost = room.calculateRentalCost(days);

            System.out.println("\n--- Rental Summary ---");
            System.out.println("Room Type: " + room.getRoomType());
            System.out.println("Price per Day: " + String.format("%,.0f", room.getPricePerDay()) + " VND");
            System.out.println("Rental Days: " + days);
            System.out.println("Total Rental Cost: " + String.format("%,.0f", totalCost) + " VND");

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        sc.close();
    }
}