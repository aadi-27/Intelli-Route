package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MapLoader {

    public static void loadMap(String filePath, Map<String, City> cities, Mapgraph map) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        String mode = "";

        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;

            if (line.endsWith(":")) {
                mode = line.replace(":", "").toUpperCase();
            } else if (mode.equals("CITIES")) {
                City city = new City(line);
                cities.put(line, city);
                //System.out.println("Loaded city: " + line);
            } else if (mode.equals("FLOODED")) {
                City city = cities.get(line);
                if (city != null) {
                    city.setFlooded(true);
                    //System.out.println("City " + line + " is flooded.");
                }
            } else if (mode.equals("ROADS")) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    City from = cities.get(parts[0]);
                    City to = cities.get(parts[1]);
                    int dist = Integer.parseInt(parts[2].trim()); // Trim whitespace from distance
                    if (from != null && to != null) {
                        map.addRoad(from, to, dist);
                        //System.out.println("Road added: " + parts[0] + " -> " + parts[1] + ", Distance: " + dist);
                    }
                }
            }
        }
        br.close();
    }

    public static void main(String[] args) {
        // Example usage:
        Map<String, City> cities = new HashMap<>();
        Mapgraph map = new Mapgraph(); // Assuming you have a Mapgraph class

        try {
            // Replace "src/main/resources/map.txt" with the actual path to your map file
            loadMap("src/main/resources/map.txt", cities, map);

            // Now you can work with the loaded cities and the map
            System.out.println("Loaded cities:");
            for (City city : cities.values()) {
                System.out.println(city.getName() + (city.isFlooded() ? " (Flooded)" : ""));
            }

            System.out.println("\nMap Roads:");
            // Assuming Mapgraph has a method to get all roads or print them
            map.printRoads(); // Now this will call the printRoads() method in Mapgraph

        } catch (IOException e) {
            System.err.println("Error loading map: " + e.getMessage());
        }
    }
}