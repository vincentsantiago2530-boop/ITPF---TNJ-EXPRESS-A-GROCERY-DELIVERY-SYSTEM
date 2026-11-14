import java.text.DecimalFormat;

public class Product extends Entity {
    private String productName;
    private double price;
    private int quantity;

    public Product(String productName, double price, int quantity) {
        setProductName(productName);
        setPrice(price);
        setQuantity(quantity);
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        if (productName != null && !productName.trim().isEmpty()) {
            this.productName = productName.trim();
        } else {
            this.productName = "Unnamed Item";
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
        System.out.printf("%-40s | Price: Php %8s | Qty: %-3d | Subtotal: Php %9s%n",
                productName, df.format(price), quantity, df.format(computeSubtotal()));
    }

    @Override
    public void displayDetails() {
        displayProduct();
    }

    @Override
    public void displaySummary() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        System.out.println(productName + " (x" + quantity + ") - Php " + df.format(computeSubtotal()));
    }
}
