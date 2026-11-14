import java.text.DecimalFormat;

public class DeliveryTransaction extends Transaction {
    private static final double DELIVERY_FEE = 55.00;

    public DeliveryTransaction(Shop shop, Customer customer, String modeOfPayment) {
        super(shop, customer, modeOfPayment);
    }

    @Override
    public double computeTotal() {
        double itemsTotal = super.computeTotal();
        double grandTotal = itemsTotal + DELIVERY_FEE;
        setTotalAmount(grandTotal);
        return grandTotal;
    }

    @Override
    public void displayReceipt() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        double change = getPayment() - getTotalAmount();

        System.out.println("\n=======================================================");
        System.out.println("            TNJ EXPRESS DELIVERY RECEIPT               ");
        System.out.println("=======================================================");
        shop.displayShop();
        System.out.println("-------------------------------------------------------");
        customer.displayCustomer();
        System.out.println("-------------------------------------------------------");
        System.out.println("Delivery Date    : " + getDeliveryDate());
        System.out.println("Delivery Time    : " + getDeliveryTime());
        System.out.println("ORDERED ITEMS:");
        for (Product item : getItems()) {
            item.displayProduct();
        }
        System.out.println("-------------------------------------------------------");
        System.out.println("Items Subtotal   : Php " + df.format(super.computeTotal()));
        System.out.println("Delivery Fee     : Php " + df.format(DELIVERY_FEE));
        System.out.println("Total Amount     : Php " + df.format(getTotalAmount()));
        System.out.println("Mode of Payment  : " + getModeOfPayment());
        System.out.println("Payment          : Php " + df.format(getPayment()));
        System.out.println("Change           : Php " + df.format(change));
        System.out.println("=======================================================\n");
    }
}
