public class City {
    private String name;
    private boolean isFlooded;
    private boolean hasAccident;

    public City(String name) {
        this.name = name;
        this.isFlooded = false;
        this.hasAccident = false;
    }

    public City(String name, boolean isFlooded, boolean hasAccident) {
        this.name = name;
        this.isFlooded = isFlooded;
        this.hasAccident = hasAccident;
    }

    public String getName() {
        return name;
    }

    public boolean isFlooded() {
        return isFlooded;
    }

    public void setFlooded(boolean flooded) {
        isFlooded = flooded;
    }

    public boolean hasAccident() {
        return hasAccident;
    }

    public void setAccident(boolean accident) {
        hasAccident = accident;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        City city = (City) obj;
        return name.equals(city.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
