import java.util.*;

public class Mapgraph {
    private Map<City, List<Road>> adjList = new HashMap<>();

    public void addCity(City city) {
        adjList.putIfAbsent(city, new ArrayList<>());
    }

    public void addRoad(City from, City to, int distance) {
        Road road = new Road(from, to, distance);
        adjList.get(from).add(road);
    }

    public List<Road> getRoadsFromCity(City city) {
        return adjList.get(city);
    }
}
