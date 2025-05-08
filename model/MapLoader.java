package model;

import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
                String cityName = line;
                if (!cities.containsKey(cityName)) { // Prevent duplicates during load
                    cities.put(cityName, new City(cityName));
                }
            } else if (mode.equals("FLOODED")) {
                String cityName = line;
                if (cities.containsKey(cityName)) {
                    cities.get(cityName).setFlooded(true);
                }
            } else if (mode.equals("ROADS")) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String fromName = parts[0];
                    String toName = parts[1];
                    try {
                        int dist = Integer.parseInt(parts[2].trim());
                        if (cities.containsKey(fromName) && cities.containsKey(toName)) {
                            map.addRoad(cities.get(fromName), cities.get(toName), dist);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing distance: " + parts[2]);
                    }
                }
            }
        }
        br.close();
    }

    public static void saveMap(String filePath, Map<String, City> cities, Mapgraph map) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            // Save cities
            writer.println("CITIES:");
            for (City city : cities.values()) {
                writer.println(city.getName());
            }
            writer.println();

            // Save flooded cities
            writer.println("FLOODED:");
            for (City city : cities.values()) {
                if (city.isFlooded()) {
                    writer.println(city.getName());
                }
            }
            writer.println();

            // Save roads
            writer.println("ROADS:");
            Map<City, Map<City, Integer>> adjacencyList = map.getAdjacencyList();
            if (adjacencyList != null) {
                Set<String> savedRoads = new HashSet<>(); // To avoid duplicate entries
                for (Map.Entry<City, Map<City, Integer>> entry : adjacencyList.entrySet()) {
                    City fromCity = entry.getKey();
                    for (Map.Entry<City, Integer> neighborEntry : entry.getValue().entrySet()) {
                        City toCity = neighborEntry.getKey();
                        Integer distance = neighborEntry.getValue();
                        String roadKey1 = fromCity.getName() + "-" + toCity.getName();
                        String roadKey2 = toCity.getName() + "-" + fromCity.getName();

                        if (!savedRoads.contains(roadKey1) && !savedRoads.contains(roadKey2)) {
                            writer.println(fromCity.getName() + "," + toCity.getName() + "," + distance);
                            savedRoads.add(roadKey1);
                            savedRoads.add(roadKey2);
                        }
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Error saving map data: " + e.getMessage());
        }
    }
}