public abstract class Entity {
    protected String address;

    public Entity(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (address != null && !address.trim().isEmpty()) {
            this.address = address;
        }
    }

    public abstract void displayAddress();
    public abstract void displayDetails();
}
