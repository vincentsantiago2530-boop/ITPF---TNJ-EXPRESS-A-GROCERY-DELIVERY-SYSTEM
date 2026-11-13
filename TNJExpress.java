import java.util.ArrayList;
import java.util.Scanner;

public class TNJExpressMain {
    private static Scanner input = new Scanner(System.in);
    private static ArrayList<Product> inventory = new ArrayList<>();
    private static ArrayList<Transaction> transactions = new ArrayList<>();
    private static Shop tnjShop;

    public static void main(String[] args) {
        tnjShop = new Shop("TNJ EXPRESS", "Santa Rosa, Nueva Ecija");
        seedGroceryProducts();

        int choice = 0;
        do {
            tnjShop.displayShop();
            System.out.println("1. Add Grocery Product");
            System.out.println("2. View Grocery Inventory");
            System.out.println("3. Search Grocery Product");
            System.out.println("4. Update Grocery Product");
            System.out.println("5. Delete Grocery Product");
            System.out.println("6. Process Grocery Order / Delivery");
            System.out.println("7. View All Delivery Transactions");
            System.out.println("0. Exit");
            choice = readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    viewProducts();
                    break;
                case 3:
                    searchProduct();
                    break;
                case 4:
                    updateProduct();
                    break;
                case 5:
                    deleteProduct();
                    break;
                case 6:
                    processOrder();
                    break;
                case 7:
                    viewTransactions();
                    break;
                case 0:
                    System.out.println("Thank you for using TNJ Express Grocery Delivery System!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select 0-7.");
            }
        } while (choice != 0);

