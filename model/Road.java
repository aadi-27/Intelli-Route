package model;

public class Road {
    private City from;
    private City to;
    private int distance;
    private boolean underConstruction;
    private boolean highTraffic;
    private boolean isBumpy;

    public Road(City from, City to, int distance) {
        this.from = from;
        this.to = to;
        this.distance = distance;
        this.underConstruction = false;
        this.highTraffic = false;
        this.isBumpy = false;
    }

    // Overloaded constructor for advanced settings
    public Road(City from, City to, int distance, boolean underConstruction, boolean highTraffic, boolean isBumpy) {
        this.from = from;
        this.to = to;
        this.distance = distance;
        this.underConstruction = underConstruction;
        this.highTraffic = highTraffic;
        this.isBumpy = isBumpy;
    }

    public City getFrom() {
        return from;
    }

    public City getTo() {
        return to;
    }

    public double getDistance() {
        return distance;
    }

    public boolean isUnderConstruction() {
        return underConstruction;
    }

    public boolean isHighTraffic() {
        return highTraffic;
    }

    public boolean isBumpy() {
        return isBumpy;
    }

    public void setUnderConstruction(boolean underConstruction) {
        this.underConstruction = underConstruction;
    }

    public void setHighTraffic(boolean highTraffic) {
        this.highTraffic = highTraffic;
    }

    public void setBumpy(boolean bumpy) {
        this.isBumpy = bumpy;
    }
}
