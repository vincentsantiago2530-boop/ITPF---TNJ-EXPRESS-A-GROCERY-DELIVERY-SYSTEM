public class Customer extends Entity {
    private String customerName;
    private String customerAddress;
    private String phoneNumber;

    public Customer(String customerName, String customerAddress, String phoneNumber) {
        super(customerAddress);
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.phoneNumber = phoneNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        if (customerName != null && !customerName.trim().isEmpty()) {
            this.customerName = customerName;
        }
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        if (customerAddress != null && !customerAddress.trim().isEmpty()) {
            this.customerAddress = customerAddress;
            super.setAddress(customerAddress);
        }
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber != null && !phoneNumber.trim().isEmpty()) {
            this.phoneNumber = phoneNumber;
        }
    }

    public void displayCustomer() {
        System.out.println("Customer Name: " + customerName);
        displayAddress();
        System.out.println("Phone Number:  " + phoneNumber);
    }

    @Override
    public void displayAddress() {
        System.out.println("Delivery Address: " + customerAddress);
    }

    @Override
    public void displayDetails() {
        displayCustomer();
    }
}
