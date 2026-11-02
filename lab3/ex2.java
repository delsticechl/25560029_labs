import java.util.ArrayList;
import java.util.Scanner;

// Base class
abstract class Ticket {
    private String tickerId;
    private String holderName;
    private int yearOfBirth;
    protected int gamePlayed; // Changed from private to protected so subclasses can access it

    public Ticket(String tickerId, String holderName, int yearOfBirth, int gamePlayed) {
        this.tickerId = tickerId;
        this.holderName = holderName;
        this.yearOfBirth = yearOfBirth;
        this.gamePlayed = gamePlayed;
        // REMOVED: this.gpa = gpa; (gpa does not exist in Ticket)
    }

    public String getTickerId() {
        return tickerId;
    }

    public String getHolderName() {
        return holderName;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public int getGamePlayed() {
        return gamePlayed;
    }

    public void displayInfo() {
        System.out.println("ID: " + tickerId);
        System.out.println("Name: " + holderName);
        System.out.println("Year of Birth: " + yearOfBirth);
        System.out.println("Games Played: " + gamePlayed);
    }
    
    public abstract int intCalculatePrice(); // Keeping your abstract method structure
    public abstract int calculatePrice();
}

// Partial Package Ticket (Class name matched with constructor name)
class PartialPackageTicket extends Ticket {

    public PartialPackageTicket(String tickerId, String holderName, int yearOfBirth, int gamePlayed) {
        super(tickerId, holderName, yearOfBirth, gamePlayed);
    }

    @Override
    public int calculatePrice() {
        return (gamePlayed * 20000) + 70000;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Ticket Type: Partial");
    }
}

// Full Package Ticket (Class name matched with constructor name)
class FullPackageTicket extends Ticket {

    public FullPackageTicket(String tickerId, String holderName, int yearOfBirth, int gamePlayed) {
        super(tickerId, holderName, yearOfBirth, gamePlayed);
    }

    @Override
    public int calculatePrice() {
        return 200000;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Ticket Type: FullPackage");
    }
}

// Main program
public class ex2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Ticket> tickets = new ArrayList<>();

        System.out.print("Enter number of tickets: ");
        int n = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < n; i++) {
            System.out.println("\nTicket " + (i + 1));
            System.out.print("Type (1-Partial, 2-Full): ");
            int type = Integer.parseInt(sc.nextLine());

            if (type != 1 && type != 2) {
                System.out.println("Invalid ticket type.");
                i--;
                continue;
            }

            System.out.print("Ticket ID: ");
            String id = sc.nextLine();

            System.out.print("Holder name: ");
            String name = sc.nextLine();

            System.out.print("Year of birth: ");
            int yearOfBirth = Integer.parseInt(sc.nextLine());

            System.out.print("Games played: ");
            int gamePlayed = Integer.parseInt(sc.nextLine());

            if (gamePlayed < 0 || gamePlayed > 30) {
                System.out.println("Invalid number of games played.");
                i--;
                continue;
            }

            if (type == 1) {
                tickets.add(new PartialPackageTicket(id, name, yearOfBirth, gamePlayed));
            } else {
                tickets.add(new FullPackageTicket(id, name, yearOfBirth, gamePlayed));
            }
        }
        
        // Count number of Partial Ticket 
        int count = 0;
        for (Ticket s : tickets) {
            if (s instanceof PartialPackageTicket) {
                count++;
            }
        }

        // Total revenue
        int totalRevenue = 0;
        for (Ticket t : tickets) {
            totalRevenue += t.calculatePrice();
        }

        System.out.println("\nNumber of Partial Tickets: " + count);
        System.out.println("Total Revenue: " + String.format("%,d", totalRevenue) + " VND");

        sc.close();
    }
}