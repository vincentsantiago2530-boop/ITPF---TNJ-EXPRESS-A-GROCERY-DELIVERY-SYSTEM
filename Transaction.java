import java.text.DecimalFormat;
import java.util.ArrayList;

public class Transaction implements Printable {
    protected Shop shop;
    protected Customer customer;
    protected ArrayList<Product> items;
    protected double totalAmount;
    protected double payment;
    protected String modeOfPayment;
    protected String deliveryDateTime;

    public Transaction(Shop shop, Customer customer, ArrayList<Product> items,
                       double payment, String modeOfPayment, String deliveryDateTime) {
        this.shop = shop;
        this.customer = customer;
        this.items = items;
        this.payment = payment;
        this.modeOfPayment = modeOfPayment;
        this.deliveryDateTime = deliveryDateTime;
        this.totalAmount = computeTotal();
    }

    public double getTotalAmount() {
        return totalAmount;
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
        this.modeOfPayment = modeOfPayment;
    }

    public String getDeliveryDateTime() {
        return deliveryDateTime;
    }

    public void setDeliveryDateTime(String deliveryDateTime) {
        this.deliveryDateTime = deliveryDateTime;
    }

    public double computeTotal() {
        double sum = 0;
        for (Product item : items) {
            sum += item.computeSubtotal();
        }
        this.totalAmount = sum;
        return totalAmount;
    }

    public double computeChange() {
        return payment - totalAmount;
    }

    public void displayReceipt() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        System.out.println("\n================ OFFICIAL RECEIPT =================");
        shop.displayShop();
        customer.displayCustomer();
        System.out.println("Pickup Date/Time: " + deliveryDateTime);
        System.out.println("Mode of Payment:  " + modeOfPayment);
        System.out.println("---------------------------------------------------");
        for (Product item : items) {
            item.printSummary();
        }
        System.out.println("---------------------------------------------------");
        System.out.println("Total Amount: Php " + df.format(totalAmount));
        System.out.println("Payment:      Php " + df.format(payment));
        System.out.println("Change:       Php " + df.format(computeChange()));
        System.out.println("===================================================\n");
    }

    @Override
    public void printSummary() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        System.out.println("Customer: " + customer.getCustomerName() +
                " | Total: Php " + df.format(totalAmount) +
                " | Payment: " + modeOfPayment +
                " | Schedule: " + deliveryDateTime);
    }
}
