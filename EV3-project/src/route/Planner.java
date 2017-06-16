package route;

/**
 * A main method to test my classes
 * 
 * @author Arqetype
 *
 */

public class Planner {

	public static void main(String[] args) {
		City A = new City("Antwerpen", 10, 20);
		City B = new City("Brussel", 5, 60);
		City C = new City("Charleroi", 20, 110);
		
		System.out.println("De steden zijn: " + A.cityname + ", " + B.cityname + " en " + C.cityname);
		System.out.println("De middelste stad ligt op " + A.xPos + " : " + A.yPos);
		System.out.println(C.cityname + " ligt op " + C.xPos + " : " + C.yPos);
		
		System.out.println(A.toString());
		System.out.println(B.cityname + " heeft locatie " + B.locatie());
		
		Road AB = new Road(A, B);
		Road BC = new Road(B, C);
		
		System.out.println("Van " + AB.from.cityname + " naar " + AB.to.cityname + " is " + AB.distance + "km.");
		System.out.println(BC);
	}

}
