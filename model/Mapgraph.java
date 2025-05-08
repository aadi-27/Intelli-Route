package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mapgraph {

    private Map<City, Map<City, Integer>> adjacencyList;

    public Mapgraph() {
        this.adjacencyList = new HashMap<>();
    }

    public void addRoad(City from, City to, int distance) {
        if (!adjacencyList.containsKey(from)) {
            adjacencyList.put(from, new HashMap<>());
        }
        adjacencyList.get(from).put(to, distance);

        // Since roads are bidirectional, add the reverse direction as well
        if (!adjacencyList.containsKey(to)) {
            adjacencyList.put(to, new HashMap<>());
        }
        adjacencyList.get(to).put(from, distance);
    }

    public Map<City, Map<City, Integer>> getAdjacencyList() {
        return adjacencyList;
    }

    // Example method to print the roads
    public void printRoads() {
        for (Map.Entry<City, Map<City, Integer>> entry : adjacencyList.entrySet()) {
            City fromCity = entry.getKey();
            System.out.print(fromCity.getName() + ": ");
            for (Map.Entry<City, Integer> neighborEntry : entry.getValue().entrySet()) {
                City toCity = neighborEntry.getKey();
                Integer distance = neighborEntry.getValue(); // Changed to Integer
                System.out.print("(" + toCity.getName() + ", " + distance + ") ");
            }
            System.out.println();
        }
    }

    // Method to get shortest path (Dijkstra's algorithm)
     public List<City> getShortestPath(City source, City destination) {
        // Implementation of Dijkstra's algorithm here...
        // (You would have this in your Mapgraph class)
        return null; // Placeholder
    }
}