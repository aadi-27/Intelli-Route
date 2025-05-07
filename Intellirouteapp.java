import java.util.*;
import java.io.*;
import model.User;
import model.City;
import model.Mapgraph;
import model.MapLoader;
import model.Package;


public class Intellirouteapp {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Step 1: Load data from file
        Map<String, City> cities = new HashMap<>(); // Corrected line
        Mapgraph map = new Mapgraph();

        try {
            MapLoader.loadMap("map.txt", cities, map); // Load cities, flooded cities, and roads from file
        } catch (IOException e) {
            System.out.println(" Error loading map: " + e.getMessage());
            return;
        }

        // Step 2: Input Vehicle Informationf
        System.out.print("Enter vehicle type (bike/truck/van): ");
        String vehicleType = sc.nextLine();

        // Step 3: Input Package Information
        System.out.print("Is the package fragile? (true/false): ");
        boolean fragile = sc.nextBoolean();
        sc.nextLine(); // Consume newline
        Package pkg = new Package(fragile);

        // Step 4: Choose Start and End Cities
        System.out.print("Enter source city: ");
        String sourceName = sc.nextLine();
        if (!cities.containsKey(sourceName)) {
            System.out.println("Source city not found.");
            return;
        }

        System.out.print("Enter destination city: ");
        String destName = sc.nextLine();
        if (!cities.containsKey(destName)) {
            System.out.println("Destination city not found.");
            return;
        }


        City source = cities.get(sourceName);
        City destination = cities.get(destName);

        System.out.println("🔍 Calculating best route...");

        if (source != null && destination != null) {
            List<City> path = map.getShortestPath(source, destination);

            if (path != null && path.size() > 1) {
                System.out.println("✅ Best route:");
                for (int i = 0; i < path.size(); i++) {
                    System.out.print(path.get(i).getName());
                    if (i != path.size() - 1) System.out.print(" -> ");
                }
                System.out.println("\n📍 Total stops: " + (path.size() - 1));

                // Optionally warn about flooded cities along path
                for (City city : path) {
                    if (city.isFlooded()) {
                        System.out.println("⚠️ Warning: " + city.getName() + " is flooded. Proceed with caution.");
                    }
                }

                if (pkg.isFragile()) {
                    System.out.println("📦 Fragile package: Handle with care throughout the route.");
                }

            } else {
                System.out.println("❌ No valid route found.");
            }
        } else {
            System.out.println("❌ Invalid source or destination.");
        }

        System.out.println("✅ Route calculation completed.");
    }
}