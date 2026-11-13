public class Shop extends Entity {
    private String shopName;
    private String shopAddress;

    public Shop(String shopName, String shopAddress) {
        super(shopAddress);
        this.shopName = shopName;
        this.shopAddress = shopAddress;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        if (shopName != null && !shopName.trim().isEmpty()) {
            this.shopName = shopName;
        }
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        if (shopAddress != null && !shopAddress.trim().isEmpty()) {
            this.shopAddress = shopAddress;
            super.setAddress(shopAddress);
        }
    }

    public void displayShop() {
        System.out.println("===================================================");
        System.out.println("Shop Name: " + shopName);
        displayAddress();
        System.out.println("===================================================");
    }

    @Override
    public void displayAddress() {
        System.out.println("Shop Address: " + shopAddress);
    }

    @Override
    public void displayDetails() {
        displayShop();
    }
}
