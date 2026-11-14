public class Customer extends Entity {
    private String customerName;
    private String customerAddress;
    private String phoneNumber;

    public Customer(String customerName, String customerAddress) {
        this(customerName, customerAddress, "N/A");
    }

    public Customer(String customerName, String customerAddress, String phoneNumber) {
        setCustomerName(customerName);
        setCustomerAddress(customerAddress);
        setPhoneNumber(phoneNumber);
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        if (customerName != null && !customerName.trim().isEmpty()) {
            this.customerName = customerName.trim();
        } else {
            this.customerName = "Walk-in Customer";
        }
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        if (customerAddress != null && !customerAddress.trim().isEmpty()) {
            this.customerAddress = customerAddress.trim();
        } else {
            this.customerAddress = "No Address";
        }
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber != null && !phoneNumber.trim().isEmpty()) {
            this.phoneNumber = phoneNumber.trim();
        } else {
            this.phoneNumber = "N/A";
        }
    }

    public void displayCustomer() {
        System.out.println("Customer Name    : " + customerName);
        System.out.println("Phone Number    : " + phoneNumber);
        displayAddress();
    }

    public void displayAddress() {
        System.out.println("Customer Address : " + customerAddress);
    }

    @Override
    public void displayDetails() {
        displayCustomer();
    }

    @Override
    public void displaySummary() {
        System.out.println(customerName + " - " + customerAddress + " - " + phoneNumber);
    }
}
