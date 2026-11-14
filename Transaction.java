import java.text.DecimalFormat;
import java.util.ArrayList;

public class Transaction implements Printable {
    private double totalAmount;
    private double payment;
    private String modeOfPayment;
    private String deliveryDate;
    private String deliveryTime;

    protected Shop shop;
    protected Customer customer;
    protected ArrayList<Product> items;

    public Transaction(Shop shop, Customer customer, String modeOfPayment) {
        this.shop = shop;
        this.customer = customer;
        this.items = new ArrayList<>();
        setModeOfPayment(modeOfPayment);
        this.totalAmount = 0.0;
        this.payment = 0.0;
        this.deliveryDate = "Not set";
        this.deliveryTime = "Not set";
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    protected void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        if (payment >= 0) {
            this.payment = payment;
        }
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment) {
        if (modeOfPayment != null && !modeOfPayment.trim().isEmpty()) {
            this.modeOfPayment = modeOfPayment.trim();
        } else {
            this.modeOfPayment = "Cash on Delivery (COD)";
        }
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        if (deliveryDate != null && !deliveryDate.trim().isEmpty()) {
            this.deliveryDate = deliveryDate.trim();
        } else {
            this.deliveryDate = "Not set";
        }
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        if (deliveryTime != null && !deliveryTime.trim().isEmpty()) {
            this.deliveryTime = deliveryTime.trim();
        } else {
            this.deliveryTime = "Not set";
        }
    }

    public void setDeliveryDateTime(String deliveryDate, String deliveryTime) {
        setDeliveryDate(deliveryDate);
        setDeliveryTime(deliveryTime);
    }

    public void addItem(Product item) {
        items.add(item);
        computeTotal();
    }

    public ArrayList<Product> getItems() {
        return items;
    }

    public double computeTotal() {
        double sum = 0.0;
        for (Product item : items) {
            sum += item.computeSubtotal();
        }
        this.totalAmount = sum;
        return this.totalAmount;
    }

    public void displayReceipt() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        double change = payment - totalAmount;

        System.out.println("\n=======================================================");
        System.out.println("              TNJ EXPRESS - ORDER RECEIPT              ");
        System.out.println("=======================================================");
        shop.displayShop();
        System.out.println("-------------------------------------------------------");
        customer.displayCustomer();
        System.out.println("-------------------------------------------------------");
        System.out.println("Delivery Date    : " + deliveryDate);
        System.out.println("Delivery Time    : " + deliveryTime);
        System.out.println("ORDERED ITEMS:");
        for (Product item : items) {
            item.displayProduct();
        }
        System.out.println("-------------------------------------------------------");
        System.out.println("Total Amount     : Php " + df.format(totalAmount));
        System.out.println("Mode of Payment  : " + modeOfPayment);
        System.out.println("Payment          : Php " + df.format(payment));
        System.out.println("Change           : Php " + df.format(change));
        System.out.println("=======================================================\n");
    }

    @Override
    public void displaySummary() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        System.out.println("Pickup: " + shop.getShopName() + " | Recipient: " + customer.getCustomerName()
                + " | Total: Php " + df.format(totalAmount) + " | Payment: " + modeOfPayment);
    }
}
