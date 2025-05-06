import java.io.*;
import java.util.*;

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
            }
            else if (mode.equals("FLOODED")) {
                City city = cities.get(line);
                if (city != null) {
                    city.setFlooded(true);
                    //System.out.println("City " + line + " is flooded.");
                }
            }
            else if (mode.equals("ROADS")) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    City from = cities.get(parts[0]);
                    City to = cities.get(parts[1]);
                    int dist = Integer.parseInt(parts[2]);
                    if (from != null && to != null) {
                        map.addRoad(from, to, dist);
                        //System.out.println("Road added: " + parts[0] + " -> " + parts[1] + ", Distance: " + dist);
                    }
                }
            }
            
        }

        br.close();
    }
}