        input.close();
    }

    private static void seedGroceryProducts() {
        inventory.add(new Product("Jasmine Rice (5kg)", 275.00, 20));
        inventory.add(new Product("Fresh Eggs (1 Tray)", 240.00, 15));
        inventory.add(new Product("Cooking Oil (1L)", 95.00, 30));
        inventory.add(new Product("Canned Sardines", 26.00, 50));
        inventory.add(new Product("White Sugar (1kg)", 82.00, 25));
    }

    private static void addProduct() {
        System.out.println("\n--- ADD GROCERY PRODUCT ---");
        String name = readNonEmptyString("Enter product name: ");
        double price = readPositiveDouble("Enter price (Php): ");
        int qty = readPositiveInt("Enter stock quantity: ");

        inventory.add(new Product(name, price, qty));
        System.out.println("Grocery product added successfully!\n");
    }

    private static void viewProducts() {
        System.out.println("\n--- TNJ EXPRESS GROCERY INVENTORY ---");
        if (inventory.isEmpty()) {
            System.out.println("No grocery products available.\n");
            return;
        }
        for (int i = 0; i < inventory.size(); i++) {
            System.out.print((i + 1) + ". ");
            inventory.get(i).displayProduct();
        }
        System.out.println();
    }

    private static void searchProduct() {
        System.out.println("\n--- SEARCH GROCERY PRODUCT ---");
        String keyword = readNonEmptyString("Enter product name to search: ").toLowerCase();
        boolean found = false;

        for (Product p : inventory) {
            if (p.getProductName().toLowerCase().contains(keyword)) {
                p.displayProduct();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching grocery product found.");
        }
        System.out.println();
    }

    private static void updateProduct() {
        viewProducts();
        if (inventory.isEmpty()) return;

        int index = readInt("Select product number to update: ") - 1;
        if (index < 0 || index >= inventory.size()) {
            System.out.println("Invalid product number.\n");
            return;
        }

        Product p = inventory.get(index);
        String newName = readNonEmptyString("Enter new name (" + p.getProductName() + "): ");
        double newPrice = readPositiveDouble("Enter new price: ");
        int newQty = readPositiveInt("Enter new stock quantity: ");

        p.setProductName(newName);
        p.setPrice(newPrice);
        p.setQuantity(newQty);
        System.out.println("Grocery product updated successfully!\n");
    }

    private static void deleteProduct() {
        viewProducts();
        if (inventory.isEmpty()) return;

        int index = readInt("Select product number to delete: ") - 1;
        if (index < 0 || index >= inventory.size()) {
            System.out.println("Invalid product number.\n");
            return;
        }

        Product removed = inventory.remove(index);
        System.out.println("Deleted: " + removed.getProductName() + "\n");
    }

    private static void processOrder() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty. Cannot process order.\n");
            return;
        }

        System.out.println("\n--- CUSTOMER DETAILS ---");
        String cName = readNonEmptyString("Enter customer name: ");
        String cAddress = readNonEmptyString("Enter delivery address: ");
        String cPhone = readNonEmptyString("Enter phone number: ");
        Customer customer = new Customer(cName, cAddress, cPhone);

        ArrayList<Product> cart = new ArrayList<>();
        char addMore;

        do {
            viewProducts();
            int prodIndex = readInt("Select product number to order: ") - 1;

            if (prodIndex >= 0 && prodIndex < inventory.size()) {
                Product selected = inventory.get(prodIndex);
                if (selected.getQuantity() == 0) {
                    System.out.println("Sorry, this item is out of stock.");
                } else {
                    int buyQty = readPositiveInt("Enter quantity (Available: " + selected.getQuantity() + "): ");
                    if (buyQty <= selected.getQuantity()) {
                        selected.setQuantity(selected.getQuantity() - buyQty);
                        cart.add(new Product(selected.getProductName(), selected.getPrice(), buyQty));
                        System.out.println("Added to grocery basket!");
                    } else {
                        System.out.println("Insufficient stock!");
                    }
                }
            } else {
                System.out.println("Invalid product selection.");
            }

            System.out.print("Add another grocery item? (Y/N): ");
            String resp = input.nextLine().trim().toUpperCase();
            addMore = resp.isEmpty() ? 'N' : resp.charAt(0);
        } while (addMore == 'Y');

        if (cart.isEmpty()) {
            System.out.println("Order cancelled (no items selected).\n");
            return;
        }

        int orderType = readInt("Service Type (1 = Store Pickup, 2 = Door-to-Door Delivery): ");
        String modeOfPayment = readNonEmptyString("Enter mode of payment (COD / GCash / Maya / Cash): ");
        String deliveryDateTime = readNonEmptyString("Enter delivery/pickup date & time (e.g., 2026-11-20 3:00 PM): ");

        Transaction tx;
        if (orderType == 2) {
            double fee = readPositiveDouble("Enter delivery fee (Php): ");
            tx = new DeliveryTransaction(tnjShop, customer, cart, 0, modeOfPayment, deliveryDateTime, fee);
        } else {
            tx = new Transaction(tnjShop, customer, cart, 0, modeOfPayment, deliveryDateTime);
        }

        double total = tx.computeTotal();
        System.out.printf("Total Amount Due: Php %.2f%n", total);

        double payment;
        do {
            payment = readPositiveDouble("Enter payment amount (Php): ");
            if (payment < total) {
                System.out.println("Insufficient payment! Please enter at least Php " + total);
            }
        } while (payment < total);

        tx.setPayment(payment);
        transactions.add(tx);
        tx.displayReceipt();
    }

    private static void viewTransactions() {
        System.out.println("\n--- TNJ EXPRESS TRANSACTION HISTORY ---");
        if (transactions.isEmpty()) {
            System.out.println("No transactions recorded yet.\n");
            return;
        }
        for (int i = 0; i < transactions.size(); i++) {
            System.out.print((i + 1) + ". ");
            transactions.get(i).printSummary();
        }
        System.out.println();
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    private static int readPositiveInt(String prompt) {
        while (true) {
            int val = readInt(prompt);
            if (val > 0) return val;
            System.out.println("Value must be greater than zero.");
        }
    }

    private static double readPositiveDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double val = Double.parseDouble(input.nextLine().trim());
                if (val >= 0) return val;
                System.out.println("Value cannot be negative.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private static String readNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String text = input.nextLine().trim();
            if (!text.isEmpty()) return text;
            System.out.println("Input cannot be empty.");
        }
    }
}
