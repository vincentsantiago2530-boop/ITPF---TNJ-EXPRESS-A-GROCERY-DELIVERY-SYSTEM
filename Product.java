import java.text.DecimalFormat;

public class Product implements Printable {
    private String productName;
    private double price;
    private int quantity;

    public Product(String productName, double price, int quantity) {
        this.productName = productName;
        setPrice(price);
        setQuantity(quantity);
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        if (productName != null && !productName.trim().isEmpty()) {
            this.productName = productName;
        }
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price >= 0) {
            this.price = price;
        }
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity >= 0) {
            this.quantity = quantity;
        }
    }

    public double computeSubtotal() {
        return price * quantity;
    }

    public void displayProduct() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        System.out.printf("%-25s | Price: Php %-10s | Stock/Qty: %d%n",
                productName, df.format(price), quantity);
    }

    @Override
    public void printSummary() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        System.out.printf("%-25s x%-4d = Php %s%n",
                productName, quantity, df.format(computeSubtotal()));
    }
}
