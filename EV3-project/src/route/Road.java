package route;

/**
 * The Road class represents a connection between two cities.
 * It holds the two cities and the distance between them.
 * A Road is always straight, so the distance can be calculated 
 * via a method, using the coordinates of the city.
 * 
 * from: the city where the road starts from
 * to: the city where the road goes to
 * distance: the distance between the two cities
 * dist: a method to calculate the distance between the two cities
 * 
 * @author Arqetype
 *
 */

public class Road {
	City from;
	City to;
	double distance;
	boolean visited;
	
	public Road(City from, City to) {
		this.from = from;
		this.to = to;
		this.distance = afstand(from, to);
	}
	
	private double afstand(City from2, City to2) {
		double x1 = from.xPos;
		double y1 = from.yPos;
		double x2 = to.xPos;
		double y2 = to.yPos;
		double d = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
		return d;
	}
	
	public String toString() {
		return from.cityname + " - " + to.cityname + " (" + distance + "km)";
	}
}
