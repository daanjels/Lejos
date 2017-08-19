package route;

/**
 * 
 * The City class represents a city on a map.
 * It has a name and X and Y coordinates.
 * cityname: the name of the city
 * xPos: the x coordinate of the city
 * yPos: the y coordinate of the city
 * 
 * @author Arqetype
 *
 */

public class City {
	String cityname;
	double xPos, yPos;
	
	public City(String name, double x, double y) {
		this.cityname = name;
		this.xPos = x;
		this.yPos = y;
	}
	
	public String toString() {
		return cityname + locatie();
	}
	
	public String locatie() {
		return "(" + xPos + ", " + yPos + ")";
	}
}
