import java.util.*;
import java.io.*;
import model.City;
import model.Mapgraph;
import model.MapLoader;
import model.Package;
import controller.*;

public class Intellirouteapp {

    private static final int CONSOLE_WIDTH = 80;

    private static void printCentered(String text, char borderChar) {
        int padding = (CONSOLE_WIDTH - text.length()) / 2;
        System.out.println(String.valueOf(borderChar).repeat(padding) + text + String.valueOf(borderChar).repeat(padding + (text.length() % 2)));
    }

    private static void printSectionTitle(String title) {
        int padding = (CONSOLE_WIDTH - title.length()) / 2;
        System.out.println("\n" + "-".repeat(padding) + " " + title + " " + "-".repeat(padding + (title.length() % 2)));
    }

    public static void printLogo() {
        int height = 7;
    
        for (int i = 0; i < height; i++) {
    
            // I
            for (int j = 0; j < 5; j++) {
                if (i == 0 || i == height - 1) System.out.print("*");
                else if (j == 2) System.out.print("*");
                else System.out.print(" ");
            }
            System.out.print(" ");
    
            // N
            for (int j = 0; j < 5; j++) {
                if (j == 0 || j == 4 || i == j) System.out.print("*");
                else System.out.print(" ");
            }
            System.out.print(" ");
    
            // T
            for (int j = 0; j < 5; j++) {
                if (i == 0) System.out.print("*");
                else if (j == 2) System.out.print("*");
                else System.out.print(" ");
            }
            System.out.print(" ");
    
            // E
            for (int j = 0; j < 5; j++) {
                if (i == 0 || i == height - 1 || i == height / 2 || j == 0) System.out.print("*");
                else System.out.print(" ");
            }
            System.out.print(" ");
    
            // L
            for (int j = 0; j < 5; j++) {
                if (j == 0 || i == height - 1) System.out.print("*");
                else System.out.print(" ");
            }
            System.out.print(" ");
    
            // L
            for (int j = 0; j < 5; j++) {
                if (j == 0 || i == height - 1) System.out.print("*");
                else System.out.print(" ");
            }
            System.out.print(" ");
    
            // I
            for (int j = 0; j < 5; j++) {
                if (i == 0 || i == height - 1) System.out.print("*");
                else if (j == 2) System.out.print("*");
                else System.out.print(" ");
            }
    
            System.out.print("   "); // space between INTELLI and ROUTE
    
            // R
            for (int j = 0; j < 5; j++) {
                if (j == 0 || (i == 0 && j < 4) || (i == height / 2 && j < 4) || (j == 4 && i > 0 && i < height / 2)) {
                    System.out.print("*");
                } else if (i > height / 2 && i - height / 2 == j) {
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.print(" ");
    
            // O
            for (int j = 0; j < 5; j++) {
                if ((i == 0 || i == height - 1) && j > 0 && j < 4) System.out.print("*");
                else if ((j == 0 || j == 4) && i > 0 && i < height - 1) System.out.print("*");
                else System.out.print(" ");
            }
            System.out.print(" ");
    
            // U
            for (int j = 0; j < 5; j++) {
                if ((j == 0 || j == 4) && i != height - 1) System.out.print("*");
                else if (i == height - 1 && j > 0 && j < 4) System.out.print("*");
                else System.out.print(" ");
            }
            System.out.print(" ");
    
            // T
            for (int j = 0; j < 5; j++) {
                if (i == 0) System.out.print("*");
                else if (j == 2) System.out.print("*");
                else System.out.print(" ");
            }
            System.out.print(" ");
    
            // E
            for (int j = 0; j < 5; j++) {
                if (i == 0 || i == height - 1 || i == height / 2 || j == 0) System.out.print("*");
                else System.out.print(" ");
            }
    
            System.out.println();
        }
    }
    
    

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Map<String, City> cities = new HashMap<>();
        Mapgraph map = new Mapgraph();
        AdminAuthenticator authenticator = new AdminAuthenticator();

        printLogo();

        try {
            MapLoader.loadMap("map.txt", cities, map);
            // System.out.println("✅ Map data loaded successfully.");
        } catch (IOException e) {
            System.out.println("❌ Error loading map: " + e.getMessage());
            return;
        }

        if (getAdminCredentialsAndAuthenticate(sc, authenticator)) {
            handleAdminOperations(sc, cities, map);
        } else {
            performRouteCalculation(sc, cities, map);
        }

        System.out.println();
        printCentered("Application Completed", '=');
        System.out.println("Thank you for using IntelliRoute!");
        printCentered("", '=');
        sc.close();
    }

    private static boolean getAdminCredentialsAndAuthenticate(Scanner sc, AdminAuthenticator authenticator) {
        printSectionTitle(" Login");
        System.out.print("Enter your name: ");
        String name = sc.nextLine().trim();
        System.out.print("Enter your email ID: ");
        String email = sc.nextLine().trim();

        if (authenticator.isAdmin(name, email)) {
            System.out.println("✅ Admin access granted.");
            return true;
        } else {
            System.out.println("❌ Admin access denied.");
            return false;
        }
    }

    private static void handleAdminOperations(Scanner sc, Map<String, City> cities, Mapgraph map) {
        int choice;
        do {
            printSectionTitle("Admin Menu");
            System.out.println("1. Add City");
            System.out.println("2. Add Route");
            System.out.println("3. Proceed as Regular User");
            System.out.println("0. Exit Admin Mode");
            System.out.print("Enter your choice: ");
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                        addCity(sc, cities, map);
                        break;
                    case 2:
                        addRoute(sc, cities, map);
                        break;
                    case 3:
                        performRouteCalculation(sc, cities, map);
                        return;
                    case 0:
                        System.out.println("🚪 Exiting Admin Mode.");
                        break;
                    default:
                        System.out.println("⚠️ Invalid choice. Please try again.");
                }
            } else {
                System.out.println("⚠️ Invalid input. Please enter a number.");
                sc.nextLine();
                choice = -1;
            }
        } while (choice != 0);
    }

    private static void addCity(Scanner sc, Map<String, City> cities, Mapgraph map) {
        printSectionTitle("Add New City");
        System.out.print("Enter the name of the new city: ");
        String cityName = sc.nextLine().trim();
        if (!cities.containsKey(cityName)) {
            cities.put(cityName, new City(cityName));
            System.out.println("✅ City '" + cityName + "' added successfully.");
            try {
                MapLoader.saveMap("map.txt", cities, map);
                System.out.println("💾 Map data saved to map.txt");
            } catch (IOException e) {
                System.err.println("❌ Error saving map: " + e.getMessage());
            }
        } else {
            System.out.println("⚠️ City '" + cityName + "' already exists.");
        }
    }

    private static void addRoute(Scanner sc, Map<String, City> cities, Mapgraph map) {
        printSectionTitle("Add New Route");
        System.out.print("Enter the source city name: ");
        String sourceName = sc.nextLine().trim();
        if (!cities.containsKey(sourceName)) {
            System.out.println("⚠️ Source city '" + sourceName + "' not found.");
            return;
        }
        System.out.print("Enter the destination city name: ");
        String destName = sc.nextLine().trim();
        if (!cities.containsKey(destName)) {
            System.out.println("⚠️ Destination city '" + destName + "' not found.");
            return;
        }
        System.out.print("Enter the distance between " + sourceName + " and " + destName + ": ");
        if (sc.hasNextDouble()) {
            double distance = sc.nextDouble();
            sc.nextLine();
            City sourceCity = cities.get(sourceName);
            City destCity = cities.get(destName);
            map.addRoad(sourceCity, destCity, (int) distance);
            System.out.println("✅ Route added between '" + sourceName + "' and '" + destName + "' with distance " + distance + ".");
            try {
                MapLoader.saveMap("map.txt", cities, map);
                System.out.println("💾 Map data saved to map.txt");
            } catch (IOException e) {
                System.err.println("❌ Error saving map: " + e.getMessage());
            }
        } else {
            System.out.println("⚠️ Invalid distance entered.");
            sc.nextLine();
        }
    }

    private static void performRouteCalculation(Scanner sc, Map<String, City> cities, Mapgraph map) {
        printCentered("Route Calculation", '=');
        System.out.println();

        System.out.print("Enter vehicle type (bike/truck/van): ");
        String vehicleType = sc.nextLine().trim().toLowerCase();
        System.out.printf("%-20s: %s\n", "Vehicle Type", vehicleType.substring(0, 1).toUpperCase() + vehicleType.substring(1));

        System.out.print("Is the package fragile? (true/false): ");
        boolean fragile = sc.nextBoolean();
        sc.nextLine();
        System.out.printf("%-20s: %s\n", "Fragile Package", fragile ? "Yes" : "No");
        Package pkg = new Package(fragile);

        System.out.print("Enter source city: ");
        String sourceName = sc.nextLine().trim();
        if (!cities.containsKey(sourceName)) {
            System.out.println("⚠️ Error: Source city '" + sourceName + "' not found.");
            return;
        }
        System.out.printf("%-20s: %s\n", "Source City", sourceName);

        System.out.print("Enter destination city: ");
        String destName = sc.nextLine().trim();
        if (!cities.containsKey(destName)) {
            System.out.println("⚠️ Error: Destination city '" + destName + "' not found.");
            return;
        }
        System.out.printf("%-20s: %s\n", "Destination City", destName);

        printSectionTitle("All Possible Routes");
        List<List<City>> allPaths = map.getAllPaths(cities.get(sourceName), cities.get(destName));

        if (!allPaths.isEmpty()) {
            System.out.println("Found " + allPaths.size() + " possible routes:");
            int i = 1;
            for (List<City> path : allPaths) {
                System.out.printf("%2d. ", i++);
                for (int j = 0; j < path.size(); j++) {
                    System.out.print(path.get(j).getName());
                    if (j != path.size() - 1) System.out.print(" -> ");
                }
                System.out.println();
            }
        } else {
            System.out.println("❌ No routes found between " + sourceName + " and " + destName + ".");
            return;
        }

        printSectionTitle("Best Route (Dijkstra's)");

        City sourceCity = cities.get(sourceName);
        City destinationCity = cities.get(destName);

        if (sourceCity != null && destinationCity != null) {
            List<City> shortestPath = map.getShortestPath(sourceCity, destinationCity);

            if (shortestPath != null && shortestPath.size() > 1) {
                System.out.println("✅ The most convenient route is:");
                for (int i = 0; i < shortestPath.size(); i++) {
                    System.out.print(shortestPath.get(i).getName());
                    if (i != shortestPath.size() - 1) System.out.print(" -> ");
                }
                System.out.println("\n📍 Total stops: " + (shortestPath.size() - 1));

                for (City city : shortestPath) {
                    if (city.isFlooded()) {
                        System.out.println("⚠️ Warning: " + city.getName() + " is flooded. Proceed with caution in " + city.getName() + ".");
                    }
                }

                if (pkg.isFragile()) {
                    System.out.println("📦 Important: This package is fragile. Handle with care throughout the journey.");
                }

            } else {
                System.out.println("❌ No valid shortest route found between " + sourceName + " and " + destName + ".");
            }
        } else {
            System.out.println("❌ Invalid source or destination for shortest path calculation.");
        }

        System.out.println();
        printCentered("Route Calculation Completed", '=');
    }
}