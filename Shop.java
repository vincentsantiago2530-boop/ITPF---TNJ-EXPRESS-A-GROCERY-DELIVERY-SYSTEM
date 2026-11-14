
public class Shop extends Entity {
    private String shopName;
    private String shopAddress;

    public Shop(String shopName, String shopAddress) {
        setShopName(shopName);
        setShopAddress(shopAddress);
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        if (shopName != null && !shopName.trim().isEmpty()) {
            this.shopName = shopName.trim();
        } else {
            this.shopName = "Unknown Shop";
        }
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        if (shopAddress != null && !shopAddress.trim().isEmpty()) {
            this.shopAddress = shopAddress.trim();
        } else {
            this.shopAddress = "No Address";
        }
    }

    public void displayShop() {
        System.out.println("Shop Name    : " + shopName);
        displayAddress();
    }

    public void displayAddress() {
        System.out.println("Shop Address : " + shopAddress);
    }

    @Override
    public void displayDetails() {
        displayShop();
    }

    @Override
    public void displaySummary() {
        System.out.println(shopName + " - " + shopAddress);
    }
}
