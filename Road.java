public class Road {
    private City source;
    private City destination;
    private int distance; // in kilometers
    private int trafficLevel; // 1 (low traffic) - 10 (heavy traffic)
    private boolean isBlocked; 
    private boolean isFlooded;

    public Road(City source, City destination, int distance) {
        this.source = source;
        this.destination = destination;
        this.distance = distance;
        this.trafficLevel = 1; // default normal traffic
        this.isBlocked = false;
        this.isFlooded = false;
    }

    // Getters and setters
}
