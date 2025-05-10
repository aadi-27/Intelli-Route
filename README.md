# <p align="center">🚀 IntelliRoute: Intelligent Route Planning Application 🗺️</p>

## <ins>**✨ Overview**</ins>

IntelliRoute is a <ins>**console-based**</ins> application designed to help users find the <ins>**best routes**</ins> between cities. Administrators can efficiently manage the map, while regular users can easily discover optimal travel plans based on their needs.

<p align="center">
  <img src="truck_icon.png" alt="Delivery Truck Icon" width="100">
</p>

## <ins>**🌟 Key Features**</ins>

* <ins>**🔒 Admin Mode:**</ins> Secure access for map management.
    * ➕ Add new cities
    * 🛣️ Define bidirectional routes with distances
    * 💾 Map data persistence in `map.txt`
* <ins>**🚶 User Mode:**</ins> Find your perfect route!
    * 🚲/🚚/🚐 Vehicle type input
    * 📦 Fragile package handling
    * 📍 Source and destination city selection
    * <ins>**🗺️ All Possible Routes:**</ins> Explore all options!
    * <ins>**🥇 Best Route (Dijkstra's):**</ins> The most convenient path
    * 🚦 Total stops information
    * <font color="red">⚠️</font> Flooded city warnings
    * <font color="yellow">❗</font> Fragile package reminders
* <ins>**💻 Console Interface:**</ins> Simple and intuitive text-based interaction
* <ins>**💾 Data Persistence:**</ins> Saves and loads map data from `map.txt`
* <ins>**🧠 Smart Pathfinding:**</ins> Dijkstra's for the shortest route
* <ins>**🔍 Comprehensive Exploration:**</ins> DFS for all possible paths
* <ins>**📢 User-Friendly Output:**</ins> Well-formatted and informative console messages

## <ins>**🛠️ Getting Started**</ins>

### <ins>**Prerequisites**</ins>

* ✅ Java Development Kit (JDK) installed

### <ins>**Setup**</ins>

1.  ⬇️ **Clone the repository** (if applicable) or **download the project files**.
2.  📂 Navigate to the project directory in your terminal.
3.  ⚙️ **Compile the Java source files:**
    ```bash
    javac model/*.java controller/*.java Intellirouteapp.java
    ```

### <ins>**🏃 Running the Application**</ins>

```bash
java Intellirouteapp
