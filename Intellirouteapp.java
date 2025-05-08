import java.util.*;
import java.io.*;
import model.City;
import model.Mapgraph;
import model.MapLoader;
import model.Package;
import controller.*;

public class Intellirouteapp {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Map<String, City> cities = new HashMap<>();
        Mapgraph map = new Mapgraph();
        AdminAuthenticator authenticator = new AdminAuthenticator(); // Create an instance of AdminAuthenticator

        try {
            MapLoader.loadMap("map.txt", cities, map);
        } catch (IOException e) {
            System.out.println("Error loading map: " + e.getMessage());
            return;
        }

        // Implement Admin Login
        if (getAdminCredentialsAndAuthenticate(sc, authenticator)) {
            handleAdminOperations(sc, cities, map);
        } else {
            // Proceed as a regular user
            performRouteCalculation(sc, cities, map);
        }

        System.out.println("✅ Application completed.");
        sc.close();
    }

    // Method to get admin credentials and authenticate using AdminAuthenticator
    private static boolean getAdminCredentialsAndAuthenticate(Scanner sc, AdminAuthenticator authenticator) {
        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        System.out.print("Enter your email ID: ");
        String email = sc.nextLine();

        if (authenticator.isAdmin(name, email)) {
            System.out.println("✅ Admin access granted.");
            return true;
        } else {
            System.out.println("❌ Admin access denied.");
            return false;
        }
    }

    // Method to handle admin operations
    private static void handleAdminOperations(Scanner sc, Map<String, City> cities, Mapgraph map) {
        int choice;
        do {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Add City");
            System.out.println("2. Add Route");
            System.out.println("3. Proceed as Regular User");
            System.out.println("0. Exit Admin Mode");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addCity(sc, cities, map); // Pass the map object as well
                    break;
                case 2:
                    addRoute(sc, cities, map);
                    break;
                case 3:
                    performRouteCalculation(sc, cities, map);
                    return; // Exit admin mode and proceed
                case 0:
                    System.out.println("🚪 Exiting Admin Mode.");
                    break;
                default:
                    System.out.println("⚠️ Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    // Method to add a new city and save the map
    private static void addCity(Scanner sc, Map<String, City> cities, Mapgraph map) { // Added Mapgraph map
        System.out.println("\n--- Add New City ---");
        System.out.print("Enter the name of the new city: ");
        String cityName = sc.nextLine();
        if (!cities.containsKey(cityName)) {
            cities.put(cityName, new City(cityName));
            System.out.println("✅ City '" + cityName + "' added successfully.");
            try {
                MapLoader.saveMap("map.txt", cities, map); // Save the map after adding the city
                System.out.println("💾 Map data saved to map.txt");
            } catch (IOException e) {
                System.err.println("❌ Error saving map: " + e.getMessage());
            }
        } else {
            System.out.println("⚠️ City '" + cityName + "' already exists.");
        }
    }

    // Method to add a new route
    private static void addRoute(Scanner sc, Map<String, City> cities, Mapgraph map) {
        System.out.println("\n--- Add New Route ---");
        System.out.print("Enter the source city name: ");
        String sourceName = sc.nextLine();
        if (!cities.containsKey(sourceName)) {
            System.out.println("⚠️ Source city '" + sourceName + "' not found.");
            return;
        }
        System.out.print("Enter the destination city name: ");
        String destName = sc.nextLine();
        if (!cities.containsKey(destName)) {
            System.out.println("⚠️ Destination city '" + destName + "' not found.");
            return;
        }
        System.out.print("Enter the distance between " + sourceName + " and " + destName + ": ");
        double distance = sc.nextDouble();
        sc.nextLine(); // Consume newline

        City sourceCity = cities.get(sourceName);
        City destCity = cities.get(destName);

        map.addRoad(sourceCity, destCity, (int) distance); // Assuming addRoad in Mapgraph handles bidirectional routes
        System.out.println("✅ Route added between '" + sourceName + "' and '" + destName + "' with distance " + distance + ".");
        try {
            MapLoader.saveMap("map.txt", cities, map); // Save the map after adding the route
            System.out.println("💾 Map data saved to map.txt");
        } catch (IOException e) {
            System.err.println("❌ Error saving map: " + e.getMessage());
        }
    }

    // Method to perform regular route calculation (your original main logic)
    private static void performRouteCalculation(Scanner sc, Map<String, City> cities, Mapgraph map) {
        System.out.println("\n--- Route Calculation ---");
        System.out.print("Enter vehicle type (bike/truck/van): ");
        String vehicleType = sc.nextLine();

        System.out.print("Is the package fragile? (true/false): ");
        boolean fragile = sc.nextBoolean();
        sc.nextLine(); // Consume newline
        Package pkg = new Package(fragile);

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