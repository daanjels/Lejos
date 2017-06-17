package route;

/**
 * A main method to test my classes
 * 
 * @author Arqetype
 *
 */

public class Planner {

	public static void main(String[] args) {
		City A = new City("The Shire", 10, 20);
		City B = new City("Bree", 5, 60);
		City C = new City("Rivendell", 20, 110);
		City D = new City("Gondor", 60, 100);
		
		System.out.println("Cities and towns of Middle Earth: " + A.cityname + ", " 
				+ B.cityname + ", " + C.cityname + " and " + D.cityname);
		System.out.println("The town of the elves is at " + A.locatie());
		System.out.println(D.cityname + " is situated here: " + C.locatie());
		
		Road AB = new Road(A, B);
		Road BC = new Road(B, C);
		
		System.out.println("From " + AB.from.cityname + " to " + AB.to.cityname + " is " + AB.distance + "km.");
		System.out.println(BC);
		
		Map middleEarth = new Map("Middle Earth");
		middleEarth.addCity(A);
		middleEarth.addCity(B);
		middleEarth.addCity(C);
		middleEarth.addRoad(BC);
		middleEarth.addRoad(AB);
		
		System.out.println(middleEarth);
	}

}
