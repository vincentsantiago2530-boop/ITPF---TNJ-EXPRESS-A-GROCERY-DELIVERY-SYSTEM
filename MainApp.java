import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class MainApp {
    private static Scanner input = new Scanner(System.in);
    private static ArrayList<Shop> shopList = new ArrayList<>();
    private static ArrayList<Product> menuList = new ArrayList<>();
    private static ArrayList<Transaction> orderList = new ArrayList<>();

    public static void main(String[] args) {
        seedInitialData();
        int choice;

        do {
            displayMainMenu();
            choice = readInt("Enter your choice (0-6): ");

            switch (choice) {
                case 1:
                    viewMenuList();
                    break;
                case 2:
                    searchMenuList();
                    break;
                case 3:
                    placeOrder();
                    break;
                case 4:
                    viewOrders();
                    break;
                case 5:
                    updateOrder();
                    break;
                case 6:
                    deleteOrder();
                    break;
                case 0:
                    System.out.println("\nExiting TNJ Express: The Delivery System of The Pink Patissier. Thank you!");
                    break;
                default:
                    System.out.println("\n[Error] Invalid selection. Please choose 0 to 6.");
            }
        } while (choice != 0);

        input.close();
    }

    private static void seedInitialData() {
        Shop shop1 = new Shop("The Pink Patissier - Santa Rosa", "Brgy. Cojuangco, Santa Rosa, Nueva Ecija");
        Shop shop2 = new Shop("The Pink Patissier - Cabanatuan", "Maharlika Hwy, Cabanatuan City, Nueva Ecija");
        shopList.add(shop1);
        shopList.add(shop2);

        menuList.add(new Product("Brioche Doughnuts (Dozen)", 440.00, 1));
        menuList.add(new Product("Classic Ensaymada (6pcs)", 300.00, 1));
        menuList.add(new Product("Ensaymada with Creamcheese Filling (6pcs)", 360.00, 1));
        menuList.add(new Product("White Almond Cream Bread (6pcs)", 380.00, 1));
        menuList.add(new Product("Milky Cheese (6pcs)", 200.00, 1));
        menuList.add(new Product("Tiramisu Cake", 1380.00, 1));
        menuList.add(new Product("Ube Cake", 1380.00, 1));
        menuList.add(new Product("Banoffee", 1500.00, 1));
        menuList.add(new Product("Oreo Cake", 1500.00, 1));
        menuList.add(new Product("Oatmeal Cookies (6pcs)", 180.00, 1));
    }

    private static void displayMainMenu() {
        System.out.println("\n===============================================================");
        System.out.println("  TNJ EXPRESS: THE DELIVERY SYSTEM OF THE PINK PATISSIER       ");
        System.out.println("===============================================================");
        System.out.println("1. View Menu List");
        System.out.println("2. Search Menu List");
        System.out.println("3. Place New Order");
        System.out.println("4. View Order");
        System.out.println("5. Update Order");
        System.out.println("6. Delete Order");
        System.out.println("0. Exit");
        System.out.println("---------------------------------------------------------------");
    }

    private static void viewMenuList() {
        System.out.println("\n===============================================================");
        System.out.println("                        PINK PATISSIER                         ");
        System.out.println("===============================================================");
        if (menuList.isEmpty()) {
            System.out.println("Menu list is currently empty.");
            return;
        }
        DecimalFormat df = new DecimalFormat("#,##0.00");
        for (int i = 0; i < menuList.size(); i++) {
            Product item = menuList.get(i);
            System.out.printf("[%2d] %-42s | Price: Php %8s%n",
                    (i + 1), item.getProductName(), df.format(item.getPrice()));
        }
        System.out.println("---------------------------------------------------------------");
    }

    private static void searchMenuList() {
        System.out.println("\n--- SEARCH PINK PATISSIER MENU LIST ---");
        String keyword = readNonEmptyString("Enter item name to search: ").toLowerCase();
        DecimalFormat df = new DecimalFormat("#,##0.00");
        boolean found = false;

        for (int i = 0; i < menuList.size(); i++) {
            Product item = menuList.get(i);
            if (item.getProductName().toLowerCase().contains(keyword)) {
                System.out.printf("[%2d] %-42s | Price: Php %8s%n",
                        (i + 1), item.getProductName(), df.format(item.getPrice()));
                found = true;
            }
        }

        if (!found) {
            System.out.println("[Notice] No menu items matched \"" + keyword + "\".");
        }
    }

    private static void placeOrder() {
        System.out.println("\n--- PLACE NEW DELIVERY ORDER ---");

        System.out.println("Available Pickup Branches:");
        for (int i = 0; i < shopList.size(); i++) {
            System.out.print("[" + (i + 1) + "] ");
            Entity entity = shopList.get(i);
            entity.displaySummary();
        }
        int shopIdx = readInt("Select pickup branch (1-" + shopList.size() + "): ") - 1;
        if (shopIdx < 0 || shopIdx >= shopList.size()) {
            System.out.println("[Error] Invalid branch selection.");
            return;
        }
        Shop selectedShop = shopList.get(shopIdx);

        String customerName = readNonEmptyString("Enter customer name: ");
        String customerAddress = readNonEmptyString("Enter customer delivery address: ");
        String customerPhone = readNonEmptyString("Enter customer phone number: ");
        Customer customer = new Customer(customerName, customerAddress, customerPhone);

        String deliveryDate = readNonEmptyString("Enter delivery date (YYYY-MM-DD): ");
        String deliveryTime = readTimeWithAmPm("Enter delivery time (HH:mm AM/PM): ");

        System.out.println("\nSelect Mode of Payment:");
        System.out.println("1. Cash on Delivery (COD)");
        System.out.println("2. GCash");
        System.out.println("3. Maya");
        int payMode = readInt("Choose mode (1-3): ");
        String modeOfPayment;
        switch (payMode) {
            case 2:
                modeOfPayment = "GCash";
                break;
            case 3:
                modeOfPayment = "Maya";
                break;
            default:
                modeOfPayment = "Cash on Delivery (COD)";
                break;
        }

        Transaction order = new DeliveryTransaction(selectedShop, customer, modeOfPayment);
        order.setDeliveryDateTime(deliveryDate, deliveryTime);

        boolean adding = true;
        while (adding) {
            viewMenuList();
            int itemIdx = readInt("Select menu item number (1-" + menuList.size() + "): ") - 1;
            if (itemIdx < 0 || itemIdx >= menuList.size()) {
                System.out.println("[Error] Invalid menu item number.");
            } else {
                Product chosen = menuList.get(itemIdx);
                int qty = readPositiveInt("Enter quantity: ");
                order.addItem(new Product(chosen.getProductName(), chosen.getPrice(), qty));
                System.out.println("[Added] " + qty + "x " + chosen.getProductName());
            }

            String more = readNonEmptyString("Add another item? (Y/N): ");
            if (!more.equalsIgnoreCase("Y")) {
                adding = false;
            }
        }

        if (order.getItems().isEmpty()) {
            System.out.println("[Notice] No items selected. Order cancelled.");
            return;
        }

        double total = order.computeTotal();
        DecimalFormat df = new DecimalFormat("#,##0.00");
        System.out.println("\nTotal Amount Due (incl. Php 55.00 delivery fee): Php " + df.format(total));

        double payment = readPositiveDouble("Enter payment amount (Php): ");
        while (payment < total) {
            System.out.println("[Error] Payment must be at least Php " + df.format(total));
            payment = readPositiveDouble("Enter payment amount (Php): ");
        }

        order.setPayment(payment);
        orderList.add(order);
        order.displayReceipt();
    }

    private static void viewOrders() {
        System.out.println("\n--- VIEW DELIVERY ORDERS ---");
        if (orderList.isEmpty()) {
            System.out.println("No orders found.");
            return;
        }

        for (int i = 0; i < orderList.size(); i++) {
            System.out.print("Order #" + (i + 1) + " -> ");
            orderList.get(i).displaySummary();
        }

        int orderIdx = readInt("\nEnter Order # to view full receipt (or 0 to go back): ") - 1;
        if (orderIdx == -1) {
            return;
        }
        if (orderIdx < 0 || orderIdx >= orderList.size()) {
            System.out.println("[Error] Invalid order number.");
            return;
        }

        orderList.get(orderIdx).displayReceipt();
    }

    private static void updateOrder() {
        System.out.println("\n--- UPDATE DELIVERY ORDER ---");
        if (orderList.isEmpty()) {
            System.out.println("No orders available to update.");
            return;
        }

        for (int i = 0; i < orderList.size(); i++) {
            System.out.print("Order #" + (i + 1) + " -> ");
            orderList.get(i).displaySummary();
        }

        int orderIdx = readInt("\nSelect Order # to update (1-" + orderList.size() + "): ") - 1;
        if (orderIdx < 0 || orderIdx >= orderList.size()) {
            System.out.println("[Error] Invalid order number.");
            return;
        }

        Transaction targetOrder = orderList.get(orderIdx);
        System.out.println("\nWhat would you like to update?");
        System.out.println("1. Add Another Menu Item to Order");
        System.out.println("2. Update Item Quantity in Order");
        System.out.println("3. Remove an Item from Order");
        System.out.println("4. Update Mode of Payment");
        int updateChoice = readInt("Choose option (1-4): ");

        switch (updateChoice) {
            case 1:
                viewMenuList();
                int itemIdx = readInt("Select menu item number (1-" + menuList.size() + "): ") - 1;
                if (itemIdx < 0 || itemIdx >= menuList.size()) {
                    System.out.println("[Error] Invalid menu item number.");
                    return;
                }
                Product chosen = menuList.get(itemIdx);
                int qty = readPositiveInt("Enter quantity: ");
                targetOrder.addItem(new Product(chosen.getProductName(), chosen.getPrice(), qty));
                break;

            case 2:
                ArrayList<Product> items = targetOrder.getItems();
                for (int i = 0; i < items.size(); i++) {
                    System.out.print("[" + (i + 1) + "] ");
                    items.get(i).displayProduct();
                }
                int editIdx = readInt("Select item number to change quantity (1-" + items.size() + "): ") - 1;
                if (editIdx < 0 || editIdx >= items.size()) {
                    System.out.println("[Error] Invalid item number.");
                    return;
                }
                int newQty = readPositiveInt("Enter new quantity: ");
                items.get(editIdx).setQuantity(newQty);
                break;

            case 3:
                ArrayList<Product> currentItems = targetOrder.getItems();
                if (currentItems.size() <= 1) {
                    System.out.println("[Error] An order must have at least 1 item. Use 'Delete Order' to cancel the entire order.");
                    return;
                }
                for (int i = 0; i < currentItems.size(); i++) {
                    System.out.print("[" + (i + 1) + "] ");
                    currentItems.get(i).displayProduct();
                }
                int removeIdx = readInt("Select item number to remove (1-" + currentItems.size() + "): ") - 1;
                if (removeIdx < 0 || removeIdx >= currentItems.size()) {
                    System.out.println("[Error] Invalid item number.");
                    return;
                }
                Product removedItem = currentItems.remove(removeIdx);
                System.out.println("[Removed] " + removedItem.getProductName());
                break;

            case 4:
                System.out.println("1. Cash on Delivery (COD) | 2. GCash | 3. Maya");
                int payChoice = readInt("Select new mode of payment (1-3): ");
                if (payChoice == 2) {
                    targetOrder.setModeOfPayment("GCash");
                } else if (payChoice == 3) {
                    targetOrder.setModeOfPayment("Maya");
                } else {
                    targetOrder.setModeOfPayment("Cash on Delivery (COD)");
                }
                break;

            default:
                System.out.println("[Error] Invalid update option.");
                return;
        }

        double updatedTotal = targetOrder.computeTotal();
        DecimalFormat df = new DecimalFormat("#,##0.00");
        System.out.println("\nUpdated Total Amount Due: Php " + df.format(updatedTotal));

        if (targetOrder.getPayment() < updatedTotal) {
            double newPayment = readPositiveDouble("Previous payment is insufficient. Enter new payment (Php): ");
            while (newPayment < updatedTotal) {
                System.out.println("[Error] Payment must be at least Php " + df.format(updatedTotal));
                newPayment = readPositiveDouble("Enter new payment (Php): ");
            }
            targetOrder.setPayment(newPayment);
        }

        System.out.println("[Success] Order updated!");
        targetOrder.displayReceipt();
    }

    private static void deleteOrder() {
        System.out.println("\n--- DELETE DELIVERY ORDER ---");
        if (orderList.isEmpty()) {
            System.out.println("No orders available to delete.");
            return;
        }

        for (int i = 0; i < orderList.size(); i++) {
            System.out.print("Order #" + (i + 1) + " -> ");
            orderList.get(i).displaySummary();
        }

        int orderIdx = readInt("\nSelect Order # to delete (1-" + orderList.size() + "): ") - 1;
        if (orderIdx < 0 || orderIdx >= orderList.size()) {
            System.out.println("[Error] Invalid order number.");
            return;
        }

        orderList.remove(orderIdx);
        System.out.println("[Success] Order #" + (orderIdx + 1) + " has been deleted.");
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (input.hasNextInt()) {
                int val = input.nextInt();
                input.nextLine();
                return val;
            } else {
                System.out.println("[Invalid Input] Please enter a valid integer.");
                input.nextLine();
            }
        }
    }

    private static int readPositiveInt(String prompt) {
        while (true) {
            int val = readInt(prompt);
            if (val > 0) return val;
            System.out.println("[Invalid Input] Value must be greater than zero.");
        }
    }

    private static double readPositiveDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (input.hasNextDouble()) {
                double val = input.nextDouble();
                input.nextLine();
                if (val > 0) return val;
                System.out.println("[Invalid Input] Amount must be greater than zero.");
            } else {
                System.out.println("[Invalid Input] Please enter a valid numeric value.");
                input.nextLine();
            }
        }
    }

    private static String readTimeWithAmPm(String prompt) {
        while (true) {
            String text = readNonEmptyString(prompt);
            String normalized = text.trim().replaceAll("\\s+", " ");
            if (normalized.matches("(?i)^(0?[1-9]|1[0-2]):[0-5][0-9]\\s*(am|pm)$")) {
                String[] parts = normalized.split("\\s+");
                String time = parts[0];
                String meridiem = parts.length > 1 ? parts[1].toUpperCase() : "AM";
                return time + " " + meridiem;
            }
            System.out.println("[Invalid Input] Use format like 9:30 am or 09:30 PM.");
        }
    }

    private static String readNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String text = input.nextLine().trim();
            if (!text.isEmpty()) return text;
            System.out.println("[Invalid Input] Input cannot be blank.");
        }
    }
}
