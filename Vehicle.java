public class Vehicle {
    private String type;
    private boolean isElectric;

    public Vehicle(String type, boolean isElectric) {
        this.type = type;
        this.isElectric = isElectric;
    }

    public String getType() {
        return type;
    }

    public boolean isElectric() {
        return isElectric;
    }
}
