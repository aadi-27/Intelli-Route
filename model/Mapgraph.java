package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

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

    // Method to find all paths between source and destination using DFS
    public List<List<City>> getAllPaths(City source, City destination) {
        List<List<City>> allPaths = new ArrayList<>();
        List<City> currentPath = new LinkedList<>();
        Set<City> visited = new HashSet<>();

        findAllPathsRecursive(source, destination, visited, currentPath, allPaths);
        return allPaths;
    }

    private void findAllPathsRecursive(City current, City destination, Set<City> visited, List<City> currentPath, List<List<City>> allPaths) {
        visited.add(current);
        currentPath.add(current);

        if (current.equals(destination)) {
            allPaths.add(new ArrayList<>(currentPath));
        } else {
            Map<City, Integer> neighbors = adjacencyList.get(current);
            if (neighbors != null) {
                for (City neighbor : neighbors.keySet()) {
                    if (!visited.contains(neighbor)) {
                        findAllPathsRecursive(neighbor, destination, visited, currentPath, allPaths);
                    }
                }
            }
        }

        currentPath.remove(currentPath.size() - 1); // Backtrack
        visited.remove(current); // Unmark for other paths
    }

    public List<City> getShortestPath(City source, City destination) {
        if (source == null || destination == null || !adjacencyList.containsKey(source) || !adjacencyList.containsKey(destination)) {
            return null; // Handle invalid input
        }

        Map<City, Integer> distances = new HashMap<>();
        Map<City, City> previousNodes = new HashMap<>();
        Set<City> visited = new HashSet<>();
        PriorityQueue<City> priorityQueue = new PriorityQueue<>((city1, city2) -> distances.get(city1) - distances.get(city2));

        // Initialize distances
        for (City city : adjacencyList.keySet()) {
            distances.put(city, Integer.MAX_VALUE);
        }
        distances.put(source, 0);
        priorityQueue.offer(source);

        while (!priorityQueue.isEmpty()) {
            City currentCity = priorityQueue.poll();

            if (currentCity.equals(destination)) {
                break; // Reached the destination
            }

            if (visited.contains(currentCity)) {
                continue;
            }
            visited.add(currentCity);

            Map<City, Integer> neighbors = adjacencyList.get(currentCity);
            if (neighbors != null) {
                for (Map.Entry<City, Integer> neighborEntry : neighbors.entrySet()) {
                    City neighbor = neighborEntry.getKey();
                    Integer distance = neighborEntry.getValue();
                    int newDistance = distances.get(currentCity) + distance;
                    if (newDistance < distances.get(neighbor)) {
                        distances.put(neighbor, newDistance);
                        previousNodes.put(neighbor, currentCity);
                        priorityQueue.offer(neighbor);
                    }
                }
            }
        }

        // Reconstruct the shortest path
        LinkedList<City> shortestPath = new LinkedList<>();
        City current = destination;
        while (current != null && previousNodes.containsKey(current)) {
            shortestPath.addFirst(current);
            current = previousNodes.get(current);
        }
        if (current == source) {
            shortestPath.addFirst(source);
            return shortestPath;
        }

        return null; // No path found
    }

    // Example method to print the roads (no changes needed here)
    public void printRoads() {
        for (Map.Entry<City, Map<City, Integer>> entry : adjacencyList.entrySet()) {
            City fromCity = entry.getKey();
            System.out.print(fromCity.getName() + ": ");
            for (Map.Entry<City, Integer> neighborEntry : entry.getValue().entrySet()) {
                City toCity = neighborEntry.getKey();
                Integer distance = neighborEntry.getValue();
                System.out.print("(" + toCity.getName() + ", " + distance + ") ");
            }
            System.out.println();
        }
    }
}