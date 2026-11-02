import java.util.ArrayList;
import java.util.Scanner;

// Base class
abstract class Singer {
    private String fullName;
    private int yearsOfExperience;
    private int albumsSold;
    private int performances;

    public Singer(String fullName, int yearsOfExperience, int albumsSold, int performances) {
        this.fullName = fullName;
        this.yearsOfExperience = yearsOfExperience;
        this.albumsSold = albumsSold;
        this.performances = performances;
    }

    public String getFullName() {
        return fullName;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public int getAlbumsSold() {
        return albumsSold;
    }

    public int getPerformances() {
        return performances;
    }

    public abstract double calculateSalary();

    public void displayInfo() {
        System.out.println("Full Name: " + fullName);
        System.out.println("Years of Experience: " + yearsOfExperience);
        System.out.println("Albums Sold: " + albumsSold);
        System.out.println("Performances: " + performances);
    }
}

// Regular Singer class
class RegularSinger extends Singer {

    public RegularSinger(String fullName, int yearsOfExperience, int albumsSold, int performances) {
        super(fullName, yearsOfExperience, albumsSold, performances);
    }

    @Override
    public double calculateSalary() {
        return 3000000.0 
             + 500000.0 * getYearsOfExperience() 
             + 1000.0 * getAlbumsSold() 
             + 200000.0 * getPerformances();
    }

    @Override
    public void displayInfo() {
        System.out.println("Regular Singer");
        super.displayInfo();
        System.out.println("Salary: " + String.format("%,.0f", calculateSalary()) + " VND");
    }
}

// Famous Singer class
class FamousSinger extends Singer {
    private int gameShowsAttended;

    public FamousSinger(String fullName, int yearsOfExperience, int albumsSold, int performances, int gameShowsAttended) {
        super(fullName, yearsOfExperience, albumsSold, performances);
        this.gameShowsAttended = gameShowsAttended;
    }

    public int getGameShowsAttended() {
        return gameShowsAttended;
    }

    @Override
    public double calculateSalary() {
        return 5000000.0 
             + 500000.0 * getYearsOfExperience() 
             + 1200.0 * getAlbumsSold() 
             + 500000.0 * getPerformances() 
             + 500000.0 * gameShowsAttended;
    }

    @Override
    public void displayInfo() {
        System.out.println("Famous Singer");
        super.displayInfo();
        System.out.println("Game Shows Attended: " + gameShowsAttended);
        System.out.println("Salary: " + String.format("%,.0f", calculateSalary()) + " VND");
    }
}

// Main program
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Singer> singers = new ArrayList<>();

        System.out.print("Enter number of singers: ");
        int n = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < n; i++) {
            System.out.println("\nSinger " + (i + 1));
            System.out.print("Type (1-Regular Singer, 2-Famous Singer): ");
            int type = Integer.parseInt(sc.nextLine());

            if (type != 1 && type != 2) {
                System.out.println("Invalid singer type. Please try again.");
                i--;
                continue;
            }

            System.out.print("Full name: ");
            String fullName = sc.nextLine();

            System.out.print("Years of experience: ");
            int years = Integer.parseInt(sc.nextLine());

            System.out.print("Number of albums sold: ");
            int albums = Integer.parseInt(sc.nextLine());

            System.out.print("Number of performances: ");
            int performances = Integer.parseInt(sc.nextLine());

            if (years < 0 || albums < 0 || performances < 0) {
                System.out.println("Invalid input. Numeric attributes cannot be negative.");
                i--;
                continue;
            }

            if (type == 1) {
                singers.add(new RegularSinger(fullName, years, albums, performances));
            } else {
                System.out.print("Number of game shows attended: ");
                int gameShows = Integer.parseInt(sc.nextLine());

                if (gameShows < 0) {
                    System.out.println("Invalid game shows count.");
                    i--;
                    continue;
                }

                singers.add(new FamousSinger(fullName, years, albums, performances, gameShows));
            }
        }

        // Display all singers and their calculated salaries
        System.out.println("\nLIST OF ALL SINGERS\n");
        if (singers.isEmpty()) {
            System.out.println("No singers recorded.");
        } else {
            for (Singer s : singers) {
                s.displayInfo();
                System.out.println();
            }
        }

        // Find the highest-paid singer
        if (!singers.isEmpty()) {
            Singer highestPaid = singers.get(0);
            for (int i = 1; i < singers.size(); i++) {
                if (singers.get(i).calculateSalary() > highestPaid.calculateSalary()) {
                    highestPaid = singers.get(i);
                }
            }

            System.out.println("HIGHEST-PAID SINGER\n");
            highestPaid.displayInfo();
        }

        sc.close();
    }
}