import java.text.DecimalFormat;
import java.util.ArrayList;

public class DeliveryTransaction extends Transaction {
    private double deliveryFee;

    public DeliveryTransaction(Shop shop, Customer customer, ArrayList<Product> items,
                               double payment, String modeOfPayment,
                               String deliveryDateTime, double deliveryFee) {
        super(shop, customer, items, payment, modeOfPayment, deliveryDateTime);
        this.deliveryFee = deliveryFee;
        this.totalAmount = computeTotal();
    }

    public double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(double deliveryFee) {
        if (deliveryFee >= 0) {
            this.deliveryFee = deliveryFee;
            this.totalAmount = computeTotal();
        }
    }

    @Override
    public double computeTotal() {
        double baseTotal = super.computeTotal();
        this.totalAmount = baseTotal + deliveryFee;
        return this.totalAmount;
    }

    @Override
    public void displayReceipt() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        System.out.println("\n============= GROCERY DELIVERY RECEIPT ============");
        shop.displayShop();
        customer.displayCustomer();
        System.out.println("Delivery Date/Time: " + deliveryDateTime);
        System.out.println("Mode of Payment:    " + modeOfPayment);
        System.out.println("---------------------------------------------------");
        for (Product item : items) {
            item.printSummary();
        }
        System.out.println("---------------------------------------------------");
        System.out.println("Delivery Fee: Php " + df.format(deliveryFee));
        System.out.println("Total Amount: Php " + df.format(totalAmount));
        System.out.println("Payment:      Php " + df.format(payment));
        System.out.println("Change:       Php " + df.format(computeChange()));
        System.out.println("===================================================\n");
    }
}
