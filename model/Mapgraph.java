package model;

import java.util.*;

public class Mapgraph {
    private Map<City, List<Road>> adjList = new HashMap<>();

    public void addCity(City city) {
        adjList.putIfAbsent(city, new ArrayList<>());
    }

    public void addRoad(City from, City to, int distance) {
        addCity(from);
        addCity(to);
        Road road1 = new Road(from, to, distance);
        Road road2 = new Road(to, from, distance); // undirected
        adjList.get(from).add(road1);
        adjList.get(to).add(road2);
    }

    public List<City> getShortestPath(City start, City end) {
        Map<City, Integer> dist = new HashMap<>();
        Map<City, City> prev = new HashMap<>();
        PriorityQueue<City> pq = new PriorityQueue<>(Comparator.comparingInt(dist::get));

        for (City city : adjList.keySet()) {
            dist.put(city, Integer.MAX_VALUE);
        }
        dist.put(start, 0);
        pq.add(start);

        while (!pq.isEmpty()) {
            City current = pq.poll();

            if ((current.isFlooded() || current.hasAccident()) && !current.equals(start) && !current.equals(end)) {
                System.out.println("Skipping " + current.getName() + " due to flood or accident.");
                continue;
            }

            List<Road> roads = adjList.getOrDefault(current, new ArrayList<>());
            for (Road road : roads) {
                City neighbor = road.getTo();

                // Logging skip of neighbors
                if (neighbor.isFlooded() || neighbor.hasAccident()) {
                    System.out.println("Skipping " + neighbor.getName() + " due to flood or accident.");
                    continue;
                }

                // Logging road evaluation
                System.out.println("Evaluating road: " + current.getName() + " -> " + neighbor.getName() + ", Distance: " + road.getDistance());

                int newDist = dist.get(current) + road.getDistance();
                if (newDist < dist.get(neighbor)) {
                    dist.put(neighbor, newDist);
                    prev.put(neighbor, current);
                    pq.add(neighbor);
                }
            }
        }

        // Reconstruct path
        List<City> path = new ArrayList<>();
        City current = end;
        while (current != null) {
            path.add(0, current);
            current = prev.get(current);
        }

        if (path.size() > 1 && path.get(0).equals(start)) {
            return path;
        } else {
            return new ArrayList<>(); // No valid route found
        }
    }

    // Added method to print the roads
    public void printRoads() {
        System.out.println("All Roads in the Map:");
        for (Map.Entry<City, List<Road>> entry : adjList.entrySet()) {
            City city = entry.getKey();
            for (Road road : entry.getValue()) {
                System.out.println(city.getName() + " --(" + road.getDistance() + ")--> " + road.getTo().getName());
            }
        }
    }
}