import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// 1. Customer Class (Encapsulation)
class Customer {
    private String customerId;
    private String fullName;
    private String address;
    private String phoneNumber;

    public Customer(String customerId, String fullName, String address, String phoneNumber) {
        this.customerId = customerId;
        this.fullName = fullName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getCustomerId() { return customerId; }
    public String getFullName() { return fullName; }
    public String getAddress() { return address; }
    public String getPhoneNumber() { return phoneNumber; }

    public void displayInfo() {
        System.out.println("Customer ID: " + customerId + " | Name: " + fullName + 
                           " | Address: " + address + " | Phone: " + phoneNumber);
    }
}

// 2. Product Class
class Product {
    private String productId;
    private String name;
    private double volume; // in ml
    private double unitPrice; // VND/ml

    public Product(String productId, String name, double volume, double unitPrice) {
        this.productId = productId;
        this.name = name;
        this.volume = volume;
        this.unitPrice = unitPrice;
    }

    public double calculatePrice() {
        return volume * unitPrice;
    }

    public String getName() { return name; }
    public double getVolume() { return volume; }
    public double getUnitPrice() { return unitPrice; }

    @Override
    public String toString() {
        return String.format("- %s (%.1f ml @ %,.0f VND/ml) = %,.0f VND", 
                name, volume, unitPrice, calculatePrice());
    }
}

// 3. Abstract Package Class (Inheritance & Polymorphism Base)
abstract class Package {
    protected String packageName;
    protected List<Product> products;

    public Package(String packageName) {
        this.packageName = packageName;
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public double calculatePackagePrice() {
        double total = 0;
        for (Product p : products) {
            total += p.calculatePrice();
        }
        return total;
    }

    public String getPackageName() { return packageName; }
    public List<Product> getProducts() { return products; }

    public abstract boolean validatePackage();
}

// 4. Romantic Package (Must include Rose Perfume; Shampoo and Shower Gel optional)
class RomanticPackage extends Package {
    public RomanticPackage() {
        super("Romantic Package");
    }

    @Override
    public boolean validatePackage() {
        boolean hasRosePerfume = false;
        for (Product p : products) {
            if (p.getName().equalsIgnoreCase("Rose Perfume")) {
                hasRosePerfume = true;
                break;
            }
        }
        return hasRosePerfume;
    }
}

// 5. Fresh-Air Package (Must include Oily Hair Shampoo; Perfume and Shower Gel optional)
class FreshAirPackage extends Package {
    public FreshAirPackage() {
        super("Fresh-Air Package");
    }

    @Override
    public boolean validatePackage() {
        boolean hasOilyShampoo = false;
        for (Product p : products) {
            if (p.getName().toLowerCase().contains("oily hair shampoo")) {
                hasOilyShampoo = true;
                break;
            }
        }
        return hasOilyShampoo;
    }
}

// 6. Order Class
class Order {
    private String orderId;
    private Customer customer;
    private String invoiceDate;
    private Package packageInfo;

    public Order(String orderId, Customer customer, String invoiceDate, Package packageInfo) {
        this.orderId = orderId;
        this.customer = customer;
        this.invoiceDate = invoiceDate;
        this.packageInfo = packageInfo;
    }

    public double getOrderPrice() {
        return packageInfo.calculatePackagePrice();
    }

    public void displayOrder() {
        System.out.println("Order ID: " + orderId + " | Date: " + invoiceDate);
        customer.displayInfo();
        System.out.println("Package Type: " + packageInfo.getPackageName());
        System.out.println("Included Products:");
        for (Product p : packageInfo.getProducts()) {
            System.out.println("  " + p);
        }
        System.out.println(String.format("Total Order Price: %,.0f VND\n", getOrderPrice()));
    }

    public String toFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order ID: ").append(orderId).append("\n");
        sb.append("Date: ").append(invoiceDate).append("\n");
        sb.append("Customer: ").append(customer.getFullName()).append(" (ID: ").append(customer.getCustomerId()).append(")\n");
        sb.append("Package: ").append(packageInfo.getPackageName()).append("\n");
        sb.append("Products:\n");
        for (Product p : packageInfo.getProducts()) {
            sb.append("  ").append(p.toString()).append("\n");
        }
        sb.append(String.format("Total Price: %,.0f VND\n\n", getOrderPrice()));
        return sb.toString();
    }
}

// 7. Main Execution Program
public class ex5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Order> orders = new ArrayList<>();

