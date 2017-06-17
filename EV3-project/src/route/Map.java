package route;

/**
 * The Map class keeps lists of cities and roads. 
 * It also contains several methods, the most important ones are to plan a route.
 * cities: an array of cities
 * roads: an array of roads between cities
 * addCity - method to add a City to the map
 * addRoad - method to add a Road to the map
 * planRoute - method that builds a Route
 * And then some helper methods
 * 
 * @author Arqetype
 *
 */

import java.util.ArrayList;

public class Map {
	String mapname;
	ArrayList<City> cities;
	ArrayList<Road> roads;
	
	public Map(String name) {
		mapname = name;
		cities = new ArrayList<City>();
		roads = new ArrayList<Road>();
	}
	
	public void addCity(City city) {
		cities.add(city);
	}
	
	public void addRoad(Road road) {
		roads.add(road);
	}
	
	public String toString() {
		String result = new String();
		
		result = "Map of " + this.mapname + "\n" + "Cities and towns: \n";
		for (int i = 0; i < this.cities.size(); i++) {
			result = result + "- " + this.cities.get(i) + "\n";
		}
		result = result + "Roads:\n";
		for (int i = 0; i < this.roads.size(); i++) {
			result = result + "- " + this.roads.get(i) + "\n";
		}
		return result;
	}
}
