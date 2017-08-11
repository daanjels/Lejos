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
		this.to= to;
		this.distance = dist(from.xPos, from.yPos, to.xPos, to.yPos);
	}
	
	static double dist(double x1, double y1, double x2, double y2) {
		double d = 0;
		d = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
		return d;
	}
	
	public String toString() {
		return from.cityname + " - " + to.cityname + " (" + distance + "km)";
	}
}