        System.out.print("Enter number of orders (N): ");
        int n = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < n; i++) {
            System.out.println("\n--- Entering Order " + (i + 1) + " ---");
            
            System.out.print("Order ID: ");
            String orderId = sc.nextLine();

            System.out.print("Invoice Date (DD/MM/YYYY): ");
            String invoiceDate = sc.nextLine();

            // Customer Info Input
            System.out.print("Customer ID: ");
            String custId = sc.nextLine();
            System.out.print("Customer Full Name: ");
            String custName = sc.nextLine();
            System.out.print("Customer Address: ");
            String custAddress = sc.nextLine();
            System.out.print("Customer Phone Number: ");
            String custPhone = sc.nextLine();

            Customer customer = new Customer(custId, custName, custAddress, custPhone);

            // Package Selection
            Package selectedPackage = null;
            while (true) {
                System.out.println("Select Package Type:");
                System.out.println("1. Romantic Package (Must include Rose Perfume)");
                System.out.println("2. Fresh-Air Package (Must include Oily Hair Shampoo)");
                System.out.print("Choice: ");
                int pkgChoice = Integer.parseInt(sc.nextLine());

                if (pkgChoice == 1) {
                    selectedPackage = new RomanticPackage();
                    // Mandatory Rose Perfume
                    System.out.print("Enter volume of Rose Perfume (ml) [Rate: 20,000 VND/ml]: ");
                    double vol = Double.parseDouble(sc.nextLine());
                    selectedPackage.addProduct(new Product("P1", "Rose Perfume", vol, 20000));

                    // Optional Shampoo
                    System.out.print("Include Shampoo? (y/n): ");
                    if (sc.nextLine().equalsIgnoreCase("y")) {
                        System.out.println("Select Shampoo Type: 1. Dry Hair Shampoo (20,000/ml)  2. Oily Hair Shampoo Standard 1 (30,000/ml)");
                        int shChoice = Integer.parseInt(sc.nextLine());
                        System.out.print("Enter volume (ml): ");
                        double shVol = Double.parseDouble(sc.nextLine());
                        if (shChoice == 1) {
                            selectedPackage.addProduct(new Product("P3", "Dry Hair Shampoo", shVol, 20000));
                        } else {
                            selectedPackage.addProduct(new Product("P4", "Oily Hair Shampoo - Standard 1", shVol, 30000));
                        }
                    }

                    // Optional Shower Gel
                    System.out.print("Include Shower Gel? (y/n): ");
                    if (sc.nextLine().equalsIgnoreCase("y")) {
                        System.out.println("Select Shower Gel Type: 1. Dry Skin (20,000/ml)  2. Oily Skin (40,000/ml)");
                        int sgChoice = Integer.parseInt(sc.nextLine());
                        System.out.print("Enter volume (ml): ");
                        double sgVol = Double.parseDouble(sc.nextLine());
                        if (sgChoice == 1) {
                            selectedPackage.addProduct(new Product("P6", "Dry Skin Shower Gel", sgVol, 20000));
                        } else {
                            selectedPackage.addProduct(new Product("P7", "Oily Skin Shower Gel", sgVol, 40000));
                        }
                    }

                } else if (pkgChoice == 2) {
                    selectedPackage = new FreshAirPackage();
                    // Mandatory Oily Hair Shampoo
                    System.out.println("Select Oily Hair Shampoo Standard: 1. Standard 1 (30,000/ml)  2. Standard 2 (40,000/ml)");
                    int oChoice = Integer.parseInt(sc.nextLine());
                    System.out.print("Enter volume (ml): ");
                    double oVol = Double.parseDouble(sc.nextLine());
                    if (oChoice == 1) {
                        selectedPackage.addProduct(new Product("P4", "Oily Hair Shampoo - Standard 1", oVol, 30000));
                    } else {
                        selectedPackage.addProduct(new Product("P5", "Oily Hair Shampoo - Standard 2", oVol, 40000));
                    }

                    // Optional Perfume
                    System.out.print("Include Perfume? (y/n): ");
                    if (sc.nextLine().equalsIgnoreCase("y")) {
                        System.out.println("Select Perfume: 1. Rose Perfume (20,000/ml)  2. Chamomile Perfume (15,000/ml)");
                        int pChoice = Integer.parseInt(sc.nextLine());
                        System.out.print("Enter volume (ml): ");
                        double pVol = Double.parseDouble(sc.nextLine());
                        if (pChoice == 1) {
                            selectedPackage.addProduct(new Product("P1", "Rose Perfume", pVol, 20000));
                        } else {
                            selectedPackage.addProduct(new Product("P2", "Chamomile Perfume", pVol, 15000));
                        }
                    }

                    // Optional Shower Gel
                    System.out.print("Include Shower Gel? (y/n): ");
                    if (sc.nextLine().equalsIgnoreCase("y")) {
                        System.out.println("Select Shower Gel Type: 1. Dry Skin (20,000/ml)  2. Oily Skin (40,000/ml)");
                        int sgChoice = Integer.parseInt(sc.nextLine());
                        System.out.print("Enter volume (ml): ");
                        double sgVol = Double.parseDouble(sc.nextLine());
                        if (sgChoice == 1) {
                            selectedPackage.addProduct(new Product("P6", "Dry Skin Shower Gel", sgVol, 20000));
                        } else {
                            selectedPackage.addProduct(new Product("P7", "Oily Skin Shower Gel", sgVol, 40000));
                        }
                    }
                } else {
                    System.out.println("Invalid package choice. Try again.");
                    continue;
                }

                if (selectedPackage.validatePackage()) {
                    break;
                } else {
                    System.out.println("Validation failed! Package requirement missing. Please re-enter.");
                }
            }

            orders.add(new Order(orderId, customer, invoiceDate, selectedPackage));
        }

        // Display all orders before saving
        System.out.println("\nALL ORDERS SUMMARY REPORT\n");
        for (Order o : orders) {
            o.displayOrder();
        }

        // Save complete order list to order_list.txt
        try (PrintWriter writer = new PrintWriter(new FileWriter("order_list.txt"))) {
            writer.println("STORE ORDER LIST EXPORT\n");
            for (Order o : orders) {
                writer.write(o.toFileString());
            }
            System.out.println("Successfully saved all orders to 'order_list.txt'.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving to file: " + e.getMessage());
        }

        sc.close();
    }
}